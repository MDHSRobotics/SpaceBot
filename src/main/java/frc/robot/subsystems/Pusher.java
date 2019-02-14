
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PusherStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Pusher subsystem for pushing the robot onto the platform
public class Pusher extends Subsystem {

    private boolean m_talonsAreConnected = false;
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    public Pusher() {
        Logger.debug("Constructing Subsystem: Pusher");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxPusher);
        Devices.talonSrxPusher.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Pusher DefaultCommand -> PusherStop...");

        PusherStop defaultCmd = new PusherStop();
        setDefaultCommand(defaultCmd);
    }

    // Stop the Pusher motor
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxPusher.stopMotor();
        }
    }

    // Pushes the robot onto the platform
    public void push(double speed) {
        if (m_talonsAreConnected) {
            Devices.talonSrxPusher.set(speed);
        }
    }

}
