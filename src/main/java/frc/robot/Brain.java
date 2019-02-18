
package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.subsystems.MecDriver.DriveOrientation;
import frc.robot.OI.ControlStick;


// This class contains all the shared NetworkTableEntries for the Robot,
// their default values, and methods for retrieving their current values
public class Brain {

    //----------------//
    // Default Values //
    //----------------//

    // Shuffler - Main Tab
    public static double matchTimeDefault = 0;

    // Shuffler - Drive Tab
    public static double driveTargetDistanceDefault = 2.0;

    // OI
    public static ControlStick controlStickDefault = ControlStick.XBOX;

    // OI - Joystick
    public static double yDeadZoneDefault = .1;
    public static double xDeadZoneDefault = .1;
    public static double zDeadZoneDefault = .5;
    public static double ySensitivityDefault = .5;
    public static double xSensitivityDefault = .5;
    public static double zSensitivityDefault = .5;

    // OI - Xbox Thumbsticks
    public static double yLeftDeadZoneDefault = .001;
    public static double xLeftDeadZoneDefault = .001;
    public static double yRightDeadZoneDefault = .001;
    public static double xRightDeadZoneDefault = .001;
    public static double yLeftSensitivityDefault = .5;
    public static double xLeftSensitivityDefault = .5;
    public static double yRightSensitivityDefault = .5;
    public static double xRightSensitivityDefault = .5;

    // Vision - LinePipeline
    public static double hueMinDefault = 0;
    public static double hueMaxDefault = 180;
    public static double saturationMinDefault = 0;
    public static double saturationMaxDefault = 146;
    public static double valueMinDefault = 232;
    public static double valueMaxDefault = 255;

    // Vision - LineDetector
    public static double hatchLineAreaDefault = 0;
    public static double hatchLineAngleDefault = 0;
    public static double hatchLineXcenterDefault = 0;
    public static double hatchLineYcenterDefault = 0;
    public static boolean hatchLineDetectedDefault = false;

    // Subsystem - MecDriver
    public static DriveOrientation driveOrientationDefault = DriveOrientation.ROBOT;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    // Shuffler - Main Tab
    public static NetworkTableEntry matchTimeEntry;

    // Shuffler - Drive Tab
    public static NetworkTableEntry driveTargetDistanceEntry;

    // OI
    public static NetworkTableEntry controlStickEntry;

    // OI - Joystick
    public static NetworkTableEntry yDeadZoneEntry;
    public static NetworkTableEntry xDeadZoneEntry;
    public static NetworkTableEntry zDeadZoneEntry;
    public static NetworkTableEntry ySensitivityEntry;
    public static NetworkTableEntry xSensitivityEntry;
    public static NetworkTableEntry zSensitivityEntry;

    // OI - Xbox Thumbsticks
    public static NetworkTableEntry yLeftDeadZoneEntry;
    public static NetworkTableEntry xLeftDeadZoneEntry;
    public static NetworkTableEntry yRightDeadZoneEntry;
    public static NetworkTableEntry xRightDeadZoneEntry;
    public static NetworkTableEntry yLeftSensitivityEntry;
    public static NetworkTableEntry xLeftSensitivityEntry;
    public static NetworkTableEntry yRightSensitivityEntry;
    public static NetworkTableEntry xRightSensitivityEntry;

    // Vision - LinePipeline
    public static NetworkTableEntry hueMinEntry;
    public static NetworkTableEntry hueMaxEntry;
    public static NetworkTableEntry saturationMinEntry;
    public static NetworkTableEntry saturationMaxEntry;
    public static NetworkTableEntry valueMinEntry;
    public static NetworkTableEntry valueMaxEntry;

    // Vision - LineDetector
    public static NetworkTableEntry hatchLineAreaEntry;
    public static NetworkTableEntry hatchLineAngleEntry;
    public static NetworkTableEntry hatchLineXcenterEntry;
    public static NetworkTableEntry hatchLineYcenterEntry;
    public static NetworkTableEntry hatchLineDetectedEntry;


    // Subsystem - MecDriver
    public static NetworkTableEntry driveOrientationEntry;

    //---------//
    // Setters //
    //---------//

    // Shuffler - Main Tab
    public static void setMatchTime() {
        DriverStation ds = DriverStation.getInstance();
        double matchTime = ds.getMatchTime();
        matchTimeEntry.setDouble(matchTime);
    }

    // Shuffler - Drive Tab
    public static void setTargetDriveDistance(NetworkTableEntry entry) {
        double value = entry.getDouble(driveTargetDistanceDefault);
        driveTargetDistanceEntry.setDouble(value);
    }

    // OI
    public static void setControlStick(NetworkTableEntry entry) {
        String defaultString = Brain.controlStickDefault.toString();
        String value = entry.getString(defaultString);
        controlStickEntry.setValue(value);
    }

    public static void setControlStick(ControlStick stick) {
        String value = stick.toString();
        controlStickEntry.setValue(value);
    }

    // OI - Joystick
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

    // OI - Xbox Thumbsticks
    public static void setYleftDeadZone(NetworkTableEntry entry) {
        double value = entry.getDouble(yLeftDeadZoneDefault);
        yLeftDeadZoneEntry.setDouble(value);
    }

    public static void setXleftDeadZone(NetworkTableEntry entry) {
        double value = entry.getDouble(xLeftDeadZoneDefault);
        xLeftDeadZoneEntry.setDouble(value);
    }

