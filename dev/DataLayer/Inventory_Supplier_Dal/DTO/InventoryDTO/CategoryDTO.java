package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

public class CategoryDTO extends DataLayer.Util.DTO{
    private String index_item;
    private String name;
    private String father_category_index;

    public CategoryDTO(String index_item, String name , String father_category_index) {
        super("inventory_categories");
        this.index_item = index_item;
        this.father_category_index = father_category_index;
        this.name = name;
    }

    public String getIndex() {
        return index_item;
    }

    public String getFatherCategoryIndex() {
        return father_category_index;
    }

    public String getName() {
        return name;
    }

    public void setIndex(String next_index) {
        this.index_item = next_index;
    }
}
