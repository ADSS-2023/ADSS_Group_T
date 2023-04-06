package ServiceLayer.Supplier;

import Util.Discounts;
import Util.PaymentTerms;

import java.time.LocalDateTime;
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
                List.of(new String[]{"sunday", "monday"}), true, PaymentTerms.SHOTEF_PLUS_30);

        HashMap<String, String> contactsSupplier2 = new HashMap<>();
        contactsSupplier2.put("menash", "18726312");
        serviceFactory.supplierService.addSupplier("Sapak2", "Golani 2, Ashkelon",
                4810203, 947182, contactsSupplier2, List.of(new String[]{"wednesday"}),
                true, PaymentTerms.SHOTEF_PLUS_30);


        serviceFactory.supplierService.addProduct(5018475, 982673, "Bamba",
                "Osem", 6, 500, LocalDateTime.now().plusMonths(1));

        serviceFactory.supplierService.addProduct(5018475, 1728439, "Click",
                "Elite", 8, 300, LocalDateTime.now().plusWeeks(2));

        serviceFactory.supplierService.addProduct(4810203, 9812763, "Ketchup",
                "Heinz", 10, 600, LocalDateTime.now().plusMonths(1));

        serviceFactory.supplierService.addProduct(4810203, 4918672, "Jasmin Rice",
                "Sogat", 9, 400, LocalDateTime.now().plusYears(1));

        serviceFactory.supplierService.addProduct(4810203, 675980, "Bamba",
                "Osem", 5, 200, LocalDateTime.now().plusMonths(1));

        serviceFactory.supplierService.addProductDiscount(5018475, 982673, 100, 5, true);
        serviceFactory.supplierService.addProductDiscount(5018475, 982673, 200, 100, false);

        serviceFactory.supplierService.addProductDiscount(5018475, 1728439, 50, 10, true);
        serviceFactory.supplierService.addProductDiscount(5018475, 1728439, 100, 50, false);

        serviceFactory.supplierService.addProductDiscount(4810203, 9812763, 20, 5, true);
        serviceFactory.supplierService.addProductDiscount(4810203, 9812763, 60, 50, false);

        serviceFactory.supplierService.addProductDiscount(4810203, 4918672, 50, 10, true);
        serviceFactory.supplierService.addProductDiscount(4810203, 4918672, 100, 50, false);

        serviceFactory.supplierService.addSupplierDiscount(5018475,Discounts.DISCOUNT_BY_TOTAL_QUANTITY,50,10,true);
        serviceFactory.supplierService.addSupplierDiscount(5018475,Discounts.DISCOUNT_BY_TOTAL_PRICE,500,50,false);

        serviceFactory.supplierService.addSupplierDiscount(4810203,Discounts.DISCOUNT_BY_TOTAL_QUANTITY,40,5,true);
        serviceFactory.supplierService.addSupplierDiscount(4810203,Discounts.DISCOUNT_BY_TOTAL_PRICE,300,40,false);

        ItemToOrder item1  = new ItemToOrder("Bamba","Osem",550);
        ItemToOrder item2  = new ItemToOrder("Ketchup","Heinz",50);
        ItemToOrder item3  = new ItemToOrder("Click","Elite",175);
        List<ItemToOrder> itemsList = new LinkedList<>();
        itemsList.add(item1);
        itemsList.add(item2);
        itemsList.add(item3);
        System.out.println(serviceFactory.orderService.createOrder(itemsList));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean over = false;
        while(!over) {
            System.out.println("--------------------------");
            System.out.println("Hello!\n" + "Please select your choice:");
            System.out.println("1.Add supplier.");
            System.out.println("2.Delete supplier.");
            System.out.println("3.Edit supplier details.");
            System.out.println("4.Create order.");
            System.out.println("5.Add product to supplier.");
            System.out.println("6.Delete product of supplier.");
            System.out.println("7.Edit product of supplier.");
            System.out.println("8.Add discount to product.");
            System.out.println("9.Delete discount of product.");
            System.out.println("10.Edit discount of product.");
            System.out.println("11.Add supplier's general discounts.");
            System.out.println("12.Delete supplier's general discounts.");
            System.out.println("13.Edit supplier's general discounts.");
            System.out.println("14.Show all orders.");
            System.out.println("15.Show all suppliers.");
            System.out.println("16.Show all products supplied by a certain Supplier.");
            System.out.println("17.Show all discounts of a certain product's supplier.");
            System.out.println("18.Show all general discounts of a certain supplier.");
            System.out.println("19.Set up System.");
            System.out.println("20.Exit System.");
            int choice = scanner.nextInt();
            while (choice > 20 || choice < 1) {
                System.out.println("Please select a number between 1-20.");
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 1:
                    addSupplier();
                    break;
                case 2:
                    deleteSupplier();
                    break;
                case 3:
                    editSupplier();
                    break;
                case 4:
                    createOrder();
                    break;
                case 5:
                    addProduct();
                    break;
                case 6:
                    deleteProduct();
                    break;
                case 7:
                    editProduct();
                    break;
                case 8:
                    addProductDiscount();
                    break;
                case 9:
                    deleteProductDiscount();
                    break;
                case 10:
                    editProductDiscount();
                    break;
                case 11:
                    addSupplierDiscount();
                    break;
                case 12:
                    deleteSupplierDiscount();
                    break;
                case 13:
                    editSupplierDiscount();
                    break;
                case 14:
                    getOrders();
                    break;
                case 15:
                    getSuppliers();
                    break;
                case 16:
                    getProducts();
                    break;
                case 17:
                    getProductDiscounts();
                    break;
                case 18:
                    getSupplierDiscount();
                    break;
                case 19:
                    setUpData();
                    break;
                case 20:
                    over = true;
                    break;
            }
        }
    }

    private void addSupplier() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        System.out.println("Select the supplier name:");
        String name = scannerString.nextLine();
        System.out.println("Select the supplier address:");
        String address = scannerString.nextLine();
        System.out.println("Select the supplier number:");
        int supplierNum = scannerInt.nextInt();
        System.out.println("Select the supplier bank account number:");
        int bankAccount = scannerInt.nextInt();
        HashMap<String, String> contacts = new HashMap<>();
        System.out.println("Select the primary contact number:");
        String contactNumber = scannerString.nextLine();
        System.out.println("Select the primary contact name:");
        String contactName = scannerString.nextLine();
        contacts.put(contactNumber, contactName);
        System.out.println("Do you want to add another contact?\n1.Yes.\n2.No.");
        int moreContactsChoice = scannerInt.nextInt();
        while (moreContactsChoice == 1) {
            System.out.println("Select the contact number:");
            contactNumber = scannerString.nextLine();
            System.out.println("Select the contact name:");
            contactName = scannerString.nextLine();
            contacts.put(contactNumber, contactName);
            System.out.println("Do you want to add another contact?\n1.Yes.\n2.No.");
            moreContactsChoice = scannerInt.nextInt();
        }
        System.out.println("Do the supplier have constant delivery days?\n1.Yes.\n2.No.");
        int constDeliveryDaysChoice = scannerInt.nextInt();
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
                deliveryDayChoice = scannerInt.nextInt();
                constDeliveryDays.add(days.get(deliveryDayChoice));
            }
        }
        System.out.println("Is the supplier deliver by himself?\n1.Yes.\n2.No.");
        int selfDeliveryChoice = scannerInt.nextInt();
        boolean selfDelivery = selfDeliveryChoice == 1;
        System.out.println("What is the payment terms of the supplier?");
        System.out.println("1.Shotef+30");
        System.out.println("2.Shotef+45");
        System.out.println("3.Shotef+60");
        System.out.println("4.Shotef+90");
        int paymentChoose = scannerInt.nextInt();
        PaymentTerms paymentTerms = null;
        switch (paymentChoose){
            case 1:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_30;
                break;
            case 2:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_45;
                break;
            case 3:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_60;
                break;
            case 4:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_90;
                break;
        }
        System.out.println(serviceFactory.supplierService.addSupplier(name, address, supplierNum, bankAccount, contacts, constDeliveryDays, selfDelivery,paymentTerms));
    }

    private void deleteSupplier() {
        getSuppliers();
        System.out.println("Please select the supplier number to be deleted.");
        Scanner scanner = new Scanner(System.in);
        int supplierNum = scanner.nextInt();
        System.out.println(serviceFactory.supplierService.deleteSupplier(supplierNum));
    }

    private void editSupplier() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        getSuppliers();
        System.out.println("Select the supplier number to be edited:");
        int supplierNum = scannerInt.nextInt();
        System.out.println("Select the new supplier name:");
        String name = scannerString.nextLine();
        System.out.println("Select the new supplier address:");
        String address = scannerString.nextLine();
        System.out.println("Select the new supplier bank account number:");
        int bankAccount = scannerInt.nextInt();
        System.out.println("Is the supplier deliver by himself?");
        System.out.println("1.Yes.");
        System.out.println("2.No.");
        int selfDeliveryChoice = scannerInt.nextInt();
        boolean selfDelivery = selfDeliveryChoice == 1;
        System.out.println("What is the payment terms of the supplier?");
        System.out.println("1.Shotef+30");
        System.out.println("2.Shotef+45");
        System.out.println("3.Shotef+60");
        System.out.println("4.Shotef+90");
        int paymentChoose = scannerInt.nextInt();
        PaymentTerms paymentTerms = null;
        switch (paymentChoose){
            case 1:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_30;
                break;
            case 2:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_45;
                break;
            case 3:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_60;
                break;
            case 4:
                paymentTerms = PaymentTerms.SHOTEF_PLUS_90;
                break;
        }
        System.out.println(serviceFactory.supplierService.editSupplier(name, address, supplierNum, bankAccount, selfDelivery,paymentTerms));
    }

    private void createOrder() {
        LinkedList<ItemToOrder> itemsList = new LinkedList<>();
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        boolean continueOrder = true;
        while (continueOrder) {
            System.out.println("All products can be supplied:");
            System.out.println(serviceFactory.supplierService.getAllProducts());

            System.out.println("Enter the name of product to order");
            String productName = scannerString.nextLine();

            System.out.println("Enter the name of the product's manufacturer");
            String manufacturer = scannerString.nextLine();

            System.out.println("Enter the product's quantity needed");
            int quantity = scannerInt.nextInt();

            itemsList.add(new ItemToOrder(productName, manufacturer, quantity));
            System.out.println("Do you want to order more products?\n1.Yes\n2.No");
            int decision = scannerInt.nextInt();
            if(decision==2)
                continueOrder=false;
        }
        System.out.println(serviceFactory.orderService.createOrder(itemsList));
    }

    private void addProduct() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        getSuppliers();
        System.out.println("Enter the supplier number that the propduct will be added to:");
        int supplierNum = scannerInt.nextInt();
        System.out.println("Enter the new product's number:");
        int productNum = scannerInt.nextInt();
        System.out.println("Enter the new product's name:");
        String productName = scannerString.nextLine();
        System.out.println("Enter the new product's manufacturer:");
        String manufacturer = scannerString.nextLine();
        System.out.println("Enter the new product's price:");
        int price = scannerInt.nextInt();
        System.out.println("Enter the product max amount in stock:");
        int maxAmount = scannerInt.nextInt();
        System.out.println("Enter the product expiry date:\nFirst select the date in this format:'YYYY-MM-DD");
        String date = scannerString.nextLine();
        System.out.println("Enter the time(example to time format:'19:34:50.63'");
        String time = scannerString.nextLine();
        LocalDateTime expiredDateTime = LocalDateTime.parse(date + 'T' + time);
        System.out.println(serviceFactory.supplierService.addProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount, expiredDateTime));
    }

    private void deleteProduct() {
        getSuppliers();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Enter the supplier number of the product that will be deleted:");
        int supplierNum = scanner.nextInt();
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);

            System.out.println("Please Enter the product number that will be deleted :");
            int productNum = scanner.nextInt();
            System.out.println(serviceFactory.supplierService.deleteProduct(supplierNum, productNum));
        }
        else System.out.println(products.get(0));

    }

    private void editProduct() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        getSuppliers();
        System.out.println("Enter the supplier number of the supplier that supplies the product :");
        int supplierNum = scannerInt.nextInt();
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);

            System.out.println("Enter the product name to be edited:");
            String productName = scannerString.nextLine();
            System.out.println("Enter the manufacturer name to be edited:");
            String manufacturer = scannerString.nextLine();
            System.out.println("Select the new product price:");
            int price = scannerInt.nextInt();
            System.out.println("Select the new product max quantity in stock:");
            int maxAmount = scannerInt.nextInt();
            System.out.println("Select the new product expiry date:\nFirst select the date in this format:'YYYY-MM-DD");
            String date = scannerString.nextLine();
            System.out.println("Select the new expiry time(example to time format:'19:34:50.63'");
            String time = scannerString.nextLine();
            LocalDateTime expiredDateTime = LocalDateTime.parse(date + 'T' + time);
            System.out.println(serviceFactory.supplierService.editProduct(supplierNum, productName, manufacturer, price, maxAmount, expiredDateTime));
        }
        else System.out.println(products.get(0));
    }

    private void addProductDiscount() {
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        System.out.println("Please Enter the number of supplier that supplies the product");
        int supplierNum = scanner.nextInt();
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);
            System.out.println("Enter the number of product");
            int productNum = scanner.nextInt();
            System.out.println("The products discounts are:");
            List<String> discounts=serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum);
            if(discounts.size()==0||(!discounts.get(0).contains("doesn't")&&!discounts.get(0).contains("exists")&&!discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
                System.out.println(discounts);
                System.out.println("Enter the amount of products to be discounted");
                int productAmount = scanner.nextInt();
                System.out.println("Enter the discount for that amount");
                int discount = scanner.nextInt();
                System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
                boolean isPercentage = scanner.nextInt() == 1;
                System.out.println(serviceFactory.supplierService.addProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
            }
            else System.out.println(discounts.get(0));
        }
        else System.out.println(products.get(0));
    }

    private void deleteProductDiscount() {
        System.out.println("Delete product discount\nEnter the number of supplier that supplies the product");
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = scanner.nextInt();
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");


        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);

            System.out.println("Enter the number of product");
            int productNum = scanner.nextInt();
            System.out.println("The products discounts are:");
            List<String> discounts=serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum);
            if(discounts.size()==0||(!discounts.get(0).contains("doesn't")&&!discounts.get(0).contains("exists")&&!discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
                System.out.println(discounts);

                System.out.println("Enter the amount of products of the discount to be deleted");
                int productAmount = scanner.nextInt();
                System.out.println("Enter the discount of that amount of products");
                int discount = scanner.nextInt();
                System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
                boolean isPercentage = scanner.nextInt() == 1;
                System.out.println(serviceFactory.supplierService.deleteProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
            }
            else System.out.println(discounts.get(0));
        }
        else System.out.println(products.get(0));
    }


    private void editProductDiscount() {
        System.out.println("Edit product discount\nEnter the number of supplier that supplies the product");
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = scanner.nextInt();
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");

        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);
            System.out.println("Enter the number of product that you are willing to edit one of it's discounts");
            int productNum = scanner.nextInt();
            System.out.println("The products discounts are:");
            List<String> discounts=serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum);
            if(discounts.size()==0||(!discounts.get(0).contains("doesn't")&&!discounts.get(0).contains("exists")&&!discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
                System.out.println(discounts);
                System.out.println("Enter the amount of products to be discounted");
                int productAmount = scanner.nextInt();
                System.out.println("Enter the new discount for that amount");
                int discount = scanner.nextInt();
                System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
                boolean isPercentage = scanner.nextInt() == 1;
                System.out.println(serviceFactory.supplierService.editProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
            }
            else System.out.println(discounts.get(0));
        }
        else System.out.println(products.get(0));
    }

    private void addSupplierDiscount() {
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        System.out.println("Please Enter the number of supplier that will have the discount");
        int supplierNum = scanner.nextInt();
        List<String> discounts = serviceFactory.supplierService.getSupplierDiscounts(supplierNum);
        if (discounts.size()==0||(!discounts.get(0).contains("doesn't") &&! discounts.get(0).contains("exists") &&! discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))){
            System.out.println(discounts);
            System.out.println("Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount");
            int discountEnumNum = scanner.nextInt();
            Discounts discountEnum;
            if (discountEnumNum == 1)
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
            else
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
            System.out.println("Enter the amount of products/price to be discounted");
            int productAmount = scanner.nextInt();
            System.out.println("Enter the discount for that amount");
            int discount = scanner.nextInt();
            System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
            boolean isPercentage = scanner.nextInt() == 1;
            System.out.println(serviceFactory.supplierService.addSupplierDiscount(supplierNum, discountEnum, productAmount, discount, isPercentage));
        }
        else System.out.println(discounts.get(0));
    }

    private void deleteSupplierDiscount(){
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        System.out.println("Please Enter the number of supplier");
        int supplierNum = scanner.nextInt();


        List<String> discounts = serviceFactory.supplierService.getSupplierDiscounts(supplierNum);
        if (discounts.size()==0||(!discounts.get(0).contains("doesn't") &&! discounts.get(0).contains("exists") &&! discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
            System.out.println(discounts);

            System.out.println("Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount");
            int discountEnumNum = scanner.nextInt();
            Discounts discountEnum;
            if (discountEnumNum == 1)
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
            else
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
            System.out.println("Enter the amount of products/price discount to be deleted");
            int productAmount = scanner.nextInt();
            System.out.println("Enter the discount for that amount");
            int discount = scanner.nextInt();
            System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
            boolean isPercentage = scanner.nextInt() == 1;
            System.out.println(serviceFactory.supplierService.deleteSupplierDiscount(supplierNum, discountEnum, productAmount, discount, isPercentage));
        }
        else System.out.println(discounts.get(0));
    }

    private void editSupplierDiscount(){
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        System.out.println("Please Enter the number of supplier ");
        int supplierNum = scanner.nextInt();
        System.out.println("The supplier discounts are:");

        List<String> discounts = serviceFactory.supplierService.getSupplierDiscounts(supplierNum);
        if (discounts.size()==0||(!discounts.get(0).contains("doesn't") &&! discounts.get(0).contains("exists") &&! discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
            System.out.println(discounts);

            System.out.println("Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount");
            int discountEnumNum = scanner.nextInt();
            Discounts discountEnum;
            if (discountEnumNum == 1)
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
            else
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
            System.out.println("Enter the amount of products/price to be discounted");
            int productAmount = scanner.nextInt();
            System.out.println("Enter the new discount for that amount");
            int discount = scanner.nextInt();
            System.out.println("Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels");
            boolean isPercentage = scanner.nextInt() == 1;
            System.out.println(serviceFactory.supplierService.editSupplierDiscount(supplierNum, discountEnum, productAmount, discount, isPercentage));
        }
        else System.out.println(discounts.get(0));
    }

    private void getOrders(){
        System.out.println("Orders saved in the system:\n");
        System.out.println(serviceFactory.orderService.getOrders());
    }

    private void getSuppliers(){
        System.out.println("Suppliers saved in the system:\n");
        System.out.println(serviceFactory.supplierService.getSuppliers());
    }

    private void getProducts(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter supplier number from below:\n ");
        getSuppliers();
        int supplierNum = scan.nextInt();
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        System.out.println(serviceFactory.supplierService.getProducts(supplierNum));
    }

    private void getProductDiscounts(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter supplier number from below:\n ");
        getSuppliers();
        int supplierNum = scan.nextInt();
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");


        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);
            System.out.println("Please enter Product number to get its discounts ");
            int productNum = scan.nextInt();
            System.out.println(serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum));
        }
        else System.out.println(products.get(0));
    }

    private void getSupplierDiscount(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter supplier number from below:\n ");
        getSuppliers();
        int supplierNum = scan.nextInt();
        System.out.println(serviceFactory.supplierService.getSupplierDiscounts(supplierNum));
    }
}
