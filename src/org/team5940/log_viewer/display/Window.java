package org.team5940.log_viewer.display;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.team5940.log_viewer.logs.LogLine;

public class Window {

	private JFrame frame;
	private JTextArea logText;
	private ArrayList<LogLine> logLines;
	private ArrayList<String> threads;
	private Hashtable<String, ArrayList<String>> moduleMessages;
	private Hashtable<String, JCheckBox> threadChecks;
	private Hashtable<String, Hashtable<String, JCheckBox>> moduleMessageChecks;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		readLogs();
	}
	
	private void readLogs() {
		//Update logs
		//TODO
		this.logLines = new ArrayList<>();
		ArrayList<String> exampleLog = new ArrayList<>();
		exampleLog.add("121230821");
		exampleLog.add("thread");
		exampleLog.add("module");
		exampleLog.add("\"initialized\"");
		exampleLog.add("Some data!");
		this.logLines.add(new LogLine(exampleLog));
		
		exampleLog = new ArrayList<>();
		exampleLog.add("91243966");
		exampleLog.add("thread2");
		exampleLog.add("module2");
		exampleLog.add("\"initialized\"");
		exampleLog.add("Some data!");
		this.logLines.add(new LogLine(exampleLog));
		
		exampleLog = new ArrayList<>();
		exampleLog.add("91243966");
		exampleLog.add("thread2");
		exampleLog.add("module2");
		exampleLog.add("\"initialized2\"");
		exampleLog.add("Some data!");
		this.logLines.add(new LogLine(exampleLog));
		
		exampleLog = new ArrayList<>();
		exampleLog.add("91243966");
		exampleLog.add("thread2");
		exampleLog.add("module2");
		exampleLog.add("Set Motor Speed");
		exampleLog.add("Some data!");
		this.logLines.add(new LogLine(exampleLog));
		
		exampleLog = new ArrayList<>();
		exampleLog.add("91243966");
		exampleLog.add("thread2");
		exampleLog.add("module2");
		exampleLog.add("Set Piston State");
		exampleLog.add("Some data!");
		this.logLines.add(new LogLine(exampleLog));
		
		exampleLog = new ArrayList<>();
		exampleLog.add("91243966");
		exampleLog.add("thread2");
		exampleLog.add("module2");
		exampleLog.add("\"initialized5\"");
		exampleLog.add("Some data!");
		this.logLines.add(new LogLine(exampleLog));
		
		//Update UI for contents of new logs
		updateUI();
	}
	
	private void updateUI() {
		//REMOVE OLD COMPONENTS
		//frame.removeAll();
		
		//CONTROLS
		JPanel controls = new JPanel();
		frame.getContentPane().add(controls, BorderLayout.NORTH);
		controls.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton loadButton = new JButton("Load Logs");
		controls.add(loadButton);
		
		JButton refreshButton = new JButton("Refresh Display");
		controls.add(refreshButton);
		
		//LOGS
		JScrollPane centerPane = new JScrollPane();
		frame.getContentPane().add(centerPane, BorderLayout.CENTER);
		
		this.logText = new JTextArea();
		this.logText.setText("this would be line one - bvgcfdtyuhijkmnb vcfdtyuijkmnbvcfdrtyuijkmn bvcfdtr7yuijknmb vgcfdtr67y8uijnb vgcftryuhjbnvgfyguh\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nthis would be a later line!");
		centerPane.setViewportView(this.logText);
		
		//OPTIONS
		updateThreads();//Update the threads.
		updateModuleMessages();
		//Options pane
		JTabbedPane options = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(options, BorderLayout.SOUTH);
		//Threads tab
		this.threadChecks = this.createOptionCheckboxes(this.threads);
		options.addTab("Threads", null, this.createOptionPanel("Threads", this.threadChecks), null);
		//Module tabs
		for(String module : this.moduleMessages.keySet()) {
			Hashtable<String, JCheckBox> messageChecks = this.createOptionCheckboxes(this.moduleMessages.get(module));
			this.moduleMessageChecks.put(module, messageChecks);
			options.addTab(module, this.createOptionPanel(module, messageChecks));
		}

		updateLogText();//Update the logLines.
		
	}
	
	//Updates logText based on threadChecks and moduleMessageChecks
	private void updateLogText() {
		//TODO
//		this.logText.setText("");
//		for(LogLine line : this.logLines) 
//			//if					 Thread is selected			   and Module message is selected
//			if(this.threadChecks.get(line.getStamp(1)).isSelected() && this.moduleMessageChecks.get(line.getStamp(2)).get(line.getStamp(3)).isSelected())
//				this.logText.append(line.getLine() + "\n");
	}
	
	//Updates threads based on the contents of logLines.
	private void updateThreads() {
		this.threads = new ArrayList<>();
		for(LogLine line : this.logLines) {
			String thread = line.getStamp(2);
			if(!this.threads.contains(thread)) this.threads.add(thread);
		}
	}
	
	//Updates moduleMessages based on the contents of logLines.
	private void updateModuleMessages() {
		this.moduleMessages = new Hashtable<>();
		for(LogLine line : this.logLines) {
			String module = line.getStamp(3);
			String message = line.getStamp(4);
			if(!this.moduleMessages.containsKey(module)) this.moduleMessages.put(module, new ArrayList<>());
			if(!this.moduleMessages.get(module).contains(message)) this.moduleMessages.get(module).add(message);
		}
	}
	
	//Creates the checkboxes for options
	private Hashtable<String, JCheckBox> createOptionCheckboxes(ArrayList<String> options) {
		Hashtable<String, JCheckBox> out = new Hashtable<>();
		for(String option : options)
			out.put(option, new JCheckBox(option));
		return out;
	}
	
	//Creates the tab for a set of options
	private JPanel createOptionPanel(String name, Hashtable<String, JCheckBox> options) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton enableAll = new JButton("All");
		enableAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(JCheckBox cBox : options.values())
					cBox.setSelected(true);
			}
		});
		panel.add(enableAll);
		
		JButton disableAll = new JButton("None");
		disableAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(JCheckBox cBox : options.values())
					cBox.setSelected(false);
			}
		});
		panel.add(disableAll);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		for(JCheckBox cBox : options.values())
			panel.add(cBox);//TODO scrollpane not working, test in it's own class
		
		return panel;
	}
}
