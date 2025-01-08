package org.example.labwork3.check;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.labwork3.models.Point;

import java.io.Serializable;

@ApplicationScoped
public class AreaChecker implements Serializable, Checker<Point> {

    @Override
    public boolean check(Point point) {
        return checkCircle(point) || checkRectangle(point) || checkTriangle(point);
    }

    @Override
    public boolean isValid(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        double MAX_X = 5;
        double MIN_X = -5;
        double MAX_Y = 5;
        double MIN_Y = -3;
        double MAX_R = 5;
        double MIN_R = 2;

        boolean validX = x >= MIN_X && x <= MAX_X;
        boolean validY = y >= MIN_Y && y <= MAX_Y;
        boolean validR = r >= MIN_R && r <= MAX_R;

        return validX || validY || validR;
    }

    private boolean checkRectangle(Point point) {
        if (point.getX() <= 0 && point.getY() >= 0) {
            return point.getX() >= -point.getR() / 2 && point.getY() <= point.getR();
        } else {
            return false;
        }
    }

    private boolean checkCircle(Point point) {
        if (point.getX() <= 0 && point.getY() <= 0) {
            return Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2) <= Math.pow(-point.getR(), 2);
        } else {
            return false;
        }
    }

    private boolean checkTriangle(Point point) {
        if (point.getX() >= 0 && point.getY() <= 0) {
            return point.getX() <= point.getR() && point.getY() >= -point.getR() && point.getX() - point.getR() >= point.getY();
        } else {
            return false;
        }
    }
}
