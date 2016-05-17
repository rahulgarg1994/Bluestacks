/**
 * Created by Rahul_Garg on 5/17/2016.
 */

import java.io.*;

public class Status extends Thread {
	public final static     int sleepTimeSecs       = 5;
	public final static boolean changes = false ;
	public final static String status      = "status.yml" ;
	int timer = 0;
	
	public void run() {
		setName("Status");
		log("Thread started");
		String previous_status = "";
		String current_status  = "";
		String current_status_long;
		try {
			while(true) {
				current_status      = City.getInstance().currentState(false);
				current_status_long = City.getInstance().currentState(true);
				if (changes) {
					if (current_status != previous_status) {
						log("Status changed: "+current_status);
					}					
				} else {
					log("Status: "+current_status);
				}

				try {
					File f = new File(status);
					FileWriter fstream = new FileWriter(f);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(current_status_long);
					out.close();
				} catch (Exception e) {
					log("Error");
				}
				Thread.sleep(sleepTimeSecs * 1000);
				timer++;
			}
		} catch (InterruptedException e) {
			log("Interrupted");
		}
	}
    public void  log(String message) {		//logs a generic message
        print(message);
    }

    private void print(String message) {
        System.out.println(message);
    }
	
}