
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdleBaller;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Cargo ball subsystem
public class Baller extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

    public Baller() {
        Logger.debug("Constructing Baller...");

        Devices.talonSrxBaller.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller default command...");

        setDefaultCommand(new IdleBaller());
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxBaller.stopMotor();
    }

    // Blocks the ball
    public void blockBall() {
        Devices.talonSrxBaller.set(-m_speed);    
    }

    // Tosses the ball
    public void tossBall() {
        Devices.talonSrxBaller.set(m_speed);
    }

    // Determines if the Ball Blocked limit switch is active
    public boolean isBlocked() {
        boolean hitLimit = Devices.limitSwitchBallBlocked.get();
        if (hitLimit) {
            Logger.debug("Ball is now blocked!");
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
