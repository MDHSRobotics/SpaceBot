
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
    public static boolean hatchLineDetectedDefault = false;

    // Shuffler - Drive Tab
    public static double driveTargetDistanceDefault = 2.0;

    // OI
    public static double yDeadZoneDefault = .1;
    public static double xDeadZoneDefault = .1;
    public static double zDeadZoneDefault = .5;
    public static double ySensitivityDefault = .5;
    public static double xSensitivityDefault = .5;
    public static double zSensitivityDefault = .5;

    // Vision - LineDetector
    public static double hatchLineAreaDefault = 0;
    public static double hatchLineAngleDefault = 0;
    public static double hatchLineXcenterDefault = 0;
    public static double hatchLineYcenterDefault = 0;

    // Subsystem - MecDriver
    public static ControlOrientation controlOrientationDefault = ControlOrientation.FIELD;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    // Shuffler - Main Tab
    public static NetworkTableEntry matchTimeEntry;
    public static NetworkTableEntry hatchLineDetectedEntry;

    // Shuffler - Drive Tab
    public static NetworkTableEntry driveTargetDistanceEntry;

    // OI
    public static NetworkTableEntry yDeadZoneEntry;
    public static NetworkTableEntry xDeadZoneEntry;
    public static NetworkTableEntry zDeadZoneEntry;
    public static NetworkTableEntry ySensitivityEntry;
    public static NetworkTableEntry xSensitivityEntry;
    public static NetworkTableEntry zSensitivityEntry;

    // Vision - LineDetector
    public static NetworkTableEntry hatchLineAreaEntry;
    public static NetworkTableEntry hatchLineAngleEntry;
    public static NetworkTableEntry hatchLineXcenterEntry;
    public static NetworkTableEntry hatchLineYcenterEntry;

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

    public static void setHatchLineDetected() {
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        hatchLineDetectedEntry.setBoolean(detected);
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

    // Vision - LineDetector
    public static void setHatchLineArea(NetworkTableEntry entry) {
        double value = entry.getDouble(hatchLineAreaDefault);
        hatchLineAreaEntry.setDouble(value);
    }

    public static void setHatchLineArea(double value) {
        hatchLineAreaEntry.setDouble(value);
    }

    public static void setHatchLineAngle(NetworkTableEntry entry) {
        double value = entry.getDouble(hatchLineAngleDefault);
        hatchLineAngleEntry.setDouble(value);
    }

    public static void setHatchLineAngle(double value) {
        hatchLineAngleEntry.setDouble(value);
    }

    public static void setHatchLineXcenter(NetworkTableEntry entry) {
        double value = entry.getDouble(hatchLineXcenterDefault);
        hatchLineXcenterEntry.setDouble(value);
    }

    public static void setHatchLineXcenter(double value) {
        hatchLineXcenterEntry.setDouble(value);
    }

    public static void setHatchLineYcenter(NetworkTableEntry entry) {
        double value = entry.getDouble(hatchLineYcenterDefault);
        hatchLineYcenterEntry.setDouble(value);
    }

    public static void setHatchLineYcenter(double value) {
        hatchLineYcenterEntry.setDouble(value);
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
        return hatchLineDetectedEntry.getBoolean(hatchLineDetectedDefault);
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

    // Vision - LineDetector
    public static double getHatchLineArea() {
        return hatchLineAreaEntry.getDouble(hatchLineAreaDefault);
    }

    public static double getHatchLineAngle() {
        return hatchLineAngleEntry.getDouble(hatchLineAngleDefault);
    }

    public static double getHatchLineXcenter() {
        return hatchLineXcenterEntry.getDouble(hatchLineXcenterDefault);
    }

    public static double getHatchLineYcenter() {
        return hatchLineYcenterEntry.getDouble(hatchLineYcenterDefault);
    }

    // Subsystems - MecDriver
    public static ControlOrientation getControlOrientation() {
        String controlOrientationDefaultString = controlOrientationDefault.toString();
        String controlOrientationString = controlOrientationEntry.getString(controlOrientationDefaultString);
        return ControlOrientation.valueOf(controlOrientationString);
    }

}
