
package frc.robot.consoles;

import edu.wpi.first.wpilibj.DriverStation;


// This is a helper class for colorful console logging.
public class Logger {

    // TODO: Using ANSI didn't work on my Windows 7 machine
    // Try this on Windows 10 by following these instructions:
    // https://stackoverflow.com/questions/52767585/how-can-you-use-vt100-escape-codes-in-java-on-windows
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    // Grey text for constructor logging, etc.
    public static void setup(Object message) {
        System.out.println("setup --> " + message);
    }

    // Pink text for waiting
    public static void waiting(Object message) {
        System.out.println("waiting --> " + message);
    }

    // Green text for taking an action, like starting a command
    public static void action(Object message) {
        System.out.println("ACTION --> " + message);
    }

    // Orange text for info, values, etc.
    public static void info(Object message) {
        System.out.println("INFO --> " + message);
    }

    // Blue text for ending or interrupting, etc.    
    public static void ending(Object message) {
        System.out.println("ending --> " + message);
    }

    // Yellow text for warnings
    public static void warning(Object message) {
        DriverStation.reportWarning("WARNING --> " + message.toString(), false);
    }

    // Red text for warnings
    public static void error(Object message) {
        DriverStation.reportError("ERROR --> " + message.toString(), false);
    }

}
