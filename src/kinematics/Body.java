package kinematics;

import util.Constants;
import util.Matrix;
import util.Position;

public class Body {
	
	private static Position[] localCornerPos = {new Position(0,0,0,0,0,0),new Position(0,0,0,0,0,0),new Position(0,0,0,0,0,0),new Position(0,0,0,0,0,0)};
	private static Position[] globalCornerPos = {new Position(0,0,0,0,0,0),new Position(0,0,0,0,0,0),new Position(0,0,0,0,0,0),new Position(0,0,0,0,0,0)};
	
	public static Position[] getLocalCornerPos(Position localBodyPos){
		double[][] unrotatedLocalCornerPos = {{Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0},
										{Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0},
										{-Constants.BODYLENGTH/2, Constants.BODYWIDTH/2, 0},
										{-Constants.BODYLENGTH/2, -Constants.BODYWIDTH/2, 0}};
		
		double[][] rollRotationMatrix = {{Math.cos(localBodyPos.roll), 0, -Math.sin(localBodyPos.roll)},
										 {0, 1, 0},
										 {Math.sin(localBodyPos.roll), 0, Math.cos(localBodyPos.roll)}};
		double[][] pitchRotationMatrix = {{1, 0, 0},
										  {0, Math.cos(localBodyPos.pitch), Math.sin(localBodyPos.pitch)},
										  {0, -Math.sin(localBodyPos.pitch), Math.cos(localBodyPos.pitch)}};
		double[][] yawRotationMatrix = {{Math.cos(localBodyPos.yaw), Math.sin(localBodyPos.yaw), 0},
									    {Math.sin(localBodyPos.yaw), Math.cos(localBodyPos.yaw), 0},
									    {0, 0, 1}};
		//double[][] RotationMatrix = Matrix.multiply(rollRotationMatrix, Matrix.Multiply)
		for(int i = 0; i < 4; i++){
			//unrotatedLocalCornerPos[i] = Matrix.multiply(rollRotationMatrix, unrotatedLocalCornerPos[i]);
			//unrotatedLocalCornerPos[i] = Matrix.multiply(pitchRotationMatrix, unrotatedLocalCornerPos[i]);
			double[] rotatedLocalCornerPos = unrotatedLocalCornerPos[i];//Matrix.multiply(yawRotationMatrix, unrotatedLocalCornerPos[i]);
			localCornerPos[i] = new Position(rotatedLocalCornerPos[0] + localBodyPos.x, rotatedLocalCornerPos[1] + localBodyPos.y,
					rotatedLocalCornerPos[2] + localBodyPos.z, localBodyPos.roll, localBodyPos.pitch, localBodyPos.yaw);
			//System.out.println(rotatedLocalCornerPos[0]);
		}
		return localCornerPos;
	}
	
	public static Position[] getGlobalCornerPos(Position localBodyPos, Position globalBodyPos){
		Position[] untranslatedCornerPos = getLocalCornerPos(localBodyPos);
		for(int i = 0; i < 4; i++){
			globalCornerPos[i] = new Position(untranslatedCornerPos[i].x + globalBodyPos.x, untranslatedCornerPos[i].y + globalBodyPos.y,
					untranslatedCornerPos[i].z + globalBodyPos.z, localBodyPos.roll, localBodyPos.pitch, localBodyPos.yaw);
		}
		return globalCornerPos;
	}
	
	public static Position getRelativeFootPos(Position globalCornerPos, Position globalFootPos){
		double x = globalFootPos.x - globalCornerPos.x;
		double y = globalFootPos.y - globalCornerPos.y;
		double z = globalFootPos.z - globalCornerPos.z;
		Position relativeFootPos = new Position(x,y,z,0,0,0);
		
		return relativeFootPos;
	}
	
	public static Position getGlobalFootPos(Position globalCornerPos, Position relativeFootPos){
		return relativeFootPos;
	}
	
}
