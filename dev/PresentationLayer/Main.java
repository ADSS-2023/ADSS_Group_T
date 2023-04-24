package PresentationLayer;
import BusinessLayer.Stock.Util.Util;
import ServiceLayer.Stock.*;

import java.time.DayOfWeek;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.Supplier;

public class Main {
    public static InventoryService inventoryService = new InventoryService();
    public static CategoryService categoryService = new CategoryService(inventoryService.get_inventory());
    public static DamagedService damagedService = new DamagedService(inventoryService.get_inventory());
    public static ItemService itemService = new ItemService(inventoryService.get_inventory());
    public static OrderService orderService = new OrderService(inventoryService.get_inventory() , new Supplier.OrderService);

    public static void printOptions(){
        System.out.println("\u001B[32m1.See categories\u001B[0m");
        System.out.println("\u001B[32m2.Produce inventory report\u001B[0m");
        System.out.println("\u001B[32m3.Set discount\u001B[0m");
        System.out.println("\u001B[32m4.Report damaged item\u001B[0m");
        System.out.println("\u001B[32m5.Set minimal amount for a specific item\u001B[0m");
        System.out.println("\u001B[32m6.Produce damaged items report\u001B[0m");
        System.out.println("\u001B[32m7.Add new item\u001B[0m");
        System.out.println("\u001B[32m8.Receive a new order (receive new supply of exists item)\u001B[0m");
        System.out.println("\u001B[32m9.Produce shortage report\u001B[0m");
        System.out.println("\u001B[32m10.Add new category\u001B[0m");
        System.out.println("\u001B[32m11.Skip day\u001B[0m");
        System.out.println("\u001B[32m12.Orders menu\u001B[0m");
    }

    public static String presentCategories(){
        System.out.println("press index of category/item in order to dive in,\npress 0 in order to choose the current category\npress -1 to exit");
        Scanner scanner = new Scanner(System.in);
        System.out.println(inventoryService.show_data());
        boolean is_active = true;
        String next_index="";
        while (is_active){
            int choise = scanner.nextInt();
            if (choise == -1){
                is_active = false;
                next_index = "exit";
            }
            else if(choise == 0)
                is_active = false;
            else {
                next_index += "." + (choise-1);
                String toShow = categoryService.show_data(next_index);
                System.out.println(toShow);
            }
        }
        return next_index;
    }

