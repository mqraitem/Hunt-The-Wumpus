

/*
 * Maan Qraitem 
*/



import java.util.Iterator;



public class MyQueue<T> implements Iterable<T> {

	private LinkedList<T> queue; // initialize the linked list.
	private int capacity; // specifies the capacity.


	// constructor to initialize the fields.
	public MyQueue (int cap){
		queue = new LinkedList<T>();
		capacity = cap;
	}


	// adds to the queue by adding to the end of the list.
	public T offer(T item){
		if (getSize() == capacity){
				return null;
		}
		return queue.addLast(item);
	}

	// polls the first element from the list.
	public T poll(){
		return queue.removeFirst();
	}

	// returns the value of the first element.
	public T peek(){
		return queue.get(0);
	}

	// returns the size of the queue.
	public int getSize(){
		return queue.size();
	}

	// returns an iterator.
	public Iterator<T> iterator(){
		return queue.iterator();
	}

	// testing.
	public static void main(String[] args) {
			MyQueue<Integer> myQ = new MyQueue<Integer>(10);

			//empty queue test
			System.out.println(myQ.peek());
			System.out.println(myQ.poll());

			for(int i = 0; i < 10; i++){
				myQ.offer(i);
			}
			for (Integer i : myQ) {
				System.out.println("thing: " + i);
			}
			//overflow test
			System.out.println(myQ.offer(10));

			System.out.println(myQ.peek());
			System.out.println(myQ.poll());

			for(int i = 0; i < 10; i++){
				myQ.poll();
			}

			System.out.println(myQ.getSize());

			for(Integer i : myQ) {
				System.out.println("thing: " + i);
			}


		}
}
