// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterOnCommand extends CommandBase {
  private ShooterSubsystem m_shooterSubsystem;
  private PIDController m_pidController; 
  /** Creates a new ShooterCommand. */
  public ShooterOnCommand(ShooterSubsystem shooterSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooterSubsystem = shooterSubsystem;
    m_pidController = new PIDController(0.5, 0, 0);
    addRequirements(m_shooterSubsystem);
  }
  Timer timer = new Timer();

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    SmartDashboard.putNumber("Current Shooter Speed", m_shooterSubsystem.getShooterSpeed());

    double currentSpeed = m_shooterSubsystem.getShooterSpeed();
    if (currentSpeed < 0.8){
      m_shooterSubsystem.setShooter(1);
    }
    
    else if (currentSpeed > 0.8) {
      m_shooterSubsystem.setShooter(0.5);
    }

    // m_pidController.setSetpoint(0.96);
    // double pidOutput = m_pidController.calculate(m_shooterSubsystem.getShooterSpeed());
    // if (pidOutput > 1) {
    //   m_shooterSubsystem.setShooter(1);
    // }
    // m_shooterSubsystem.setShooter(pidOutput);
    
    

    

    // if (timer.hasElapsed(4)) {
    //   m_shooterSubsystem.setShooter(0.8);

    // } else {
    //   m_shooterSubsystem.setShooter(0.1);
    // }
        
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