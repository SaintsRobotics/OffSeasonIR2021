// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class MoveBackwardsAutonCommand extends CommandBase {
  private double m_xspeed = 1;
  private double m_time = 1;
  private double m_currentTime = 0;
  private SwerveDriveSubsystem m_swerveSubsystem;
  /** Creates a new MoveBackwardsAutonCommand. */
  public MoveBackwardsAutonCommand(SwerveDriveSubsystem subsystem) {
    m_swerveSubsystem = subsystem;
    addRequirements(subsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_swerveSubsystem.move(-m_xspeed, 0, 0, true);
    m_currentTime += 0.02;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_swerveSubsystem.move(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_currentTime > m_time;
  }

  public MoveBackwardsAutonCommand withSpeed(double speed) {
    m_xspeed = speed;
    return this;
  }
  public MoveBackwardsAutonCommand withTime(double time) {
    m_time = time;
    return this;
  }

}
