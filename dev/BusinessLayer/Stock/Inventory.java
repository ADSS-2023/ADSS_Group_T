package BusinessLayer.Stock;

import BusinessLayer.Stock.Util.Util;
import BusinessLayer.Supplier_Stock.ItemToOrder;
import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.*;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Inventory_Supplier_Dal.DalController.ItemDalController;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/*
    This class represents the inventory of the store. It includes all items
    ordered in two data structures, categories trees and a hash table.
    This class also keep on count the shortage items.
 */
public class Inventory {
    protected List<Category> categories;
    protected HashMap<Integer,Item> items;
    protected HashMap<String,Integer> name_to_id;
    protected List<Item> shortage_list;
    protected Damaged damaged;
    protected InventoryDalController inv_dal_controller;
    protected ItemDalController itemDalController;
    private int discount_counter;

    public Inventory(){
        categories = new LinkedList<>();
        items = new HashMap<>();
        shortage_list = new LinkedList<>();
        damaged = new Damaged(null);
        name_to_id = new HashMap<>();
        discount_counter = 0;
    }

    public void setInventoryDalController(InventoryDalController inv){
        this.inv_dal_controller = inv;
        damaged.inventoryDalController = inv;
    }

    public void setItemDalController(ItemDalController itemDalController){
        this.itemDalController = itemDalController;
    }
    public InventoryDalController getInv_dal_controller(){
        return inv_dal_controller;
    }
    /**
     * this function gets an item and set his alert callback
     * to be added to shortage list, when needed.
     * @param item
     */
    public void set_item_call_back(Item item){
        item.set_on_alert_callback(()->{if(!shortage_list.contains(item)){shortage_list.add(item);}});
    }

    /**
     * returns a report of all the item in stock that have reached their minimum amount.
     * @return
     */
    public String produce_shortage_list(){
        String report="";
        for (Item item:shortage_list
             ) {
            report += String.format("%s, %s\n minimal amount: %d, current amount: %d, amount to order: %d",
                    item.name,item.manufacturer_name,item.min_amount,item.current_amount(), item.min_amount-item.current_amount());
                    report+="\n-------------------------------------------\n";
        }
        if (report.isEmpty()) return "no shortage";
        return report;
    }

    /**
     * This function adds a damaged item to the list of the damaged items.
     * @param item_id
     * @param order_id
     * @param amount
     * @param description
     * @return
     * @throws Exception
     */
    public String addDamagedItem(int item_id,int order_id,int amount,String description) throws Exception {
        if (!items.containsKey(item_id))
            throw new Exception("Illegal index");
        Item i = items.get(item_id);
        ItemPerOrder ipo = i.getItemPerOrder(order_id);
        return damaged.addDamagedItem(items.get(item_id),order_id,amount,
                description+"\narrived in: "+ipo.getArrived_date().toString());
    }

    /**
     * This function present the names of this specific category
     * @return
     */
    public String present_names(){
        if (categories.isEmpty())
            return "No categories";
        String names = "";
        int index = 1;
        for(Category cur_category : categories){
            names += index++ + " : " + cur_category.get_name() + ", ";
        }
        return names.substring(0,names.length()-2);
    }
    /**
     * This function show the details on "index" sub-category
     * @param index
     * @return
     * @throws Exception
     */

    public String show_data(String index) throws Exception {
        if(index == "")
            return present_names();
        else {
            String cur = Util.extractFirstNumber(index);
            if(cur == ""){
                throw new Exception("illegal choise of index");
            }
            int current_index = Integer.parseInt(cur);
            String next_index = Util.extractNextIndex(index);
            if (categories.size() <= current_index || current_index < 0)
                throw new Exception("Illegal index has been chosen");
            return categories.get(current_index).show_data(next_index);
        }

    }

    /**
     * Set discount to a specific category or specific item
     * @param index the index of the category/item in the categories tree
     * @param percentageAmount
     * @param end_date_string
     * @param start_date_string
     */
    public void set_discount(String index , double percentageAmount , String end_date_string , String start_date_string) throws Exception {
        int current_index = Integer.parseInt(Util.extractFirstNumber(index));
        String next_index = Util.extractNextIndex(index);
        LocalDate end_date = Util.stringToDate(end_date_string);
        LocalDate start_date = Util.stringToDate(start_date_string);
        if (categories.size()<= current_index)
            throw new Exception("Illegal index");
        Discount new_discount = new Discount(discount_counter , start_date , end_date , percentageAmount , index);
        categories.get(current_index).setDiscount(next_index , new_discount);
        inv_dal_controller.insert(new_discount.getDto());
        inv_dal_controller.update(new DiscountCounterDTO(discount_counter),new DiscountCounterDTO(++discount_counter));
    }

