// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

/** Creates a new {@link ShootOneBallCommand}. */
public class ShootOneBallCommand extends CommandBase {
  private ShooterSubsystem m_shooterSubsystem;

  public ShootOneBallCommand(ShooterSubsystem shooter) {
    m_shooterSubsystem = shooter;
    addRequirements(m_shooterSubsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if (m_shooterSubsystem.getShooterSpeed() >= 0.96) {
      m_shooterSubsystem.turnFeederOn();
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_shooterSubsystem.turnFeederOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
