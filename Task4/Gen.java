import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gen extends Proc{

	//Slumptalsgeneratorn startas:
	//The random number generator is started:
	Random slump = new Random();

	//Generatorn har tv� parametrar:
	//There are two parameters:
	public Proc sendTo;    //Anger till vilken process de genererade kunderna ska skickas //Where to send customers
	public double lambda;  //Hur m�nga per sekund som ska generas //How many to generate per second
	public int arrivals = 0; 
	//H�r nedan anger man vad som ska g�ras n�r en signal kommer //What to do when a signal arrives
	
	
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case READY:
				if(slump.nextInt(2) == 0) //Change nextInt value for different cases
					SignalList.SendSignal(SPECARRIVAL, sendTo, time);
				else 
					SignalList.SendSignal(NORMARRIVAL, sendTo, time);
				SignalList.SendSignal(READY, this, time + randomExpCalc(5));
				break;
		}
	}
	
	private double randomExpCalc(double mean) {
		double lambda = 1/mean;
		return -(1/lambda) * Math.log(1 - slump.nextDouble());
	}
	

}