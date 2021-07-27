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
    public class ShooterHardware{
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
    
    public class IntakeHardware{
        public VictorSPX intakeController;
        public VictorSPX armController;
        public IntakeHardware(){
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
