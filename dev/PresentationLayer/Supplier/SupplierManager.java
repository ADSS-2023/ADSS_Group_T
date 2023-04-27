package PresentationLayer.Supplier;

import BusinessLayer.Supplier_Stock.ItemToOrder;
import PresentationLayer.Supplier_Stock.PreviousCallBack;
import ServiceLayer.Supplier_Stock.ServiceFactory;
import BusinessLayer.Supplier.Util.Discounts;
import BusinessLayer.Supplier.Util.PaymentTerms;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.*;

public class SupplierManager {
    private ServiceFactory serviceFactory;
    private PreviousCallBack previousCallBack;

    public SupplierManager() {
        this.serviceFactory = new ServiceFactory();
    }

    public void setUpData() {
        HashMap<String, String> contactsSupplier1 = new HashMap<>();
        contactsSupplier1.put("yossi", "052284621");
        serviceFactory.supplierService.addSupplier("Sapak1", "Shoham 43, Tel Aviv",
                5018475, 1199922,-1, contactsSupplier1,
                List.of(new DayOfWeek[]{DayOfWeek.SUNDAY,DayOfWeek.MONDAY}), true, PaymentTerms.SHOTEF_PLUS_30);

        HashMap<String, String> contactsSupplier2 = new HashMap<>();
        contactsSupplier2.put("menash", "18726312");
        serviceFactory.supplierService.addSupplier("Sapak2", "Golani 2, Ashkelon",
                4810203, 947182,-1, contactsSupplier2, List.of(new DayOfWeek[]{DayOfWeek.WEDNESDAY}),
                true, PaymentTerms.SHOTEF_PLUS_30);

        HashMap<String, String> contactsSupplier3 = new HashMap<>();
        contactsSupplier3.put("Yagil", "052275937");
        serviceFactory.supplierService.addSupplier("Sapak3", "Rabin 95, Tel Aviv",
                4810203, 947182,10, contactsSupplier3, null,
                true, PaymentTerms.SHOTEF_PLUS_30);


        serviceFactory.supplierService.addProduct(5018475, 982673, "Bamba",
                "Osem", 6, 500, LocalDate.now().plusMonths(1));

        serviceFactory.supplierService.addProduct(5018475, 1728439, "Click",
                "Elite", 8, 300, LocalDate.now().plusWeeks(2));

        serviceFactory.supplierService.addProduct(4810203, 9812763, "Ketchup",
                "Heinz", 10, 600, LocalDate.now().plusMonths(1));

        serviceFactory.supplierService.addProduct(4810203, 4918672, "Jasmin Rice",
                "Sogat", 9, 400, LocalDate.now().plusYears(1));

        serviceFactory.supplierService.addProduct(4810203, 675980, "Bamba",
                "Osem", 5, 200, LocalDate.now().plusMonths(1));

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

        ItemToOrder item1  = new ItemToOrder("Bamba","Osem",550,LocalDate.now().plusMonths(1),-1,-1);
        ItemToOrder item2  = new ItemToOrder("Ketchup","Heinz",50,LocalDate.now().plusMonths(1),-1,-1);
        ItemToOrder item3  = new ItemToOrder("Click","Elite",175,LocalDate.now().plusMonths(1),-1,-1);
        List<ItemToOrder> itemsList = new LinkedList<>();
        itemsList.add(item1);
        itemsList.add(item2);
        itemsList.add(item3);
        System.out.println(serviceFactory.orderService.createSpecialOrder(itemsList,false));
    }

