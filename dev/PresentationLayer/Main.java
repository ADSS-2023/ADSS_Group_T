package PresentationLayer;
import ServiceLayer.Stock.CategoryService;

import java.util.Scanner;

public class Main {
    public static CategoryService categoryService;
    public static void printOptions(){
        System.out.println("1.See categories");
        System.out.println("2.Produce inventory report");
        System.out.println("3.Set discount");
        System.out.println("4.Report damaged item");
    }
    public static void presentCategories(){
        categoryService.show_data();
    }
    private static void inventoryReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many categories would you like?");
        int number_of_categories = scanner.nextInt();
        for(int i=0;i<number_of_categories;i++){

        }
    }
    public static void act(String choise){
        switch (choise) {
            case "1":
                presentCategories();
            case "2":
                inventoryReport();
            case "3":

            case "4":
        }
    }



    public static void main(String[] args) {
        categoryService = new CategoryService();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Superly inventory.\nWhat would you like to do?");
        printOptions();
        String choise = scanner.nextLine();
        act(choise);
    }

}
