package controller;

import constant.Message;
import exception.RestaurantException;
import model.Bill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.BillService;
import service.BillServiceImp;
import service.MenuService;
import service.MenuServiceImp;

import java.util.List;

/**
 * Controller and redirect function concerned bill
 */
public class BillController {
    private static final Logger LOGGER = LogManager.getLogger(BillController.class);
    private static final BillService billService = new BillServiceImp();
    private static final MenuService menuService = new MenuServiceImp();

    /**
     * Add selected item to bill when create bill
     *
     * @param bill
     * @param clazz
     * @param id
     * @param quantity
     */
    public void addBillItem(Bill bill, Class clazz, int id, int quantity) throws RestaurantException {
        if (menuService.checkValidId(id, clazz)) {
            if (quantity > 0) {
                billService.addItemToBill(bill, clazz, id, quantity);
                LOGGER.debug("[{}]", "ADD ITEM ID=" + id + "QUANTITY=" + quantity + " TO BILL");
            } else {
                LOGGER.error("[{}]", "INVALID QUANTITY DATA");
                throw new RestaurantException(Message.ERROR_INVALID_VALUE);
            }
        } else {
            LOGGER.error("[{}]", "NON EXIST ITEM ID=" + id);
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }

    /**
     * Save bill to list
     *
     * @param bill
     */
    public void saveBill(Bill bill) throws Exception {
        if (!billService.checkIdBill(bill.getId())) {
            billService.saveBill(bill);
            LOGGER.debug("[{}]", "SAVE BILL ID=" + bill.getId());
        } else {
            LOGGER.error("[{}]", "DUPLICATE BILL ID=" + bill.getId());
            throw new RestaurantException(Message.ERROR_INVALID_VALUE);
        }
    }

    /**
     * Delete bill by id
     * @param id
     */
    public void deleteBill(int id) throws Exception {
        if (billService.checkIdBill(id)) {
            billService.deleteBill(id);
            LOGGER.debug("[{}]", "DELETE BILL ID=" + id);
        } else {
            LOGGER.error("[{}]", "NON EXIST BILL ID=" + id);
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }

    /**
     * Create new bill with identify id
     * @return
     */
    public Bill createBill() {
        int id = billService.getAllBills().size()+ 1;
        LOGGER.debug("[{}]", "CREATE EMPTY BILL ID=" + id);
        return new Bill(id);
    }

    /**
     * Get bill list
     */
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    /**
     * Pay bill by position
     */
    public int getTotalBill(int id) throws Exception {
        if(billService.checkIdBill(id)) {
            return billService.getTotalBillById(id);
        }else{
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }

    /**
     * Pay bill
     */
    public int payBill(int total,int inputMoney) throws RestaurantException {
        if(inputMoney>=0) {
            return inputMoney - total;
        }else{
            throw new RestaurantException(Message.ERROR_INVALID_VALUE);
        }
    }
    /**
     * Change bill status
     */
    public void changeBillStatus(int id) throws Exception {
        billService.changeBillStatus(id);
    }

    /**
     * Check id bill
     */
    public boolean checkIdBill(int id){
        return billService.checkIdBill(id);
    }

    /**
     * Check if bill is unpaid
     */
    public boolean checkUnpaidBill(int id){
        return billService.checkUnpaidBill(id);
    }

    /**
     * Get bill by id
     */
    public Bill getBillById(int id) throws RestaurantException{
        Bill bill=billService.getBillById(id);
        if(bill!=null){
            return bill;
        }else {
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }
}