    public void set_discount(DiscountDTO discount) throws Exception {
        int current_index = Integer.parseInt(Util.extractFirstNumber(discount.getIndex_product()));
        String next_index = Util.extractNextIndex(discount.getIndex_product());
        Discount new_discount = new Discount(discount);
        categories.get(current_index).setDiscount(next_index , new_discount);
    }
    /**
     * This function receives a list of indexes that represent categories,
     * and produce an inventory report for all of those categories.
     * @param categories_indexes
     * @return
     */
    public String produce_inventory_report(LinkedList<String> categories_indexes) throws Exception {

        if(Util.no_categories(categories_indexes))
            throw new Exception("No categories have been chosen");
        String report = "inventory report:\n" ;
        for (String index:categories_indexes
             ) {
            int current_index = Integer.parseInt(Util.extractFirstNumber(index));
            String next_index = Util.extractNextIndex(index);
            report +="\n"+ categories.get(current_index).produceInventoryReport(next_index);
        }
        return report;
    }

    /**
     * This function is a setup function with specific items and categories
     * in order to test the system.
     */
    public void setUp() throws Exception {
        this.add_category("","Milk-product");
        this.add_category(".0","Cheese");
        this.add_category(".0","bottle milk");
        this.add_category(".0","chocolate");
        this.add_category("" , "Meat-product");
        this.add_category(".1" , "beef");
        this.add_category(".1" , "chicken");
        this.add_item(".0.2" , 4, "Click" , 5 , "Elite",  15);
        this.add_item(".0.0",2,"yellow cheese",5,"Emeck",10.2);
        this.add_item(".0.1",1 , "1.5% milk" , 2 , "IDO LTD",  3.5);
        this.add_item(".0.1", 0,"3% milk" , 5 , "IDO LTD",  3.5);
        this.add_item(".1" , 5  , "Beef Sausage",15,"Zogloveck",10.05);
        receive_order(155,0,20,"ile 5 shelf 10",Util.stringToDate("2023-05-20"),2.15);
        receive_order(120,1,10,"ile 5 shelf 11",Util.stringToDate("2023-05-23"),2.55);
        receive_order(20,2,6,"ile 2 shelf 3",Util.stringToDate("2023-10-25"),5.3);
        receive_order(155,4,20,"ile 5 shelf 10",Util.stringToDate("2023-05-20"),2.15);
        receive_order(345,5,15,"ile 6 shelf 2",Util.stringToDate("2023-10-20"),12.25);
    }

    /**
     * This function sets the minimal amount of items in the specific item.
     * @param item_id
     * @param amount
     * @return
     */
    public String set_minimal_amount(int item_id, int amount) throws Exception {
        if(amount < 0){
            throw new Exception("illegal amount");
        }
        if(items.containsKey(item_id))
            return items.get(item_id).setMin_amount(amount);
        else{
            throw new Exception("illegal item id");
        }
    }
    /**
     * This function produces the report of the damaged items.
     * @return
     * @throws Exception
     */
    public String produce_damaged_report() throws Exception{
        return damaged.produce_damaged_report();
    }

    public void add_item(String categories_index,int item_id, String name, int min_amount, String manufacturer_name, double original_price) throws Exception {
        Item i = new Item(item_id,name,min_amount,manufacturer_name,original_price,itemDalController, categories_index);
        add_item(categories_index,i);
    }

    /**
     *
     * @param categories_index
     * @param i
     * @throws Exception
     */
    public void add_item(String categories_index,Item i) throws Exception {

        set_item_call_back(i);
        if(items.containsKey(i.item_id)) {
            throw new Exception("Item id already exists");
        }
        items.put(i.item_id, i);
        int current_index = Integer.parseInt(Util.extractFirstNumber(categories_index));
        String next_index = Util.extractNextIndex(categories_index);
        categories.get(current_index).add_item(next_index, i);
        shortage_list.add(i); // why is it here?
        name_to_id.put(i.name+" "+i.manufacturer_name,i.item_id);
    }

