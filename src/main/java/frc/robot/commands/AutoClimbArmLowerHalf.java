
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is activated by a button and lowers the arm until interrupted
// See todo in the ClimbArm subsystem for more details.
public class AutoClimbArmLowerHalf extends Command {
    // Default Power
    private double m_defaultPower = 0.2;
    // Power setting for drive: 0.0 to +1.0
    private double m_power;

    // The following is a temporary code that uses the timer until we have the encoder working:
    // Timer for this command
    private Timer m_timer;
    // Target duration for the motor to run in second
    private int m_target = 2;
    
    public AutoClimbArmLowerHalf() {
        Logger.debug("Constructing AutoClimbArmLowerHalf...");

        m_power = m_defaultPower;
        m_timer = new Timer();

        requires(Robot.robotClimbArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoClimbArmLowerHalf...");

        m_timer.reset();
        m_timer.start();
    }

    @Override
    protected void execute() {
        Robot.robotClimbArm.move(m_power);
    }

    // This command continues until it is interrupted
    @Override
    protected boolean isFinished() {
        double elapsedTime = m_timer.get();
        return (elapsedTime >= m_target);
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoClimbArmLowerHalf...");

        Robot.robotClimbArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoClimbArmLowerHalf...");

        Robot.robotClimbArm.stop();
    }

}
