
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;
import java.util.Map;

import frc.robot.commands.interactive.TankSpin;
import frc.robot.consoles.ShuffleLogger;
import frc.robot.Robot;


// The Shuffleboard Subsystems Tab
public class SubsystemsTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private ShuffleboardLayout m_tankLayout;

    // Constructor
    public SubsystemsTab() {
        ShuffleLogger.logTrivial("Constructing SubsystemsTab...");

        m_tab = Shuffleboard.getTab("Subsystems");
    }

    // Create Brain Widgets
    public void preInitialize() {
    }

    // Create all other Widgets
    public void initialize() {
        m_tankLayout = m_tab.getLayout("Tank Subsystem", BuiltInLayouts.kList);
        m_tankLayout.add(Robot.robotTank);
        m_tankLayout.add(new TankSpin());
    }

    // Configure all Widgets
    public void configure() {
        m_tankLayout.withPosition(0, 0);
        m_tankLayout.withSize(2, 2);
        m_tankLayout.withProperties(Map.of("Label position", "HIDDEN")); // hide labels for elements within layout
    }

    // This will be called in the robotPeriodic
    public void update() {
    }

}
