/**
 * Created by Rahul_Garg on 5/17/2016.
 */

public class Track {
	Train train_here;
	int position;

	public Track(int pos) {
		position = pos;
		train_here = null ;
	}
	public boolean isBusy () {
		return ! isEmpty() ;
	}
	
	public synchronized void addTrain(Train newTrain)  {
		if (train_here == newTrain) {
			System.err.println("Register\n"
				+ " New:     " + newTrain   + '\n'
				+ " No: " + train_here + '\n'
			);
			System.exit(67);
		}
		if (train_here != null) {
			System.err.println("Collided:\n"
				+ " NEW:  " + newTrain   + '\n'
				+ " HERE: " + train_here + '\n'
			);
			System.exit(66);
		}
		train_here = newTrain;
	}
	

	public synchronized void removeTrain(Train oldTrain) {
		if (train_here != oldTrain) {
			System.err.println("No Train Present:\n"
					+ " - THIS: " + oldTrain   + '\n'
					+ " - HERE: " + train_here + '\n'
				);
			System.exit(99);
		}
		train_here = null;
	}
	
	public boolean isEmpty() {
		return train_here == null;
	}

	public String toString() {
		return "Track"+position+"("+ (train_here == null ? '-' : train_here)+")";
	}

	public String toStringMini() {
		return "Track"+position + (train_here == null
					? "" 
					: "{" + train_here.toStringMini() + "}"
				);
	}
	public int getPosition() {
		return 2 * position + 1;
	}

}
