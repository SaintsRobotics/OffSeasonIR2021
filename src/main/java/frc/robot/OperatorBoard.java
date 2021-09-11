package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * A wrapper class for our custom-made operator board. Does not have a
 * constructor. Access statically. Access Buttons via public static state.
 * Access axis values via methods. Use methods to make buttons light up.
 */
public class OperatorBoard {

    private static Joystick HARDWARE;
    public OperatorBoardButton BUTTON_ONE;
    public OperatorBoardButton BUTTON_TWO;
    public OperatorBoardButton BUTTON_THREE;
    public OperatorBoardButton BUTTON_SIX;
    public OperatorBoardButton BUTTON_ELEVEN;
    public OperatorBoardButton BUTTON_TWELVE;
    public OperatorBoardButton BUTTON_THIRTEEN;
    public OperatorBoardButton BUTTON_FOURTEEN;
    // TODO rename these, and use lowercase camel case names

    /**
     * Constructs one operator board. Since we only have one operator board, this
     * class should be treated as a singleton.
     * 
     * @param port The USB port number for the controller
     */
    public OperatorBoard(int port) {
        HARDWARE = new Joystick(port);

        BUTTON_ONE = new OperatorBoardButton(1);
        BUTTON_TWO = new OperatorBoardButton(2);
        BUTTON_THREE = new OperatorBoardButton(3);
        BUTTON_SIX = new OperatorBoardButton(6);
        BUTTON_ELEVEN = new OperatorBoardButton(11);
        BUTTON_TWELVE = new OperatorBoardButton(12);
        BUTTON_THIRTEEN = new OperatorBoardButton(13);
        BUTTON_FOURTEEN = new OperatorBoardButton(14);
    }

    /**
     * Access the x-axis of the left joystick.
     * 
     * @return Left is negative, right is positive.
     */
    public double getLeftJoystickX() {
        return -HARDWARE.getRawAxis(0);
        // This value is negated because something is weird with the wiring.
    }

    /**
     * Access the y-axis of the left joystick.
     * 
     * @return Up is negative, down is positive.
     */
    public double getLeftJoystickY() {
        return HARDWARE.getRawAxis(1);
    }

    /**
     * Access the x-axis of the right joystick.
     * 
     * @return Up is negative, down is positive.
     */
    public double getRightJoystickX() {
        return HARDWARE.getRawAxis(2);
    }

    /**
     * Access the y-axis of the right joystick.
     * 
     * @return Up is negative, down is positive.
     */
    public double getRightJoystickY() {
        return HARDWARE.getRawAxis(3);
    }

    public class OperatorBoardButton extends Button {
        private int m_buttonNumber;

        public OperatorBoardButton(int button) {
            m_buttonNumber = button;
        }

        /**
         * @return Whether or not the button is being pressed.
         */
        @Override
        public boolean get() {
            return HARDWARE.getRawButton(m_buttonNumber);
        }

        /**
         * Turns the button's light on, and keeps it on until turnLightOff() is called.
         */
        public void turnLightOn() {
            HARDWARE.setOutput(m_buttonNumber, true);
        }

        /**
         * Turns the button's light off, and keeps it off until turnLightOn() is called.
         */
        public void turnLightOff() {
            HARDWARE.setOutput(m_buttonNumber, false);
        }
    }

}
