
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.HatcherStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Hatcher subsystem, for grabbing and releasing hatches
public class Hatcher extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

    public Hatcher() {
        Logger.debug("Constructing Subsystem: Hatcher...");

        if (Devices.isConnected(Devices.talonSrxHatcher)) {
            Devices.talonSrxHatcher.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Hatcher DefaultCommand -> HatcherStop...");

        setDefaultCommand(new HatcherStop());
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxHatcher.stopMotor();
    }

    // Opens the Hatcher claw to grab a hatch
    public void grab() {
        Devices.talonSrxHatcher.set(m_speed);
    }

    // Closes the Hatcher claw to release a hatch
    public void release() {
        Devices.talonSrxHatcher.set(-m_speed);
    }

    // TODO: Need to change these to use encoders
    public boolean isGrabbed() {
        boolean hitLimit = Devices.limitSwitchHatchGrabbed.get();
        return hitLimit;
    }

    public boolean isReleased() {
        boolean hitLimit = Devices.limitSwitchHatchReleased.get();
        return hitLimit;
    }

}
