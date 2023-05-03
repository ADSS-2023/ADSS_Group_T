package BusinessLayer.Transport;

import java.util.LinkedHashMap;

public class File {
    /**
     * the file id, unique value
     */
    private final int id;
    /**
     * map of the amount for each product in this file
     */
    private final LinkedHashMap<Product, Integer> products;

    public File(int id) {
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
     *
     * @param product - the required product
     * @param amount  - the required amount
     */
    public void addProduct(Product product, int amount) {
        if (products.containsKey(product))
            products.replace(product, products.get(product) + amount);
        else
            products.put(product, amount);
    }

    /**
     * remove product in the required amount from the products map
     *
     * @param product - the required product
     * @param amount  - the required amount
     */
    public void removeProduct(Product product, int amount) {
        if (!products.containsKey(product))
            throw new RuntimeException("attempt to remove a product that does not exist in thr file");
        else if (products.get(product) < amount)
            throw new RuntimeException("attempt to remove an amount larger than the current amount in thr file");
        else
            products.replace(product, products.get(product) - amount);
    }
}
