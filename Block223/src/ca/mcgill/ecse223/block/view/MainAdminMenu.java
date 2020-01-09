package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MainAdminMenu {


	public JFrame frame;
	private JTextField gameTextField;
	private JLabel errorMessage;
	private String error = "";

	//Select Game
	private HashMap<Integer, String> availableGames;
	private JComboBox<String> gameList;
	Block223 block223 = new Block223();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainAdminMenu window = new MainAdminMenu();
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
	public MainAdminMenu() {

		initialize();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.PINK);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel gameNameTextField = new JLabel("Game Name:");

		gameTextField = new JTextField();
		gameTextField.setColumns(10);

		JButton btnCreate = new JButton("Create");

		JButton btnTestGame = new JButton("Test Game");

		JButton btnPublishGame = new JButton("Publish Game");

		JButton btnLogout = new JButton("Logout");


		JSeparator separator = new JSeparator();

		JLabel lblSelectAGame = new JLabel("Select a Game:");

		gameList = new JComboBox<String>();

		JButton btnDelete = new JButton("Delete");

		JButton btnCreateLevel = new JButton("Design Levels");

		JButton btnCreateBlock = new JButton("Create Blocks");

		JButton btnGameSettings = new JButton("Define/Update Game Settings");
		btnGameSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		btnCreate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCreateActionPerformed(evt);
			}
		});

		btnLogout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLogoutActionPerformed(evt);
			}
		});

		btnGameSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					btnGameSettingsActionPerformed(evt);
				} catch (InvalidInputException e) {
					errorMessage.setText("Must select a game to edit settings");
				}
			}
		});


		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDeleteActionPerformed(evt);
			}
		});

		btnCreateLevel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnCreateLevelActionPerformed(evt);
				} catch (InvalidInputException e) {
					errorMessage.setText("Must select a game to create levels");
				}
			}
		});

		btnCreateBlock.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnCreateBlockActionPerformed(evt);
				} catch (InvalidInputException e) {
					errorMessage.setText("Must select a game to create blocks");
				}
			}
		});



		JLabel lblNewLabel = new JLabel("Once you have created a game, select it from the list below and perform any specified action on it.");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JLabel lblForASelected = new JLabel("For a selected game, you can choose to delete it, design its levels or create a set of blocks to be used in the game.");
		lblForASelected.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JLabel lblCreateA = new JLabel("1. Create a new game or select and modify an exisiting game");
		lblCreateA.setFont(new Font("Lucida Grande", Font.BOLD, 18));

		JLabel lblFirstEnterThe = new JLabel("First, enter the name of the game.");

		errorMessage = new JLabel("");
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setForeground(Color.RED);


		btnPublishGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					btnPublishGameActionPerformed(evt);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					refreshData();
				}
			}

			private void btnPublishGameActionPerformed(ActionEvent evt) throws InvalidInputException {
				String gameName = (String) gameList.getSelectedItem();
				if (gameName != null && gameName != "") {
					Game game = Block223.findGame(gameName);
					Block223Application.setCurrentGame(game);
					try {
						Block223Controller.publishGame();
						Block223Persistence.save(Block223Application.getBlock223());
					} catch (InvalidInputException e) {
						throw new InvalidInputException(e.getMessage());

					}
					error = "Game published";	
				}else {
					error = "Must select a game to publish";
				}
				refreshData();
			}
		});



		btnTestGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnTestGameActionPerformed(evt);
			}

			private void btnTestGameActionPerformed(ActionEvent evt) {
				String game_select = (String) gameList.getSelectedItem();
				if (game_select != null && game_select != "") {
					Block223 block223 = Block223Application.getBlock223();
					UserRole user = Block223Application.getCurrentUserRole();
					String username = User.findUsername(user);
					Game game = Block223.findGame(game_select);
					int num_blocks = game.getBlocks().size();
					if(num_blocks < 1) {
						error = "At least one block must be defined to test a game.";
						try {
							throw new InvalidInputException(error);
						} catch (InvalidInputException e) {
							error = e.getMessage();
						}

					} else {
						Block223Application.setCurrentGame(game);
						PlayedGame pgame_temp = new PlayedGame(username, game, block223);
						Block223Application.setCurrentPlayableGame(pgame_temp);
						Block223Application.adminMainPage.frame.setVisible(false);
						PlayMode testGame = new PlayMode();
						try {
							Block223Controller.testGame(testGame);
						} catch (InvalidInputException e) {
							error = e.getMessage();
						}
					}
				} else {
					error = "Must select a game to test it.";
				}
				refreshData();
			}
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCreateA)
						.addGap(18)
						.addComponent(btnLogout)
						.addContainerGap(17, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(118)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblFirstEnterThe)
										.addContainerGap())
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(gameNameTextField)
												.addGap(18)
												.addComponent(gameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(43)
												.addComponent(btnCreate)
												.addGap(243))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(lblNewLabel)
														.addContainerGap())
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup()
																.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
																		.addGroup(groupLayout.createSequentialGroup()
																				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																						.addGroup(groupLayout.createSequentialGroup()
																								.addGap(416)
																								.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
																						.addGroup(groupLayout.createSequentialGroup()
																								.addComponent(lblSelectAGame)
																								.addGap(36)
																								.addComponent(gameList, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
																								.addComponent(btnGameSettings)))
																				.addGap(94))
																		.addComponent(lblForASelected, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 569, GroupLayout.PREFERRED_SIZE))
																.addContainerGap())
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(btnDelete)
																.addGap(81)
																.addComponent(btnCreateLevel)
																.addPreferredGap(ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
																.addComponent(btnCreateBlock)
																.addGap(73)))))))
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(91)
										.addComponent(errorMessage, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addContainerGap(226, Short.MAX_VALUE)
										.addComponent(btnTestGame)
										.addGap(85)
										.addComponent(btnPublishGame)))
						.addGap(172))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCreateA)
								.addComponent(btnLogout))
						.addGap(3)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addComponent(lblFirstEnterThe)
						.addGap(29)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(gameNameTextField)
								.addComponent(gameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCreate))
						.addGap(51)
						.addComponent(lblNewLabel)
						.addGap(47)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSelectAGame)
								.addComponent(gameList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnGameSettings))
						.addGap(32)
						.addComponent(lblForASelected, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addGap(35)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnDelete)
								.addComponent(btnCreateLevel)
								.addComponent(btnCreateBlock))
						.addGap(30)
						.addComponent(errorMessage)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnPublishGame)
								.addComponent(btnTestGame))
						.addGap(18))
				);
		frame.getContentPane().setLayout(groupLayout);
	}
	void refreshData() {
		errorMessage.setText(error);
		//Select Games
		String published = "false";

		//Create Game Text Field
		gameTextField.setText("");
		availableGames = new HashMap<Integer, String>();
		gameList.removeAllItems();
		int index = 0;



		try {
			for (TOGame game : Block223Controller.getDesignableGames()) {
				availableGames.put(index, game.getName());
				gameList.addItem(game.getName());
				index++;
			}
		} catch (InvalidInputException e) {
			error = e.getMessage();
		};
		gameList.setSelectedIndex(-1);
	}

	private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = "";
		// call the controller
		try {
			//Block223Application.setCurrentUserRole(Tyler);
			//System.out.println(gameTextField.getText());
			Block223Controller.createGame(gameTextField.getText());
			Block223Persistence.save(Block223Application.getBlock223());


		} catch (InvalidInputException e) {
			error = e.getMessage();
			//throw new RuntimeException(error);
		}

		// update visuals
		refreshData();
	}


	private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		//STATEMENT TO LOGOUT AND CHANGE BACK TO LOGIN WINDOW
		Block223Controller.logout();
		Block223Application.loginPage.frame.setVisible(true);
		Block223Application.adminMainPage.frame.setVisible(false);
	}

	private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";

		try {
			if(gameList.getSelectedItem()!=null) {
				Block223Application.setCurrentGame(Block223.findGame(gameList.getSelectedItem().toString()));
				Block223Controller.deleteGame(gameList.getSelectedItem().toString());
			}

		}catch (InvalidInputException e){
			error = e.getMessage();
			//throw new RuntimeException(error);
		}

		//update visuals
		refreshData();
	}

	private void btnGameSettingsActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		error = "";
		try {
			Block223Application.setCurrentGame(Block223.findGame(gameList.getSelectedItem().toString()));
		}catch(NullPointerException e) {
			throw new InvalidInputException("must select game");
		}

		Block223Application.adminMainPage.frame.setVisible(false);
		Block223Application.gameSetting.setCurrentParameters();
		Block223Application.gameSetting.refreshData();
		Block223Application.gameSetting.frame.setVisible(true);
	}

	private void btnCreateLevelActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		error = "";
		try {
			Block223Application.setCurrentGame(Block223.findGame(gameList.getSelectedItem().toString()));
		}catch(NullPointerException e) {
			throw new InvalidInputException("To create a level you first must choose a game");
		}

		Block223Application.levelPage.refreshData();
		Block223Application.levelPage.frame.setVisible(true);
		Block223Application.adminMainPage.frame.setVisible(false);
	}

	private void btnCreateBlockActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException {
		error = "";
		try {
			Block223Application.setCurrentGame(Block223.findGame(gameList.getSelectedItem().toString()));
		}catch(NullPointerException e) {
			throw new InvalidInputException("Must choose game");
		}
		Block223Application.blockPage.frame.setVisible(true);
		Block223Application.adminMainPage.frame.setVisible(false);
		Block223Application.blockPage.refreshBlockList();
		Block223Application.blockPage.refreshData();
	}
}