// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

/** Command to shoot one ball. */
public class TimedFeedCommand extends CommandBase {
  private ShooterSubsystem m_shooterSubsystem;
  private double m_currentTime = 0;
  private double m_targetTime = 1;

  /**
   * Creates a new {@link TimedFeedCommand}.
   * 
   * @param shooter Shooter subsystem that the command will run on.
   */
  public TimedFeedCommand(ShooterSubsystem shooter) {
    m_shooterSubsystem = shooter;
    addRequirements(m_shooterSubsystem);
  }

  public TimedFeedCommand withTime (double time) {
    m_targetTime = time;
    return this;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if (m_shooterSubsystem.getFlywheelRPM() >= 0.96) {
      m_shooterSubsystem.turnFeederOn();
    }
    m_currentTime += 0.02;
  }

  @Override
  public void end(boolean interrupted) {
    m_shooterSubsystem.turnFeederOff();
  }

  @Override
  public boolean isFinished() {
    return m_currentTime >= m_targetTime;
  }
}
