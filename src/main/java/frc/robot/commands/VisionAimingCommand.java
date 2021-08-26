// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Limelight;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class VisionAimingCommand extends CommandBase {
  private final PIDController m_pid = new PIDController(0.5, 0, 0);
  private final SwerveDriveSubsystem m_drive; // Trying out final and seeing if it works
  
  /** Creates a new {@link VisionAimingCommand}. */
  public VisionAimingCommand(SwerveDriveSubsystem subsystem) {
    m_drive = subsystem;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
    m_pid.reset();
    Limelight.setLed(3);
    m_pid.setSetpoint(0.0); // 0.0 means the limelight is pointed at the right direction

  }

  @Override
  public void execute() {
    m_drive.move(0, 0, -m_pid.calculate(Limelight.getX()), false);
  }

  @Override
  public void end(boolean interrupted) {
    m_drive.move(0, 0, 0, false);
    Limelight.setLed(1);
  } 
}
