
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.commands.auto.ArmLowerHalf;
import frc.robot.commands.auto.ArmLowerFull;
import frc.robot.commands.idle.ArmStop;
import frc.robot.commands.interactive.ArmLowerMore;
import frc.robot.helpers.Logger;
import frc.robot.Robot;


public class ArmNextPosition extends InstantCommand {

    private ArmLowerHalf m_armLowerHalfCmd;
    private ArmLowerFull m_armLowerFullCmd;
    private ArmLowerMore m_armLowerMoreCmd;
    private ArmStop m_armStopCmd;

    public ArmNextPosition() {
        super();
        Logger.debug("Constructing InstantCommand: ArmNextPosition...");

        m_armLowerHalfCmd = new ArmLowerHalf();
        m_armLowerFullCmd = new ArmLowerFull();
        m_armLowerMoreCmd = new ArmLowerMore();
        m_armStopCmd = new ArmStop();
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing InstantCommand: ArmNextPosition...");

        switch(Robot.robotArm.currentArmPosition) {
            case START:
                m_armLowerHalfCmd.start();
                break;

            case HALF:
                m_armLowerFullCmd.start();
                break;

            case FULL:
                m_armLowerMoreCmd.start();
                break;

            case MORE:
                m_armStopCmd.start();
                break;
        }
    }

}
