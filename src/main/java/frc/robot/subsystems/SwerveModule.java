// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AbsoluteEncoder;
import frc.robot.Constants;

public class SwerveModule extends SubsystemBase {

  CANSparkMax m_driveMotor, m_turningMotor;
  AbsoluteEncoder m_turningEncoder;
  PIDController m_turningPidController;
  double m_turningSpeed;
  Constants constants = new Constants();
  Translation2d m_location;

  //Stores current, real state of the wheel
  SwerveModuleState m_state;


  /** Creates a new SwerveModule. */
  public SwerveModule(CANSparkMax driveMotor, CANSparkMax turningMotor, double X, double Y, AbsoluteEncoder turningEncoder) {
    m_driveMotor = driveMotor;
    m_turningMotor = turningMotor;
    m_turningEncoder = turningEncoder;
    m_turningPidController = new PIDController(0.05, 0, 0);
    m_location = new Translation2d(X, Y);
  }

  public void setDesiredState(SwerveModuleState desiredState) {
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(m_turningEncoder.getRadians()));

    m_driveMotor.set(state.speedMetersPerSecond / constants.Drivetrain.maxMetersPerSecond);
    m_turningSpeed = m_turningPidController.calculate(m_turningEncoder.getRadians());
    m_turningMotor.set(m_turningSpeed);
  }

  public Translation2d getLocation() {
    return m_location;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
