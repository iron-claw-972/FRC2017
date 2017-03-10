package org.usfirst.frc.team972.robot;

import edu.wpi.cscore.*;

public class CameraStreaming {
	private static boolean camTogglePressed;
	private static boolean pressedLastTime = false;
	public static boolean useCam1 = true;
	public static boolean useCam3 = false; // HOPPER CAM
	public static int currentCamNum = 1, newCamNum = 1;;
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
		useCam3 = Robot.operatorJoystick.getRawButton(Constants.HOPPER_CAM_BUTTON);
//		camTogglePressed = Robot.leftJoystick.getRawButton(Constants.JOYSTICK_INVERSE_DRIVE_TOGGLE_BUTTON);
		camTogglePressed = Robot.gamepadJoystick.getRawButton(Constants.GAMEPAD_INVERSE_DRIVE_TOGGLE_BUTTON);
		if (camTogglePressed && !pressedLastTime) {
			useCam1 = !useCam1;
		}
		pressedLastTime = camTogglePressed;

		if (useCam3) {
			newCamNum = 3;
		} else {
			if (useCam1) {
				newCamNum = 1;
			} else {
				newCamNum = 2;
			}
		}

		currentCamNum = newCamNum;
		switch (currentCamNum) {
			case 1:
				cameraSink.setSource(usbCamera1);
				break;
			case 2:
				cameraSink.setSource(usbCamera2);
				break;
			case 3:
				cameraSink.setSource(usbCamera3);
				break;
			default:
				System.out.println("CAMERA DEFAULTING ERROR!!!");
				break;
		}
		System.out.println("Current CAM: " + currentCamNum);
	}
}
