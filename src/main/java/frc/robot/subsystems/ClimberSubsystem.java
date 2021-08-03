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

import frc.robot.HardwareMap.ClimberHardware;

public class ClimberSubsystem {
  public Servo m_servoMotor;
  public Servo m_ratchetServo;
  public CANSparkMax m_winchMotor;



  public ClimberSubsystem(ClimberHardware climberHardware) {
    m_servoMotor = climberHardware.servoMotor;
    m_ratchetServo = climberHardware.ratchetServo;
    m_winchMotor = climberHardware.winchMotor;

  }


  public double getAngle() {
    return m_servoMotor.get();
  }

  public void reverseClimb() {
      //check FRC2020, we just put in 0, previously was a var in _config
      m_ratchetServo.set(Constants.ClimberConstants.WINCH_REVERSE_SERVO_POSITION);
      DriverStation.reportError("climb direction reversed", false);
  }

  public void normalClimb() {
    //check FRC2020, we just put in 0.5, previously was a var in _config
      m_ratchetServo.set(Constants.ClimberConstants.WINCH_NORMAL_SERVO_POSITION);
      DriverStation.reportError("climb direction normal", false);
  }

  public void releaseClimber() {
      //check FRC2020, we just put in 0, previously was a var in _config
      m_servoMotor.set(Constants.ClimberConstants.SERVO_RELEASE_POSITION);
      DriverStation.reportError("climb released ", false);
  }

  public void lockServo() {
    //check FRC2020, we just put in 0.5, previously was a var in _config
    m_servoMotor.set(Constants.ClimberConstants.SERVO_RETURN_POSITION);
  }

  public void climb(double speed) {
      m_winchMotor.set(speed);
  }

  public double getSpeed() {
      return m_winchMotor.getEncoder().getVelocity();
  }
}