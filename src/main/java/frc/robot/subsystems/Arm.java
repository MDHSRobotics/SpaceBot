
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.ArmStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class Arm extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    // Constructor
    public Arm() {
        Logger.debug("Contructing Subsystem: Arm...");

        Devices.talonSrxArm.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Arm DefaultCommand -> ArmStop...");

        setDefaultCommand(new ArmStop());
    }

    // Stop the Arm motor
    public void stop() {
        Devices.talonSrxArm.stopMotor();
    }

    // Raises or lowers the Arm based on given speed
    public void move(double speed) {
        // TODO: this method actually needs to be split into three methods
        // Method 1: lowerHalf() - hard coded position
        // Method 2: lowerFull() - hard coded position
        // Method 3: lowerMore() - user controlled, but with a max position limit
        // TODO: after this is done, I think the ArmLower commands might be better combined

        Devices.talonSrxArm.set(speed);
    }

}
