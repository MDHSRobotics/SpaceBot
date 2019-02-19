
package frc.robot.consoles;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import edu.wpi.first.wpilibj.DriverStation;


// This is a helper class for colorful console logging.
public class Logger {

    // ANSI Colors
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println 
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Logger() {
        // Support ANSI colors on Windows 10 consoles
        // https://stackoverflow.com/questions/52767585/how-can-you-use-vt100-escape-codes-in-java-on-windows
        String osName = System.getProperty("os.name");
        System.out.println("Current OS: " + osName);
        if (osName.startsWith("Windows")) {
            System.out.println("Windows OS - Attempting to support VT100...");

            // Set output mode to handle virtual terminal sequences
            Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
            DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
            HANDLE hOut = (HANDLE)GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});

            DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
            Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
            GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});

            int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
            DWORD dwMode = p_dwMode.getValue();
            dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
            Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
            SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
        }
        System.out.println("If this is colorful, you can use ANSI colors in this terminal: \033[0;31m A \033[0;32m N \033[0;33m S \033[0;34m I \u001B[0m!");
        System.out.println("Or if this is colorful, you can use ANSI colors in this terminal: \u001B[31m A \u001B[32m N \u001B[33m S \u001B[34m I \033[0m!");
        System.out.println("<font color=\"red\">This is red some text!</font>");
    }

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
