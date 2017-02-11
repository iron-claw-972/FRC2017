package org.usfirst.frc.team972.robot;

import edu.wpi.cscore.*;

public class CameraStreaming {
	private static boolean pressed;
	private static boolean pressedLastTime = false;
	public static boolean whichCam = true; // true = cam1, false = cam2
	int x = 0;
	
	private static UsbCamera usbCamera1;
	private static UsbCamera usbCamera2;
	
	private static VideoSink cameraSink;
	
	public static void init() {
		usbCamera1 = new UsbCamera("Cam 1", 0);
		usbCamera2 = new UsbCamera("Cam 2", 1);
		cameraSink = CameraStreamingServer.getInstance().startAutomaticCapture(usbCamera1);
	}
	
	
	public static void periodic() {
		pressed = Robot.leftJoystick.getRawButton(Constants.INVERSE_DRIVE_TOGGLE_BUTTON);
		if(pressed && !pressedLastTime) {
			if(whichCam) {
				cameraSink.setSource(usbCamera2);
				System.out.println("Switch to Camera: 2");
			} else {
				cameraSink.setSource(usbCamera1);
				System.out.println("Switch to Camera: 1");
			}			
			whichCam = !whichCam;
		}
		pressedLastTime = pressed;
	}
}