    private static void inventoryReport() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> categories = new LinkedList<>();
        boolean is_active = true;
        while (is_active){
            String result = presentCategories();
            if (result != "exit")
                categories.add(result);
            System.out.println("Would you like another category?\n1.yes 2.no");
            if (scanner.nextInt() == 2)
                is_active = false;
        }
        if (categories.isEmpty())
            System.out.println("you didnt choose any category");
        else
            System.out.println(inventoryService.produce_inventory_report(categories));

    }

    public static void setDiscount(){
        Scanner scanner = new Scanner(System.in);
        String product = presentCategories();

        System.out.println("Choose dates by the next format : yyyy-mm-dd");
        System.out.println("Choose start date :");
        String start_date_string = scanner.nextLine();
        System.out.println("Choose end date :");
        String end_date_string = scanner.nextLine();
        System.out.println("Choose percentage amount :");
        double percentageAmount = scanner.nextDouble();
        inventoryService.set_discount(product , percentageAmount , end_date_string , start_date_string);
    }

    private static void setMinimalAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("insert item id:");
        int item_id = scanner.nextInt();
        System.out.println("insert minimal amount:");
        int amount = scanner.nextInt();
        System.out.println(itemService.setMinimalAmount(item_id,amount));
    }

    private static void damagedItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("insert item id:");
        int item_id = scanner.nextInt();
        System.out.println("insert order id:");
        int order_id = scanner.nextInt();
        System.out.println("insert amount:");
        int amount = scanner.nextInt();
        System.out.println("insert reason of damaged");
        String description = scanner.next();
        System.out.println(damagedService.report_damaged_item(item_id,order_id,amount,description));
    }

    private static void damageItemReport() {
        System.out.println(damagedService.produce_damaged_report());
    }

    private static void addItem() {
        Scanner scanner = new Scanner(System.in);
        String choise = presentCategories();
        System.out.println("insert item id:");
        int item_id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("insert name:");
        String name = scanner.nextLine();
        System.out.println("what is the minimal amount for alert for this item?");
        int amount = scanner.nextInt();
        System.out.println("insert manufacturer name:");
        String manufacturer = scanner.next();
        System.out.println("insert price for costumer:");
        double price = scanner.nextDouble();
        itemService.addItem(choise,item_id,name,amount,manufacturer,price);
    }

    public static void receive_order(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter order id:");
        int order_id = scanner.nextInt();
        System.out.println("Enter item id:");
        int item_id = scanner.nextInt();
        System.out.println("Enter amount that have received:");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter location in store:");
        String location = scanner.nextLine();
        System.out.println("Choose dates by the next format : yyyy-mm-dd");
        System.out.println("Choose validity date :");
        String validity = scanner.nextLine();
        System.out.println("what is the cost price?");
        double cost_price = scanner.nextDouble();
        System.out.println(itemService.receive_order(order_id,item_id,amount,location, Util.stringToDate(validity),cost_price));
    }

    private static void produceShortageReport() {
        System.out.println(inventoryService.produce_shortage_report());
    }

    public static void act(String choise){
        switch (choise) {
            case "1":
                presentCategories();
                break;
            case "2":
                inventoryReport();
                break;
            case "3":
                setDiscount();
                break;
            case "4":
                damagedItem();
                break;
            case "5":
                setMinimalAmount();
                break;
            case "6":
                damageItemReport();
                break;
            case "7":
                addItem();
                break;
            case "8":
                receive_order();
                break;
            case "9":
                produceShortageReport();
                break;
            case "10":
                addCategory();
                break;
            case "11":
                moveToNextDay();
                break;
            case "12":
                edit_create_orders();
                break;
            case "21":
                editRegularItemOrder();
                break;
            case "22":
                create_regular_order();
                break;
            case "23":
                create_special_order();
                break;
            case "24":
                place_waiting_items();
                break;
            case "logout":
                break;
        }
    }

    private static void place_waiting_items() {
        boolean isActive = true;
        while (isActive) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose item to be placed:");
            System.out.println(orderService.presentItemsToBePlaced());
            int choise = scanner.nextInt();
            System.out.println("Where to place the item? ile:'ile number' shelf:'shelf number'");
            String location = scanner.nextLine();
            orderService.placeNewArrival(choise,location);
            System.out.println("Would you like to place another item?\n1.yes 2.no");
            choise = scanner.nextInt();
            isActive = choise==1;
        }

    }

    private static void create_special_order() {
        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        HashMap<Integer,Integer> products = new HashMap<>();
        while (isActive){
            System.out.println("Insert item id");
            int id = scanner.nextInt();
            System.out.println("Insert amount desired");
            int amount = scanner.nextInt();
            System.out.println("1.Add more products , 2.Finish order");
            int choice = scanner.nextInt();
            products.put(id,amount);
            isActive = choice==1;
        }
        System.out.println("Do you want to mark this order as urgent?\n1.yes 2.no");
        int choice = scanner.nextInt();
        boolean isUrgent = choice == 1;
        orderService.createSpecialOrder(products,isUrgent);
    }

    private static void create_regular_order() {
        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        HashMap<Integer,Integer> products = new HashMap<>();
        while (isActive){
            System.out.println("Insert item id");
            int id = scanner.nextInt();
            System.out.println("Insert amount desired");
            int amount = scanner.nextInt();
            System.out.println("1.Add more products , 2.Finish order");
            int choice = scanner.nextInt();
            products.put(id,amount);
            isActive = choice==1;
        }
        orderService.createRegularOrder(products);
    }

    private static void edit_create_orders() {
        Scanner scanner = new Scanner(System.in);
        printOrderOptions();
        System.out.println("What would you like to do?");
        String choice = scanner.nextLine();
        act("2"+choice);
        System.out.println("\n");

    }

    private static void printOrderOptions() {
        System.out.println("\u001B[32m1.Edit regular order\u001B[0m");
        System.out.println("\u001B[32m2.Create regular order\u001B[0m");
        System.out.println("\u001B[32m3.Create special order\u001B[0m");
        System.out.println("\u001B[32m4.Place waiting items\u001B[0m");
    }

    private static void editRegularItemOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert id of product:");
        int id = scanner.nextInt();
        //maybe present him the days?
        System.out.println("Insert the day of the week (big letters only):");
        String day = scanner.nextLine();
        DayOfWeek cur_day = DayOfWeek.valueOf(day);
        //maybe present him the item details from the order?
        System.out.println("Insert the new amount of product:");
        int amount = scanner.nextInt();
        orderService.editRegularOrder(id , cur_day , amount);
    }

    private static void moveToNextDay() {
        orderService.makeAutomaticalOrder();
    }

    private static void addCategory() {
        Scanner scanner = new Scanner(System.in);
        String index = presentCategories();
        System.out.println("Insert name of category:");
        String name = scanner.nextLine();
        categoryService.add_category(index,name);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\033[1mWelcome to Superly inventory\033[0m\n\u001B[32m" +
                "Would you like to load data or continue on an empty system?\n" +
                "1.Load data\n2.Empty system\u001B[0m");

        int action = scanner.nextInt();
        scanner.nextLine();
        if(action==1)
            inventoryService.setUp();
        while(true) {
            System.out.println("What would you like to do?");
            printOptions();
            String choise = scanner.nextLine();
            act(choise);
            System.out.println("\n");
        }
    }
}
