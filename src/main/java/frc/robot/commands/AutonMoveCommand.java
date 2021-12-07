// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutonMoveCommand extends CommandBase {
  /** Creates a new AutonMoveCommand. */
  private SwerveDriveSubsystem m_drivetrain;
  private double m_x;
  private double m_y;
  private PIDController m_pidX;
  private PIDController m_pidY;

  public AutonMoveCommand(SwerveDriveSubsystem drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = drivetrain;
    addRequirements(m_drivetrain);
    m_pidX = new PIDController(0.2, 0, 0);
    m_pidY = new PIDController(0.2, 0, 0);
  }

  public AutonMoveCommand withTargetX(double x) {
    m_x = x;
    return this;
  }

  public AutonMoveCommand withTargetY(double y) {
    m_y = y;
    return this;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_pidX.setTolerance(0.05);
    m_pidY.setTolerance(0.05);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSpeed = m_pidX.calculate(m_drivetrain.m_pose.getX(), m_x);
    double ySpeed = m_pidY.calculate(m_drivetrain.m_pose.getY(), m_y);
    m_drivetrain.move(xSpeed, ySpeed, 0, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.move(0, 0, 0, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_pidX.atSetpoint() && m_pidY.atSetpoint();
  }
}
