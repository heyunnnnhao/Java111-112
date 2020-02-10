
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ALStack<T> {
	private ArrayList<T> items;

	public ALStack() {
		items = new ArrayList<>();
	}

	public void push(T item) {
		items.add(item);
	}

	public T pop() 
	throws NoSuchElementException {
		if (items.size() == 0) {
			throw new NoSuchElementException();
		}
		return items.remove(items.size()-1);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public int size() {
		return items.size();
	}

	public void clear() {
		items.clear();
	}

    /* testing ALStack */
	 public static void main(String[] args) {
        ALStack<String> stack = new ALStack<String>();
        String exp = args[0];
        System.out.println(exp);
        for (int i=0; i<exp.length();i++)
        	stack.push(""+exp.charAt(i));
        while (!stack.isEmpty())
        	System.out.println(stack.pop());
        /*
               if token == '('  push
               if token == ')'  pop
               if stack is empty at the end you are fine
        */
        /*
        System.out.println(stack.size());
        stack.push("me");
        stack.push("you");
        stack.push("him");
        while (!stack.isEmpty()){
        	System.out.println(stack.pop());
        }
        */


        
    }

}