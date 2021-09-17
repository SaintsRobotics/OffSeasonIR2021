// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DeleteSub extends SubsystemBase {
  double m_angle;
  double m_speed;
  boolean m_isOn;
  /** Creates a new DeleteSub. */
  public DeleteSub(double angle, double speed, boolean isOn) {
    this.m_angle = angle;
    this.m_speed = speed;
    this.m_isOn = isOn;
  }

  @Override
  public void periodic() {
	// This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake Angle", this.m_angle);
    SmartDashboard.putNumber("Intake Speed", this.m_speed);
    SmartDashboard.putBoolean("Intake Power Switch", this.m_isOn);
  }
}
