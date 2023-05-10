package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Discounts.Discount;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Supplier.SupplierProductBusiness;
import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;
import DataLayer.Inventory_Supplier_Dal.DTO.SupplierDTO.SupplierDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;

public class OccasionalSupplier extends SupplierBusiness {

    @Override
    public int findEarliestSupplyDay() {
        return daysToDeliver;
    }

    private int daysToDeliver;


    public OccasionalSupplier(String supplierName, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, int daysToDeliver, boolean selfDelivery, PaymentTerms paymentTerms, SupplierDalController supplierDalController) throws SQLException {
        super(supplierName, address, supplierNum, bankAccountNum, contacts, selfDelivery, paymentTerms, supplierDalController);
        this.supplierDTO = new SupplierDTO(supplierNum, supplierName, address, bankAccountNum, selfDelivery, daysToDeliver, paymentTerms.toString());
        supplierDalController.insert(supplierDTO);
        this.daysToDeliver=daysToDeliver;
    }

    public OccasionalSupplier(SupplierDTO supplierDTO, HashMap<String, String> contacts, HashMap<Integer, SupplierProductBusiness> products, int daysToDeliver, SupplierDalController supplierDalController, List<Discount> discountPerTotalQuantity, List<Discount> discountPerTotalPrice) throws SQLException {
        super(supplierDTO.getSupplierName(), supplierDTO.getAddress(), supplierDTO.getSupplierNum(), supplierDTO.getBankAccountNum(), contacts, supplierDTO.isSelfDelivery(), SupplierController.stringToPaymentTerms(supplierDTO.getPaymentTerms()), supplierDalController);
        setProducts(products);
        this.daysToDeliver = daysToDeliver;
        this.supplierDTO = supplierDTO;
        this.discountPerTotalPrice = discountPerTotalPrice;
        this.discountPerTotalQuantity = discountPerTotalQuantity;
    }
}
