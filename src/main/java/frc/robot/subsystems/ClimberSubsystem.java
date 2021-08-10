// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;


import frc.robot.Constants;

//test

//import edu.wpi.first.wpilibj.interfaces.Gyro;
/*
import frc.robot.commands.DriveArmCommand;
import frc.robot.common.IClimbSubsystem;
import frc.robot.common.ILogger;
import frc.robot.common.TraceableSubsystem;
import com.google.inject.Inject;
*/
import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HardwareMap.ClimberHardware;

public class ClimberSubsystem extends SubsystemBase{
  public Servo m_servoMotor;
  public Servo m_ratchetServo;
  public CANSparkMax m_winchMotor;



  public ClimberSubsystem(ClimberHardware climberHardware) {
    m_servoMotor = climberHardware.servoMotor;
    m_ratchetServo = climberHardware.ratchetServo;
    m_winchMotor = climberHardware.winchMotor;

  }

/**
 * The Angle of the Servo that releases the climber
 * @return
 */
  public double getAngle() {
    return m_servoMotor.get();
  }
/**
 * Sets the ratchet servo so that when the winch drives it drives backward (The Climber mechanism moves up)
 */
  public void reverseClimb() {
      m_ratchetServo.set(Constants.ClimberConstants.WINCH_REVERSE_SERVO_POSITION);
      SmartDashboard.putString("Ratchet State", "reverse");
      DriverStation.reportError("climb direction reversed", false);
  }
  /**
   * Sets the ratchet servo so that when the winch drives it drives forward (The Climber mechanism moves down)
   */
  public void normalClimb() {
      m_ratchetServo.set(Constants.ClimberConstants.WINCH_NORMAL_SERVO_POSITION);
      SmartDashboard.putString("Ratchet State", "normal");
      DriverStation.reportError("climb direction normal", false);
  }
/**
 * Moves the Servo to release the Climber mechanism
 */
  public void releaseClimber() {
      m_servoMotor.set(Constants.ClimberConstants.SERVO_RELEASE_POSITION);
      SmartDashboard.putString("ClimberState", "Released");
      DriverStation.reportError("climb released ", false);
  }
/**
 * Moves the servo to the position where the robot would be secured down
 */
  public void lockServo() {
    m_servoMotor.set(Constants.ClimberConstants.SERVO_RETURN_POSITION);
  }
/**
 * The speed of the winch
 * @param speed Can only input negative values between 0 and -1 pulls the robot up
 */
  public void climb(double speed) {
      m_winchMotor.set(speed);
  }
/**
 * 
 * @return The speed of the winch motor
 */
  public double getSpeed() {
      return m_winchMotor.getEncoder().getVelocity();
  }
}