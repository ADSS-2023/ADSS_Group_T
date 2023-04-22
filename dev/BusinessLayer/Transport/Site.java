package BusinessLayer.Transport;

public abstract class Site {
    protected String address;
    protected String telNumber;
    protected String contactName;

    public Site(String address,String telNumber,String contactName){
        this.address = address;
        this.telNumber = telNumber;
        this.contactName = contactName;
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


}
