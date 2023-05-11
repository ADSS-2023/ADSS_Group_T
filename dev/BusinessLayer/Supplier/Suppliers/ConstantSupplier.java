package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import BusinessLayer.Supplier_Stock.Util_Supplier_Stock;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.ConstDeliveryDaysDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierContactDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConstantSupplier extends SupplierBusiness {

    private List<DayOfWeek> constDeliveryDays;

    private List<ConstDeliveryDaysDTO> constDeliveryDaysDTOS;
    public ConstantSupplier(String supplierName, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, List<DayOfWeek> constDeliveryDays, boolean selfDelivery, PaymentTerms paymentTerms, SupplierDalController supplierDalController) throws SQLException, SQLException {
        super(supplierName, address, supplierNum, bankAccountNum, contacts, selfDelivery, paymentTerms, supplierDalController);
        this.constDeliveryDaysDTOS = new LinkedList<>();
        this.supplierDTO = new SupplierDTO(supplierNum, supplierName, address, bankAccountNum, String.valueOf(selfDelivery), -1, paymentTerms.toString());
        supplierDalController.insert(supplierDTO);
        for (DayOfWeek day : constDeliveryDays) {
            ConstDeliveryDaysDTO constDeliveryDaysDTO = new ConstDeliveryDaysDTO(supplierNum, day.getValue());
            supplierDalController.insert(constDeliveryDaysDTO);
            constDeliveryDaysDTOS.add(constDeliveryDaysDTO);
        }
        this.constDeliveryDays =constDeliveryDays;
    }

    public ConstantSupplier(SupplierDTO supplierDTO, HashMap<String, String> contacts, ConcurrentHashMap<Integer, SupplierProductBusiness> products, List<DayOfWeek> days, SupplierDalController supplierDalController, List<Discount> discountPerTotalQuantity, List<Discount> discountPerTotalPrice) throws SQLException {
        super();
        this.contactDTOS = new LinkedList<>();
        this.constDeliveryDaysDTOS = new LinkedList<>();
        this.products = new ConcurrentHashMap<>();
        this.discountPerTotalQuantity = new ArrayList<>();
        this.discountPerTotalPrice = new ArrayList<>();
        this.supplierName = supplierDTO.getSupplierName();
        this.address = supplierDTO.getAddress();
        this.supplierNum = supplierDTO.getSupplierNum();
        this.bankAccountNum = supplierDTO.getBankAccountNum();
        this.contacts = contacts;
        this.selfDelivery = Boolean.parseBoolean(supplierDTO.isSelfDelivery());
        this.constDeliveryDays = days;
        this.paymentTerms = SupplierController.stringToPaymentTerms(supplierDTO.getPaymentTerms());
        this.supplierDalController = supplierDalController;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            SupplierContactDTO supplierContactDTO = new SupplierContactDTO(supplierNum, entry.getKey(), entry.getValue());
            contactDTOS.add(supplierContactDTO);
        }
        for (DayOfWeek day : constDeliveryDays) {
            ConstDeliveryDaysDTO constDeliveryDaysDTO = new ConstDeliveryDaysDTO(supplierNum, day.getValue());
            constDeliveryDaysDTOS.add(constDeliveryDaysDTO);
        }
        this.products = products;
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

    public void deleteConstantDays() throws SQLException {
        for (ConstDeliveryDaysDTO constDeliveryDay : constDeliveryDaysDTOS)  {
            supplierDalController.delete(constDeliveryDay);
        }
        constDeliveryDays = new ArrayList<>();
        constDeliveryDaysDTOS = new ArrayList<>();
    }
}
