// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
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
  private SwerveDriveOdometry m_odometry;
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
    m_odometry = new SwerveDriveOdometry(m_kinematics, m_gyro.getRotation2d());

    m_headingPidController = new PIDController(0.3, 0, 0);
    m_headingPidController.enableContinuousInput(-Math.PI, Math.PI);
    m_headingPidController.setSetpoint(0);
    // TODO right now, the heading PID controller is in degrees. do we want to
    // switch to radians?

    this.resetGyro();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Heading correction
    
    if (m_isTurning) { // if should be turning
      m_headingPidController.setSetpoint(Math.toRadians(m_gyro.getAngle()));
      SmartDashboard.putString("heading correction", "setting setpoint");
    }
    if (!m_isTurning && (m_xSpeed != 0 || m_ySpeed != 0)) { // if translating only (want heading correction)
      SmartDashboard.putString("heading correction", "correcting heading");
      m_rotationSpeed = m_headingPidController.calculate(Utils.normalizeAngle(Math.toRadians(m_gyro.getAngle()), 2 * Math.PI));
    }
    else {
      SmartDashboard.putString("heading correction", "not correcting heading, not translating");
    }

    SmartDashboard.putNumber("gyro angle ", m_gyro.getRotation2d().getRadians());
    SmartDashboard.putNumber("gyro rate ",
        Utils.deadZones(m_gyro.getRate(), Constants.SwerveConstants.GYRO_RATE_DEADZONE));
    SmartDashboard.putNumber("rotation speed ", m_rotationSpeed);
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

    // Update odometry
    m_odometry.update(m_gyro.getRotation2d(), m_frontLeftModule.getState(), m_frontRightModule.getState(),
        m_backLeftModule.getState(), m_backRightModule.getState());

    SmartDashboard.putNumber("OdometryX", m_odometry.getPoseMeters().getX());
    SmartDashboard.putNumber("OdometryY", m_odometry.getPoseMeters().getY());
    SmartDashboard.putNumber("Odometryrot", m_odometry.getPoseMeters().getRotation().getDegrees());

    SmartDashboard.putBoolean("is turning ", m_isTurning);
    SmartDashboard.putNumber("heading pid error ", m_headingPidController.getPositionError());
    SmartDashboard.putNumber("heading pid output ",
        m_headingPidController.calculate(Utils.normalizeAngle(m_gyro.getAngle(), 360)));
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
    m_isTurning = rotationSpeed != 0;
  }

  /**
   * Resets the gyro. Note that whatever command calls this method <b>will require
   * the subsystem</b>. So no other commands can run on the subsystem while the
   * gyro is being reset, including any drive control commands.
   */
  public void resetGyro() {
    m_gyro.reset();
    // Important to reset heading pid setpoint when resetting gyro.
    m_headingPidController.setSetpoint(0);
  }

  /**
   * Zeroes odometry coordinates.
   */
  public void resetOdometry() {
    m_odometry.resetPosition(new Pose2d(), new Rotation2d());
  }

  /**
   * Sets odometry values to the given position and rotation.
   */
  public void resetOdometry(Pose2d position, Rotation2d rotation) {
    m_odometry.resetPosition(position, rotation);
  }

  /**
   * Uses odometry to calculate the position of the robot.
   * 
   * @return The x and y position (aka coordinates) of the robot, in meters.
   */
  public Pose2d getPose2d() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Sets each of the swerve modules' drive motors to the specified idle state.
   * 
   * @param mode Brake or coast.
   */
  public void setDriveIdleMode(IdleMode mode) {
    m_frontLeftModule.getDriveMotor().setIdleMode(mode);
    m_frontRightModule.getDriveMotor().setIdleMode(mode);
    m_backLeftModule.getDriveMotor().setIdleMode(mode);
    m_backRightModule.getDriveMotor().setIdleMode(mode);
  }
}
