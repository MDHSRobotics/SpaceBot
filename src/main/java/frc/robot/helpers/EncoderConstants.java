/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helpers;

/**
 * Add your docs here.
 */
public class EncoderConstants {

    /**
     * Which PID slot to pull gains from. Starting 2018, you can choose from
     * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
     * configuration.
     */
    public static final int kPIDSlot0 = 0;

    /*
     * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
     * now we just want the primary one.
     */
    public static final int kPIDLoopPrimary = 0;

    /*
     * set to zero to skip waiting for confirmation, set to nonzero to wait and
     * report to DS if action fails.
     */
    public static final int kTimeoutMs = 20;
    

    // the amount of native ticks per revolution (Tpr) in the CTRE MagEncoder
    public static final int kMagEncoderTpr = 4096;

    // the amount of native ticks per revolution (Tpr) in the Redline Encoder
    public static final int kRedlineEncoderTpr = 4096;

}

