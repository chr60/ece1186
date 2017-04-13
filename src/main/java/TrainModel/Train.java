package TrainModel;
import TrackModel.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

//doxygen comment format below!
/**
 * a normal member taking two arguments and returning an integer value.
 * @param a an integer argument.
 * @param s a constant character pointer.
 * @see Javadoc_Test()
 * @see ~Javadoc_Test()
 * @see testMeToo()
 * @see publicVar()
 * @return The test results
 */
public class Train implements Serializable {

	//variables for train values.
	Block currAuthority;
	Block currBlock;
	Double mass, length, velocity, oldVelocity, power, currGrade;
	Double netForce;
	Double currTemp, currThermostat, distance, setPointSpeed;
	Double acceleration;
	Double maxPower = 120000.00; 		//maximum power is 120 kW
	Double Height, Width;
	Double Kp, Ki;
	Double weightPass, weightCar;
	Double lengthCar = 8.69;			//length of one car in feet
	Double safeDistSB, safeDistEB;
	Double startTime;
	Double frictionC = 0.16; 			//coefficient of friction of steel wheels on lubricated steel rails
	Double SBrate = -1.2, EBrate = -2.73;
	Double g = 9.8;
	//Double Fx, Fy, Ax, Ay, Vx, Vy, oldVx, oldVy;				//gravity constant in m/s^2
	boolean engineFailure, signalFailure, brakeFailure;
	Integer trainID;
	int numPassengers, numCars, numCrew;
	int statusAC, statusHeater, statusLeftDoor, statusRightDoor, statusEB, statusSB, statusLights;
	String messageBoard;
	GPS trainLocation;
	TrackModel globalTrack;
        Block prevBlock;


	/**
     * Constructor to create a new Train object based on Assigned ID
     * @param a an integer argument to assign to Trains new ID.
     */
	public Train(Integer ID, TrackModel gTrack){
		mass = 40.9 * 907.185;  		//mass of empty car in kg
		velocity = 0.01;
		velocity =0.0;
		//Vx = 0.0;
		//Vy = 0.0;
		trainID = ID;
		power = 0.0;
		currGrade = 0.0;
		globalTrack = gTrack;
		trainLocation = new GPS();
	}





	/**
     * Modifier to apply a new power command to the current train and adjust velocity accordingly
     * @param a Double argument to assign as the new applied power command.
     * @see changeSpeed()
     */
	public void powerCommand(Double newPower){

		Double forceApp;
		if (velocity == 0)
		{
			//to avoid division by zero
			forceApp = newPower;
		}else if (statusEB == 1 || statusSB == 1){
			//if either brake is engaged
			Double brakeDeceleration = 0.0;
			if (statusSB == 1)
			{
				brakeDeceleration = SBrate;
			}else if(statusEB == 1)
			{
				brakeDeceleration = EBrate;
			}

			forceApp = mass * brakeDeceleration;
		}else{
			forceApp = newPower / velocity;
		}

		power = newPower;
		if(newPower >= maxPower){
			//if power command is greater than or equal to max power do nothing
                        power = maxPower;
                        // System.out.println("Hey you!");
                        changeSpeed(forceApp);
		}else {
			//if power command calls for increase of speed
                        // System.out.println("Hey you!");
			changeSpeed(forceApp);
		}

	}




	/**
     * Modifier to change current speed based on force applied to system
     * @param a Double argument to assign as the new applied force.
     * @see myCos()
     * @see mySin()
     * @see magnitude()
     */
	private void changeSpeed(Double Fapp) {
		oldVelocity = velocity;
		//compute force lost due to friction
		Double Fs = mass * g * mySin(currGrade);
		//compute net force based on applied force and friction
		netForce = Fapp - Fs;
		//compute acceleration based on net force and current mass
		acceleration = netForce / mass;
		//max acceleration is 0.5
		if (acceleration > 0.5){
			acceleration = 0.5;
		}

		//compute new velocity based on old velocity and acceleration
		velocity = velocity + acceleration;
		if (velocity > 19.4444)              //70 kph in m/s (max velocity)
		{
			velocity = 19.4444;
		}
		if (velocity < 0)
		{
			velocity = 0.0;
		}

		//using S = Vi(t) + (1/2)(a)(t^2)  to compute distance
		distance = (oldVelocity) + (0.5)*acceleration;

		updateCurrBlock(distance);
		updateSpeedAndAuthority();
	}


