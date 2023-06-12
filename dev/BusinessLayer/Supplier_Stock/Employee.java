package BusinessLayer.Supplier_Stock;

public class Employee {
    private String id;

    Occupation occupation;

    public Employee(String id,Occupation occupation){
        this.id = id;
        this.occupation = occupation;
    }

    public enum Occupation {
        WareHouse,Suppliers,Manager
    }
    public boolean isWareHouseEmployee(){
        return  occupation.equals(Occupation.WareHouse);
    }
    public boolean isManager(){
        return  occupation.equals(Occupation.Manager);
    }
    public boolean isSupplierEmployee(){
        return occupation.equals(Occupation.Suppliers);
    }

    public String getId() {
        return id;
    }
}
