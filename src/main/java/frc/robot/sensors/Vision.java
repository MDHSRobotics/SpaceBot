
package frc.robot.sensors;

import frc.robot.Brain;
import frc.robot.Robot;


public class Vision {

    private static final double MINIMUM_AREA = (Robot.CAM_RESOLUTION_HEIGHT / 3) ^ 2;

    private static final double ANGLE_TARGET = 90;
    private static final double CENTER_X_TARGET = Robot.CAM_RESOLUTION_WIDTH / 2;

    private static final double ANGLE_THRESHOLD = 10;
    private static final double CENTER_X_THRESHOLD = Robot.CAM_RESOLUTION_WIDTH / 64;

    public static boolean lineDetected() {
        double area = Brain.getFrontLineArea();
        boolean detected = lineDetected(area);
        return detected;
    }

    public static boolean lineDetected(double area) {
        boolean detected = area >= MINIMUM_AREA;
        return detected;
    }

    public static boolean isStraight() {
        double angle = Brain.getFrontLineAngle();
        boolean straight = isStraight(angle);
        return straight;
    }

    public static boolean isStraight(double angle) {
        boolean straight = (ANGLE_TARGET - ANGLE_THRESHOLD <= angle || angle <= -ANGLE_TARGET + ANGLE_THRESHOLD);
        return straight;
    }

    public static boolean isCentered() {
        double centerX = Brain.getFrontLineXcenter();
        boolean centered = isCentered(centerX);
        return centered;
    }

    public static boolean isCentered(double centerX) {
        boolean centered = (CENTER_X_TARGET - CENTER_X_THRESHOLD <= centerX && centerX <= CENTER_X_TARGET + CENTER_X_THRESHOLD);
        return centered;
    }

    public static double getCorrectedZ() {
        double angle = Brain.getFrontLineAngle();
        return ANGLE_TARGET + angle;
    }

    public static double getCorrectedX() {
        double centerX = Brain.getFrontLineXcenter();
        return CENTER_X_TARGET - centerX;
    }

}
