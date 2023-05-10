package BusinessLayer.Supplier_Stock;
import BusinessLayer.Stock.Util.Util;
import DataLayer.Inventory_Supplier_Dal.DTO.CurrDateDTO;
import DataLayer.Inventory_Supplier_Dal.DalController.InventoryDalController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
public class Util_Supplier_Stock {
    private static LocalDate currDay;
    private InventoryDalController inventoryDalController;
    public Util_Supplier_Stock(InventoryDalController inventoryDalController) throws SQLException {
        this.inventoryDalController = inventoryDalController;
        currDay = Util.stringToDate(inventoryDalController.find("currDate","name", "inventory_constants",CurrDateDTO.class).getDate());
    }
    public static LocalDate getCurrDay(){
        return currDay;
    }
    public void nextDay() throws SQLException {
        inventoryDalController.update(new CurrDateDTO(currDay.toString()),new CurrDateDTO(currDay.plusDays(1).toString()));
        currDay = currDay.plusDays(1);

    }
}



