
package frc.robot.consoles;

import edu.wpi.first.wpilibj.shuffleboard.*;
import java.util.Map;

import frc.robot.commands.xbox.PulleyUp;
import frc.robot.Robot;


// The Shuffleboard Subsystems Tab
public class SubsystemsTab {

    private ShuffleboardTab subsystemsTab;

    // Constructor
    public SubsystemsTab() {

        subsystemsTab = Shuffleboard.getTab("Subsystems");

        ShuffleboardLayout pulleyLayout = subsystemsTab.getLayout("Pulley Subsystem", BuiltInLayouts.kList);
        pulleyLayout.withSize(2, 2);
        pulleyLayout.withPosition(0, 0);
        pulleyLayout.withProperties(Map.of("Label position", "HIDDEN")); // hide labels for elements within layout
        pulleyLayout.add(Robot.robotPulley);

        pulleyLayout.add(new PulleyUp());
    }

    public void update() {

    }

}
