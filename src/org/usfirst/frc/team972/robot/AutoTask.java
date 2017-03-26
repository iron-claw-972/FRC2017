package org.usfirst.frc.team972.robot;

public class AutoTask {
	public double endTime = 0;
	public double endPos = 0;
	public AutoTask(double pos, double curTime, double duration) {
		endPos = pos;
		endTime = duration + curTime;
	}
	public boolean run(double time) {
		if(time>=endTime) {
			//We have reached destination aprox. We can now run pure PID to get very close.
			DriveLoopRedundant.drive(true, 100000);
		} else{
			DriveLoopRedundant.drive(true, easeInOut(tweenCounter, 0, 100000, TimeToDestination));
		}
	}
	
	public static double easeInOut(double t, double b, double c, double d){ //accelarates up, gets to middle of time, then decelerates
		t /= d/2;
		if (t < 1) return c/2*t*t + b;
		t--;
		return -c/2 * (t*(t-2) - 1) + b;
	}
	
}
