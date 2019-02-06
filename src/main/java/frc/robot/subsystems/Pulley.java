
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PulleyStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Pulley subsystem for lifting the robot onto the platform
public class Pulley extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    public Pulley() {
        Logger.debug("Constructing Pulley...");

        Devices.talonSrxPulley.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Pulley default command...");

        setDefaultCommand(new PulleyStop());
    }

    // Stop the Pulley motor
    public void stop() {
        Devices.talonSrxPulley.stopMotor();
    }

    // Run the motor to lift the pulley
    public void lift(double speed) {
        // TODO: define a private member variable for the speed in this class,
        // and use that instead of an argument for this method.
        // Then add another method called: lower()

        Devices.talonSrxPulley.set(speed);
    }

}
