
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.commands.auto.BallHold;
import frc.robot.commands.auto.BallToss;
import frc.robot.helpers.Logger;
import frc.robot.Robot;


// Toggles the position of the Baller
public class BallerTogglePosition extends InstantCommand {

    private BallHold m_ballHoldCmd;
    private BallToss m_ballTossCmd;

    public BallerTogglePosition() {
        super();
        Logger.debug("Constructing InstantCommand: BallerTogglePosition...");

        m_ballHoldCmd = new BallHold();
        m_ballTossCmd = new BallToss();
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing InstantCommand: BallerTogglePosition...");

        if (Robot.robotBaller.isBallTossed) {
            m_ballHoldCmd.start();
        }
        else {
            m_ballTossCmd.start();
        }
        // TODO: Should this happen here, or in the isFinished() methods for each command?
        Robot.robotBaller.toggleBallTossed();
    }

}
