package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utils;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberControllerCommand extends CommandBase{
    private ClimberSubsystem m_climberSubsystem;
    private XboxController m_controller;

    public ClimberControllerCommand(ClimberSubsystem climberSubsystem, XboxController xboxController){
        m_climberSubsystem = climberSubsystem;
        m_controller = xboxController;
    }

    public void execute(){
        m_climberSubsystem.climb(Utils.deadZones(-m_controller.getY(Hand.kRight), Constants.ClimberConstants.CLIMBER_CONTROL_SPEED_DEADZONE));
    }

    public boolean isFinished(){
        return false;
    }
}