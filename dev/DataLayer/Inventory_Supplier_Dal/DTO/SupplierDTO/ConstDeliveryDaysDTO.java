package DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO;

import DataLayer.Util.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstDeliveryDaysDTO extends DTO {

    private int supplierNum;
    private int day;

    public ConstDeliveryDaysDTO(int supplierNum, int day){
        super("supplier_const_delivery_days");
        this.supplierNum = supplierNum;
        this.day = day;
    }

    public ConstDeliveryDaysDTO(){
        super("supplier_const_delivery_days");
    }

    public int getSupplierNum() {
        return supplierNum;
    }

    public int getDay() {
        return day;
    }

}