    public static void setYrightDeadZone(NetworkTableEntry entry) {
        double value = entry.getDouble(yRightDeadZoneDefault);
        yRightDeadZoneEntry.setDouble(value);
    }

    public static void setXrightDeadZone(NetworkTableEntry entry) {
        double value = entry.getDouble(xRightDeadZoneDefault);
        xRightDeadZoneEntry.setDouble(value);
    }

    public static void setYleftSensitivity(NetworkTableEntry entry) {
        double value = entry.getDouble(yLeftSensitivityDefault);
        yLeftSensitivityEntry.setDouble(value);
    }

    public static void setXleftSensitivity(NetworkTableEntry entry) {
        double value = entry.getDouble(xLeftSensitivityDefault);
        xLeftSensitivityEntry.setDouble(value);
    }

    public static void setYrightSensitivity(NetworkTableEntry entry) {
        double value = entry.getDouble(yRightSensitivityDefault);
        yRightSensitivityEntry.setDouble(value);
    }

    public static void setXrightSensitivity(NetworkTableEntry entry) {
        double value = entry.getDouble(xRightSensitivityDefault);
        xRightSensitivityEntry.setDouble(value);
    }

    // Vision - LinePipeline
    public static void setHueMin(NetworkTableEntry entry) {
        double value = entry.getDouble(hueMinDefault);
        hueMinEntry.setDouble(value);
    }

    public static void setHueMax(NetworkTableEntry entry) {
        double value = entry.getDouble(hueMaxDefault);
        hueMaxEntry.setDouble(value);
    }

    public static void setSaturationMin(NetworkTableEntry entry) {
        double value = entry.getDouble(saturationMinDefault);
        saturationMinEntry.setDouble(value);
    }

    public static void setSaturationMax(NetworkTableEntry entry) {
        double value = entry.getDouble(saturationMaxDefault);
        saturationMaxEntry.setDouble(value);
    }

    public static void setValueMin(NetworkTableEntry entry) {
        double value = entry.getDouble(valueMinDefault);
        valueMinEntry.setDouble(value);
    }

    public static void setValueMax(NetworkTableEntry entry) {
        double value = entry.getDouble(valueMaxDefault);
        valueMaxEntry.setDouble(value);
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

    public static void setHatchLineDetected() {
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        hatchLineDetectedEntry.setBoolean(detected);
    }

    // Subsystems - MecDriver
    public static void setDriveOrientation(NetworkTableEntry entry) {
        String defaultString = Brain.driveOrientationDefault.toString();
        String value = entry.getString(defaultString);
        driveOrientationEntry.setValue(value);
    }

    public static void setDriveOrientation(DriveOrientation orientation) {
        String value = orientation.toString();
        driveOrientationEntry.setValue(value);
    }

    //---------//
    // Getters //
    //---------//

    // Shuffler - Main Tab
    public static double getMatchTime() {
        return matchTimeEntry.getDouble(matchTimeDefault);
    }

    // Shuffler - Drive Tab
    public static double getTargetDriveDistance() {
        return driveTargetDistanceEntry.getDouble(driveTargetDistanceDefault);
    }

    // OI
    public static ControlStick getControlStick() {
        String defaultString = controlStickDefault.toString();
        String stickString = controlStickEntry.getString(defaultString);
        return ControlStick.valueOf(stickString);
    }

    // OI - Joystick
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

    // OI - Xbox Thumbsticks
    public static double getYleftDeadZone() {
        return yLeftDeadZoneEntry.getDouble(yLeftDeadZoneDefault);
    }

    public static double getXleftDeadZone() {
        return xLeftDeadZoneEntry.getDouble(xLeftDeadZoneDefault);
    }

    public static double getYrightDeadZone() {
        return yRightDeadZoneEntry.getDouble(yRightDeadZoneDefault);
    }

    public static double getXrightDeadZone() {
        return xRightDeadZoneEntry.getDouble(xRightDeadZoneDefault);
    }

    public static double getYleftSensitivity() {
        return yLeftSensitivityEntry.getDouble(yLeftSensitivityDefault);
    }

    public static double getXleftSensitivity() {
        return xLeftSensitivityEntry.getDouble(xLeftSensitivityDefault);
    }

    public static double getYrightSensitivity() {
        return yRightSensitivityEntry.getDouble(yRightSensitivityDefault);
    }

    public static double getXrightSensitivity() {
        return xRightSensitivityEntry.getDouble(xRightSensitivityDefault);
    }

    // Vision - LinePipeline
    public static double getHueMin() {
        return hueMinEntry.getDouble(hueMinDefault);
    }

    public static double getHueMax() {
        return hueMaxEntry.getDouble(hueMaxDefault);
    }

    public static double getSaturationMin() {
        return saturationMinEntry.getDouble(saturationMinDefault);
    }

    public static double getSaturationMax() {
        return saturationMaxEntry.getDouble(saturationMaxDefault);
    }

    public static double getValueMin() {
        return valueMinEntry.getDouble(valueMinDefault);
    }

    public static double getValueMax() {
        return valueMaxEntry.getDouble(valueMaxDefault);
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

    public static boolean getLineDetected() {
        return hatchLineDetectedEntry.getBoolean(hatchLineDetectedDefault);
    }

    // Subsystems - MecDriver
    public static DriveOrientation getDriveOrientation() {
        String defaultString = driveOrientationDefault.toString();
        String orientationString = driveOrientationEntry.getString(defaultString);
        return DriveOrientation.valueOf(orientationString);
    }

}
