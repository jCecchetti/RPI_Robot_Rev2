import kinematics.Robot;
import util.Trig;

public class Main{
	
	public static final double FEMUR = 0.7975;
	public static final double TIBIA = 2.3585;
	public static final double TARSUS = 3.4;
	public static final double SERVOWIDTH = 1.927;
	
	private static double hipAngle = 90;
	private static double kneeAngle = 90;
	private static double ankleAngle = 90;
	private static float hipCenter = 0;
	private static float kneeCenter = 0;
	private static float ankleCenter = 0;
	

	public static void main(String[] args) {
		Robot robot = new Robot();
		robot.start();
		double x = 1;
		double y = 3;
		double z = -4;
		
		
		double C = Math.sqrt(z*z + x*x);
		double c = 90 + Trig.atan(FEMUR/SERVOWIDTH*2);//Okay
		double A = Math.sqrt(FEMUR*FEMUR + SERVOWIDTH*SERVOWIDTH/4);
		hipAngle = hipCenter + 180 - c - Trig.asin(A*Trig.sin(c)/C) + Trig.acos(-z/C) - Trig.atan(SERVOWIDTH/FEMUR/2);
		double B = C * Trig.sin(180 - c - Trig.asin(A*Trig.sin(c)/C))/Trig.sin(c);
		double L = Math.sqrt(B*B + y*y);
		double absoluteKneeAngle = Trig.acos((L*L + TIBIA*TIBIA - TARSUS*TARSUS)/(2*L*TIBIA));
		kneeAngle = kneeCenter + absoluteKneeAngle - Trig.atan2(y, B);
		ankleAngle = ankleCenter + Trig.acos((TIBIA*TIBIA + TARSUS*TARSUS - L*L)/(2*TIBIA*TARSUS));
		
		//System.out.println(hipAngle);
		//System.out.println(kneeAngle);
		//System.out.println(ankleAngle);

		
	}	
}