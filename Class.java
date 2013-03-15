package aeriesrefresher;


//basic container class to conveniently hold all attributes of a class
public class Class {
	
	private String name;
	private String lastUpdated;
	private String teacher;
	private String grade;
	
	public Class(String n, String l, String t, String g){
		name = n;
		lastUpdated = l;
		teacher = t;
		grade = g;
	}
	
	public String getName(){
		return name;
	}
	
	public String getLastUpdated(){
		return lastUpdated;
	}
	
	public String getTeacher(){
		return teacher;
	}
	
	public String getGrade(){
		return grade;
	}
	
	public String toString(){
		return name + " (" + teacher + "): " + grade + ", " + lastUpdated + " ||"; 
	}
	
}
