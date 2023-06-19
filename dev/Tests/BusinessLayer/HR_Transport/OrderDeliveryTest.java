package BusinessLayer.HR_Transport;
import BusinessLayer.HR.*;
import BusinessLayer.Transport.Delivery;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import DataLayer.HR_T_DAL.DB_init.SiteAddresses;
import PresentationLayer.TransportManagerPresentation;
import UtilSuper.Pair;
import UtilSuper.ServiceFactory;
import UtilSuper.Time;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import static org.junit.Assert.*;
public class OrderDeliveryTest {
private ServiceFactory serviceFactory;
    @Before
    public void setUp() throws Exception {
        serviceFactory   = new ServiceFactory();
        Data_init.initOldData(serviceFactory.getDAO(),serviceFactory.getSupplierService(),serviceFactory.getDeliveryService());
        Data_init_HR.initOldData(serviceFactory.getDAO(), serviceFactory.getEmployeeService(), serviceFactory.getShiftService(),
                serviceFactory.getEmployeeController(), serviceFactory.getShiftController(),serviceFactory.getDalShiftService());
        String branch1 = SiteAddresses.getBranchAddress(0);
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products1 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts1 = new LinkedHashMap<>();
        supplierProducts1.put("Milk", 500);
        supplierProducts1.put("Cheese", 1000);
        supplierProducts1.put("Eggs", 200);
        products1.put(SiteAddresses.getSupplierAddress(0), supplierProducts1);
        LinkedHashMap<String, Integer> supplierProducts2 = new LinkedHashMap<>();
        supplierProducts2.put("Coke", 1000);
        supplierProducts2.put("Diet Coke", 250);
        supplierProducts2.put("Coke Zero", 1000);
        supplierProducts2.put("Sprite", 300);
        supplierProducts2.put("Diet Sprite", 200);
        supplierProducts2.put("Fanta", 200);
        supplierProducts2.put("Diet Fanta", 150);
        products1.put(SiteAddresses.getSupplierAddress(1), supplierProducts2);
        String date1 = Time.localDateToString(LocalDate.now().plusDays(2));

        try {
            serviceFactory.getDeliveryController().orderDelivery(branch1, products1, date1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkIfAddDelivery() throws SQLException {
        assertEquals(5, serviceFactory.getDeliveryController().getAllDeliveries().size());
    }

    @Test
    public void  checkIfRequierementsDrverswith() throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requierementsForDate  = serviceFactory.getDriverController().lazyLoadAllRequierementsForDate(LocalDate.now().plusDays(3));
        assertEquals(2,requierementsForDate.size());
    }

    @Test
    public void  checkIfRequierementsDrverswithout() throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requierementsForDate  = serviceFactory.getDriverController().lazyLoadAllRequierementsForDate(LocalDate.now().plusDays(4));
        assertEquals(0,requierementsForDate.size());
    }

    @Test
    public void checkDriversToTruck() throws Exception {
        serviceFactory.getDeliveryController().getNextDayDeatails();
        serviceFactory.getDeliveryController().skipDay();
        serviceFactory.getDeliveryController().getNextDayDeatails();
        LinkedHashMap <Integer,Delivery> deliveries = serviceFactory.getDeliveryController().getAllDeliveries();

        Driver driver1 = serviceFactory.getDriverController().lazyLoadDriver(serviceFactory.getDeliveryController().getDelivery(0).getDriverID());
        Driver driver2 = serviceFactory.getDriverController().lazyLoadDriver(serviceFactory.getDeliveryController().getDelivery(1).getDriverID());

       assertNotEquals(driver1,driver2);
    }

    @Test
    public void testEnterOverWeightAction() {
        TransportManagerPresentation presentation = new TransportManagerPresentation(serviceFactory.getLogisticCenterService(),serviceFactory.getDeliveryService()
                , serviceFactory.getSupplierService(), serviceFactory.getBranchService());
        String input = "1"; // simulate user input
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // set System.in to the simulated input stream
        int action = presentation.enterOverWeightAction(0); // call the function with a dummy delivery ID
        assertEquals(1, action); // verify that the function returned the expected value
    }




}


