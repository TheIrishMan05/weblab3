package org.example.labwork3.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
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
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Named("pointBean")
@SessionScoped
public class PointDAO implements Serializable {
    private double x = 0;
    private double y = 0;
    private double r = 2;
    private List<Point> points;
    private Point point;
    private LocalDateTime time;
    private String sessionId;
    @Inject
    AreaChecker checker;

    @Inject
    PointService service;

    @PostConstruct
    public void init() {
        HttpSession session = getSession();
        if(session != null) {
            sessionId = session.getId();
        }
        points = service.findBySessionId(sessionId);
        Collections.reverse(points);
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
            PrimeFaces.current().executeScript("drawPoint(" + point.getX() + ", " + point.getY() + ", " + point.getR() + ")");
            this.addPoint(point);
        }
    }

    private void addPoint(Point point) {
        service.insert(point);
        points.add(0, point);
        this.point = point;
    }


    private HttpSession getSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            return (HttpSession) context.getExternalContext().getSession(true);
        } else {
            return null;
        }
    }




}
