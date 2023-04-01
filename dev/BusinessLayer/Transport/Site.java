package BusinessLayer.Transport;

public class Site {
    private String address;
    private String telNumber;
    private String contactName;
    private String shippingArea;

    public Site(String address,String telNumber,String contactName,String shippingArea){
        this.address = address;
        this.telNumber = telNumber;
        this. contactName = contactName;
        this.shippingArea = shippingArea;
    }

    public String getName() {
        return this.address;
    }


}
