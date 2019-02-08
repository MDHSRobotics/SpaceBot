
package frc.robot.consoles;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Map;

import frc.robot.Robot;


// The Shuffleboard Main Tab
public class MainTab {

    private ShuffleboardTab mainTab;

    // Updatable entries, either by user or under program control
    private NetworkTableEntry driver_matchTime;

    // Constructor
    public MainTab() {

        mainTab = Shuffleboard.getTab("Main");

        // Match time
        SimpleWidget m_matchTimeWidget = mainTab.add("Match Time", 0.0);
        m_matchTimeWidget.withPosition(2, 0);
        m_matchTimeWidget.withWidget(BuiltInWidgets.kDial);
        m_matchTimeWidget.withProperties(Map.of("min", -1, "max", 135)); // this property setting isn't working
        driver_matchTime = m_matchTimeWidget.getEntry();

        // Autonomous Command
        ComplexWidget autoCmdWidget = mainTab.add("Auto Command", Robot.autoCommandChooser);
        autoCmdWidget.withSize(2, 1);
        autoCmdWidget.withPosition(0, 0);

        //Line Detector
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        SimpleWidget lineDetectWidget = mainTab.add("Line Detected", detected);
        lineDetectWidget.withPosition(3,0);



    }

    public void update() {
        double matchTime = DriverStation.getInstance().getMatchTime();
        driver_matchTime.setDouble(matchTime);
    }

}
