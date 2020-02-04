/*************************************************************************
 *  Compilation:  javac IntLinkedList.java
 *  Execution:    java IntLinkedList n  (n is the max size of the list)
 *
 *  @author: Andy Guna
 *
 *  This is a demo program to understand LL operations and Big O
 *
 *************************************************************************/

public class IntLinkedList {
  //create a node class
  private static class Node {
      String data;
      Node next;
      // constructor
      public Node(String n, Node ptr ){
        data = n;
        next = ptr;
      }

      public String toString(){
        return "data:" + data ;
      }
  } 

  /* check LL invariants */
  public static boolean isLL() {
       return true;
  }

  /*
      ADD A FRONT NODE
      input: 
      output: 
      return : 
      Big O : 
  */
  public static Node addFront(Node front, int data) {
    return null;
  }

  /*
      DELETE FRONT NODE
      input: 
      output: 
      return : 
      Big O : 
  */
  public static Node deleteFront(Node front) {
    return null;
  }

  /*
      TRAVERSE THE LIST
      input: 
      output: 
      Return : 
      Big O : 
  */
  public static void traverse(Node front) {
    for (Node ptr=front; ptr!=null; ptr=ptr.next)
      // System.out.println(ptr.data);
      System.out.println(ptr);
  }

  /*
      SEARCH THE LIST
      input: 
      output : 
      return: 
      Big O : 
  */
  public static boolean search(Node front, int target){
    return true;
  }

  /* Driver program to test LL methods */
  public static void main(String[] args)
  {
     //int key = Integer.parseInt(args[0]);  
    Node front = new Node("me",null);
    Node N = new Node("you",null);
    front.next =  N;
   // traverse(front);
    N = new Node("him", null);
    // insert between 12 and 24
    N.next = front.next;
    front.next = N;
     //N.next = front.next;  <--- dont do this :(
    // delete the last node
     front.next.next = null;
     traverse(front);
  }

}
         
  