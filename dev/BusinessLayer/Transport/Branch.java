package BusinessLayer.Transport;

import DataLayer.HR_T_DAL.DTOs.SiteDTO;

public class Branch extends Site {
    public Branch(String address, String telNumber, String contactName, int x, int y) {
        super(address, telNumber, contactName, x, y);
    }

    public Branch(SiteDTO dto){
        super(dto.getSiteAddress(), dto.getTelNumber(), dto.getContactName(), dto.getX(), dto.getY());
    }
}
