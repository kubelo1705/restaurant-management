package model;

import enumtype.MenuType;

/**
 * Selected
 */
public class BillItem {
    private static int count = 1;
    private int id;
    private MenuType type;
    private String name;
    private int quantity;
    private int price;
    private int amount;

    public BillItem(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.amount = price * quantity;
    }

    public BillItem(MenuType type, String name, int quantity, int price) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.amount = price * quantity;
        this.id = count;
        count++;
    }

    public BillItem() {
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        BillItem.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmount() {
        this.amount = this.quantity * this.price;
    }

    public void show() {
        System.out.printf("%-5d %-20s %-15s %-10d %-15d %-15d%n", getId(), getName(), getType().toString(), getQuantity(), getPrice(), getAmount());
    }
}
