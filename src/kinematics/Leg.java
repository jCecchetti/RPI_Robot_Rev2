package kinematics;

import adafruithat.AdafruitServo;
import util.Position;
import util.Trig;

public class Leg {
	
	public static final double FEMUR = 6.0;
	public static final double TIBIA = 6.0;
	public static final double TARSUS = 2.0;
	public static final double SERVOWIDTH = 1.0;
	
	private double hipAngle = 90;
	private double kneeAngle = 90;
	private double ankleAngle = 90;
	private float hipCenter = 90;
	private float kneeCenter = 90;
	private float ankleCenter = 0;
	
	
	private AdafruitServo servo1, servo2, servo3;
	
	float minimumPulseWidth = 0.6f;
	float neutralPulseWidth = 1.5f;
	float maximumPulseWidth = 2.4f;
	
	boolean left;
	
	public Leg(AdafruitServo servo1, AdafruitServo servo2, AdafruitServo servo3, boolean left){
		servo1.setOperatingLimits(minimumPulseWidth, neutralPulseWidth, maximumPulseWidth);
		servo2.setOperatingLimits(minimumPulseWidth, neutralPulseWidth, maximumPulseWidth);
		servo3.setOperatingLimits(minimumPulseWidth, neutralPulseWidth, maximumPulseWidth);
		servo1.setPositionRange(0.0f, 180.0f);
		servo2.setPositionRange(0.0f, 180.0f);
		servo3.setPositionRange(0.0f, 180.0f);
		
		this.servo1 = servo1;
		this.servo2 = servo2;
		this.servo3 = servo3;
		this.left = left;
	}
	
	public boolean setFootPos(Position pos){
		calculateAngles(pos.x, pos.y, pos.z);
		System.out.println(hipAngle);
		System.out.println(kneeAngle);
		System.out.println(ankleAngle);
		servo1.setPosition((float) hipAngle);
		servo2.setPosition((float) kneeAngle);
		servo3.setPosition((float) ankleAngle);
		return true;
	}
	
	public void calculateAngles(double x, double y, double z){
		//if(!left) x = -x;
		
		double C = Math.sqrt(z*z + x*x);
		double c = 90 + Trig.atan(FEMUR/SERVOWIDTH*2);//Okay
		double A = Math.sqrt(FEMUR*FEMUR + SERVOWIDTH*SERVOWIDTH/4);
		hipAngle = hipCenter + 180 - c - Trig.asin(A*Trig.sin(c)/C) + Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2);
		double B = C * Trig.sin(180 - c - Trig.asin(A*Trig.sin(c)/C))/Trig.sin(c);
		double L = Math.sqrt(B*B + y*y);
		double absoluteKneeAngle = Trig.acos((L*L + TIBIA*TIBIA - TARSUS*TARSUS)/(2*L*TIBIA));
		kneeAngle = kneeCenter + absoluteKneeAngle - Trig.atan2(y, B);
		ankleAngle = ankleCenter + Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS));
		
		
		/*hipAngle = (float) (hipCenter + Math.atan2(z, y));
		kneeAngle = (float) (kneeCenter + Math.acos(((FEMUR*FEMUR + (Math.sqrt(z*z + y*y)-COXA)*(Math.sqrt(z*z + y*y)-COXA) - TIBIA*TIBIA)/
				(2*FEMUR*Math.sqrt((Math.sqrt(z*z + y*y) - COXA)*(Math.sqrt(z*z + y*y) - COXA))))));
		if(left){
			ankleAngle = (float) (ankleCenter + Math.acos((TIBIA*TIBIA + FEMUR*FEMUR - (Math.sqrt(z*z + y*y) - COXA)*(Math.sqrt(z*z + y*y) - COXA))/
					2*FEMUR*TIBIA));
		}
		else{
			ankleAngle = (float) (ankleCenter - Math.acos((TIBIA*TIBIA + FEMUR*FEMUR - (Math.sqrt(z*z + y*y) - COXA)*(Math.sqrt(z*z + y*y) - COXA))/
					2*FEMUR*TIBIA));
		}*/
	}

}
