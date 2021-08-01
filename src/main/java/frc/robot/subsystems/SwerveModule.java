// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.AbsoluteEncoder;
import frc.robot.Constants;

public class SwerveModule {

  private CANSparkMax m_driveMotor, m_turningMotor;
  private AbsoluteEncoder m_turningEncoder;
  private PIDController m_turningPidController;
  private Translation2d m_location;

  // Stores current, real state of the wheel, based on information from the
  // sensors.
  private SwerveModuleState m_state;

  /**
   * For the x and y coordinates, forward is along the x-axis. This method also
   * updates the swerve module state, accessed by getState, for odometry.
   * 
   * @param driveMotor     Drive Motor drives the wheel
   * @param turningMotor   Rotates the wheel
   * @param X              X Coordinate of the Swerve Wheel
   * @param Y              Y Coordinate of the Swerve Wheel
   * @param turningEncoder Measures the angle of the wheel
   */
  public SwerveModule(CANSparkMax driveMotor, CANSparkMax turningMotor, double X, double Y,
      AbsoluteEncoder turningEncoder) {
    m_driveMotor = driveMotor;
    m_turningMotor = turningMotor;
    m_turningEncoder = turningEncoder;
    m_turningPidController = new PIDController(Constants.SwerveConstants.MODULE_PID_P,
        Constants.SwerveConstants.MODULE_PID_I, Constants.SwerveConstants.MODULE_PID_D);
    m_turningPidController.enableContinuousInput(-Math.PI, Math.PI);
    m_location = new Translation2d(X, Y);
  }

  public void setDesiredState(SwerveModuleState desiredState) {

    desiredState = SwerveModuleState.optimize(desiredState, m_turningEncoder.getRotation2d());

    // Dividing given speed by max meters per second to fit the value within the
    // range of [-1, 1]
    m_driveMotor.set(desiredState.speedMetersPerSecond / Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND);

    m_turningPidController.setSetpoint(desiredState.angle.getRadians());
    double turningMotorPower = m_turningPidController.calculate(m_turningEncoder.getRotation2d().getRadians());
    m_turningMotor.set(turningMotorPower);
    m_turningEncoder.sendVoltage(turningMotorPower);
    this.updateSwerveModuleState();
  }

  /**
   * Overload setDesiredState method, takes in no params. This method halts the
   * swerve module, stopping both the drive and turning motors. This is so that
   * the direction that the module is facing is preserved, and the pid loop isn't
   * activated. This method also updates the swerve module state, accessed by
   * getState, for odometry.
   */
  public void setDesiredState() {
    m_turningMotor.set(0);
    m_driveMotor.set(0);

    this.updateSwerveModuleState();
  }

  /**
   * 
   * @return The location of the Swerve wheel w.r.t. the frame of the robot.
   *         Origin is the center of the robot. Robt is facing forward along the
   *         x-axis.
   */
  public Translation2d getLocation() {
    return m_location;
  }

  /**
   * @return The actual state of the module (not the necessarily the same as the
   *         desired state passed in setDesiredState), measured from encoders on
   *         the module.
   */
  public SwerveModuleState getState() {
    return m_state;
  }

  /**
   * Private method that is used in this class to update the state (heading and
   * velocity) of the swerve module. State is accessable by the getState method of
   * this class.
   */
  private void updateSwerveModuleState() {
    // TODO RIght now, drive motor velocity is in RPM. see rev CANEncoder docs to
    // configure conversion to meters per second.
    m_state = new SwerveModuleState(
        m_driveMotor.getEncoder().getVelocity() * Constants.SwerveConstants.DRIVE_WHEEL_RPM_TO_METERS_PER_SECOND,
        m_turningEncoder.getRotation2d());
  }

  /**
   * @return the wheel heading PID controller object for the wheel
   */
  public PIDController getPidController() {
    return m_turningPidController;
  }

  /**
   * @return the CANSparkMax object for the drive motor
   */
  public CANSparkMax getDriveMotor() {
    return m_driveMotor;
  }

}
