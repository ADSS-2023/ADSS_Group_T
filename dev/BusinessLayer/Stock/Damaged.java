package BusinessLayer.Stock;

import java.util.LinkedList;
import java.util.List;
/*
    This class represents all damaged goods that have found in the store.
    It holds a list of damaged items.
 */
public class Damaged {
    protected List<DamagedItem> damagedItems;

    public Damaged(){
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
    public void addDamagedItem(Item item ,int order_id, int amount , String description){
        damagedItems.add(new DamagedItem(item , amount , description));
        item.reduce(order_id,amount);
    }

    /**
     *  This function called from ___Service when there is a requirement to produce
     *  a damaged items report , and return list of strings for each damaged item.
     *
     * @return
     */
    public List<String> produce_damaged_report(){
        List<String> returnReport = new LinkedList<>();
        for (DamagedItem curItem : damagedItems) {
            returnReport.add(curItem.produceReport());
        }
        return returnReport;
    }
}
