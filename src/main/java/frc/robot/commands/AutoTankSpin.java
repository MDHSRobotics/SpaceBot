
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command spins the Tanker
public class AutoTankSpin extends Command {

    public AutoTankSpin() {
        Logger.debug("Constructing AutoTankSpin...");

        // Declare subsystem dependencies
        requires(Robot.robotTanker);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoTankSpin...");
    }

    @Override
    protected void execute() {
        // TODO: declare this value as a private member variable
        Robot.robotTanker.climb(.5);
    }

    @Override
    protected boolean isFinished() {
        // TODO: should this not continue until something interrupts it?
        // Or is it controlled by a held joystick or xbox controller button?

        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoTankSpin...");

        Robot.robotTanker.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoTankSpin...");

        Robot.robotTanker.stop();
    }

}
