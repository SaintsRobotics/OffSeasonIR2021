package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class ResetGyroCommand extends InstantCommand{
    private SwerveDriveSubsystem m_drivetrain;

    public ResetGyroCommand(SwerveDriveSubsystem drivetrain) {
        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
    }

    @Override
    public void initialize() {
        m_drivetrain.resetGyro();
    }

}
