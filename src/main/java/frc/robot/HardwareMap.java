package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.SwerveModule;

public class HardwareMap {

    public class ShooterHardware{
        public CANSparkMax leftShooter = new CANSparkMax(16, MotorType.kBrushless);
        public CANSparkMax rightShooter = new CANSparkMax(17, MotorType.kBrushless);
        public SpeedControllerGroup shooter = new SpeedControllerGroup(leftShooter, rightShooter);
        public ShooterHardware(){
            leftShooter.setInverted(true);
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

    public class SwerveHardware {

        public Constants constants = new Constants();
        public double y = constants.Physical.widthInMeters; //names are a bit off
        public double x = constants.Physical.lengthInMeters; //names are a bit off
        public SwerveModuleHardware swerveModuleHardware = new SwerveModuleHardware();
        public SwerveModule frontLeft = new SwerveModule(swerveModuleHardware.frontLeftDriveMotor, swerveModuleHardware.frontLeftTurningMotor, -x/2, y/2, swerveModuleHardware.frontLeftTurningEncoder);
        public SwerveModule frontRight = new SwerveModule(swerveModuleHardware.frontRightDriveMotor, swerveModuleHardware.frontRightTurningMotor, x/2, y/2, swerveModuleHardware.frontRightTurningEncoder);
        public SwerveModule backLeft = new SwerveModule(swerveModuleHardware.backLeftDriveMotor, swerveModuleHardware.backLeftTurningMotor, -x/2, -y/2, swerveModuleHardware.backLeftTurningEncoder);
        public SwerveModule backRight = new SwerveModule(swerveModuleHardware.backRightDriveMotor, swerveModuleHardware.backRightTurningMotor, x/2, -y/2, swerveModuleHardware.backRightTurningEncoder);
        public SwerveHardware() {

        }

    }

    public ShooterHardware shooterHardware;
    public HardwareMap() {
        shooterHardware = new ShooterHardware();
    }
}