
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdleClimbArm;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class ClimbArm extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    // Constructor
    public ClimbArm(){
        Logger.debug("Contructing ClimbArm...");

        Devices.talonSrxClimbArm.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing ClimbArm default command...");

        setDefaultCommand(new IdleClimbArm());
    }

    // Stop the Arm motor
    public void stop() {
        Devices.talonSrxClimbArm.stopMotor();
    }

    // Raises or Lowers the Arm based on given speed
    public void move(double speed) {
        // TODO: this method actually needs to be split into three methods.
        // Method 1: lowerHalf() - hard coded position
        // Method 2: lowerFull() - hard coded position
        // Method 3: lowerMore() - user controlled, but with a max position limit

        Devices.talonSrxClimbArm.set(speed);
    }

}
