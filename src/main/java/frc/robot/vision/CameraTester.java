
package frc.robot.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import java.util.concurrent.TimeUnit;

import frc.robot.consoles.Logger;


public class CameraTester {

    private static final long SLEEP_SECONDS = 1;

    public static UsbCamera captureCamera(int deviceNumber, int camResolutionHeight, int camResolutionWidth) {
        Logger.action("Starting Camera Capture... Device: " + deviceNumber);

        CameraServer camServer = CameraServer.getInstance();
        UsbCamera cam = camServer.startAutomaticCapture(deviceNumber);
        cam.setResolution(camResolutionWidth, camResolutionHeight);

        return cam;
    }

    public static boolean testConnection(int deviceNumber) {
        Logger.action("Checking for USB Camera Connection... Device: " + deviceNumber);

        boolean cameraIsConnected = false;
        UsbCamera testCam = new UsbCamera("Test USB Camera " + deviceNumber, deviceNumber);
        try {
            Logger.waiting("Waiting for Test USB Camera " + deviceNumber + " to connect...");
            try {
                TimeUnit.SECONDS.sleep(SLEEP_SECONDS);
            }
            catch (InterruptedException e) {
                Logger.warning("Swallowed InterruptedException: " + e);
            }
            cameraIsConnected = testCam.isConnected();
        }
        finally {
            testCam.close();
        }
        if (!cameraIsConnected) {
            Logger.warning("USB Camera " + deviceNumber + " not found, disabled!");
        }
        return cameraIsConnected;
    }

}
