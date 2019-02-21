
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.commands.auto.BallHold;
import frc.robot.commands.auto.BallToss;
import frc.robot.consoles.Logger;
import frc.robot.Robot;


// Toggles the position of the Baller
public class BallerTogglePosition extends InstantCommand {

    private BallHold m_ballHoldCmd;
    private BallToss m_ballTossCmd;

    public BallerTogglePosition() {
        super();
        Logger.setup("Constructing InstantCommand: BallerTogglePosition...");

        m_ballHoldCmd = new BallHold();
        m_ballTossCmd = new BallToss();
    }

    @Override
    protected void initialize() {
        System.out.println("--");
        Logger.action("Initializing InstantCommand: BallerTogglePosition...");

        if (Robot.robotBaller.ballIsTossed) {
            Logger.action("Baller -> Moving to HOLD...");
            m_ballHoldCmd.start();
        }
        else {
            Logger.action("Baller -> Moving to TOSS...");
            m_ballTossCmd.start();
        }
        Robot.robotBaller.toggleBallTossed();
    }

}
