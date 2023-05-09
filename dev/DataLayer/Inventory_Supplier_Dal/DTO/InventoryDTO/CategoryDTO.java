package DataLayer.Inventory_Supplier_Dal.DTO.InventoryDTO;

public class CategoryDTO {
    private String index;
    private String name;
    private String father_category_index;

    public CategoryDTO(String index, String name , String father_category_index) {
        this.index = index;
        this.father_category_index = father_category_index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public String getFatherCategoryIndex() {
        return father_category_index;
    }

    public String getName() {
        return name;
    }

}
