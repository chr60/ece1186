package MBO;
import java.util.*;
import TrackModel.*;
import java.util.HashMap;

public class DriverSchedule{

	private HashMap<Integer, Driver> driverSchedule;
	private final long START_TO_BREAK = 14400;
	private final long BREAK_LEN = 1800;
	private long runTime;
	private int numTrains;

	/**
	 * Constructor to create driver schedule object
	 */
	public DriverSchedule(long runTime, int numTrains){
		this.runTime = runTime;
		this.numTrains = numTrains;
		driverSchedule = new HashMap<Integer, Driver>();
	}

	/**
	 * Creates a list of drivers by parsing a csv file
	 * @param   fileName                  CSV file location
	 * @return  HashMap<Integer, Driver>  driveSchedule
	 */
	public HashMap<Integer, Driver> updateDrivers(String fileName){
		return null;
	}

	/**
	 * Creates a new driver and adds them to the schedule
	 * @param  firstName  Driver's first name
	 * @param  lastName   Driver's last name
	 * @param  employeeID Driver's employee ID
	 * @param  trainID    Driver's current train's ID	
	 * @param  shiftStart Time the driver's shift begins
	 * @param  shiftEnd   Time the driver's shift ends
	 * @param  breakStart Time the driver's break begins
	 * @param  breakEnd   Time the driver's break ends
	 * @param  status     String stating what the driver is currently doing
	 */
	public void addDriver(int employeeID, int trainID, long shiftStart, long shiftEnd, 
							long breakStart, long breakEnd, String status){

		if(employeeID == -1) employeeID = driverSchedule.size();
		
		driverSchedule.put(employeeID, new Driver(employeeID, trainID, shiftStart, 
										shiftEnd, breakStart, breakEnd, status));
	}

	/**
	 * Gets a driver from their employee ID
	 * @param  employeeID ID of the driver
	 * @return Driver driver
	 */
	public Driver getDriver(int employeeID){
		return driverSchedule.get(employeeID);
	}

	/**
	 * Gets the size of the schedule
	 * @return int size
	 */
	public int size(){
		return driverSchedule.size();
	}

	/**
	 * Gets the schedule
	 * @return HashMap<Integer, Driver> driverSchedule
	 */
	public HashMap<Integer, Driver> getSchedule(){
		return driverSchedule;
	}

}