/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helpers;

/**
 * The values (magnitude, angle, rotation) needed to drive using the polar method.
 */
public class PolarMovement {

    public double magnitude = 0;
    public double angle = 0;
    public double rotation = 0;

    public static double calculateMagnitude(double x, double y) {
        // Joystick will give x & y in a range of -1 <= 0 <= 1
        // The magnitude indicates how fast the robot should be driving.
        // Use the distance formula: s = sqrt(x^2 = y^2)
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
  
    public static double calculateAngle(double x, double y) {
        // Joystick will give x & y in a range of -1 <= 0 <= 1
        // The direction indicates in what angle the robot should move. This is not rotation.
        //      0 degrees means go straight.
        //      180 degrees means back up.
        //      90 degrees means go to the right.
        // Use trigonometry. Tangent (angle) = opposite (in our case x) / adjacent (in our case y)
        // We have x & y, solve for angle by taking the inverse tangent. angle = tangent^-1(x/y)
        // Since this includes a division we need logic to handle things when x & y are 0.
        double angle = 0;

        x = -x;
        y = -y;

        if (y == 0) {
            if (x > 0) angle = 90;
            if (x < 0) angle = -90;
        }
        else if (x == 0) {
            if (y < 0) angle = 180;
        }
        else
        {
            angle = Math.atan2(x, y) * 180/Math.PI;
        }

        return angle;
    }

}
