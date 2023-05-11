package BusinessLayer.Stock;

import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.DamagedItemDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;

import java.util.LinkedList;
import java.util.List;
/*
    This class represents all damaged goods that have found in the store.
    It holds a list of damaged items.
 */
public class Damaged {
    protected List<DamagedItem> damagedItems;
    protected InventoryDalController inventoryDalController;

    public Damaged(InventoryDalController inventoryDalController){
        this.inventoryDalController = inventoryDalController;
        damagedItems = new LinkedList<>();
    }

    /**
     * This function gets the details that need to make a new DamagedItem and add new
     * one to the list.
     *
     * @param item
     * @param amount
     * @param description
     */
    public String addDamagedItem(Item item ,int order_id, int amount , String description) throws Exception {
        DamagedItem new_damaged_item = new DamagedItem(item , amount , description);
        damagedItems.add(new_damaged_item);
        this.inventoryDalController.insert(new_damaged_item.getDto());
        return item.reduce(order_id,amount);
    }
    public void addDamagedItem(Item item, DamagedItemDTO damagedItemDTO){
        damagedItems.add(new DamagedItem(damagedItemDTO,item));
    }
    /**
     *  This function called from ___Service when there is a requirement to produce
     *  a damaged items report , and return list of strings for each damaged item.
     *
     * @return
     */

    public String produce_damaged_report() throws Exception {
        if (damagedItems.isEmpty())
            throw new Exception("no damaged items");
        String returnReport = "";
        for (DamagedItem curItem : damagedItems) {
            returnReport+=curItem.produceReport();
        }
        return returnReport;
    }
}
