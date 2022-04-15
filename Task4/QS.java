import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public int specQ = 0, normQ = 0, sReady = 0, nReady = 0, arrived = 0;
	public double totTimeS = 0, totTimeN = 0, waitTimeS = 0, waitTimeN;
	public LinkedList<Double> normal = new LinkedList<Double>(), special = new LinkedList<Double>();
	public Proc sendTo;
	Random slump = new Random();
	public boolean newCashierNeeded = false;

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

			case SPECARRIVAL:
				specarr();
				break;

			case NORMARRIVAL:
				normarr();
				break;
			
			case READY:
				ready();
				break;
		}
			
	}

	private double randomExpCalc(double mean) {
		double lambda = 1/mean;
		return -(1/lambda) * Math.log(1 - slump.nextDouble());
	}

	private void specarr(){
		arrived++;
		specQ++;
		special.add(time);
		if (specQ + normQ == 1) {
			SignalList.SendSignal(READY, this, time + randomExpCalc(4));
		}

	}

	private void normarr(){
		arrived++;
		normQ++;
		normal.add(time);
		if (specQ + normQ == 1) {
			SignalList.SendSignal(READY, this, time + randomExpCalc(4));
		}
	}

	private void ready(){
		if (specQ > 0){
			waitTimeS = (time - special.poll());
			totTimeS += waitTimeS;
			specQ--;
			sReady++;
		} else if (normQ > 0){
			waitTimeN = (time - normal.poll());
			totTimeN += waitTimeN;
			normQ--;
			nReady++;
		} 
		if(waitTimeS > 15.0 || waitTimeN > 15.0){
			newCashierNeeded = true;
		}
		if (specQ > 0 || normQ > 0){
			SignalList.SendSignal(READY, this, time + randomExpCalc(4));
		}
	}
}