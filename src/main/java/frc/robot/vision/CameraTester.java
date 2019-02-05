
package frc.robot.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import java.util.concurrent.TimeUnit;

import frc.robot.helpers.Logger;


public class CameraTester {

    private static final long m_sleepSeconds = 1;

    public static UsbCamera initCamera(int deviceNumber, int camResolutionHeight, int camResolutionWidth) {
        UsbCamera cam = null;
        boolean isConnected = CameraTester.testCameraConnection(deviceNumber);
        if (!isConnected) {
            Logger.debug("No USB Camera Found, Disabling Camera: " + deviceNumber);
        }
        else {
            Logger.debug("Starting Camera Capture... Device: " + deviceNumber);
            CameraServer camServer = CameraServer.getInstance();
            cam = camServer.startAutomaticCapture();
            cam.setResolution(camResolutionWidth, camResolutionHeight);
        }
        return cam;
    }

    public static boolean testCameraConnection(int deviceNumber) {
        Logger.debug("Checking for USB Camera Connection... Device: " + deviceNumber);

        boolean cameraIsConnected = false;
        UsbCamera testCam = new UsbCamera("Test Camera " + deviceNumber, deviceNumber);
        try {
            Logger.debug("Waiting for the Camera " + deviceNumber + " to connect...");
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
        return cameraIsConnected;
    }

}
