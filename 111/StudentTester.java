public class StudentTester {
	
	public static void main(String[] args) {

		Student s1 = new Student("Yunhao", "He", "male", 20);
		Student s2 = new Student("Jaime", "Lannister");
		
		System.out.println(s1.getEmail());
		s1.setName("I don't", "know");
		s1.setGender("no one cares");
		s2.setAge(69);
		System.out.println(s2.getInfo());
		System.out.println(s1.getInfo());
    	
    }

}