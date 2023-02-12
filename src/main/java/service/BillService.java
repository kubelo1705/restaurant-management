package service;

import exception.RestaurantException;
import model.Bill;

import java.io.IOException;
import java.util.List;

/**
 * Bill service interface define necessary function
 */
public interface BillService {
    /**
     * Save bill to list and update in file
     *
     * @param bill
     */
    void saveBill(Bill bill) throws IOException;

    /**
     * Get list bill from database
     */
    List<Bill> getAllBills();

    /**
     * Add item when create bill
     *
     * @param bill
     * @param clazz
     * @param id
     * @param quantity
     */
    void addItemToBill(Bill bill, Class clazz, int id, int quantity) throws RestaurantException;

    /**
     * Delete bill by id
     *
     * @param id
     */
    void deleteBill(int id) throws RestaurantException, IOException;

    /**
     * Check validate id in bill list
     *
     * @param id
     * @return
     */
    boolean checkIdBill(int id);

    /**
     * Get total of bill
     */
    int getTotalBillById(int id) throws Exception;

    /**
     * Change bill status when pay bill
     */
    void changeBillStatus(int id) throws Exception;

    /**
     * @param id
     * @return
     */
    Bill getBillById(int id);

    /**
     * Check if bill is unpaid
     *
     * @param id
     * @return
     */
    boolean checkUnpaidBill(int id);
}

/**
 * 1. Create a new class: RestaurantException
 * 1.1 class RestaurantException extends Exception
 */