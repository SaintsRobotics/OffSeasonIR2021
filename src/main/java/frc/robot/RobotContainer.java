// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.ResetGyroCommand;
import frc.robot.commands.ShootOneBallCommand;
import frc.robot.commands.ShooterOffCommand;
import frc.robot.commands.ShooterOnCommand;

import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.commands.VisionAimingCommand;
import frc.robot.subsystems.IntakeSubsystem;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private HardwareMap hardwareMap = new HardwareMap();

  private XboxController m_driveController = hardwareMap.inputHardware.driveController;
  private XboxController m_operatorController = hardwareMap.inputHardware.operatorController;

  private ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(hardwareMap.shooterHardware);
  private SwerveDriveSubsystem m_swerveSubsystem = new SwerveDriveSubsystem(hardwareMap.swerveDriveHardware);

  private SwerveJoystickCommand m_swerveJoystickCommand = new SwerveJoystickCommand(m_swerveSubsystem,
      m_driveController);

  private IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(hardwareMap.intakeHardware);
  private MoveArmCommand m_moveArmCommand = new MoveArmCommand(m_operatorController, m_intakeSubsystem);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_swerveSubsystem.setDefaultCommand(m_swerveJoystickCommand);

    m_intakeSubsystem.setDefaultCommand(m_moveArmCommand);

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // turns on shooter when Left Bumper is pressed
    new JoystickButton(m_operatorController, Button.kBumperLeft.value)
        .whenPressed(new ShooterOnCommand(m_shooterSubsystem));
    // turns off shooter when Right Bumper is pressed
    new JoystickButton(m_operatorController, Button.kBumperRight.value)
        .whenPressed(new ShooterOffCommand(m_shooterSubsystem));
    // runs intake forwards while X is held
    new JoystickButton(m_operatorController, Button.kX.value).whenHeld(new IntakeCommand(m_intakeSubsystem));
    // runs the intake backwards while Y is heldnew
    // ShooterOnCommand(m_shooterSubsystem)
    new JoystickButton(m_operatorController, Button.kY.value).whenHeld(new IntakeCommand(m_intakeSubsystem));
    // runs the shoot one ball command while A is held
    new JoystickButton(m_operatorController, Button.kA.value).whenHeld(new ShootOneBallCommand(m_shooterSubsystem));

    new JoystickButton(m_driveController, Button.kStart.value).whenPressed(new ResetGyroCommand(m_swerveSubsystem));

    new JoystickButton(m_driveController, Button.kBumperRight.value).whenPressed(new VisionAimingCommand(m_swerveSubsystem, m_driveController));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}