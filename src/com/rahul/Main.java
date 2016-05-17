/**
 * Created by Rahul_Garg on 5/17/2016.
 */

public class Main {
	
	public static void main(String[] args)  {
		
		System.out.println("Starting simulation with "+
				City.NumberOfTrains +" trains and "+ City.NumberOfStations +" stations");

		ShipmentHandler shipmentThread = new ShipmentHandler();
		shipmentThread.start();
		Thread statusThread = new Status();
		statusThread.start();


		System.out.println("Exiting main thread");
	}
}
