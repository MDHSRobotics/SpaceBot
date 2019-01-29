/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helpers;


/**
 * This is a helper class for robot logging.
 */
public class Logger {

    public static void debug(Object message) {
        System.out.println("DEBUG --> " + message);
    }

}
