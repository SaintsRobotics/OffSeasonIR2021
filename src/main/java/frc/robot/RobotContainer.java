// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimberControllerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.ShooterOffCommand;
import frc.robot.commands.ShooterOnCommand;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.subsystems.ClimberSubsystem;
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
    private HardwareMap hardwareMap = new HardwareMap();

    private XboxController m_driveController = hardwareMap.inputHardware.driveController;
    private XboxController m_operatorController = hardwareMap.inputHardware.operatorController;

    private SwerveDriveSubsystem m_swerveSubsystem = new SwerveDriveSubsystem(hardwareMap.swerveDriveHardware);
    private SwerveJoystickCommand m_swerveJoystickCommand = new SwerveJoystickCommand(m_swerveSubsystem,
            m_driveController);

    private IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(hardwareMap.intakeHardware);
    private MoveArmCommand m_moveArmCommand = new MoveArmCommand(m_operatorController, m_intakeSubsystem);

    private ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(hardwareMap.shooterHardware);

    private ClimberSubsystem m_climberSubsystem = new ClimberSubsystem(hardwareMap.climberHardware);
    private ClimberControllerCommand m_climberControllerCommand = new ClimberControllerCommand(m_climberSubsystem,
            m_operatorController);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureButtonBindings();

        m_swerveSubsystem.setDefaultCommand(m_swerveJoystickCommand);
        m_intakeSubsystem.setDefaultCommand(m_moveArmCommand);
        m_climberSubsystem.setDefaultCommand(m_climberControllerCommand);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // resets the gyro when the Start button is pressed on the drive controller
        new JoystickButton(m_driveController, XboxController.Button.kStart.value)
                .whenPressed(new InstantCommand(m_swerveSubsystem::resetGyro, m_swerveSubsystem));

        // wheels are set to coast when the left bumper is pressed otherwise they are
        // set to brake
        new JoystickButton(m_driveController, XboxController.Button.kBumperLeft.value)
                .whenPressed(() -> m_swerveSubsystem.setDriveIdleMode(IdleMode.kCoast))
                .whenReleased(() -> m_swerveSubsystem.setDriveIdleMode(IdleMode.kBrake));

        // runs the intake while the left trigger is held on the operator controller
        new Button(() -> m_operatorController.getTriggerAxis(Hand.kLeft) > 0.5)
                .whileHeld(new IntakeCommand(m_intakeSubsystem));

        // runs the outtake while the right trigger is held
        new Button(() -> m_operatorController.getTriggerAxis(Hand.kRight) > 0.5)
                .whileHeld(new OuttakeCommand(m_intakeSubsystem));

        // turns on the feeder when the B button is pressed on the operator controller
        new JoystickButton(m_operatorController, XboxController.Button.kB.value)
                .whenPressed(new RunCommand(() -> m_shooterSubsystem.turnFeederOn(), m_shooterSubsystem))
                .whenReleased(new RunCommand(() -> m_shooterSubsystem.turnFeederOff(), m_shooterSubsystem));

        // turns on the shooter when the left bumper is pressed on the operator
        // controller
        new JoystickButton(m_operatorController, XboxController.Button.kBumperLeft.value)
                .whenPressed(new ShooterOnCommand(m_shooterSubsystem));

        // turns off the shooter when the right bumper is pressed on the operator
        // controller
        new JoystickButton(m_operatorController, XboxController.Button.kBumperRight.value)
                .whenPressed(new ShooterOffCommand(m_shooterSubsystem));

        // locks the ratchet when the Y button is pressed on the operator controller
        // DO NOT EXTEND CLIMBER WHEN LOCKED, MIGHT BREAK HARDWARE
        new JoystickButton(m_operatorController, XboxController.Button.kY.value)
                .whenPressed(new InstantCommand(m_climberSubsystem::lockRatchet, m_climberSubsystem));

        // releases the ratchet when the X button is pressed on the operator controller
        new JoystickButton(m_operatorController, XboxController.Button.kX.value)
                .whenPressed(new InstantCommand(m_climberSubsystem::releaseRatchet, m_climberSubsystem));

        // releases the climber when start is pressed on the operator controller
        new JoystickButton(m_operatorController, XboxController.Button.kStart.value)
                .whenPressed(new InstantCommand(m_climberSubsystem::releaseClimber, m_climberSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return null;
    }
}
