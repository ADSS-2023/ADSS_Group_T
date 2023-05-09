package BusinessLayer.Stock;

import DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO.DamagedItemDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;
import DataLayer.Util.DTO;

import java.util.Map;

import java.util.Map;
/*
    This class represents a damaged item. It has a pointer to the item,
    amount of damaged items from this kind, and description of the faulty.
 */
public class DamagedItem {
    private Item item;
    private int amount;
    private String description;
    private DamagedItemDTO damaged_item_DTO;

    public DamagedItem(Item item, int amount, String description) {
        this.item = item;
        this.amount = amount;
        this.description = description;
        this.damaged_item_DTO = new DamagedItemDTO(item.item_id , amount , description);
    }

    /**
     * This function update the amount of the specific damaged item.
     *
     * @param amount - int that represent the amount of damaged items
     */
    public void updateAmount(int amount) {
        this.amount = amount;
    }

    /**
     * This function called from Damaged class when there is a requirement to produce
     *  a damaged report , and returns a string with the details about a specific damaged item.
     *
     * @return
     */
    public String produceReport(){
        return "Item name : " + item.get_name() + " , Item ID : " + item.getItem_id() + " , Amount : " + amount + " , Description : " + description;
    }

    public DTO getDto() {
        return damaged_item_DTO;
    }
}

