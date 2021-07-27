// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

/** Command to shoot one ball. */
public class ShootOneBallCommand extends CommandBase {
  private ShooterSubsystem m_shooterSubsystem;

  /**
   * Creates a new {@link ShootOneBallCommand}.
   * 
   * @param shooter Shooter subsystem that the command will run on.
   */
  public ShootOneBallCommand(ShooterSubsystem shooter) {
    m_shooterSubsystem = shooter;
    addRequirements(m_shooterSubsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if (m_shooterSubsystem.getFlywheelRPM() >= 0.96) {
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
