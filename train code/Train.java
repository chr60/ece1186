package trainModel;

public class Train {
	
	//variables for train values
	Double mass, velocity, oldvelocity, power, currGrade; 
	Double Vx,Vy, oldVx, oldVy;
	Double maxPower = 120000.00; 		//maximum power is 120 kW
	Double frictionC = 0.42; 			//coefficient of friction of steel wheels on steel rails
	Double g = 9.8;						//gravity constant in m/s^2
	int TrainID;
	Double startTime;
	
	
	public Train(int ID){
		mass = 2.0; //mass = 40.9 * 907.185;  		//mass of empty car in kg
		velocity = 0.01;
		Vx = 0.01;
		Vy = 0.0;
		TrainID = ID;
		power = 0.0;
		currGrade = 0.0;
		startTime = (double) System.currentTimeMillis();		//initialize start time of train 
	}
	
	public int getID(){
		return TrainID;
	}
	
	public Double getVelocity(){
		return (velocity * 2.23694);			//convert velocity to MPH from m/s
	}
	
	public Double getMass(){
		return (mass * 2.20462);				//convert mass from Kg to lbs before display 
	}
	
	public void powerCommand(Double newPower){
		Double forceApp = newPower/velocity;
		power = newPower;
		if(newPower >= maxPower){
			//if power command is greater than or equal to max power do nothing
		}else {
			//if power command calls for increase of speed
			changeSpeed(forceApp);																					
		}
	}

	private void changeSpeed(Double Fapp) {
		// function to find new current velocity based on increase of speed
		Double Fx, Fy, Ax, Ay;
		oldVx = Vx;
		oldVy = Vy;
		Double Fn = mass * g * myCos(currGrade);
		Double Fs = frictionC * Fn;
		System.out.println("Fapp is :"+Fapp+" and Fs is :"+Fs+" and Fn is: "+Fn);
		if (currGrade >= 0){
			//if on flat land or uphill use following physics model
			Fx = (Fapp* (myCos(currGrade))) - (Fs * myCos(currGrade)) - (Fn * myCos(90.0 - currGrade));
			Fy = (Fapp* (mySin(currGrade))) - (Fs * mySin(currGrade)) + (Fn * mySin(90.0 - currGrade)) - (mass*g);
		}else{
			//if going down hill use following model
			Fx = (Fapp* (myCos(currGrade))) - (Fs * myCos(currGrade)) + (Fn * myCos(90.0 - currGrade));
			Fy = (Fs* (mySin(currGrade))) - (Fapp * mySin(currGrade)) + (Fn * mySin(90.0 - currGrade)) - (mass*g);
		}
		
		//System.out.println("\nfx is :"+Fx+" and Fy is :"+Fy);
		//using F = ma divide by mass to get acceleration vector
		Ax = Fx / mass;
		Ay = Fy / mass;
		System.out.println("\nAx is :"+Ax+" and Ay is :"+Ay);
		//using v = a*t find velocity vector by multiplying by time since train left Yard
		Double timeElapse = (double) System.currentTimeMillis() - startTime;
		startTime = (double) System.currentTimeMillis();		//reset Start time
		//System.out.println("\nTime elapse is :"+timeElapse);
		Vx = oldVx + Ax* (0.01);
		Vy = oldVy + Ay* (0.01);
		//System.out.println("\nVx is :"+Vx+" and Vy is :"+Vy);
		if (Vx < 0)
		{
			velocity = 0.01;
		}else{
			velocity = magnitude(Vx,Vy);
		}
		
		
	}
	
	
	
	private Double magnitude(Double x, Double y) {
		Double sum = x*x + y*y;
		return Math.sqrt(sum);
	}

	private Double myCos (Double deg){
		return Math.cos(Math.toRadians(deg));
	}
	
	private Double mySin (Double deg){
		return Math.sin(Math.toRadians(deg));
	}

	public Double getPower() {
		return power;
	}
	

}

