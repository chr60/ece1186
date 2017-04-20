package TrackModel;

/**
* Station class.Is a station that holds the blocks that host (have platforms to) the station,a name
* a temperature, heaters, and passengers
*/
import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public class Station implements Comparable<Station>, Serializable {
  public String stationName;
  public Integer stationTemp;
  public Boolean trackHeatersOn;
  public Integer passengersWaiting;
  public ArrayList<Block> hostBlocks; 
  public TrackModel superTrackModel;
  private Random rand;
  private Integer numLoaded;
  private Lights forwardStationLights;
  private Lights backWardStationLights;
  private String nextArrivalTime;

  public Station(TrackModel superTrackModel, String stationName, ArrayList<Block> hostBlocks) {
    this.superTrackModel = superTrackModel;
    this.hostBlocks = hostBlocks;
    this.stationName = stationName;
    this.stationTemp = new Integer(20);
    this.trackHeatersOn = false;
    this.passengersWaiting = new Integer(100);
    this.forwardStationLights = new Lights(this.superTrackModel);
    this.backWardStationLights = new Lights(this.superTrackModel);
    this.nextArrivalTime = "0:00";
	rand = new Random();
  }

  /**
  * Loads passengers from the waiting area to a train.
  * @param maxPassengers maximum passengers to be added to a train
  * @return return value for a number of passengers
  */
  public Integer loadPassengers(Integer maxPassengers) {
    if (maxPassengers > this.passengersWaiting) {
      numLoaded = this.rand.nextInt(this.passengersWaiting);
      }
    else {
      numLoaded = this.rand.nextInt(maxPassengers);
      }
    this.passengersWaiting = this.passengersWaiting - numLoaded;
    return numLoaded;
  }

  /**
  * Adds departing passengers to the waiting area.
  * @param numPassengers number of passengers to add to the waiting area
  */
  public void addDepartingPassengers(Integer numPassengers) {
    this.passengersWaiting = this.passengersWaiting + numPassengers;
  }

  /**
  * Updates the status of track heaters at a station.
  */
  public void updateHeaters() {
    this.updateTemp();
    if (this.stationTemp < 40) {
      this.trackHeatersOn = true;
    } else {
      this.trackHeatersOn = false;
    }
  }

  public void updateTemp() {
    Random tempRandom = new Random();
    this.stationTemp = this.stationTemp + tempRandom.nextInt(5);
  }
  /**
  * Returns the lights visible when travelling from the "forward" direction.
  */
  public Lights getForwardLights() {
    return this.forwardStationLights;
  }

  /**
  * Returns the lights visible when travelling from the "backward" direction.
  */
  public Lights getBackwardLights() {
    return this.backWardStationLights;
  }

  /**
  * Allows for setting the time of arrival of the next train by the MBO.
  */
  public void setArrivalTime(String arrivalTime) {
    this.nextArrivalTime = arrivalTime;
  }
  
  /** 
  * Allows for viewing of the next arrival time.
  */
  public String nextArrivalTime() {
    return this.nextArrivalTime;
  }

  /**
  * Allows for viewing the associated blocks of a station.
  */
  public ArrayList<Block> getHostBlocks() {
    return this.hostBlocks;
  }

  /**
  * Adds passengers to a station.
  */
  public void addWaiting(Integer newWaiting) {
    this.passengersWaiting = this.passengersWaiting + newWaiting;
  }

  /**
  * Implements comparable interface for a station based upon the name of a station.
  * @param thatStation station to be compared to
  */
  @Override
  public int compareTo(Station thatStation) {
    if (thatStation.stationName.equals(stationName)) {
      return 1;
      } else {
      return 0;
    }
  }
}
