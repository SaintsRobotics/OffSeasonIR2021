package frc.robot;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class AbsoluteEncoder {
	/**
	 * this class is mainly a wrapper for accessing the current position of rotation
	 * that the motor is at it uses the potentiometer values as voltage and
	 * correlates them to rotation degrees
	 */

	private AnalogInput analogIn;
	private double m_offset = 0; // the offset from zero for each motor
	private boolean isInverted;
	private double voltageToDegrees = 72;
	private AnalogEncoder x;

	/**
	 * Construct and absolute encoder, most likely a US Digital MA3 encoder.
	 * 
	 * @param channel  Analog in (sometimes also refered to as AIO) port on the
	 *                 roboRIO.
	 * @param inverted Set this to <i>TRUE</i> if physically turning the wheel
	 *                 <i>CLOCKWISE</i> (looking down on it from the top of the bot)
	 *                 <i>INCREASES</i> the voltage it returns.
	 * @param offset   Swerve offset (in <i>DEGREES</i>), like we've been using for
	 *                 the past three years. This value is <i>SUBTRACTED</i> from
	 *                 the output.
	 */
	public AbsoluteEncoder(int channel, double offset, boolean isInverted) {
		analogIn = new AnalogInput(channel);
		this.x = new AnalogEncoder(analogIn);
		this.isInverted = isInverted;
		this.m_offset = offset;
	}

	/**
	 * Gets rotation in degrees
	 * 
	 * @return the position of encoder in degrees
	 */
	public double getDegrees() {
		if (isInverted) {
			return ((5 - analogIn.getVoltage()) * this.voltageToDegrees);
		}

		else {
			return (analogIn.getVoltage() * this.voltageToDegrees);
		}
	}

	public double getRadians() {
		return Math.toRadians(getDegrees()) - this.m_offset;
    }

    /**
     * Gets the Rotation2d
     * @return
     */
    public Rotation2d getRotation2d() {
        return new Rotation2d(Math.toRadians(getDegrees()) - this.m_offset);
    }

	public void reset() {
		this.m_offset = analogIn.getVoltage() * this.voltageToDegrees;
	}

}