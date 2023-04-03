package ServiceLayer.Supplier;

import Util.Discounts;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class SupplierManager {
    private ServiceFactory serviceFactory;

    public SupplierManager() {
        this.serviceFactory = new ServiceFactory();
    }

    public void setUpData() {
        HashMap<String, String> contactsSupplier1 = new HashMap<>();
        contactsSupplier1.put("yossi", "052284621");
        serviceFactory.supplierService.addSupplier("Sapak1", "Shoham 43, Tel Aviv",
                5018475, 1199922, contactsSupplier1,
                List.of(new String[]{"sunday", "monday"}), true);

        HashMap<String, String> contactsSupplier2 = new HashMap<>();
        contactsSupplier2.put("menash", "18726312");
        serviceFactory.supplierService.addSupplier("Sapak2", "Golani 2, Ashkelon",
                4810203, 947182, contactsSupplier2, List.of(new String[]{"wednesday"}), true);


        serviceFactory.supplierService.addProduct(5018475, 982673, "Bamba",
                "Osem", 6, 500, LocalDateTime.now().plusMonths(1));

        serviceFactory.supplierService.addProduct(5018475, 1728439, "Click",
                "Elite", 8, 300, LocalDateTime.now().plusWeeks(2));

        serviceFactory.supplierService.addProduct(4810203, 9812763, "Ketchup",
                "Heinz", 10, 600, LocalDateTime.now().plusMonths(1));

        serviceFactory.supplierService.addProduct(4810203, 4918672, "Jasmin Rice",
                "Sogat", 9, 400, LocalDateTime.now().plusYears(1));

        serviceFactory.supplierService.addProductDiscount(5018475, 982673, 100, 5, true);
        serviceFactory.supplierService.addProductDiscount(5018475, 982673, 200, 100, false);

        serviceFactory.supplierService.addProductDiscount(5018475, 1728439, 50, 10, true);
        serviceFactory.supplierService.addProductDiscount(5018475, 1728439, 100, 50, false);

        serviceFactory.supplierService.addProductDiscount(4810203, 9812763, 20, 5, true);
        serviceFactory.supplierService.addProductDiscount(4810203, 9812763, 60, 50, false);

        serviceFactory.supplierService.addProductDiscount(4810203, 4918672, 50, 10, true);
        serviceFactory.supplierService.addProductDiscount(4810203, 4918672, 100, 50, false);
    }

    public void start() {
        setUpData();
        Scanner scanner = new Scanner(System.in);
        boolean over = false;
        while(!over) {
            System.out.println("--------------------------");
            System.out.println("Hello!\n" + "Please select your choice:");
            System.out.println("1.Add supplier.");
            System.out.println("2.Delete supplier.");
            System.out.println("3.Edit supplier.");
            System.out.println("4.Create order.");
            System.out.println("5.Add product to supplier.");
            System.out.println("6.Delete product of supplier.");
            System.out.println("7.Edit product of supplier.");
            System.out.println("8.Add discount to product.");
            System.out.println("9.Delete discount of product.");
            System.out.println("10.Edit discount of product.");
            System.out.println("11.Add discount to supplier.");
            System.out.println("12.Delete discount of supplier.");
            System.out.println("13.Edit discount of supplier.");
            System.out.println("14.Exit.");
            int choice = scanner.nextInt();
            while (choice > 13 || choice < 1) {
                System.out.println("Please select a number between 1-10.");
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 1:
                    addSupplier();
                case 2:
                    deleteSupplier();
                case 3:
                    editSupplier();
                case 4:
                    createOrder();
                case 5:
                    addProduct();
                case 6:
                    deleteProduct();
                case 7:
                    editProduct();
                case 8:
                    addProductDiscount();
                case 9:
                    deleteProductDiscount();
                case 10:
                    editProductDiscount();
                case 11:
                    addSupplierDiscount();
                case 12:
                    deleteSupplierDiscount();
                case 13:
                    editSupplierDiscount();
                case 14:
                    over = true;
            }
        }
    }

    private void addSupplier() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the supplier name:");
        String name = scanner.nextLine();
        System.out.println("Select the supplier address:");
        String address = scanner.nextLine();
        System.out.println("Select the supplier number:");
        int supplierNum = scanner.nextInt();
        System.out.println("Select the supplier bank account number:");
        int bankAccount = scanner.nextInt();
        HashMap<String, String> contacts = new HashMap<>();
        System.out.println("Select the primary contact number:");
        String contactNumber = scanner.nextLine();
        System.out.println("Select the primary contact name:");
        String contactName = scanner.nextLine();
        contacts.put(contactNumber, contactName);
        System.out.println("Do you want to add another contact?\n1.Yes.\n2.No.");
        int moreContactsChoice = scanner.nextInt();
        while (moreContactsChoice == 1) {
            System.out.println("Select the contact number:");
            contactNumber = scanner.nextLine();
            System.out.println("Select the contact name:");
            contactName = scanner.nextLine();
            contacts.put(contactNumber, contactName);
            System.out.println("Do you want to add another contact?\n1.Yes.\n2.No.");
            moreContactsChoice = scanner.nextInt();
        }
        System.out.println("Do the supplier have constant delivery days?\n1.Yes.\n2.No.");
        int constDeliveryDaysChoice = scanner.nextInt();
        List<String> constDeliveryDays = new ArrayList<>();
        if (constDeliveryDaysChoice == 1) {
            Map<Integer, String> days = new HashMap<Integer, String>() {{
                put(1, "Sunday");
                put(2, "Monday");
                put(3, "Tuesday");
                put(4, "Wednesday");
                put(5, "Thursday");
                put(6, "Friday");
                put(7, "Saturday");
            }};
            System.out.println("What is the first constant delivery day?\nType -1 in order to stop.\n1.Sunday.\n2.Monday.\n3.Tuesday.\n4.Wednesday.\n5.Thursday.\n6.Friday.\n7.Saturday.");
            int deliveryDayChoice = 0;
            while (deliveryDayChoice != -1) {
                deliveryDayChoice = scanner.nextInt();
                constDeliveryDays.add(days.get(deliveryDayChoice));
            }
        }
        System.out.println("Is the supplier deliver by himself?\n1.Yes.\n2.No.");
        int selfDeliveryChoice = scanner.nextInt();
        boolean selfDelivery = selfDeliveryChoice == 1;
        System.out.println(serviceFactory.supplierService.addSupplier(name, address, supplierNum, bankAccount, contacts, constDeliveryDays, selfDelivery));
    }

    private void deleteSupplier() {
        System.out.println("Please select the number of the supplier you want to delete.");
        Scanner scanner = new Scanner(System.in);
        int supplierNum = scanner.nextInt();
        System.out.println(serviceFactory.supplierService.deleteSupplier(supplierNum));
    }

    private void editSupplier() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the supplier number:");
        int supplierNum = scanner.nextInt();
        System.out.println("Select the new supplier name:");
        String name = scanner.nextLine();
        System.out.println("Select the new supplier address:");
        String address = scanner.nextLine();
        System.out.println("Select the new supplier bank account number:");
        int bankAccount = scanner.nextInt();
        System.out.println("Is the supplier deliver by himself?");
        System.out.println("1.Yes.");
        System.out.println("2.No.");
        int selfDeliveryChoice = scanner.nextInt();
        boolean selfDelivery = selfDeliveryChoice == 1;
        System.out.println(serviceFactory.supplierService.editSupplier(name, address, supplierNum, bankAccount, selfDelivery));
    }

    private void createOrder() {
        LinkedList<ItemToOrder> itemsList = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        boolean continueOrder = true;
        while (continueOrder) {
            System.out.println("Enter the name of product to order");
            String productName = scanner.nextLine();

            System.out.println("Enter the name of the product's manufacturer");
            String manufacturer = scanner.nextLine();

            System.out.println("Enter the product's quantity needed");
            int quantity = scanner.nextInt();

            itemsList.add(new ItemToOrder(productName, manufacturer, quantity));
            System.out.println("Do you want to order more products?\n1.Yes\n2.No");
            continueOrder = scanner.nextLine().equals("1");
        }
        System.out.println(serviceFactory.orderService.createOrder(itemsList));
    }

    private void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the supplier number:");
        int supplierNum = scanner.nextInt();
        System.out.println("Select the product number:");
        int productNum = scanner.nextInt();
        System.out.println("Select the product name:");
        String productName = scanner.nextLine();
        System.out.println("Select the product manufacturer:");
        String manufacturer = scanner.nextLine();
        System.out.println("Select the product price:");
        int price = scanner.nextInt();
        System.out.println("Select the product max amount:");
        int maxAmount = scanner.nextInt();
        System.out.println("Select the product expired date:\nFirst select the date in this format:'YYYY-MM-DD");
        String date = scanner.nextLine();
        System.out.println("Select the time(example to time format:'19:34:50.63'");
        String time = scanner.nextLine();
        LocalDateTime expiredDateTime = LocalDateTime.parse(date + 'T' + time);
        System.out.println(serviceFactory.supplierService.addProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount, expiredDateTime));
    }

    private void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the supplier number:");
        int supplierNum = scanner.nextInt();
        System.out.println("Select the new product name:");
        int productNum = scanner.nextInt();
        System.out.println(serviceFactory.supplierService.deleteProduct(supplierNum, productNum));
    }

    private void editProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the supplier number:");
        int supplierNum = scanner.nextInt();
        System.out.println("Select the new product name:");
        String productName = scanner.nextLine();
        System.out.println("Select the new product manufacturer:");
        String manufacturer = scanner.nextLine();
        System.out.println("Select the new product price:");
        int price = scanner.nextInt();
        System.out.println("Select the new product max amount:");
        int maxAmount = scanner.nextInt();
        System.out.println("Select the new product expired date:\nFirst select the date in this format:'YYYY-MM-DD");
        String date = scanner.nextLine();
        System.out.println("Select the new expired time(example to time format:'19:34:50.63'");
        String time = scanner.nextLine();
        LocalDateTime expiredDateTime = LocalDateTime.parse(date + 'T' + time);
        System.out.println(serviceFactory.supplierService.editProduct(supplierNum, productName, manufacturer, price, maxAmount, expiredDateTime));
    }

    private void addProductDiscount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add product discount\nEnter the number of supplier that supplies the product");
        int supplierNum = scanner.nextInt();
        System.out.println("Enter the number of product");
        int productNum = scanner.nextInt();
        System.out.println("Enter the amount of products to be discounted");
        int productAmount = scanner.nextInt();
        System.out.println("Enter the discount for that amount");
        int discount = scanner.nextInt();
        System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
        boolean isPercentage = scanner.nextLine().equals("1");
        System.out.println(serviceFactory.supplierService.addProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
    }

    private void deleteProductDiscount() {
        System.out.println("Delete product discount\nEnter the number of supplier that supplies the product");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of supplier that supplies the product");
        int supplierNum = scanner.nextInt();
        System.out.println("Enter the number of product");
        int productNum = scanner.nextInt();
        System.out.println("Enter the amount of products to be discounted");
        int productAmount = scanner.nextInt();
        System.out.println("Enter the discount for that amount");
        int discount = scanner.nextInt();
        System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
        boolean isPercentage = scanner.nextLine().equals("1");
        System.out.println(serviceFactory.supplierService.deleteProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
    }

    private void editProductDiscount() {
        System.out.println("Edit product discount\nEnter the number of supplier that supplies the product");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of supplier that supplies the product");
        int supplierNum = scanner.nextInt();
        System.out.println("Enter the number of product");
        int productNum = scanner.nextInt();
        System.out.println("Enter the amount of products to be discounted");
        int productAmount = scanner.nextInt();
        System.out.println("Enter the discount for that amount");
        int discount = scanner.nextInt();
        System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
        boolean isPercentage = scanner.nextLine().equals("1");
        System.out.println(serviceFactory.supplierService.editProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
    }

    private void addSupplierDiscount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add product discount\nEnter the number of supplier that supplies the product");
        int supplierNum = scanner.nextInt();
        System.out.println("Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount");
        int discountEnumNum = scanner.nextInt();
        Discounts discountEnum;
        if(discountEnumNum == 1)
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
        else
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
        System.out.println("Enter the amount of products/price to be discounted");
        int productAmount = scanner.nextInt();
        System.out.println("Enter the discount for that amount");
        int discount = scanner.nextInt();
        System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
        boolean isPercentage = scanner.nextLine().equals("1");
        System.out.println(serviceFactory.supplierService.addSupplierDiscount(supplierNum,discountEnum,productAmount,discount,isPercentage));
    }

    private void deleteSupplierDiscount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Delete product discount\nEnter the number of supplier that supplies the product");
        int supplierNum = scanner.nextInt();
        System.out.println("Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount");
        int discountEnumNum = scanner.nextInt();
        Discounts discountEnum;
        if(discountEnumNum == 1)
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
        else
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
        System.out.println("Enter the amount of products/price discount to be deleted");
        int productAmount = scanner.nextInt();
        System.out.println("Enter the discount for that amount");
        int discount = scanner.nextInt();
        System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
        boolean isPercentage = scanner.nextLine().equals("1");
        System.out.println(serviceFactory.supplierService.deleteSupplierDiscount(supplierNum,discountEnum,productAmount,discount,isPercentage));
    }

    private void editSupplierDiscount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Edit product discount\nEnter the number of supplier that supplies the product");
        int supplierNum = scanner.nextInt();
        System.out.println("Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount");
        int discountEnumNum = scanner.nextInt();
        Discounts discountEnum;
        if(discountEnumNum == 1)
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
        else
            discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
        System.out.println("Enter the amount of products/price to be discounted");
        int productAmount = scanner.nextInt();
        System.out.println("Enter the discount for that amount");
        int discount = scanner.nextInt();
        System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
        boolean isPercentage = scanner.nextLine().equals("1");
        System.out.println(serviceFactory.supplierService.editSupplierDiscount(supplierNum,discountEnum,productAmount,discount,isPercentage));
    }
}
