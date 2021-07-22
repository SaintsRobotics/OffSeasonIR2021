// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootOneBallCommand extends CommandBase {
  /** Creates a new ShootOneBallCommand. */
  private ShooterSubsystem m_shooterSubsystem;
  
  public ShootOneBallCommand(ShooterSubsystem shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooterSubsystem = shooter;
    addRequirements(m_shooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

    
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_shooterSubsystem.getShooterSpeed() >= 0.96) {
      m_shooterSubsystem.turnFeederOn();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooterSubsystem.turnFeederOff(); 
  }

     
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
