// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HardwareMap;
import frc.robot.HardwareMap.ShooterHardware;

public class ShooterSubsystem extends SubsystemBase {
  private SpeedControllerGroup m_shooter;
  private CANEncoder m_canEncoder;
  private double m_targetSpeed;
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem(ShooterHardware shooter) {    
    m_shooter = shooter.shooter;
    m_canEncoder = shooter.rightCanEncoder;
  }
  public void setShooter(double speed){
    this.m_targetSpeed = speed;
  }

  public double getShooterSpeed() {
    return m_canEncoder.getVelocity();
  }

  @Override
  public void periodic() {

    // Spit out the shooter speed
    SmartDashboard.putNumber("Current Shooter Speed", getShooterSpeed());

    m_shooter.set(m_targetSpeed);
    // This method will be called once per scheduler run

  } 
}
