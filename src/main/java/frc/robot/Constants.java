/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class ShooterConstants {
        public static final int LEFT_FLYWHEEL_PORT = 16;
        public static final int RIGHT_FLYWHEEL_PORT = 17;

        public static final int KICKER_PORT = 26;
        public static final int WHEELS_PORT = 27;

        // TODO Might need a better, more descriptive name.
        // TODO write a comment explaining the distinction between these two values
        public static final double FLYWHEEL_RPM_THRESHOLD = 4500;
        public static final double FLYWHEEL_READY_RPM = 4500;
        // TODO Subject to tuning
    }

    public static final class IntakeConstants {
        // Speed [-1, 1] at which to run the intake motor.
        public static final double INTAKE_SPEED = 0.5;

        public static final int WHEELS_PORT = 25;
        public static final int ARM_PORT = 24;
    }

    public final class SwerveConstants {
        public static final double MAX_METERS_PER_SECOND = 3.627;
        public static final double MAX_RADIANS_PER_SECOND = 8.76;

        /**
         * X offset from the center of rotation.
         */
        public static final double SWERVE_X = .67 / 2;

        /**
         * Y offset from the center of rotation.
         */
        public static final double SWERVE_Y = .25;

        public static final double TRANSLATIONAL_FRICTION = 0.0205;

        public static final double FRONT_LEFT_ROTATION_OFFSET = 2.75 - (Math.PI / 5);
        public static final double FRONT_RIGHT_ROTATION_OFFSET = 2.573;
        public static final double BACK_LEFT_ROTATION_OFFSET = -6.091199;
        public static final double BACK_RIGHT_ROTATION_OFFSET = 3.9;
    }

}
