package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel; 
import javax.swing.table.TableCellRenderer;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;

public class LevelDesign extends JFrame{

	public JFrame frame;
	private JTextField newBlockXField;
	private JTextField newBlockYField;
	private JComboBox<Integer> levelComboBox;
	JComboBox<Integer> blockComboBox;

	String error = "";

	private JLabel errorLabel;
	private JLabel errorLabel_1;
	private JTable table;
	private JTextField currentBlockXField;
	private JTextField currentBlockYField;
	private JTextField newMoveBlockXField;
	private JTextField newMoveBlockYField;

	// displaying the current game and current level
	JLabel gameNameLabel;
	JLabel indexLevelLabel;

	//current level display
	int currentLevel;


	String[][] data = {
			{"","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"},
			{"1","","","","","","","","","","","","","","",""},
			{"2","","","","","","","","","","","","","","",""},
			{"3","","","","","","","","","","","","","","",""},
			{"4","","","","","","","","","","","","","","",""},
			{"5","","","","","","","","","","","","","","",""},
			{"6","","","","","","","","","","","","","","",""},
			{"7","","","","","","","","","","","","","","",""},
			{"8","","","","","","","","","","","","","","",""},
			{"9","","","","","","","","","","","","","","",""},
			{"10","","","","","","","","","","","","","","",""},
			{"11","","","","","","","","","","","","","","",""},
			{"12","","","","","","","","","","","","","","",""},
			{"13","","","","","","","","","","","","","","",""},
			{"14","","","","","","","","","","","","","","",""},
			{"15","","","","","","","","","","","","","","",""}
	};

	String[][] emptyCanvas = {
			{"","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"},
			{"1","","","","","","","","","","","","","","",""},
			{"2","","","","","","","","","","","","","","",""},
			{"3","","","","","","","","","","","","","","",""},
			{"4","","","","","","","","","","","","","","",""},
			{"5","","","","","","","","","","","","","","",""},
			{"6","","","","","","","","","","","","","","",""},
			{"7","","","","","","","","","","","","","","",""},
			{"8","","","","","","","","","","","","","","",""},
			{"9","","","","","","","","","","","","","","",""},
			{"10","","","","","","","","","","","","","","",""},
			{"11","","","","","","","","","","","","","","",""},
			{"12","","","","","","","","","","","","","","",""},
			{"13","","","","","","","","","","","","","","",""},
			{"14","","","","","","","","","","","","","","",""},
			{"15","","","","","","","","","","","","","","",""}

	};

	String[] columnNames = {"","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};

	//error message

	private JTextField blockField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelDesign window = new LevelDesign();
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
	public LevelDesign() {
		initialize();
	}
	public LevelDesign(String[][] data, int currentLevel) 
	{
		this.data = data;
		this.currentLevel = currentLevel;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.PINK);
		frame.setBounds(100, 100, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Game: ");

		JLabel lblNewLabel_2 = new JLabel("Design a level by adding/moving/removing blocks");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 18));

		JLabel label = new JLabel("");

		JLabel lblNewLabel_3 = new JLabel("Select a level for this game:");

		levelComboBox = new JComboBox();

		JLabel lblNewLabel_4 = new JLabel("X:");

		newBlockXField = new JTextField();
		newBlockXField.setColumns(10);

		JLabel lblY = new JLabel("Y:");

		newBlockYField = new JTextField();
		newBlockYField.setColumns(10);

		JLabel lblSelectANew = new JLabel("Select a new block to add:");

		blockComboBox = new JComboBox<Integer>();

		JButton btnAddsaveBlock = new JButton("Add Block");

		JLabel lblMoveOrRemove = new JLabel("Move or Remove a currently added block:");

		JButton backToMainMenuButton = new JButton("Back to main menu");

		JLabel lblAfterHavingSelected = new JLabel("After having selected a block, determine its position on the grid.");
		lblAfterHavingSelected.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JButton backToGameSettingsButton = new JButton("Back to game settings");


		backToMainMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backtoMainMenuActionPerformed(evt);
			}
		});

		backToGameSettingsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backtoGameSettingsActionPerformed(evt);
			}
		});


		table = new JTable(data, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!c.getBackground().equals(getSelectionBackground())) {

					Object obj = getModel().getValueAt(row, column);

					if (obj instanceof java.lang.String) {					
						String str = (String) obj;
						str.trim();

						c.setBackground(str.endsWith(" ") ? new Color(Block223Application.getCurrentGame().findBlock(Integer.parseInt((String) getModel().getValueAt(row, column).toString().trim())).getRed(), Block223Application.getCurrentGame().findBlock(Integer.parseInt((String) getModel().getValueAt(row, column).toString().trim())).getGreen(), Block223Application.getCurrentGame().findBlock(Integer.parseInt((String) getModel().getValueAt(row, column).toString().trim())).getBlue()) : Color.WHITE );

					}
					else {
						c.setBackground(Color.WHITE);
					}
				}
				return c;
			}	
		};

		table.setGridColor(Color.pink);
		table.setRowHeight(30);
		table.setEnabled(false);						

		JButton removeBlockButton = new JButton("Remove Block");

		JButton moveBlockButton = new JButton("Move Block");

		currentBlockXField = new JTextField();
		currentBlockXField.setColumns(10);

		currentBlockYField = new JTextField();
		currentBlockYField.setColumns(10);

		newMoveBlockXField = new JTextField();
		newMoveBlockXField.setColumns(10);

		newMoveBlockYField = new JTextField();
		newMoveBlockYField.setColumns(10);

		JLabel label_1 = new JLabel("Current X:");

		JLabel label_2 = new JLabel("Current Y:");

		JLabel label_3 = new JLabel("New X:");

		JLabel label_4 = new JLabel("New Y:");

		gameNameLabel = new JLabel("Game name");

		JLabel lblLevel = new JLabel("Current level: ");

		indexLevelLabel = new JLabel("index");



		JButton loadLevelButton = new JButton("Load level");

		blockField = new JTextField();
		blockField.setHorizontalAlignment(SwingConstants.CENTER);
		blockField.setColumns(10);
		blockField.setEditable(false);

		blockComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(blockComboBox.getSelectedItem() != null) {
					itemBlockSelected(e);
				}else {
					blockField.setText("");
					blockField.setBackground(Color.WHITE);
				}
			}
		});

		JButton saveButton = new JButton("Save Level");

		errorLabel_1 = new JLabel(""); ////////////delete this in window builder when it starts working
		errorLabel_1.setForeground(Color.RED);

		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);


		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(648)
										.addComponent(errorLabel_1, GroupLayout.PREFERRED_SIZE, 690, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(20)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(lblNewLabel)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
																		.addGroup(groupLayout.createSequentialGroup()
																				.addComponent(lblNewLabel_4)
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addComponent(newBlockXField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
																				.addGap(18)
																				.addComponent(lblY)
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addComponent(newBlockYField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
																				.addGap(18)
																				.addComponent(btnAddsaveBlock))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
																						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																						.addComponent(currentBlockXField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
																						.addComponent(newMoveBlockXField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
																						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																						.addComponent(currentBlockYField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
																						.addComponent(newMoveBlockYField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
																				.addGap(49)
																				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
																						.addComponent(moveBlockButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(removeBlockButton, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																						.addGroup(groupLayout.createSequentialGroup()
																								.addComponent(lblNewLabel_3)
																								.addPreferredGap(ComponentPlacement.RELATED)
																								.addComponent(levelComboBox, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
																						.addGroup(groupLayout.createSequentialGroup()
																								.addGap(6)
																								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																										.addComponent(gameNameLabel, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
																										.addComponent(label))))
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addComponent(loadLevelButton)
																				.addGap(84))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addComponent(lblSelectANew, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
																						.addGroup(groupLayout.createSequentialGroup()
																								.addGap(98)
																								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																										.addComponent(backToGameSettingsButton, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
																										.addComponent(backToMainMenuButton)))
																						.addGroup(groupLayout.createSequentialGroup()
																								.addComponent(blockComboBox, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																								.addComponent(blockField, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
																								.addGap(50))))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addComponent(lblMoveOrRemove, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(ComponentPlacement.RELATED)))
																.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																		.addComponent(saveButton)
																		.addComponent(lblAfterHavingSelected)))
														.addGap(34)
														.addComponent(table, GroupLayout.PREFERRED_SIZE, 683, GroupLayout.PREFERRED_SIZE))
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(lblNewLabel_2)
														.addGap(322)
														.addComponent(lblLevel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(indexLevelLabel))
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(errorLabel, GroupLayout.PREFERRED_SIZE, 977, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(errorLabel_1, GroupLayout.PREFERRED_SIZE, 690, GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_2)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblLevel)
														.addComponent(indexLevelLabel)))
										.addGap(19)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup()
																		.addGap(58)
																		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																				.addComponent(lblNewLabel_3)
																				.addComponent(levelComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(loadLevelButton)))
																.addGroup(groupLayout.createSequentialGroup()
																		.addGap(9)
																		.addComponent(label)
																		.addGap(4)
																		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																				.addComponent(lblNewLabel)
																				.addComponent(gameNameLabel))))
														.addGap(34)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(blockComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblSelectANew)
																.addComponent(blockField, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
														.addGap(17)
														.addComponent(lblAfterHavingSelected)
														.addGap(8)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblNewLabel_4)
																.addComponent(newBlockXField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblY)
																.addComponent(newBlockYField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(btnAddsaveBlock))
														.addGap(19)
														.addComponent(lblMoveOrRemove)
														.addGap(27)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(removeBlockButton)
																.addComponent(currentBlockXField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(label_1)
																.addComponent(currentBlockYField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(label_2))
														.addGap(23)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(moveBlockButton)
																.addComponent(newMoveBlockXField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(newMoveBlockYField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(label_3)
																.addComponent(label_4)))))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(54)
										.addComponent(backToMainMenuButton)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(backToGameSettingsButton)))
						.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
						.addComponent(saveButton)
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(errorLabel_1)
								.addComponent(errorLabel))
						.addContainerGap(117, Short.MAX_VALUE))
				);
		frame.getContentPane().setLayout(groupLayout);

		//		backButton.addActionListener(new java.awt.event.ActionListener() {
		//			public void actionPerformed(java.awt.event.ActionEvent evt) {
		//				backButtonActionPerformed(evt);
		//				}
		//			});
		removeBlockButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeBlockButtonActionPerformed(evt);
			}
		});
		moveBlockButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveBlockButtonActionPerformed(evt);
			}
		});
		btnAddsaveBlock.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnAddsaveBlockActionPerformed(evt);
				} catch (InvalidInputException e) {
					error = e.getMessage();
				}
			}
		});
		loadLevelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadLevelButtonActionPerformed(evt);
			}
		});
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});




	}



	protected void saveButtonActionPerformed(ActionEvent evt) {
		error = "";
		try {
			Block223Controller.saveGame();
			error = "Level saved";
		}catch(InvalidInputException e) {
			error = e.getMessage();
		}
		refreshData();

	}

	public void refreshData() 
	{
		errorLabel.setText(error);
		if (error == null || error.length() == 0) {
			newBlockXField.setText("");
			newBlockYField.setText("");
			currentBlockXField.setText("");
			currentBlockYField.setText("");
			newMoveBlockXField.setText("");
			newMoveBlockYField.setText("");
			blockComboBox.setSelectedIndex(-1);
			gameNameLabel.setText(Block223Application.getCurrentGame().getName());
			if(currentLevel>0) {
				indexLevelLabel.setText(currentLevel+"");
			}else {
				indexLevelLabel.setText("No level selected");
			}


			// filling the level combo box
			levelComboBox.removeAllItems();
			int numLevels = Block223Application.getCurrentGame().getLevels().size();
			for(int i = 1 ; i <= numLevels; i++) 
			{
				levelComboBox.addItem(i);
			}


			// filling the block combo box
			blockComboBox.removeAllItems();
			for(Block block : Block223Application.getCurrentGame().getBlocks()) 
			{
				blockComboBox.addItem(block.getId());
			}
			levelComboBox.setSelectedIndex(currentLevel -1);
		}

	}

	// load the level selected by the admin and display it
	private void loadLevelButtonActionPerformed(java.awt.event.ActionEvent evt) 
	{
		error = "";
		// resetting display 
		data = emptyCanvas;
		// current level
		int index=0;
		try {
			index = (int) levelComboBox.getSelectedItem();
		}catch(NullPointerException e) {
			error = "Must choose a level to load";
		}
		if (error == null || error.length() == 0) {
			// filling the display with the actual block assignments and respective IDs
			for(int y = 1; y <= 15; y++) 
			{
				for(int x = 1; x<= 16; x++) 
				{
					if(Block223Application.getCurrentGame().getLevel(index - 1).findBlockAssignment(x, y) != null) 
					{
						String blockID = Block223Application.getCurrentGame().getLevel(index - 1).findBlockAssignment(x, y).getBlock().getId() + " ";
						data[y][x] = blockID;

					}					
				}
			}
			currentLevel = index;

			LevelDesign ld = new LevelDesign(data,index);
			this.frame.setVisible(false);
			ld.refreshData();
			ld.frame.setVisible(true);
		}
		refreshData();
	}

	private void btnAddsaveBlockActionPerformed(java.awt.event.ActionEvent evt) throws InvalidInputException 
	{
		// error message 
		error = "";
		int X = 0;
		int Y = 0;
		Integer blockID = 0;
		int index =0;
		if(blockComboBox.getSelectedIndex() == -1) {
			error = "Must select a block to add! ";
		}
		if(levelComboBox.getSelectedIndex() == -1) {
			error = "Must select a level! ";
		}
		if (error.length() == 0) {
			try {
				X = Integer.parseInt(newBlockXField.getText());
				Y = Integer.parseInt(newBlockYField.getText());			
			}catch(NumberFormatException e) 
			{
				error += "A grid position must be a numerical value! ";
			}
			//does positionBlock not cover this?
			if(X <= 0 || Y <= 0 ) 
			{
				error += "A block position must be positive integer greater than zero.";
			}
			if (X >15) {
				error += "X position must be < 16 ";
			}
			if (Y>16) {
				error += "Y position must be < 17 ";
			}

			if(error.length() == 0) 
			{
				try {
					blockID = (int) blockComboBox.getSelectedItem();
					index = (int) levelComboBox.getSelectedItem();
					Block223Controller.positionBlock(blockID, currentLevel, X, Y);
				}catch(InvalidInputException e) {
					error = e.getMessage();

				}			
			}
			// valid input to add block
			if(error.length() == 0) 
			{
				data[Y][X] = blockID+ " ";
				LevelDesign ld = new LevelDesign(data,index);
				this.frame.setVisible(false);
				ld.refreshData();
				ld.frame.setVisible(true);

			}
		}
		refreshData();

	}
	private void removeBlockButtonActionPerformed(java.awt.event.ActionEvent evt)  
	{
		// error message
		error = "";
		int X = 0;
		int Y = 0;
		int index = 0;
		if (levelComboBox.getSelectedIndex() == -1) {
			error = "Must choose a level to remove a block! ";
		}
		if (error.length() == 0) {
			try {
				X = Integer.parseInt(currentBlockXField.getText());
				Y = Integer.parseInt(currentBlockYField.getText());
			}catch(NumberFormatException e) 
			{
				error += "A grid position must be a numerical value! ";
			}
			if(X <= 0 || Y <= 0 ) 
			{
				error += "A block position must be positive integer greater than zero.";
			}
			if(error.length() == 0) 
			{
				try {
					index = (int) levelComboBox.getSelectedItem();
					Block223Controller.removeBlock(currentLevel, X, Y);
				}catch(InvalidInputException e) {
					error = e.getMessage();
				}

			}
			// valid input to remove block
			if(error.length() == 0) 
			{
				data[Y][X] = "";
				LevelDesign ld = new LevelDesign(data,index);
				this.frame.setVisible(false);
				ld.refreshData();
				ld.frame.setVisible(true);
			}

		}
		// Update visuals
		refreshData();

	}
	private void moveBlockButtonActionPerformed(java.awt.event.ActionEvent evt) 
	{
		// error message
		error = "";

		int cur_X = 0;
		int cur_Y = 0;
		int new_X = 0;
		int new_Y = 0;
		int index = 0;
		String blockID = "";
		if (levelComboBox.getSelectedIndex() == -1) {
			error = "Must choose a level to remove a block! ";
		}

		try {
			cur_X = Integer.parseInt(currentBlockXField.getText());
			cur_Y = Integer.parseInt(currentBlockYField.getText());
			new_X = Integer.parseInt(newMoveBlockXField.getText());
			new_Y = Integer.parseInt(newMoveBlockYField.getText());
		}catch(NumberFormatException e) 
		{
			error += "A grid position must be a numerical value! ";
		}
		if(cur_X <= 0 || cur_Y <= 0 || new_X <= 0 || new_Y <= 0 ) 
		{
			error += "A block position must be positive integer greater than zero.";
		}
		if(error.length() == 0) 
		{
			try {
				index = (int) levelComboBox.getSelectedItem();
				// finding the block ID;
				blockID =  Block223Application.getCurrentGame().getLevel(index - 1)
						.findBlockAssignment(cur_X, cur_Y).getBlock().getId() + " ";

				Block223Controller.moveBlock(currentLevel, cur_X, cur_Y, new_X, new_Y);
			}catch(InvalidInputException e) {
				error = e.getMessage();

			}			
		}

		// valid input to move a block
		if(error.length() == 0) {
			data[cur_Y][cur_X] = "";
			data[new_Y][new_X] = blockID;
			LevelDesign ld = new LevelDesign(data,index);
			this.frame.setVisible(false);
			ld.refreshData();
			ld.frame.setVisible(true);
		}

		// Update visuals
		errorLabel.setText(error);
		try{
			refreshData();
		}catch(NullPointerException e) {
			System.out.println("");
		}
	}

	private void backtoMainMenuActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.adminMainPage.frame.setVisible(true);
		this.frame.setVisible(false);
	}

	private void backtoGameSettingsActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.gameSetting.setCurrentParameters();
		Block223Application.gameSetting.frame.setVisible(true);
		this.frame.setVisible(false);
	}
	private void itemBlockSelected(ActionEvent e) {
		int id = 0;
		if (blockComboBox.getSelectedItem() != null) {
			id = (int) blockComboBox.getSelectedItem();
			Block block = Block223Application.getCurrentGame().findBlock(id);
			blockField.setBackground(new Color(block.getRed(), block.getGreen(), block.getBlue()));
			blockField.setText(block.getPoints() +"\nPoints");
		}
	}
}
