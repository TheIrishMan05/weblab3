package org.example.labwork3.database;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Named;
import lombok.extern.log4j.Log4j2;

import org.example.labwork3.beans.ClickIntervalBean;
import org.example.labwork3.beans.HitBean;
import org.example.labwork3.models.Point;
import org.example.labwork3.utils.MBeanRegisterUtil;

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

    private final HitBean hitMBean = new HitBean();
    private final ClickIntervalBean clickIntervalMBean = new ClickIntervalBean();
    private AtomicInteger idGenerator;

    @PostConstruct
    public void init() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            log.info("Oracle JDBC driver successfully registered");
            createTable();
            idGenerator = new AtomicInteger(getMaxIdFromDatabase());
        } catch (SQLException e) {
            log.error("Failed to register JDBC driver", e);
        }
    }


    public void init(@Observes @Initialized(SessionScoped.class) Object unused) {
        MBeanRegisterUtil.registerBean(hitMBean, "HitBean");
        MBeanRegisterUtil.registerBean(clickIntervalMBean, "ClickIntervalBean");
    }

    public void destroy(@Observes @Initialized(SessionScoped.class) Object unused){
        MBeanRegisterUtil.unregisterBean(hitMBean);
        MBeanRegisterUtil.unregisterBean(clickIntervalMBean);
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

    private void createTable() {
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
                    p.setHit(rs.getInt("is_hit") == 1);
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
                ps.setInt(5, point.isHit() ? 1 : 0);
                ps.setString(6, point.getTime());
                ps.setLong(7, point.getExecutionTime());
                ps.setString(8, point.getSessionId());
                ps.executeUpdate();
                hitMBean.updateHits(point.isHit());
                clickIntervalMBean.recordClick();
                conn.commit();
            } catch (SQLException exception) {
                log.error("Error inserting point", exception);
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException e) {
                        log.error("Error during rollback", e);
                    }
                }
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                        conn.close();
                    } catch (SQLException e) {
                        log.error("Error closing connection", e);
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("Error inserting point", ex);
        }    
    }
}
