package model;

/**
 * Generic Menu model
 *
 * @param <T>
 */
public class Menu<T> {
    protected int id;
    protected String name;
    protected String description;
    protected int price;
    protected T type;

    public Menu(int id, String name, String description, int price, T type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public Menu() {
    }

    public Menu(int id, String name, String description, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Menu(String name, String description, int price, T type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void show() {
        System.out.printf("%-5d %-21s %-21s %-15d %-15s%n", getId(), getName(), getDescription(), getPrice(), getType().toString());
    }
}
