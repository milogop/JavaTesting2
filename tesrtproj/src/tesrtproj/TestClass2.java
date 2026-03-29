package tesrtproj;


import java.io.*; 
import java.math.*; 
import java.security.*; 
import java.text.*; 
import java.util.*; 
import java.util.concurrent.*; 
import java.util.function.*; 
import java.util.regex.*; 
import java.util.stream.*; 
import static java.util.stream.Collectors.joining; 
import static java.util.stream.Collectors.toList; 


/*
"Making Cakes"

You are running a bakery. There are n cakes that you need to make over a continuous time period measured in hours. A 2D array deliveryHours of size n x m specficies the hours each cake can be delivered. The i'th cake can be delivered at any hour listed in deliverHours[[i].

Each cake needs to be prepared before it is delivered. An array preparationTime of n integers indicated the hours required for each cake to be prepared.

Each hour, one (and only one) of the following actions can be taken:
- PRepare the i'th cake for one hour.
- Deliver the i'th cake if it is fully prepared and the current hour is a number in deliveryHours[i]
- Do nothing.

Determine the minimum nuumber of hours required to deliver all cakes.
Return -1 if it is impossible.

Notes:
- each cake must be delivered exactly once.
- a cake cannot be prepared for more than the specified number of hours.
- Cake preparation does not have to take place in consecutive hours, you can pause preparation for as along as you want. You may also "interleave" preparation. i.e. prepare cake 2 in hour 1, cake 3 in hour 2, then cake 2 again in hour 3.

Example:
n = 3
m = 2
deliveryHours = [[3,9], [4,7], [1,5]]
prepationTime = [1,1,4]

An optimal way to deliver the cakes is:
Cake     Preparation Hours   Delivery Hour
Cake 1    1st, 2nd, 3rd, 4th    5th
Cake 2    6th                          7th
Cake 3    8th                          9th
Hence the minimum number of hours required is 9 as the last delivery occurs on the 9th hour.

Function Description:
Complete the function getMinimumHours in the editor with the following parameter(s):
- int deliveryHours[n][m]: The hours on which the cakes can be delivered.
- int preaparationTime[n]: The number of hours reuqired to prepare the cake

Returns:
- int: The minimum number of hours rqeuired to deliver all cakes or -1 if it is impossible.

Constraints:
- 1 <= n, m <= 2 * 10^5
- 1 <= n * m <= 2 * 10^5
- 1 <= deliveryHours[i][j] <= 10^9
- 1 <= preparationTime[i] <= 10^9
- it is guaranteed the 2D array deliveryHours consists of distinct integers.

---------------------------
*/

//Here is my commented work on the function already.
	

public class TestClass2 {

	
	public static int getMinimumHours(List<List<Integer>> deliveryHours, List<Integer> preparationTimes) {
		
		
		// instead of a trial and error step-forward approach, lets pick a target hour to try and deliver all cakes by, then loop closer to correct 'minimum time' after each schedule feasibility calculation.
		
		// yes this will be computationally expensive but unlike a heuristic approach this one will guarantee the correct answer is reached, at least.
		
		// we can use binary search instead of an exhaustive loop because the hours are technically 'ordered' in that all hours before the target will fail and all hours after will be successful (but not the minimum).
		
		
		int min = 1;
		int max = 1000000000; // 10^9 is upper bound.
		
		while (min < max) {
			int mid = (min + max) / 2;
			if (checkScheduleCorrectness(mid,deliveryHours,preparationTimes)) {
				max = mid; // Found a correct schedule, now we need to see if we can find one that can be satisfied at an earlier time.
			}
			else {
				min = mid + 1;
			}
		}
		
		if (checkScheduleCorrectness(min, deliveryHours,preparationTimes)) {
			return min;
		}
		else {
			return -1;
		}
		
	}
	
	
	
	
	// ---------------------------------------------------------------------------------------------------------
		
	private static boolean checkScheduleCorrectness(int T, List<List<Integer>> deliveryHours, List<Integer> preparationTimes) {
		

		
		// ------------------------- set up ordered list of deadlines to iterate through.
			
			
        List<int[]> cakes = new ArrayList<>(); // for clarity, represent each cake as a pairing of latest delivery time and preparation time.
        int cakeIndex = 0;
	        
		/*
	        for (Integer i : preparationTimes) {
	        	cakes.add(new int[]{deliveryHours.get(cakeIndex).get(deliveryHours.get(cakeIndex).size()-1), i}); // get the latest of the two delivery hours and use as index.
	        	//System.out.println(cakes.get(cakes.size()-1));
	        	cakeIndex++;
	        }
	        
	        cakes.sort(
	        		(a, b) -> a[0] - b[0]
	        );
		*/

		// NEW VERSION
        for (Integer prep : preparationTimes) {
    		List<Integer> slots = deliveryHours.get(cakeIndex);
    		int latestValid = -1;
    		for (int slot : slots) {
        		if (slot <= T && slot > latestValid) {
            			latestValid = slot;
        		}
    		}
    		if (latestValid == -1) {
    			return false; // no valid slot for this cake
    		}
		cakes.add(new int[]{latestValid, prep});
   		cakeIndex++;
		}

		cakes.sort(
        		(a, b) -> a[0] - b[0]
        );
        
        // ----------------------- feasibility check code :
        
        
        int currentTime = 0;
		int freeHours = 0;
		for (int i = 0; i < cakes.size(); i++) { // for each cake in sorted order:
		    	int deadline = cakes.get(i)[0]; // deadline = cake[0]
		    	int prepNeeded = cakes.get(i)[1];	// prepNeeded = cake[1]
		    	freeHours += deadline - currentTime - 1;  // -1 because delivery takes the deadline hour itself
				if (prepNeeded <= freeHours) {
					currentTime = deadline; // += prepNeeded + 1; // spend those prep hours
				}
				else {
					return false; // not enough room
				}
		}
		return true;
		
	}
}