	/**
     * Method to determine what block the train is in now
     * @param a Double which corresponds to the distance travelled by the train
     */
	private void updateCurrBlock(Double distTravelled){
		Double distBlock = trainLocation.getDistIntoBlock();
		Double dist = distBlock + distTravelled;

		//check if distance exceeds length of block (if so enter new block) if not update location
		while (dist > trainLocation.getCurrBlock().getLen())
		{
                        //System.out.println("Next block forward" + currBlock.nextBlockForward().blockNum());
			dist = getCurrBlock().getLen() - dist;
			currBlock.setOccupied(false);

                        Block blockForward = currBlock.nextBlockForward();
                        Block blockBackward = currBlock.nextBlockBackward();

                        //System.out.println("forward block: " + blockForward.blockNum());
                        //System.out.println("backward block: " + blockBackward.blockNum());
                        if(blockForward != null && blockBackward != null){
                            //theres both a forward and backward. go to the one that wasnt last visited
                            if (blockBackward.compareTo(prevBlock) == 0)
                            {
                                //we were just in blockBackward so go forward
                                prevBlock = currBlock;
                                currBlock = currBlock.nextBlockForward();
                            }else{
                                //go backwards
                                prevBlock = currBlock;
                                currBlock = currBlock.nextBlockBackward();
                            }
                        }else if (blockForward == null)
                        {
                            //go backwards
                            if (blockBackward != null )
                            {
                                prevBlock = currBlock;
                                currBlock = currBlock.nextBlockBackward();
                            }
                        }else if(blockBackward == null)
                        {
                            //go forward
                            if (blockForward != null )
                            {
                                prevBlock = currBlock;
                                currBlock = currBlock.nextBlockForward();
                            }

                        }

                        //System.out.println("Going to next block: " + currBlock.blockNum());
                        //System.out.println("Block length: " + currBlock.getLen());
			currBlock.setOccupied(true);
		}
		trainLocation.setCurrBlock(currBlock);
                //System.out.println("Dist: " + dist);
		trainLocation.setDistIntoBlock(dist);

	}

	/**
     * Method to update speed and authority (and other block properties)
     */
	private void updateSpeedAndAuthority(){

            if (this.getCurrBlock() != null){
                if (this.getCurrBlock().getSuggestedSpeed() != null){

                    setPointSpeed = getCurrBlock().getSuggestedSpeed();
                }

                if (this.getCurrBlock().getAuthority() != null){

                    currAuthority = getCurrBlock().getAuthority();
                }

                if (this.getCurrBlock().getGrade() != null){
                    currGrade = getCurrBlock().getGrade();
                }
            }
        }



	/**
     * Method to calculate safe Braking Distance of train based on its current velocity and mass
     * @return a Double which corresponds to the amount of distance required to stop the train using the service brake
     */
	public Double getSafeBrakingDistSB(){
		Double decRate = deccelRate(SBrate);
		Double timeSB = timeToStop(decRate);
		Double SBD = distanceToStop(decRate,timeSB);
		return SBD;
	}

	/**
     * Method to calculate safe emergency Braking Distance of train based on its current velocity and mass
     * @return a Double which corresponds to the amount of distance required to stop the train using the emergency brake
     */
	public Double getSafeBrakingDistEB(){
		Double decRate = deccelRate(EBrate);
		Double timeEB = timeToStop(decRate);
		Double SEBD = distanceToStop(decRate,timeEB);
		return SEBD;
	}

/**
     * Method to calculate decceration rate based on brake rates and mass of train
     * @param a Double which corresponds to the deceleration rate of the brakes
     * @return a Double which corresponds to the deceleration rate based on brakes and mass
     */
	private Double deccelRate(Double Drate){

		Double forceApplied = mass * Drate;
		Double Fs = mass * g * mySin(currGrade);
		Double netF = forceApplied - Fs;
		//compute acceleration based on net force and current mass
		Double decceleration = netF / mass;
		return decceleration; 				//deceleration rate based on brakes and mass
	}


	/**
     * Method to calculate time to stop based on brake rate, mass and velocity
     * @param a Double which corresponds to the deceleration rate of the brakes
     * @return a Double which corresponds to the amount of time required to stop the train using the brakes
     */
	private Double timeToStop(Double Drate){
		Double time = 0.0;
		Double tempVelocity = velocity;
                
                //System.out.println(tempVelocity); 
		while (tempVelocity > 0.0)
		{    
			tempVelocity = tempVelocity + Drate;
			time++;
		}
                
		return time; 				//time required to stop the train in seconds
	}

