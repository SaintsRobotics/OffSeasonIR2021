package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.subsystems.SwerveModule;

public class HardwareMap {

    // either construct each subclass seperately in robotcontainer,
    // OR create fields for each subclass in the constructor for hardwaremap
    public class ShooterHardware {
        public CANEncoder rightCanEncoder;
        public CANSparkMax leftShooter;
        public CANSparkMax rightShooter;
        public SpeedControllerGroup shooter;
        public WPI_VictorSPX wheels;
        public WPI_VictorSPX kicker;
        public SpeedControllerGroup feeder;

        public ShooterHardware() {
            leftShooter = new CANSparkMax(16, MotorType.kBrushless);
            leftShooter.setInverted(true);
            rightShooter = new CANSparkMax(17, MotorType.kBrushless);
            rightCanEncoder = rightShooter.getEncoder();
            shooter = new SpeedControllerGroup(leftShooter, rightShooter);

            kicker = new WPI_VictorSPX(26);
            wheels = new WPI_VictorSPX(27);
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

    public class SwerveHardware {

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
        public SwerveModule frontLeftSwerveModule;
        public SwerveModule frontRightSwerveModule;
        public SwerveModule backLeftSwerveModule;
        public SwerveModule backRightSwerveModule;

        public SwerveHardware() {
            frontLeftDriveMotor = new CANSparkMax(DriveConstants.FRONT_LEFT_DRIVE_MOTOR_PORT, MotorType.kBrushless);
            frontRightDriveMotor = new CANSparkMax(DriveConstants.FRONT_RIGHT_DRIVE_MOTOR_PORT, MotorType.kBrushless);
            backLeftDriveMotor = new CANSparkMax(DriveConstants.REAR_LEFT_DRIVE_MOTOR_PORT, MotorType.kBrushless);
            backRightDriveMotor = new CANSparkMax(DriveConstants.REAR_RIGHT_DRIVE_MOTOR_PORT, MotorType.kBrushless);

            frontLeftTurningMotor = new CANSparkMax(DriveConstants.FRONT_LEFT_TURNING_MOTOR_PORT, MotorType.kBrushless);
            frontLeftTurningMotor.setIdleMode(IdleMode.kCoast);
            frontRightTurningMotor = new CANSparkMax(DriveConstants.FRONT_RIGHT_TURNING_MOTOR_PORT,
                    MotorType.kBrushless);
            frontRightTurningMotor.setIdleMode(IdleMode.kCoast);
            backLeftTurningMotor = new CANSparkMax(DriveConstants.REAR_LEFT_TURNING_MOTOR_PORT, MotorType.kBrushless);
            backLeftTurningMotor.setIdleMode(IdleMode.kCoast);
            backRightTurningMotor = new CANSparkMax(DriveConstants.REAR_RIGHT_TURNING_MOTOR_PORT, MotorType.kBrushless);
            backRightTurningMotor.setIdleMode(IdleMode.kCoast);

            frontLeftTurningEncoder = new AbsoluteEncoder(DriveConstants.FRONT_LEFT_TURNING_ENCODER_PORT, true,
                    ModuleConstants.FRONT_LEFT_ROTATION_OFFSET);
            frontRightTurningEncoder = new AbsoluteEncoder(DriveConstants.FRONT_RIGHT_TURNING_ENCODER_PORT, true,
                    ModuleConstants.FRONT_RIGHT_ROTATION_OFFSET);
            backLeftTurningEncoder = new AbsoluteEncoder(DriveConstants.REAR_LEFT_TURNING_ENCODER_PORT, true,
                    ModuleConstants.REAR_LEFT_ROTATION_OFFSET);
            backRightTurningEncoder = new AbsoluteEncoder(DriveConstants.REAR_RIGHT_TURNING_ENCODER_PORT, true,
                    ModuleConstants.REAR_RIGHT_ROTATION_OFFSET);

            // Robot is facing towards positive x direction
            frontLeftSwerveModule = new SwerveModule("Front Left Swerve Module", frontLeftDriveMotor,
                    frontLeftTurningMotor, ModuleConstants.TRACK_WIDTH / 2, ModuleConstants.WHEEL_BASE,
                    frontLeftTurningEncoder);
            frontRightSwerveModule = new SwerveModule("Front Right Swerve Module", frontRightDriveMotor,
                    frontRightTurningMotor, ModuleConstants.TRACK_WIDTH, -ModuleConstants.WHEEL_BASE,
                    frontRightTurningEncoder);
            backLeftSwerveModule = new SwerveModule("Back Left Swerve Module", backLeftDriveMotor, backLeftTurningMotor,
                    -ModuleConstants.TRACK_WIDTH, ModuleConstants.WHEEL_BASE, backLeftTurningEncoder);
            backRightSwerveModule = new SwerveModule("Back Right Swerve Module", backRightDriveMotor,
                    backRightTurningMotor, -ModuleConstants.TRACK_WIDTH, -ModuleConstants.WHEEL_BASE,
                    backRightTurningEncoder);

        }
    }

    public ShooterHardware shooterHardware;
    public IntakeHardware intakeHardware;

    public HardwareMap() {
        shooterHardware = new ShooterHardware();
        intakeHardware = new IntakeHardware();
    }

}
