package BusinessLayer.Supplier.Suppliers;

import BusinessLayer.Supplier.OrderProduct;
import Util.PaymentTerms;
import Util.WeekDays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ConstantSupplier extends SupplierBusiness {

    private List<WeekDays> constDeliveryDays;
    public ConstantSupplier(String supplierName, String address, int supplierNum, int bankAccountNum, HashMap<String, String> contacts, List<WeekDays> constDeliveryDays, boolean selfDelivery, PaymentTerms paymentTerms) {
        super(supplierName, address, supplierNum, bankAccountNum, contacts, selfDelivery, paymentTerms);
        this.constDeliveryDays =constDeliveryDays;
    }

    @Override
    public int findEarliestSupplyDay() {
        LocalDate today = LocalDate.now();
        int todayValue = today.getDayOfWeek().getValue();
        int daysToAdd = 7;
        for (WeekDays weekDay : constDeliveryDays) {
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


    public List<WeekDays> getConstDeliveryDays() {
        return constDeliveryDays;
    }
}