    public void start() throws Exception {
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
            System.out.println("19.Go to next day.");
            System.out.println("20.Go back to main menu.");
            System.out.println("21.Set up System.");
            System.out.println("22.Exit System.");
            int choice = getInteger(scanner, "Please select an integer between 1 to 20.", 1, 20);
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
                    nextDay();
                    break;
                case 20:
                    goBack();
                    break;
                case 21:
                    setUpData();
                    break;
                case 22:
                    over = true;
                    break;
            }
        }
    }

    private void nextDay() {
        //TODO::
        //the system time has proceeded.
        //execute function execute orders
    }

    private void addSupplier() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        String name = getString(scannerString, "Select the supplier name:");
        String address = getString(scannerString, "Select the supplier address:");
        int supplierNum = getInteger(scannerInt,"Select the supplier number:", Integer.MIN_VALUE, Integer.MAX_VALUE);
        int bankAccount = getInteger(scannerInt,"Select the supplier bank account number:", Integer.MIN_VALUE, Integer.MAX_VALUE);
        HashMap<String, String> contacts = new HashMap<>();
        String contactNumber = getString(scannerString, "Select the primary contact number:");
        while(!Pattern.matches("\\d+", contactNumber))
            contactNumber = getString(scannerString, "Select the primary contact number:");
        String contactName = getString(scannerString, "Select the primary contact name:");
        contacts.put(contactNumber, contactName);
        int moreContactsChoice = getInteger(scannerInt, "Do you want to add another contact?\n1.Yes.\n2.No.", 1, 2);
        while (moreContactsChoice == 1) {
            contactNumber = getString(scannerString, "Select the contact number:");
            while(!Pattern.matches("\\d+", contactNumber))
                contactNumber = getString(scannerString, "Select the contact number:");
            contactName = getString(scannerString, "Select the contact name:");
            contacts.put(contactNumber, contactName);
            moreContactsChoice =  getInteger(scannerInt, "Do you want to add another contact?\n1.Yes.\n2.No.", 1, 2);;
        }
        int constDeliveryDaysChoice =  getInteger(scannerInt, "Do the supplier have constant delivery days?\n1.Yes.\n2.No.", 1, 2);;
        List<String> constDeliveryDays = new ArrayList<>();
        if (constDeliveryDaysChoice == 1) { //TODO::change to list of Dayofweek
            Map<Integer, String> days = new HashMap<Integer, String>() {{
                put(1, "Sunday");
                put(2, "Monday");
                put(3, "Tuesday");
                put(4, "Wednesday");
                put(5, "Thursday");
                put(6, "Friday");
                put(7, "Saturday");
            }};
            System.out.println("What is the first constant delivery day?");
            int deliveryDayChoice = 0;
            while (true) {
                deliveryDayChoice = getInteger(scannerInt,"Type -1 in order to stop.\n1.Sunday.\n2.Monday.\n3.Tuesday.\n4.Wednesday.\n5.Thursday.\n6.Friday.\n7.Saturday.\n", -1,7);
                if(deliveryDayChoice == -1)
                    break;
                if(deliveryDayChoice == 0)
                    continue;
                constDeliveryDays.add(days.get(deliveryDayChoice));
            }
        }
        int selfDeliveryChoice = getInteger(scannerInt, "Is the supplier deliver by himself?\n1.Yes.\n2.No.", 1, 2);
        boolean selfDelivery = selfDeliveryChoice == 1;
        int paymentChoose = getInteger(scannerInt, "What is the payment terms of the supplier?\n1.Shotef+30\n2.Shotef+45\n3.Shotef+60\n4.Shotef+90\n",1,4);
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
        Scanner scanner = new Scanner(System.in);
        int supplierNum = getInteger(scanner, "Please select the supplier number to be deleted.",0, Integer.MAX_VALUE);
        System.out.println(serviceFactory.supplierService.deleteSupplier(supplierNum));
    }

    private void editSupplier() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scannerInt, "Select the supplier number to be edited:", 0, Integer.MAX_VALUE);
        String name = getString(scannerString, "Select the new supplier name:");
        String address = getString(scannerString, "Select the new supplier address:");
        int bankAccount = getInteger(scannerInt, "Select the new supplier bank account number:", 0, Integer.MAX_VALUE);
        int selfDeliveryChoice = getInteger(scannerInt, "Is the supplier deliver by himself?\n1.Yes.\n2.No.", 1, 2);
        boolean selfDelivery = selfDeliveryChoice == 1;
        int paymentChoose = getInteger(scannerInt, "What is the payment terms of the supplier?\n1.Shotef+30\n2.Shotef+45\n3.Shotef+60\n4.Shotef+90\n",1,4);
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

            String productName = getString(scannerString, "Enter the name of product to order");

            String manufacturer = getString(scannerString, "Enter the name of the product's manufacturer");

            int quantity = getInteger(scannerInt, "Enter the product's quantity needed", 0, Integer.MAX_VALUE);

           // itemsList.add(new ItemToOrder(productName, manufacturer, quantity));
            int decision = getInteger(scannerInt, "Do you want to order more products?\n1.Yes\n2.No", 1, 2);
            if(decision==2)
                continueOrder=false;
        }
       // System.out.println(serviceFactory.orderService.createOrder(itemsList));
    }

    private void addProduct() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scannerInt, "Enter the supplier number that the propduct will be added to:", 0, Integer.MAX_VALUE);
        int productNum = getInteger(scannerInt, "Enter the new product's number:", 0, Integer.MAX_VALUE);
        String productName = getString(scannerString, "Enter the new product's name:");
        String manufacturer = getString(scannerString, "Enter the new product's manufacturer:");
        int price = getInteger(scannerInt, "Enter the new product's price:", 0, Integer.MAX_VALUE);
        int maxAmount = getInteger(scannerInt, "Enter the product max amount in stock:", 0, Integer.MAX_VALUE);;
        String date = getString(scannerString, "Enter the product expiry date:\nFirst select the date in this format:'YYYY-MM-DD");
        String time = getString(scannerString, "Enter the time(example to time format:'19:34:50.63'");
        LocalDateTime expiredDateTime = LocalDateTime.parse(date + 'T' + time);
        System.out.println(serviceFactory.supplierService.addProduct(supplierNum, productNum, productName, manufacturer, price, maxAmount, expiredDateTime));
    }

    private void deleteProduct() {
        getSuppliers();
        Scanner scanner = new Scanner(System.in);
        int supplierNum = getInteger(scanner, "Please Enter the supplier number of the product that will be deleted:", 0, Integer.MAX_VALUE);
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);
            int productNum = getInteger(scanner, "Please Enter the product number that will be deleted :", 0, Integer.MAX_VALUE);
            System.out.println(serviceFactory.supplierService.deleteProduct(supplierNum, productNum));
        }
        else System.out.println(products.get(0));

    }

    private void editProduct() {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        getSuppliers();
        System.out.println();
        int supplierNum = getInteger(scannerInt, "Enter the supplier number of the supplier that supplies the product :", 0, Integer.MAX_VALUE);
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);

            String productName = getString(scannerString, "Enter the product name to be edited:");
            String manufacturer = getString(scannerString, "Enter the manufacturer name to be edited:");
            int price = getInteger(scannerInt,"Select the new product price:", 0, Integer.MAX_VALUE);
            int maxAmount = getInteger(scannerInt, "Select the new product max quantity in stock:", 0, Integer.MAX_VALUE);
            String date = getString(scannerString, "Select the new product expiry date:\nFirst select the date in this format:'YYYY-MM-DD");
            LocalDate expiredDate = LocalDate.parse(date);//maybe to change
            System.out.println(serviceFactory.supplierService.editProduct(supplierNum, productName, manufacturer, price, maxAmount, expiredDate));
        }
        else System.out.println(products.get(0));
    }

    private void addProductDiscount() {
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scanner, "Please Enter the number of supplier that supplies the product", 0, Integer.MAX_VALUE);
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);
            int productNum = getInteger(scanner, "Enter the number of product", 0, Integer.MAX_VALUE);
            System.out.println("The products discounts are:");
            List<String> discounts=serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum);
            if(discounts.size()==0||(!discounts.get(0).contains("doesn't")&&!discounts.get(0).contains("exists")&&!discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
                System.out.println(discounts);
                int productAmount = getInteger(scanner, "Enter the amount of products to be discounted", 0, Integer.MAX_VALUE);
                int discount = getInteger(scanner, "Enter the discount for that amount", 0, Integer.MAX_VALUE);
                boolean isPercentage = getInteger(scanner, "Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels", 1, 2) == 1;
                System.out.println(serviceFactory.supplierService.addProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
            }
            else System.out.println(discounts.get(0));
        }
        else System.out.println(products.get(0));
    }

    private void deleteProductDiscount() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scanner, "Delete product discount\nEnter the number of supplier that supplies the product", 0, Integer.MAX_VALUE);
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");


        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);

            int productNum = getInteger(scanner, "Enter the number of product", 0, Integer.MAX_VALUE);
            System.out.println("The products discounts are:");
            List<String> discounts=serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum);
            if(discounts.size()==0||(!discounts.get(0).contains("doesn't")&&!discounts.get(0).contains("exists")&&!discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
                System.out.println(discounts);

                int productAmount = getInteger(scanner, "Enter the amount of products of the discount to be deleted", 0, Integer.MAX_VALUE);
                int discount = getInteger(scanner, "Enter the discount of that amount of products", 0, Integer.MAX_VALUE);
                boolean isPercentage = getInteger(scanner, "Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels", 1, 2) == 1;
                System.out.println(serviceFactory.supplierService.deleteProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
            }
            else System.out.println(discounts.get(0));
        }
        else System.out.println(products.get(0));
    }


    private void editProductDiscount() {
        System.out.println("Edit product discount.");
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scanner, "Enter the number of supplier that supplies the product", 0, Integer.MAX_VALUE);
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");

        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);
            int productNum = getInteger(scanner, "Enter the number of product that you are willing to edit one of it's discounts", 0, Integer.MAX_VALUE);
            System.out.println("The products discounts are:");
            List<String> discounts=serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum);
            if(discounts.size()==0||(!discounts.get(0).contains("doesn't")&&!discounts.get(0).contains("exists")&&!discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
                System.out.println(discounts);
                int productAmount = getInteger(scanner, "Enter the amount of products to be discounted", 0, Integer.MAX_VALUE);
                System.out.println();
                int discount = getInteger(scanner, "Enter the new discount for that amount", 0, Integer.MAX_VALUE);
                boolean isPercentage = getInteger(scanner, "Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels", 1, 2) == 1;
                System.out.println(serviceFactory.supplierService.editProductDiscount(supplierNum, productNum, productAmount, discount, isPercentage));
            }
            else System.out.println(discounts.get(0));
        }
        else System.out.println(products.get(0));
    }

    private void addSupplierDiscount() {
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scanner, "Please Enter the number of supplier that will have the discount", 0, Integer.MAX_VALUE);
        List<String> discounts = serviceFactory.supplierService.getSupplierDiscounts(supplierNum);
        if (discounts.size()==0||(!discounts.get(0).contains("doesn't") &&! discounts.get(0).contains("exists") &&! discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))){
            System.out.println(discounts);
            int discountEnumNum = getInteger(scanner, "Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount", 1, 2);
            Discounts discountEnum;
            if (discountEnumNum == 1)
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
            else
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
            int productAmount = getInteger(scanner, "Enter the amount of products/price to be discounted", 0, Integer.MAX_VALUE);
            int discount = getInteger(scanner, "Enter the discount for that amount", 0, Integer.MAX_VALUE);
            boolean isPercentage = getInteger(scanner, "Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels", 1, 2) == 1;
            System.out.println(serviceFactory.supplierService.addSupplierDiscount(supplierNum, discountEnum, productAmount, discount, isPercentage));
        }
        else System.out.println(discounts.get(0));
    }

    private void deleteSupplierDiscount(){
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scanner, "Please Enter the number of supplier", 0, Integer.MAX_VALUE);


        List<String> discounts = serviceFactory.supplierService.getSupplierDiscounts(supplierNum);
        if (discounts.size()==0||(!discounts.get(0).contains("doesn't") &&! discounts.get(0).contains("exists") &&! discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
            System.out.println(discounts);

            int discountEnumNum = getInteger(scanner, "Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount", 1, 2);
            Discounts discountEnum;
            if (discountEnumNum == 1)
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
            else
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
            int productAmount = getInteger(scanner, "Enter the amount of products/price discount to be deleted", 0, Integer.MAX_VALUE);
            int discount = getInteger(scanner, "Enter the discount for that amount", 0, Integer.MAX_VALUE);
            boolean isPercentage = getInteger(scanner, "Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels", 1, 2) == 1;
            System.out.println(serviceFactory.supplierService.deleteSupplierDiscount(supplierNum, discountEnum, productAmount, isPercentage));
        }
        else System.out.println(discounts.get(0));
    }

    private void editSupplierDiscount(){
        Scanner scanner = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scanner, "Please Enter the number of supplier ", 0, Integer.MAX_VALUE);
        System.out.println("The supplier discounts are:");

        List<String> discounts = serviceFactory.supplierService.getSupplierDiscounts(supplierNum);
        if (discounts.size()==0||(!discounts.get(0).contains("doesn't") &&! discounts.get(0).contains("exists") &&! discounts.get(0).contains("failed")&&!discounts.get(0).contains("Cannot"))) {
            System.out.println(discounts);

            int discountEnumNum = getInteger(scanner, "Enter the type of the discount:\n1.Total products purchased.\n2.Total Price discount", 1, 2);
            Discounts discountEnum;
            if (discountEnumNum == 1)
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_QUANTITY;
            else
                discountEnum = Discounts.DISCOUNT_BY_TOTAL_PRICE;
            int productAmount = getInteger(scanner, "Enter the amount of products/price to be discounted", 0, Integer.MAX_VALUE);
            int discount = getInteger(scanner, "Enter the new discount for that amount", 0, Integer.MAX_VALUE);
            boolean isPercentage = getInteger(scanner, "Is the discount is by percentage or by shekels?\n1.Percetage\n2.Shekels", 1, 2) == 1;
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
        getSuppliers();
        int supplierNum = getInteger(scan, "Please enter supplier number from below:\n ", 0, Integer.MAX_VALUE);
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");
        System.out.println(serviceFactory.supplierService.getProducts(supplierNum));
    }

    private void getProductDiscounts(){
        Scanner scan = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scan, "Please enter supplier number from below:\n ", 0, Integer.MAX_VALUE);
        System.out.println("The products that supplier "+supplierNum+" supplies are:\n");


        List<String> products =  serviceFactory.supplierService.getProducts(supplierNum);
        if(products.size()==0||(!products.get(0).contains("doesn't")&&!products.get(0).contains("exists")&&!products.get(0).contains("failed")&&!products.get(0).contains("Cannot"))) {
            System.out.println(products);
            int productNum = getInteger(scan, "Please enter Product number to get its discounts ", 0, Integer.MAX_VALUE);
            System.out.println(serviceFactory.supplierService.getProductDiscounts(supplierNum, productNum));
        }
        else System.out.println(products.get(0));
    }

    private void getSupplierDiscount(){
        Scanner scan = new Scanner(System.in);
        getSuppliers();
        int supplierNum = getInteger(scan, "Please enter supplier number from below:\n ", 0, Integer.MAX_VALUE);
        System.out.println(serviceFactory.supplierService.getSupplierDiscounts(supplierNum));
    }

    public int getInteger(Scanner scanner, String message, int min, int max){
        int choice;
        while(true){
            System.out.println(message);
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if(choice >= min && choice <= max)
                    break;
            } else {

                scanner.next();
            }
        }
        return choice;
    }

    public String getString(Scanner scanner, String message){
        String input;
        while (true) {
            System.out.print(message);
            input = scanner.nextLine().trim();
            if (!input.equals("")) {
                break;
            }
        }
        return input;
    }

    public void setPreviousCallBack(PreviousCallBack previousCallBack) {
        this.previousCallBack = previousCallBack;
    }
    public void goBack(){
        previousCallBack.goBack();
    }
}
