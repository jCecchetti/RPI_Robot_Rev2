package kinematics;

import adafruithat.AdafruitServoHat;
import util.Constants;
import util.Position;

public class Robot extends Thread{
	
	AdafruitServoHat servoHat;
	final int servoHATAddress = 0X40;
	private boolean running = true;
	
	Leg frontLeftLeg, frontRightLeg, hindLeftLeg, hindRightLeg;
	public Leg legs[] = {frontLeftLeg, frontRightLeg, hindLeftLeg, hindRightLeg};
	public Position GlobalRobotPos = new Position(0,0,0,0,0,0);
	
	
	public Robot(){
		servoHat = new AdafruitServoHat(servoHATAddress);
		frontLeftLeg = new Leg(servoHat.getServo("S13"), servoHat.getServo("S14"), servoHat.getServo("S15"), true);
		//frontRightLeg = new Leg(servoHat.getServo("S09"), servoHat.getServo("S10"), servoHat.getServo("S11"), false);
		//hindLeftLeg = new Leg(servoHat.getServo("S05"), servoHat.getServo("S06"), servoHat.getServo("S07"), true);
		//hindRightLeg = new Leg(servoHat.getServo("S01"), servoHat.getServo("S02"), servoHat.getServo("S03"), false);
	}
	
	public void setStartPosition(){
		//frontLeftLeg.calculateAngles(0.0, 0.0, -4.0);
		frontLeftLeg.setFootPos(new Position(2,0,-4.0,0,0,0));
	}
	
	public void init(){
		setStartPosition();
	}
	
	public void stopRobot(){
		servoHat.stopAll();//stop all servos
	}
	
	public void update(){
		
	}
	
	public void render(){
		
	}
	
	public Position calcNewRobotPos(){
		return new Position(0,0,0,0,0,0);
	}
	

	@Override
	public void run() {
			init();
			
			double fps = Constants.UPDATESPERSECOND;// updates per second
			double timePerTick = 1000000000/fps;
			double delta = 0;
			long now;
			long lastTime = System.nanoTime();
			long timer = 0;
			int ticks = 0;
			long totalSeconds = 0;
			
			while(running){
				now = System.nanoTime();
				delta += (now - lastTime) / timePerTick;
				timer += now - lastTime;
				lastTime = now;
				
				if(delta >= 1){
					update();
					render();
					ticks++;
					delta--;
					if(totalSeconds > 3) running = false;
				}
				
				if(timer >= 1000000000){
					System.out.println("Ticks and Frames " + ticks);
					timer = 0;
					ticks = 0;
					totalSeconds++;
				}
			}
			
			System.out.println("Done!!!");
			
			stopRobot();
	}

}
