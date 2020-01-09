package ca.mcgill.ecse223.block.view;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.model.Block;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class CreateBlock implements ChangeListener {

	public JFrame frame;

	private String error;
	private JLabel errorLabel;
	private JTextField previewField;

	private JLabel lblRedValue;
	private JLabel lblGreenValue;
	private JLabel lblBlueValue;
	private JLabel lblPointsValue;

	private JSlider sliderRed;
	private JSlider sliderGreen;
	private JSlider sliderBlue;
	private JSlider sliderPoints;

	private JComboBox<String> blocksComboBox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateBlock window = new CreateBlock();
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
	public CreateBlock() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.pink);
		frame.getContentPane().setForeground(Color.white);

		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblCreate = new JLabel("Create or Select a block for this game:");



		JLabel lblCreateASet = new JLabel("Create a set of blocks to be used in different levels");
		lblCreateASet.setFont(new Font("Lucida Grande", Font.BOLD, 18));

		errorLabel = new JLabel("New label");
		errorLabel.setForeground(Color.RED);

		blocksComboBox = new JComboBox();

		JLabel lblRed = new JLabel("Red:");
		JLabel lblGreen = new JLabel("Green:");
		JLabel lblBlue = new JLabel("Blue:");
		JLabel lblPoints = new JLabel("Points:");

		lblRedValue = new JLabel("100");
		lblGreenValue = new JLabel("100");
		lblBlueValue = new JLabel("100");
		lblPointsValue = new JLabel("100");

		sliderRed = new JSlider();
		sliderRed.setMinimum(0);
		sliderRed.setMaximum(255);
		sliderRed.setValue(100);
		sliderRed.addChangeListener((ChangeListener) this);

		sliderGreen = new JSlider();
		sliderGreen.setMinimum(0);
		sliderGreen.setMaximum(255);
		sliderGreen.setValue(100);
		sliderGreen.addChangeListener((ChangeListener) this);

		sliderBlue = new JSlider();
		sliderRed.setMinimum(0);
		sliderBlue.setMaximum(255);
		sliderBlue.setValue(100);
		sliderBlue.addChangeListener((ChangeListener) this);

		sliderPoints = new JSlider();
		sliderPoints.setMinimum(1);
		sliderPoints.setMaximum(1000);
		sliderPoints.setValue(100);
		sliderPoints.addChangeListener((ChangeListener) this);

		previewField = new JTextField();
		previewField.setEditable(false);
		previewField.setColumns(10);

		JButton deleteBlock = new JButton("Delete selected block");
		deleteBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				deleteBlockActionPerformed(evt);
			}
		});

		JButton createBlock = new JButton("Create a new block");
		createBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				createBlockActionPerformed(evt);
			}
		});

		blocksComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (blocksComboBox.getSelectedItem() != null) {
					refreshData();
				}
			}
		});

		JButton updateBlock = new JButton("Update selected block");
		updateBlock.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					updateBlockActionPerformed(evt);
				} catch (InvalidInputException e) {
					error = e.getMessage();
				}
			}
		});

		JButton backToGameSettings = new JButton("Back to game settings");
		backToGameSettings.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backtoGameSettingsActionPerformed(evt);
			}
		});

		JButton backToMainMenu = new JButton("Back to main menu");

		backToMainMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backtoMainMenuActionPerformed(evt);
			}
		});

		//////////////////////////////////////////////////////////////////////////////

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(18)
										.addComponent(lblCreateASet))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(36)
										.addComponent(lblCreate)
										.addGap(10)
										.addComponent(blocksComboBox, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(18)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup()
																		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
																				.addComponent(lblBlue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(lblGreen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(lblRed, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(lblPoints, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
																				.addComponent(lblPointsValue, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
																				.addComponent(lblBlueValue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(lblGreenValue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(lblRedValue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																				.addComponent(sliderPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(sliderBlue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(sliderGreen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(sliderRed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(previewField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																.addComponent(errorLabel)))
												.addComponent(backToGameSettings, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
										.addGap(26)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(updateBlock, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
												.addComponent(backToMainMenu, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
												.addComponent(createBlock, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
												.addComponent(deleteBlock))))
						.addContainerGap(50, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(30)
						.addComponent(lblCreateASet)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(25)
										.addComponent(lblCreate))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(21)
										.addComponent(blocksComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(47)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblRed)
														.addComponent(lblRedValue))
												.addComponent(sliderRed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(deleteBlock))
										.addGap(14)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblGreen)
														.addComponent(lblGreenValue))
												.addComponent(sliderGreen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(16)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblBlue)
														.addComponent(lblBlueValue))
												.addComponent(sliderBlue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblPoints)
																.addComponent(lblPointsValue))
														.addPreferredGap(ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
														.addComponent(errorLabel)
														.addGap(49))
												.addGroup(groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																.addComponent(createBlock)
																.addComponent(sliderPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(backToMainMenu)
																.addComponent(backToGameSettings))))
										.addGap(20))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(75)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(previewField, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
												.addComponent(updateBlock))
										.addContainerGap())))
				);
		frame.getContentPane().setLayout(groupLayout);
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void createBlockActionPerformed(ActionEvent e) {
		error = "";
		int red=0;
		int green=0;
		int blue=0;
		int points=0;
		if (blocksComboBox.getSelectedItem().equals("Create New Block: ")) {
			try {
				red = Integer.parseInt(lblRedValue.getText());
				green = Integer.parseInt(lblGreenValue.getText());
				blue = Integer.parseInt(lblBlueValue.getText());
				points = Integer.parseInt(lblPointsValue.getText());

			} catch (NumberFormatException e1) {
				error = "All paramters must be specified and be numerical values";
			}
			if (error.length() == 0) {
				try {
					Block223Controller.addBlock(red, green, blue, points);
				}catch(InvalidInputException e1) {
					error += e1.getMessage();
				}
			}
		}
		else {
			error = "Block cannot be created if there is a block selected.";
		}
		refreshBlockList();
		refreshData();
	}

	private void updateBlockActionPerformed(ActionEvent e) throws InvalidInputException {
		error = "";
		String bcb = "";
		if (!blocksComboBox.getSelectedItem().equals("Create New Block: ")) {
			try {
				bcb = (String)blocksComboBox.getSelectedItem();
			}catch(RuntimeException e1) {
				error = e1.getMessage();
			}
			if (error.length() == 0) {
				try {
					int red = Integer.parseInt(lblRedValue.getText());
					int green = Integer.parseInt(lblGreenValue.getText());
					int blue = Integer.parseInt(lblBlueValue.getText());
					int points = Integer.parseInt(lblPointsValue.getText());
					int id = Integer.parseInt(bcb.split("#")[1]);
					Block223Controller.updateBlock(id, red, green, blue, points);

				} catch (RuntimeException e1) {
					error = e1.getMessage();
				}
			}
		}
		else {
			error = ("Block can only be updated if there is a block selected.");
		}
		refreshBlockList();
		refreshData();
	}

	public void refreshBlockList() {
		try {
			blocksComboBox.removeAllItems();
			blocksComboBox.addItem("Create New Block: ");
			for (TOBlock block : Block223Controller.getBlocksOfCurrentDesignableGame()) {
				blocksComboBox.addItem("Pts: " + block.getPoints() + ", rgb(" + block.getRed() + ", " + block.getGreen() + ", " + block.getBlue() + ") - #"+block.getId()+"");
			}
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
	}
	public void refreshData() {
		errorLabel.setText(error);

		if (error == null || error.length() == 0) {
			if (!blocksComboBox.getSelectedItem().equals("Create New Block: ")) { // There is something selected
				String bcb = (String)blocksComboBox.getSelectedItem();
				Block found_block = Block223Application.getCurrentGame().findBlock(Integer.parseInt(bcb.split("#")[1]));
				int red = found_block.getRed();
				int green = found_block.getGreen();
				int blue = found_block.getBlue();
				int points = found_block.getPoints();
				lblRedValue.setText(red+"");
				sliderRed.setValue(red);
				lblGreenValue.setText(green+"");
				sliderGreen.setValue(green);
				lblBlueValue.setText(blue+"");
				sliderBlue.setValue(blue);
				lblPointsValue.setText(points+"");
				sliderPoints.setValue(points);
				previewField.setText(points + " Points");
				previewField.setBackground(new Color(red, green, blue));


			} else { // nothing

				lblRedValue.setText("100");
				sliderRed.setValue(100);
				lblGreenValue.setText("100");
				sliderGreen.setValue(100);
				lblBlueValue.setText("100");
				sliderBlue.setValue(100);
				lblPointsValue.setText("100");
				sliderPoints.setValue(100);
				blocksComboBox.setSelectedItem("Create New Block: ");
				previewField.setText("");
				previewField.setBackground(Color.white);
			}
		}
	}


	private void deleteBlockActionPerformed(ActionEvent evt) {
		error = "";
		if (!blocksComboBox.getSelectedItem().equals("Create New Block: ")) {
			try {
				String bcb = (String)blocksComboBox.getSelectedItem();
				int id = Integer.parseInt(bcb.split("#")[1]);
				Block223Controller.deleteBlock(id);

			} catch (InvalidInputException e1) {
				error = e1.getMessage();
			}
		}
		else {
			error = ("Select a block to delete.");
		}
		refreshBlockList();
		refreshData();

	}


	private void backtoMainMenuActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.adminMainPage.frame.setVisible(true);
		Block223Application.blockPage.frame.setVisible(false);
	}

	private void backtoGameSettingsActionPerformed(java.awt.event.ActionEvent evt) {
		Block223Application.gameSetting.setCurrentParameters();
		Block223Application.gameSetting.frame.setVisible(true);
		Block223Application.blockPage.frame.setVisible(false);
	}

	public void stateChanged(ChangeEvent e) { 
		JSlider source = (JSlider)e.getSource();
		int value = source.getValue();

		if (source == sliderRed)
		{
			lblRedValue.setText(value+"");
			sliderRed.setValue(value);
		}
		else if (source == sliderGreen)
		{
			lblGreenValue.setText(value+"");
			sliderGreen.setValue(value);
		}
		else if (source == sliderBlue)
		{
			lblBlueValue.setText(value+"");
			sliderBlue.setValue(value);
		}
		else if (source == sliderPoints)
		{
			lblPointsValue.setText(value+"");
			sliderPoints.setValue(value);
		}

		previewBlock();
	}

	private void previewBlock() {
		errorLabel.setText("");
		String red = lblRedValue.getText();
		String green = lblGreenValue.getText();
		String blue = lblBlueValue.getText();
		String points = lblPointsValue.getText();
		Color color = new Color(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
		previewField.setBackground(color);
		previewField.setText(points +"\nPoints");
	}
}