
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PulleyStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Pulley subsystem for lifting the robot onto the platform
public class Pulley extends Subsystem {

    // Motor variables
    private double m_secondsFromNeutralToFull = 0;
    private int m_timeoutMS = 10;
    private double m_pulleySpeed = .5;

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Pulley() {
        Logger.debug("Constructing Subsystem: Pulley...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxPulley);
        if (m_talonsAreConnected) {
            Devices.talonSrxPulley.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Pulley DefaultCommand -> PulleyStop...");

        setDefaultCommand(new PulleyStop());
    }

    // Stop the Pulley motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulley.stopMotor();
    }

    // Run the motor to lift the pulley
    public void lift() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulley.set(m_pulleySpeed);
    }

     // Run the motor to lower the pulley
     public void lower() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulley.set(-m_pulleySpeed);
    }

}
