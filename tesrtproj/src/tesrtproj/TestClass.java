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

class TestClass { 
	/* 
	 * Complete the 'getMinCattleDogs' function below. * 
	 * The function is expected to return an INTEGER. 
	 * The function accepts following parameters: 
	 * * 1. INTEGER n 
	 * * 2. INTEGER d 
	 * * 3. INTEGER_ARRAY x
	 *  * 4. INTEGER_ARRAY y
	 */
	public static int getMinCattleDogs(int n, int d, List<Integer> x, List<Integer> y) { 
		// 2d array 
		// vector pos based on bottom left corner. 
		// minimum number of "dogs" to touch every sheep. 
		// sheep in associative groups based on distance, can hit all sheep in one group at a time. 
		// n = number of sheep in pasture 
		// d = friendship distance. 
		// sheep positions determined by 2 array inputs: 
		// x = [0, 0, 1, 2] // y = [0, 1, 0, 2] 
		// means sheep at (0,0), (0,1), (1,0), (2,2) 
		
		ArrayList<ArrayList<Integer[]>> groups = new ArrayList<ArrayList<Integer[]>>(); // list of all the groups. 
		ArrayList<Integer[]> GROUPED = new ArrayList<Integer[]>(); // list of all the sheep that Have a group. 
		
		// create list of sheep. 
		ArrayList<Integer[]> sheep = new ArrayList<Integer[]>(); 
		
		for (int i = 0; i < x.size(); i++) { 
			Integer[] beast = {x.get(i), y.get(i)}; 
			sheep.add(beast); 
			// System.out.println(beast[0] + "," + beast[1]); 
		} 
		
		// lets do an initial pass. 
		
		for (Integer[] origin : sheep) { 
			for (Integer[] checked : sheep) { // every sheep group with the immediate neighbour within d distance. 
				
				int dist = distBetweenSheep(origin, checked);
				if (dist <= d && dist != -1) { // if either the root sheep or the neighbour sheep is in 'grouped' list, add it to the "group" array which the root sheep already exists in. 
					if (GROUPED.contains(origin) || GROUPED.contains(checked)) { 
						if (GROUPED.contains(origin) && GROUPED.contains(checked)) { // if both are in groups, merge the groups. 
							
							if (origin == checked) {
								continue;
							}
							
							// find the group that contains origin, add all the elements from checked, then delete the checked group. 
							for (int k = 0; k < groups.size(); k++) { 
								if (groups.get(k).contains(origin)) { 
									for (int i = 0; i < groups.size(); i++) { 
										if (groups.get(i).contains(checked)) { 
											
											if (k != i) { // NEW ADDITION :::::::::::::::::::: FORGOT TO CHECK IF COMPARISON IS IN SAME GROUP, SO DELETING THE "OTHER GROUP" DELETED SELF.
											
												groups.get(k).addAll(groups.get(i)); // add all nodes in 'checked' sheep's group to 'origin' sheep's group. 
												groups.remove(i); // remove the group containing the 'checked' sheep. 
												break;
											}
												
												
										} 
									} 
									break; 
								} 
							} 
						} 
						else { 
							if (GROUPED.contains(origin)) { // if origin is in group but checked isn't, add origin to checked. (remember to add the 'checked' sheep to 'grouped' set too) 
								for (int i = 0; i < groups.size(); i++) { 
									if (groups.get(i).contains(origin)) { 
										groups.get(i).add(checked); 
										GROUPED.add(checked); 
										break; 
									} 
								} 
							} 
							else { // vice versa. 
								for (int i = 0; i < groups.size(); i++) { 
									if (groups.get(i).contains(checked)) { 
										groups.get(i).add(origin); 
										GROUPED.add(origin); 
										break; 
									} 
								} 
							} 
						} 
					} 
					else { 
						// if the sheep and its neighbour are both not in a group yet, create a new group. 
						ArrayList<Integer[]> newGroup = new ArrayList<Integer[]>(); 
						newGroup.add(origin);
						GROUPED.add(origin); 
						newGroup.add(checked); 
						GROUPED.add(checked); 
						groups.add(newGroup); 
					} 
				} 
			} 
		} 
		
		// then we just return 1 dog for each group. 
		
		int dogs = 0; 
		for (ArrayList<Integer[]> group : groups) { 
			dogs += 1; 
		} 
		return dogs; 
	} 
	
	// function for determining distance between two sheep. 
	
	public static int distBetweenSheep(Integer[] point1, Integer[] point2) { 
		if (point1[0] == point2[0]) { 
			return Math.abs(point1[1] - point2[1]); 
		} 
		if (point1[1] == point2[1]) { 
			return Math.abs(point1[0] - point2[0]); 
		} 
		// FRIENDS = SAME X OR Y COORDINATE. 
		// no diaognals. 
		// if not aligned in either x or y, distance becomes irrelevant, return -1. 
		return -1; 
	} 

}