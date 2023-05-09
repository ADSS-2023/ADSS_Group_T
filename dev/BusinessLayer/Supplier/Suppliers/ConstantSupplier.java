package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierContactDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConstantSupplier extends SupplierBusiness {

    private List<DayOfWeek> constDeliveryDays;
    public ConstantSupplier(String supplierName, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, List<DayOfWeek> constDeliveryDays, boolean selfDelivery, PaymentTerms paymentTerms, SupplierDalController supplierDalController) throws SQLException, SQLException {
        super(supplierName, address, supplierNum, bankAccountNum, contacts, selfDelivery, paymentTerms, supplierDalController);
        this.supplierDTO = new SupplierDTO(supplierNum, supplierName, address, bankAccountNum, selfDelivery, -1);
        supplierDalController.insert(supplierDTO);
        this.constDeliveryDays =constDeliveryDays;
    }

    @Override
    public int findEarliestSupplyDay() {
        LocalDate today = Util_Supplier_Stock.getCurrDay();
        int todayValue = today.getDayOfWeek().getValue();
        int daysToAdd = 7;
        for (DayOfWeek weekDay : constDeliveryDays) {
            String dayOfWeekStr = weekDay.name();
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekStr.toUpperCase(Locale.ENGLISH));
            int dayValue = dayOfWeek.getValue();
            int daysToNext = (dayValue >= todayValue) ? dayValue - todayValue : 7 - (todayValue - dayValue);
            if (daysToNext < daysToAdd) {
                daysToAdd = daysToNext;
            }
        }
        return daysToAdd;
    }


    public List<DayOfWeek> getConstDeliveryDays() {
        return constDeliveryDays;
    }
}
