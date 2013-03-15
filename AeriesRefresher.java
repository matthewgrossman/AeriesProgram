package aeriesrefresher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextArea;

public class AeriesRefresher extends Thread {
	HTMLParser htmlProcessor;
	Email emailer;
	Class pastClasses[];
	Class recentClasses[];
	ArrayList <Integer> flags;
	int refreshRate;
	JTextArea logArea;
	
	public AeriesRefresher(String u, String p, String a, String r, JTextArea l){
		htmlProcessor = new HTMLParser(u,p);
		emailer = new Email(a);
		refreshRate = Integer.parseInt(r);
		logArea = l;
		flags = new ArrayList <Integer>();
	}
	
	public void run(){
		try {
			runProgram();
		}catch (Exception e) {
			String errorMessage = "There was an error with signing in. \n" +
					"Possible issues:\n" +
					"-your username and password are incorrect\n" +
					"-your internet isn't properly working\n" +
					"-Aeries wants you to reset your password\n" +
					"After checking these steps, press STOP and rerun the program";
			logArea.append(errorMessage);
			try {
				emailer.sendMessage(errorMessage);
			} catch (Exception e1) {
				
			}
		}
	}
	
	
	public void runProgram() throws Exception{
		intializeValues();
		while(true){
			String message;
			if(gradesWereUpdated()){
				pastClasses = recentClasses;
				message = generateMessage();
				emailer.sendMessage(message);
				flags.clear();
			}else{
				message = "No changes were found @ " + getTime() + "\n";
			}
			logArea.append(message);
			sleep(refreshRate * 1000);
		}
	}
	
	//sets the initial values for all the classes
	public void intializeValues(){
		pastClasses = htmlProcessor.getClasses();
	}
	
	
	//checks to see if graders were updated by essentially refreshing the page and comparing the grade and updated time 
	//to the old data
	public boolean gradesWereUpdated(){
		boolean answer = false;
		Class[] recentClasses = htmlProcessor.getClasses();
		for(int i = 0; i < pastClasses.length; i++){
			boolean dateHasChanged = !pastClasses[i].getLastUpdated().equals(recentClasses[i].getLastUpdated());
			boolean gradeHasChanged = !pastClasses[i].getGrade().equals(recentClasses[i].getGrade());
			if(dateHasChanged || gradeHasChanged){
				answer = true;
				flags.add(i);
			}
		}
		return answer;
	}
	
	//generates message based upon what classes have changed (possibly none)
	public String generateMessage(){
		String message = "";
		for(int i = 0; i < flags.size(); i++){
			Class changedClass = pastClasses[flags.get(i)];
			message += "Your new grade in " + changedClass.getName() + " is " + changedClass.getGrade() + "\n";
		}
		if(message.equals("")) 
			message = "No changes were found @ " + getTime() + "\n";
		return message;
	}
	
	
	//get current time of update, copied essentially off the internet
	public String getTime(){
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
		return formatter.format(currentDate.getTime());
	}
}
