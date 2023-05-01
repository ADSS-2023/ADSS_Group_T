package BusinessLayer.Transport;
import BusinessLayer.Transport.Driver.CoolingLevel;

import java.util.ArrayList;

public class Supplier extends Site{

    ArrayList<Product> products;

    public Supplier(String address,String telNumber,String contactName,int x,int y,ArrayList<Product> product){
        super(address,telNumber,contactName,x,y);
        this.products = product;
    }

    public void addProductToSupplier(Product p){
        products.add(p);
    }

    public boolean removeProductFromSupplier(Product p){
        if(products.contains(p))
            return false;
        products.remove(p);
        return true;
    }
}
