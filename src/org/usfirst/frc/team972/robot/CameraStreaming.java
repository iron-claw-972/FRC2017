package org.usfirst.frc.team972.robot;

import edu.wpi.cscore.*;

public class CameraStreaming {
	private static boolean pressed;
	private static boolean pressedLastTime = false;
	public static int whichCam = 1; // true = cam1, false = cam2
	int x = 0;

	public static UsbCamera usbCamera1;
	public static UsbCamera usbCamera2;
	public static UsbCamera usbCamera3;

	private static int NumberOfCameras = 3;

	private static VideoSink cameraSink;

	public static void init() {
		usbCamera1 = new UsbCamera("Cam 1", 0);
		usbCamera2 = new UsbCamera("Cam 2", 1);
		usbCamera3 = new UsbCamera("Cam 3", 2);

		cameraSink = CameraStreamingServer.getInstance().startAutomaticCapture(usbCamera1);
	}

	public static void periodic() {
		pressed = Robot.leftJoystick.getRawButton(Constants.INVERSE_DRIVE_TOGGLE_BUTTON);
		if (pressed && !pressedLastTime) {
			switch (whichCam) {
				case 1:
					cameraSink.setSource(usbCamera1);
					break;
				case 2:
					cameraSink.setSource(usbCamera2);
					break;
				case 3:
					cameraSink.setSource(usbCamera3);
					break;
			}
			whichCam++;
			if (whichCam > NumberOfCameras)
				whichCam = 1;
		}
		pressedLastTime = pressed;
	}
}
