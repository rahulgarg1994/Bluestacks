/**
 * Created by Rahul_Garg on 5/17/2016.
 */

import java.util.Random;

public class Shipment {
	private static Random random = new Random();
	
	int source;
	int destination;
	int absolutePosition ;

	public Shipment(int src, int dest) {
		source      = src;
		destination = dest;
	}
	Shipment(int pos) {
		absolutePosition = pos;
	}

	public static int nextPos(int pos) {
		int next = pos+1;
		if (next == City.NumberOfStations * 2)
			next = 0;
		return next;
	}

	public String toString() {
		return "Source("+source+"->"+destination+")";
	}
	

	public static Shipment getRandomShipment() {
		int srcStation = random.nextInt( City.NumberOfStations );
		int dstStation = random.nextInt( City.NumberOfStations -1);
		if (dstStation >= srcStation)
			dstStation++;
		return new Shipment(srcStation,dstStation);
	}

	public int getSource() {
		return source;
	}

	public int getDestination() {
		return destination;
	}

}