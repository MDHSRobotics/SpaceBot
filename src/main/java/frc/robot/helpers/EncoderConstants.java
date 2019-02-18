
package frc.robot.helpers;

public class EncoderConstants {
    /**
     * Which PID slot to pull gains from. Starting 2018, you can choose from
     * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
     * configuration.
     */
    public static final int PID_SLOT_0 = 0;

    /*
     * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
     * now we just want the primary one.
     */
    public static final int PID_LOOP_PRIMARY = 0;

    /*
     * Set to zero to skip waiting for confirmation, set to nonzero to wait and
     * report to DS if action fails.
     */
    public static final int TIMEOUT_MS = 20;
    
    // The amount of native ticks per revolution (Tpr) in the CTRE MagEncoder
    public static final int MAG_ENCODER_TPR = 4096;

    // The amount of native ticks per revolution (Tpr) in the Redline Encoder
    public static final int REDLIN_ENCODER_TPR = 4096;

}
