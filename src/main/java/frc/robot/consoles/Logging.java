
package frc.robot.consoles;

import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

// Log events to various places: Shuffleboard, stdout, etc.

// Importance levels of events:
// Trivial - A trivial event such as a change in command state
// Low - A low importance event such as acquisition of a game piece.
// Normal - A "normal" importance event, such as a transition from autonomous mode to teleoperated control.
// High - A high-importance event such as scoring a game piece.
// Critical - A critically important event such as a brownout, component failure, or software deadlock.

// Note: Event names should be terse
public class Logging {

    // Trivial events such as command switching state
    public static void logTrivial(String eventName) {

        // Print to Shuffleboard
        Shuffleboard.addEventMarker(eventName, EventImportance.kTrivial);

        // Print to stdout
        System.out.println(eventName);
    }

    // Normal events such as transition from autonomous to teleop mode
    public static void logNormal(String eventName) {

        // Print to Shuffleboard
        Shuffleboard.addEventMarker(eventName, EventImportance.kNormal);

        // Print to stdout
        System.out.println(eventName);
    }

    //TODO Need to add methods for other levels of events: Critical, High, Low
    //TODO Add verbose versions of the event logging methods so that a description can be added
}
