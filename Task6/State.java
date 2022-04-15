import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public boolean Machine1IsAllive = true;
	public boolean Machine2IsAllive = true;
	public boolean Machine3IsAllive = true;
	public boolean Machine4IsAllive = true;
	public boolean Machine5IsAllive = true;
	//public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. så här kommer den maskinen som har minst tid tas ut, och dens metod kommer köras.
	public void treatEvent(Event x) {
		switch (x.eventType){
			case M1:
			    m1();	
				break;
			case M2:
				m2();
				break;
			case M3:
				m3();
				break;
			case M4:
				m4();
				break;
			case M5:
				m5();
				break;
		
		}
	
	
	}

	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	public boolean Everyoneisbroken1(){

		if((Machine1IsAllive == false) && (Machine2IsAllive==false)  && (Machine3IsAllive ==false) && (Machine4IsAllive ==false) && (Machine5IsAllive ==false)){
		   
			   return true; //if every machine is broken, return true.
		   }
			   return false;
		   }


	
	private void m1(){
		Machine1IsAllive=false;
		Machine2IsAllive=false;
		Machine5IsAllive=false;
		Everyoneisbroken1(); //check if every machine is broken.

		}
	private void m2(){
		Machine2IsAllive=false;
		Everyoneisbroken1();
	
	}
	private void m3(){		                               

		Machine3IsAllive=false;
		Machine4IsAllive=false;
		Everyoneisbroken1();
	}
	private void m4(){
		Machine4IsAllive=false;
		Everyoneisbroken1();
	}
	private void m5(){
		Machine5IsAllive=false;
		Everyoneisbroken1();
	}
	
	   
	   

}
