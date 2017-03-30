package TrainModel;

import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Train test = new Train(123);
		System.out.println(test.getVelocity());
		Scanner inScan = new Scanner(System.in);
		System.out.println("\n\nEnter a power: ");
		Double pow = inScan.nextDouble();
		for(int i =0; i< 100000;i++ )
		{
			
			test.powerCommand(pow);
			System.out.println("\n\nCurrent Velocity (MPH): "+test.getVelocity());
		}
	}
	

}
