// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.HardwareMap.IntakeHardware;

public class IntakeSubsystem extends SubsystemBase {
  
  private VictorSPX m_intakeController;
  private VictorSPX m_armController;
  private double desiredSpeed;

  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem(IntakeHardware intake) {
    // fill this in based on hardwaremap
    m_intakeController = intake.intakeController;
    m_armController = intake.armController;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake MotorSpeed", desiredSpeed);
    m_intakeController.set(VictorSPXControlMode.PercentOutput, desiredSpeed);
  }

  public void moveArm(double speed) {
    m_armController.set(VictorSPXControlMode.PercentOutput, speed);
  }

  public void intake() {
    desiredSpeed = Constants.intakeSpeed;
  }

  public void stopIntake() {
    desiredSpeed = 0;
  }

  public void outtake() {
    desiredSpeed = -Constants.intakeSpeed;
  }
}
