package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;

public class HardwareMap {

    public class ShooterHardware{
        public CANSparkMax leftShooter; 
        public CANSparkMax rightShooter;
        public SpeedControllerGroup shooter; 
        public ShooterHardware() {
            leftShooter = new CANSparkMax(16, MotorType.kBrushless);
            leftShooter.setInverted(true);      
            rightShooter = new CANSparkMax(17, MotorType.kBrushless);
            shooter = new SpeedControllerGroup(leftShooter, rightShooter);
        }        
    };

    public ShooterHardware shooterHardware;
    public HardwareMap() {
        shooterHardware = new ShooterHardware();
    }
}
