package BusinessLayer.Stock;

import java.util.Map;

import java.util.Map;

public class DamagedItem {
    private Map<Item, Integer> items;

    public DamagedItem(Map<Item, Integer> items) {
        this.items = items;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }
}

