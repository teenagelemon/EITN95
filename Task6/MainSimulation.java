import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Random slump = new Random();
		int n=1;
		double medeltid=0;
		
		while(n<10001){
		Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(M1, (1+4*slump.nextDouble())); 
		insertEvent(M2, (1+4*slump.nextDouble()));
		insertEvent(M3, (1+4*slump.nextDouble()));
		insertEvent(M4, (1+4*slump.nextDouble()));
		insertEvent(M5, (1+4*slump.nextDouble()));  
        
        
        // The main simulation loop
		boolean EveryoneIsbroken=false;

    	while (EveryoneIsbroken==false){
    		actEvent = eventList.fetchEvent(); //Fetch the first componenet/machine that breaksdown 
    		time = actEvent.eventTime; //the time for the components life span.
    		actState.treatEvent(actEvent);
			EveryoneIsbroken=actState.Everyoneisbroken1();  //when every component is broken, this returns true and the loop breaks.
			
    	}
		medeltid=medeltid+time; //saves the machine that had the longest lifespan/the last machine that was taken out of the linkedlist.
    	//System.out.println(medeltid);
		n++;
    	// Printing the result of the simulation, in this case a mean value
	}
		
		
		
		System.out.println("Mean time for the system to break " + (medeltid/n)); //meantime for the system to breakdowm
    }
}