import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int accumulated = 0, noMeasurements = 0;
	public int nbrOfA = 0, nbrOfB = 0; //Counters that count the number of type As and Bs in the buffer
	
	private final int lambda = 150, d = 1;
	private final double x_A = 0.002, x_B = 0.004, interval_measure = 0.1, oneDivLambda = 1 / lambda;
	private final int delaySetting; //1 if constant delay is to be used, otherwise -1
	private final boolean bHasPrio; //If type B is to have priority
	Random slump = new Random(); // This is just a random number generator
	public List<DataPlotPoint> dataPlotPoints = new ArrayList<DataPlotPoint>(); //Point to used in the plot
	
	public State(int delaySetting, boolean bHasPrio) {
		this.delaySetting = delaySetting;
		this.bHasPrio = bHasPrio;
	}
	
	public double expRandom(double mean) {
		return -mean * Math.log(1 - slump.nextDouble());
	}
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_TO_A:
				arrival_to_a();
				break;
			case ARRIVAL_TO_B:
				arrival_to_b();
				break;
			case LEAVE_A:
				leave_a();
				break;
			case LEAVE_B:
				leave_b();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	//Calculates the delay time depending on the delay settings
	private double calcDelayFromSettings() {
		if(delaySetting < 0)
			return expRandom(1);
		else 
			return 1;
	}
	
	//Handles arrival for jobs of type A to the buffer
	private void arrival_to_a() {
		if(nbrOfA == 0 && nbrOfB == 0) {
			insertEvent(LEAVE_A, time + x_A);
		}
		nbrOfA++;
		insertEvent(ARRIVAL_TO_A, time + expRandom(0.006666));
	}
	
	//Handles arrival for jobs of type B to the buffer
	private void arrival_to_b() {
		if(nbrOfA == 0 && nbrOfB == 0)
			insertEvent(LEAVE_B, time + x_B);
		nbrOfB++;

	}
	
	//Handles departure for jobs of type A in the buffer
	private void leave_a() {
		nbrOfA--;
		insertEvent(ARRIVAL_TO_B, time + calcDelayFromSettings());
		if(bHasPrio)
			prioritizeTypeB();
		else 
			prioritizeTypeA();
	}
	
	//Handles departure for jobs of type B in the buffer
	private void leave_b() {
		nbrOfB--;
		if(bHasPrio)
			prioritizeTypeB();
		else 
			prioritizeTypeA();	
	}
	
	//Handles event insertion if priority type is B
	private void prioritizeTypeB() {
		if(nbrOfB > 0)
			insertEvent(LEAVE_B, time + x_B);
		else if(nbrOfA > 0)
			insertEvent(LEAVE_A, time + x_A);
	}
	
	//Handles event insertion if priority type is A
	private void prioritizeTypeA() {
		if(nbrOfA > 0)
			insertEvent(LEAVE_A, time + x_A);
		else if(nbrOfB > 0)
			insertEvent(LEAVE_B, time + x_B);
	}
	
	private void measure(){
		accumulated = accumulated + nbrOfA + nbrOfB;
		noMeasurements++;
		dataPlotPoints.add(new DataPlotPoint(nbrOfA, nbrOfB)); //Store data for plot
		insertEvent(MEASURE, time + interval_measure);
	}
	
	//Class used to collect data points for the plot
	public class DataPlotPoint {
		public int nbrOfA = 0, nbrOfB = 0;
		
		public DataPlotPoint(int a, int b) {
			nbrOfA = a;
			nbrOfB = b;
		}
	}
}