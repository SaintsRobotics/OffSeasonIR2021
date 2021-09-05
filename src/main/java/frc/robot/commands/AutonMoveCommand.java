// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutonMoveCommand extends CommandBase {

  // member vars
  private SwerveDriveSubsystem m_swerveDriveSubsystem;
  double m_x;
  double m_y;
  double m_rot;
  boolean m_fieldRelative;


  //Pid
  PIDController m_xPID;
  PIDController m_yPID;
  PIDController m_rotPID;
  

  public AutonMoveCommand withX(double new_x) {
    m_x = new_x;
    return this;
  }

  public AutonMoveCommand withY(double new_y) {
    m_y = new_y;
    return this;
  }

  public AutonMoveCommand withRot(double new_rot) {
    m_rot = new_rot;
    return this;
  }

  public AutonMoveCommand changeX(double new_x) {
    m_x += new_x;
    return this;
  }

  public AutonMoveCommand changeY(double new_y) {
    m_y += new_y;
    return this;
  }

  public AutonMoveCommand changeRot(double new_rot) {
    m_rot += new_rot;
    return this;
  }


  /** Creates a new AutonMoveCommand. */
  public AutonMoveCommand(SwerveDriveSubsystem swerveDriveSubsystem) {
    m_x = SwerveDriveSubsystem.getPose2D().getTranslation().getX();
    m_y = SwerveDriveSubsystem.getPose2D().getTranslation().getY();
    m_rot = SwerveDriveSubsystem.getPose2D().getRotation().getRadians();
    m_fieldRelative = true;

    m_xPID = new PIDController(0.5, 0, 0);
    m_yPID = new PIDController(0.5, 0, 0);
    m_rotPID = new PIDController(0.5, 0, 0);

    m_swerveDriveSubsystem = swerveDriveSubsystem;
    addRequirements(m_swerveDriveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_xPID.setSetpoint(m_x);
    m_yPID.setSetpoint(m_y);
    m_rotPID.setSetpoint(m_rot);
    Pose2d currPos = SwerveDriveSubsystem.getPose2D();
    
    m_swerveDriveSubsystem.move(m_xPID.calculate(currPos.getX()), m_yPID.calculate(currPos.getY()), m_rotPID.calculate(currPos.getRotation().getRadians()), m_fieldRelative);
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
