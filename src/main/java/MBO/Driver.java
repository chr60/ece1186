package MBO;

public class Driver{

  private final int employeeID;
  private int trainID;
  private long shiftStart;
  private final long START_TO_BREAK = 14400;
  private final long BREAK_LEN = 1800;
  private long shiftEnd;
  private long breakStart;
  private long breakEnd;
  private String status;

  /**
   * Bare-bones constructor of Driver object
   * @param  employeeID Driver's employee ID
   */
  public Driver(int employeeID) {

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
   * @param  employeeID Driver's employee ID
   * @param  trainID    Driver's current train's ID 
   * @param  shiftStart Time the driver's shift begins
   * @param  shiftEnd   Time the driver's shift ends
   * @param  breakStart Time the driver's break begins
   * @param  breakEnd   Time the driver's break ends
   * @param  status     String stating what the driver is currently doing
   */
  public Driver(int employeeID, int trainID, long shiftStart, long shiftEnd, 
                long breakStart, long breakEnd, String status) {

    this.employeeID = employeeID;
    this.trainID = trainID;
    this.shiftStart = shiftStart;
    this.shiftEnd = shiftEnd;
    this.breakStart = breakStart;
    this.breakEnd = breakEnd;
    this.status = status;
  }

  /**
   * Gets the employee's ID
   * @return int employeeID
   */
  public int getEmployeeID() {
    return employeeID;
  }

  /**
   * Sets the driver's current train ID
   * @param trainID ID of the train the driver is on
   */
  public void setTrainID(int trainID) {
    this.trainID = trainID;
  }

  /**
   * Gets the driver's current train ID
   * @return int trainID
   */
  public int getTrainID() {
    return trainID;
  }

  /**
   * Sets the time the driver starts their shift
   * @param shiftStart number of seconds from the beginning 
   *                   of the day to the shift start time
   */
  public void setShiftStart(long shiftStart) {
    this.shiftStart = shiftStart;
  }

  /**
   * Gets the time the driver starts their shift
   * @return long shiftStart
   */
  public long getShiftStart() {
    return shiftStart;
  }

  /**
   * Sets the time the driver ends their shift
   * @param shiftEnd number of seconds from the beginning 
   *                 of the day to the shift end time
   */
  public void setShiftEnd(long shiftEnd) {
    this.shiftEnd = shiftEnd;
  }

  public void setShiftEnd() {
    if (breakEnd >= 0) {
      shiftEnd = breakEnd + START_TO_BREAK;
    } else if (breakStart >= 0) {
      shiftEnd = breakStart + BREAK_LEN + START_TO_BREAK;
    } else {
      shiftEnd = shiftStart + 2 * START_TO_BREAK + BREAK_LEN;
    }
  }

  /**
   * Gets the time the driver ends their shift
   * @return long shiftEnd
   */
  public long getShiftEnd() {
    return shiftEnd;
  }

  /**
   * Sets the time the driver starts their break
   * @param breakStart number of seconds from the beginning 
   *                 of the day to the break start time
   */
  public void setBreakStart(long breakStart) {
    this.breakStart = breakStart;
  }

  public void setBreakStart() {
    breakStart = shiftStart + START_TO_BREAK;
  }

  /**
   * Gets the time the driver starts their break
   * @return long breakStart
   */
  public long getBreakStart() {
    return breakStart;
  }

  /**
   * Sets the time the driver ends their break
   * @param breakEnd number of seconds from the beginning 
   *                 of the day to the break end time
   */
  public void setBreakEnd(long breakEnd) {
    this.breakEnd = breakEnd;
  }

  public void setBreakEnd() {
    if (breakStart >= 0) {
      breakEnd = breakStart + BREAK_LEN;
    } else {
      breakEnd = shiftStart + START_TO_BREAK + BREAK_LEN;
    }
  }

  /**
   * Gets the time the driver ends their break
   * @return long breakEnd
   */
  public long getBreakEnd() {
    return breakEnd;
  }

  /**
   * Sets the status of the driver. On break, etc
   * @param status Status of the driver
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Gets the status of the driver
   * @return String status
   */
  public String getStatus() {
    return status;
  }

}