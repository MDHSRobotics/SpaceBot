
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Relay;

import frc.robot.commands.auto.LightOn;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Lighter Subsystem
public class Lighter extends Subsystem {
    
    public Lighter() {
        Logger.debug("Constructing Subsystem: Lighter...");
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Lighter DefaultCommand -> LightOn...");

        setDefaultCommand(new LightOn());
    }

    public void turnOn() {
        Devices.lighterRelay.set(Relay.Value.kOn);
    }

    public void turnOff() {
        Devices.lighterRelay.set(Relay.Value.kOff);
    }

}
