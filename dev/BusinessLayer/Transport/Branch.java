package BusinessLayer.Transport;

public class Branch extends Site{

    String shippingArea;

    public Branch(String address,String telNumber,String contactName, String shippingArea){
        super(address,telNumber,contactName);
        this.shippingArea = shippingArea;
    }

    public String getShippingArea() {
        return shippingArea;
    }
}
