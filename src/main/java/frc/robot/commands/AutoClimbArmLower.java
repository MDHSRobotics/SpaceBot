
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is activated by a button and lowers the arm until interrupted
// TODO: rename this AutoClimbArmLowerHalf() and create two more commands:
// 1. AutoClimbArmLowerFull()
// 2. AutoClimbArmLowerMore()
// See todo in the ClimbArm subsystem for more details.
public class AutoClimbArmLower extends Command {
    // Default Power
    private double m_defaultPower = 0.2;
    // Power setting for drive: 0.0 to +1.0
    private double m_power;

    // The following is a temporary code that uses the timer until we have the encoder working:
    // Timer for this command
    private Timer m_timer;
    // Target duration for the timer
    // TODO: give this a more explicit variable name. Target for what?
    private int m_target;
    
    public AutoClimbArmLower() {
        Logger.debug("Constructing AutoClimbArmLower...");

        m_power = m_defaultPower;
        m_timer = new Timer();

        requires(Robot.robotClimbArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoClimbArmLower...");

        // TODO: just assign this value when the m_target variable is declared above.
        m_target = 2; //seconds
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
        Logger.debug("Ending AutoClimbArmLower...");

        Robot.robotClimbArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoClimbArmLower...");

        Robot.robotClimbArm.stop();
    }

}
