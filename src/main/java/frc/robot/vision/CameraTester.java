
package frc.robot.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import java.util.concurrent.TimeUnit;

import frc.robot.helpers.Logger;


public class CameraTester {

    private static final long m_sleepSeconds = 1;

    public static UsbCamera captureCamera(int deviceNumber, int camResolutionHeight, int camResolutionWidth) {
        Logger.debug("Starting Camera Capture... Device: " + deviceNumber);

        CameraServer camServer = CameraServer.getInstance();
        UsbCamera cam = camServer.startAutomaticCapture(deviceNumber);
        cam.setResolution(camResolutionWidth, camResolutionHeight);

        return cam;
    }

    public static boolean testConnection(int deviceNumber) {
        Logger.debug("Checking for USB Camera Connection... Devivce: " + deviceNumber);

        boolean cameraIsConnected = false;
        UsbCamera testCam = new UsbCamera("Test USB Camera " + deviceNumber, deviceNumber);
        try {
            Logger.debug("Waiting for Test USB Camera " + deviceNumber + " to connect...");
            try {
                TimeUnit.SECONDS.sleep(m_sleepSeconds);
            }
            catch (InterruptedException e) {
                Logger.debug("Swallowed InterruptedException: " + e);
            }
            cameraIsConnected = testCam.isConnected();
        }
        finally {
            testCam.close();
        }
        if (!cameraIsConnected) {
            Logger.debug("USB Camera " + deviceNumber + " not found, disabled!");
        }
        return cameraIsConnected;
    }

}
