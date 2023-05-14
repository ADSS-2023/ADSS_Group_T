package BusinessLayer.Transport;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;
import DataLayer.Util.DAO;
import junit.framework.TestCase;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.Assert.assertThrows;

public class LogisticCenterControllerTest extends TestCase {
    private LogisticCenterController logisticCenterController;
    private DalLogisticCenterService dalLogisticCenterService;

    String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
    Connection connection = DriverManager.getConnection(testDBUrl);
    DAO dao = new DAO(connection);

    public LogisticCenterControllerTest() throws Exception {
    }

    public void setUp() throws Exception {
        super.setUp();
        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        Connection connection = DriverManager.getConnection(testDBUrl);
        this.dao.deleteAllDataFromDatabase();
        Data_init.initBasicData(dao);
        dalLogisticCenterService = new DalLogisticCenterService(connection);
        this.logisticCenterController = new LogisticCenterController(dalLogisticCenterService);
    }

    public void tearDown() throws Exception {
        dao.deleteAllDataFromDatabase();
    }

    public void testGetProductsInStock() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            logisticCenterController.getProductsInStock();
        });
        assertEquals("no products in stock", exception.getMessage());
    }

    public void testAddTruck() throws Exception {
        boolean added = logisticCenterController.addTruck(12345, "Test Model", 5000, 10000, 2);
        assertTrue(added);
        Truck truck = logisticCenterController.getTruck(12345);
        assertNotNull(truck);
    }

    public void testGetAllTrucks() throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            logisticCenterController.getAllTrucks();
        });
        assertEquals("no trucks in the system", exception.getMessage());
    }
}