package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Util.PaymentTerms;
import DataLayer.Inventory_Supplier_Dal.DalController.SupplierDalController;

import java.sql.SQLException;
import java.util.HashMap;

public class OccasionalSupplier extends SupplierBusiness {

    @Override
    public int findEarliestSupplyDay() {
        return daysToDeliver;
    }

    private int daysToDeliver;//TODO: change


    public OccasionalSupplier(String supplierName, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, int daysToDeliver, boolean selfDelivery, PaymentTerms paymentTerms, SupplierDalController supplierDalController) throws SQLException {
        super(supplierName, address, supplierNum, bankAccountNum, contacts, selfDelivery, paymentTerms, supplierDalController);
        this.daysToDeliver=daysToDeliver;
    }
}
