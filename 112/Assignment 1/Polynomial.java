package poly;

import java.io.IOException;
import java.util.Scanner;

public class Polynomial {

	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	public static Node add(Node poly1, Node poly2) {
		
		
		return null;
	}	
	
	public static Node multiply(Node poly1, Node poly2) {
	
		return null;
	}		
	
	public static float evaluate(Node poly, float x) {
		
		return 0;
	}
	
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}