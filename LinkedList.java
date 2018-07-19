

/*
 * Maan Qraitem 
*/

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.util.Random;

public class LinkedList<T> implements Iterable<T> {


	/* Container classs that holds a genericc object and
	 * a next pointer
	 */
	private class Node{
		private Node next;
		private Node prev;
		private T thing;

		public Node(T item){
			thing = item; // holds the data stored in the Node.
			next = null; // holds a reference to the next node.
			prev = null; // holds a reference to the previous node.
		}

		// returns the data stored in the node.
		public T getThing() {
			return thing;
		}

		// sets the reference to the next node.
		public void setNext (Node n){
			next = n;
		}

		// sets the refernce to the previous node.
		public void setPrev (Node n){
			prev = n;
		}

		// returns the reference to the next node.
		public Node getNext(){
			return next;
		}

		// returns the reference to the previous node.
		public Node getPrev(){
			return prev;
		}
	}

	/* iterator subclass handling traversing the list */
	private class LLIterator implements Iterator <T> {
		private Node nextNode;

		public LLIterator (Node head){
			nextNode = head;
		}

		public boolean hasNext (){
			return nextNode != null;
		}

		public T next (){
			if (nextNode == null) return null;

			T t = nextNode.getThing();
			nextNode = nextNode.getNext();

			return t;
		}

		//optional

		public void remove ()  {}
	}

	/* iterator subclass handling traversing the list backwards.  */
	private class LLBackwardIterator implements Iterator<T>{

		private Node prevNode;

		public LLBackwardIterator (Node head){
			prevNode = tail;
		}

		public boolean hasNext(){
			return prevNode != null;
		}

		public T next() {
			if (prevNode == null) return null;

			T t = prevNode.getThing();
			prevNode = prevNode.getPrev();

			return t;
		}

	}

	// initiliaze the values of the linked list.
	private Node head;
	private Node tail;
	private int size;

	public LinkedList(){
		head = null;
		tail = null;
		size = 0;
	}


	// clear the whole linked list.
	public void clear(){
		head = null;
		tail = null;
		size = 0;
	}

	//returns the size of the linked list.
	public int size(){
		return size;
	}

	// gets the data stored in the node at a specific inedex.
	public T get(int index){

		Node thing = head;
		if (size == 0){
			return null;
		}

		//traverse the whole list until it gets to the requried index.
		for (int i = 0; i < index; i ++){
			thing = thing.getNext();
		}

		return thing.getThing();
	}


	// adds to the beginning of the list.
	public T addFirst(T item){
		Node t = new Node(item);
		Node thing = this.head;

		if (size == 0){
			head = t;
			tail = t;
			size ++;
			return t.getThing();
		}

		else{
			t.setNext(head);
			thing.setPrev(t);
			head = t;
			size ++;
			return t.getThing();
		}
	}

	// adds to the end of the list.
	public T addLast(T item){
		Node t = new Node(item);
		Node thing = head;

		if (thing == null){
			head = t;
			tail = t;
			size ++;
			return t.getThing();
		}

		else {
			for (int i = 0; i < size - 1; i++){
				thing = thing.getNext();
			}
			thing.setNext(t);
			t.setPrev(thing);
			tail = t;
			size ++;
			return t.getThing();
		}
	}

	// adds to a specific index in the list.
	public T add(int index, T item){
		Node t = new Node(item);
		Node thing = head;

		if (thing == null){
			head = t;
			tail = t;
			size ++;
			return t.getThing();
		}

		else if (index == 0){
			t.setNext(head);
			thing.setPrev(t);
			head = t;
			size ++;
			return t.getThing();
		}
		else if (size - 1 == index) {
			for (int i = 0; i < size - 1; i++){
				thing = thing.getNext();
			}
			thing.setNext(t);
			t.setPrev(thing);
			tail = t;
			size ++;
			return t.getThing();
		}

		else {
			for (int i = 0; i < index - 1; i ++){
				thing = thing.getNext();
			}
			t.setNext(thing.getNext());
			thing.setNext(t);
			t.setPrev(thing);
			thing.getNext().setPrev(t);
			size ++;
			return t.getThing();
		}

	}

