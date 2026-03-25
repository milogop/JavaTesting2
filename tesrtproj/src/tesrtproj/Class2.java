package tesrtproj;

import java.util.*;

public class Class2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		/*
		System.out.println("sheeej");
		
		ArrayList<Integer> arg1 = new ArrayList<Integer>() {
			{
	        add(0);
	        add(0);
	        add(1);
			}
		};
		
		ArrayList<Integer> arg2 = new ArrayList<Integer>() {
			{
	        add(0);
	        add(1);
	        add(2);
			}
		};
		
		System.out.println(TestClass.getMinCattleDogs(3, 0, arg1, arg2)+"  -  Expected answer 3 dogs.");
		
		arg1 = new ArrayList<Integer>() {
			{
	        add(0);
	        add(0);
	        add(1);
	        add(1);
			}
		};
		
		arg2 = new ArrayList<Integer>() {
			{
	        add(2);
	        add(5);
	        add(5);
	        add(1);
			}
		};
		
		System.out.println(TestClass.getMinCattleDogs(4, 1, arg1, arg2)+"  -  Expected answer 3 dogs.");
		
		
		arg1 = new ArrayList<Integer>() {{
		    add(0); add(2); add(4);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(1); add(3); add(5);
		}};

		System.out.println(TestClass.getMinCattleDogs(3, 2, arg1, arg2) + "  -  Expected answer 3 dogs.");
		
		
		arg1 = new ArrayList<Integer>() {{
		    add(0); add(1); add(2); add(3);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(5); add(5); add(5); add(5);
		}};

		System.out.println(TestClass.getMinCattleDogs(4, 2, arg1, arg2) + "  -  Expected answer 1 dog.");
		
		
		arg1 = new ArrayList<Integer>() {{
		    add(0); add(0); add(0);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(0); add(2); add(4);
		}};

		System.out.println(TestClass.getMinCattleDogs(3, 2, arg1, arg2) + "  -  Expected answer 1 dog.");
		
		
		System.out.println("\n\n\n\n");
		
		// ------------------ TARGETED ERROR TESTS ------------------------------------------
		
		arg1 = new ArrayList<Integer>() {{
		    add(0); add(0); add(0); add(0);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(0); add(1); add(2); add(3);
		}};
		// three sheep at (0,0),(0,1),(0,2),(0,3) with d = 1 → chain connects all → answer MUST be 1
		System.out.println(TestClass.getMinCattleDogs(4, 1, arg1, arg2) + "  - LOGIC FLAW: Expected answer 1 dog.");
		
		// KEY ERROR - WHEN WE TRY TO MERGE GROUPS, WE DON'T CHECK IF THE TWO COMPARED PARTIES ARE ALREADY IN THE SAME GROUP.
		
		// -------------------------------------------------- TESTS START FAILING ------------------
		
		arg1 = new ArrayList<Integer>() {{
		    add(0); add(0); add(0); add(5); add(5);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(0); add(2); add(4); add(1); add(3);
		}};

		System.out.println(TestClass.getMinCattleDogs(5, 2, arg1, arg2) + "  -  Expected answer 2 dogs.");
		
		arg1 = new ArrayList<Integer>() {{
		    add(0); add(0); add(0); add(0);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(0); add(10); add(20); add(30);
		}};

		System.out.println(TestClass.getMinCattleDogs(4, 50, arg1, arg2) + "  -  Expected answer 1 dog.");
		
		arg1 = new ArrayList<Integer>() {{
		    add(3); add(3); add(3); add(3);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(0); add(2); add(4); add(6);
		}};

		System.out.println(TestClass.getMinCattleDogs(4, 2, arg1, arg2) + "  -  Expected answer 1 dog.");
		
		arg1 = new ArrayList<Integer>() {{
		    add(0); add(0); add(5); add(10);
		}};
		arg2 = new ArrayList<Integer>() {{
		    add(0); add(1); add(7); add(20);
		}};

		System.out.println(TestClass.getMinCattleDogs(4, 2, arg1, arg2) + "  -  Expected answer 3 dogs.");
		*/
		
		// ---------------------------------------------------------------------------------------------
		
		

        // --- Test 1: Example from the original question ---
        // Expected: 9
        test(
            Arrays.asList(
                Arrays.asList(3, 9),
                Arrays.asList(4, 7),
                Arrays.asList(1, 5)
            ),
            Arrays.asList(1, 1, 4),
            9,
            "Original example"
        );

        // --- Test 2: Single cake, can be delivered ---
        // Expected: 3
        test(
            Arrays.asList(
                Arrays.asList(3, 7)
            ),
            Arrays.asList(2),
            3,
            "Single cake, hits first deadline"
        );

        // --- Test 3: Single cake, prep time exceeds all deadlines ---
        // Expected: -1
        test(
            Arrays.asList(
                Arrays.asList(2, 4)
            ),
            Arrays.asList(5),
            -1,
            "Impossible - prep time exceeds all deadlines"
        );

        // --- Test 4: All cakes deliverable at hour 1 but only one slot ---
        // Only one cake can be delivered per hour, others must wait
        // Expected: -1 (two cakes both only available at hour 1)
        test(
            Arrays.asList(
                Arrays.asList(1),
                Arrays.asList(1)
            ),
            Arrays.asList(1, 1),
            -1,
            "Impossible - two cakes share only delivery slot"
        );

        // --- Test 5: Cakes must be interleaved to succeed ---
        // Expected: 4
        test(
            Arrays.asList(
                Arrays.asList(2, 4),
                Arrays.asList(3, 6)
            ),
            Arrays.asList(2, 2),
            4,
            "Interleaved prep required"
        );

        // --- Test 6: Large stress test - 10 cakes, wide delivery windows ---
        // Expected: should return a valid hour, not -1
        List<List<Integer>> bigDelivery = new ArrayList<>();
        List<Integer> bigPrep = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bigDelivery.add(Arrays.asList(i * 10 + 5, i * 10 + 9));
            bigPrep.add(3);
        }
        testNotMinusOne(bigDelivery, bigPrep, "Stress test - 10 cakes");
        
        
        ///---------------------------------------------------------------------------------------
        
        System.out.println("\nBEGINNING OF LESS RELIABLE TESTS - GOOD DATA SET, BUT SOLUTION NOT VERIFIED YET\n");
        
        
	     // --- Test 7: 5 cakes, varied prep times and delivery windows ---
	     // Expected: 14
	     test(
	         Arrays.asList(
	             Arrays.asList(3, 7, 12),
	             Arrays.asList(5, 10, 14),
	             Arrays.asList(2, 6, 11),
	             Arrays.asList(8, 13),
	             Arrays.asList(4, 9, 15)
	         ),
	         Arrays.asList(2, 3, 1, 4, 2),
	         14,
	         "5 cakes, varied prep and windows"
	     );
	
	     // --- Test 8: 7 cakes, some tight windows ---
	     // Expected: 18
	     test(
	         Arrays.asList(
	             Arrays.asList(2, 8, 15),
	             Arrays.asList(3, 9, 18),
	             Arrays.asList(5, 11),
	             Arrays.asList(4, 10, 16),
	             Arrays.asList(6, 12),
	             Arrays.asList(7, 14),
	             Arrays.asList(1, 13, 17)
	         ),
	         Arrays.asList(1, 3, 4, 2, 5, 3, 1),
	         18,
	         "7 cakes, some tight windows"
	     );
	
	     // --- Test 9: 6 cakes, heavy prep requirements ---
	     // Expected: 20
	     test(
	         Arrays.asList(
	             Arrays.asList(6, 12, 20),
	             Arrays.asList(5, 11, 18),
	             Arrays.asList(7, 15),
	             Arrays.asList(9, 17),
	             Arrays.asList(8, 14, 19),
	             Arrays.asList(10, 16)
	         ),
	         Arrays.asList(4, 3, 5, 6, 2, 4),
	         20,
	         "6 cakes, heavy prep requirements"
	     );
    }
	
	

    private static void test(List<List<Integer>> deliveryHours,
                              List<Integer> preparationTimes,
                              int expected,
                              String label) {
        int result = TestClass2.getMinimumHours(deliveryHours, preparationTimes);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.printf("[%s] %s  →  got %d, expected %d%n", status, label, result, expected);
    }

    private static void testNotMinusOne(List<List<Integer>> deliveryHours,
                                         List<Integer> preparationTimes,
                                         String label) {
        int result = TestClass2.getMinimumHours(deliveryHours, preparationTimes);
        String status = result != -1 ? "PASS" : "FAIL";
        System.out.printf("[%s] %s  →  got %d (expected: not -1)%n", status, label, result);
    }
    
}



