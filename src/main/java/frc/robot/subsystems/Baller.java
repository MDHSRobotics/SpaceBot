
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.BallerStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Cargo ball subsystem
public class Baller extends Subsystem {

    private boolean m_talonsAreConnected = false;
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

    public Baller() {
        Logger.debug("Constructing Subsystem: Baller...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxBaller);
        Devices.talonSrxBaller.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller DefaultCommand -> BallerStop...");

        setDefaultCommand(new BallerStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.stopMotor();
        }
    }

    // Holds the ball
    public void holdBall() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.set(-m_speed);
        }
    }

    // Tosses the ball
    public void tossBall() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.set(m_speed);
        }
    }

    // Determines if the Ball Held limit switch is active
    public boolean isHeld() {
        boolean hitLimit = Devices.limitSwitchBallHeld.get();
        if (hitLimit) {
            Logger.debug("Ball is now held!");
        }
        return hitLimit;
    }

    // Determines if the Ball Tossed limit switch is active
    public boolean isTossed() {
        boolean hitLimit = Devices.limitSwitchBallTossed.get();
        if (hitLimit) {
            Logger.debug("Ball has been tossed!");
        }
        return hitLimit;
    }

}
