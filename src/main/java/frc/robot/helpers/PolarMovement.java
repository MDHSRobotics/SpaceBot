
package frc.robot.helpers;


// The values (magnitude, angle, rotation) needed to drive using polar coordinates.
public class PolarMovement {

    public double magnitude = 0;
    public double angle = 0;
    public double rotation = 0;

    public static double calculateMagnitude(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
  
    public static double calculateAngle(double x, double y) {
        return Math.atan2(x, y);
    }

}
