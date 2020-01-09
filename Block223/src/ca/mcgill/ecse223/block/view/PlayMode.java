package ca.mcgill.ecse223.block.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.HallOfFameEntry;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.UserRole;

public class PlayMode extends JFrame implements Block223PlayModeInterface {

	private final int FRAME_WIDTH = 1000;
	private final int FRAME_HEIGHT = 1000;
	private final int PLAY_AREA_WIDTH = 390;
	private final int PLAY_AREA_HEIGHT = 390;

	public GameAreaView gameAreaView;
	public JTable hof_table;
	public JLabel game_lives;
	public JLabel game_level;
	public JLabel game_score;
	public JLabel game_multiplier;
	public JLabel game_over;
	public JButton start_btn;

	public JFrame mainFrame = this;

	private static final long serialVersionUID = -613534727974834342L;
	JTextArea gameArea;
	JTextArea pauseArea;
	JPanel gamePanel;
	JPanel hofArea;
	PlayModeListener bp;
	StartPauseListener sp;
	Graphics g;

	public PlayMode() {
		createAndShowGUI();
	}

	/**
	 * Creating GUI
	 */
	private void createAndShowGUI() {
		// Create and set up the window.
		this.setTitle("Block223 Play Mode");
		this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.setBackground(Color.PINK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add components to window pane
		this.addComponentsToPane();

		// Display the window.
		this.pack();
		this.setVisible(true);
	}

	private void addComponentsToPane() {


		//		JPanel main = new JPanel();
		//		BorderLayout main_layout = new BorderLayout();
		//		main.setLayout(main_layout);
		//		main.setBackground(Color.pink);
		//		main.setPreferredSize(new Dimension(1000,1000));


		// Game area key listener
		JPanel gameArea_panel = new JPanel();
		gameArea_panel.setLayout(new BorderLayout());
		gameArea_panel.setPreferredSize(new Dimension(FRAME_WIDTH, 100));

		// Top bar
		JPanel top_bar = new JPanel();
		JPanel top_bar_panel = new JPanel();
		top_bar.setLayout(new BorderLayout());
		top_bar.setPreferredSize(new Dimension(FRAME_WIDTH, 100));
		top_bar.setBackground(Color.pink);
		top_bar_panel.setLayout(new GridLayout(1,2));
		top_bar.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel welcome_text = new JLabel("Block223");
		game_over = new JLabel("");
		game_over.setForeground(Color.red);
		game_over.setFont(new Font("Verdana",1, 25));
		top_bar_panel.add(game_over);
		top_bar_panel.add(welcome_text);
		top_bar_panel.setBackground(Color.pink);
		top_bar.add(top_bar_panel, BorderLayout.CENTER);
		welcome_text.setFont(new Font("Verdana",1,30));
		JButton back_btn = new JButton("Back to main menu");

		back_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backBtnActionPerformed(evt);
			}
		});

		top_bar_panel.add(welcome_text, BorderLayout.PAGE_START);
		top_bar_panel.add(back_btn);

		// HOF

		// HOF (Hall Of Fame) 
		String[][] hof_data = {};
		//String[][] hof_data = {};
		TOHallOfFame to_hof = null;
		/*try {
			to_hof = Block223Controller.getHallOfFame(0, 10);
		} catch (InvalidInputException e) {
			System.out.println("Error in getting HOF: " + e.getMessage());
		}

		if(to_hof != null) {
			List<TOHallOfFameEntry> entries = to_hof.getEntries();
			for(int i=1; i < entries.size(); i++) {
				TOHallOfFameEntry entry = entries.get(i);
				String username = entry.getPlayername();
				String score = entry.getScore()+"";
				hof_data[i][0] = username;
				hof_data[i][1] = score; 
		}
	}*/
		String[] columnNames = {"Player", "Score"};

		/*
		JPanel next_prev_panel = new JPanel();
		next_prev_panel.setLayout(new BorderLayout());
		JButton next_entries = new JButton("Next");
		JButton prev_entries = new JButton("Prev");
		next_prev_panel.add(next_entries, BorderLayout.EAST);
		next_prev_panel.add(prev_entries, BorderLayout.WEST);

		next_entries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextPrevBtnActionPerformed(evt, "next");
			}
		});

		prev_entries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextPrevBtnActionPerformed(evt, "prev");
			}
		});
		 */

		hof_table = new JTable(31, 2); 
		hof_table.setBackground(Color.pink);
		hof_table.setGridColor(Color.white);
		JScrollPane table_pane = new JScrollPane(hof_table); 
		hof_table.setRowHeight(20);
		hof_table.setEnabled(false);
		hof_table.getColumnModel().getColumn(0).setHeaderValue("NAME");
		hof_table.getColumnModel().getColumn(1).setHeaderValue("SCORE");
		hofArea = new JPanel();
		hofArea.setLayout(new BorderLayout());
		hofArea.setBackground(Color.pink);
		hofArea.setPreferredSize(new Dimension(300, 500));
		hofArea.setBorder(new EmptyBorder(10, 20, 10, 20));
		hofArea.setEnabled(false);
		JLabel hof_text = new JLabel("HOF: ");
		hof_text.setFont(new Font("Verdana", 1, 15));
		hofArea.add(hof_text, BorderLayout.PAGE_START);
		hofArea.add(table_pane, BorderLayout.CENTER);
		//hofArea.add(next_prev_panel, BorderLayout.PAGE_END);

		// Playing Area
		JPanel playingArea = new JPanel();
		playingArea.setLayout(new BorderLayout());
		playingArea.setBackground(Color.pink);
		playingArea.setPreferredSize(new Dimension(390, 390));
		playingArea.setBorder(new EmptyBorder(10, 10, 10, 0));


		// West Bar
		int font_size = 15;
		PlayedGame curr_game = Block223Application.getCurrentPlayableGame();
		JPanel west_bar = new JPanel();
		west_bar.setLayout(new BorderLayout());
		start_btn = new JButton("Start");
		JPanel west_bar_panel = new JPanel();
		west_bar_panel.setLayout(new GridLayout(5, 1));
		west_bar_panel.setBorder(new EmptyBorder(10, 20, 10, 20));
		west_bar_panel.setBackground(Color.PINK);
		west_bar.setPreferredSize(new Dimension(300, 500));
		JLabel game_name = new JLabel("Current Game: " + curr_game.getGame().getName());
		game_name.setFont(new Font("Verdana", 1, font_size));
		game_level = new JLabel("Level: " + curr_game.getCurrentLevel());
		game_level.setFont(new Font("Verdana", 1, font_size));
		game_score = new JLabel("Score: " + curr_game.getScore());
		game_score.setFont(new Font("Verdana", 1, font_size));
		game_multiplier = new JLabel("Multiplier: " + (curr_game.getMultiplier() + 1));
		game_multiplier.setFont(new Font("Verdana", 1, font_size));
		game_lives = new JLabel("Lives Remaining: " + curr_game.getLives());
		game_lives.setFont(new Font("Verdana", 1, font_size));
		west_bar.setBackground(Color.pink);
		west_bar.add(start_btn, BorderLayout.PAGE_START);
		west_bar_panel.add(game_name);
		west_bar_panel.add(game_level);
		west_bar_panel.add(game_score);
		west_bar_panel.add(game_multiplier);
		west_bar_panel.add(game_lives);
		west_bar.add(west_bar_panel, BorderLayout.CENTER);
		//west_bar.add(back_btn, BorderLayout.PAGE_END);


		// Bottom bar
		JPanel bottom_bar = new JPanel();
		bottom_bar.setPreferredSize(new Dimension(FRAME_WIDTH, 0));
		bottom_bar.setBackground(Color.pink);
		gameArea = new JTextArea();
		gameArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(gameArea);
		scrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, 100));
		bottom_bar.add(scrollPane);

		// Get Blocks Area
		gameAreaView = new GameAreaView();
		gameAreaView.setMinimumSize(new Dimension(PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT));
		playingArea.add(gameAreaView);

		pauseArea = new JTextArea();
		pauseArea.setEditable(false);
		JScrollPane scrollPane2 = new JScrollPane(pauseArea);
		bottom_bar.add(scrollPane2);


		this.getContentPane().add(top_bar, BorderLayout.PAGE_START);
		this.getContentPane().add(west_bar, BorderLayout.WEST);
		this.getContentPane().add(playingArea, BorderLayout.CENTER);
		this.getContentPane().add(hofArea, BorderLayout.EAST);
		this.getContentPane().add(bottom_bar, BorderLayout.SOUTH);



		start_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				start_btn.setVisible(false);
				// initiating a thread to start listening to keyboard inputs

				bp = new PlayModeListener();
				sp = new StartPauseListener();
				Runnable r1 = new Runnable() {
					@Override
					public void run() {
						// in the actual game, add keyListener to the game window
						gameArea.addKeyListener(bp);
						gameArea.requestFocus();
						back_btn.addKeyListener(bp);
						pauseArea.addKeyListener(sp);
					}
				};
				Thread t1 = new Thread(r1);
				t1.start();
				// to be on the safe side use join to start executing thread t1 before executing
				// the next thread
				try {
					t1.join();
				} catch (InterruptedException e1) {
				}

				// initiating a thread to start the game loop
				Runnable r2 = new Runnable() {
					@Override
					public void run() {
						try {

							Block223Controller.startGame(PlayMode.this);
							//start_btn.setVisible(true);
							//start_btn.setVisible(true);
							pauseArea.requestFocus();
						} catch (InvalidInputException e) {
						}
					}
				};
				Thread t2 = new Thread(r2);
				t2.start();
			}
		});
	}

	private class StartPauseListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_SPACE) {
				Runnable r = new Runnable() {
					@Override
					public void run() {
						try {
							gameArea.requestFocus();
							Block223Controller.startGame(PlayMode.this);
							pauseArea.requestFocus();
						} catch (InvalidInputException x) {
						}

					}

				};
				Thread t = new Thread(r);
				t.start();		
			}	
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void swap(List<HallOfFameEntry> entry, int a, int b) {
		HallOfFameEntry temp = entry.get(a);
		HallOfFameEntry temp2 = entry.get(b);

		entry.set(b, temp);
		entry.set(a, temp2);
	}

	public void sort(List<HallOfFameEntry> entry) {
		boolean done = false;        
		while ( ! done ){
			done = true;     
			for (int i = 0; i < entry.size()-1; i++) {
				if (entry.get(i).getScore() < entry.get(i+1).getScore()) {
					swap(entry, i, i+1);
					done = false;  
				}
			}
		}
	}

	public void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
		//Block223Application.getCurrentPlayableGame().pause();
		//Block223Application.setCurrentPlayableGame(null);
		UserRole user = Block223Application.getCurrentUserRole();
		this.setVisible(false);
		if(Block223Application.getCurrentPlayableGame() != null) {
			Block223Application.getCurrentPlayableGame().pause();
		}
		if(user instanceof Player) {
			Block223Application.gamePage.refreshData();
			Block223Application.gamePage.frame.setVisible(true);
		} else if(user instanceof Admin) {
			Block223Application.adminMainPage.frame.setVisible(true);
		} else {
			throw new RuntimeException("Current user role is null, cannot go back.");
		}

	}

	/*
	public void nextPrevBtnActionPerformed(java.awt.event.ActionEvent evt, String action) {
		System.out.println("Number of HOF Entries : " + Block223Application.getCurrentGame().getHallOfFameEntries().size() + "");
	}
	 */

	@Override
	public String takeInputs() {
		if (bp == null) {
			return "";
		}
		return bp.takeInputs();
	}


	@Override
	public void refresh() {
		if(Block223Application.getCurrentGame()!= null) {
			//List<HallOfFameEntry> entries = Block223Application.getCurrentPlayableGame().getGame().getHallOfFameEntries();
			ArrayList<HallOfFameEntry> entry = new ArrayList<HallOfFameEntry>();
			List<HallOfFameEntry> entries =  Block223Application.getCurrentGame().getHallOfFameEntries();
			for (HallOfFameEntry h : entries) {
				entry.add(h);
			}
			sort(entry);
			for(int i = 0; i < entry.size(); i++) {

				hof_table.setValueAt(entry.get(i).getPlayername(), i, 0);
				hof_table.setValueAt(entry.get(i).getScore(), i, 1);
			}
		}
		if (Block223Application.getCurrentPlayableGame() != null) {
			game_level.setText("Level: " + Block223Application.getCurrentPlayableGame().getCurrentLevel());
			game_score.setText("Score: " +  Block223Application.getCurrentPlayableGame().getScore());
			game_multiplier.setText("Multiplier: " +  (Block223Application.getCurrentPlayableGame().getMultiplier() + 1));
			game_lives.setText("Lives Remaining: " + Block223Application.getCurrentPlayableGame().getLives());

			if(Block223Application.getCurrentPlayableGame().getLives() != 0 && Block223Application.getCurrentPlayableGame().getPlayStatus() == PlayStatus.GameOver) {
				game_over.setText("GAME WON");
				game_over.setForeground(Color.GREEN);
				start_btn.setVisible(false);
				start_btn.setEnabled(false);

			}
			if (Block223Application.getCurrentPlayableGame().getLives() == 0) {

				game_over.setText("GAME OVER");
				start_btn.setVisible(false);
				start_btn.setEnabled(false);
			} else {
				gameAreaView.repaint();
			}

		}
	}

	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		// not used, here so that state machine tests pass
	}
}
