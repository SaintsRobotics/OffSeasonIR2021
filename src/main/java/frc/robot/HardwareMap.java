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
            frontLeftDriveMotor = new CANSparkMax(Constants.SwervePorts.FRONT_LEFT_DRIVE_MOTOR_PORT, MotorType.kBrushless);
            frontRightDriveMotor = new CANSparkMax(Constants.SwervePorts.FRONT_RIGHT_DRIVE_MOTOR_PORT, MotorType.kBrushless);
            backLeftDriveMotor = new CANSparkMax(Constants.SwervePorts.REAR_LEFT_DRIVE_MOTOR_PORT, MotorType.kBrushless);
            backRightDriveMotor = new CANSparkMax(Constants.SwervePorts.REAR_RIGHT_DRIVE_MOTOR_PORT, MotorType.kBrushless);

            frontLeftTurningMotor = new CANSparkMax(Constants.SwervePorts.FRONT_LEFT_TURNING_MOTOR_PORT, MotorType.kBrushless);
            frontRightTurningMotor = new CANSparkMax(Constants.SwervePorts.FRONT_RIGHT_TURNING_MOTOR_PORT, MotorType.kBrushless);
            backLeftTurningMotor = new CANSparkMax(Constants.SwervePorts.REAR_LEFT_TURNING_MOTOR_PORT, MotorType.kBrushless);
            backRightTurningMotor = new CANSparkMax(Constants.SwervePorts.REAR_RIGHT_TURNING_MOTOR_PORT, MotorType.kBrushless);

            frontLeftTurningEncoder = new AbsoluteEncoder(Constants.SwervePorts.FRONT_LEFT_TURNING_ENCODER_PORT, true,
                    Constants.SwerveConstants.FRONT_LEFT_ROTATION_OFFSET);
            frontRightTurningEncoder = new AbsoluteEncoder(Constants.SwervePorts.FRONT_RIGHT_TURNING_ENCODER_PORT, true,
                    Constants.SwerveConstants.FRONT_RIGHT_ROTATION_OFFSET);
            backLeftTurningEncoder = new AbsoluteEncoder(Constants.SwervePorts.REAR_LEFT_TURNING_ENCODER_PORT, true, Constants.SwerveConstants.BACK_LEFT_ROTATION_OFFSET);
            backRightTurningEncoder = new AbsoluteEncoder(Constants.SwervePorts.REAR_RIGHT_TURNING_ENCODER_PORT, true,
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