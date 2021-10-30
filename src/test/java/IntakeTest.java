import static org.junit.Assert.*;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.HardwareMap;
import frc.robot.Constants;
import org.junit.*;

public class IntakeTest {
  IntakeSubsytem intake;
  HardwareMap hardwareMap;
  public static final double DELTA = 1e-2;
  
  @Before
  public void setup() {
    hardwareMap = new HardwareMap();
    intake = new IntakeSubsytem(hardwareMap.intakeHardware);
  }

  @Test
  public void setIntakeTest() {
    intake.intake()
    assertEquals(intake.getDesiredIntakeSpeed(), Constants.IntakeConstants.INTAKE_SPEED, DELTA)
  }

  @Test
  public void clearIntakeTest() {
    intake.intake();
    intake.stopIntake();
    assertEquals(intake.getDesiredIntakeSpeed(), 0, DELTA)
  }
  
}