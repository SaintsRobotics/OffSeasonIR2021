// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class SwerveJoystickCommand extends CommandBase {
  private SwerveDriveSubsystem m_drivetrain;
  private XboxController m_controller;

  /** Creates a new SwerveJoystickCommand. */
  public SwerveJoystickCommand(SwerveDriveSubsystem drivetrain, XboxController controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    m_drivetrain = drivetrain;
    m_controller = controller;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Just think really hard about why these values are negated and flip-flopped.
    // Maybe use a whiteboard or piece of paper.
    double x = Utils.oddSquare(Utils.deadZones(-m_controller.getY(Hand.kLeft), 0.2))
        * Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND * 0.2;
    double y = Utils.oddSquare(Utils.deadZones(-m_controller.getX(Hand.kLeft), 0.2))
        * Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND * 0.2;
    double rot = Utils.oddSquare(Utils.deadZones(-m_controller.getX(Hand.kRight), 0.2))
        * Constants.SwerveConstants.MAX_MODULE_ANGULAR_SPEED_RADIANS_PER_SECOND * 0.2;
    boolean fieldRelative = m_controller.getBumper(Hand.kRight);


    // if (m_controller.getY(Hand.kLeft) > 0.5) {
    //   x = 0.2;
    // }
    // if (m_controller.getY(Hand.kLeft) < -0.5) {
    //   x = -0.2;
    // }
    m_drivetrain.move(x, y, rot, fieldRelative);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.move(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
