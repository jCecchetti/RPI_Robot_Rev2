import input.KeyManager;
import kinematics.Body;
import kinematics.Robot;
import util.Position;
import util.Trig;

public class Main{
	

	public static void main(String[] args) {
		Position[] corners = Body.getGlobalCornerPos(localRobotPos, globalRobotPos);
		Body.getRelativeFootPos(corners[0], globalFeetPos[0]);
		//mathTest(0,0,-5.0, true);
		//mathTest(0,0,-5.0, false);
		Robot robot = new Robot();
		robot.start();
//		for(int i = 0; i < 1000; i++){
//			KeyManager.tick();
//			if(KeyManager.w)System.out.print("s");
//		}
	
	}	
	

	public static final double FEMUR = 1.6;
	public static final double TIBIA = 2.3585;
	public static final double TARSUS = 3.4;
	public static final double SERVOWIDTH = 1.927;
	
	public static void mathTest(double x, double y, double z, boolean left){
		
		double hipAngle = 90;
		double kneeAngle = 90;
		double ankleAngle = 90;
		float hipCenter = 90;
		float kneeCenter = 90;
		float ankleCenter = 7;
		
		if(!left) x = -x;
		
		double C = Math.sqrt(z*z + x*x);
		
		double c = 90 + Trig.atan(FEMUR/SERVOWIDTH*2);//Okay
		double A = Math.sqrt(FEMUR*FEMUR + SERVOWIDTH*SERVOWIDTH/4);
		if((left && x > 0) || (!left && x > 0)) hipAngle = hipCenter - (180 - c - Trig.asin(A*Trig.sin(c)/C) + Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2));
		else hipAngle = hipCenter - (180 - c - Trig.asin(A*Trig.sin(c)/C) - Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2));
		if(!left){
			if(x > 0) hipAngle = hipCenter - (hipAngle - hipCenter);
			else hipAngle =  hipCenter + (hipCenter - hipAngle);
		}
		double B = C * Trig.sin(180 - c - Trig.asin(A*Trig.sin(c)/C))/Trig.sin(c);
		double L = Math.sqrt(B*B + y*y);
		double absoluteKneeAngle = Trig.acos((L*L + TIBIA*TIBIA - TARSUS*TARSUS)/(2*L*TIBIA));
		kneeAngle = kneeCenter - (absoluteKneeAngle - Trig.atan2(y, B));
		if(left) ankleAngle = ankleCenter + (180 - Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS)));
		else ankleAngle = 180 - (ankleCenter + (180 - Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS))));

//		System.out.println(Trig.asin(A*Trig.sin(c)/C));//Angle of an intermediate triangle from front view
//		System.out.println(Trig.acos(-z/C));//Angle from hip to foot from front view
//		System.out.println(Trig.atan(SERVOWIDTH/FEMUR/2));
//		System.out.println(90 - Trig.atan(FEMUR/SERVOWIDTH*2));

		System.out.println(hipAngle);
		System.out.println(kneeAngle);
		System.out.println(ankleAngle);
	}
}