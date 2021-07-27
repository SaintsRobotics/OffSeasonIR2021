package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class HardwareMap {

    // either construct each subclass seperately in robotcontainer,
    // OR create fields for each subclass in the constructor for hardwaremap
    public class ShooterHardware {
        public CANEncoder rightCanEncoder;
        private CANSparkMax leftFlywheel;
        private CANSparkMax rightFlywheel;
        public SpeedControllerGroup flywheel;
        private WPI_VictorSPX wheels;
        private WPI_VictorSPX kicker;
        public SpeedControllerGroup feeder;

        public ShooterHardware() {
            leftFlywheel = new CANSparkMax(Constants.ShooterConstants.LEFT_FLYWHEEL_PORT, MotorType.kBrushless);
            leftFlywheel.setInverted(true);
            rightFlywheel = new CANSparkMax(Constants.ShooterConstants.RIGHT_FLYWHEEL_PORT, MotorType.kBrushless);
            rightCanEncoder = rightFlywheel.getEncoder();
            flywheel = new SpeedControllerGroup(leftFlywheel, rightFlywheel);

            kicker = new WPI_VictorSPX(Constants.ShooterConstants.KICKER_PORT);
            wheels = new WPI_VictorSPX(Constants.ShooterConstants.WHEELS_PORT);
            wheels.setInverted(true);
            feeder = new SpeedControllerGroup(kicker, wheels);
        }
    };

    public class IntakeHardware {
        public VictorSPX intakeController;
        public VictorSPX armController;

        public IntakeHardware() {
            intakeController = new VictorSPX(25);
            armController = new VictorSPX(24);

        }
    };

    public ShooterHardware shooterHardware;
    public IntakeHardware intakeHardware;

    public HardwareMap() {
        shooterHardware = new ShooterHardware();
        intakeHardware = new IntakeHardware();
    }

}
