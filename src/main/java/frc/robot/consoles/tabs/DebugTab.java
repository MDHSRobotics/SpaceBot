
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.*;


// The Shuffleboard Debug Tab
public class DebugTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private ComplexWidget m_schedulerWidget;

    // Constructor
    public DebugTab() {
        m_tab = Shuffleboard.getTab("Debug");

        //  Command Scheduler
        //  Not sure why this isn't working
        Scheduler sched = Scheduler.getInstance();
        m_schedulerWidget = m_tab.add("Scheduler", sched);
        m_schedulerWidget.withPosition(0, 1);
        m_schedulerWidget.withSize(2, 1);
    }

    // This will be called in the robotPeriodic
    public void update() {

    }

}
