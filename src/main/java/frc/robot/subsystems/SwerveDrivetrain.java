// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AbsoluteEncoder;
import frc.robot.HardwareMap;
import frc.robot.HardwareMap.SwerveHardware;

public class SwerveDrivetrain extends SubsystemBase {

  private SwerveModule m_frontLeftModule;
  private SwerveModule m_frontRightModule;
  private SwerveModule m_backLeftModule;
  private SwerveModule m_backRightModule;

  private double m_xSpeed;
  private double m_ySpeed;
  private double m_rotation;
  private boolean m_isFieldRelative;

  private ChassisSpeeds m_chassisSpeeds;
  private SwerveDriveKinematics m_kinematics;

  HardwareMap hardwareMap = new HardwareMap();

  /** Creates a new SwerveDrivetrain. */
  public SwerveDrivetrain(SwerveHardware swerveHardware) {
    m_frontLeftModule = swerveHardware.frontLeft;
    m_frontRightModule = swerveHardware.frontRight;
    m_backLeftModule = swerveHardware.backLeft;
    m_backRightModule = swerveHardware.backRight;

    m_kinematics = new SwerveDriveKinematics();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Create chassisSpeed object
    m_chassisSpeeds = new ChassisSpeeds(m_xSpeed, m_ySpeed, m_rotation);

    //toSwerveModuleState array create it from kinematics
    SwerveModuleState[] swerveModuleStates;
  

    /*m_frontLeftModule.setDesiredState(swerveModuleStates[]);
    m_frontRightModule.setDesiredState(swerveModuleStates[]);
    m_backLeftModule.setDesiredState(swerveModuleStates[]);
    m_backRightModule.setDesiredState(swerveModuleStates[]);*/


  }

  public void move(double xSpeed, double ySpeed, double rotation, boolean isFieldRelative) {

    m_xSpeed = xSpeed;
    m_ySpeed = ySpeed;
    m_rotation = rotation;
    m_isFieldRelative = isFieldRelative;



  }
}
