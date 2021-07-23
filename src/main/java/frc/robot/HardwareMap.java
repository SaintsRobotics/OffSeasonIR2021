package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.SwerveModule;

public class HardwareMap {

    public class InputHardware {
        public XboxController driveController;
        public XboxController operatorController;

        public InputHardware() {
            driveController = new XboxController(0);
            operatorController = new XboxController(1);
        }
    }

    public class ShooterHardware {
        public CANSparkMax leftShooter;
        public CANSparkMax rightShooter;
        public SpeedControllerGroup shooter;

        public ShooterHardware() {
            leftShooter = new CANSparkMax(16, MotorType.kBrushless);
            leftShooter.setInverted(true);
            rightShooter = new CANSparkMax(17, MotorType.kBrushless);
            shooter = new SpeedControllerGroup(leftShooter, rightShooter);
        }
    }

    private class SwerveModuleHardware {
        public CANSparkMax frontLeftDriveMotor;
        public CANSparkMax frontRightDriveMotor;
        public CANSparkMax backLeftDriveMotor;
        public CANSparkMax backRightDriveMotor;

        public CANSparkMax frontLeftTurningMotor;
        public CANSparkMax frontRightTurningMotor;
        public CANSparkMax backLeftTurningMotor;
        public CANSparkMax backRightTurningMotor;

        public AbsoluteEncoder frontLeftTurningEncoder;
        public AbsoluteEncoder frontRightTurningEncoder;
        public AbsoluteEncoder backLeftTurningEncoder;
        public AbsoluteEncoder backRightTurningEncoder;

        public SwerveModuleHardware() {
            frontLeftDriveMotor = new CANSparkMax(8, MotorType.kBrushless);
            frontRightDriveMotor = new CANSparkMax(4, MotorType.kBrushless);
            backLeftDriveMotor = new CANSparkMax(2, MotorType.kBrushless);
            backRightDriveMotor = new CANSparkMax(6, MotorType.kBrushless);

            frontLeftTurningMotor = new CANSparkMax(1, MotorType.kBrushless);
            frontRightTurningMotor = new CANSparkMax(5, MotorType.kBrushless);
            backLeftTurningMotor = new CANSparkMax(3, MotorType.kBrushless);
            backRightTurningMotor = new CANSparkMax(7, MotorType.kBrushless);

            frontLeftTurningEncoder = new AbsoluteEncoder(0, true,
                    Constants.SwerveConstants.FRONT_LEFT_ROTATION_OFFSET);
            frontRightTurningEncoder = new AbsoluteEncoder(1, true,
                    Constants.SwerveConstants.FRONT_RIGHT_ROTATION_OFFSET);
            backLeftTurningEncoder = new AbsoluteEncoder(3, true, Constants.SwerveConstants.BACK_LEFT_ROTATION_OFFSET);
            backRightTurningEncoder = new AbsoluteEncoder(2, true,
                    Constants.SwerveConstants.BACK_RIGHT_ROTATION_OFFSET);
        }
    }

    public class SwerveDriveHardware {
        private double x;
        private double y;
        private SwerveModuleHardware swerveModuleHardware;
        public SwerveModule frontLeft;
        public SwerveModule frontRight;
        public SwerveModule backLeft;
        public SwerveModule backRight;

        public AHRS gyro;

        public SwerveDriveHardware() {
            x = Constants.SwerveConstants.WHEEL_BASE / 2;
            y = Constants.SwerveConstants.TRACK_WIDTH / 2;
            swerveModuleHardware = new SwerveModuleHardware();
            frontLeft = new SwerveModule(swerveModuleHardware.frontLeftDriveMotor,
                    swerveModuleHardware.frontLeftTurningMotor, x, y, swerveModuleHardware.frontLeftTurningEncoder);
            frontRight = new SwerveModule(swerveModuleHardware.frontRightDriveMotor,
                    swerveModuleHardware.frontRightTurningMotor, x, -y, swerveModuleHardware.frontRightTurningEncoder);
            backLeft = new SwerveModule(swerveModuleHardware.backLeftDriveMotor,
                    swerveModuleHardware.backLeftTurningMotor, -x, y, swerveModuleHardware.backLeftTurningEncoder);
            backRight = new SwerveModule(swerveModuleHardware.backRightDriveMotor,
                    swerveModuleHardware.backRightTurningMotor, -x, -y, swerveModuleHardware.backRightTurningEncoder);

            gyro = new AHRS();
        }

    }

    public InputHardware inputHardware;
    public ShooterHardware shooterHardware;
    public SwerveDriveHardware swerveDriveHardware;

    public HardwareMap() {
        inputHardware = new InputHardware();
        shooterHardware = new ShooterHardware();
        swerveDriveHardware = new SwerveDriveHardware();
    }
}