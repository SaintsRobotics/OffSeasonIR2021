package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class HardwareMap {
    // make subclasses each with their own constructor + fields
    // one subclass per subsystem

    // either construct each subclass seperately in robotcontainer,
    // OR create fields for each subclass in the constructor for hardwaremap
    public class IntakeHardware {
        public VictorSPX intakeController;
        public VictorSPX armController;

        public IntakeHardware() {
            armController = new VictorSPX(24);
            intakeController = new VictorSPX(25);
        }
    }

    IntakeHardware intakeHardware;

    public HardwareMap() {
        intakeHardware = new IntakeHardware();
    }

}
