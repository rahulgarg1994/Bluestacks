/**
 * Created by Rahul_Garg on 5/17/2016.
 */

import java.util.ArrayList;
import java.lang.Math;


public class Train extends Thread {
    int speed ;
    int position ;
	ArrayList<Shipment> shipments;
    int occurrence;
    final int ShipmentCapacity;
    TrainStatus trainstatus;
    public static boolean Verbose = true;
    
    public enum TrainStatus { STATION_START, STATION_UNLOAD_Shipment, STATION_LOAD_Shipment, STATION_END };

    public Train(int n, int railway_period, int initial_position, int Shipment_capacity) {
        this.speed      = railway_period;
        this.position      = 2*initial_position;
        this.shipments = new ArrayList<Shipment>();
        this.occurrence    = n;
        this.ShipmentCapacity = Shipment_capacity;
        this.trainstatus = TrainStatus.STATION_START;
    }


    public void run() {
    	System.out.println("Train started: "+ toString() );
		setName("Train."+occurrence);
    	log("started: "+ toString() );

    	myStation().addTrain(this);
    	while (true) {
    		set_status(TrainStatus.STATION_START);
    		set_status(TrainStatus.STATION_UNLOAD_Shipment);
    		set_status(TrainStatus.STATION_LOAD_Shipment);
    		set_status(TrainStatus.STATION_END);
    	}
    }

    public synchronized void  registerToStation(int station_number) {
    	Station myStation = City.getInstance().getStation(station_number);
    	log("Train entering station: "+ myStation.toStringMini() );
    	myStation.addTrain(this);
    }
    

    public synchronized void register(int railway_number) {
    	Track myTrack = City.getInstance().getTrack(railway_number);
    	Station myStation = City.getInstance().getStation(railway_number);

    	myStation.removeTrain(this);

    	while(myTrack.isBusy()) {
    		try {
    			wait();
    		} catch (InterruptedException e) {
    			log("Error");
    		}
    	}
 
    	myTrack.addTrain(this);

    	try {
    		log("Train entering railway track: " + myTrack.toStringMini() );
        	Thread.sleep(speed * 1000);
    	} catch (InterruptedException e) {
    		log("Train interrupted when on Railway: "+ myTrack);
    	}
    	
    	myTrack.removeTrain(this);
    }

    public synchronized void set_status(TrainStatus new_status) {
    	trainstatus = new_status;

    	switch(new_status) {
    		case STATION_START:
    	        registerToStation(position/2);
    			break ;
    		case STATION_UNLOAD_Shipment:
    			unloadShipment();
    			break;
    		case STATION_LOAD_Shipment:
    			loadShipment();
    			break;
    		case STATION_END:
    	        increment_position();
    	        register(position/2);
    	        increment_position();
    	        notifyAll();
    	        break;
    	}
    }

    private void increment_position() {
		position = Shipment.nextPos(position);
	}

	void unloadShipment() {
		synchronized(shipments) {
	    	for(int k = 0; k < shipments.size(); k++) {
	    		Shipment c = shipments.get(k);
	    		if (c.destination == myStation().getIndex() ) {
	    			Shipment tmpShipment = shipments.remove(k);
	    			vlog("Shipment correctly unloaded on station "+myStation().toStringMini()+": " + tmpShipment);
	    		}
	    		try {
	        		Thread.sleep(City.UnloadTimeMilliSecs);
	    		} catch (Exception e) {
	    			log("Interrupted while unloading..");
	    		}
	    	}
		}
	}

	private Station myStation() {
			return City.getInstance().getStation(position/2);
	}

	public synchronized void loadShipment() {
		Station myStation = myStation();
		synchronized(myStation.depotLock) {
			int nShipmentsToGet = Math.min(
					ShipmentCapacity - shipments.size(),
					myStation.getShipments().size()
				);
			for (int i=0 ; i < nShipmentsToGet ; i++) {
				shipments.add(
						myStation.removeAndGetShipment()
				);
			}
    		try {
        		Thread.sleep(City.LoadTimeMilliSecs);
    		} catch (Exception e) {
    			log("Interrupted while loading..");
    		}
		}
	}

    public String toStringVerbose() {
        return "Train."+occurrence
        		+ "("
        		+ "position => " + position
        		+ ",Shipment => "  + shipments.size()
        		+ ")";
    }
	@Override
	public String toString() {
		return toStringMini();
	}
	public String toStringMini() {
		return "Train"+occurrence+"(Position"+position+",Shipment"+ shipments.size()+")";
	}
    public void vlog(String message) {
        if (Verbose) {
            print(message);
        }
    }
    public void  log(String message) {		//logs a generic message
        print(message);
    }

    private void print(String message) {
        System.out.println(message);
    }
}