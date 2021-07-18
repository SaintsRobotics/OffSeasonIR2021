package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class HardwareMap {
    // make subclasses each with their own constructor + fields
    // one subclass per subsystem

    // either construct each subclass seperately in robotcontainer, 
    // OR create fields for each subclass in the constructor for hardwaremap
    public class IntakeHardware{
        public VictorSPX intakeController = new VictorSPX(25);
        public VictorSPX armController = new VictorSPX(24);

        public IntakeHardware(){

        }
    }
        IntakeHardware intakeHardware = new IntakeHardware();

}
