
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdleLiftDriver;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Lift driver subsystem for pushing the robot onto the platform
public class LiftDriver extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    public LiftDriver() {
        Logger.debug("Contructring LiftWheel");

        Devices.talonSrxLiftWheel.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing LiftWheel default command");

        IdleLiftDriver defaultCmd = new IdleLiftDriver();
        setDefaultCommand(defaultCmd);
    }

    // Stop the lift drive motor
    public void stop() {
        Devices.talonSrxLiftWheel.stopMotor();
    }

    // Pushes the robot onto the platform
    public void push(double speed) {
        Devices.talonSrxLiftWheel.set(speed);
    }

}
