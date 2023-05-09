package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.Supplier_Util.PaymentTerms;

import java.util.HashMap;

public class OccasionalSupplier extends SupplierBusiness {

    @Override
    public int findEarliestSupplyDay() {
        return daysToDeliver;
    }

    private int daysToDeliver;//TODO: change


    public OccasionalSupplier(String supplierName, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, int daysToDeliver, boolean selfDelivery, PaymentTerms paymentTerms) {
        super(supplierName, address, supplierNum, bankAccountNum, contacts, selfDelivery, paymentTerms);
        this.daysToDeliver=daysToDeliver;
    }
}
