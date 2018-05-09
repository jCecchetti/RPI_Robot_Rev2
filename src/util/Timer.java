package util;

public class Timer {
	
	private long zeroTime = 0;
	private long currentTime = 0;

	public Timer(){
		reset();
	}
	
	public void start(){
		
	}
	
	public void reset(){
		zeroTime = System.nanoTime();
	}
	
	public double get(){
		return (double) (System.nanoTime() - zeroTime)/1000000000.0;
	}
}
