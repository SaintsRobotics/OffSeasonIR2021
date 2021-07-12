// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  
  private VictorSPX intakeController;
  private VictorSPX armController;
  private double desiredSpeed;

  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    // fill this in based on hardwaremap
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake MotorSpeed", desiredSpeed);
    intakeController.set(VictorSPXControlMode.PercentOutput, desiredSpeed);
  }

  public void moveArm(double speed) {
    armController.set(VictorSPXControlMode.PercentOutput, speed);
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
