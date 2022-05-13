package service;

import exception.RestaurantException;
import filehandler.JsonHandler;
import model.Drink;
import model.Food;
import model.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ServiceUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bill service
 * Handle logic about menu (food and drink)
 */
public class MenuServiceImp implements MenuService {
    private static final Logger LOGGER = LogManager.getLogger(MenuServiceImp.class);
    private static final Map<String, List<Menu>> mapMenu = new HashMap<>();

    //get data from json
    static {
        mapMenu.put(Food.class.getSimpleName(), JsonHandler.getListFromFile(Food.class));
        mapMenu.put(Drink.class.getSimpleName(), JsonHandler.getListFromFile(Drink.class));
    }

    @Override
    public List<Menu> getMenuByClass(Class clazz) {
        return mapMenu.get(clazz.getSimpleName());
    }

    /**
     * Add new item to menu and update id in menu list
     * @param menu
     * @throws RestaurantException
     */
    @Override
    public void addItemToMenu(Menu menu) throws IOException {
        Class clazz = menu.getClass();
        List<Menu> list = getMenuByClass(clazz);
        list.add(menu);
        LOGGER.debug("[{}]", "ADD " + menu.getClass().getSimpleName() + " NAME=" + menu.getName() + " TO MENU");
        ServiceUtils.updateIdMenu(list);
        JsonHandler.saveListToFile(list);
    }

    /**
     * Delete item in menu and update id in menu list
     *
     * @param id
     * @param clazz
     * @throws RestaurantException
     */
    @Override
    public void deleteItemInMenu(int id, Class clazz) throws IOException {
        List<Menu> list = mapMenu.get(clazz.getSimpleName());
        list.remove(id - 1);
        LOGGER.debug("[{}]", "DELETE " + clazz.getSimpleName() + " ID=" + id + " FROM MENU");
        ServiceUtils.updateIdMenu(list);
        JsonHandler.saveListToFile(list);
    }

    @Override
    public void editItemInMenu(Menu menu, Class clazz) throws IOException {
        List<Menu> list = mapMenu.get(clazz.getSimpleName());
        list.set(menu.getId() - 1, menu);
        LOGGER.debug("[{}]", "EDIT " + menu.getClass().getSimpleName() + " ID=" + menu.getId() + " TO MENU");
        ServiceUtils.updateIdMenu(list);
        JsonHandler.saveListToFile(list);
    }

    @Override
    public Menu createItem(Class clazz) throws Exception {
        Constructor constructor = clazz.getConstructor(int.class);
        //get id of latest item in menu
        Object object = constructor.newInstance(getMenuByClass(clazz).get(getMenuByClass(clazz).size() - 1).getId() + 1);
        return (Menu) object;
    }

    @Override
    public boolean checkValidId(int id, Class clazz) {
        return ServiceUtils.checkIdInList(mapMenu.get(clazz.getSimpleName()), id);
    }
}
