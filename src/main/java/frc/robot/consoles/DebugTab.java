
package frc.robot.consoles;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.*;


// The Shuffleboard Debug Tab
public class DebugTab {

    private ShuffleboardTab debugTab;

    // Constructor
    public DebugTab() {
        debugTab = Shuffleboard.getTab("Debug");

        //  Command Scheduler
        //  Not sure why this isn't working
        Scheduler sched = Scheduler.getInstance();
        ComplexWidget schedulerWidget = debugTab.add("Scheduler", sched);
        schedulerWidget.withSize(2, 1);
        schedulerWidget.withPosition(0, 1);
    }

    public void update() {

    }

}
