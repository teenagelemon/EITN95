import java.util.*;

import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState;
    	int[] delaySettings = {1, -1, 1};
    	boolean[] prioSettings = {true, true, false};
    	String[] text = {"Const_delay_B", "Exp_delay_B", "Const_delay_A"};
    	for(int i = 0; i < 3; i++) {
	    	actState = new State(delaySettings[i], prioSettings[i]); // The state that should be used
	
	    	// Some events must be put in the event list at the beginning
	    	insertEvent(ARRIVAL_TO_A, 0);  
	        insertEvent(MEASURE, 0.1);
	        
	        // The main simulation loop
	    	while (actState.noMeasurements < 1000){
	    		actEvent = eventList.fetchEvent();
	    		time = actEvent.eventTime;
	    		actState.treatEvent(actEvent);
	    	}
	    	// Printing the result of the simulation, in this case a mean value
	    	//System.out.println(1.0*actState.accumulated/actState.noMeasurements);
	    	
	    	StringBuilder nbrAStr = new StringBuilder();
	    	StringBuilder nbrBStr = new StringBuilder();
	    	for(java.awt.Point p : actState.points) {
	    		nbrAStr.append(((int)p.getX()) + " ");
	    		nbrBStr.append(((int)p.getY()) + " ");
	    	}
	    	//nbrAStr.delete(nbrAStr.length()-2, nbrAStr.length());
	    	//nbrBStr.delete(nbrBStr.length()-2, nbrBStr.length());
	    	//System.out.println(" ");
	    	System.out.println(nbrAStr.toString());
	    	System.out.println(" ");
	    	System.out.println(nbrBStr.toString());
	    	System.out.println(" ");

    	}
    }
}