package org.usfirst.frc.team972.robot;

import com.ctre.CANTalon;

public class AutoDriveTask {
	private static TweenTask currentTask[] = new TweenTask[2]; // Up to 2 driving tasks at a time... 2 for each wheel!
	private static double timeCounter = 0;
	
	public static void runTask() {
		timeCounter = timeCounter + 0.02; //Time is incremented at 20hz
		for(int i=0; i<2; i++){
			if(currentTask[i] != null) {
				boolean result = currentTask[i].run(timeCounter);
				if(result) {
					currentTask[i] = null; // task has been finished.
				}
			}
		}
	}
	public static void killTask() {
		timeCounter = 0;
		for(int i=0; i<2; i++) {
			currentTask[i] = null;
		}
	}
	public static void addTask(double pos, double dur, CANTalon can) {
			System.out.println("Adding new task");
			TweenTask task = new TweenTask(pos, timeCounter, dur, can);
			for(int i=0; i<2; i++){
				if(currentTask[i] == null) {
					
					//Check if can is available
					for(int a=0; a<2; a++){
						if((currentTask[a] != null) && (currentTask[a].getCan().getDeviceID() == can.getDeviceID())) {
							System.out.println("Task Running on Can Currently");
							return;
						}
					}
					
					currentTask[i] = task;
					return;
				}
			}
			System.out.println("Task Not Added, Task Manager Full");
	}
}
