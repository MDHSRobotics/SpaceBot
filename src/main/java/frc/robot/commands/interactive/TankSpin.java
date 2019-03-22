
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command spins the Tank
public class TankSpin extends Command {

    public TankSpin() {
        Logger.setup("Constructing Command: TankSpin...");

        // Declare subsystem dependencies
        requires(Robot.robotTank);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: TankSpin...");
    }

    @Override
    protected void execute() {
        double speed = OI.getTankSpinSpeed();
        Robot.robotTank.spin(speed);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: TankSpin...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: TankSpin...");
    }

}
