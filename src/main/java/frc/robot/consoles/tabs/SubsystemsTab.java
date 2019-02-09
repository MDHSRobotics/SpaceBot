
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;
import java.util.Map;

import frc.robot.commands.xbox.PulleyLift;
import frc.robot.Robot;


// The Shuffleboard Subsystems Tab
public class SubsystemsTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private ShuffleboardLayout m_pulleyLayout;

    // Constructor
    public SubsystemsTab() {

        m_tab = Shuffleboard.getTab("Subsystems");
    }

    // Create Brain Widgets
    public void preInitialize() {
    }

    // Create all other Widgets
    public void initialize() {
        m_pulleyLayout = m_tab.getLayout("Pulley Subsystem", BuiltInLayouts.kList);
        m_pulleyLayout.add(Robot.robotPulley);
        m_pulleyLayout.add(new PulleyLift());
    }

    // Configure all Widgets
    public void configure() {
        m_pulleyLayout.withPosition(0, 0);
        m_pulleyLayout.withSize(2, 2);
        m_pulleyLayout.withProperties(Map.of("Label position", "HIDDEN")); // hide labels for elements within layout
    }

    // This will be called in the robotPeriodic
    public void update() {
    }

}
