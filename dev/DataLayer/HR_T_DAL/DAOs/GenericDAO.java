package DataLayer.HR_T_DAL.DAOs;

import java.sql.*;
import java.util.*;

public class GenericDAO<T> {
    private final String tableName;
    private final String createTableSql;
    private final String insertSql;
    private final String selectAllSql;
    private final String selectByIdSql;
    private final String updateSql;
    private final String deleteSql;

    private final Connection connection;
    private final PreparedStatement createTableStatement;
    private final PreparedStatement insertStatement;
    private final PreparedStatement selectAllStatement;
    private final PreparedStatement selectByIdStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public GenericDAO(String tableName, String createTableSql, String insertSql, String selectAllSql, String selectByIdSql, String updateSql, String deleteSql) throws SQLException {
        this.tableName = tableName;
        this.createTableSql = createTableSql;
        this.insertSql = insertSql;
        this.selectAllSql = selectAllSql;
        this.selectByIdSql = selectByIdSql;
        this.updateSql = updateSql;
        this.deleteSql = deleteSql;

        connection = DriverManager.getConnection("jdbc:sqlite:database.db");

        createTableStatement = connection.prepareStatement(createTableSql);
        insertStatement = connection.prepareStatement(insertSql);
        selectAllStatement = connection.prepareStatement(selectAllSql);
        selectByIdStatement = connection.prepareStatement(selectByIdSql);
        updateStatement = connection.prepareStatement(updateSql);
        deleteStatement = connection.prepareStatement(deleteSql);

        createTableStatement.executeUpdate();
    }

    public void create(T object) throws SQLException {
        insertStatement.clearParameters();
        insertParameters(insertStatement, object);
        insertStatement.executeUpdate();
    }

    public List<T> getAll() throws SQLException {
        ResultSet resultSet = selectAllStatement.executeQuery();
        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(mapResultSetToObject(resultSet));
        }
        return result;
    }

    public T getById(int id) throws SQLException {
        selectByIdStatement.clearParameters();
        selectByIdStatement.setInt(1, id);
        ResultSet resultSet = selectByIdStatement.executeQuery();
        if (!resultSet.next()) {
            throw new SQLException("No such record");
        }
        return mapResultSetToObject(resultSet);
    }

    public void update(T object) throws SQLException {
        updateStatement.clearParameters();
        updateParameters(updateStatement, object);
        updateStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        deleteStatement.clearParameters();
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    protected void insertParameters(PreparedStatement statement, T object) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    protected void updateParameters(PreparedStatement statement, T object) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    protected T mapResultSetToObject(ResultSet resultSet) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void close() throws SQLException {
        connection.close();
    }
}
