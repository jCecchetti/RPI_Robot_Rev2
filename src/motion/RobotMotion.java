package motion;

import input.KeyManager;
import kinematics.Body;
import kinematics.Leg;
import util.Constants;
import util.Position;

public class RobotMotion {
	
	private Position globalRobotPos = new Position(0,0,6.0,0,0,0);
	private Position localRobotPos = new Position(0,0,0,0,0,0);
	private Position[] globalFeetPos = {new Position(Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0,0,0,0)};

	private double robotSpeed = 1;// in/s
	private double turningSpeed = 45;// degrees/s
	private double updateRate = Constants.UPDATESPERSECOND;
	private Leg frontLeftLeg, frontRightLeg, hindLeftLeg, hindRightLeg;
	private Leg[] legs;
	
	public RobotMotion(Leg frontLeftLeg, Leg frontRightLeg, Leg hindLeftLeg, Leg hindRightLeg){
		this.legs = legs;
		this.frontLeftLeg = frontLeftLeg;
		this.frontRightLeg = frontRightLeg;
		this.hindLeftLeg = hindLeftLeg;
		this.hindRightLeg = hindRightLeg;
	}
	
	public void updateGlobalRobotPos(){
		KeyManager.tick();
		if(KeyManager.w) globalRobotPos.y += robotSpeed/updateRate;
		if(KeyManager.s) globalRobotPos.y -= robotSpeed/updateRate;
		if(KeyManager.d) globalRobotPos.x += robotSpeed/updateRate;
		if(KeyManager.a) globalRobotPos.x -= robotSpeed/updateRate;
		if(KeyManager.j) localRobotPos.yaw += turningSpeed/updateRate;
		if(KeyManager.l) localRobotPos.yaw -= turningSpeed/updateRate;
	}
	
	private enum leg{frontLeft, frontRight, rearLeft, rearRight}
	
	private leg steppingLeg = leg.frontLeft;
	
	public void handleLegs(){
		Position[] corners = Body.getGlobalCornerPos(localRobotPos, globalRobotPos);
//		legs[0].setFootPos(Body.getRelativeFootPos(corners[0], globalFeetPos[0]));
//		legs[1].setFootPos(Body.getRelativeFootPos(corners[1], globalFeetPos[1]));
//		legs[2].setFootPos(Body.getRelativeFootPos(corners[2], globalFeetPos[2]));
//		legs[3].setFootPos(Body.getRelativeFootPos(corners[3], globalFeetPos[3]));
		frontLeftLeg.setFootPos(Body.getRelativeFootPos(corners[0], globalFeetPos[0]));
		frontRightLeg.setFootPos(Body.getRelativeFootPos(corners[1], globalFeetPos[1]));
		hindLeftLeg.setFootPos(Body.getRelativeFootPos(corners[2], globalFeetPos[2]));
		hindRightLeg.setFootPos(Body.getRelativeFootPos(corners[3], globalFeetPos[3]));
		switch(steppingLeg){
			case frontLeft:
				if(globalRobotPos.x < 2) globalRobotPos.x += robotSpeed/updateRate;
				else steppingLeg = leg.rearLeft;
			break;
			case rearLeft:
				
			break;
			case frontRight:
				
			break;
			case rearRight:
				
			break;	
		
		}
	}
	
	

}
