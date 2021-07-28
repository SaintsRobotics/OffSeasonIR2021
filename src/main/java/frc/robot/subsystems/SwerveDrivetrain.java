// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AbsoluteEncoder;
import frc.robot.HardwareMap;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.HardwareMap.SwerveHardware;
import frc.robot.Robot;

/** The drive subsystem of the robot. */
public class SwerveDrivetrain extends SubsystemBase {

	private SwerveModule m_frontLeftSwerveWheel;
	private SwerveModule m_frontRightSwerveWheel;
	private SwerveModule m_backLeftSwerveWheel;
	private SwerveModule m_backRightSwerveWheel;

	private double m_xSpeed;
	private double m_ySpeed;
	private double m_rotationSpeed;
	private boolean m_isFieldRelative;
	private AHRS m_gyro;
	private SwerveDriveOdometry m_odometry;
	public SwerveDriveKinematics m_kinematics;
	private double time;
	private final Field2d m_field = new Field2d();

	private double m_gyroOffset = 0;

	private double m_desiredHeading;

	/**
	 * Creates a new {@link SwerveDrivetrain}.
	 */
	public SwerveDrivetrain(SwerveHardware swerveHardware) {
		SmartDashboard.putData("Field", m_field);

		// Robot is facing towards positive x direction
		m_frontLeftSwerveWheel = swerveHardware.frontLeftSwerveModule;
		m_frontRightSwerveWheel = swerveHardware.frontRightSwerveModule;
		m_backLeftSwerveWheel = swerveHardware.backLeftSwerveModule;
		m_backRightSwerveWheel = swerveHardware.backRightSwerveModule;

		m_kinematics = new SwerveDriveKinematics(m_frontLeftSwerveWheel.getLocation(),
				m_frontRightSwerveWheel.getLocation(), m_backLeftSwerveWheel.getLocation(),
				m_backRightSwerveWheel.getLocation());

		m_gyro = new AHRS();
		m_odometry = new SwerveDriveOdometry(m_kinematics, m_gyro.getRotation2d());

		m_desiredHeading = 0;
	}

	@Override
	public void periodic() {
		double gyroAngle = m_gyro.getAngle();
		if (time > 10) {
			m_odometry.update(m_gyro.getRotation2d(), m_frontLeftSwerveWheel.getState(),
					m_frontRightSwerveWheel.getState(), m_backLeftSwerveWheel.getState(),
					m_backRightSwerveWheel.getState());
			m_field.setRobotPose(m_odometry.getPoseMeters());
		}
		time++;
		ChassisSpeeds desiredSpeed;

		// convert to robot relative if in field relative
		if (m_isFieldRelative) {
			desiredSpeed = ChassisSpeeds.fromFieldRelativeSpeeds(m_xSpeed, m_ySpeed, m_rotationSpeed,
					Rotation2d.fromDegrees(gyroAngle));
		} else {
			desiredSpeed = new ChassisSpeeds(m_xSpeed, m_ySpeed, m_rotationSpeed);
		}

		SwerveModuleState[] desiredSwerveModuleStates = m_kinematics.toSwerveModuleStates(desiredSpeed);

		// If the robot is real, adds friction coefficient * max wheel speed to account
		// for friction
		if (Robot.isReal()) {
			for (SwerveModuleState swerveModule : desiredSwerveModuleStates) {
				swerveModule.speedMetersPerSecond += (ModuleConstants.TRANSLATIONAL_FRICTION
						* DriveConstants.MAX_SPEED_METERS_PER_SECOND);
			}
		}

		SwerveDriveKinematics.normalizeWheelSpeeds(desiredSwerveModuleStates,
				DriveConstants.MAX_SPEED_METERS_PER_SECOND);

		if (desiredSpeed.vxMetersPerSecond == 0 && desiredSpeed.vyMetersPerSecond == 0
				&& desiredSpeed.omegaRadiansPerSecond == 0) {
			m_frontLeftSwerveWheel.setVelocity(0);
			m_frontRightSwerveWheel.setVelocity(0);
			m_backLeftSwerveWheel.setVelocity(0);
			m_backRightSwerveWheel.setVelocity(0);
		} else {
			m_frontLeftSwerveWheel.setDesiredState(desiredSwerveModuleStates[0]);
			m_frontRightSwerveWheel.setDesiredState(desiredSwerveModuleStates[1]);
			m_backLeftSwerveWheel.setDesiredState(desiredSwerveModuleStates[2]);
			m_backRightSwerveWheel.setDesiredState(desiredSwerveModuleStates[3]);
		}

		// updates the gyro yaw value and prints it to the simulator
		double m_degreeRotationSpeed = Math.toDegrees(m_rotationSpeed);
		double m_degreesSinceLastTick = m_degreeRotationSpeed * Robot.kDefaultPeriod;

		printSimulatedGyro(m_gyro.getYaw() + m_degreesSinceLastTick + m_gyroOffset);

		SmartDashboard.putNumber("OdometryX", m_odometry.getPoseMeters().getX());
		SmartDashboard.putNumber("OdometryY", m_odometry.getPoseMeters().getY());
		SmartDashboard.putNumber("Odometryrot", m_odometry.getPoseMeters().getRotation().getDegrees());

		SmartDashboard.putNumber("Gyro Heading", m_gyro.getYaw());
		SmartDashboard.putNumber("Gyro angle in degrees", gyroAngle);
		SmartDashboard.putNumber("The desired angle", m_desiredHeading * (180 / Math.PI));
	}

