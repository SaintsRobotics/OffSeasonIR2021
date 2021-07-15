// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AbsoluteEncoder;

public class SwerveModule extends SubsystemBase {

  CANSparkMax m_driveMotor, m_turningMotor;
  AbsoluteEncoder m_turningEncoder;

  //Stores current, real state of the wheel
  SwerveModuleState m_state;


  /** Creates a new SwerveModule. */
  public SwerveModule(CANSparkMax driveMotor, CANSparkMax turningMotor, AbsoluteEncoder turningEncoder) {
    m_driveMotor = driveMotor;
    m_turningMotor = turningMotor;
    m_turningEncoder = turningEncoder;
  }

  public void setDesiredState(SwerveModuleState desiredState) {

    //SwerveModuleState state = SwerveModuleState.optimize(desiredState, m_turningEncoder.getAngle());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