    public void add_category(String categories_index, String name) throws Exception {
        if (categories_index == "") {
            Category new_category = new Category(name, "" + categories.size() , inv_dal_controller);
            categories.add(new_category);
            inv_dal_controller.insert(new_category.getDto());
        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber(categories_index));
            String next_index = Util.extractNextIndex(categories_index);
            Category cur_category = categories.get(current_index);
            cur_category.add_product(next_index,name);
        }
    }
    public void add_category(CategoryDTO categoryDTO) throws Exception {
        if (!categoryDTO.getIndex().contains(".")) {
            Category new_category = new Category(categoryDTO , inv_dal_controller,itemDalController);
            categories.add(new_category);

        }
        else {
            int current_index = Integer.parseInt(Util.extractFirstNumber("."+categoryDTO.getIndex()));
            String next_index = Util.extractNextIndex("."+categoryDTO.getFatherCategoryIndex());
            Category cur_category = categories.get(current_index);
            cur_category.add_product(categoryDTO,next_index);
        }
    }

    /**
     * Receive a new order for a specific item
     * @param order_id each order must have unique id.
     * @param item_id
     * @param amount
     * @param location
     * @param validity
     * @param cost_price the price that the store paid for this item, after discounts.
     */
    public String receive_order(int order_id, int item_id, int amount,String location,LocalDate validity,double cost_price) throws Exception {
        String to_return="";
        if(amount < 0)
            throw new Exception("illegal amount");
        if(cost_price < 0)
            throw new Exception("illegal cost price");
        int amount_warehouse = amount/2;
        int amount_store = amount - amount_warehouse;
        //check if exist
        if(items.containsKey(item_id)) {
            Item cur_item = items.get(item_id);
            to_return =  cur_item.recive_order(order_id, amount_warehouse, amount_store, cost_price, location, validity);
            if (shortage_list.contains(cur_item)) {
                if (cur_item.current_amount() >= cur_item.min_amount)
                    shortage_list.remove(cur_item);
            }
        }
        else{
            throw new Exception("illegal item id");
        }
        return to_return;


    }

    private void receive_order(ItemPerOrderDTO itemPerOrderDTO) throws Exception {
        String to_return="";
        int amount_warehouse = itemPerOrderDTO.getAmountWarehouse();
        int amount_store = itemPerOrderDTO.getAmountStore();
        //check if exist
        if(items.containsKey(itemPerOrderDTO.getItemId())) {
            Item cur_item = items.get(itemPerOrderDTO.getItemId());
            to_return =  cur_item.recive_order(new ItemPerOrder(itemPerOrderDTO));
            if (shortage_list.contains(cur_item)) {
                if (cur_item.current_amount() >= cur_item.min_amount)
                    shortage_list.remove(cur_item);
            }
        }
        else{
            throw new Exception("illegal item id");
        }


    }
    /**
     * This functions returns a specific Item by an id as a parameter.
     * @param id
     * @return
     */
    public Item get_item_by_id(int id) throws Exception {
        if (!items.containsKey(id))
            throw new Exception("Illegal id");
        return items.get(id);
    }

    /**
     * This function returns the list of the items that missing
     * @return
     */
    public List<Item> getShortageList(){
        return shortage_list;
    }

    /**
     * Cast Item_to_order object to Item object
     * @param itemToOrder
     * @return
     */
    public Item itemToOrder_to_item(ItemToOrder itemToOrder){
        if (!name_to_id.containsKey(itemToOrder.getProductName()+" "+itemToOrder.getManufacturer()))
            throw new IllegalArgumentException("this item not exist in the system");
        return items.get(name_to_id.get( itemToOrder.getProductName()+" "+itemToOrder.getManufacturer()));
    }

    /**
     * This function makes the things that need to be done automatically at the next day.
     * @param tomorrow_day
     */
    public void nextDay(DayOfWeek tomorrow_day) {
    }
    public void loadData() throws Exception {
        DiscountCounterDTO discountCounterDTO = inv_dal_controller.find("discountCounter","name","inventory_constants", DiscountCounterDTO.class);
        discount_counter = discountCounterDTO.getCount();
        List<CategoryDTO> categoryDTOList = inv_dal_controller.findAllCategories("inventory_categories",CategoryDTO.class);
        for (CategoryDTO categoryDTO : categoryDTOList){
            add_category(categoryDTO);
        }
        loadItems();
    }
    public void loadItems() throws Exception {
        List<ItemDTO> itemDTOList = itemDalController.findAll("inventory_item",ItemDTO.class);
        for(ItemDTO itemDTO : itemDTOList){
            Item i = new Item(itemDTO,itemDalController);
            add_item(itemDTO.getCategoriesIndex(),i);
        }
        loadItemPerOrder();
        //loadWaitingItems();
        //need to call here to the orderController
    }
    public void loadItemPerOrder() throws Exception{
        List<ItemPerOrderDTO> itemPerOrder = itemDalController.findAll("inventory_item_per_order", ItemPerOrderDTO.class);
        for (ItemPerOrderDTO itemPerOrderDto:itemPerOrder) {
            receive_order(itemPerOrderDto);
        }
        loadDicounts();

    }



    public void loadDicounts() throws Exception{
        List<DiscountDTO> discountDTOList = inv_dal_controller.findAll("inventory_discount", DiscountDTO.class);
        for (DiscountDTO discountDTO : discountDTOList){
            set_discount(discountDTO);
        }
        loadDamagedItems();
    }
    public void loadDamagedItems() throws Exception{
        List<DamagedItemDTO> damagedItemDTOS = itemDalController.findAll("inventory_damaged_items", DamagedItemDTO.class);
        for (DamagedItemDTO i:damagedItemDTOS) {
            damaged.addDamagedItem(items.get(i.getItem_id()),i);
        }
    }




    public ItemDalController getItemDalController() {
        return itemDalController;
    }
}
