package MBO;
import java.util.*;
import TrackModel.*;

public class Driver{

	private final String firstName;
	private final String lastName;
	private final int employeeID;
	private int trainID;
	private long shiftStart;
	private long shiftEnd;
	private long breakStart;
	private long breakEnd;
	private String status;

	/**
	 * Bare-bones constructor of Driver object
	 * @param  firstName  Driver's first name
	 * @param  lastName   Driver's last name
	 * @param  employeeID Driver's employee ID
	 */
	public Driver(String firstName, String lastName, int employeeID){
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;

		trainID = -1;
		shiftStart = -1;
		shiftEnd = -1;
		breakStart = -1;
		breakEnd = -1;
		status = "";
	}

	/**
	 * Full fledged constructor for Driver object
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
	public Driver(String firstName, String lastName, int employeeID, int trainID, 
		long shiftStart, long shiftEnd, long breakStart, long breakEnd, String status){
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
		this.trainID = trainID;
		this.shiftStart = shiftStart;
		this.shiftEnd = shiftEnd;
		this.breakStart = breakStart;
		this.breakEnd = breakEnd;
		this.status = status;
	}

	/**
	 * Gets the driver's first name
	 * @return String firstName
	 */
	public String getFirstName(){
		return firstName;
	}

	/**
	 * Gets the driver's last name
	 * @return String lastName
	 */
	public String getLastName(){
		return lastName;
	}

	/**
	 * Gets the driver's full name
	 * @return String fullName
	 */
	public String getName(){
		return firstName + " " + lastName;
	}

	/**
	 * Gets the driver's name in last, first format
	 * @return String fullName
	 */
	public String getLastFirst(){
		return lastName + ", " + firstName;
	}

	/**
	 * Gets the employee's ID
	 * @return int employeeID
	 */
	public int getEmployeeID(){
		return employeeID;
	}

	/**
	 * Sets the driver's current train ID
	 * @param trainID ID of the train the driver is on
	 */
	public void setTrainID(int trainID){
		this.trainID = trainID;
	}

	/**
	 * Gets the driver's current train ID
	 * @return int trainID
	 */
	public int getTrainID(){
		return trainID;
	}

	/**
	 * Sets the time the driver starts their shift
	 * @param shiftStart number of seconds from the beginning 
	 *                   of the day to the shift start time
	 */
	public void setShiftStart(long shiftStart){
		this.shiftStart = shiftStart;
	}

	/**
	 * Gets the time the driver starts their shift
	 * @return long shiftStart
	 */
	public long getShiftStart(){
		return shiftStart;
	}

	/**
	 * Sets the time the driver ends their shift
	 * @param shiftEnd number of seconds from the beginning 
	 *                 of the day to the shift end time
	 */
	public void setShiftEnd(long shiftEnd){
		this.shiftEnd = shiftEnd;
	}

	/**
	 * Gets the time the driver ends their shift
	 * @return long shiftEnd
	 */
	public long getShiftEnd(){
		return shiftEnd;
	}

	/**
	 * Sets the time the driver starts their break
	 * @param breakStart number of seconds from the beginning 
	 *                 of the day to the break start time
	 */
	public void setBreakStart(long breakStart){
		this.breakStart = breakStart;
	}

	/**
	 * Gets the time the driver starts their break
	 * @return long breakStart
	 */
	public long getBreakStart(){
		return breakStart;
	}

	/**
	 * Sets the time the driver ends their break
	 * @param breakEnd number of seconds from the beginning 
	 *                 of the day to the break end time
	 */
	public void setBreakEnd(long breakEnd){
		this.breakEnd = breakEnd;
	}

	/**
	 * Gets the time the driver ends their break
	 * @return long breakEnd
	 */
	public long getBreakEnd(){
		return breakEnd;
	}

	/**
	 * Sets the status of the driver. On break, etc
	 * @param status Status of the driver
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 * Gets the status of the driver
	 * @return String status
	 */
	public String getStatus(){
		return status;
	}

}