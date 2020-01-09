package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.UserRole;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class PlayGame{

	public JFrame frame;
	private String error;
	private JLabel errorMessage;
	private JComboBox<String> gameNameComboBox;
	private JComboBox<String> cBoxContinueGame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayGame window = new PlayGame();
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
	public PlayGame() {
		initialize();
		//refreshData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.PINK);
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblSelectAGame = new JLabel("Select a New Game:");

		gameNameComboBox = new JComboBox<String>();

		JButton logoutBtn = new JButton("Logout");

		JButton playGameBtn = new JButton("Play Game");


		logoutBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logoutBtnActionPerformed(evt);
			}
		});

		playGameBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try{
					playModeActionPerformed(evt);
				}catch (InvalidInputException e) {
					error = "Must select a game";
					refreshData();
				}
			}
		});

		JLabel lblWelcomeChooseA = new JLabel("Welcome! Choose a game from the list and start playing!");
		lblWelcomeChooseA.setFont(new Font("Lucida Grande", Font.BOLD, 18));

		errorMessage = new JLabel("");
		errorMessage.setBackground(Color.RED);
		errorMessage.setForeground(Color.RED);

		JLabel continueGame = new JLabel("Continue a Game:");

		cBoxContinueGame = new JComboBox<String>();

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(184)
										.addComponent(logoutBtn)
										.addGap(128)
										.addComponent(playGameBtn))
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(165)
														.addComponent(continueGame, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(155)
														.addComponent(lblSelectAGame)))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(gameNameComboBox, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
												.addComponent(cBoxContinueGame, 0, 213, Short.MAX_VALUE))))
						.addContainerGap(192, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap(95, Short.MAX_VALUE)
						.addComponent(lblWelcomeChooseA)
						.addGap(78))
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(318)
						.addComponent(errorMessage, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(139, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(24)
						.addComponent(lblWelcomeChooseA)
						.addGap(104)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSelectAGame)
								.addComponent(gameNameComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(continueGame)
								.addComponent(cBoxContinueGame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(44)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(logoutBtn)
								.addComponent(playGameBtn))
						.addGap(151)
						.addComponent(errorMessage)
						.addContainerGap(22, Short.MAX_VALUE))
				);
		frame.getContentPane().setLayout(groupLayout);
	}
	public void refreshData() {
		errorMessage.setText(error);
		//int index = 0;
		Player p = (Player) Block223Application.getCurrentUserRole();
		gameNameComboBox.removeAllItems();
		for (Game game : Block223Application.getBlock223().getGames()) {
			if(game.isPublished()) { // make sure game has been published
				gameNameComboBox.addItem(game.getName());
				//index++;
			}
			for (PlayedGame pGame : p.getPlayedGames()) {
				gameNameComboBox.removeItem(pGame.getGame().getName());
			}
		};

		cBoxContinueGame.removeAllItems();
		for (PlayedGame pGame : p.getPlayedGames()) {
			if(!(pGame.getPlayStatus() == PlayStatus.GameOver)) {
				cBoxContinueGame.addItem(pGame.getGame().getName());
			}
		}
		gameNameComboBox.setSelectedIndex(-1);
	}

	public void playModeActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		if (gameNameComboBox.getSelectedItem() == null && cBoxContinueGame.getSelectedItem() == null) {
			throw new InvalidInputException("Must select a game");
		}


		//Block223Application.setCurrentGame(game);
		// Set Current Playable Game
		Player p = (Player)Block223Application.getCurrentUserRole();
		List<PlayedGame> listPG = p.getPlayedGames();
		for (PlayedGame pG : listPG) {
			if (!(gameNameComboBox.getSelectedItem() == null)){
				Game game = Block223.findGame(gameNameComboBox.getSelectedItem().toString());
				if (pG.getGame().getName().equals(game.getName())) {
					Block223Application.setCurrentPlayableGame(pG);
					//System.out.println(" ");
				}
			}else {
				Game game = Block223.findGame(cBoxContinueGame.getSelectedItem().toString());
				if (pG.getGame().getName().equals(game.getName())) {
					Block223Application.setCurrentPlayableGame(pG);
					//System.out.println(" ");
				}
			}

		}
		if (Block223Application.getCurrentPlayableGame() == null) {
			try {
				Block223Controller.selectPlayableGame(gameNameComboBox.getSelectedItem().toString(), -1);
			} catch(InvalidInputException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
		PlayMode playMode = new PlayMode();
		playMode.setVisible(true);
		this.frame.setVisible(false);
		errorMessage.setText("");
		//System.out.println(p.getPlayedGames().size());
	}

	public void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Controller.logout();
		Block223Application.loginPage.frame.setVisible(true);
		Block223Application.gamePage.frame.setVisible(false);
		errorMessage.setText("");
	}
}