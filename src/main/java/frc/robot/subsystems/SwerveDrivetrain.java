// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AbsoluteEncoder;
import frc.robot.HardwareMap;
import frc.robot.HardwareMap.SwerveHardware;

public class SwerveDrivetrain extends SubsystemBase {

  private SwerveModule m_frontLeftModule;
  private SwerveModule m_frontRightModule;
  private SwerveModule m_backLeftModule;
  private SwerveModule m_backRightModule;
  HardwareMap hardwareMap = new HardwareMap();

  /** Creates a new SwerveDrivetrain. */
  public SwerveDrivetrain(SwerveHardware swerveHardware) {
    m_frontLeftModule = swerveHardware.frontLeft;
    m_frontRightModule = swerveHardware.frontRight;
    m_backLeftModule = swerveHardware.backLeft;
    m_backRightModule = swerveHardware.backRight;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
