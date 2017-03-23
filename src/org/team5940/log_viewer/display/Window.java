package org.team5940.log_viewer.display;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.team5940.log_viewer.logs.LogLine;
import org.team5940.log_viewer.logs.LogReader;

public class Window {

	private JFrame frame;
	private JTabbedPane options;
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
		
		//CONTROLS
		JPanel controls = new JPanel();
		frame.getContentPane().add(controls, BorderLayout.NORTH);
		controls.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton loadButton = new JButton("Load Logs");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				readLogs();
			}
		});
		controls.add(loadButton);
		
		JButton allMessagesButton = new JButton("All Messages");
		allMessagesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Hashtable<String, JCheckBox> module : moduleMessageChecks.values())
					for(JCheckBox message : module.values())
						message.setSelected(true);
				updateLogText();
			}
		});
		controls.add(allMessagesButton);
		
		JButton noMessagesButton = new JButton("No Messages");
		noMessagesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Hashtable<String, JCheckBox> module : moduleMessageChecks.values())
					for(JCheckBox message : module.values())
						message.setSelected(false);
				updateLogText();
			}
		});
		controls.add(noMessagesButton);
		
		//LOGS
		JScrollPane centerPane = new JScrollPane();
		frame.getContentPane().add(centerPane, BorderLayout.CENTER);
		
		this.logText = new JTextArea();
		this.logText.setText("this would be line one - bvgcfdtyuhijkmnb vcfdtyuijkmnbvcfdrtyuijkmn bvcfdtr7yuijknmb vgcfdtr67y8uijnb vgcftryuhjbnvgfyguh\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nthis would be a later line!");
		centerPane.setViewportView(this.logText);
		
		readLogs();
	}
	
	private void readLogs() {
		//Update logs
		//TODO
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new File("ftp://roborio-5940-frc.local/media/sda1/"));
	    chooser.setDialogTitle("Select Log Files Directory");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog((new JFrame()).getContentPane()) == JFileChooser.APPROVE_OPTION) { 
	    	//TODO
	    	System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
	    	this.logLines = LogReader.returnLogLines(chooser.getSelectedFile());
	    	
			//Update UI for contents of new logs
			updateOptions();
	    } else {//TODO add check for existing logs and settings
	    	//Example logs
			this.logLines = new ArrayList<>();
			ArrayList<String> exampleLog;
			
			exampleLog = new ArrayList<>();
			exampleLog.add("121230820");
			exampleLog.add("thread");
			exampleLog.add("module");
			exampleLog.add("Initialized");
			exampleLog.add("Module");
			this.logLines.add(new LogLine(exampleLog));
			
			exampleLog = new ArrayList<>();
			exampleLog.add("121230821");
			exampleLog.add("thread");
			exampleLog.add("module");
			exampleLog.add("Initialized");
			exampleLog.add("AbstractModule");
			this.logLines.add(new LogLine(exampleLog));
			
			exampleLog = new ArrayList<>();
			exampleLog.add("121230822");
			exampleLog.add("thread");
			exampleLog.add("piston");
			exampleLog.add("Initialized");
			exampleLog.add("Module");
			this.logLines.add(new LogLine(exampleLog));
			
			exampleLog = new ArrayList<>();
			exampleLog.add("121230823");
			exampleLog.add("thread");
			exampleLog.add("piston");
			exampleLog.add("Did a thing!");
			this.logLines.add(new LogLine(exampleLog));
			
			exampleLog = new ArrayList<>();
			exampleLog.add("121230824");
			exampleLog.add("thread2");
			exampleLog.add("piston");
			exampleLog.add("Set Piston State");
			exampleLog.add("false");
			this.logLines.add(new LogLine(exampleLog));
			
			exampleLog = new ArrayList<>();
			exampleLog.add("121230825");
			exampleLog.add("thread2");
			exampleLog.add("piston");
			exampleLog.add("Set Piston State");
			exampleLog.add("true");
			this.logLines.add(new LogLine(exampleLog));
			
			exampleLog = new ArrayList<>();
			exampleLog.add("121230826");
			exampleLog.add("thread2");
			exampleLog.add("piston");
			exampleLog.add("Set Piston State" + (new Random()).nextInt());
			exampleLog.add((new Random()).nextInt() + " - int");
			this.logLines.add(new LogLine(exampleLog));

			//Update UI for contents of new logs
			updateOptions();
	    }
	
	}
	
	private void updateOptions() {
		//REMOVE OLD COMPONENTS
		if(this.options != null) this.options.removeAll();
		else options = new JTabbedPane(JTabbedPane.TOP);
		
		//OPTIONS
		updateThreads();//Update the list of threads.
		updateModuleMessages();//Update the hashtable of modules and their messages.
		//Options pane
		frame.getContentPane().add(options, BorderLayout.SOUTH);
		//Threads tab
		this.threadChecks = this.createOptionCheckboxes(this.threads);
		options.addTab("Threads", null, this.createOptionPanel("Threads", this.threadChecks), null);
		//Module tabs
		this.moduleMessageChecks = new Hashtable<>();
		for(String module : this.moduleMessages.keySet()) {
			Hashtable<String, JCheckBox> messageChecks = this.createOptionCheckboxes(this.moduleMessages.get(module));
			this.moduleMessageChecks.put(module, messageChecks);
			options.addTab(module, this.createOptionPanel(module, messageChecks));
		}

		updateLogText();//Update the logLines.
		
	}
	
	//Updates logText based on threadChecks and moduleMessageChecks
	private void updateLogText() {
		this.logText.setText("");
		for(LogLine line : this.logLines) {
			String thread = line.getStamp(2);
			String module = line.getStamp(3);
			String message = line.getStamp(4);
			
			//TODO check if thread and message are selected
			if(this.threadChecks.get(thread).isSelected() && this.moduleMessageChecks.get(module).get(message).isSelected())
				this.logText.append(line.getLine() + "\n");
		}
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
		for(String option : options) {
			JCheckBox check = new JCheckBox(option);
			check.setSelected(true);
			check.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updateLogText();
				}
			});
			out.put(option, check);
		}
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
				updateLogText();
			}
		});
		panel.add(enableAll);
		
		JButton disableAll = new JButton("None");
		disableAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(JCheckBox cBox : options.values())
					cBox.setSelected(false);
				updateLogText();
			}
		});
		panel.add(disableAll);
		
		for(JCheckBox cBox : options.values())
			panel.add(cBox);//TODO scrollpane not working, test in it's own class
		
		return panel;
	}
}
