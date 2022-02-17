 /** An SLList is a list of integers, which hides the terrible truth
   * of the nakedness within. */
public class SLList {
	private static class IntNode {
		public int item;
		public IntNode next;

		public IntNode(int i, IntNode n) {
			item = i;
			next = n;
		}
	}

	/* The first item (if it exists) is at sentinel.next. */
	private IntNode sentinel;
	private int size;

	/** Creates an empty SLList. */
	public SLList() {
		sentinel = new IntNode(63, null);
		size = 0;
	}

	public SLList(int x) {
		sentinel = new IntNode(63, null);
		sentinel.next = new IntNode(x, null);
		size = 1;
	}

	public SLList(int[] items){
		sentinel = new IntNode(63, null);
		IntNode p = sentinel;
		for (int i = 0; i != items.length; ++i){
			p.next = new IntNode(items[i], null);
			p = p.next;
			++size;
		}
	}

 	/** Adds x to the front of the list. */
 	public void addFirst(int x) {
 		sentinel.next = new IntNode(x, sentinel.next);
 		size = size + 1;
 	}

 	/** Returns the first item in the list. */
 	public int getFirst() {
 		return sentinel.next.item;
 	}

 	/** Adds x to the end of the list. */
 	public void addLast(int x) {
 		size = size + 1; 		

 		IntNode p = sentinel;

 		/* Advance p to the end of the list. */
 		while (p.next != null) {
 			p = p.next;
 		}

 		p.next = new IntNode(x, null);
 	}

 	/** Returns the size of the list. */
 	public int size() {
 		return size;
 	}

	 /** Deletes the first ite, in the list. */
	 public void deleteFirst(){
		 sentinel.next = sentinel.next.next;
		 sentinel.next = null;
	 }

	public static void main(String[] args) {
 		/* Creates a list of one integer, namely 10 */
 		SLList L = new SLList();
 		L.addLast(20);
 		//System.out.println(L.size());
		int[] arr = new int[] {0, 2, 4, 6, 8};
		SLList L_a = new SLList(arr);
		L_a.deleteFirst();
 	}
}