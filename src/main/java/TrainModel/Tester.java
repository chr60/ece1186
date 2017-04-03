package TrainModel;

import java.util.Scanner;
//THIS CLASS IS USED FOR TESTING VARIOUS FUNCTIONS WITHOUT USING GUI.
//WILL BE DELETED UPON COMPLETTION OF THIS PROJECT
//CODED OUT FUNCTIONS WILL ALSO BE STORED HERE TO DECLUTTER USEFUL CODE

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Train test = new Train(123);
		//System.out.println(test.getVelocity());
		Scanner inScan = new Scanner(System.in);
		System.out.println("\n\nEnter a power: ");
		Double pow = inScan.nextDouble();
		for(int i =0; i< 100000;i++ )
		{
			
			//test.powerCommand(pow);
			//System.out.println("\n\nCurrent Velocity (MPH): "+test.getVelocity());
		}
	}
	
//code to comptue speed using my complex physics. fix later if time otehrwise stick to simple physics
	//changeSpeed(Double)
	
	/*
	oldVelocity = velocity;
	oldVx = Vx;
	oldVy = Vy;
	Double Fn = mass * g * myCos(currGrade);
	Double Fs = frictionC * Fn;
	System.out.println("\nForce App is :"+Fapp);
	//System.out.println("Fapp is :"+Fapp+" and Fs is :"+Fs+" and Fn is: "+Fn);
	if (currGrade >= 0){
		//if on flat land or uphill use following physics model
		Fx = (Fapp* (myCos(currGrade))) - (Fs * myCos(currGrade)) - (Fn * myCos(90.0 - currGrade));
		Fy = (Fapp* (mySin(currGrade))) - (Fs * mySin(currGrade)) + (Fn * mySin(90.0 - currGrade)) - (mass*g);
	}else{
		//if going down hill use following model
		Fx = (Fapp* (myCos(currGrade))) - (Fs * myCos(currGrade)) + (Fn * myCos(90.0 - currGrade));
		Fy = (Fs* (mySin(currGrade))) - (Fapp * mySin(currGrade)) + (Fn * mySin(90.0 - currGrade)) - (mass*g);
	}

	System.out.println("\nfx is :"+Fx+" and Fy is :"+Fy);
	//using F = ma divide by mass to get acceleration vector
	Ax = Fx / mass;
	Ay = Fy / mass;
	if (Ax > 0.5)
	{
		Ax = 0.5;
	}

	if (Ay > 0.5)
	{
		Ay = 0.5;
	}

	System.out.println("\nAx is :"+Ax+" and Ay is :"+Ay);
	//using v = a*t find velocity vector by multiplying by time since train left Yard
	Vx = oldVx + Ax;		//assume time interval is 1 second (multiply by 1)
	Vy = oldVy + Ay;
	if (Vx < 0)
	{
		Vx = 0.00;
	}
	if (Vy < 0)
	{
		Vy = 0.00;
	}

	System.out.println("\nVx is :"+Vx+" and Vy is :"+Vy);
	velocity = magnitude(Vx,Vy);
	System.out.println("\nVelocity is :"+velocity);


	if (velocity > 19.4444)              //70 kph in m/s (max velocity)
	{
		velocity = 19.4444;
	}

	*/
	
	
	
	/*
	 * 
	 
	 
     * Method to calculate safe emergency Braking Distance of train based on its current velocity and mass
     * @return a Double which corresponds to the amount of distance required to stop the train using the emergency brake
     
	private Double safeBrakingDist(Double Drate){
	//compute distance required to stop based on rate selected
	/* Safe braking distance computation found on https://pdfs.semanticscholar.org/bdd1/42932455dce2c08b8027bd9672aa0ed548f6.pdf
		S = -((U + b*td)^2)/2(a + b) - U*td- (b*td^2)/2
		"U" is the speed of the train when the brake command was issued
		"a" is the acceleration provided by the braking system
		"b" is the acceleration provided by gravity
		"td" is the train's brake delay time
	

		Double SEBD =0.0;
		return SEBD;
	
	*/
}
