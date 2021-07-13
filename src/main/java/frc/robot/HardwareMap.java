package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;

public class HardwareMap {

    public class ShooterHardware{
        public CANSparkMax leftShooter = new CANSparkMax(16, MotorType.kBrushless);
        public CANSparkMax rightShooter = new CANSparkMax(17, MotorType.kBrushless);
        public SpeedControllerGroup shooter = new SpeedControllerGroup(leftShooter, rightShooter);
        public ShooterHardware(){
            leftShooter.setInverted(true);
        }
    };

    public ShooterHardware shooterHardware;
    public HardwareMap() {
        shooterHardware = new ShooterHardware();
    }
}