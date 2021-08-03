// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.management.InstanceAlreadyExistsException;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.HardwareMap.ClimberHardware;
import frc.robot.commands.ClimberControllerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.ShootOneBallCommand;
import frc.robot.commands.ShooterOffCommand;
import frc.robot.commands.ShooterOnCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private XboxController m_driverController = new XboxController(0);
  private XboxController m_operatorController = new XboxController(1);
  private HardwareMap m_hardwareMap = new HardwareMap();
  private ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(m_hardwareMap.shooterHardware);
 
  private IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(m_hardwareMap.intakeHardware);
  private MoveArmCommand m_moveArmCommand = new MoveArmCommand(m_operatorController, m_intakeSubsystem);

  //Climber stuff
  private ClimberSubsystem m_climberSubsytem = new ClimberSubsystem(m_hardwareMap.climberHardware);
  private ClimberControllerCommand m_climberControllerCommand = new ClimberControllerCommand(m_climberSubsytem, m_operatorController);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_climberSubsytem.setDefaultCommand(m_climberControllerCommand);
    m_intakeSubsystem.setDefaultCommand(m_moveArmCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // turns on shooter when Left Bumper is pressed
    new JoystickButton(m_operatorController, Button.kBumperLeft.value).whenPressed(new ShooterOnCommand(m_shooterSubsystem));
    // turns off shooter when Right Bumper is pressed
    new JoystickButton(m_operatorController, Button.kBumperRight.value).whenPressed(new ShooterOffCommand(m_shooterSubsystem));
    // runs intake forwards while X is held
		new JoystickButton(m_operatorController, Button.kX.value).whenHeld(new IntakeCommand(m_intakeSubsystem));
		// runs the intake backwards while Y is heldnew ShooterOnCommand(m_shooterSubsystem)
		new JoystickButton(m_operatorController, Button.kY.value).whenHeld(new IntakeCommand(m_intakeSubsystem));
    // runs the shoot one ball command while A is held
    new JoystickButton(m_operatorController, Button.kA.value).whenHeld(new ShootOneBallCommand(m_shooterSubsystem));
    // runs the Climber backwards when A is pressed
    new JoystickButton(m_operatorController, Button.kStart.value).whenPressed(new InstantCommand(m_climberSubsytem::reverseClimb, m_climberSubsytem));
    // runs the Climber forwards when A is released
    new JoystickButton(m_operatorController, Button.kStart.value).whenReleased(new InstantCommand(m_climberSubsytem::normalClimb, m_climberSubsytem));
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