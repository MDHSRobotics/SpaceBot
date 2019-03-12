
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.consoles.Logger;
import frc.robot.commands.reactive.ArmReset;
import frc.robot.commands.reactive.PulleyReset;


// Resets the Arm and the Pulley to their start positions, in parallel
public class ArmPulleyReset extends CommandGroup {

    public ArmPulleyReset() {
        Logger.setup("Constructing Command Group: ArmPulleyReset...");

        addParallel(new ArmReset());
        addParallel(new PulleyReset());
    }

}
