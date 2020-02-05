public class BankAccount {
	//instance variables
	private String accountNumber;
	public String name;
	private String address;
	private double balance;
	//constructor 1
	public BankAccount(String acctnum, String name, String addr){
	
		this.accountNumber = acctnum;
	    this.name = name;
	    this.address = addr;
	    this.balance = 0;
	}
	//constructor 2
	public BankAccount(String acctnum, String name, String addr, double initialDeposit){
	
	  	this.accountNumber = acctnum;
	    this.name = name;
	    this.address = addr;
	    this.balance = initialDeposit;
	}
	//get method
	public String getAddress(){ 
	    
	    return this.address;
	}
	
	//set method
	public void setAddress(String newAddress){
	  	
	  	this.address = newAddress;
	}
	
	//method for transferring money
	public static boolean transferMoney(BankAccount from, BankAccount to, double amount){
	
	    if(from.withdrawMoney(amount)){
	        to.depositMoney(amount);
	        return true;
	    }
	    return false;
	}
	
	public boolean depositMoney(double amount){
	
	    if(amount > 0){
	    	this.balance += amount;
	        return true;
	    }
	    return false;
	}
	
	public boolean withdrawMoney(double amount){
	
	    if(amount > 0  &&  this.balance >= amount){
	        this.balance -= amount;
	        return true;
	    }
	    return false;
	}

	public static void main(String[] args) {
	
	    BankAccount Kim = new BankAccount("1001", "Kim", "25 Hamilton Rd");
	    BankAccount Tom = new BankAccount("12009", "Tom", "1 New York Plaza", 1000.78);
	 	Tom.getAddress();
	  	Kim.setAddress("1 New York Plaza");
	  	boolean result = transferMoney(Tom, Kim, 100.0);
	  	boolean result1 = Kim.withdrawMoney(1000);
	  	boolean result2 = Tom.depositMoney(100);
	  	System.out.println(result);	
	  	System.out.println(result1);
	  	System.out.println(result2);		  	
	}
}