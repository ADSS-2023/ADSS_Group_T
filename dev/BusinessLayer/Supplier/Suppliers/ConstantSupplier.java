package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
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
        this.supplierDTO = new SupplierDTO(supplierNum, supplierName, address, bankAccountNum, selfDelivery, -1, paymentTerms.toString());
        supplierDalController.insert(supplierDTO);
        this.constDeliveryDays =constDeliveryDays;
    }

    public ConstantSupplier(SupplierDTO supplierDTO, HashMap<String, String> contacts, HashMap<Integer, SupplierProductBusiness> products, List<DayOfWeek> days, SupplierDalController supplierDalController, List<Discount> discountPerTotalQuantity, List<Discount> discountPerTotalPrice) throws SQLException {
        super(supplierDTO.getSupplierName(), supplierDTO.getAddress(), supplierDTO.getSupplierNum(), supplierDTO.getBankAccountNum(), contacts, supplierDTO.isSelfDelivery(), SupplierController.stringToPaymentTerms(supplierDTO.getPaymentTerms()), supplierDalController);
        setProducts(products);
        this.constDeliveryDays = days;
        this.supplierDTO = supplierDTO;
        this.discountPerTotalPrice = discountPerTotalPrice;
        this.discountPerTotalQuantity = discountPerTotalQuantity;
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
