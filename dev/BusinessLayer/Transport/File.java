package BusinessLayer.Transport;
import java.util.LinkedHashMap;

public class File {
    /**
     * the file id, unique value
     */
    private int id;
    /**
     * map of the amount for each product in this file
     */
    private LinkedHashMap<Product,Integer> products; 

    public File(int id){
        this.id = id;
        this.products = new LinkedHashMap<Product, Integer>();
    }


    public LinkedHashMap<Product, Integer> getProducts() {
        return products;
    }
    public int getId() {
        return this.id;
    }

    /**
     * add product in the required amount to the products map
     * @param product
     * @param amount
     */
    public void addProduct(Product product, int amount) {
        if(products.containsKey(product))
            products.replace(product,products.get(product) + amount);
        else
            products.put(product,amount);
    }
    
}