	// removes any element at any index
	public void remove (int index){
		Node thing = head;

		if (index == 0){
			head = thing.getNext();
			head.setPrev(null);
		}

		else if (size - 1 == index){
			for (int i = 0; i < size - 2; i++){
				thing = thing.getNext();
			}
			thing.getNext().setPrev(null);
			thing.setNext(null);
			tail = thing;
		}
		else {
			for (int i = 0; i < index - 1; i ++){
				thing = thing.getNext();
			}
			Node start = thing;
			thing = thing.getNext().getNext();
			start.setNext(thing);
			thing.setPrev(start);
		}

		size --;
	}


	// removes the first elemnt from the linked list.
	public T removeFirst (){
		Node thing = head;

		if (this.size == 0){
			return null;
		}

		if (this.size == 1){
			head = null;
			tail = null;
			size --;
			return thing.getThing();
		}

		head = thing.getNext();
		head.setPrev(null);
		size --;
		return thing.getThing();
	}

	// removes the last element from the linked list.
	public void removeLast(){
		Node thing = head;

		if (this.size == 0){
			return;
		}

		if (this.size == 1){
			head = null;
			tail = null;
			size --;
		}

		for (int i = 0; i < size - 2; i++){
			thing = thing.getNext();
		}
		thing.getNext().setPrev(null);
		thing.setNext(null);
		tail = thing;
		size --;
	}

  	// Return a new LLIterator pointing to the head of the list
 	public Iterator<T> iterator() {
      	return new LLIterator( this.head );
  	}

	  public Iterator<T> backward_iterator() {
	      return new LLBackwardIterator(this.tail);
	}

  	public ArrayList<T> toArrayList(){
  		Random rand = new Random();
  		Node thing = head;
  		ArrayList<T> list = new ArrayList<T>();
  		for (int i = 0; i < size - 1; i++){
  			list.add(thing.getThing());
  			thing = thing.getNext();
		}
		list.add(thing.getThing());
		return list;
  	}


  	public ArrayList<T> toShuffledList(){
  		Node thing = head;
  		ArrayList<T> list = new ArrayList<T>();
  		Random rand = new Random();
  		for (int i = 0; i < size - 1; i++){
  			list.add(thing.getThing());
  			thing = thing.getNext();
		}
		list.add(thing.getThing());
		int size = list.size();
		ArrayList<T> temp = new ArrayList<T>();

		for (int x = 0; x < size; x ++) {
			int rand2 = rand.nextInt(list.size());
			temp.add(list.remove(rand2));

		}

		return temp;

  	}


public static void main(String[] args) {

		LinkedList<Integer> llist = new LinkedList<Integer>();

		llist.addFirst(5);
		llist.addFirst(10);
		llist.addFirst(20);

		System.out.printf("\nAfter setup %d\n", llist.size());
		for(Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		llist.clear();

		System.out.printf("\nAfter clearing %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		llist.addLast(5);
		llist.addLast(10);
		llist.addLast(20);

		System.out.printf("\nAfter setup %d\n", llist.size());
		for(Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		llist.clear();

		System.out.printf("\nAfter clearing %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		for (int i = 10; i > 0; i -= 2) {
			llist.add(0, i);
		}
		llist.add(5, 12);
		llist.add(3, 0);

		System.out.printf("\nAfter setting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		llist.remove(0);
		System.out.printf("\nAfter removing %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		llist.remove(2);
		System.out.printf("\nAfter removing %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		llist.remove(4);
		System.out.printf("\nAfter removing %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		llist.removeLast();
		llist.removeFirst();

		System.out.printf("\nAfter removing %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}

		 System.out.printf( "\nIterating backward %d\n", llist.size());
		 Iterator bi = llist.backward_iterator();
		 while (bi.hasNext()) {
		    System.out.printf("thing %d\n", bi.next());
		  }

	}
}
