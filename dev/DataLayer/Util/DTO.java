package DataLayer.Util;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DTO {
    protected String tableName;

    /**
     * DTO constructor, must get a table name as 'name_of_object'
     * @param tableName
     */
    public DTO(String tableName){
        this.tableName=tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName=tableName;
    }

}
