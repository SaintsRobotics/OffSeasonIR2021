// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.HardwareMap.SwerveDriveHardware;

public class SwerveDriveSubsystem extends SubsystemBase {

  private SwerveModule m_frontLeftModule;
  private SwerveModule m_frontRightModule;
  private SwerveModule m_backLeftModule;
  private SwerveModule m_backRightModule;
  private AHRS m_gyro;

  private double m_xSpeed;
  private double m_ySpeed;
  private double m_rotationSpeed;
  private boolean m_isFieldRelative;

  private ChassisSpeeds m_chassisSpeeds;
  private SwerveDriveKinematics m_kinematics;
  private PIDController m_headingPidController;

  /**
   * Determined by the gyro. Signifies whether or not the robot is
   * <b>physically</b> rotating, aka changing heading
   */
  private boolean m_isTurning;

  /** Creates a new SwerveDrivetrain. */
  public SwerveDriveSubsystem(SwerveDriveHardware swerveHardware) {
    m_frontLeftModule = swerveHardware.frontLeft;
    m_frontRightModule = swerveHardware.frontRight;
    m_backLeftModule = swerveHardware.backLeft;
    m_backRightModule = swerveHardware.backRight;
    m_gyro = swerveHardware.gyro;

    m_kinematics = new SwerveDriveKinematics(m_frontLeftModule.getLocation(), m_frontRightModule.getLocation(),
        m_backLeftModule.getLocation(), m_backRightModule.getLocation());

    m_headingPidController = new PIDController(Constants.SwerveConstants.HEADING_PID_P,
        Constants.SwerveConstants.HEADING_PID_I, Constants.SwerveConstants.HEADING_PID_D);
    m_headingPidController.enableContinuousInput(-180, 180);
    // TODO right now, the heading PID controller is in degrees. do we want to
    // switch to radians?
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Heading correction
//     if (m_rotationSpeed != 0) {
//       m_headingPidController.setSetpoint(m_gyro.getAngle());
//       SmartDashboard.putString("heading correction", "not correcting heading");
//     } 
//     if (m_rotationSpeed == 0 && (m_xSpeed != 0 || m_ySpeed != 0)) {
//       SmartDashboard.putString("heading correction", "correcting heading");
//       m_rotationSpeed = m_headingPidController.calculate(Utils.normalizeAngle(m_gyro.getAngle(), 360));
//     }
//     else {
//       SmartDashboard.putString("heading correction", "not correcting heading, not translating");
//     }

    SmartDashboard.putNumber("gyro angle ", Utils.normalizeAngle(m_gyro.getAngle(), 360));
    SmartDashboard.putNumber("gyro rate ", Utils.deadZones(m_gyro.getRate(), Constants.SwerveConstants.GYRO_RATE_DEADZONE));
    SmartDashboard.putNumber("heading pid output ", m_headingPidController.calculate(Utils.normalizeAngle(m_gyro.getAngle(), 360)));
    SmartDashboard.putNumber("heading pid setpoint ", m_headingPidController.getSetpoint());

    // TODO somehow account for static friction, I think?

    // Create chassis speeds object.
    // Convert chassis speeds from field-relative speeds to robot-relative speeds,
    // if needed.
    if (m_isFieldRelative) {
      m_chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(m_xSpeed, m_ySpeed, m_rotationSpeed,
          m_gyro.getRotation2d());
    } else {
      m_chassisSpeeds = new ChassisSpeeds(m_xSpeed, m_ySpeed, m_rotationSpeed);
    }

    // toSwerveModuleState array create it from kinematics
    SwerveModuleState[] swerveModuleStates;
    swerveModuleStates = m_kinematics.toSwerveModuleStates(m_chassisSpeeds);

    // Normalizing wheel speeds so that the maximum possible speed isn't exceeded.
    SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates,
        Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND);

    if (m_chassisSpeeds.vxMetersPerSecond == 0 && m_chassisSpeeds.vyMetersPerSecond == 0
        && m_chassisSpeeds.omegaRadiansPerSecond == 0) {
      // Only stops swerve module's wheel from driving, preserves module's heading.
      m_frontLeftModule.setDesiredState();
      m_frontRightModule.setDesiredState();
      m_backLeftModule.setDesiredState();
      m_backRightModule.setDesiredState();
    } else {
      // I assume swerve module states are given in the same order that the wheels are
      // given to the kinematics object.
      m_frontLeftModule.setDesiredState(swerveModuleStates[0]);
      m_frontRightModule.setDesiredState(swerveModuleStates[1]);
      m_backLeftModule.setDesiredState(swerveModuleStates[2]);
      m_backRightModule.setDesiredState(swerveModuleStates[3]);
    }
    SmartDashboard.putBoolean("is turning ", m_isTurning);
    SmartDashboard.putNumber("heading pid error ", m_headingPidController.getPositionError());
    SmartDashboard.putNumber("heading pid output ", m_headingPidController.calculate(Utils.normalizeAngle(m_gyro.getAngle(), 360)));
  }

  /**
   * 
   * @param xSpeed          Represents forward velocity w.r.t the robot frame of
   *                        reference. Meters per second, forward is positive
   * @param ySpeed          Represents sideways velocity w.r.t the robot frame of
   *                        reference. Meters per second, <b>left is positive</b>
   * @param rotationSpeed   Represents the angular velocity of the robot frame.
   *                        Radians per second, <b>counterclockwise is
   *                        positive</b>
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

  /**
   * Resets the gyro.
   * Aka, sets the current heading of the robot to zero.
   * TODO make sure this also updates odometry, <i>if needed</i>.
   */
  public void resetGyro() {
    m_gyro.reset();
  }
}