	/**
     * Method to calculate distance to stop based on brake rate, mass and velocity
     * @param a Double which corresponds to the deceleration rate of the brakes
     * @param a Double which corresponds to the time that it will take the train to stop
     * @return a Double which corresponds to the amount of distance required to stop the train using the brakes
     */
	private Double distanceToStop(Double Drate, Double stopTime){
		//using S = Vi(t) + (1/2)(a)(t^2)  to compute distance
		Double stopDist = (velocity)*(stopTime) + (1/2)*(Drate)*(Math.pow(stopTime, 2));
		return stopDist; 				//time required to stop the train in seconds
	}


	/**
     * Mutator to set the current Block that the train is on
     * @param Block object to set curr block to
     */
	public void setCurrBlock(Block newBlock){

            this.trainLocation.setCurrBlock(newBlock);
            this.trainLocation.setDistIntoBlock(0.0);
            this.currBlock = newBlock;
            this.prevBlock = currBlock;
	}

	/**
     * Accessor to get the current Block that the train is on
     * @return Block object to return curr block
     */
	public Block getCurrBlock(){
		return this.currBlock;
	}

	/**
     * Mutator to compute how far into the block a train has traveled to more accurately provide location to the MBO
     * @return a Double which denotes how far into the block the train currently is
     */
	public Double distIntoBlock(Double dist){
		Double location=0.0;
		return location;
	}



	/**
     * Method to update temperature based on current temp and thermostat setting. This method will be called periodically at each cycle of the system
     */
	public void updateTemp(){

	}





	/* FUNCTIOSN TO INTEGRATE WITH TRACK MODEL GETTING PEOPLE ON AND OFF
	 * Public Integer loadPassengers (Integer maxPassengers)
		return random numeber

		public void addDepartingPassengers(Integer numPassengers)
	 */


	//CODE BELOW THIS LINE IS DONE. DO NOT TOUCH.
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************


    /**
     * Accessor to return current train's ID

     * @return an Integer which corresponds to the current train's ID.
     */
	public Integer getID(){

		return trainID;
	}

	/**
     * Accessor to return current train's setpoint speed
     * @return an Double object which corresponds to train's current suggested speed.
     */
	public Double getSuggestedSpeed(){
		return setPointSpeed;
	}

	/**
     * Accessor to return current train's Authority
     * @return an Block object which corresponds to train's current authority.
     */
	public Block getAuthority(){
		return currAuthority;
	}

	/**
     * Accessor to return current train's Velocity
     * @return an Double object which corresponds to train's current velocity. This value will be converted from m/s to MPH prior to returning.
     */
	public Double getVelocity(){
		return (velocity * 2.23694);			//convert velocity to MPH from m/s
	}

	/**
     * Accessor to return current train's mass
     * @return an Double object which corresponds to train's current mass. This value will be converted from kg to lbs prior to returning.
     */
	public Double getMass(){
		return ((mass) * 2.20462);				//convert mass from Kg to lbs before display
	}

	/**
     * Accessor to return current train's location
     * @return an GPS object which corresponds to train's current location
     */
	public GPS getGPS(){
		return trainLocation;
	}

    /**
     * Accessor to get the current thermostat setting onboard the train
     * @return a Double which denotes the thermostat setting on board the train in Fahrenheit
     */
	public Double getThermostat(){
		return currThermostat;
	}

	/**
     * Accessor to get engine failure status
     * @return a boolean which denotes whether or not there is a failure in the engines. False means no failure and true means failure.
     */
	public boolean isEngineFailure(){
		return engineFailure;
	}

	/**
     * Accessor to get signal failure status
     * @return a boolean which denotes whether or not there is a failure in the signaling system. False means no failure and true means failure.
     */
	public boolean isSignalFailure(){
		return signalFailure;
	}

	/**
     * Accessor to get brake failure status
     * @return a boolean which denotes whether or not there is a failure in the service brake. False means no failure and true means failure.
     */
	public boolean isBrakeFailure(){
		return brakeFailure;
	}

	/**
     * Accessor to get the current temperature onboard the train
     * @return a Double which denotes the temperature on board the train in Fahrenheit
     */
	public Double getTemp(){
		return currTemp;
	}

