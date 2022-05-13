package service;

import exception.RestaurantException;
import model.Menu;

import java.io.IOException;
import java.util.List;

/**
 * Menu service interface define necessary function
 */
public interface MenuService {

    List<Menu> getMenuByClass(Class clazz);

    /**
     * Add  item to menu list
     *
     * @param menu
     */
    void addItemToMenu(Menu menu) throws RestaurantException, IOException;

    /**
     * Delete  item in menu by id
     *
     * @param id
     */
    void deleteItemInMenu(int id, Class clazz) throws RestaurantException, IOException;

    /**
     * Overwrite menu in list by id
     */
    void editItemInMenu(Menu menu, Class clazz) throws RestaurantException, IOException;

    /**
     * Create new menu item in menu
     */
    Menu createItem(Class clazz) throws Exception;

    /**
     * Check valid id in menu
     */
    boolean checkValidId(int id, Class clazz);
}
