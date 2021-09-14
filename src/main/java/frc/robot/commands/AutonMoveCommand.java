// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

/**
 * Command to move the robot autonomously either with absolute or relative
 * values.
 */
public class AutonMoveCommand extends CommandBase {

  // member vars
  private SwerveDriveSubsystem m_swerveDriveSubsystem;
  double m_x;
  double m_y;
  double m_rot;
  boolean m_fieldRelative;

  // Pid
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
    m_rotPID = new PIDController(-0.7, 0, 0);

    m_xPID.setTolerance(0.05);
    m_yPID.setTolerance(0.05);
    m_rotPID.setTolerance(Math.PI / 24);
    m_rotPID.enableContinuousInput(-Math.PI, Math.PI);
  }

  @Override
  public void initialize() {
    m_x = SwerveDriveSubsystem.getPose2D().getTranslation().getX();
    m_y = SwerveDriveSubsystem.getPose2D().getTranslation().getY();
    m_rot = SwerveDriveSubsystem.getPose2D().getRotation().getRadians();
  }

  @Override
  public void execute() {
    Pose2d currPos = SwerveDriveSubsystem.getPose2D();

    m_swerveDriveSubsystem.move(m_xPID.calculate(currPos.getX()), m_yPID.calculate(currPos.getY()),
        m_rotPID.calculate(currPos.getRotation().getRadians()), true);
  }

  @Override
  public void end(boolean interrupted) {
    m_swerveDriveSubsystem.move(0, 0, 0, true);
  }

  @Override
  public boolean isFinished() {
    return m_xPID.atSetpoint() && m_yPID.atSetpoint() && m_rotPID.atSetpoint();
  }

  /**
   * Updates the X position with an absolute value.
   * 
   * @param x The new absolute X position (in meters).
   * @return This command, for method chaining.
   */
  public AutonMoveCommand withX(double x) {
    m_xPID.setSetpoint(x);
    return this;
  }

  /**
   * Updates the Y position with an absolute value.
   * 
   * @param y The new absolute Y position (in meters).
   * @return This command, for method chaining.
   */
  public AutonMoveCommand withY(double y) {
    m_yPID.setSetpoint(y);
    return this;
  }

  /**
   * Updates the rotation with an absolute value.
   * 
   * @param rot The new absolute rotation (in radians).
   * @return This command, for method chaining.
   */
  public AutonMoveCommand withRot(double rot) {
    m_rotPID.setSetpoint(rot);
    return this;
  }

  /**
   * Updates the X position with a relative value.
   * 
   * @param x X position to add (in meters).
   * @return This command, for method chaining.
   */
  public AutonMoveCommand withRelativeX(double x) {
    m_xPID.setSetpoint(m_x + x);
    return this;
  }

  /**
   * Updates the Y position with a relative value.
   * 
   * @param y Y position to add (in meters).
   * @return This command, for method chaining.
   */
  public AutonMoveCommand withRelativeY(double y) {
    m_yPID.setSetpoint(m_y + y);
    return this;
  }

  /**
   * Updates the rotation with a relative value.
   * 
   * @param rot Rotation to add (in radians).
   * @return This command, for method chaining.
   */
  public AutonMoveCommand withRelativeRot(double rot) {
    m_rotPID.setSetpoint(m_rot + rot);
    return this;
  }
}
