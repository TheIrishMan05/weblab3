package org.example.labwork3.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.extern.log4j.Log4j2;
import org.example.labwork3.models.Point;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Named
@Log4j2
@ApplicationScoped
public class PointService implements Repository<Point>, Serializable {

    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "Oracle_123";
    private static final String DB_URL = "jdbc:oracle:thin:@//db:1521/FREE";
    private final AtomicInteger idGenerator = new AtomicInteger(getMaxIdFromDatabase());

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        createTable();
    }

    private int getMaxIdFromDatabase() {
        String query = "SELECT NVL(MAX(id), 0) AS max_id FROM points";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("max_id");
            }
        } catch (SQLException exception) {
            log.error("Error fetching max id from database", exception);
        }
        return 0;
    }


    private static void createTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String checkTableExisting = """
            SELECT COUNT(*) AS count
            FROM all_tables
            WHERE table_name = 'POINTS'
        """;
            try (ResultSet resultSet = statement.executeQuery(checkTableExisting)) {
                if (resultSet.next() && resultSet.getInt("count") > 0) {
                    log.info("Table already exists. Skipping...");
                    return;
                }
            }

            String createQuery = """
           CREATE TABLE points (
           id NUMBER PRIMARY KEY,
           x NUMBER NOT NULL,
           y NUMBER NOT NULL,
           r NUMBER NOT NULL,
           is_hit NUMBER(1) NOT NULL CHECK(is_hit in (0, 1)),
           time VARCHAR(255) NOT NULL,
           execution_time NUMBER NOT NULL,
           session_id VARCHAR(50))
        """;
            statement.execute(createQuery);
            log.info("Table 'points' created successfully.");

        } catch (SQLException exception) {
            log.error("Error creating table", exception);
        }
    }



    @Override
    public List<Point> findBySessionId(String sessionId) {
        List<Point> points = new ArrayList<>();
        String selectByIdQuery = "SELECT * FROM points WHERE session_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectByIdQuery)) {

            statement.setString(1, sessionId);
            log.info("Executing query: " + selectByIdQuery + " for session_id: " + sessionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Point point = new Point();
                    point.setId(resultSet.getInt("id"));
                    point.setX(resultSet.getDouble("x"));
                    point.setY(resultSet.getDouble("y"));
                    point.setR(resultSet.getDouble("r"));
                    point.setHit(resultSet.getInt("is_hit") == 1);
                    point.setTime(resultSet.getString("time"));
                    point.setExecutionTime(resultSet.getLong("execution_time"));
                    point.setSessionId(resultSet.getString("session_id"));
                    points.add(point);
                }
            }
        } catch (SQLException exception) {
            log.error("Error executing query: " + selectByIdQuery, exception);
        }
        return points;
    }

    @Override
    public void insert(Point point) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String insertQuery = """
                        INSERT INTO points (id, x, y, r, is_hit, time, execution_time, session_id)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, idGenerator.incrementAndGet());
                statement.setDouble(2, point.getX());
                statement.setDouble(3, point.getY());
                statement.setDouble(4, point.getR());
                statement.setInt(5, point.isHit() ? 1 : 0);
                statement.setString(6, point.getTime());
                statement.setLong(7, point.getExecutionTime());
                statement.setString(8, point.getSessionId());
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException exception) {
            log.error("Error inserting point", exception);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    log.error("Error during rollback", e);
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error closing connection", e);
                }
            }
        }
    }
}
