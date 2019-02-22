
package frc.robot.helpers;

public class TalonConstants {
    
    //number of miliseconds that the talon can stay at peak current
    public static final int PEAK_CURRENT_DURATION = 40;

    //max amps that the talon can be supplied at peak
    public static final int PEAK_CURRENT_AMPS = 11;

    //max amps that the talon can be supplied continuously
    public static final int CONTINUOUS_CURRENT_LIMIT = 10;
    
    //minimum speed the talon can move forwards
    public static final int NOMINAL_OUTPUT_FORWARD = 0;

    //minimum speed the talon can move backwards
    public static final int NOMINAL_OUTPUT_REVERSE = 0;
    
    /*
     * Set to zero to skip waiting for confirmation, set to nonzero to wait and
     * report to DS if action fails.
     */
    public static final int TIMEOUT_MS = 20;


    //------------------------------encoder constants----------------------------------------//
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

    
    // The amount of native ticks per revolution (Tpr) in the CTRE MagEncoder
    public static final int MAG_ENCODER_TPR = 4096;

    // The amount of native ticks per revolution (Tpr) in the Redline Encoder
    public static final int REDLIN_ENCODER_TPR = 4096;

}