	/**
     *Accessor to get the status of the service brake of the train
     * @return an int which corresponds to the service brake's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
	public int getServiceBrake(){
		return statusSB; 		//1 = on, 0 = off, -1 = failure
	}

	/**
     * Accessor to get the status of the Emergency brake of the train
     * @return an int which corresponds to the Emergency brake's status. 1 means on,and 0 means off
     */
	public int getEmergencyBrake(){
		return statusEB; 		//1 = on, 0 = off
	}

	/**
     * Accessor to get the max power the train can go.
     * @return a Double corresponding to the max power the train can go.
     */
    public Double getMaxPower(){
        return this.maxPower;
    }

    /**
     * Accessor to return current train's applied power
     * @return an Double object which corresponds to train's current power command.
     */
	public Double getPower() {
		return power;
	}

	/**
     * Accessor to get current train's Kp
     * @return an Double object which corresponds to the new Kp.
     */
	public Double getKp(){
		return Kp;
	}

	/**
     * Accessor to get current train's Ki
     * @return an Double object which corresponds to the new Ki.
     */
	public Double getKi(){
		return Ki;
	}

	/**
     * accessor to get the status of the right doors.
     * @return int which corresponds to the right door's status. 1 means open, 0 means closed, and -1 denotes a failure.
     */
    public int getRightDoor(){
        return this.statusRightDoor;
    }

    /**
     * Accessor to get the status of the left door.
     * @return int which corresponds to the left door's status. 1 means open, 0 means closed, and -1 denotes a failure.
     */
    public int getLeftDoor(){
        return this.statusLeftDoor;
    }

	/**
     * Accessor to get the status of the interior lights onboard the train
     * @return an int which corresponds to the light's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
	public int getLights(){
		return statusLights; 		//1 = on, 0 = off, -1 = failure
	}

	/**
     * Accessor to get the status of the AC onboard the train
     * @return an int which corresponds to the AC's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
    public int getAC(){
        return this.statusAC;
    }


    /**
     * Accessor to get the status of the Heat onboard the train
     * @return an int which corresponds to the Heat's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
    public int getHeat(){
        return this.statusHeater;
    }






	/**
     * Mutator to set current train's Authority
     * @param an Double object which corresponds to the new authority.
     */
	public void setAuthority(Block goToBlock){
		currAuthority = goToBlock;
	}

	/**
     * Mutator to set current train's Kp and Ki
     * @param an Double object which corresponds to the new Kp.
     * @param an Double object which corresponds to the new Ki.
     */
	public void setKpAndKi(Double newKp, Double newKi){
		Kp = newKp;
		Ki = newKi;
	}

	/**
     * Modifier to change the current grade the train is residing at
     * @param an Double object which corresponds to train's current grade
     */
	public void setGrade(Double grade) {
		currGrade = Math.toDegrees(Math.atan2(grade, 100));
	}

	/**
     * Modifier to change the current mass of the train
     * @param an Double object which corresponds to the change in mass to apply. To decrease mass a negative number should be passed to this method.
     */
	public void changeMass(Double mass2) {
		mass = mass + mass2;
	}

	/**
     * Modifier to change the status of the right doors
     * @param an int which corresponds to the right door's status. 1 means open, 0 means closed, and -1 denotes a failure.
     */
	public void setRightDoor(int status){
		statusRightDoor = status; 		//1 = open, 0 = closed, -1 = failure
	}

	/**
     * Modifier to change the status of the left doors
     * @param an int which corresponds to the left door's status. 1 means open, 0 means closed, and -1 denotes a failure.
     */
	public void setLeftDoor(int status){
		statusLeftDoor = status; 		//1 = open, 0 = closed, -1 = failure
	}

	/**
     * Modifier to change the status of the interior lights onboard the train
     * @param an int which corresponds to the light's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
	public void setLights(int status){
		statusLights = status; 		//1 = on, 0 = off, -1 = failure
	}

	/**
     * Modifier to change the status of the AC onboard the train
     * @param an int which corresponds to the AC's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
	public void setAC(int status){
		statusAC = status; 		//1 = on, 0 = off, -1 = failure
	}

	/**
     * Modifier to change the status of the heater onboard the train
     * @param an int which corresponds to the heater's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
	public void setHeat(int status){
		statusHeater = status; 		//1 = on, 0 = off, -1 = failure
	}

	/**
     * Mutator to set the current thermostat setting onboard the train
     * @param a Double argument which denotes the thermostat setting on board the train in Fahrenheit
     */
	public void setThermostat(Double newThermostat){


            System.out.println("Set Thermostat Called.");
		currThermostat = newThermostat;

                this.updateTemp();

	}

