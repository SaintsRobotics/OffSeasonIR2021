// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Limelight;

public class LimelightOutput extends CommandBase {
  /** Creates a new LimelightOutput. */
  public LimelightOutput() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Y Offset", Limelight.getY());
    SmartDashboard.putNumber("X Offset", Limelight.getX());
    SmartDashboard.putNumber("Skew", Limelight.getAngle());
    SmartDashboard.putNumber("Latency ", Limelight.getLatency());
    SmartDashboard.putNumber("Target Area", Limelight.getArea());
    SmartDashboard.putBoolean("Has Target", Limelight.hasTarget());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
