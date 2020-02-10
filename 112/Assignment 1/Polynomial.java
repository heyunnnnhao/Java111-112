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
        Node create = pMult;
        Node entered = poly2;
        Node thisPol = poly1;

        int high = 0;
        int low = 999999;

        while (entered != null) {
            thisPol = poly1;
            while (thisPol != null) {
                if (thisPol.term.degree + entered.term.degree > high)
                    high = thisPol.term.degree + entered.term.degree;
                if (thisPol.term.degree + entered.term.degree < low)
                    low = thisPol.term.degree + entered.term.degree;
                
                thisPol = thisPol.next;
            }
            
            entered = entered.next;
        }

        entered = poly2;
        for (int i = low; i <= high; i++) {
            create.term.degree = i;
            create.term.coeff = 0;
            
            create.next = new Node (0, 0, null);
            create = create.next;
        }

        entered = poly2;
        while (entered != null) {
            thisPol = poly1;
            while (thisPol != null) {
                int degree = entered.term.degree + thisPol.term.degree;
                create = pMult;
                while (create != null) {
                    if (create.term.degree == degree) {
                        create.term.coeff = entered.term.coeff * thisPol.term.coeff;
                    }
                    create = create.next;
                }
                thisPol = thisPol.next;
            }
            entered = entered.next;
        }

        create = pMult;
        while (create != null) {
            if (create.term.degree == high) {
                create.next = null;
                create = create.next;
            }
            else
                create = create.next;
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