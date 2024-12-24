package org.example.labwork3.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.extern.log4j.Log4j2;
import org.example.labwork3.models.Point;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@Log4j2
@ApplicationScoped
public class PointService implements Repository<Point>, Serializable {

    static {
        log.info("Добро пожаловать! Сейчас будет создана таблица для точек.");
        createTable();
        log.info("Таблица создана! Можно начать работу.");
    }

    @Override
    public List<Point> findBySessionId(String sessionId) {
        List<Point> points = new ArrayList<>();
        Connection connection = DatabaseManager.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            String selectByIdQuery = String.format("SELECT * FROM points WHERE session_id = %s", sessionId);
            try(ResultSet resultSet = statement.executeQuery(selectByIdQuery)) {
                while (resultSet.next()) {
                    Point point = new Point();
                    point.setId(resultSet.getInt("id"));
                    point.setX(resultSet.getDouble("x"));
                    point.setY(resultSet.getDouble("y"));
                    point.setR(resultSet.getDouble("r"));
                    point.setHit(resultSet.getBoolean("is_hit"));
                    point.setTime(resultSet.getString("time"));
                    point.setExecutionTime(resultSet.getLong("execution_time"));
                    point.setSessionId(resultSet.getString("session_id"));
                    points.add(point);
                }
            }
        } catch (SQLException exception) {
            log.error(exception);
        }
        return points;
    }

    @Override
    public void insert(Point point) {
        Connection connection = null;
        try {
            connection = DatabaseManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                String insertQuery = String.format("INSERT INTO points (x, y, r, is_hit, time, execution_time, session_id) VALUES (%f, %f, %f, %d, %s, %d, %s)",
                        point.getX(), point.getY(), point.getR(),
                        point.isHit() ? 1 : 0, point.getTime(), point.getExecutionTime(), point.getSessionId());
                statement.execute(insertQuery);
            }
            connection.commit();
        } catch (SQLException exception) {
            log.error(exception);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
        }
    }

    private static void createTable() {
        Connection connection = DatabaseManager.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            String createQuery = """
                       CREATE TABLE points (
                       id NUMBER PRIMARY KEY,
                       x NUMBER NOT NULL,
                       y NUMBER NOT NULL,
                       r NUMBER NOT NULL,
                       is_hit CHAR(1) NOT NULL CHECK(is_hit in (0, 1)),
                       time VARCHAR(255) NOT NULL,
                       execution_time NUMBER NOT NULL,
                       session_id VARCHAR(50))
                    """;
            statement.execute(createQuery);
        } catch (SQLException exception) {
            log.error(exception);
        }
    }
}
