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

class TestClass2 { 
	
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
		
		
		// KEY REASLISATION: The multiple possible deadlines were tripping me up a lot in terms of which deadline should be considered in what order, recursive permutations of all of these, etc.
		// BUT ITS ACTUALLY SIMPLE --- literally, just pick the latest deadline that fits within time limit T. Every time. There is zero benefit to delivering a cake on an earlier deadline,
		// ALSO -- Backloading deliveries could cause issues because of the whole "its not a deadline, its a singular event timeslot", BUT -- the key realisation is that deliveryTimes are DISTINCT, so there's never an overlap, which means backloading them is not a risk.
		
		
		
		// Feasibility check:
		
		/// assign each cake to its latest deadline <= T.
		// make sure target time T is not smaller than any cake's both delivery time options.
		
		// step through list of deadlines you prepared.
		
		// POP first deadline cake to heap.
		
		// check every slack by calculating time between current time (prep finished of cake 1, not its deadline) and that cake's own deadline.
		
		// pick cake with lowest slack to pop from heap?
		
		// ???
		
		// max heap brings cake with NEXT DEADLINE to top of heap because these have the highest urgency to finish before their deadline.
				
		// between the new time that this cake has finished prep, get a new gap for the next highest 'latest deadline', repeat steps above.
		
		// if we reach a point where the current time + the cake with the lowest slack exceeds T (aka the next cake has negative slack), abandon schedule.
		
		
		/*
		* pop next deadline cake.
		* free hours = cake deadline - current time (aka. slack)
		* if prepTime <= freeHours, cake is next in schedule.
		* else,
		* schedule is abandoned.
		*/
		
		
		// ------------------------- set up ordered list of deadlines to iterate through.
		
		
        List<int[]> cakes = new ArrayList<>(); // for clarity, represent each cake as a pairing of latest delivery time and preparation time.
        int cakeIndex = 0;
        
        for (Integer i : preparationTimes) {
        	cakes.add(new int[]{deliveryHours.get(cakeIndex).get(deliveryHours.get(cakeIndex).size()-1), i}); // get the latest of the two delivery hours and use as index.
        	System.out.println(cakes.get(cakes.size()-1));
        	cakeIndex++;
        }
        
        cakes.sort(
        		(a, b) -> a[0] - b[0]
        );
        
        
        // ----------------------- feasibility check code :
        
        /*
         * While cakes != empty:
         * add cake with earliest deadline to scheduledCake list.
         * if time between now and its deadline > 0:	
         * 		calculate slack for all cakes at index > i.
         * 		remove cake with lowest slack
         * 		add it to the scheduledCake list.
         * 		if no more cakes can fit, start on cake with next deadline
         */
        
        // wrong. dont need to pick cake based on slack.
        
        int currentTime = 0;
		for (int i = 0; i < cakes.size(); i++) { // for each cake in sorted order:
		    	int deadline = cakes.get(i)[0]; // deadline = cake[0]
		    	int prepNeeded = cakes.get(i)[1];	// prepNeeded = cake[1]
		    	int freeHours = deadline - currentTime - 1;  // -1 because delivery takes the deadline hour itself
				if (prepNeeded <= freeHours) {
					currentTime += prepNeeded; // spend those prep hours
				}
				else {
					return false; // not enough room
				}
		}
		return true;
		
	}
}






















//* OLD PLAN: 
		// public static int getMinimumHours(List<List<Integer>> deliveryHours, List<Integer> preparationTimes) {
		//wont have time to complete even a draft fo this question so ill just see if I can plot out a commented plan and submite that.
		
		// cake[i] can be delivered at any time listed in deliveryHours[i] (where deliveryHours is 2d array of timeslots where delivery is accepted for that cake element)
		// cake[i] also has preparationTime[i] which says an integer of how long cake[i] takes to make.
		
		// oh this is cool.
		
		// okay so:
		// - run though a loop of hours until all cakes are delivered.
		// - each hour, choose between:
		//    preparation a cake
		//   deliver a fully prepared cake (if the current hour matches a viable delivery hour for that cake)
		//   do nothing
		
		// ideas for solution:
		
		/*
		
		order cakes by earliest delivery hour in ascending order.
		
		for cake 1, go to first delivery time and work backwards a number of hours equal to preparation time to find ‘baseline preparation start time’ for that first delivery to be met.
		 
		for cake 2, repeat.
		
		If there are not enough “free hours” to meet cake 2’s first deadline while also meeting cake 1’s first deadline… 
		~ metric for efficiency is how long we have to wait until next viable delivery timeslot.
		~ therefore, if cake2 can’t be fit greedily doing all its prep after cake1 does its own thing, put all remaining hours after cake1 toward whichever cake will have the GREATEST CONSEQUENCE for missing its next possible timeslot (aka most wait time between that timeslot and the one that comes after for that cake).
		
		This doesn’t guarantee optimised hour usage, but it’s a decent heuristic to start from. Start with the earliest possible cake, fully prepare it, and then when choosing the next target, pick either: The next earliest cake that can be fully prepared before its first deadline OR (if none are possible) second deadline OR (if no deadlines can be met) put the spare hours toward the cake which has the greatest consequence for missing a deadline. The deadline won’t be met, but it does mitigate the aftereffect of that by making sure its ready as soon as it can be on the following day, rather than a potential repeat of it not having the priority  it needs to be completed.
		
		The other aspect to this idea: We keep using the spare hours until we reach a point where, if we swap to cake X and only work on that Cake, the cake will be prepared and delivered by its deadline. Then, we can repeat from the start of this process, choosing the next cake based on plausibility of completion before deadlines, and chipping away at the “highest consequence failure” if absolutely no deadlines can be reached during the current day anymore.
		*/
		
		// return 0;		
//






