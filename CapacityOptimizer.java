public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
		
		int lotSize = 1;
		int sum;
		double average;
		Simulator theMatrix;
		
		while(true){
		
			sum = 0;

			System.out.println("====== Setting lot capacity to: " + lotSize + "======");
			System.out.println("");

			for(int i=0; i<NUM_RUNS; i++){
				long mainStart = System.currentTimeMillis();
				theMatrix = new Simulator(new ParkingLot(lotSize), hourlyRate, 86400);
				theMatrix.simulate();
				long mainEnd = System.currentTimeMillis();
				System.out.println("Simulation run " + (i+1) + " (" + (mainEnd - mainStart) + "ms);" + " Queue length at the end of simulation: " + theMatrix.getIncomingQueueSize());
				sum += theMatrix.getIncomingQueueSize();
			}
			average = sum/10;
			System.out.println("");
			
			if(average <= THRESHOLD){
				break;
			}else{
				lotSize++;
			}
		}
		return lotSize;
	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}