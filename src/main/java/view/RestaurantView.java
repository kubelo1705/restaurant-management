package view;

import constant.FormatOutput;
import constant.Message;
import constant.Options;
import controller.BillController;
import controller.MenuController;
import enumtype.DrinkType;
import enumtype.FoodType;
import exception.RestaurantException;
import model.Bill;
import model.Drink;
import model.Food;
import model.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * Show information for user can be use easily
 */
public class RestaurantView {
    private static final Logger LOGGER = LogManager.getLogger(RestaurantView.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static final BillController billController = new BillController();
    private static final MenuController menuController = new MenuController();

    /**
     * Order item and add it to bill
     */

    private void selectBillItem(Bill bill) {
        try {
            int selection;
            Class clazz;
            int id;
            int quantity;
            while (true) {
                LOGGER.debug("[{}]", "SELECT ITEM");
                System.out.println("1.Food");
                System.out.println("2.Drink");
                System.out.println("0.Exit");
                try {
                    selection = Integer.parseInt(scanner.nextLine());
                    if (selection == Options.FOOD || selection == Options.DRINK) {
                        if (selection == Options.FOOD) {
                            clazz = Food.class;
                        } else {
                            clazz = Drink.class;
                        }
                        LOGGER.debug("[{}]", "INPUT ID TO SELECT");
                        System.out.println("Enter id (0 to exit):");
                        id = Integer.parseInt(scanner.nextLine());
                        if (id != Options.EXIT) {
                            System.out.println("Quantity:");
                            quantity = Integer.parseInt(scanner.nextLine());
                            billController.addBillItem(bill, clazz, id, quantity);
                        }
                    } else if (selection == Options.EXIT) {
                        LOGGER.debug("[{}]", "SELECTION DONE");
                        break;
                    } else {
                        System.out.println(Message.INVALID_VALUE);
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("[{}]", e);
                    System.out.println(Message.ERROR_FORMAT_INPUT);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (!bill.getListSelectedItems().isEmpty()) {
                try {
                    billController.saveBill(bill);
                    LOGGER.debug("[{}]", "CREATE BILL SUCCESSFULLY");
                    showLatestBill();
                    LOGGER.debug("SHOW THE LATEST BILL");
                } catch (RestaurantException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(Message.ERROR_FORMAT_INPUT);
        } catch (Exception e) {
            System.out.println(e);
            LOGGER.error("[{}]", "INVALID POSITION");
        }
    }

    /**
     * Show menu manager. Include: add/delete/edit food and drink.
     */
    private void menuManager() {
        int selection = 1;
        while (selection != 0) {
            showFoodMenu();
            showDrinkMenu();
            LOGGER.debug("[{}]", "SHOW LIST MENU");

            System.out.println("1. Add menu");
            System.out.println("2. Delete menu");
            System.out.println("3. Edit menu");
            System.out.println("0. Exit");
            LOGGER.debug("[{}]", "SHOW OPTIONS MENU");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                LOGGER.error("[{}]", "INPUT EMPTY VALUE");
                System.out.println(Message.EMPTY_VALUE);
            } else {
                try {
                    selection = Integer.parseInt(input);
                    switch (selection) {
                        case Options.EXIT:
                            break;
                        case Options.ADD_MENU:
                            LOGGER.debug("[{}]", "GO TO ADD FOOD/DRINK");
                            boolean exitAdd = false;
                            while (!exitAdd) {
                                System.out.println("1.Food");
                                System.out.println("2.Drink");
                                System.out.println("0.Exit");
                                LOGGER.debug("[{}]", "SHOW FOOD/DRINK OPTIONS");

                                input = scanner.nextLine().trim();
                                if (input.isEmpty()) {
                                    System.out.println(Message.EMPTY_VALUE);
                                    LOGGER.error("[{}]", "INPUT EMPTY VALUE");
                                } else {
                                    try {
                                        int selectionAdd = Integer.parseInt(input);
                                        switch (selectionAdd) {
                                            case Options.EXIT:
                                                exitAdd = true;
                                                break;
                                            case Options.FOOD:
                                                LOGGER.debug("[{}]", "ADD FOOD");
                                                Menu food;
                                                food = createFood();
                                                food.show();
                                                menuController.addItemToMenu(food);
                                                exitAdd = true;
                                                break;
                                            case Options.DRINK:
                                                LOGGER.debug("[{}]", "ADD DRINK");
                                                Menu drink;
                                                drink = createDrink();
                                                menuController.addItemToMenu(drink);
                                                exitAdd = true;
                                                break;
                                            default:
                                                LOGGER.error("[{}]", "INVALID INPUT");
                                                System.out.println(Message.ERROR_NON_EXIST_VALUE);
                                                break;
                                        }
                                    } catch (NumberFormatException e) {
                                        LOGGER.error("[{}]", e);
                                        System.out.println(Message.INVALID_VALUE);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            }
                            LOGGER.debug("[{}]", "BACK TO MENU MANAGER");
                            break;
                        case Options.DELETE_MENU:
                            LOGGER.debug("[{}]", "GO TO DELETE FOOD/DRINK");
                            boolean exitDel = false;
                            while (!exitDel) {
                                System.out.println("1.Food");
                                System.out.println("2.Drink");
                                System.out.println("0.Exit");
                                LOGGER.debug("[{}]", "SHOW FOOD/DRINK OPTIONS");
                                input = scanner.nextLine().trim();
                                if (input.isEmpty()) {
                                    System.out.println(Message.EMPTY_VALUE);
                                    LOGGER.error("[{}]", "INPUT EMPTY VALUE");
                                } else {
                                    try {
                                        int selectionDel = Integer.parseInt(input);
                                        switch (selectionDel) {
                                            case Options.EXIT:
                                                exitDel = true;
                                                break;
                                            case Options.FOOD:
                                                LOGGER.debug("[{}]", "DELETE FOOD");
                                                deleteMenuItem(Food.class);
                                                exitDel = true;
                                                break;
                                            case Options.DRINK:
                                                LOGGER.debug("[{}]", "DELETE DRINK");
                                                deleteMenuItem(Drink.class);
                                                exitDel = true;
                                                break;
                                            default:
                                                LOGGER.debug("[{}]", "INVALID VALUE");
                                                System.out.println(Message.ERROR_NON_EXIST_VALUE);
                                                break;
                                        }
                                    } catch (NumberFormatException e) {
                                        LOGGER.error("[{}]", e);
                                        System.out.println(Message.INVALID_VALUE);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            }
                            break;
                        case Options.EDIT_MENU:
                            boolean exitEdit = false;
                            while (!exitEdit) {
                                System.out.println("1.Food");
                                System.out.println("2.Drink");
                                System.out.println("0.Exit");
                                input = scanner.nextLine().trim();
                                if (input.isEmpty()) {
                                    System.out.println(Message.EMPTY_VALUE);
                                    LOGGER.debug("[{}]", "INPUT EMPTY VALUE");
                                } else {
                                    try {
                                        int selectionEdit = Integer.parseInt(input);
                                        switch (selectionEdit) {
                                            case Options.EXIT:
                                                exitEdit = true;
                                                break;
                                            case Options.FOOD:
                                                LOGGER.debug("[{}]", "EDIT FOOD");
                                                editMenu(Food.class);
                                                exitEdit = true;
                                                break;
                                            case Options.DRINK:
                                                LOGGER.debug("[{}]", "EDIT DRINK");
                                                editMenu(Drink.class);
                                                exitEdit = true;
                                                break;
                                            default:
                                                LOGGER.error("[{}]", "INVALID VALUE");
                                                System.out.println(Message.ERROR_NON_EXIST_VALUE);
                                                break;
                                        }
                                    } catch (NumberFormatException e) {
                                        LOGGER.error("[{}]", e);
                                        System.out.println(Message.INVALID_VALUE);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            }
                            break;
                        default:
                            LOGGER.error("[{}]", "INVALID INPUT");
                            System.out.println(Message.INVALID_VALUE);
                            break;
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("[{}]", e);
                    System.out.println(Message.INVALID_VALUE);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * Display drink menu
     */
    public void showDrinkMenu() {
        LOGGER.debug("[{}]", "SHOW DRINK MENU");
        System.out.println("=============DRINK MENU=============");
        System.out.printf(FormatOutput.FORMAT_MENU, "id", "name", "description", "price", "type");
        menuController.getAllDrink().forEach(drink -> drink.show());
    }

    /**
     * Display food menu
     */
    public void showFoodMenu() {
        LOGGER.debug("[{}]", "SHOW FOOD MENU");
        System.out.println("=============FOOD MENU=============");
        System.out.printf(FormatOutput.FORMAT_MENU, "id", "name", "description", "price", "type");
        menuController.getAllFood().forEach(food -> food.show());
    }

    /**
     * Edit menu by id
     */
    private void editMenu(Class clazz) {
        LOGGER.debug("[{}]", "INPUT ID MENU TO EDIT");
        System.out.println("Enter id to edit:");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println(Message.EMPTY_VALUE);
            LOGGER.error("[{}]", "INPUT ID MENU IS EMPTY");
        } else {
            try {
                int id = Integer.parseInt(input);
                Menu menu;
                if (clazz == Food.class)
                    menu = createFood();
                else
                    menu = createDrink();
                if (menu == null) {
                    System.out.println(Message.ERROR_CREATE_MENU);
                } else {
                    menu.setId(id);
                    menuController.editItemInMenu(menu);
                    LOGGER.error("[{}]", "MENU EDITED");
                }
            } catch (NumberFormatException e) {
                LOGGER.error("[{}]", e);
                System.out.println(Message.INVALID_VALUE);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Create new food
     */
    private Menu createFood() throws Exception {
        LOGGER.debug("[{}]", "INPUT VALUE TO CREATE FOOD");
        Menu food = menuController.createMenuItem(Food.class);
        inputBasicParam(food);
        FoodType typeFood = FoodType.BREAKFAST;
        System.out.println("Type:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            LOGGER.error("[{}]", "INPUT EMPTY VALUE");
            System.out.println(Message.EMPTY_VALUE);
        } else {
            try {
                int selection = Integer.parseInt(input);
                if (selection == Options.BREAKFAST || selection == Options.LUNCH || selection == Options.DINNER) {
                    if (selection == Options.BREAKFAST)
                        typeFood = FoodType.BREAKFAST;
                    if (selection == Options.LUNCH)
                        typeFood = FoodType.LUNCH;
                    if (selection == Options.DINNER)
                        typeFood = FoodType.DINNER;
                    food.setType(typeFood);
                } else {
                    LOGGER.error("[{}]", "INPUT VALID VALUE");
                    System.out.println(Message.INVALID_VALUE);
                }
            } catch (NumberFormatException e) {
                LOGGER.error("[{}]", "INPUT VALID VALUE");
                System.out.println(Message.INVALID_VALUE);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        LOGGER.debug("[{}]", "CREATED FOOD");
        return food;
    }

    /**
     * Create new drink
     */
    private Menu createDrink() throws Exception {
        LOGGER.debug("[{}]", "INPUT VALUE TO CREATE DRINK");
        boolean check = false;
        Menu drink = menuController.createMenuItem(Drink.class);
        DrinkType alcohol = DrinkType.ALCOHOLIC;
        inputBasicParam(drink);
        while (!check) {
            System.out.println("Alcohol?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String input = scanner.nextLine().trim();
            if (input.isEmpty())
                System.out.println(Message.EMPTY_VALUE);
            else {
                try {
                    int selection = Integer.parseInt(input);
                    if (selection == Options.ALCOHOL || selection == Options.NON_ALCOHOL) {
                        if (selection == 2)
                            alcohol = DrinkType.NON_ALCOHOLIC;
                        drink.setType(alcohol);
                        check = true;
                    } else {
                        LOGGER.error("[{}]", "INPUT VALID VALUE");
                        System.out.println(Message.INVALID_VALUE);
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("[{}]", "INPUT VALID VALUE");
                    System.out.println(Message.INVALID_VALUE);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        LOGGER.debug("[{}]", "CREATED DRINK");
        return drink;
    }

    /**
     * Input basic parameters to new menu item
     *
     * @param menu
     */
    private void inputBasicParam(Menu menu) {
        boolean check = false;
        while (!check) {
            try {
                LOGGER.debug("[{}]", "INPUT GENERAL VALUES TO CREATE MENU");
                String name = "";
                String description = "";
                int price = 0;
                System.out.println("Name:");
                name = scanner.nextLine().trim();
                System.out.println("Description:");
                description = scanner.nextLine().trim();
                while (price == 0) {
                    System.out.println("Price:");
                    price = Integer.parseInt(scanner.nextLine().trim());
                }
                if (name.isEmpty() || description.isEmpty()) {
                    LOGGER.error("[{}]", "INPUT EMPTY VALUES");
                    System.out.println(Message.EMPTY_VALUE);
                } else {
                    menu.setName(name);
                    menu.setDescription(description);
                    menu.setPrice(price);
                    check = true;
                }
            } catch (Exception e) {
                LOGGER.error("[{}]", e);
                System.out.println(Message.ERROR_FORMAT_INPUT);
            }
        }
    }

    /**
     * Delete menu item by id
     */
    private void deleteMenuItem(Class clazz) {
        LOGGER.error("[{}]", "INPUT ID TO DELETE MENU");
        System.out.println("Input id to delete:");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            LOGGER.error("[{}]", "INPUT EMPTY VALUES");
            System.out.println(Message.EMPTY_VALUE);
        } else {
            try {
                int id = Integer.parseInt(input);
                menuController.deleteItemInMenu(id, clazz);
            } catch (NumberFormatException e) {
                LOGGER.error("[{}]", e);
                System.out.println(Message.ERROR_INVALID_VALUE);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Show bill manager. Include: show detail bill by id, delete bill by id
     */
    private void billManager() {
        int selection = 1;
        while (selection != 0) {
            showBillList();
            System.out.println("--------------------");
            LOGGER.debug("[{}]", "SHOW OPTIONS BILL");
            if (billController.getAllBills().size() != 0) {
                System.out.println("1. Show bill detail");
                System.out.println("2. Delete bill");
                System.out.println("3. Pay bill");
                System.out.println("0. Exit");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    LOGGER.error("[{}]", "INPUT EMPTY VALUES");
                    System.out.println(Message.EMPTY_VALUE);
                } else {
                    try {
                        selection = Integer.parseInt(input);
                        switch (selection) {
                            case Options.DETAIL_BILL:
                                LOGGER.debug("[{}]", "INPUT ID TO DETAILS BILL");
                                System.out.println("Enter id bill to detail");
                                int idDetail = Integer.parseInt(scanner.nextLine());
                                if (idDetail != 0) {
                                    showBillDetailById(idDetail);
                                }
                                break;
                            case Options.DELETE_BILL:
                                LOGGER.debug("[{}]", "INPUT ID TO DELETE BILL");
                                System.out.println("Enter id bill to delete");
                                int idDelete = Integer.parseInt(scanner.nextLine());
                                if (idDelete != 0) {
                                    billController.deleteBill(idDelete);
                                }
                                break;
                            default:
                                break;
                        }
                    } catch (NumberFormatException e) {
                        LOGGER.error("[{}]", e);
                        System.out.println(Message.ERROR_FORMAT_INPUT);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }

    /**
     * Show list bill
     */
    public void showBillList() {
        LOGGER.debug("[{}]", "SHOW BILL LIST");
        List<Bill> billList = billController.getAllBills();
        System.out.println("=============BILL LIST=============");
        if (billList.size() == 0) {
            System.out.println("EMPTY");
        } else {
            System.out.printf(FormatOutput.FORMAT_BILL, "id", "total", "date", "status");
            billList.forEach((bill) -> System.out.printf(FormatOutput.FORMAT_BILL, bill.getId(), bill.getTotal(), bill.getDate(), bill.getStatus()));
        }
    }

    /**
     * Show all information of bill by id
     *
     * @param idBill
     */
    public void showBillDetailById(int idBill) {
        Bill billShow = billController.getAllBills().stream().filter(bill -> bill.getId() == idBill).findFirst().orElse(null);
        if (billShow != null) {
            LOGGER.debug("[{}]", "SHOW BILL ID=" + idBill);
            System.out.printf(FormatOutput.FORMAT_BILL_DETAIL, "id", "name", "type", "quantity", "price", "amount");
            billShow.getListSelectedItems().forEach((o) -> o.show());
            System.out.println("TOTAL:" + billShow.getTotal());
        } else {
            LOGGER.error("[{}]", "NON EXIST BILL ID=" + idBill);
            System.out.println(Message.ERROR_NON_EXIST_VALUE);
        }
    }

    /**
     * Show bill was just created
     */
    public void showLatestBill() {
        LOGGER.debug("[{}]", "SHOW LATEST BILL");
        showBillDetailById(billController.getAllBills().size());
    }

    /**
     * Show total and input money
     */
    public void payBill() {
        int id;
        int total;
        int inputMoney = 0;
        int changeMoney = -1;
        try {
            System.out.println("Input id bill to pay:");
            id = Integer.parseInt(scanner.nextLine());
            total = billController.getTotalBill(id);
            try {
                while (changeMoney < 0) {
                    System.out.println("Input money:");
                    int input = Integer.parseInt(scanner.nextLine());
                    if (input == Options.EXIT)
                        break;
                    if (input > 0) {
                        inputMoney += input;
                        changeMoney = billController.payBill(total, inputMoney);
                        if (changeMoney < 0) {
                            System.out.println("Not enough money. Please add money. ");
                        } else {
                            if (changeMoney == 0) {
                                System.out.println("Thanks you");
                            } else {
                                System.out.println("Change money: " + changeMoney);
                            }
                            billController.changeBillStatus(id);
                            LOGGER.debug("[{}]", "PAID BILL ID=", id);
                        }
                    } else {
                        System.out.println(Message.ERROR_INVALID_VALUE);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                LOGGER.error("[{}]", e);
            }
        } catch (Exception e) {
            System.out.println(e);
            LOGGER.error("[{}]", e);
        }
    }

    public void addItemToBill() {
        int id;
        System.out.println("Input id bill:");
        try {
            id = Integer.parseInt(scanner.nextLine());
            if (billController.checkUnpaidBill(id)) {
                Bill bill = billController.getBillById(id);
                selectBillItem(bill);
            } else {
                System.out.println(Message.ERROR_INVALID_VALUE);
            }
        } catch (Exception e) {
            System.out.println(e);
            LOGGER.error(e);
        }
    }

    /**
     * Display main options to choose
     */
    public void run() {
        LOGGER.debug("[{}]", "START......................................");
        int selection = 1;
        while (selection != 0) {
            System.out.println("1. Menu Manager");
            System.out.println("2. Bill Manager");
            System.out.println("3. Create Bill");
            System.out.println("4. Add item to bill");
            System.out.println("5. Pay bill");
            System.out.println("0. Exit");
            System.out.println("Enter your selection: ");
            LOGGER.error("[{}]", "SHOW MAIN OPTIONS");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(Message.EMPTY_VALUE);
                LOGGER.debug("[{}]", "INPUT EMPTY VALUE");
            } else {
                try {
                    selection = Integer.parseInt(input);
                    switch (selection) {
                        case Options.EXIT:
                            break;
                        case Options.MENU_MANAGER:
                            LOGGER.debug("[{}]", "GO TO MENU MANAGER");
                            menuManager();
                            LOGGER.debug("[{}]", "EXIT MENU MANAGER");
                            break;
                        case Options.BILL_MANAGER:
                            LOGGER.debug("[{}]", "GO TO BILL MANAGER");
                            billManager();
                            LOGGER.debug("[{}]", "EXIT MENU MANAGER");
                            break;
                        case Options.CREATE_BILL:
                            Bill bill = billController.createBill();
                            LOGGER.debug("[{}]", "GO TO CREATE BILL");
                            selectBillItem(bill);
                            break;
                        case Options.ADD_ITEM_TO_BILL:
                            addItemToBill();
                            break;
                        case Options.PAY_BILL:
                            payBill();
                            break;
                        default:
                            LOGGER.error("[{}]", "INVALID INPUT");
                            System.out.println(Message.INVALID_VALUE);
                            break;
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("[{}]", e);
                    System.out.println(Message.INVALID_VALUE);
                }

                LOGGER.debug("[{}]", "GO TO MAIN OPTIONS");
            }
        }
        LOGGER.debug("[{}]", "END......................................");
        scanner.close();
    }
}
