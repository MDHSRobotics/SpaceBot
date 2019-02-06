
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command spins the Tank
public class TankSpin extends Command {

    public TankSpin() {
        Logger.debug("Constructing TankSpin...");

        // Declare subsystem dependencies
        requires(Robot.robotTank);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing TankSpin...");
    }

    @Override
    protected void execute() {
        // TODO: declare this value as a private member variable
        Robot.robotTank.spin(.5);
    }

    @Override
    protected boolean isFinished() {
        // TODO: should this not continue until something interrupts it?
        // Or is it controlled by a held joystick or xbox controller button?

        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending TankSpin...");

        Robot.robotTank.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted TankSpin...");

        Robot.robotTank.stop();
    }

}
