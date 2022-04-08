import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int nbrInNormQ = 0, nbrInSpecQ = 0, normAcc = 0, specAcc = 0, noMeasurements = 0, specLeftQ, normLeftQ;
	public double specAccTime = 0, normAccTime = 0;
	public Proc sendTo;
	Random slump = new Random();
	private LinkedList<Double> specTime = new LinkedList<Double>(), normTime = new LinkedList<Double>();

	public double expRandom(double mean) {
		return -mean * Math.log(1 - slump.nextDouble());
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case NORMARRIVAL:
				nbrInNormQ++;
				if (nbrInNormQ == 1 && nbrInSpecQ == 0){
					SignalList.SendSignal(NORMREADY,this, time + expRandom(4));
				}
				normTime.add(time);
				break;
			
			case SPECARRIVAL:
				nbrInSpecQ++;
				if(nbrInSpecQ == 1)
					SignalList.SendSignal(SPECREADY, this, time + expRandom(4));
				specTime.add(time);
				break;

			case SPECREADY:
				nbrInSpecQ--;
				if(sendTo != null)
					SignalList.SendSignal(SPECARRIVAL, sendTo, time + expRandom(5));
				if(nbrInSpecQ > 0)
					SignalList.SendSignal(SPECREADY, this, time + expRandom(4));
				else if(nbrInNormQ > 0)
					SignalList.SendSignal(NORMREADY, this, time + expRandom(4));
				specLeftQ++;
				specAccTime += time - specTime.poll();	
				break;

			case NORMREADY:
				nbrInNormQ--;
				if (sendTo != null){
					SignalList.SendSignal(NORMARRIVAL, sendTo, time + expRandom(5));
				}
				if (nbrInSpecQ > 0) {
					SignalList.SendSignal(SPECARRIVAL, this, time + expRandom(5));
				} else if (nbrInNormQ > 0){
					SignalList.SendSignal(NORMREADY, this, time + expRandom(4));
					
				}
				normLeftQ++;
				normAccTime += time - normTime.poll();
				break;

			case MEASURE:
				noMeasurements++;
				System.out.println(nbrInSpecQ);
				normAcc += nbrInNormQ;
				specAcc += nbrInSpecQ;
				
				//System.out.println("acc: " + accumulated);
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
				break;
		}
	}
}