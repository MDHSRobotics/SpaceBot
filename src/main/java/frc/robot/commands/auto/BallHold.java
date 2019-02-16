
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.helpers.Logger;
import frc.robot.Devices;
import frc.robot.Robot;


// This command moves the Baller to hold the cargo ball until it is ready to be tossed
public class BallHold extends Command {

    public BallHold() {
        Logger.debug("Constructing Command: BallHold...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: BallHold...");

        Robot.robotBaller.holdBall();
    }

    @Override
    protected void execute() {
        Logger.debug("Position: " + Robot.robotBaller.getPosition());
        Logger.debug("Velocity: " +  Robot.robotBaller.getVelocity());
    }

    // This command is finished when the ball has been fully blocked
    @Override
    protected boolean isFinished() {
        return Robot.robotBaller.isPositionMet();
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: BallHold...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: BallHold...");

        Robot.robotBaller.stop();
    }

}
