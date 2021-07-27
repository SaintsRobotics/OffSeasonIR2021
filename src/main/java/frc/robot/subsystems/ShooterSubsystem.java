// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HardwareMap.ShooterHardware;

public class ShooterSubsystem extends SubsystemBase {
  private SpeedControllerGroup m_shooter;
  private SpeedControllerGroup m_feeder;
  private CANEncoder m_shooterSpeedEncoder;
  private double m_targetSpeed;
  private double m_feederSpeed;
  

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem(ShooterHardware shooter) {    
    m_shooter = shooter.shooter;
    m_feeder = shooter.feeder;
    m_shooterSpeedEncoder = shooter.rightCanEncoder;
    

  }
  public void setShooter(double speed){
    this.m_targetSpeed = speed;
  }

  public double getShooterSpeed() {
    return m_shooterSpeedEncoder.getVelocity();
  }

  public void turnFeederOn() {
    m_feederSpeed = 1;
  }

  public void turnFeederOff() {
    m_feederSpeed = 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Spit out the shooter speed
    SmartDashboard.putNumber("Current Shooter Speed", getShooterSpeed());
    SmartDashboard.putNumber("Feeder Speed", m_feederSpeed);
    m_shooter.set(m_targetSpeed);
    m_feeder.set(m_feederSpeed);

  } 
}
