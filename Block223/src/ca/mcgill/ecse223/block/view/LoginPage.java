package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;



public class LoginPage {

	public JFrame frame;
	private JTextField txtUsernameLogin;
	private JTextField txtPasswordLogin;
	
	private JLabel errorMessage;
	private String error = "";
	private JTextField txtPlayerPasswordSignup;
	private JTextField txtUsernameSignup;
	private JTextField txtAdminPasswordSignup;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
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
	public LoginPage() {
		Block223Persistence.load();
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
		
		JLabel lblWelcomeToBlock = new JLabel("Welcome to Block223");
		lblWelcomeToBlock.setBackground(Color.BLACK);
		lblWelcomeToBlock.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblWelcomeToBlock.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtUsernameLogin = new JTextField();
		txtUsernameLogin.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsernameLogin.setColumns(10);
		
		txtPasswordLogin = new JTextField();
		txtPasswordLogin.setColumns(10);
		
		JLabel loginUsername = new JLabel("Username: ");
		loginUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel loginPassword = new JLabel("Password: ");
		loginPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel signupPlayerPassword = new JLabel("Player Password: ");
		signupPlayerPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lblSignup = new JLabel("Signup");
		lblSignup.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lblDesignedByTeam = new JLabel("Designed by Team P20");
		
		txtPlayerPasswordSignup = new JTextField();
		txtPlayerPasswordSignup.setColumns(10);
		
		txtUsernameSignup = new JTextField();
		txtUsernameSignup.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel lblAdminPassword = new JLabel("Admin Password: ");
		lblAdminPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		txtAdminPasswordSignup = new JTextField();
		txtAdminPasswordSignup.setColumns(10);
		
		errorMessage = new JLabel("     ");
		errorMessage.setForeground(Color.RED);
		
		JButton btnLogin = new JButton("Login");
		
		JButton btnSignup = new JButton("Signup");
		
		btnLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLoginActionPerformed(evt);
			}
		});
		btnSignup.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSignupActionPerformed(evt);
			}
		});
		
		//layout
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(117)
							.addComponent(lblLogin)
							.addGap(357)
							.addComponent(lblSignup, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblWelcomeToBlock, GroupLayout.PREFERRED_SIZE, 687, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(9)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(loginUsername)
											.addGap(18)
											.addComponent(txtUsernameLogin, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(loginPassword)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(txtPasswordLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnLogin, Alignment.TRAILING))
									.addPreferredGap(ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(signupPlayerPassword)
											.addComponent(lblAdminPassword, Alignment.LEADING))
										.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(276)
									.addComponent(lblDesignedByTeam)))
							.addGap(36)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtUsernameSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtAdminPasswordSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtPlayerPasswordSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnSignup, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(errorMessage, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(14)
					.addComponent(lblWelcomeToBlock, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDesignedByTeam)
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSignup)
								.addComponent(lblLogin))
							.addGap(80)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(loginPassword)
								.addComponent(txtPasswordLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(signupPlayerPassword)
								.addComponent(txtPlayerPasswordSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtUsernameLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(loginUsername)
								.addComponent(txtUsernameSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
							.addGap(53)))
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtAdminPasswordSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAdminPassword, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLogin)
						.addComponent(btnSignup))
					.addGap(76)
					.addComponent(errorMessage)
					.addGap(557))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public void refreshData() {
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			txtUsernameSignup.setText("");
			txtPlayerPasswordSignup.setText("");
			txtAdminPasswordSignup.setText("");
			txtUsernameLogin.setText("");
			txtPasswordLogin.setText("");
		}
			
		
	}
	public void btnSignupActionPerformed(ActionEvent evt) {
		error = "";
		if (error.length() == 0) {
		
			String username = txtUsernameSignup.getText();
			String playerPassword = txtPlayerPasswordSignup.getText();
			String adminPassword = txtAdminPasswordSignup.getText();
			try {
				Block223Controller.register(username, playerPassword, adminPassword);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}	
		}
		refreshData();
	}
	
	public void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		if (error.length() == 0) {
			String username = txtUsernameLogin.getText();
			String password = txtPasswordLogin.getText();
			try {
				Block223Controller.login(username, password);
				if (Block223Application.getCurrentUserRole() instanceof Player) {
					Block223Application.gamePage.refreshData();
					Block223Application.gamePage.frame.setVisible(true);
				}
				if (Block223Application.getCurrentUserRole() instanceof Admin){
					Block223Application.adminMainPage.refreshData();
					Block223Application.adminMainPage.frame.setVisible(true);
				}
				Block223Application.loginPage.frame.setVisible(false);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
			Block223Controller.logout();
			try {
				Block223Controller.login(username, password);
				if (Block223Application.getCurrentUserRole() instanceof Player) {
					Block223Application.gamePage.frame.setVisible(true);
				}
				if (Block223Application.getCurrentUserRole() instanceof Admin){
					Block223Application.adminMainPage.refreshData();
					Block223Application.adminMainPage.frame.setVisible(true);
				}
				Block223Application.loginPage.frame.setVisible(false);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		refreshData();
		
	}
}
