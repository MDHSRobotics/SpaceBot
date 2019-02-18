
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command spins the Tank
public class TankSpin extends Command {

    public TankSpin() {
        Logger.debug("Constructing Command: TankSpin...");

        // Declare subsystem dependencies
        requires(Robot.robotTank);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: TankSpin...");
    }

    @Override
    protected void execute() {
        double speed = OI.getTankSpinSpeed();
        Robot.robotTank.spin(speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: TankSpin...");

        Robot.robotTank.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: TankSpin...");

        Robot.robotTank.stop();
    }

}
