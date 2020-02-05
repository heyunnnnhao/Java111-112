//This is a class with some random array stuff
public class ArrayStuff {

    public static void main(String args[] ){
        
        //used to test compress
        System.out.println("r: " + compress("r"));
    	System.out.println("zzzzzzzz: " + compress("zzzzzzzz"));
    	System.out.println("ab: " + compress("ab"));
    	System.out.println(compress("qwwwwwwwwweeeeerrtyyyyyqqqqwEErTTT"));

    	//used to test sum array
    	int[] inputArray = {3, 4, 9, 22, 17};
      	int sum = arraySum(inputArray);
      	System.out.println(sum);

      	//used to test occurrence
      	double searchFor = 3.3;
        double[][] inputarray = {{1.2, 5.5, 4.4}, {3.3, 3.4, 7,7}, {8.2, 3.3, 4.1},{3.4, 2.2, 3.3}};
        int result = occurrences(searchFor, inputarray);
        System.out.println(result);
    }
    
    //compress wwwwweeeeerrr to 5w5e3r
    public static String compress (String str){
		
		int count = 0;
		String temp = "";

      	for(int i = 0; i < str.length(); i++){
			if(i == str.length()-1){
				if(count == 0){
					temp = temp + str.charAt(i);
				break;
				} else {
					temp = temp + (count+1) + str.charAt(i);
					break;
				}
			}
			if(str.charAt(i) == str.charAt(i+1)){
				count++;	
			}
			else{
				if(count == 0){
					temp = temp + str.charAt(i);
				}
				else{
					temp = temp + (count+1) + str.charAt(i);
					count = 0;
				}
			}
		}

		return temp.trim();
    }

    //calculate the sum of an array of integers
    public static int arraySum(int[] sumArray){
    	
    	int result = 0;
    	for (int i = 0; i < sumArray.length; i ++){
    		result += sumArray[i];
    	}
    	
    	return result;
  	}

  	//find the occurrence of a certain number in an array
  	public static int occurrences(double target, double[][] array){
        
        int counter = 0;
        for (int row = 0; row < array.length; row++){
            for (int col = 0; col < array[0].length; col++){
                if (array[row][col] == target) counter ++;          
            }
        }

        return counter;
    }
}