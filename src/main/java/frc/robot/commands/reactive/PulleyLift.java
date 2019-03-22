
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.commands.interactive.PulleyManual;
import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lifts the Pulley via encoder, and keeps it there, with the user option to manually override
public class PulleyLift extends Command {

    private PulleyManual m_pulleyManual;

    public PulleyLift() {
        Logger.setup("Constructing Command: PulleyLift...");

        requires(Robot.robotPulley);
        m_pulleyManual = new PulleyManual();
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: PulleyLift...");

        // Set the encoded position
        Robot.robotPulley.lift();
        Robot.robotPulley.isLifting = true;
    }

    @Override
    protected void execute() {
        // int position = Robot.robotPulley.getPosition();
        // int velocity = Robot.robotPulley.getVelocity();
        // Logger.info("PulleyUp -> Position: " + position + "; Velocity: " + velocity);

        double speed = OI.getPulleyLiftSpeed();
        if (speed != 0) {
            m_pulleyManual.start();
        }
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: PulleyLift...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: PulleyLift...");
    }

}
