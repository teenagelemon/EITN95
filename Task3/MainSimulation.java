import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVALQ1, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (actState.noMeasurements < 1000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	System.out.println("Mean in queue: " + ((float)actState.accumulated/actState.noMeasurements) + " mean time spent: " + actState.calcMeanTime());
    	// Printing the result of the simulation, in this case a mean value
    	//System.out.println("Interval Q1: " + actState.intervalQ1 + " mean in Q2: " + 1.0*actState.accumulated/actState.noMeasurements + " prob rejected " + ((double)actState.rejected/actState.arrived));
    }
}