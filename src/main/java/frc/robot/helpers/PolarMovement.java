/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helpers;


/**
 * The values (magnitude, angle, rotation) needed to drive using polar coordinates.
 */
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
