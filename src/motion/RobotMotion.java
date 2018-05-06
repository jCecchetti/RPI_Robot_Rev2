package motion;

import input.KeyManager;
import kinematics.Body;
import kinematics.Leg;
import util.Constants;
import util.Position;

public class RobotMotion {
	
	private Position globalRobotPos = new Position(0,0,0,0,0,0);
	private Position localRobotPos = new Position(0,0,0,0,0,0);
	private Position[] globalFeetPos = {new Position(-Constants.BODYWIDTH/2, Constants.BODYLENGTH/2, 0,0,0,0),
			new Position(Constants.BODYWIDTH/2, Constants.BODYLENGTH/2, 0,0,0,0),
			new Position(-Constants.BODYWIDTH/2, -Constants.BODYLENGTH/2, 0,0,0,0),
			new Position(Constants.BODYWIDTH/2, -Constants.BODYLENGTH/2, 0,0,0,0)};
	private double robotSpeed = 1;// in/s
	private double turningSpeed = 45;// degrees/s
	private double updateRate = Constants.UPDATESPERSECOND;
	//private Leg frontLeftLeg, frontRightLeg, hindLeftLeg, hindRightLeg;
	private Leg[] legs;
	
	public RobotMotion(Leg[] legs){
		this.legs = legs;
//		frontLeftLeg = legs[0];
//		frontRightLeg = legs[1];
//		hindLeftLeg = legs[2];
//		hindRightLeg = legs[3];
	}
	
	public void updateGlobalRobotPos(){
		KeyManager.tick();
		if(KeyManager.w) globalRobotPos.y += updateRate/robotSpeed;
		if(KeyManager.s) globalRobotPos.y -= updateRate/robotSpeed;
		if(KeyManager.d) globalRobotPos.x += updateRate/robotSpeed;
		if(KeyManager.a) globalRobotPos.x -= updateRate/robotSpeed;
		if(KeyManager.j) localRobotPos.yaw += updateRate/turningSpeed;
		if(KeyManager.l) localRobotPos.yaw -= updateRate/turningSpeed;
	}
	
	private enum leg{frontLeft, frontRight, rearLeft, rearRight}
	
	private leg steppingLeg = leg.frontLeft;
	
	public void handleLegs(){
		Position[] corners = Body.getGlobalCornerPos(localRobotPos, globalRobotPos);
		legs[0].setFootPos(Body.getRelativeFootPos(corners[0], globalFeetPos[0]));
		legs[1].setFootPos(Body.getRelativeFootPos(corners[1], globalFeetPos[1]));
		legs[2].setFootPos(Body.getRelativeFootPos(corners[2], globalFeetPos[2]));
		legs[3].setFootPos(Body.getRelativeFootPos(corners[3], globalFeetPos[3]));
		switch(steppingLeg){
			case frontLeft:
				
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
