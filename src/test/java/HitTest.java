import org.example.labwork3.check.AreaChecker;
import org.example.labwork3.models.Point;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class HitTest {
    private final AreaChecker checker = new AreaChecker();
    private final Point point = new Point();
    @Test
    public void hitInRectangle() {
        point.setX(-0.25);
        point.setY(0.5);
        point.setR(2);
        assertTrue(checker.check(point));
    }

    @Test
    public void hitInCircle() {
        point.setX(-1);
        point.setY(-1.5);
        point.setR(2);
        assertTrue(checker.check(point));
    }

    @Test
    public void hitInTriangle(){
        point.setX(2);
        point.setY(-1);
        point.setR(3);
        assertTrue(checker.check(point));
    }

    @Test
    public void hitOutOfPlot() {
        point.setX(0.5);
        point.setY(0.5);
        point.setR(3);
        assertFalse(checker.check(point));
    }

    @Test
    public void hitOutOfRectangle() {
        point.setX(-2);
        point.setY(2);
        point.setR(2);
        assertFalse(checker.check(point));
    }
    
    @Test
    public void hitOutOfCircle() {
        point.setX(-2);
        point.setY(-1);
        point.setR(2);
        assertFalse(checker.check(point));
    }

    @Test
    public void hitOutOfTriangle() {
        point.setX(1);
        point.setY(-2);
        point.setR(2);
        assertFalse(checker.check(point));
    }

}
