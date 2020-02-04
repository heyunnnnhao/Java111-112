/*************************************************************************
 *  Compilation:  javac GenericLinkedList.java
 *  Execution:    java GenericLinkedList 
 *
 *  @author: Andy Guna
 *
 *  This is a demo program to understand Generic LL operations and Big O
 *
 *************************************************************************/

import java.util.NoSuchElementException;

 class Node<T> {   // Node class has a generic type parameter T
  T data;
  Node<T> next;

  public Node(T data, Node<T> next) { // constructor name does NOT have a <T> next to it
    this.data = data;
    this.next = next;
  }

  public String toString() {
    return data.toString();
  }
}


public class GenericLinkedList<T> {

  Node<T> front;
  int size;

  public GenericLinkedList() {  // empty linked list to start with
    front = null;
    size = 0;
  }

  public void addFront(T item) {
    front = new Node<T>(item, front);
    size++;
  }

  public void deleteFront() 
      throws NoSuchElementException {
    if (front == null) {
      //throw new NoSuchElementException();
      throw new NoSuchElementException("Can't delete from an empty list");
    }
    front = front.next;
    size--;
  }

  public boolean search(T target) {
    for (Node<T> ptr=front; ptr != null; ptr=ptr.next) {
      if (target.equals(ptr.data)) {
        return true;
      }
    }
    return false;
  }

  // while loop version, stylized single-line output
  public void traverse() {
    if (front == null) {
      System.out.println("Empty list");
      return;
    }
    System.out.print(front.data);  // first item
    Node<T> ptr=front.next;    // prepare to loop starting with second item
    while (ptr != null) {
      System.out.print(" --> " + ptr.data);
      ptr = ptr.next;
    }
    System.out.println();
  }

  public T getFront() 
      throws NoSuchElementException {
    if (front == null) {
      //throw new NoSuchElementException();
      throw new NoSuchElementException("Can't get at front of an empty list");
    }
    return front.data;
  }

  public int getSize() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public static void main(String[] args) {

    // set up a linked list of Strings
    GenericLinkedList<String> strLL = new GenericLinkedList<>();
    strLL.addFront("cs112");
    strLL.addFront("cs111"); 
    strLL.traverse();
    strLL.deleteFront();
    strLL.traverse();
    boolean found = strLL.search("cs112");
    if (found) {
      System.out.println("found \"cs112\"\n");
    }

    // set up a linked list of integers
    GenericLinkedList<Integer> intll = new GenericLinkedList<>();
    int x=5;
    intll.addFront(x);  // auto boxes value of x into an Integer object
    intll.addFront(15); // auto boxes -15 into an Integer object
    intll.addFront(-10);  // auto boxes -10 into an Integer object



    intll.traverse();

    x = intll.getFront();  // auto unboxes returned object into a primitive
    System.out.println("front = " + x);

    try {
      System.out.println("\ndeleting front");
      intll.deleteFront();
      intll.traverse();
      System.out.println("\ndeleting front");
      intll.deleteFront();
      intll.traverse();
      System.out.println("\ndeleting front");
      intll.deleteFront();
      intll.traverse();
      System.out.println("\ndeleting front");
      
      intll.deleteFront(); // trying to delete from an empty list
      
      System.out.println("DONE!");
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    } 
  }

}