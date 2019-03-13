
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.ShuffleLogger;
import frc.robot.Brain;
import frc.robot.Robot;


// The Shuffleboard Sight Tab
public class EncoderTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;

    // Encoder Properties
    private SimpleWidget m_hatchRotationDegrees;

    // Constructor
    public EncoderTab() {
        ShuffleLogger.logTrivial("Constructing SightTab...");

        m_tab = Shuffleboard.getTab("Sight");
    }

    // Create Brain Widgets
    public void preInitialize() {
        m_hatchRotationDegrees = m_tab.add("Brightness", Brain.hatchRotationDegreeEntry);
        Brain.hatchRotationDegreeEntry = m_hatchRotationDegrees.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_hatchRotationDegrees.withWidget(BuiltInWidgets.kTextView);
    }

    // This will be called in the robotPeriodic
    public void update() {

        // Don't need to update anything if the sight camera is not active
        if (Robot.robotCameraSight == null ) return;

        double rotations = Brain.getHatchRotationDegree();
        NetworkTableEntry hatchRotationDegreeEntry = m_hatchRotationDegrees.getEntry();
        double newRotations = hatchRotationDegreeEntry.getDouble(rotations);
        if (newRotations != rotations) {
            Brain.setHatchRotationDegrees(hatchRotationDegreeEntry);
        }
    }

}
