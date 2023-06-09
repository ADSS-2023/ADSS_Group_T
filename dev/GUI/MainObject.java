package GUI;

import PresentationLayer.DriverPresentation;
import PresentationLayer.EmployeePresentation;
import PresentationLayer.HRManagerPresentation;
import PresentationLayer.TransportManagerPresentation;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.SupplierService;
import ServiceLayer.UserService;
import UtilSuper.ServiceFactory;

import javax.swing.*;

public class MainObject {
    private ServiceFactory serviceFactory;
    private ShiftService shiftService;
    private EmployeeService employeeService;
    private LogisticCenterService logisticCenterService;
    private DeliveryService deliveryService;
    private UserService userService;
    private BranchService branchService;
    private SupplierService supplierService;
    private TransportManagerPresentation transportManagerPresentation;
    private HRManagerPresentation hrManagerPresentation;
    private EmployeePresentation employeePresentation;
    private DriverPresentation driverPresentation;
    public MainObject() {
        try {
            this.serviceFactory = new ServiceFactory();
        }
        catch (Exception exception){
            System.out.println(exception.toString());
        }


        // Create an instance of MyWindow
        this.shiftService = serviceFactory.getShiftService();
        this.employeeService = serviceFactory.getEmployeeService();
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.deliveryService = serviceFactory.getDeliveryService();
        this.userService = serviceFactory.getUserService();
        this.branchService = serviceFactory.getBranchService();
        this.supplierService = serviceFactory.getSupplierService();
        transportManagerPresentation = new TransportManagerPresentation(logisticCenterService,deliveryService,supplierService,branchService);
        hrManagerPresentation = new HRManagerPresentation(shiftService,employeeService,branchService);
        employeePresentation = new EmployeePresentation(employeeService,branchService);
        driverPresentation = new DriverPresentation(employeeService);
        serviceFactory.callbackEnterWeight(this.transportManagerPresentation::enterWeightFunction);
        serviceFactory.callbackEnterOverWeight(this.transportManagerPresentation::enterOverWeightAction);
        MainFrame mainFrame = new MainFrame(serviceFactory);


    }
}
