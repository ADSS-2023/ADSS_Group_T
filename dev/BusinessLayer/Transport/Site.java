package BusinessLayer.Transport;

import UtilSuper.Location;

public abstract class Site {
    protected String address;
    protected String telNumber;
    protected String contactName;
    protected Location location;

    public Site(String address, String telNumber, String contactName, int x, int y) {
        this.address = address;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.location = new Location(x, y);
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

    public int getShippingArea() {
        return this.location.getShippingArea();
    }


}
