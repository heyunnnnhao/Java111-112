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
		
        Node pAdd = new Node(0, 0, null);
        Node front = pAdd;
        Node entered = poly2;
        Node thisPol = poly1;

        while (entered != null || thisPol != null) {
            boolean bothExist = (entered != null & thisPol != null);
            boolean bothEqual = false;

            if (bothExist) {
                bothEqual = (entered.term.degree == thisPol.term.degree);
            }

            if (bothExist && bothEqual) {
                pAdd.term = new Term(entered.term.coeff
                        + thisPol.term.coeff, thisPol.term.degree);

                thisPol = thisPol.next;
                entered = entered.next;
            } else {
                if (entered != null && ((thisPol == null) || entered.term.degree < thisPol.term.degree)) {
                    pAdd.term = entered.term;
                    entered = entered.next;
                } else {
                    pAdd.term = thisPol.term;
                    thisPol = thisPol.next;
                }
            }

            pAdd.next = new Node(0, 0, null);
            pAdd = pAdd.next;
        }

        Node previous = null;
        Node current = front;

        while (current != null) {
            if (current.term.coeff == 0) {
                current = current.next;
                if (previous == null) {
                    previous = current;
                } else {
                    previous.next = current;
                }
            } else {
                previous = current;
                current = current.next;
            }
        }

        pAdd = front;
        
        if (pAdd.term.coeff == 0 && pAdd.next.term.coeff == 0) {
            Node zero = new Node (0, 0, null);
            return zero;
        }
        else return pAdd;
	}	
	
	public static Node multiply(Node poly1, Node poly2) {
	
	Node pMult = new Node(0, 0, null);
        Node thisPol = poly1;      
        
        while(thisPol != null)
        {
            Node entered = poly2;
            Node temp = new Node(0, 0, null);
            Node tempCurr = temp;
            
            while(entered != null)
            {
                tempCurr.term.degree = thisPol.term.degree + entered.term.degree;
                tempCurr.term.coeff = thisPol.term.coeff * entered.term.coeff;
                if((thisPol.next != null) || (entered.next != null))
                    tempCurr.next = new Node(0, 0, null);
                tempCurr = tempCurr.next;
                entered = entered.next;
            }
            
            thisPol = thisPol.next;
            pMult = add(pMult, temp);
        }
        
        return pMult;
       
	}		
	
	public static float evaluate(Node poly, float x) {
        
        float pEval = 0;
        Node current = poly;

        while (current != null) {

            float currentValue = (float) Math.pow(x,
                    current.term.degree);
            currentValue *= current.term.coeff;

            pEval += currentValue;

            current = current.next;
        }

        return pEval;
	}
	
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String pEval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			pEval = current.term.toString() + " + " + pEval;
		}
		return pEval;
	}	
}
