
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Relay;

import frc.robot.commands.AutoLightOff;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Lighter Subsystem
public class Lighter extends Subsystem {
    
    public Lighter() {
        Logger.debug("Constructing Lighter...");
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Lighter default command...");

        setDefaultCommand(new AutoLightOff());
    }

    public void turnOn() {
        Devices.lighterRelay.set(Relay.Value.kOn);
    }

    public void turnOff() {
        Devices.lighterRelay.set(Relay.Value.kOff);
    }

}
