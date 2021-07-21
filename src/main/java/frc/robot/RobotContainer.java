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
import frc.robot.commands.ShooterOffCommand;
import frc.robot.commands.ShooterOnCommand;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private HardwareMap hardwareMap = new HardwareMap();

  private XboxController m_driveController = hardwareMap.inputHardware.driveController;
  private XboxController m_operatorController = hardwareMap.inputHardware.operatorController;

  private ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(hardwareMap.shooterHardware);
  private SwerveDriveSubsystem m_swerveSubsystem = new SwerveDriveSubsystem(hardwareMap.swerveDriveHardware);

  private ShooterOnCommand m_shooterOnCommand = new ShooterOnCommand(m_shooterSubsystem);
  private ShooterOffCommand m_shooterOffCommand = new ShooterOffCommand(m_shooterSubsystem);
  private SwerveJoystickCommand m_swerveJoystickCommand = new SwerveJoystickCommand(m_swerveSubsystem, m_driveController);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings    
    configureButtonBindings();
    m_swerveSubsystem.setDefaultCommand(m_swerveJoystickCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // turns on shooter when A is pressed
    new JoystickButton(m_operatorController, Button.kA.value).whenPressed(m_shooterOnCommand);

    // turns off shooter when B is pressed
    new JoystickButton(m_operatorController, Button.kB.value).whenPressed(m_shooterOffCommand);
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
