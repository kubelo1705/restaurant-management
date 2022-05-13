package service;

import constant.Message;
import enumtype.BillStatus;
import enumtype.MenuType;
import exception.RestaurantException;
import filehandler.JsonHandler;
import model.Bill;
import model.BillItem;
import model.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ServiceUtils;

import java.io.IOException;
import java.util.List;

/**
 * Bill service
 * Handle logic about bill
 */
public class BillServiceImp implements BillService {
    private static final Logger LOGGER = LogManager.getLogger(BillServiceImp.class);
    private static MenuService menuService = new MenuServiceImp();

    @Override
    public List<Bill> getAllBills() {
        return JsonHandler.getListFromFile(Bill.class);
    }

    @Override
    public void saveBill(Bill bill) throws IOException {
        List<Bill> bills=getAllBills();
        bills.add(bill);
        LOGGER.debug("{[]}", "SAVE BILL TO FILE");
        JsonHandler.saveListToFile(bills);
        bills.clear();
    }

    /**
     * Delete bill and update new id in bill list
     * @param id
     * @throws RestaurantException
     */
    @Override
    public void deleteBill(int id) throws IOException {
        List<Bill> bills=getAllBills();
        bills.remove(id - 1);
        LOGGER.debug("{[]}", "DELETE SUCCESSFULLY");
        ServiceUtils.updateIdBill(bills);
        JsonHandler.saveListToFile(getAllBills());
        LOGGER.debug("{[]}", "SAVE BILL LIST TO FILE");
    }

    /**
     * Add item to new bill and update new id in bill list
     *
     * @param bill
     * @param clazz
     * @param id
     * @param quantity
     * @throws RestaurantException
     */
    @Override
    public void addItemToBill(Bill bill, Class clazz, int id, int quantity) {
        List<Menu> menus = menuService.getMenuByClass(clazz);
        Menu menu = menus.get(id - 1);
        BillItem billItem = new BillItem(MenuType.valueOf(clazz.getSimpleName().toUpperCase()), menu.getName(), quantity, menu.getPrice());
        bill.setId(getAllBills().get(getAllBills().size() - 1).getId() + 1);
        bill.getListSelectedItems().add(billItem);
        bill.setTotal(bill.getTotal() + billItem.getPrice() * billItem.getQuantity());
    }

    @Override
    public boolean checkIdBill(int id) throws IndexOutOfBoundsException {
        List<Bill> bills=getAllBills();
        return ServiceUtils.checkIdInList(bills, id);
    }

    @Override
    public boolean checkUnpaidBill(int id){
        return (getAllBills().stream().filter(bill -> bill.getId()==id).findFirst().orElse(null).getStatus()==BillStatus.UNPAID?true:false)&&checkIdBill(id);
    }
    @Override
    public int getTotalBillById(int id) throws Exception{
        Bill bill=getBillById(id);
        if(bill!=null){
            return bill.getTotal();
        }else{
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }

    @Override
    public Bill getBillById(int id){
        try{
            List<Bill> bills=getAllBills();
            if (ServiceUtils.checkIdInList(bills,id)){
                return bills.stream().filter(bill -> bill.getId()==id).findFirst().orElse(null);
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void changeBillStatus(int id) throws Exception {
        List<Bill> bills=getAllBills();
        Bill billChange=bills.stream().filter(bill -> bill.getId()==id).findFirst().orElse(null);
        if(billChange!=null){
            billChange.setStatus(BillStatus.PAID);
            JsonHandler.saveListToFile(bills);
        }else{
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }
}
