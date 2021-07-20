// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public final Constants.Controller Controller = new Constants.Controller();
    public final Constants.SwerveDrivetrain Drivetrain = new Constants.SwerveDrivetrain();
    public final Constants.Physical Physical = new Constants.Physical();
    public final Constants.Intake Intake = new Constants.Intake();
    public final Constants.Shooter Shooter = new Constants.Shooter();
    public final Constants.Limelight Limelight = new Constants.Limelight();
    public final Constants.Climber Climber = new Constants.Climber();
    public final Constants.TurnToHeading turnToHeading = new Constants.TurnToHeading();

    public final class Controller {
        public final int driverControllerPort = 0;
        public final int operatorControllerPort = 1;

        public final int resetGyroButtonPort = XboxController.Button.kStart.value;
        public final int driveMotorIdleStateButtonPort = XboxController.Button.kBumperLeft.value;

        public final int shooterStartupButtonPort = XboxController.Button.kA.value;
        public final int feedBackwardButtonPort = XboxController.Button.kB.value;
        public final int feedOneBallButtonPort = XboxController.Button.kX.value;
        public final int shooterShutdownButtonPort = XboxController.Button.kY.value;
        public final int intakeInButtonPort = XboxController.Button.kBumperRight.value;
        public final int intakeOutButtonPort = XboxController.Button.kBumperLeft.value;

        public final int shooterUpperPresetButton = 0;
        public final int shooterLowerPresetButton = 180;

        public final int visionTrackButtonPort = XboxController.Button.kB.value;

        public final int fieldWestButtonAngle = 270;
        public final int fieldEastButtonAngle = 90;
        public final int fieldNorthButtonAngle = 0;
        public final int fieldSouthButtonAngle = 180;
        public final double trenchSideGeneratorSwitchAngle = 247.51;
        public final int generatorSwitchHeadingButtonPort = XboxController.Button.kX.value;

        public final double kDriveDeadzone = 0.2;
        public final double kDriveScale = 1;
        public final double kTurnDeadzone = 0.2;
        public final double kTurnScale = 1;

        public final int climberReleaseButtonPort = XboxController.Button.kBack.value;
        public final int climbDirectionSwitchButtonPort = XboxController.Button.kStart.value;
    }

    public final class SwerveDrivetrain {
        public final double maxMetersPerSecond = 3.66;
        public final double maxRadiansPerSecond = 8.76;
        public final int frontLeftDriveMotorPort = 8;
        public final int frontLeftTurnMotorPort = 1;
        public final int rearLeftDriveMotorPort = 2;
        public final int rearLeftTurnMotorPort = 3;
        public final int frontRightDriveMotorPort = 4;
        public final int frontRightTurnMotorPort = 5;
        public final int rearRightDriveMotorPort = 6;
        public final int rearRightturnMotorPort = 7;
        public final int frontLeftAbsoluteEncoder = 0;
        public final int frontRightAbsoluteEncoder = 1;
        public final int rearLeftAbsoluteEncoder = 3;
        public final int rearRightAbsoluteEncoder = 2;

        public final double gyroRateDeadzone = 0.05;
    }

    public final class Physical {
        /*
         * When using these values for the swerve drive, make sure to divide them by two
         * if the pivot point of the bot is in the center.
         * 
         * Note that standard convention is the bot is centered on the origin, facing
         * right, along the positive x-axis. So the top-left portion of the bot is in
         * the first quadrant, the back-left is in the second quadrant, the back-right
         * is in the third quadrant, and the back-right is in the fourth quadrant.
         */

        // Note: these are the distances of the swerve wheels to each other, not the
        // size of the bot's frame.
        public final double widthInMeters = .67;
        public final double lengthInMeters = .5;
        public final double weightInKgs = 0; // TODO this isn't the real weight!!!
        public final double staticFrictionConstant = .0205;

    }

    public final class Intake {
        public final int intakeControllerPort = 25;
        public final int armControllerPort = 24;

        public final double armLowerSetpoint = .305;
        public final double armInnerSetpoint = .115;

    }

    public final class Climber {
        public final int winchPort = 19;
        public final int releaseServoPort = 1;
        public final double servoReleasePosition = 0;
        public final double servoReturnPosition = 0.5;

        public final int directionServoPort = 0;
        public final double winchNormalServoPosition = 0.5;
        public final double winchReverseServoPosition = 0;

        public final double servoMaxPWM = 2.5;
        public final double servoMaxDeadband = 0;
        public final double servoCenterPWM = 0;
        public final double servoDeadbandMin = 0;
        public final double servoMinPWM = 0.5;

        public final double matchTimeForEndgame = 30;
    }

    public final class Shooter {
        public final int feederPort = 26;
        public final int spinnerPort = 27;
        public final int leftShooterPort = 16;
        public final int rightShooterPort = 17;
        public final int beamBrakeSensorPort = 51;
        // This is the scaled voltage that the motor is set to when overshooting the
        // desired RPM

        public final int stallLimit = 30;
        public final int freeLimit = 60;
        public final int limitRPM = 150;

        public final double shooterDefaultRPM = 0.93;
        public final double shooterUpperRPM = 0.96;
        public final double shooterLowerRPM = 0.84;

        public final double feederTimeoutSeconds = .5;

        public final int beamBrakeLimit = 60;
    }

    public final class Limelight {
        public final double kP = .008;
        public final double kI = 0.00;
        public final double kD = 0.07;
        public final double tolerance = 1.5;

        public final double angleSetpointDegrees = 0.0;
        public final double mountingAngleDegrees = 45.0;
        public final double mountingHeightMeters = 0.508;
        public final double targetHeightMeters = 2.49555; // the height of the inner circle, not the height you want to
                                                          // get to

    }

    public final class TurnToHeading {

        public final double kP = .013;

        public final double kI = 0;
        public final double kD = 0;
        public final int pidOnTargetTicksGoal = 5;
        public final double pidTolerance = 2.5;

    }


}
