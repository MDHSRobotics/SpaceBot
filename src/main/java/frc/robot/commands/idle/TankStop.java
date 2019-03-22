
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command stops the Tank motor
public class TankStop extends Command {

    public TankStop() {
        Logger.setup("Constructing Command: TankStop...");

        // Declare subsystem dependencies
        requires(Robot.robotTank);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: TankStop...");
    }

    @Override
    protected void execute() {
        Robot.robotTank.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: TankStop...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: TankStop...");
    }

}
