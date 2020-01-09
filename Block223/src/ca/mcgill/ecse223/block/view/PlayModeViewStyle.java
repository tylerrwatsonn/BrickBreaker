package ca.mcgill.ecse223.block.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;
import ca.mcgill.ecse223.block.model.PlayedGame;

public class PlayModeViewStyle extends JFrame {

	private static final long serialVersionUID = -613534727974834342L;
	JTextArea gameArea;
	JPanel gamePanel;
	JPanel hofArea;
	PlayModeListener bp;
	
	public static void run(String[] args) {
		PlayModeViewStyle page = new PlayModeViewStyle();
	}

	public PlayModeViewStyle() {
		createAndShowGUI();
	}

	/**
	 * Creating GUI
	 */
	private void createAndShowGUI() {
		// Create and set up the window.
		this.setTitle("Block223 Play Mode");
		this.setSize(600, 600);
		//this.getContentPane().setBackground(Color.PINK);
		
		this.setBounds(100, 100, 650, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new JPanel();
		gamePanel.setLayout(new FlowLayout());
		
		this.setLayout(new GridLayout(2, 1));
		
		// Add components to window pane
		this.addComponentsToPane();

		// Display the window.
		this.pack();
		this.setVisible(true);
	}

	private void addComponentsToPane() {
		
		// Top bar
		JPanel top_bar = new JPanel();
		top_bar.setLayout(new FlowLayout());
		top_bar.setBackground(Color.green);
		JLabel welcome_text = new JLabel("Block223");
		welcome_text.setFont(new Font("Verdana",1,30));
		JButton start_btn = new JButton("Start");
		JButton pause_btn = new JButton("Pause");
		JButton back_btn = new JButton("Back to main menu");
		JLabel current_game_name = new JLabel("Current game: ");
		JLabel current_level = new JLabel("Level: ");
		
	    
		GridLayout top_layout = new GridLayout(2, 4); 
		top_layout.setHgap(0);
		top_layout.setVgap(0);
		top_bar.setLayout(top_layout);
		top_bar.add(welcome_text);
		top_bar.add(start_btn);
		top_bar.add(pause_btn);
		
		top_bar.add(back_btn);
		top_bar.add(current_game_name);
		top_bar.add(current_level);
		top_bar.add(new JLabel("TEST"));

		
		
		// Bottom bar
		JPanel bottom_bar = new JPanel();
		bottom_bar.setLayout(new FlowLayout());
		bottom_bar.setBackground(Color.red);
		
		JButton btn1 = new JButton("Button 1");
		JButton btn2 = new JButton("Button 2");
		//GRect paddle = new GRect(200, 20);
		
		hofArea = new JPanel();
		hofArea.setLayout(new FlowLayout());
		hofArea.setBackground(Color.yellow);
		
		JPanel playingArea = new JPanel();
		playingArea.setLayout(new FlowLayout());
		playingArea.setBackground(Color.blue);
		
		JPanel bottomArea = new JPanel();
		bottomArea.setLayout(new FlowLayout());
		bottomArea.setBackground(Color.BLACK);
			
		GridLayout center_layout = new GridLayout(1, 1); 
		gamePanel.setLayout(center_layout);
		gamePanel.add(playingArea);
		gamePanel.add(hofArea);
		
		
		this.add(top_bar);
		this.add(gamePanel);
		
		
		
	}

}
