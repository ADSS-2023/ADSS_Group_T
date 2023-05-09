package DataLayer.HR_T_DAL.DTOs;

import DataLayer.Util.DTO;

public class SiteDTO extends DTO {

    private String siteAddress;
    private String telNumber;
    private String contactName;
    private int x;
    private int y;
    private int shippingArea;
    private String type;

    public SiteDTO(String siteAddress, String telNumber, String contactName, int x, int y, int shippingArea,String type){
        super("Site");
        this.siteAddress = siteAddress;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.x = x;
        this.y = y;
        this.shippingArea = shippingArea;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getShippingArea() {
        return shippingArea;
    }
}
