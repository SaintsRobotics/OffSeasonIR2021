package frc.robot;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;

public class HardwareMap {

    public class ShooterHardware{
        public CANEncoder rightCanEncoder;
        public CANSparkMax leftShooter; 
        public CANSparkMax rightShooter;
        public SpeedControllerGroup shooter; 
        public ShooterHardware() {
            leftShooter = new CANSparkMax(16, MotorType.kBrushless);
            leftShooter.setInverted(true);      
            rightShooter = new CANSparkMax(17, MotorType.kBrushless);
            rightCanEncoder = rightShooter.getEncoder();
            shooter = new SpeedControllerGroup(leftShooter, rightShooter);
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
