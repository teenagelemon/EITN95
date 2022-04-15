import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();
		Dispatch dispatcher = new Dispatch();
		dispatcher.disRule = SHORT; //ROUND for Round robin, RANDOM for Random and SHORT for choosing the the one with the shortest que

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	Gen Generator = new Gen();
    	Generator.lambda = 9.09; //Change this value: 9.09 for 0.11/s, 6.66 for 0.15/s and 0.5 for 2.00/s 
    	Generator.sendTo = dispatcher; //De genererade kunderna ska skickas till k�systemet QS  // The generated customers shall be sent to Q1

    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list
		
		SignalList.SendSignal(READY, Generator, time);
		for(QS q : dispatcher.queues) {
    		SignalList.SendSignal(MEASURE, q, time);
		}
    	// Detta �r simuleringsloopen:
    	// This is the main loop

    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}
		
		double meanNbrJobs = 0;
    	for (QS q : dispatcher.queues) {
    		meanNbrJobs += (1.0*q.accumulated) /  (1.0*q.noMeasurements);
    	}
    	System.out.println(meanNbrJobs);
    }
}