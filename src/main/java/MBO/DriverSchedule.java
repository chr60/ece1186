package MBO;

import java.util.ArrayList;

public class DriverSchedule{

  private ArrayList<Driver> driverSchedule;
  private final long START_TO_BREAK = 14400;
  private final long BREAK_LEN = 1800;
  private long freeAt = 0;
  private long runTime;
  private int numTrains;

  /**
   * Constructor to create driver schedule object
   */
  public DriverSchedule(long runTime, int numTrains) {
    this.runTime = runTime;
    this.numTrains = numTrains;
    driverSchedule = new ArrayList<Driver>();
  }

  /**
   * Creates a list of drivers by parsing a csv file
   * @param   fileName                  CSV file location
   * @return  ArrayList<Driver>  driveSchedule
   */
  public ArrayList<Driver> updateDrivers(String fileName) {
    return null;
  }

  /**
   * Creates a new driver and adds them to the schedule
   * @param  employeeID Driver's employee ID
   * @param  trainID    Driver's current train's ID 
   * @param  shiftStart Time the driver's shift begins
   * @param  shiftEnd   Time the driver's shift ends
   * @param  breakStart Time the driver's break begins
   * @param  breakEnd   Time the driver's break ends
   * @param  status     String stating what the driver is currently doing
   */
  public void addDriver(int employeeID, int trainID, long shiftStart, long shiftEnd, 
              long breakStart, long breakEnd, String status) {

    if (employeeID == -1) { 
      employeeID = driverSchedule.size();
    }
    
    driverSchedule.add(new Driver(employeeID, trainID, shiftStart, 
                    shiftEnd, breakStart, breakEnd, status));
  }

  public void addDriver(int employeeID, Driver driver) {
    
    if (-1 == employeeID) {
      employeeID = driverSchedule.size();
    } else {
      driver.setEmployeeID(employeeID);
    }

    driverSchedule.add(driver);
  }

  public void addDriver(int employeeID, int trainID, long shiftStart, String status) {

    if (employeeID == -1) { 
      employeeID = driverSchedule.size();
    }

    long breakStart, breakEnd, shiftEnd;

    breakStart = shiftStart + START_TO_BREAK;
    breakEnd = breakStart + BREAK_LEN;
    shiftEnd = breakEnd + START_TO_BREAK;

    if (breakStart > shiftStart + runTime) {
      breakStart = shiftStart + runTime;
      breakEnd = breakStart;
      shiftEnd = breakEnd;
    } else if (breakEnd > shiftStart + runTime) {
      breakEnd = shiftStart + runTime;
      shiftEnd = breakEnd;
    } else if (shiftEnd > shiftStart + runTime) {
      shiftEnd = shiftStart + runTime;
    }

    driverSchedule.add(new Driver(employeeID, trainID, shiftStart, 
                    shiftEnd, breakStart, breakEnd, status));
  }

  /**
   * Gets a driver from their employee ID
   * @param  employeeID ID of the driver
   * @return Driver driver
   */
  public Driver getDriver(int employeeID) {
    return driverSchedule.get(employeeID);
  }

  public Driver get(int i) {
    return driverSchedule.get(i);
  }

  public long getFree() {
    return freeAt;
  }

  public void setFree(long freeAt) {
    this.freeAt = freeAt;
  }

  /**
   * Gets the size of the schedule
   * @return int size
   */
  public int size() {
    return driverSchedule.size();
  }

  /**
   * Gets the schedule
   * @return ArrayList<Driver> driverSchedule
   */
  public ArrayList<Driver> getSchedule() {
    return driverSchedule;
  }

}