// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AutonAimingCommand;
import frc.robot.commands.ClimberControllerCommand;
import frc.robot.commands.FeederCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.MoveBackwardsAutonCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.ReleaseRatchetCommand;
import frc.robot.commands.ShooterOnCommand;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.commands.TimedFeedCommand;
import frc.robot.subsystems.ClimberSubsystem;
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
        private OperatorBoard m_operatorController = hardwareMap.inputHardware.operatorBoard;

        private ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem(hardwareMap.shooterHardware);
        private SwerveDriveSubsystem m_swerveSubsystem = new SwerveDriveSubsystem(hardwareMap.swerveDriveHardware);
        private IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(hardwareMap.intakeHardware);
        private ClimberSubsystem m_climberSubsystem = new ClimberSubsystem(hardwareMap.climberHardware);

        private SwerveJoystickCommand m_swerveJoystickCommand = new SwerveJoystickCommand(m_swerveSubsystem,
                        m_driveController);
        private MoveArmCommand m_moveArmCommand = new MoveArmCommand(m_operatorController, m_intakeSubsystem);
        private Trajectory m_trajectory = new Trajectory();
        
        //private ClimberControllerCommand m_climberControllerCommand = new ClimberControllerCommand(m_climberSubsystem,
          //              m_operatorController);

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                configureButtonBindings();
                m_swerveSubsystem.setDefaultCommand(m_swerveJoystickCommand);
                m_intakeSubsystem.setDefaultCommand(m_moveArmCommand);
        
                //m_climberSubsystem.setDefaultCommand(m_climberControllerCommand);
        }

        /**
         * Use this method to define your button->command mappings. Buttons can be
         * created by instantiating a {@link GenericHID} or one of its subclasses
         * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
         * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
         */
        private void configureButtonBindings() {

                // turns on shooter when Left Bumper is pressed
                m_operatorController.startShooter.toggleWhenPressed(
                                new ShooterOnCommand(m_shooterSubsystem, m_operatorController.startShooter));

                // turns on Feeder while button is held so balls will be shot
                m_operatorController.feeder
                                .whileHeld(new FeederCommand(m_shooterSubsystem, m_operatorController.feeder));

                // runs the intake while the button is held
                m_operatorController.intakeForward
                                .whileHeld(new IntakeCommand(m_intakeSubsystem, m_operatorController.intakeForward));

                // runs the intake backwards while the button is held
                m_operatorController.intakeReverse
                                .whileHeld(new OuttakeCommand(m_intakeSubsystem, m_operatorController.intakeReverse));

                // press to release ratchet. press again to lock ratchet.
                //m_operatorController.releaseRatchet.toggleWhenPressed(
                  //              new ReleaseRatchetCommand(m_climberSubsystem, m_operatorController.releaseRatchet));

                // releases the Climber when button is pressed
                //m_operatorController.releaseClimber.whenPressed(
                  //              new InstantCommand(m_climberSubsystem::releaseClimber, m_climberSubsystem));

                // set climber servo to reseting position
                //m_operatorController.resetClimber.whenPressed(() -> m_climberSubsystem.resetClimber());

                // resets the gyro when the Start button is pressed
                new JoystickButton(m_driveController, Button.kStart.value)
                                .whenPressed(new InstantCommand(m_swerveSubsystem::resetGyro, m_swerveSubsystem));

                // Sets brake and coast mode with left bumper
                new JoystickButton(m_driveController, Button.kBumperLeft.value)
                                .whenPressed(() -> m_swerveSubsystem.setDriveIdleMode(IdleMode.kCoast))
                                .whenReleased(() -> m_swerveSubsystem.setDriveIdleMode(IdleMode.kBrake));

                new JoystickButton(m_driveController, Button.kA.value)
                                .whileHeld(new VisionAimingCommand(m_swerveSubsystem, m_driveController));

        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                SmartDashboard.putString("Auton test", "Auton run");

                // move off line auto
                // return new
                // MoveBackwardsAutonCommand(m_swerveSubsystem).withSpeed(1).withTime(1.5).withTimeout(3);

                // three ball auto
                ShooterOnCommand shooteron = new ShooterOnCommand(m_shooterSubsystem,
                        m_operatorController.startShooter);
                return new SequentialCommandGroup(new InstantCommand(() -> m_shooterSubsystem.setFlywheelPower(0.98)),
                        new MoveBackwardsAutonCommand(m_swerveSubsystem).withSpeed(1).withTime(1.2),
                                new AutonAimingCommand(m_swerveSubsystem).withTimeout(4),
                                new TimedFeedCommand(m_shooterSubsystem).withTime(3.5),
                                new InstantCommand(() -> shooteron.cancel(), m_shooterSubsystem));

                // return null;
        }

        public Command pathFollowCommand() {
                String trajectoryJSON = "paths/PathWeaver.wpilib.json";
                try {
                  Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
                  m_trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
                } catch (IOException ex) {
                  DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
                }
            
                PIDController xPID = new PIDController(Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND, 0, 0);
                PIDController yPID = new PIDController(Constants.SwerveConstants.MAX_SPEED_METERS_PER_SECOND, 0, 0);
                ProfiledPIDController rotPID = new ProfiledPIDController(-Math.PI * 6, 0.0, 0.0,
                    new TrapezoidProfile.Constraints(Constants.SwerveConstants.MAX_MODULE_ANGULAR_SPEED_RADIANS_PER_SECOND, 2.6));
                xPID.setTolerance(.05);
                yPID.setTolerance(0.05);
                rotPID.setTolerance(Math.PI / 24);
                rotPID.enableContinuousInput(-Math.PI, Math.PI);
                return new SwerveJoystickCommand(m_trajectory, m_swerveSubsystem::getCurrentPosition,
                    m_swerveSubsystem.getKinematics(), xPID, yPID, rotPID, m_swerveSubsystem::move, m_swerveSubsystem);
            
              }
}
