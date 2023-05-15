package BusinessLayer.HR_Transport;
import BusinessLayer.HR.*;
import BusinessLayer.Transport.Delivery;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
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
        String branch1 = "b1";
        LinkedHashMap<String, LinkedHashMap<String, Integer>> products1 = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> supplierProducts1 = new LinkedHashMap<>();
        supplierProducts1.put("Beef", 5);
        supplierProducts1.put("Pork", 10);
        supplierProducts1.put("Chicken", 20);
        products1.put("s1", supplierProducts1);
        LinkedHashMap<String, Integer> supplierProducts2 = new LinkedHashMap<>();
        supplierProducts2.put("Bread", 8);
        supplierProducts2.put("Cupcake", 5);
        products1.put("s2", supplierProducts2);
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
        assertEquals(3,requierementsForDate.size());
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


