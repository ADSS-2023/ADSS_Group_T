package BusinessLayer.Transport;
import java.util.HashMap;

public class File {
    private int id;
    private HashMap<Product,Integer> products; 

    public File(int id){
        this.id = id;
        this.products = new HashMap<Product, Integer>();
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }
    public int getId() {
        return this.id;
    }

    public void addProduct(Product product, int amount) {
        if(products.containsKey(product))
            products.replace(product,products.get(product) + amount);
        else
            products.put(product,amount);
    }
    
}