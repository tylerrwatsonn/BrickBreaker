package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import java.awt.Color;

public class GameSetup {

	public JFrame frame;
	private JTextField gameNameField;
	private JLabel lblAreaWidth;
	private JLabel lblAreaHeight;
	private JLabel lblNumberBlocksPer;
	private JTextField numBlocksField;
	private JLabel lblMinBallXspeed;
	private JLabel lblMinBallYspeed;
	private JTextField minBallSpeedXField;
	private JTextField minBallSpeedYField;
	private JLabel lblBallIncreaseFactor;
	private JTextField ballIncreaseFactorField;
	private JLabel lblMinPaddleWidth;
	private JTextField minPaddleWidthField;
	private JTextField maxPaddleWidthField;
	private JTextField numLevelsField;
	private JLabel lblPixels;
	private JLabel lblPixels_1;
	private JLabel errorMessage;
	private String error;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameSetup window = new GameSetup();
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
	public GameSetup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.PINK);
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Game name:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		gameNameField = new JTextField();
		gameNameField.setColumns(10);

		lblAreaWidth = new JLabel("Area Width:");
		lblAreaWidth.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		lblAreaHeight = new JLabel("Area Height:");
		lblAreaHeight.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		lblNumberBlocksPer = new JLabel("Number of Blocks per Level:");
		lblNumberBlocksPer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		numBlocksField = new JTextField();
		numBlocksField.setColumns(10);

		lblMinBallXspeed = new JLabel("Min Ball x-speed:");
		lblMinBallXspeed.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		lblMinBallYspeed = new JLabel("Min Ball y-speed:");
		lblMinBallYspeed.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		minBallSpeedXField = new JTextField();
		minBallSpeedXField.setColumns(10);

		minBallSpeedYField = new JTextField();
		minBallSpeedYField.setColumns(10);

		lblBallIncreaseFactor = new JLabel("Ball Increase Factor:");
		lblBallIncreaseFactor.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		ballIncreaseFactorField = new JTextField();
		ballIncreaseFactorField.setColumns(10);

		lblMinPaddleWidth = new JLabel("Min Paddle Width:");
		lblMinPaddleWidth.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		minPaddleWidthField = new JTextField();
		minPaddleWidthField.setColumns(10);

		JButton updateButton = new JButton("Update Game Settings");

		JButton backMainMenuButton = new JButton("Back to main menu");

		JLabel lblMaxPaddleWidth = new JLabel("Max Paddle Width:");
		lblMaxPaddleWidth.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		maxPaddleWidthField = new JTextField();
		maxPaddleWidthField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Number of Levels:");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		numLevelsField = new JTextField();
		numLevelsField.setColumns(10);

		JLabel lblNotePleaseSpecify = new JLabel("Note: please specify all game parameters before submitting changes.");
		lblNotePleaseSpecify.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JLabel lblUpdatedefineGame = new JLabel("2. Update/Define Game Settings");
		lblUpdatedefineGame.setFont(new Font("Lucida Grande", Font.BOLD, 18));

		lblPixels = new JLabel("390 pixels");

		lblPixels_1 = new JLabel("390 pixels");



		updateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					updateButtonActionPerformed(evt);
				} catch (InvalidInputException e) {
					error = e.getMessage();
				}
			}
		});

		backMainMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backMainMenuButtonActionPerformed(evt);
			}
		});

		errorMessage = new JLabel("");
		errorMessage.setForeground(Color.RED);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(gameNameField))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblMinPaddleWidth, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(minPaddleWidthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblAreaWidth, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
										.addComponent(lblPixels)))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addGroup(groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(lblMaxPaddleWidth, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblNewLabel_1)
																.addComponent(lblMinBallYspeed, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblAreaHeight, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED))
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(backMainMenuButton)
														.addGap(34)))
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(minBallSpeedYField)
														.addComponent(numLevelsField)
														.addComponent(maxPaddleWidthField)
														.addComponent(updateButton, Alignment.TRAILING))
												.addComponent(lblPixels_1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(33)
										.addComponent(lblNumberBlocksPer)
										.addGap(18)
										.addComponent(numBlocksField, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(28, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblUpdatedefineGame)
						.addContainerGap(396, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(14)
						.addComponent(lblNotePleaseSpecify)
						.addContainerGap(348, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblMinBallXspeed, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(minBallSpeedXField, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblBallIncreaseFactor, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(ballIncreaseFactorField, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)))
						.addGap(429))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
						.addGap(18)
						.addComponent(errorMessage, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(29)
						.addComponent(lblUpdatedefineGame)
						.addGap(18)
						.addComponent(lblNotePleaseSpecify)
						.addGap(31)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(gameNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNumberBlocksPer, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(numBlocksField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(30)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAreaHeight, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAreaWidth, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPixels)
								.addComponent(lblPixels_1))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMinBallXspeed, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(minBallSpeedXField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMinBallYspeed, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(minBallSpeedYField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBallIncreaseFactor, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(ballIncreaseFactorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1)
								.addComponent(numLevelsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(27)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMinPaddleWidth, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(minPaddleWidthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(maxPaddleWidthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMaxPaddleWidth, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGap(69)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(updateButton)
								.addComponent(backMainMenuButton))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(errorMessage)
						.addGap(32))
				);
		frame.getContentPane().setLayout(groupLayout);
	}
	void refreshData() {
		errorMessage.setText(error);

	}

	// Simple method to display the current game parameters for a selected game.
	public void setCurrentParameters() {
		error = "";
		Game curr_game = Block223Application.getCurrentGame();
		gameNameField.setText(curr_game.getName());
		numLevelsField.setText(curr_game.getLevels().size()+"");
		numBlocksField.setText(curr_game.getNrBlocksPerLevel()+"");
		minBallSpeedXField.setText(curr_game.getBall().getMinBallSpeedX()+"");
		minBallSpeedYField.setText(curr_game.getBall().getMinBallSpeedY()+"");
		ballIncreaseFactorField.setText(curr_game.getBall().getBallSpeedIncreaseFactor()+"");
		maxPaddleWidthField.setText(curr_game.getPaddle().getMaxPaddleLength()+"");
		minPaddleWidthField.setText(curr_game.getPaddle().getMinPaddleLength()+"");
	}

	private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		error = "";
		// call the controller
		String game_name = gameNameField.getText();
		if(game_name.length() == 0 || game_name.isEmpty()) {
			error += "Game name must be specified. ";
		}
		Integer num_levels = 0;
		Integer num_blocks = 0;
		Integer min_speed_x = 0;
		Integer min_speed_y = 0;
		Double increase_factor = 0.0;
		Integer max_paddle_width = 0;
		Integer min_paddle_width = 0;
		if(error.length() == 0 || error.equals(null)) {
			try {
				num_levels = Integer.parseInt(numLevelsField.getText());
				num_blocks = Integer.parseInt(numBlocksField.getText());
				min_speed_x = Integer.parseInt(minBallSpeedXField.getText());
				min_speed_y = Integer.parseInt(minBallSpeedYField.getText());
				increase_factor = Double.parseDouble(ballIncreaseFactorField.getText());
				max_paddle_width = Integer.parseInt(maxPaddleWidthField.getText());
				min_paddle_width = Integer.parseInt(minPaddleWidthField.getText());
			} catch (NumberFormatException e) {
				error += "Game settings must be entered and must be numerical values. ";
			}
		}
		if( error == null || error.length() == 0) {
			List<Game> games = Block223Application.getBlock223().getGames();
			for (int i = 0; i < games.size(); i++) {
				if (games.get(i) == Block223Application.getCurrentGame()) {
					break;
				}
				if(games.get(i).getName().equals(game_name)) {
					error = "Name of game already exists ";
				}
			}
		}

		if (error == null || error.length() == 0) {
			if (225 < Integer.parseInt(numBlocksField.getText())){
				error = "Blocks per level must be below 225";
			}
		}

		if( error == null || error.length() == 0) {
			try {	
				Block223Controller.updateGame(game_name,  num_levels, num_blocks, min_speed_x, min_speed_y, increase_factor, max_paddle_width, min_paddle_width);
				error = "Game settings updated";
			} catch (InvalidInputException e) {
				error = e.getMessage();	
			}
			refreshData();
		}

		// update visuals
		refreshData();
	}
	private void backMainMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.adminMainPage.frame.setVisible(true);
		Block223Application.adminMainPage.refreshData();
		Block223Application.gameSetting.frame.setVisible(false);
		Block223Persistence.save(Block223Application.getBlock223());
	}
}
