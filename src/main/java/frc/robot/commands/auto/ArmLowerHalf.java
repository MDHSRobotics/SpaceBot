
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is activated by a button and lowers the arm to the half position
public class ArmLowerHalf extends Command {
    // Default Speed
    private double m_defaultSpeed = 0.2;
    // Speed setting for drive: 0.0 to +1.0
    private double m_speed;

    // The following is a temporary code that uses the timer until we have the encoder working:
    // Timer for this command
    private Timer m_timer;
    // Target duration for the motor to run in second
    private int m_target = 2;

    public ArmLowerHalf() {
        Logger.debug("Constructing Command: ArmLowerHalf...");

        m_speed = m_defaultSpeed;
        m_timer = new Timer();

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: ArmLowerHalf...");

        m_timer.reset();
        m_timer.start();
    }

    @Override
    protected void execute() {
        Robot.robotArm.move(m_speed);
    }

    // This command continues for a certain period of time
    // Will be replaced be encoder logic
    @Override
    protected boolean isFinished() {
        double elapsedTime = m_timer.get();
        return (elapsedTime >= m_target);
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmLowerHalf...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmLowerHalf...");

        Robot.robotArm.stop();
    }

}