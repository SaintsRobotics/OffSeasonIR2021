import static org.junit.Assert.assertEquals;
import frc.robot.Utils;

import org.junit.*;

public class UtilityTest {
    public static final double DELTA = 1e-2; // acceptable deviation range
    @Test
    public void deadZonesTest() {
        double deadZonedResultOne = Utils.deadZones(10, 15);
        double deadZonedExpectedOne = 0;
        assertEquals(deadZonedExpectedOne, deadZonedResultOne, DELTA);
        double deadZonedResultTwo = Utils.deadZones(10, 7);
        double deadZonedExpectedTwo = 10;
        assertEquals(deadZonedExpectedTwo, deadZonedResultTwo, DELTA);
    }

    @Test
    public void toleranceTest() {
        double toleranceResultOne = Utils.tolerance(0.5, 1, 1);
        double toleranceExpectedOne = 1;
        assertEquals(toleranceExpectedOne, toleranceResultOne, DELTA);
    }

    @Test
    public void oddSquareTest() {

    }

    @Test 
    public void normalizeAngleTest() {

    }

}
