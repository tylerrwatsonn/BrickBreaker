package ca.mcgill.ecse223.block.application;


import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.CreateBlock;
import ca.mcgill.ecse223.block.view.GameSetup;
import ca.mcgill.ecse223.block.view.LevelDesign;
import ca.mcgill.ecse223.block.view.LoginPage;
import ca.mcgill.ecse223.block.view.MainAdminMenu;

import ca.mcgill.ecse223.block.view.PlayGame;
import ca.mcgill.ecse223.block.view.PlayMode;
import ca.mcgill.ecse223.block.view.PlayModeViewStyle;


public class Block223Application {
	
	public static UserRole currentUserRole;
	public static Block223 block223;
	public static Game currentGame = null;
	public static LoginPage loginPage = new LoginPage();
	public static PlayGame gamePage = new PlayGame();
	public static MainAdminMenu adminMainPage = new MainAdminMenu();
	public static GameSetup gameSetting = new GameSetup();
	public static LevelDesign levelPage = new LevelDesign();
	public static CreateBlock blockPage = new CreateBlock();
	
	public static PlayedGame currentPlayedGame = null;
	
	public static void main(String[] args) {
		
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                loginPage.frame.setVisible(true);
                adminMainPage.frame.setVisible(false);
                gamePage.frame.setVisible(false);
                gameSetting.frame.setVisible(false);
                levelPage.frame.setVisible(false);
                blockPage.frame.setVisible(false);
            }
        });
        
	}

	public static Block223 getBlock223() {
		if (block223 == null) {
			// load model
			block223 = Block223Persistence.load();
		}
 		return block223;
	}
	 
	public static Block223 resetBlock223() {
		Block223Persistence.load();
		// Added 
		setCurrentGame(null);
		setCurrentPlayableGame(null);
		return block223;
	}
	
	public static void setCurrentUserRole(UserRole aUserRole) {
		currentUserRole = aUserRole;
	}

	public static UserRole getCurrentUserRole() {
		return currentUserRole;
	}
	public static void setCurrentGame(Game aGame) {
		currentGame = aGame;
	}
	
	public static Game getCurrentGame() {
		return currentGame;
	}
	
	public static PlayedGame getCurrentPlayableGame() {
		return currentPlayedGame;
	}
	public static void setCurrentPlayableGame(PlayedGame pg) {
		currentPlayedGame = pg;
	}


}
