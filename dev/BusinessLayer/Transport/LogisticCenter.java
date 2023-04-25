package BusinessLayer.Transport;

import java.util.LinkedHashMap;

public class LogisticCenter extends Site {

    private LinkedHashMap<Product, Integer> productsInStock;
    public LogisticCenter(String address,String telNumber,String contactName){
        super(address,telNumber,contactName,0,0);
        this.productsInStock = new LinkedHashMap<>();
    }

    public void add_1_ProducttoStock(Product product,int amount ) {
        if(productsInStock.containsKey(product))
            productsInStock.replace(product,productsInStock.get(product) + amount);
        else
            this.productsInStock.put(product,amount);
    }
    public void add_N_ProducttoStock(LinkedHashMap<Product, Integer> products) {
        for (Product product : products.keySet()) {
            add_1_ProducttoStock(product, products.get(product));
        }
    }

    public LinkedHashMap<Product, Integer> getAllProductInStock() {
        return this.productsInStock;
    }

    public int remove_1_ProductFromStock(Product product, int amount) {
        int currentAmount = productsInStock.get(product);
        int newAmount = currentAmount - amount;
        if (newAmount < 0) {
            newAmount = 0;
        }
        this.productsInStock.put(product, newAmount);
        return currentAmount - newAmount;
    }

    public LinkedHashMap<Product, Integer> remove_N_ProductsFromStock(LinkedHashMap<Product, Integer> products) {
        LinkedHashMap<Product, Integer> removedProducts = new LinkedHashMap<>();
        for (Product product : products.keySet()) {
            int amountToRemove = products.get(product);
            int amountRemoved = this.remove_1_ProductFromStock(product, amountToRemove);
            removedProducts.put(product, amountRemoved);
        }
        return removedProducts;
    }




}
