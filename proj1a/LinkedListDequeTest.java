/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	
	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");
		System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
		/*
		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		printTestStatus(passed);
		*/
	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");

		System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);

	}

	/** My implementation to print the nodes. Not a "REAL" test, but rather used to see what will happen. */
	public static void printTest(){
		System.out.println("Running printing test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		lld1.addFirst(10);
		lld1.addFirst(11);
		lld1.addFirst(12);

		lld1.addLast(13);
		lld1.addLast(14);
		lld1.addLast(15);
		lld1.addLast(16);

		lld1.removeFirst();
		lld1.removeLast();

		lld1.printDeque();

	}

	/** My implementation to tests on ArrayDeque. Just for visualizer use. */
	public static void ArrayTests() {
		System.out.println("Running tests on ArrayDeque");

		ArrayDeque<String> ad1 = new ArrayDeque<String>();

		ad1.addLast("a");
		ad1.addLast("b");
		ad1.addFirst("c");
		ad1.addLast("d");
		ad1.addLast("e"); // wrapping around adding
		ad1.addFirst("f");

		ad1.removeFirst();
		ad1.removeLast();
		ad1.removeFirst();
		ad1.removeLast();
		ad1.removeFirst();
		ad1.removeLast(); // wrapping around removal

		ad1.removeFirst();
		ad1.removeLast();

		ad1.addLast("a");
		ad1.addLast("b");
		ad1.addLast("c");
		ad1.addLast("d");
		ad1.addLast("e");
		ad1.addLast("f");
		ad1.addLast("g");
		ad1.addLast("h");
		ad1.addLast("i");
		ad1.addLast("j");

		ad1.addLast("a");
		ad1.addLast("b");
		ad1.addLast("c");
		ad1.addLast("d");
		ad1.addLast("e");
		ad1.addLast("f");
		ad1.addLast("g");
		ad1.addLast("h");
		ad1.addLast("i");
		ad1.addLast("j");

	}

	/** Another informal test*/
	public static void ArrayResizeTests() {
		ArrayDeque<String> ad1 = new ArrayDeque<String>();

		ad1.addFirst("a");
		ad1.addFirst("b");
		ad1.addFirst("c");
		ad1.addFirst("d");
		ad1.addFirst("e");
		ad1.addFirst("f");
		ad1.addFirst("g");
		ad1.addFirst("h");
		ad1.addFirst("i");
		ad1.addFirst("j");

		ad1.addFirst("a");
		ad1.addFirst("b");
		ad1.addFirst("c");
		ad1.addFirst("d");
		ad1.addFirst("e");
		ad1.addFirst("f");
		ad1.addFirst("g");
		ad1.addFirst("h");
		ad1.addFirst("i");
		ad1.addFirst("j");
	}

	public static void ArrayResizeTests2(){
		ArrayDeque<String> ad1 = new ArrayDeque<String>();
		ad1.addFirst("a");
		ad1.addFirst("b");
		ad1.addFirst("c");
		ad1.addFirst("d");
		ad1.addFirst("e");
		ad1.addFirst("f");
		ad1.addFirst("g");
		ad1.addFirst("h");
		ad1.addFirst("i");
		ad1.addFirst("j");

		ad1.get(0);
		ad1.get(1);
		ad1.get(2);
		ad1.get(3);
		ad1.get(4);
		ad1.get(5);
		ad1.get(6);


		ad1.addLast("a");
		ad1.addLast("b");
		ad1.addLast("c");
		ad1.addLast("d");
		ad1.addLast("e");
		ad1.addLast("f");
		ad1.addLast("g");
		ad1.addLast("h");
		ad1.addLast("i");
		ad1.addLast("j");

		ad1.addFirst("a");
		ad1.addFirst("b");
		ad1.addFirst("c");
		ad1.addFirst("d");
		ad1.addFirst("e");
		ad1.addFirst("f");
		ad1.addFirst("g");
		ad1.addFirst("h");
		ad1.addFirst("i");
		ad1.addFirst("j");

		ad1.addLast("a");
		ad1.addLast("b");
		ad1.addLast("c");
		ad1.addLast("d");
		ad1.addLast("e");
		ad1.addLast("f");
		ad1.addLast("g");
		ad1.addLast("h");
		ad1.addLast("i");
		ad1.addLast("j");

		ad1.get(0);
		ad1.get(1);
		ad1.get(2);
		ad1.get(3);
		ad1.get(4);
		ad1.get(5);
		ad1.get(6);

		ad1.printDeque();
	}

	public static void reductionTest1() {
		ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

		for (int i = 0; i != 20; ++i){
			ad1.addLast((i*10) + 100);
		}
		for (int i = 0; i != 12; ++i){
			ad1.removeLast();
		}
		ad1.removeLast();
		ad1.addFirst(90);
		ad1.addLast(170);
	}

	public static void reductionTest2() {
		ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

		for (int i = 0; i != 20; ++i){
			ad1.addFirst((i*10) + 100);
		}
		for (int i = 0; i != 12; ++i){
			ad1.removeFirst();
		}
		ad1.removeFirst();
	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
//		addIsEmptySizeTest();
//		addRemoveTest();
//		printTest();
//		ArrayTests();
//		ArrayResizeTests();
//		ArrayResizeTests2();
//		reductionTest1();
		reductionTest2();
	}
} 