package model;

import enumtype.FoodType;

/**
 * Food model
 */
public class Food extends Menu<FoodType> {
    public Food(int id, String name, String description, int price, FoodType type) {
        super(id, name, description, price, type);
    }

    public Food() {
    }

    public Food(int id) {
        this.id = id;
    }

    public Food(String name, String description, int price, FoodType type) {
        super(name, description, price, type);
    }

    public Food(int id, String name, String description, int price) {
        super(id, name, description, price);
    }

}
