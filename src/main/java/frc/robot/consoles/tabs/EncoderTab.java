
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.ShuffleLogger;
import frc.robot.Brain;
import frc.robot.Robot;
import frc.robot.consoles.Logger;


// The Shuffleboard Sight Tab
public class EncoderTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;

    // Encoder Properties
    private SimpleWidget m_hatchRotationDegreesWidget;

    // Constructor
    public EncoderTab() {
        ShuffleLogger.logTrivial("Constructing EncoderTab...");

        m_tab = Shuffleboard.getTab("Encoders");
    }

    // Create Brain Widgets
    public void preInitialize() {
        m_hatchRotationDegreesWidget = m_tab.add("Hatch Rotations", Brain.hatchRotationDegreeDefault);
        Brain.hatchRotationDegreeEntry = m_hatchRotationDegreesWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_hatchRotationDegreesWidget.withWidget(BuiltInWidgets.kTextView);
    }

    // This will be called in the robotPeriodic
    public void update() {
        NetworkTableEntry hatchRotationDegreeEntry = m_hatchRotationDegreesWidget.getEntry();
            Brain.setHatchRotationDegrees(hatchRotationDegreeEntry);
    }

}
