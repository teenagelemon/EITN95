import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nbrInQ1 = 0, nbrInQ2 = 0, accumulated = 0, noMeasurements = 0, arrived = 0;
	public double totQueueTime = 0; //The total time jobs spends in the system
	private final double intervalQ1 = 2; //Interval arrival time to Q1
	private LinkedList<Double> timeTable = new LinkedList<>(); 
	
	
	Random slump = new Random(); // This is just a random number generator
	
	public double expRandom(double mean) {
		return -mean * Math.log(1 - slump.nextDouble());
	} 
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVALQ1:
				arrQ1();
				break;
			case LEAVEQ1:
				leaQ1();
				break;
			case LEAVEQ2:
				leaQ2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrQ1(){
		arrived++;
		nbrInQ1++;
		if(nbrInQ1 == 1){
			insertEvent(LEAVEQ1, time + expRandom(1));
		} 
		insertEvent(ARRIVALQ1, time+ expRandom(intervalQ1));
		timeTable.add(time); //Job added to system, add to time table
	}

	private void leaQ1(){
		nbrInQ1--;
		if(nbrInQ1 > 0){
			insertEvent(LEAVEQ1, time +  expRandom(1));
		}
		
		nbrInQ2++;
		if(nbrInQ2 == 1){
			insertEvent(LEAVEQ2, time + expRandom(1));
		}
	}
	
	private void leaQ2(){
		nbrInQ2--;
		if(nbrInQ2 > 0) {
			insertEvent(LEAVEQ2, time+expRandom(1));
		}
		totQueueTime += (time - timeTable.poll()); //Added the total time spent for this job
	}
		
	private void measure(){
		accumulated = accumulated + + nbrInQ1 + nbrInQ2;
		noMeasurements++;
	//	insertEvent(MEASURE, time + slump.nextDouble()*10);
		insertEvent(MEASURE, time + expRandom(5));
	}
	
	public double calcMeanTime() {
		return (totQueueTime / arrived);
	}
}