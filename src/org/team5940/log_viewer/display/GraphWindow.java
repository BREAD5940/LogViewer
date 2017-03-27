package org.team5940.log_viewer.display;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GraphWindow {

	private JDialog dialog;
	private final Hashtable<String, Hashtable<String, ArrayList<DoublePoint>>> toGraph;

	/**
	 * Launch the application.
	 */
	public static void createWindow(Hashtable<String, Hashtable<String, ArrayList<DoublePoint>>> toGraph) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphWindow window = new GraphWindow(toGraph);
					window.dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphWindow(Hashtable<String, Hashtable<String, ArrayList<DoublePoint>>> toGraph) {
		this.toGraph = toGraph;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dialog = new JDialog();
		dialog.setBounds(100, 100, 450, 300);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JTabbedPane moduleTabs = new JTabbedPane(JTabbedPane.TOP);
		dialog.getContentPane().add(moduleTabs, BorderLayout.CENTER);
		
		for(String moduleString : this.toGraph.keySet()) {
			Hashtable<String, ArrayList<DoublePoint>> modules = this.toGraph.get(moduleString);
			JTabbedPane messageTabs = new JTabbedPane(JTabbedPane.TOP);
			moduleTabs.addTab(moduleString, null, messageTabs, null);
			
			for(String messageString : modules.keySet()) {
				ArrayList<DoublePoint> data = modules.get(messageString);
				JPanel graph = new Graph(data);
				messageTabs.addTab(messageString, null, graph, null);
			}
		}
		
//		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
//		moduleTabs.addTab("module", null, tabbedPane_1, null);
//		
//		JPanel panel = new JPanel();
//		tabbedPane_1.addTab("message", null, panel, null);
		
		
	}

}
