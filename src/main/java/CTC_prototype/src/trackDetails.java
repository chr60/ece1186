import java.util.*;

public class trackDetails {
	
	private String line;
	private String section;
	private int block;
	private double speedLimit;
	private String station;
	private String switchNum;
	
	public trackDetails(String line, String section, int block, double speedLimit, String station, String switchNum){
		this.line = line;
		this.section = section;
		this.block = block;
		this.speedLimit = speedLimit;
		this.station = station;
		this.switchNum = switchNum;
	
		
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public double getSpeedLimit() {
		return speedLimit;
	}

	public String getSpeedLimitS(double speedLimit) {
		String limitS = Double.toString(speedLimit);
		
		return limitS;
	}
	
	public void setSpeedLimit(double speedLimit) {
		this.speedLimit = speedLimit;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSwitchNum() {
		return switchNum;
	}

	public void setSwitchNum(String switchNum) {
		this.switchNum = switchNum;
	}
	
	
	
}
