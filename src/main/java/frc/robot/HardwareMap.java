package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
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

    public class ShooterHardware{
        public CANSparkMax leftShooter; 
        public CANSparkMax rightShooter;
        public SpeedControllerGroup shooter; 
        public ShooterHardware(){
            leftShooter = new CANSparkMax(16, MotorType.kBrushless);
            leftShooter.setInverted(true);
            rightShooter = new CANSparkMax(17, MotorType.kBrushless);
            shooter = new SpeedControllerGroup(leftShooter, rightShooter);            
        }
    }

    public class SwerveModuleHardware {
        public CANSparkMax frontLeftDriveMotor = new CANSparkMax(8, MotorType.kBrushless);
        public CANSparkMax frontRightDriveMotor = new CANSparkMax(4, MotorType.kBrushless);
        public CANSparkMax backLeftDriveMotor = new CANSparkMax(2, MotorType.kBrushless);
        public CANSparkMax backRightDriveMotor = new CANSparkMax(6, MotorType.kBrushless);

        public CANSparkMax frontLeftTurningMotor = new CANSparkMax(1, MotorType.kBrushless);
        public CANSparkMax frontRightTurningMotor = new CANSparkMax(5, MotorType.kBrushless);
        public CANSparkMax backLeftTurningMotor = new CANSparkMax(3, MotorType.kBrushless);
        public CANSparkMax backRightTurningMotor = new CANSparkMax(7, MotorType.kBrushless);

        public AbsoluteEncoder frontLeftTurningEncoder = new AbsoluteEncoder(0, 2.254991, true);
        public AbsoluteEncoder frontRightTurningEncoder = new AbsoluteEncoder(1, 2.466641, true);
        public AbsoluteEncoder backLeftTurningEncoder = new AbsoluteEncoder(3, .279, true);
        public AbsoluteEncoder backRightTurningEncoder = new AbsoluteEncoder(2, 3.925, true);

        public SwerveModuleHardware() {

        }
    }

    public class SwerveDriveHardware {

        public Constants constants = new Constants();       
        public double x = Constants.ModuleConstants.WHEEL_BASE/2; 
        public double y = Constants.ModuleConstants.TRACK_WIDTH/2; 
        public SwerveModuleHardware swerveModuleHardware = new SwerveModuleHardware();
        public SwerveModule frontLeft = new SwerveModule(swerveModuleHardware.frontLeftDriveMotor, swerveModuleHardware.frontLeftTurningMotor, x, y, swerveModuleHardware.frontLeftTurningEncoder);
        public SwerveModule frontRight = new SwerveModule(swerveModuleHardware.frontRightDriveMotor, swerveModuleHardware.frontRightTurningMotor, x, -y, swerveModuleHardware.frontRightTurningEncoder);
        public SwerveModule backLeft = new SwerveModule(swerveModuleHardware.backLeftDriveMotor, swerveModuleHardware.backLeftTurningMotor, -x, y, swerveModuleHardware.backLeftTurningEncoder);
        public SwerveModule backRight = new SwerveModule(swerveModuleHardware.backRightDriveMotor, swerveModuleHardware.backRightTurningMotor, -x, -y, swerveModuleHardware.backRightTurningEncoder);
        
        public SwerveDriveHardware() {

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