package motion;

import java.awt.Color;
import java.awt.Graphics;

import input.KeyManager;
import kinematics.Body;
import kinematics.Leg;
import util.Constants;
import util.Position;
import util.Timer;

public class RobotMotion {
	
	private Position globalRobotPos = new Position(0,0,6.5,0,0,0);
	private Position localRobotPos = new Position(0,0,0,0,0,0);
	private Position[] globalFeetPos = {new Position(Constants.BODYLENGTH/2, Constants.BODYWIDTH/2 + 1, 0,0,0,0),
			new Position(Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2 - 1, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, Constants.BODYWIDTH/2 + 1, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2 - 1, 0,0,0,0)};
	private Position[] globalCornerPos = {new Position(Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0,0,0,0)};

	private Position[] lastGlobalCornerPos = {new Position(Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0,0,0,0),
			new Position(-Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0,0,0,0)};

	private double robotSpeed = 1;// in/s
	private double currentRobotSpeed = 0;
	private double turningSpeed = 45;// degrees/s
	private double updateRate = Constants.UPDATESPERSECOND;
	private Leg frontLeftLeg, frontRightLeg, hindLeftLeg, hindRightLeg;
	private Leg[] legs;
	private double stepTime = 1.0;
	private double stepLength = 0;
	private double stepHeight = 1;
	private Timer timer = new Timer();
	public boolean end = false;

	public RobotMotion(Leg frontLeftLeg, Leg frontRightLeg, Leg hindLeftLeg, Leg hindRightLeg){
		this.legs = legs;
		this.frontLeftLeg = frontLeftLeg;
		this.frontRightLeg = frontRightLeg;
		this.hindLeftLeg = hindLeftLeg;
		this.hindRightLeg = hindRightLeg;
	}
	
	public void updateGlobalRobotPos(){
		KeyManager.tick();
		if(KeyManager.w) {
			globalRobotPos.x += robotSpeed/updateRate;
			currentRobotSpeed = robotSpeed; 
		}
		else if(KeyManager.s) {
			globalRobotPos.x -= robotSpeed/updateRate;
			currentRobotSpeed = -robotSpeed; 
		}
		else currentRobotSpeed = 0; 
		if(KeyManager.d) globalRobotPos.y += robotSpeed/updateRate;
		if(KeyManager.a) globalRobotPos.y -= robotSpeed/updateRate;
		if(KeyManager.j) localRobotPos.yaw += turningSpeed/updateRate;
		if(KeyManager.l) localRobotPos.yaw -= turningSpeed/updateRate;
		if(KeyManager.i) end = true;
	}
	
	private enum leg{frontLeft, frontRight, rearLeft, rearRight}
	
	private leg steppingLeg = leg.frontLeft;
	private leg lastSteppingLeg = leg.frontLeft;
	
	public void handleLegs(){
		updateGlobalRobotPos();
		globalCornerPos = Body.getGlobalCornerPos(localRobotPos, globalRobotPos);
//		legs[0].setFootPos(Body.getRelativeFootPos(globalCornerPos[0], globalFeetPos[0]));
//		legs[1].setFootPos(Body.getRelativeFootPos(globalCornerPos[1], globalFeetPos[1]));
//		legs[2].setFootPos(Body.getRelativeFootPos(globalCornerPos[2], globalFeetPos[2]));
//		legs[3].setFootPos(Body.getRelativeFootPos(globalCornerPos[3], globalFeetPos[3]));
		frontLeftLeg.setFootPos(Body.getRelativeFootPos(globalCornerPos[0], globalFeetPos[0]));
		frontRightLeg.setFootPos(Body.getRelativeFootPos(globalCornerPos[1], globalFeetPos[1]));
		hindLeftLeg.setFootPos(Body.getRelativeFootPos(globalCornerPos[2], globalFeetPos[2]));
		hindRightLeg.setFootPos(Body.getRelativeFootPos(globalCornerPos[3], globalFeetPos[3]));
		//globalRobotPos.x += robotSpeed/updateRate;
		stepLength = currentRobotSpeed*stepTime*2.0;
		switch(steppingLeg){
			case frontLeft:
				localRobotPos.y = -.5;
				localRobotPos.x = -.5;
				if(timer.get() < .25){
					globalFeetPos[0].x = lastGlobalCornerPos[0].x;
					globalFeetPos[0].z = stepHeight;
				}
				else if(timer.get() < 1){
					globalFeetPos[0].x = lastGlobalCornerPos[0].x + stepLength;
					globalFeetPos[0].z = 0;
				}
				else{
					steppingLeg = leg.rearLeft;
					timer.reset();
				}
				//if(HandleCoM.CoMStable(diagonalLeg1, diagonalLeg2, farLeg, CoMPos) 
			break;
			case rearLeft:
				localRobotPos.y = -.5;
				localRobotPos.x = .5;
				if(timer.get() < .25){
					globalFeetPos[2].x = lastGlobalCornerPos[2].x;
					globalFeetPos[2].z = stepHeight;
					//localRobotPos.y = -.5;
				}
				else if(timer.get() < 1){
					globalFeetPos[2].x = lastGlobalCornerPos[2].x + stepLength;
					globalFeetPos[2].z = 0;
					//localRobotPos.y = .5;
				}
				else{
					steppingLeg = leg.rearRight;
					timer.reset();
				}
			break;
			case frontRight:
				localRobotPos.y = .5;
				localRobotPos.x = -.5;
				if(timer.get() < .25){
					globalFeetPos[1].x = lastGlobalCornerPos[1].x;
					globalFeetPos[1].z = stepHeight;
				}
				else if(timer.get() < 1){
					globalFeetPos[1].x = lastGlobalCornerPos[1].x + stepLength;
					globalFeetPos[1].z = 0;
				}
				else{
					steppingLeg = leg.frontLeft;
					timer.reset();
				}
			break;
			case rearRight:
				localRobotPos.y = .5;
				localRobotPos.x = .5;
				if(timer.get() < .25){
					globalFeetPos[3].x = lastGlobalCornerPos[3].x;
					globalFeetPos[3].z = stepHeight;
					//localRobotPos.y = .5;
				}
				else if(timer.get() < 1){
					globalFeetPos[3].x = lastGlobalCornerPos[3].x + stepLength;
					globalFeetPos[3].z = 0;
					//localRobotPos.y = -.5;
				}
				else{
					steppingLeg = leg.frontRight;
					timer.reset();
				}
			break;
		}
		if(steppingLeg != lastSteppingLeg) lastGlobalCornerPos = globalCornerPos.clone();
		lastSteppingLeg = steppingLeg;
	}
	
