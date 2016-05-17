/**
 * Created by Rahul_Garg on 5/17/2016.
 */


public class City {

	public static City singleton_instance;

	public static final int NumberOfStations      = 8 ;
	public static final int NumberOfTrains        = 4 ;
	public static final int RandomShipmentPeriodMs = 10000 ;
	public static final int UnloadTimeMilliSecs   = 300 ;
	public static final int LoadTimeMilliSecs     = 200 ;
	public static final int ShipmentCapacity         = 15 ;
	
	final Station[] stations = new Station[City.NumberOfStations];
	final Track[] tracks = new Track[City.NumberOfStations];
	final   Train[] trains   = new   Train[City.NumberOfTrains];

	private City() {
		for(int i = 0; i < City.NumberOfStations; i++) {
			stations[i] = new Station(i);
			tracks[i] = new Track(i);
		}
		for(int i = 0; i < City.NumberOfTrains ; i++) {
 			trains[i] = new Train(
					i ,
					4+i,
					2*i,
					City.ShipmentCapacity
			); 
 			trains[i].start();
		}
	}


	public static City getInstance() {
		if (singleton_instance == null) {
			singleton_instance = new City();
		}
		return singleton_instance;
	}
	

	public String currentState(boolean verbose) {
		String st = "";
		if (verbose) {
			st += "# Current City Status \n";
			for(int i=0;i<NumberOfStations;i++) {
				st += " Station "+i+": ";
				st += stations[i].toString();
				st += '\n';
				st += " Track "+i+": ";
				st += tracks[i].toString();
				st += '\n';
			}
		} else {
			for(int i=0;i<NumberOfStations;i++) {
				st += '[';
				st += stations[i].toStringMini();
				st += ']';
				st += "  ";
				st += "#";
				st += tracks[i].toStringMini();
				st += "#";
			}			
		}
		return st;
	}

	public void deliverShipment(Shipment shipment) {
		int src = shipment.getSource();
		stations[src].addShipment(shipment);
	}
	
	public String toString() {
		return currentState(false);
	}
	
	public Station getStation(int which) {
		return stations[which];
	}

	public Track getTrack(int track_number) {
		return tracks[track_number];
	}
}
