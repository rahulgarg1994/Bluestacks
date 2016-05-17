/**
 * Created by Rahul_Garg on 5/17/2016.
 */

import java.util.Arrays;
import java.util.ArrayList;

public class Station{
	private ArrayList<Train> trains_here;
	private ArrayList<Shipment> shipments;
	private              int index;
	protected         Object depotLock  = new Object() ;


	public Station(int label) {
		index = label;
		trains_here = new ArrayList<Train>();
		shipments = new ArrayList<Shipment>();
	}

	public synchronized void addTrain(Train newTrain) {
		synchronized(trains_here) {
			if (trains_here.indexOf(newTrain) != -1) {
				System.err.println("This train is already present: " + newTrain);
			} else {
				trains_here.add(newTrain);				
			}
		}
	}


	public synchronized void removeTrain(Train train) {
		synchronized(trains_here) {
			if (trains_here == null) {
				System.err.println("No Train");
			}
			trains_here.remove(train);
		}
	}
	
	public boolean isEmpty() {
		return trains_here.isEmpty();
	}
	public String toString(boolean verbose) {
		if (verbose) 
			return "Station"+index+"(Shipment:"+ shipments.size() +", Trains:"+trains_here+")";
		else {
			int occurrences[]; 
			occurrences = new int[ trains_here.size() ];
			for (int i=0; i< trains_here.size(); i++) {
				occurrences[i] = trains_here.get(i).occurrence;
			}
			return "Station"+index+"(Shipment:"+ shipments.size() +", Trains:"+Arrays.toString(occurrences)+")";
		}
	}
	public String toString() {
		return toString(true); 
	}
	public String toStringMini() {
		return toString(false);
	}
	public int getPosition() {
		return 2 * index;
	}

	public synchronized void addShipment(Shipment c) {
		shipments.add(c) ;
	}

	public synchronized ArrayList<Shipment> getShipments() {
		return shipments;
	}

	public synchronized void removeShipment(int ShipmentIndex) {
		this.shipments.remove(ShipmentIndex);
	}

	public int getIndex() {
		return index;
	}

	public synchronized Shipment removeAndGetShipment() {
		return shipments.remove(0);
	}

}