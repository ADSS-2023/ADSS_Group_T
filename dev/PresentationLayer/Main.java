package PresentationLayer;
import ServiceLayer.Stock.CategoryService;
import ServiceLayer.Stock.DamagedService;
import ServiceLayer.Stock.InventoryService;
import java.util.List;

import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static InventoryService inventoryService = new InventoryService();
    public static CategoryService categoryService = new CategoryService(inventoryService.get_inventory());
    public static DamagedService damagedService = new DamagedService();


    public static void printOptions(){
        System.out.println("1.See categories");
        System.out.println("2.Produce inventory report");
        System.out.println("3.Set discount");
        System.out.println("4.Report damaged item");
    }
    public static String presentCategories(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(inventoryService.show_data());
        boolean is_active = true;
        String next_index="";

        while (is_active){
            System.out.println("choose category");
            String choise = scanner.nextLine();
            if (choise == "-1"){
                is_active = false;
                next_index = "exit";
            }
            else if(choise =="0")
                is_active = false;
            else {
                next_index += "." +choise;
                System.out.println(categoryService.show_data(next_index));
            }
        }
        return next_index;

    }

    private static void inventoryReport() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> categories = new LinkedList<>();
        System.out.println("to exit press -1,\n and if you want to produce the report on the current category please press 0");
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
        String end_date_string = scanner.nextLine();
        System.out.println("Choose end date :");
        String start_date_string = scanner.nextLine();
        System.out.println("Choose percentage amount :");
        double percentageAmount = scanner.nextDouble();
        inventoryService.set_discount(product , percentageAmount , end_date_string , start_date_string);
    }

    public static void act(String choise){
        switch (choise) {
            case "1":
                System.out.println("in order to exit press -1 in any time");
                presentCategories();
            case "2":
                inventoryReport();
            case "3":
                setDiscount();
            case "4":
                damagedItem();
        }
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
        String description = scanner.nextLine();
        damagedService.report_damaged_item(item_id,order_id,amount,description);
    }


    public static void main(String[] args) {
        inventoryService.setUp();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Superly inventory.\nWhat would you like to do?");
        printOptions();
        String choise = scanner.nextLine();
        act(choise);
    }
}
