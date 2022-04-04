import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nbrInQ1 = 0, nbrInQ2 = 0, accumulated = 0, noMeasurements = 0, intervalQ1 = 5, arrived = 0, rejected = 0;

	private final int serviceTimeQ2 = 2;

	Random slump = new Random(); // This is just a random number generator
	
	public double expRandom(double mean) {
		return -mean * Math.log(1 - slump.nextDouble());
	} //Har så länge, men borde inte vara kvar då vi inte förstår den
	
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
		if (nbrInQ1 < 10){
			nbrInQ1++;
			if(nbrInQ1 == 1){
				
				insertEvent(LEAVEQ1, time + expRandom(2.1));
				
			} 
		} else {
			rejected++;
		}
		insertEvent(ARRIVALQ1, time+intervalQ1);
	}

	private void leaQ1(){
		nbrInQ1--;
		if(nbrInQ1 > 0){
			insertEvent(LEAVEQ1, time +  expRandom(2.1));
		}
		
		nbrInQ2++;
		if(nbrInQ2 == 1){
			insertEvent(LEAVEQ2, time + serviceTimeQ2);
		}
	}
	
	private void leaQ2(){
		nbrInQ2--;
		if(nbrInQ2 > 0) {
			insertEvent(LEAVEQ2, time+serviceTimeQ2);
		}
	}
		
	private void measure(){
		accumulated = accumulated + nbrInQ2;
		noMeasurements++;
	//	insertEvent(MEASURE, time + slump.nextDouble()*10);
		insertEvent(MEASURE, time + expRandom(5));
	}
}