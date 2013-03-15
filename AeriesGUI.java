package aeriesrefresher;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class AeriesGUI extends JFrame implements ActionListener {
	
	JPanel inputPanel = new JPanel();
	JLabel usernameLabel = new JLabel("Aeries Email Address");
	JTextField usernameField = new JTextField("");
	JLabel passwordLabel = new JLabel("Aeries Password");
	JPasswordField passwordField = new JPasswordField("");
	JLabel alertLabel = new JLabel("Alert Email Address");
	JTextField alertField = new JTextField("");
	JLabel refreshLabel = new JLabel("Refresh Rate (seconds)");
	JTextField refreshField = new JTextField("");
	JButton startButton = new JButton("Start");
	JButton stopButton = new JButton("Stop");
	
	JTextArea logArea = new JTextArea();
	JScrollPane scrollBar;

	public AeriesGUI(String windowName){
		super(windowName);
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createPanel();
		logArea.setEditable(false);
		scrollBar = new JScrollPane(logArea);
		
		setLayout(new GridLayout(2,1));
		add(inputPanel);
		add(scrollBar);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(startButton)){
			AeriesRefresher program = new AeriesRefresher(usernameField.getText(),convertPassword(passwordField.getPassword()),
					alertField.getText(),refreshField.getText(),logArea);
			program.start();
			startButton.setEnabled(false);
		}
		if(event.getSource().equals(stopButton)){
			System.exit(0);
		}
	}
	
	public void createPanel(){
		inputPanel.setLayout(new GridLayout(5,2));
		inputPanel.add(usernameLabel);
		inputPanel.add(usernameField);
		inputPanel.add(passwordLabel);
		inputPanel.add(passwordField);
		inputPanel.add(alertLabel);
		inputPanel.add(alertField);
		inputPanel.add(refreshLabel);
		inputPanel.add(refreshField);
		inputPanel.add(startButton);
		inputPanel.add(stopButton);
		
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		
		
	}
	
	public String convertPassword(char[] letters){
		String password = "";
		for(int i = 0; i < letters.length; i++){
			password += (String.valueOf(letters[i]));
		}
		return password;
	}
	

}
