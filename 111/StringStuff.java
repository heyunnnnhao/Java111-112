//This is a class with some random string stuff
public class StringStuff {
	
	public static void main(String[] args) {
        
        System.out.println("Enter a string to do something with it");
        String a = IO.readString();
        
        System.out.println("Here are your options:");
        System.out.println("Press 1 to: test if it is palindrome");
        System.out.println("Press 2 to: test if it is palindrome(using recursive)");
        System.out.println("Press 3 to: reverse it");
        System.out.println("Press 4 to: reverse it(using recursive)");
        System.out.println("Press 0 to end this boring shit");
       
        while (true){            
            int command = IO.readInt();
            if (command == 1) System.out.println(palindrome(a));
            if (command == 2) System.out.println(recursivePalindrome(a));
            if (command == 3) System.out.println(reverse(a));
            if (command == 4) System.out.println(recursiveReverse(a));
            if (command == 0) break;
            System.out.println("Again? Enter a command again");
        }
    }
   
    //check if this string is the same if reversed
    public static boolean palindrome(String str){
        
        for(int i = 0; i < str.length(); i ++){
            if(str.charAt(i) != str.charAt(str.length() - i - 1)) return false;
        } 
        return true;         
    }
    
    //check if this string is the same if reversed using recursion
    public static boolean recursivePalindrome(String str){
    
        if(str.length() <= 1) return true;
        if(str.charAt(0) != str.charAt(str.length() - 1)) return false;
        String innerSubstr = str.substring(1, str.length() - 1);
        
        return recursivePalindrome(innerSubstr); 
    }

    //reverse a string
    public static String reverse(String str){

        String reversed = "";
        for (int i = str.length() -1; i >= 0 ; i--){
            reversed = reversed + str.charAt(i);
        }
        return reversed;    
    }

    //reverse a string using recursion
    public static String recursiveReverse(String str){
    
        if(str.length() <= 0) return str;
        String rightSide = str.substring(1, str.length());
        
        return reverse(rightSide) + str.charAt(0);
    }   
}