package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierContactDTO;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class OccasionalSupplier extends SupplierBusiness {

    @Override
    public int findEarliestSupplyDay() {
        return daysToDeliver;
    }

    @Override
    public void deleteConstantDays() throws SQLException {
    }

    private int daysToDeliver;


    public OccasionalSupplier(String supplierName, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, int daysToDeliver, boolean selfDelivery, PaymentTerms paymentTerms, SupplierDalController supplierDalController) throws SQLException {
        super(supplierName, address, supplierNum, bankAccountNum, contacts, selfDelivery, paymentTerms, supplierDalController);
        this.supplierDTO = new SupplierDTO(supplierNum, supplierName, address, bankAccountNum, String.valueOf(selfDelivery), daysToDeliver, paymentTerms.toString());
        supplierDalController.insert(supplierDTO);
        this.daysToDeliver=daysToDeliver;
    }

    public OccasionalSupplier(SupplierDTO supplierDTO, HashMap<String, String> contacts, HashMap<Integer, SupplierProductBusiness> products, int daysToDeliver, SupplierDalController supplierDalController, List<Discount> discountPerTotalQuantity, List<Discount> discountPerTotalPrice) throws SQLException {
        super();
        this.contactDTOS = new LinkedList<>();
        this.products = new HashMap<>();
        this.discountPerTotalQuantity = new ArrayList<>();
        this.discountPerTotalPrice = new ArrayList<>();
        this.supplierName = supplierDTO.getSupplierName();
        this.address = supplierDTO.getAddress();
        this.supplierNum = supplierDTO.getSupplierNum();
        this.bankAccountNum = supplierDTO.getBankAccountNum();
        this.contacts = contacts;
        this.daysToDeliver = daysToDeliver;
        this.selfDelivery = Boolean.parseBoolean(supplierDTO.isSelfDelivery());
        this.paymentTerms = SupplierController.stringToPaymentTerms(supplierDTO.getPaymentTerms());
        this.supplierDalController = supplierDalController;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            SupplierContactDTO supplierContactDTO = new SupplierContactDTO(supplierNum, entry.getKey(), entry.getValue());
            contactDTOS.add(supplierContactDTO);
        }
        this.products = products;
        this.supplierDTO = supplierDTO;
        this.discountPerTotalPrice = discountPerTotalPrice;
        this.discountPerTotalQuantity = discountPerTotalQuantity;
    }
}
