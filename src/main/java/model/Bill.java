package model;

import enumtype.BillStatus;
import utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Bill model
 */
public class Bill {
    private int id;
    private int total;
    private String date;
    private List<BillItem> listSelectedItems = new ArrayList<BillItem>();
    private BillStatus status;

    public Bill(int id) {
        this.id = id;
        this.date = TimeUtils.getCurrentFormatedTime();
        status = BillStatus.UNPAID;
        this.total = 0;
    }

    public Bill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BillItem> getListSelectedItems() {
        return listSelectedItems;
    }

    public void setListSelectedItems(List<BillItem> listSelectedItems) {
        this.listSelectedItems = listSelectedItems;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }
}
