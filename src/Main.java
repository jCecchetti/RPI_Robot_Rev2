import input.KeyManager;
import kinematics.Body;
import kinematics.Robot;
import util.Position;
import util.Trig;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import input.Joystick;

public class Main{
	
	//static Joystick joystick;

	public static void main(String[] args) {

		//joystick = new Joystick();
		//mathTest(0,0,-5.0, true);
		//mathTest(0,0,-5.0, false);
		Robot robot = new Robot();
		//robot.start();
		System.out.println(Body.getLocalCornerPos(new Position(0,0,0,0,32,10))[0].x);
		//mathTest(0.0,0.0,-4.0,false);
		//for(int i = 0; i < 1000; i++){
			//KeyManager.tick();
			//if(KeyManager.w)System.out.print("s");
			//joystick.updateJoystick();
		//}
	
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
		
		if(!left) y = -y;
		
		double C = Math.sqrt(z*z + y*y);
		
		double c = 90 + Trig.atan(FEMUR/SERVOWIDTH*2);//Okay
		double A = Math.sqrt(FEMUR*FEMUR + SERVOWIDTH*SERVOWIDTH/4);
		if((left && y > 0) || (!left && y > 0)) hipAngle = hipCenter - (180 - c - Trig.asin(A*Trig.sin(c)/C) + Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2));
		else hipAngle = hipCenter - (180 - c - Trig.asin(A*Trig.sin(c)/C) - Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2));
		if(!left){
			if(y > 0) hipAngle = hipCenter - (hipAngle - hipCenter);
			else hipAngle =  hipCenter + (hipCenter - hipAngle);
		}
		double B = C * Trig.sin(180 - c - Trig.asin(A*Trig.sin(c)/C))/Trig.sin(c);
		double L = Math.sqrt(B*B + x*x);
		double absoluteKneeAngle = Trig.acos((L*L + TIBIA*TIBIA - TARSUS*TARSUS)/(2*L*TIBIA));
		kneeAngle = kneeCenter - (absoluteKneeAngle - Trig.atan2(x, B));
		if(left) ankleAngle = ankleCenter + (180 - Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS)));
		else ankleAngle = 180 - (ankleCenter + (180 - Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS))));
		
//		System.out.println(Trig.asin(A*Trig.sin(c)/C));//Angle of an intermediate triangle from front view
//		System.out.println(Trig.acos(-z/C));//Angle from hip to foot from front view
//		System.out.println(Trig.atan(SERVOWIDTH/FEMUR/2));
//		System.out.println(90 - Trig.atan(FEMUR/SERVOWIDTH*2));

		//System.out.println(hipAngle);
		//System.out.println(kneeAngle);
		//System.out.println(ankleAngle);
	}
}