package org.usfirst.frc.team972.robot;

import com.ctre.CANTalon;

public class TweenTask {
	private double endTime = 0;
	private double endPos = 0;
	private double Time_To_Finalize = 2;
	public CANTalon can;
	public Easing easer;
	public TweenTask(double pos, double curTime, double duration, CANTalon _can) {
		endPos = pos;
		endTime = duration + curTime;
		can = _can;
		if(pos < (4096*2)) {
			easer = new Easing(){
				public double ease(double a, double b, double c, double d) {
					return easeQuad(a, b, c, d);
				}
			};
		} else {
			easer = new Easing(){
				public double ease(double a, double b, double c, double d) {
					return easerSin(a, b, c, d);
				}
			};
		}
	}
	public CANTalon getCan() {
		return can;
	}
	
	public boolean run(double time) {
		if(time>=endTime) {
			if((time - Time_To_Finalize) >= endTime) {
				System.out.println(can.getPosition() + " Final");
				DriveLoopRedundant.drive(can, false, 0); // Brake Motor
				
				AutonomousRedundancy.ready = true; // Signal that our task('s) have finished!
				
				return true;
			}
			can.setPID((double)120/Constants.PID_DIVISION_FACTOR, (double)35/(Constants.PID_DIVISION_FACTOR), (double)1000/Constants.PID_DIVISION_FACTOR);
			DriveLoopRedundant.drive(can, true, endPos); //After ease drive, we finalize the position with pure PID
			System.out.println(can.getPosition() + " Finalize");
			return false;
		} else{
			DriveLoopRedundant.drive(can, true, easer.ease(time, 0, endPos, endTime)); //Start pos can be 0 as we reset the encoders every new drive event
			System.out.println(can.getPosition() + " Ease Driving");
			return false;
		}
	}
	
	public interface Easing {
		double ease(double a, double b, double c, double d);
	}
	
		public double easerSin(double t, double b, double c, double d){ //accelarates up, gets to middle of time, then decelerates
			return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
		}
	
		public double easeQuad(double t, double b, double c, double d){ //accelarates up, gets to middle of time, then decelerates
			t /= d/2;
			if (t < 1) return c/2*t*t + b;
			t--;
			return -c/2 * (t*(t-2) - 1) + b;
		}
	
}