	public void render(Graphics g){
		int xpos[] = {(int) (globalCornerPos[0].x*Constants.PixelsPerIn + Constants.x0), (int) (globalCornerPos[1].x*Constants.PixelsPerIn + Constants.x0),
				(int) (globalCornerPos[3].x*Constants.PixelsPerIn + Constants.x0), (int) (globalCornerPos[2].x*Constants.PixelsPerIn + Constants.x0)};
		int ypos[] = {(int) (globalCornerPos[0].y*Constants.PixelsPerIn + Constants.y0), (int) (globalCornerPos[1].y*Constants.PixelsPerIn + Constants.y0),
				(int) (globalCornerPos[3].y*Constants.PixelsPerIn + Constants.y0), (int) (globalCornerPos[2].y*Constants.PixelsPerIn + Constants.y0)};
		
		int[] xposTri = {0,0,0,0};
		int[] yposTri = {0,0,0,0};
		
		for(int i = 0; i < 4; i++){
			if(i != getSteppingLegNum()){
				xposTri[i] = (int) (globalFeetPos[i].x*Constants.PixelsPerIn + Constants.x0);
				yposTri[i] = (int) (globalFeetPos[i].y*Constants.PixelsPerIn + Constants.y0);
			}
			else if(getSteppingLegNum() != 0){
				xposTri[i] = (int) (globalFeetPos[i-1].x*Constants.PixelsPerIn + Constants.x0);
				yposTri[i] = (int) (globalFeetPos[i-1].y*Constants.PixelsPerIn + Constants.y0);
			}
			else{
				xposTri[i] = (int) (globalFeetPos[i+1].x*Constants.PixelsPerIn + Constants.x0);
				yposTri[i] = (int) (globalFeetPos[i+1].y*Constants.PixelsPerIn + Constants.y0);
			}
		}
		
		//Draw the rotated rectangle
		g.setColor(Color.gray);
		g.fillPolygon(xpos, ypos, 4);
		g.setColor(Color.BLACK);
		g.drawPolygon(xpos, ypos, 4);
		g.fillOval((int) (globalRobotPos.x*Constants.PixelsPerIn + Constants.x0), (int) (globalRobotPos.y*Constants.PixelsPerIn + Constants.y0), 10, 10);//draw global pos
		g.fillOval((int) (globalFeetPos[0].x*Constants.PixelsPerIn + Constants.x0), (int) (globalFeetPos[0].y*Constants.PixelsPerIn + Constants.y0), 10, 10);
		g.fillOval((int) (globalFeetPos[1].x*Constants.PixelsPerIn + Constants.x0), (int) (globalFeetPos[1].y*Constants.PixelsPerIn + Constants.y0), 10, 10);
		g.fillOval((int) (globalFeetPos[2].x*Constants.PixelsPerIn + Constants.x0), (int) (globalFeetPos[2].y*Constants.PixelsPerIn + Constants.y0), 10, 10);
		g.fillOval((int) (globalFeetPos[3].x*Constants.PixelsPerIn + Constants.x0), (int) (globalFeetPos[3].y*Constants.PixelsPerIn + Constants.y0), 10, 10);
		g.drawPolygon(xposTri, yposTri, 4);
		if(steppingLeg == leg.frontLeft && HandleCoM.CoMStable(globalFeetPos[1], globalFeetPos[2], globalFeetPos[3], Body.getGlobalCoMPos(localRobotPos, globalRobotPos))) g.setColor(Color.WHITE);
		else if(steppingLeg == leg.frontRight && HandleCoM.CoMStable(globalFeetPos[0], globalFeetPos[2], globalFeetPos[3], Body.getGlobalCoMPos(localRobotPos, globalRobotPos))) g.setColor(Color.WHITE);
		else if(steppingLeg == leg.rearLeft && HandleCoM.CoMStable(globalFeetPos[0], globalFeetPos[1], globalFeetPos[3], Body.getGlobalCoMPos(localRobotPos, globalRobotPos))) g.setColor(Color.WHITE);
		else if(steppingLeg == leg.rearRight && HandleCoM.CoMStable(globalFeetPos[0], globalFeetPos[1], globalFeetPos[2], Body.getGlobalCoMPos(localRobotPos, globalRobotPos))) g.setColor(Color.WHITE);
		g.fillOval((int) (Body.getGlobalCoMPos(localRobotPos, globalRobotPos).x*Constants.PixelsPerIn + Constants.x0),
				(int) (Body.getGlobalCoMPos(localRobotPos, globalRobotPos).y*Constants.PixelsPerIn + Constants.y0), 10, 10);
	}
	
	public int getSteppingLegNum(){
		if(steppingLeg == leg.frontLeft) return 0;
		else if(steppingLeg == leg.frontRight) return 1;
		else if(steppingLeg == leg.rearLeft) return 2;
		else return 3;
	}

}
