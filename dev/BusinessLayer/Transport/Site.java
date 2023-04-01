package BusinessLayer.Transport;

public class Site {
    protected String address;
    protected String telNumber;
    protected String contactName;
    private String shippingArea;

    public Site(String address,String telNumber,String contactName,String shippingArea){
        this.address = address;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.shippingArea = shippingArea;
    }

    public String getAddress() {
        return address;
    }
    public String getTelNumber() {
        return telNumber;
    }
    public String getContactName() {
        return contactName;
    }
    public String getShippingArea() {
        return shippingArea;
    }
}
