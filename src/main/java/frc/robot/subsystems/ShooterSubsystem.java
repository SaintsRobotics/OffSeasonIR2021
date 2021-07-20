// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HardwareMap;
import frc.robot.Constants.ShooterConstants;
import frc.robot.HardwareMap.ShooterHardware;

public class ShooterSubsystem extends SubsystemBase {
  private SpeedControllerGroup m_shooter;
  private double m_targetSpeed;
  private double m_feederSpeed;
  private WPI_VictorSPX m_wheels;
  private WPI_VictorSPX m_kicker;
  private SpeedControllerGroup m_feeder;
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem(ShooterHardware shooter) {    
    m_shooter = shooter.shooter;
    m_kicker = new WPI_VictorSPX(ShooterConstants.FEEDER_PORT); 
    m_wheels = new WPI_VictorSPX(ShooterConstants.SPINNER_PORT);
    m_wheels.setInverted(true);

    m_feeder = new SpeedControllerGroup(m_kicker, m_wheels);

  }
  public void setShooter(double speed){
    this.m_targetSpeed = speed;
  }

  public double getShooterSpeed() {
    return m_shooter.get();
  }

  public void turnFeederOn() {
    m_feederSpeed = 1;
  }

  public void turnFeederOff() {
    m_feederSpeed = 0;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("shooter speed", m_targetSpeed);
    m_shooter.set(m_targetSpeed);
    // This method will be called once per scheduler run

  } 
}
