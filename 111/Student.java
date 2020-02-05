public class Student {
	
	private String fname;
    private String lname;
    private String gender;
    private int age;
    //constructor invoked when all four info inputed
    public Student(String firstname, String lastname, String sex, int yage){

        this.fname = firstname;
        this.lname = lastname;
        this.gender = sex;
        this.age = yage;
    } 
    //constructor no.2 invoked when only first and last name inputed
    public Student(String firstname, String lastname){

        fname = firstname;
        lname = lastname;
    }
    //constructor no.3 invoked if nothing inputed, default name: John Doe
    public Student(){

        fname = "John";
        lname = "Doe";
    }

    public void setName(String firstname, String lastname){

        fname = firstname;
        lname = lastname;
    }
    
    public void setGender(String g){

        gender = g;
    }

    public void setAge(int year){

        age = year;
    }

    public String getName(){

        return fname + " " + lname;
    }

    public String getGender(){

        return gender;
    }

    public int getAge(){

        return age;
    }

    public String getInfo(){

        return "Name: " + getName() + " Gender: " + getGender() + " Age: " + getAge();
    }

    public String getEmail(){

        int random = (int)(Math.random() * 1001);
        String f = (fname.substring(0, 1)).toLowerCase();
        String l = (lname.substring(0, 1)).toLowerCase();
        return f + l + random + "@rutgers.edu";
    }
}