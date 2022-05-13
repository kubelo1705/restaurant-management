package controller;

import constant.Message;
import exception.RestaurantException;
import model.Drink;
import model.Food;
import model.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MenuService;
import service.MenuServiceImp;

import java.util.List;


/**
 * Controller and redirect function concerned menu (food and bill)
 */
public class MenuController {
    private static final Logger LOGGER = LogManager.getLogger(MenuController.class);
    private static final MenuService menuService = new MenuServiceImp();

    /**
     * Get food list
     */
    public List<Menu> getAllFood() {
        return menuService.getMenuByClass(Food.class);
    }

    /**
     * Get food list
     */
    public List<Menu> getAllDrink() {
        return menuService.getMenuByClass(Drink.class);
    }

    /**
     * @param id drink
     * @return drink with this id in list
     */
    public Menu getMenuItemById(int id, Class clazz) {
        LOGGER.error("[{}]", clazz.getSimpleName() + ":ID=" + id);
        return menuService.getMenuByClass(clazz).stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    /**
     * Add new item to list
     *
     * @param menu
     */
    public void addItemToMenu(Menu menu) throws Exception {
        if (menuService.checkValidId(menu.getId(), menu.getClass())) {
            if (menuService.getMenuByClass(menu.getClass()).stream().filter(item -> item.getName().equals(menu.getName())).findFirst().orElse(null) == null) {
                menuService.addItemToMenu(menu);
                LOGGER.debug("[{}]", "ADD " + menu.getClass().getSimpleName() + " TO MENU");
            } else {
                LOGGER.error("[{}]", "DUPLICATE NAME IN MENU");
                throw new RestaurantException(Message.DUPLICATE_MENU);
            }
        } else {
            LOGGER.error("[{}]", "INVALID INPUT ID");
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }

    /**
     * Delete drink in list by id
     *
     * @param id
     */
    public void deleteItemInMenu(int id, Class clazz) throws Exception {
        if (menuService.checkValidId(id, clazz)) {
            menuService.deleteItemInMenu(id, clazz);
            LOGGER.debug("[{}]", "DELETE " + clazz.getSimpleName() + " ID=" + id);
        } else {
            LOGGER.error("[{}]", "INVALID INPUT ID");
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }

    /**
     * Create new item in menu based on clazz
     *
     * @param clazz
     * @return new item with identify id
     */
    public Menu createMenuItem(Class clazz) throws Exception {
        return menuService.createItem(clazz);
    }

    public void editItemInMenu(Menu newMenu) throws Exception {
        Class clazz = newMenu.getClass();
        if (menuService.checkValidId(newMenu.getId(), clazz)) {
            if (menuService.getMenuByClass(newMenu.getClass()).stream().filter(item -> item.getName().equals(newMenu.getName())).findFirst().orElse(null) == null) {
                menuService.editItemInMenu(newMenu, clazz);
                LOGGER.debug("[{}]", "EDIT " + newMenu.getClass().getSimpleName() + " IN MENU ID=" + newMenu.getId());
            } else {
                LOGGER.error("[{}]", "DUPLICATE NAME IN MENU");
                throw new RestaurantException(Message.DUPLICATE_MENU);
            }
        } else {
            LOGGER.error("[{}]", "INVALID INPUT ID");
            throw new RestaurantException(Message.ERROR_NON_EXIST_VALUE);
        }
    }
}