	/**
	 * Method to drive the robot using joystick info.
	 *
	 * @param xSpeed        Speed of the robot in the x direction (forward).
	 *                      Positive values are forwards and negative values are
	 *                      backwards.
	 * @param ySpeed        Speed of the robot in the y direction (sideways).
	 *                      Positive values are left and negative values are right.
	 * @param rot           Angular rate of the robot. Positive values are
	 *                      counterclockwise and negative values are clockwise.
	 * @param fieldRelative Whether the provided x and y speeds are relative to the
	 *                      field.
	 */
	public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
		m_xSpeed = xSpeed;
		m_ySpeed = -ySpeed;
		m_rotationSpeed = -rot;
		m_isFieldRelative = fieldRelative;

		// scales m_xSpeed and m_ySpeed such that the net speed is equal to
		// MAX_METERS_PER_SECOND (only if the net speed is above MAX_METERS_PER_SECOND)
		double m_netSpeed = Math.sqrt((m_xSpeed * m_xSpeed) + (m_ySpeed * m_ySpeed));
		if (m_netSpeed > DriveConstants.MAX_SPEED_METERS_PER_SECOND) {
			// the scale factor will always be less than one
			double m_scaleFactor = DriveConstants.MAX_SPEED_METERS_PER_SECOND / m_netSpeed;
			m_xSpeed *= m_scaleFactor;
			m_ySpeed *= m_scaleFactor;
		}

		if (m_rotationSpeed > ModuleConstants.MAX_MODULE_ANGULAR_SPEED_RADIANS_PER_SECOND) {
			m_rotationSpeed = ModuleConstants.MAX_MODULE_ANGULAR_SPEED_RADIANS_PER_SECOND;
		}
		SmartDashboard.putNumber("X Speed", m_xSpeed);
		SmartDashboard.putNumber("Y Speed", m_ySpeed);
		SmartDashboard.putNumber("Rotation Speed", m_rotationSpeed);
	}

	/**
	 * Sets the swerve ModuleStates.
	 *
	 * @param moduleStates The desired SwerveModule states.
	 */
	public void setModuleStates(SwerveModuleState... moduleStates) {
		ChassisSpeeds speeds = m_kinematics.toChassisSpeeds(moduleStates);
		drive(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond, speeds.omegaRadiansPerSecond, false);
	}

	/**
	 * Returns the currently-estimated pose of the robot.
	 *
	 * @return The pose.
	 */
	public Pose2d getPose() {
		return m_odometry.getPoseMeters();
	}

	/**
	 * Returns the current {@link SwerveDriveKinematics}.
	 * 
	 * @return The current {@link SwerveDriveKinematics}.
	 */
	public SwerveDriveKinematics getKinematics() {
		return m_kinematics;
	}

	/** Zeroes the heading of the robot. */
	public void zeroHeading() {
		if (Robot.isReal()) {
			m_gyro.reset();
		} else {
			m_gyroOffset = m_gyro.getYaw();
		}
	}

	/** Zeroes the odometry. */
	public void resetOdometry() {
		m_odometry.resetPosition(new Pose2d(), new Rotation2d());
	}

	/**
	 * Resets the odometry to the specified pose.
	 *
	 * @param pose  The pose to which to set the odometry.
	 * @param angle The angle to which to set the odometry.
	 */
	public void resetOdometry(Pose2d pose, Rotation2d angle) {
		m_odometry.resetPosition(pose, angle);
	}

	/**
	 * Prints the estimated gyro value to the simulator.
	 * 
	 * @param printHeading The estimated gyro value.
	 */
	public void printSimulatedGyro(double printHeading) {
		int dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
		SimDouble angle = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw"));
		angle.set(printHeading);
	}

}