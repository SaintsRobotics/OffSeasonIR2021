// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.HardwareMap;
import frc.robot.HardwareMap.SwerveDriveHardware;

public class SwerveDriveSubsystem extends SubsystemBase {

  private SwerveModule m_frontLeftModule;
  private SwerveModule m_frontRightModule;
  private SwerveModule m_backLeftModule;
  private SwerveModule m_backRightModule;

  private double m_xSpeed;
  private double m_ySpeed;
  private double m_rotationSpeed;
  private boolean m_isFieldRelative;

  private ChassisSpeeds m_chassisSpeeds;
  private SwerveDriveKinematics m_kinematics;

  HardwareMap hardwareMap = new HardwareMap();

  /** Creates a new SwerveDrivetrain. */
  public SwerveDriveSubsystem(SwerveDriveHardware swerveHardware) {
    m_frontLeftModule = swerveHardware.frontLeft;
    m_frontRightModule = swerveHardware.frontRight;
    m_backLeftModule = swerveHardware.backLeft;
    m_backRightModule = swerveHardware.backRight;

    m_kinematics = new SwerveDriveKinematics(m_frontLeftModule.getLocation(), m_frontRightModule.getLocation(),
        m_backLeftModule.getLocation(), m_backRightModule.getLocation());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Create chassis speeds object.
    // Convert chassis speeds from field-relative speeds to robot-relative speeds,
    // if needed.
    if (m_isFieldRelative) {
      m_chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(m_xSpeed, m_ySpeed, m_rotationSpeed, robotAngle);
      // TODO implement gyro!! code won't build witout fixing this lol
    } else {
      m_chassisSpeeds = new ChassisSpeeds(m_xSpeed, m_ySpeed, m_rotationSpeed);
    }

    // toSwerveModuleState array create it from kinematics
    SwerveModuleState[] swerveModuleStates;
    swerveModuleStates = m_kinematics.toSwerveModuleStates(m_chassisSpeeds);

    // Normalizing wheel speeds so that the maximum possible speed isn't exceeded.
    SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates,
        Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND);

    m_frontLeftModule.setDesiredState(swerveModuleStates[0]);
    m_frontRightModule.setDesiredState(swerveModuleStates[1]);
    m_backLeftModule.setDesiredState(swerveModuleStates[2]);
    m_backRightModule.setDesiredState(swerveModuleStates[3]);
  }

  /**
   * 
   * @param xSpeed          Represents forward velocity w.r.t the robot frame of
   *                        reference. Meters per second, forward is positive
   * @param ySpeed          Represents sideways velocity w.r.t the robot frame of
   *                        reference. Meters per second, left is positive
   * @param rotationSpeed   Represents the angular velocity of the robot frame.
   *                        Radians per second, counterclockwise is positive
   * @param isFieldRelative Whether or not the provided x and y values should be
   *                        considered relative to the field, or relative to the
   *                        robot.
   */
  public void move(double xSpeed, double ySpeed, double rotationSpeed, boolean isFieldRelative) {

    m_xSpeed = xSpeed;
    m_ySpeed = ySpeed;
    m_rotationSpeed = rotationSpeed;
    m_isFieldRelative = isFieldRelative;
  }
}
