/**
 * Created by Rahul_Garg on 5/17/2016.
 */

import java.util.Random;
	
class ShipmentHandler extends Thread {
	public static Random random = new Random();

	public void run() {
		setName("ShipmentHandler");
		log("started");
		try {
			while (true) {
				Thread.sleep(random.nextInt( City.RandomShipmentPeriodMs ));
				Shipment recurrent = Shipment.getRandomShipment();
				City.getInstance().deliverShipment(recurrent);
				log("New Shipment available: "+recurrent);
			}
		} catch (InterruptedException e) {
			log("Error");
		}
	}

	public void  log(String message) {		//logs a generic message
		print(message);
	}

	private void print(String message) {
		System.out.println( message);
	}
}