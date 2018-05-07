import kinematics.Robot;
import util.Trig;

public class Main{
	

	public static void main(String[] args) {
		

		mathTest(0,0,-5.0, true);
		Robot robot = new Robot();
		robot.start();
		
	
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
		
		double C = Math.sqrt(z*z + x*x);//Distance from hip to foot from front view
		double c = 90 + Trig.atan(FEMUR/SERVOWIDTH*2);//Angle from base of hip to side of knee
		double A = Math.sqrt(FEMUR*FEMUR + SERVOWIDTH*SERVOWIDTH/4);//distance from base of hip to side of knee
		if(x > 0) hipAngle = hipCenter - (180 - c - Trig.asin(A*Trig.sin(c)/C) + Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2));
		else hipAngle = hipCenter - (180 - c - Trig.asin(A*Trig.sin(c)/C) + Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2));
		
		System.out.println(Trig.asin(A*Trig.sin(c)/C));//Angle of an intermediate triangle from front view
		System.out.println(Trig.acos(-z/C));//Angle from hip to foot from front view
		System.out.println(Trig.atan(SERVOWIDTH/FEMUR/2));
		System.out.println(90 - Trig.atan(FEMUR/SERVOWIDTH*2));
		
		double B = C * Trig.sin(180 - c - Trig.asin(A*Trig.sin(c)/C))/Trig.sin(c);
		//System.out.println(B);
		double L = Math.sqrt(B*B + y*y);//Distance from knee to foot from side view
		double absoluteKneeAngle = Trig.acos((L*L + TIBIA*TIBIA - TARSUS*TARSUS)/(2*L*TIBIA));
		if(left) kneeAngle = kneeCenter - (absoluteKneeAngle - Trig.atan2(y, B));
		else kneeAngle = kneeCenter - (absoluteKneeAngle - Trig.atan2(y, B));
		if(left) ankleAngle = ankleCenter + (180 - Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS)));
		else ankleAngle = 180 - (ankleCenter + (180 - Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS))));

		//System.out.println(hipAngle);
		//System.out.println(kneeAngle);
		//System.out.println(ankleAngle);
	}
}