	/**
     * Mutator to set engine failure status
     * @param a boolean argument is passed to denote whether or not there is a failure in the engines. False means no failure and true means failure.
     */
	public void setEngineFailure(boolean engineFail){
		engineFailure = engineFail;
	}

	/**
     * Mutator to set Signal failure status
     * @param a boolean argument is passed to denote whether or not there is a failure in the signaling system. False means no failure and true means failure.
     */
	public void setSignalFailure(boolean signalFail){
		signalFailure = signalFail;
	}

	/**
     * Mutator to set brake failure status
     * @param a boolean argument is passed to denote whether or not there is a failure in the service brake. False means no failure and true means failure.
     */
	public void setBrakeFailure(boolean brakeFail){
		brakeFailure = brakeFail;
	}

	/**
     * Modifier to change the status of the service brake of the train
     * @param an int which corresponds to the service brake's status. 1 means on, 0 means off, and -1 denotes a failure.
     */
	public void setServiceBrake(int status){
		statusSB = status; 		//1 = on, 0 = off, -1 = failure
		if (status == 1)
		{
			this.powerCommand(0.0);
		}

	}
         
        public HashMap<Block, Beacon> getBeacons(){
        
            return this.globalTrack.viewBeaconMap();
        }

	/**
     * Modifier to change the status of the Emergency brake of the train
     * @param an int which corresponds to the Emergency brake's status. 1 means on,and 0 means off
     */
	public void setEmergencyBrake(int status){
		statusEB = status; 		//1 = on, 0 = off
		if (status == 1)
		{
			this.powerCommand(0.0);
		}
	}

	/**
     * Modifier to change the amount of passengers onboard the train
     * @param an int which corresponds to the number of passengers to add or remove. To remove a negative number should be sent to the method
     * @see changeMass()
     */
	public void changePassengers(int pass)
	{
		numPassengers = numPassengers + pass;
		Double massPass = pass * weightPass;
		changeMass(massPass);
	}

	/**
     * Modifier to change the amount of cars connected to the train
     * @param an int which corresponds to the number of cars to add or remove. To remove a negative number should be sent to the method
     * @see changeMass()
     * @see changeLength()
     */
	public void changeCar(int car)
	{
		numCars = numCars + car;
		Double massCar = car * weightCar;
		changeMass(massCar);
		Double carLength = car * lengthCar;
		changeLength(carLength);
	}

	/**
     * Modifier to change the current length of the train
     * @param an Double object which corresponds to the change in length to apply. To decrease length a negative number should be passed to this method.
     */
	public void changeLength(Double length2) {
		length = length + length2;
	}

	/**
     * Mutator to set current train's setpoint speed
     * @param an Double object which corresponds to train's current suggested speed.
     */
	public void setSpeed(Double speed) {
		setPointSpeed = speed;
	}


        /**
         * Refreshes the temperature on the train.
         *
         */
        public void refreshTemp(){

            this.updateTemp();
        }





	/**
     * Checks to see if both the Kp and Ki are set.
     * @return returns true if they are both set, and false otherwise.
     */
    public boolean powerConstantsSet(){

        if (this.Kp != null && this.Ki != null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method to calculate magnitude of vector based on X and Y components
     * @param a double argument to be used as x component of vector.
     * @param a double argument to be used as y component of vector.
     * @return a Double which corresponds to the computed magnitude of the desired vector
     */
	private Double magnitude(Double x, Double y) {
		Double sum = x*x + y*y;
		return Math.sqrt(sum);
	}

	/**
     * Method to calculate Cosine of an angle given in terms of degrees
     * @param a double argument to be used as the degree of the angle being computed
     * @return a Double which corresponds to the computed cosine value of the desired angle
     */
	private Double myCos (Double deg){
		return Math.cos(Math.toRadians(deg));
	}

	/**
     * Method to calculate sine of an angle given in terms of degrees
     * @param a double argument to be used as the degree of the angle being computed
     * @return a Double which corresponds to the computed sine value of the desired angle
     */
	private Double mySin (Double deg){
		return Math.sin(Math.toRadians(deg));
	}

}
