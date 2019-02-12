
package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.subsystems.MecDriver.ControlOrientation;


// This class contains all the shared NetworkTableEntries for the Robot,
// their default values, and methods for retrieving their current values
public class Brain {

    //----------------//
    // Default Values //
    //----------------//

    // Shuffler - Main Tab
    public static double matchTimeDefault = 0;
    public static boolean lineDetectedDefault = false;

    // Shuffler - Drive Tab
    public static double driveTargetDistanceDefault = 2.0;

    // OI
    public static double yDeadZoneDefault = .1;
    public static double xDeadZoneDefault = .1;
    public static double zDeadZoneDefault = .5;
    public static double ySensitivityDefault = .5;
    public static double xSensitivityDefault = .5;
    public static double zSensitivityDefault = .5;

    // Subsystem - MecDriver
    public static ControlOrientation controlOrientationDefault = ControlOrientation.FIELD;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    // Shuffler - Main Tab
    public static NetworkTableEntry matchTimeEntry;
    public static NetworkTableEntry lineDetectedEntry;

    // Shuffler - Drive Tab
    public static NetworkTableEntry driveTargetDistanceEntry;

    // OI
    public static NetworkTableEntry yDeadZoneEntry;
    public static NetworkTableEntry xDeadZoneEntry;
    public static NetworkTableEntry zDeadZoneEntry;
    public static NetworkTableEntry ySensitivityEntry;
    public static NetworkTableEntry xSensitivityEntry;
    public static NetworkTableEntry zSensitivityEntry;

    // Subsystem - MecDriver
    public static NetworkTableEntry controlOrientationEntry;

    //---------//
    // Setters //
    //---------//

    // Shuffler - Main Tab
    public static void setMatchTime() {
        DriverStation ds = DriverStation.getInstance();
        double matchTime = ds.getMatchTime();
        matchTimeEntry.setDouble(matchTime);
    }

    public static void setLineDetected() {
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        lineDetectedEntry.setBoolean(detected);
    }

    // Shuffler - Drive Tab
    public static void setTargetDriveDistance(NetworkTableEntry entry) {
        double value = entry.getDouble(driveTargetDistanceDefault);
        driveTargetDistanceEntry.setDouble(value);
    }

    // OI
    public static void setYdeadZone(NetworkTableEntry entry) {
        double value = entry.getDouble(yDeadZoneDefault);
        yDeadZoneEntry.setDouble(value);
    }

    public static void setXdeadZone(NetworkTableEntry entry) {
        double value = entry.getDouble(xDeadZoneDefault);
        xDeadZoneEntry.setDouble(value);
    }

    public static void setZdeadZone(NetworkTableEntry entry) {
        double value = entry.getDouble(zDeadZoneDefault);
        zDeadZoneEntry.setDouble(value);
    }

    public static void setYsensitivity(NetworkTableEntry entry) {
        double value = entry.getDouble(ySensitivityDefault);
        ySensitivityEntry.setDouble(value);
    }

    public static void setXsensitivity(NetworkTableEntry entry) {
        double value = entry.getDouble(xSensitivityDefault);
        xSensitivityEntry.setDouble(value);
    }

    public static void setZsensitivity(NetworkTableEntry entry) {
        double value = entry.getDouble(zSensitivityDefault);
        zSensitivityEntry.setDouble(value);
    }

    // Subsystems - MecDriver
    public static void setControlOrientation(NetworkTableEntry entry) {
        String controlOrientationDefaultString = Brain.controlOrientationDefault.toString();
        String value = entry.getString(controlOrientationDefaultString);
        controlOrientationEntry.setValue(value);
    }

    public static void setControlOrientation(ControlOrientation orientation) {
        String value = orientation.toString();
        controlOrientationEntry.setValue(value);
    }

    //---------//
    // Getters //
    //---------//

    // Shuffler - Main Tab
    public static double getMatchTime() {
        return matchTimeEntry.getDouble(matchTimeDefault);
    }

    public static boolean getLineDetected() {
        return lineDetectedEntry.getBoolean(lineDetectedDefault);
    }

    // Shuffler - Drive Tab
    public static double getTargetDriveDistance() {
        return driveTargetDistanceEntry.getDouble(driveTargetDistanceDefault);
    }

    // OI
    public static double getYdeadZone() {
        return yDeadZoneEntry.getDouble(yDeadZoneDefault);
    }

    public static double getXdeadZone() {
        return xDeadZoneEntry.getDouble(xDeadZoneDefault);
    }

    public static double getZdeadZone() {
        return zDeadZoneEntry.getDouble(zDeadZoneDefault);
    }

    public static double getYsensitivity() {
        return ySensitivityEntry.getDouble(ySensitivityDefault);
    }

    public static double getXsensitivity() {
        return xSensitivityEntry.getDouble(xSensitivityDefault);
    }

    public static double getZsensitivity() {
        return zSensitivityEntry.getDouble(zSensitivityDefault);
    }

    // Subsystems - MecDriver
    public static ControlOrientation getControlOrientation() {
        String controlOrientationDefaultString = controlOrientationDefault.toString();
        String controlOrientationString = controlOrientationEntry.getString(controlOrientationDefaultString);
        return ControlOrientation.valueOf(controlOrientationString);
    }

}
