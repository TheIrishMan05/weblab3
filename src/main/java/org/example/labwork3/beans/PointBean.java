package org.example.labwork3.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.example.labwork3.check.AreaChecker;
import org.example.labwork3.database.PointService;
import org.example.labwork3.models.Point;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Named
@ViewScoped
public class PointBean implements Serializable {
    @Inject
    AreaChecker checker;
    @Inject
    PointService service;
    private double x = 0;
    private double y = 0;
    private double r = 2;
    private List<Point> points;
    private Point point;
    private LocalDateTime time;
    private String sessionId;

    @PostConstruct
    public void init() {
        HttpSession session = getCurrentSession();
        if (session != null) {
            sessionId = session.getId();
        }
        points = service.findBySessionId(sessionId);
        if (points == null) {
            points = new ArrayList<>();
        } else {
            Collections.reverse(points);
        }
    }

    public void checkPoint() {
        long time = System.currentTimeMillis();
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        point.setSessionId(sessionId);
        if (checker.isValid(point)) {
            point.setHit(checker.check(point));
            point.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            point.setExecutionTime(System.currentTimeMillis() - time);
            PrimeFaces.current().executeScript(String.format("drawPoint(%f, %f, %f)", point.getX(), point.getY(), point.getR()));
            this.addPoint(point);
        }
    }

    public void checkPointByImageClick(){
        long time = System.currentTimeMillis();
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        point.setSessionId(sessionId);
        if (checker.isValid(point)) {
            point.setHit(checker.check(point));
            point.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            point.setExecutionTime(System.currentTimeMillis() - time);
            this.addPoint(point);
        }
    }

    private void addPoint(Point point) {
        service.insert(point);
        points.add(0, point);
        this.point = point;
    }


    private HttpSession getCurrentSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            return (HttpSession) context.getExternalContext().getSession(true);
        } else {
            return null;
        }
    }


}
