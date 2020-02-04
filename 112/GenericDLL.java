/*************************************************************************
 *  Compilation:  javac GenericDLL.java
 *  Execution:    java GenericDLL
 *
 *  @author: Andy Guna
 *
 *  This is a demo program to understand Generic DLL operations and Big O
 *
 *************************************************************************/

import java.util.NoSuchElementException;

 class Node<T> {   // Node class has a generic type parameter T
  T data;
  Node<T> next;
  Node<T> prev;

  public Node(T data, Node<T> next, Node<T> prev) { // constructor name does NOT have a <T> next to it
    this.data = data;
    this.next = next;
    this.prev = prev;
  }

  public String toString() {
    return data.toString();
  }
}

/* The generic DLL */
public class GenericDLL<T> {

  Node<T> front;
  int size;

  public GenericDLL() {  // empty linked list to start with
    front = null;
    size = 0;
  }

  public void addFront(T item) {
    if (front==null) 
      front = new Node<T>(item, null, null);
    else {
      Node<T> N = new Node<T>(item, null, null);
      N.next = front;
      front.prev = N;
      front = N;
    }
    size++;
  }

  public Node<T> getFront() {
    return front;
  }

  public void deleteFront() throws NoSuchElementException {
    if (front == null) {
      throw new NoSuchElementException("Can't delete from an empty list");
    }
    /* need work to fix this for DLL */
    front = front.next;
    /* need more operations */
    size--;
  }
  /* traverses the DLL with printing nodes */
  public void traverse(){
      Node<T> ptr = front;
      while (ptr!=null){
        System.out.println(ptr);
        ptr = ptr.next;
      }
  }

  public int getSize() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public static void main(String[] args) {
      GenericDLL<String> myDLL = new GenericDLL<>();
      myDLL.addFront("guna");
      myDLL.addFront("Andy"); 
      myDLL.traverse(); 
    }

}