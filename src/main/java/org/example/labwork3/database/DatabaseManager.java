package org.example.labwork3.database;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
@Setter
@Getter
public class DatabaseManager {
    private DatabaseConfiguration config = new DatabaseConfiguration();
    private Connection connection;
    private static DatabaseManager instance;

    public DatabaseManager() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(config.getUrl(),
                    config.getHostname(),
                    config.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e);
            System.exit(1);
        }
    }

    @SneakyThrows
    public static DatabaseManager getInstance() {
        if (instance == null || !instance.connection.isClosed()) {
            return instance = new DatabaseManager();
        } else {
            return instance;
        }
    }
}
