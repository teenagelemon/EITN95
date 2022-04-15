	import java.util.*;
	import java.io.*;


	public class Dispatch extends Proc {
		
		Random rand = new Random();
		public ArrayList<QS> queues = new ArrayList<QS>();		
		public int disRule, count = 0, qSize = 5, accumulated, noMeasurements;
		public Proc dest;
		
		public Dispatch() {
			
		for(int i = 0; i < qSize; i++) {
			queues.add(new QS());		}
		}
		
		@Override
		public void TreatSignal(Signal x){
			switch(x.signalType){
				case ARRIVAL:
					
					switch (disRule) {
					case RANDOM:
						dest = queues.get(rand.nextInt(5));
					break;

					case ROUND:
						count = (count + 1) % queues.size();
						dest = queues.get(count);
						count++;
					break;

					case SHORT:
						ArrayList<QS> shortest = new ArrayList<QS>(); 
						int min = Integer.MAX_VALUE;
						for (QS queue : queues){
							if(queue.numberInQueue < min){
								shortest.clear();
								shortest.add(queue);
								min = queue.numberInQueue;
							} else if (queue.numberInQueue == min){
								shortest.add(queue);
							}
						}
						dest = shortest.get(rand.nextInt(shortest.size()));
					break;
					}
					
					SignalList.SendSignal(ARRIVAL, dest, time);
				break;
			}				
		}
	}