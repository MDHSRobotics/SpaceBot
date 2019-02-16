
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.commands.auto.BallHold;
import frc.robot.commands.auto.BallToss;
import frc.robot.Robot;


public class BallerTogglePosition extends CommandGroup {

    public BallerTogglePosition() {
        if (Robot.robotBaller.ballIsTossed) {
            addSequential(new BallHold());
        }
        else {
            addSequential(new BallToss());
        }
        Robot.robotBaller.toggleBallTossed();
    }

}
