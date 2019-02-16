
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Relay;

import frc.robot.commands.interactive.LightToggle;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Lighter Subsystem
public class Lighter extends Subsystem {
    
    public Lighter() {
        Logger.debug("Constructing Subsystem: Lighter...");
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Lighter DefaultCommand -> LightToggle...");

        setDefaultCommand(new LightToggle());
    }

    //TODO: change Forward and Reverse to White and Red once we know which is which
    public void turnOnForward() {
        Devices.lighterRelay.set(Relay.Value.kForward);
    }

    public void turnOnReverse() {
        Devices.lighterRelay.set(Relay.Value.kReverse);
    }

    public void turnOff() {
        Devices.lighterRelay.set(Relay.Value.kOff);
    }

}
