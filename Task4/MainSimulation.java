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


    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 0.20; //1 person arriving per minute
    	Generator.sendTo = Q1; //De genererade kunderna ska skickas till k�systemet QS  // The generated customers shall be sent to Q1

    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	//SignalList.SendSignal(MEASURE, Q1, time);


    	// Detta �r simuleringsloope n:
    	// This is the main loop

    	while (Q1.arrived < 1000 ){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	
		
		System.out.println("Mean queue time for the customers in the normal queue: " + Q1.totTimeN/Q1.nReady + 
			"\n" + "Mean queue time for the customers in the special queue: " + Q1.totTimeS/Q1.sReady);
		if (Q1.newCashierNeeded){
			System.out.println("A new cashier is needed!");
		}
    }
}