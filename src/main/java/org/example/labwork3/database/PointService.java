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

    private static final String USER = getEnvOrThrow("USER");
    private static final String PASSWORD = getEnvOrThrow("DB_PASSWORD");
    private static final String DB_URL = "jdbc:postgresql://pg:5432/studs?currentSchema=s409109";

    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver not found ", e);
        }
        createTable();
    }

    private static void createTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {


            String createQuery = """
                CREATE OR REPLACE TABLE points (
                  id            INTEGER PRIMARY KEY,
                  x             DOUBLE PRECISION NOT NULL,
                  y             DOUBLE PRECISION NOT NULL,
                  r             DOUBLE PRECISION NOT NULL,
                  is_hit        BOOLEAN NOT NULL,
                  time          VARCHAR(255) NOT NULL,
                  execution_time BIGINT NOT NULL,
                  session_id    VARCHAR(50)
                )
                """;
            statement.execute(createQuery);
            log.info("Table 'points' created successfully.");

        } catch (SQLException ex) {
            log.error("Error creating table 'points'", ex);
        }
    }

    @Override
    public List<Point> findBySessionId(String sessionId) {
        List<Point> points = new ArrayList<>();
        String sql = "SELECT * FROM points WHERE session_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sessionId);
            log.info("Executing query: {}", sql);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Point p = new Point();
                    p.setId(rs.getInt("id"));
                    p.setX(rs.getDouble("x"));
                    p.setY(rs.getDouble("y"));
                    p.setR(rs.getDouble("r"));
                    p.setHit(rs.getBoolean("is_hit"));
                    p.setTime(rs.getString("time"));
                    p.setExecutionTime(rs.getLong("execution_time"));
                    p.setSessionId(rs.getString("session_id"));
                    points.add(p);
                }
            }
        } catch (SQLException ex) {
            log.error("Error in findBySessionId", ex);
        }
        return points;
    }

    @Override
    public void insert(Point point) {
        String insertSql = """
            INSERT INTO points (id, x, y, r, is_hit, time, execution_time, session_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, idGenerator.incrementAndGet());
                ps.setDouble(2, point.getX());
                ps.setDouble(3, point.getY());
                ps.setDouble(4, point.getR());
                ps.setBoolean(5, point.isHit());
                ps.setString(6, point.getTime());
                ps.setLong(7, point.getExecutionTime());
                ps.setString(8, point.getSessionId());
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException ex) {
            log.error("Error inserting point", ex);
        }
    }

    private static String getEnvOrThrow(String name) {
        String val = System.getenv(name);
        if (val == null || val.isBlank()) {
            throw new IllegalStateException("Environment variable '" + name + "' is not set");
        }
        return val;
    }
}
