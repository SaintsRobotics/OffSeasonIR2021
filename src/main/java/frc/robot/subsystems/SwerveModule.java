// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.AbsoluteEncoder;
import frc.robot.Constants;

public class SwerveModule {

  private CANSparkMax m_driveMotor, m_turningMotor;
  private AbsoluteEncoder m_turningEncoder;
  private PIDController m_turningPidController;
  private double m_turningSpeed;
  private Translation2d m_location;

  //Stores current, real state of the wheel, based on information from the sensors.
  SwerveModuleState m_state;

 /**
  * For the x and y coordinates, forward is along the x-axis.
  * @param driveMotor     Drive Motor drives the wheel
  * @param turningMotor   Rotates the wheel
  * @param X              X Coordinate of the Swerve Wheel
  * @param Y              Y Coordinate of the Swerve Wheel
  * @param turningEncoder Measures the angle of the wheel
  */
  public SwerveModule(CANSparkMax driveMotor, CANSparkMax turningMotor, double X, double Y, AbsoluteEncoder turningEncoder) {
    m_driveMotor = driveMotor;
    m_turningMotor = turningMotor;
    m_turningEncoder = turningEncoder;
    m_turningPidController = new PIDController(0.05, 0, 0);
    m_location = new Translation2d(X, Y);
  }

  public void setDesiredState(SwerveModuleState desiredState) {

    // We assumed/guessed that optimize method uses radians for encoder position.
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(m_turningEncoder.getAngle().getRadians()));

    m_driveMotor.set(state.speedMetersPerSecond / Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND);
    m_turningPidController.setSetpoint(state.angle.getRadians());
    m_turningSpeed = m_turningPidController.calculate(m_turningEncoder.getAngle().getRadians());
    m_turningMotor.set(m_turningSpeed);

    // TODO RIght now, drive motor velocity is in RPM.  see rev CANEncoder docs to configure conversion to meters per second.
    m_state = new SwerveModuleState(m_driveMotor.getEncoder().getVelocity(), new Rotation2d(m_turningEncoder.getAngle().getRadians()));
  }

  /**
   * 
   * @return The location of the Swerve wheel. Origin is the center of the robot. Robt is facing forward along the x-axis.
   */
  public Translation2d getLocation() {
    return m_location;
  }
}
