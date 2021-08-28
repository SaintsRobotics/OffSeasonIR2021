/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class TargetAimCommand extends CommandBase {
  private SwerveDriveSubsystem m_drivetrain;
  private final double ACCEPTABLE_EXPECTED_SCORE = 0.95;
  private double currentExpectedScore;

  /**
   * Creates a new TargetAimCommand.
   */
  public TargetAimCommand(SwerveDriveSubsystem drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = drivetrain;
    addRequirements(m_drivetrain);
    currentExpectedScore = 0;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  /**
   * Called every time the scheduler runs while the command is scheduled.
   * 
   * This code is designed to send and recieve packets from the python file that
   * is constantly predicting the best place to go to.
   */
  @Override
  public void execute() {

    try {
      //declare the transfer file
      File f = new File("transfer.txt");

      //create a scanner for it
      Scanner input = new Scanner(f);

      //Get the move amounts
      double deltaX = input.nextDouble();
      double deltaY = input.nextDouble();
      
      //Translate the drivetrain

    } catch (Exception e) {
      System.out.println("\nTRANSFER FAILED\n");
    }

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
