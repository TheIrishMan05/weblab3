package org.example.labwork3.check;

import jakarta.enterprise.context.SessionScoped;
import org.example.labwork3.models.Point;

import java.io.Serializable;

@SessionScoped
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
        return point.getX() >= -point.getR() && point.getY() <= point.getR() / 2;
    }

    private boolean checkCircle(Point point) {
        return Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2) <= Math.pow(-point.getR(), 2);
    }

    private boolean checkTriangle(Point point) {
        return point.getX() <= point.getR() && point.getY() <= -point.getR() / 2 && -0.5 * point.getX() + point.getY() <= point.getR();
    }
}
