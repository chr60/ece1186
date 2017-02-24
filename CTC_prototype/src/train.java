import java.util.*;

public class train {
	private int trainID;
	private double speed;
	private double authority;
	private String line;
	
	
	public train(){
		this.setSpeed(speed);
		this.setAuthority(authority);
		this.trainID = trainID;
		
	}


	public String getTrainID() {
		String trainStringID = Integer.toString(trainID);
		return trainStringID;
	}


	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public double getAuthority() {
		return authority;
	}
	
	public String getAuthorityS(){
		String a = Double.toString(authority);
		return a;
	}

	public String getSpeedS(){
		String s = Double.toString(speed);
		return s;
	}

	public void setAuthority(double authority) {
		this.authority = authority;
	}


	public String getLine() {
		return line;
	}


	public void setLine(String line) {
		this.line = line;
	}
}
