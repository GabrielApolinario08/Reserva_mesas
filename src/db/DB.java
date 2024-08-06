package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                String initialUrl = url.substring(0, url.lastIndexOf('/'));
                connection = DriverManager.getConnection(initialUrl, properties);
                initializeDatabase(connection, properties);
                connection = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")){
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static void initializeDatabase(Connection connection, Properties properties) throws SQLException {
        String databaseName = properties.getProperty("databaseName");
        String table1Name = properties.getProperty("table1Name");
        String table2Name = properties.getProperty("table2Name");

        try (Statement statement = connection.createStatement()) {
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.execute(createDatabaseSQL);

            String useDatabaseSQL = "USE " + databaseName;
            statement.execute(useDatabaseSQL);

            ResultSet resultSet2 = connection.getMetaData().getTables(null, null, table2Name, null);
            if (!resultSet2.next()) {
                String createTable2SQL = "CREATE TABLE IF NOT EXISTS " + table2Name + " ("
                        + "Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "Number INT NOT NULL, "
                        + "Capacity INT NOT NULL"
                        + ")";
                statement.execute(createTable2SQL);
            }

            ResultSet resultSet1 = connection.getMetaData().getTables(null, null, table1Name, null);
            if (!resultSet1.next()) {
                String createTable1SQL = "CREATE TABLE IF NOT EXISTS " + table1Name + " ("
                        + "Id INT NOT NUlL AUTO_INCREMENT PRIMARY KEY, "
                        + "ClientName VARCHAR(255) NOT NULL, "
                        + "ReservationDate TIMESTAMP NOT NULL, "
                        + "PeopleNumber INT NOT NULL, "
                        + "IdTable INT NOT NULL, "
                        + "FOREIGN KEY (IdTable) REFERENCES restauranttable(Id)"
                        + ")";
                statement.execute(createTable1SQL);
            }
        }
    }
}
