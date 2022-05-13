package model;

import enumtype.DrinkType;

/**
 * Drink model
 */
public class Drink extends Menu<DrinkType> {
    public Drink(int id, String name, String description, int price, DrinkType type) {
        super(id, name, description, price, type);
    }

    /**
     * Default constructor
     */
    public Drink() {
    }

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param description
     * @param price
     */
    public Drink(int id, String name, String description, int price) {
        super(id, name, description, price);
    }

    public Drink(String name, String description, int price, DrinkType type) {
        super(name, description, price, type);
    }

    public Drink(int id) {
        this.id = id;
    }

}
