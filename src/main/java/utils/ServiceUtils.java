package utils;

import model.Bill;
import model.Menu;

import java.util.List;

public class ServiceUtils {
    public static void updateIdBill(List<Bill> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId(i + 1);
        }
    }

    public static void updateIdMenu(List<Menu> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId(i + 1);
        }
    }

    public static <T> T getLastIndexInList(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> boolean checkIdInList(List<T> list, int id) {
        if (id > 0 && id <= list.size())
            return true;
        return false;
    }

}
