package DataLayer.Inventory_Supplier_Dal.DalController;

public class ItemDalController {
    private ItemDAO item_DAO;
    private ItemPerOrderDAO item_per_order_DAO;

    public ItemDalController(){
        item_DAO = new ItemDAO();
        item_per_order_DAO = new ItemPerOrderDAO();
    }
}

