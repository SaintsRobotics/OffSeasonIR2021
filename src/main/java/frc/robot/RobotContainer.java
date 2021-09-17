// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AutonMoveCommand;
import frc.robot.commands.ClimberControllerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.MoveBackwardsAutonCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.ShooterOffCommand;
import frc.robot.commands.ShooterOnCommand;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.commands.TimedFeedCommand;
import frc.robot.commands.VisionAimingCommand;
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
    // The robot's subsystems and commands are defined here...

    private HardwareMap hardwareMap = new HardwareMap();

    private XboxController m_driveController = hardwareMap.inputHardware.driveController;
    private XboxController m_operatorController = hardwareMap.inputHardware.operatorController;

    private ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(hardwareMap.shooterHardware);
    private SwerveDriveSubsystem m_swerveDriveSubsystem = new SwerveDriveSubsystem(hardwareMap.swerveDriveHardware);

    private SwerveJoystickCommand m_swerveJoystickCommand = new SwerveJoystickCommand(m_swerveDriveSubsystem,
            m_driveController);

    private IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(hardwareMap.intakeHardware);
    private MoveArmCommand m_moveArmCommand = new MoveArmCommand(m_operatorController, m_intakeSubsystem);

    private ClimberSubsystem m_climberSubsystem = new ClimberSubsystem(hardwareMap.climberHardware);
    private ClimberControllerCommand m_climberControllerCommand = new ClimberControllerCommand(m_climberSubsystem,
            m_operatorController);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        m_climberSubsystem.setDefaultCommand(m_climberControllerCommand);

        m_swerveDriveSubsystem.setDefaultCommand(m_swerveJoystickCommand);

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
        // turns on Feeder and shoots ball

        new JoystickButton(m_operatorController, Button.kB.value)
                .whenPressed(new RunCommand(() -> m_shooterSubsystem.turnFeederOn(), m_shooterSubsystem))
                .whenReleased(new RunCommand(() -> m_shooterSubsystem.turnFeederOff(), m_shooterSubsystem));

        // runs intake while left trigger is held
        new Trigger(() -> m_operatorController.getTriggerAxis(Hand.kLeft) > 0.5)
                .whileActiveOnce(new IntakeCommand(m_intakeSubsystem));
        // runs outtake while right trigger is held
        new Trigger(() -> m_operatorController.getTriggerAxis(Hand.kRight) > 0.5)
                .whileActiveOnce(new OuttakeCommand(m_intakeSubsystem));

        // locks ratchet so it cannot extend, only retract (press when hooked on and
        // want to raise bot)
        // do not try and extend at this point!! (might break hardware)
        // potential to-do - add check in code so when ratchet locked cannot send signal
        // to extend
        new JoystickButton(m_operatorController, Button.kY.value)
                .whenPressed(new InstantCommand(m_climberSubsystem::lockRatchet, m_climberSubsystem));
        // release ratchet so it can extend and retract
        new JoystickButton(m_operatorController, Button.kX.value)
                .whenPressed(new InstantCommand(m_climberSubsystem::releaseRatchet, m_climberSubsystem));
        // releases the Climber when Start is pressed
        new JoystickButton(m_operatorController, Button.kStart.value)
                .whenPressed(new InstantCommand(m_climberSubsystem::releaseClimber, m_climberSubsystem));

        // resets the gyro when the Start button is pressed
        new JoystickButton(m_driveController, Button.kStart.value)
                .whenPressed(new InstantCommand(m_swerveDriveSubsystem::resetGyro, m_swerveDriveSubsystem));
        // Sets brake and coast mode with left bumper
        new JoystickButton(m_driveController, Button.kBumperLeft.value)
                .whenPressed(() -> m_swerveDriveSubsystem.setDriveIdleMode(IdleMode.kCoast))
                .whenReleased(() -> m_swerveDriveSubsystem.setDriveIdleMode(IdleMode.kBrake));
        new JoystickButton(m_driveController, Button.kA.value)
                .whileHeld(new VisionAimingCommand(m_swerveDriveSubsystem, m_driveController));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        SmartDashboard.putString("Auton test", "Auton run");

        // move off line auto
         return new MoveBackwardsAutonCommand(m_swerveDriveSubsystem).withSpeed(1).withTime(1.5).withTimeout(3);

        // three ball auto
        /*return new SequentialCommandGroup(
                new ShooterOnCommand(m_shooterSubsystem),
                new MoveBackwardsAutonCommand(m_swerveDriveSubsystem).withSpeed(1).withTime(1.2),
                new VisionAimingCommand(m_swerveDriveSubsystem, m_driveController).withTimeout(3),
                new TimedFeedCommand(m_shooterSubsystem).withTime(3),
                new ShooterOffCommand(m_shooterSubsystem)); */

    // return null;
  }
}
