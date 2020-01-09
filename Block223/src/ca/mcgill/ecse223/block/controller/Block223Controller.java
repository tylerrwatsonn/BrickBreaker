package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.BouncePoint;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.HallOfFameEntry;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;
import ca.mcgill.ecse223.block.view.GameAreaView;
import ca.mcgill.ecse223.block.view.PlayMode;

public class Block223Controller {

	// ****************************
	// Modifier methods
	// ****************************

	public static void createGame(String name) throws InvalidInputException {
		String error = "";
		Block223 block223 = Block223Application.getBlock223();
		UserRole admin = Block223Application.getCurrentUserRole();
		if(!(admin instanceof Admin)) {
			error = "Admin privileges are required to create a game.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		try{
			Game game = new Game(name, 1, (Admin) admin, 1, 1, 1, 10, 10, block223);
			block223.addGame(game);
			Block223Persistence.save(block223);
		} catch(RuntimeException e) {
			error = e.getMessage();
			throw new InvalidInputException(error);
		}

	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		String error = "";
		UserRole admin = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();

		if(game == null) {
			error += "A game must be selected to define game settings.";
			throw new InvalidInputException(error.trim());
		}

		Admin gameAdmin = game.getAdmin();

		if(!(admin instanceof Admin)) {
			error += "Admin privileges are required to define game settings.";
		}
		if(admin != gameAdmin) {
			error += "Only the admin who created the game can define its game settings.";
		}
		if(nrLevels<1 || nrLevels>99) {
			error += "The number of levels must be between 1 and 99.";
		}
		for(Level level: game.getLevels() ) {
			if(level.getBlockAssignments().size() > nrBlocksPerLevel) {
				error += "The maximum number of blocks per level cannot be less than the number of existing blocks in a level.";
			}
		}

		if((minBallSpeedX + minBallSpeedY)== 0) {
			error += "The minimum speed of the ball must be greater than zero.";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		try {

			game.setNrBlocksPerLevel(nrBlocksPerLevel);

			Ball ball = game.getBall();
			ball.setMinBallSpeedX(minBallSpeedX);
			ball.setMinBallSpeedY(minBallSpeedY);
			ball.setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);

			Paddle paddle = game.getPaddle();
			paddle.setMaxPaddleLength(maxPaddleLength);
			paddle.setMinPaddleLength(minPaddleLength);

			List<Level> levels = game.getLevels();
			int size = levels.size();
			while (nrLevels>size) {
				game.addLevel();
				size = levels.size();
			}
			while (nrLevels<size) {
				Level level = game.getLevel(size-1);
				level.delete();
				size = levels.size();
			}
		} catch (RuntimeException e) {
			error = e.getMessage();
			throw new InvalidInputException(error);
		}

	}


	public static void deleteGame(String name) throws InvalidInputException {
		String error = "";
		UserRole admin = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		Admin gameAdmin = current_game.getAdmin();
		if(current_game.isPublished()) {
			throw new InvalidInputException("A published game cannot be deleted.");
		}
		if(!(admin instanceof Admin)) {
			error = "Admin privileges are required to delete a game.";
		}
		if(admin != gameAdmin) { // EDIT
			error = error + "Only the admin who created the game can delete the game.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		try {
			Game game = Block223.findGame(name);
			if(game != null) {
				game.delete();
				try {
					Block223Persistence.save(Block223Application.getBlock223());
				}
				catch (RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
			} // we do not throw an InvalidInputException if such a game with the specified name does not exist 
			// since the end result will be the same.
		} catch (RuntimeException e) {
			error = e.getMessage();
			throw new InvalidInputException(error);
		}
	}

	public static void selectGame(String name) throws InvalidInputException {
		String error = "";
		UserRole admin = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		if(current_game.isPublished()) {
			throw new InvalidInputException("A published game cannot be changed.");
		}
		if(!(admin instanceof Admin)) {
			error = "Admin privileges are required to select a game.";
			throw new InvalidInputException(error);
		}
		if(current_game.getAdmin() != admin) {
			error = error + "Only the admin who created the game can select the game.";
		}
		Game game_exists = Block223.findGame(name);
		if(game_exists == null) { // EDIT 
			error = error + "A game with name " + name + " does not exist.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		try {
			Game game = Block223.findGame(name);
			Block223Application.setCurrentGame(game);
		} catch (RuntimeException e) {
			error = e.getMessage();
			throw new InvalidInputException(error);
		}
	}

	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		// EDIT 
		if(current_game == null) {
			error = "A game must be selected to define game settings.";
			throw new InvalidInputException(error.trim());
		}
		Admin admin = Block223Application.getCurrentGame().getAdmin();
		if(!(user instanceof Admin)) {
			error = error + "Admin privileges are required to define game settings.";
		}
		if(admin != user) { // !(user.getPassword().equals(admin.getPassword())) // EDIT
			error = error + "Only the admin who created the game can define its game settings.";
		}
		if(name == null || name.equals("")) {
			error =  error + "The name of a game must be specified."; 
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		Block223Controller.setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor, maxPaddleLength, minPaddleLength);

		String currentName = current_game.getName();
		if(Block223.findGame(name) != null) {

		}
		if(!((currentName).equals(name))) {
			try {	
				current_game.setName(name);
			}
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}

		// EDIT
		try {
			Block223Persistence.save(Block223Application.getBlock223());
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	//------------------------Karl------------------------//
	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
		String error = "";
		Game current_game = Block223Application.getCurrentGame();

		if (current_game != null)
		{
			UserRole user = Block223Application.getCurrentUserRole();
			Admin gameAdmin = current_game.getAdmin();

			if(!(user instanceof Admin)) {
				error = "Admin privileges are required to add a block.";
			}
			if(user != gameAdmin) {
				error = error + "Only the admin who created the game can add a block.";
			}
			if(error.length() > 0) {
				throw new InvalidInputException(error.trim());
			}

			for (Block block : current_game.getBlocks())
			{
				if (red == block.getRed() && green == block.getGreen() && blue == block.getBlue())
				{
					error = "A block with the same color already exists for the game.";
				}
			}

			if(error.length() > 0) {
				throw new InvalidInputException(error.trim());
			}

			try {
				new Block(red, green, blue, points, current_game);
			}
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
			try {
				Block223Persistence.save(Block223Application.getBlock223());
			}
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}

		} else {
			error = "A game must be selected to add a block.";
			throw new InvalidInputException(error.trim());
		}
	}
	//------------------------Karl------------------------//





	//------------------------Karl------------------------//
	public static void deleteBlock(int id) throws InvalidInputException {	

		Game current_game = Block223Application.getCurrentGame();
		String error = "";

		if (current_game != null) {
			UserRole user = Block223Application.getCurrentUserRole();
			Admin gameAdmin = current_game.getAdmin();

			if(!(user instanceof Admin)) {
				error = "Admin privileges are required to delete a block. ";
			}
			if(user != gameAdmin) {
				error += "Only the admin who created the game can delete a block.";
			}
			if(error.length() > 0) {
				throw new InvalidInputException(error.trim());
			}

			Block block = current_game.findBlock(id);

			if (block != null)
			{
				try {
					block.delete();
					Block223Persistence.save(Block223Application.getBlock223());
				}
				catch (RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
			}
		} else
		{
			error = "A game must be selected to delete a block.";
			throw new InvalidInputException(error.trim());
		}
	}
	//------------------------Karl------------------------//


	// Allan Reuben //
	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {

		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		if(current_game == null) {
			error = "A game must be selected to update a block.";
			throw new InvalidInputException(error.trim());
		}

		if(!(user instanceof Admin)) {
			error = "Admin privileges are required to update a block.";
			throw new InvalidInputException(error.trim());
		}

		Block blockToUpdate = current_game.findBlock(id);
		Admin gameAdmin = current_game.getAdmin();

		if(gameAdmin != user) {
			error = "Only the admin who created the game can update a block.";
			throw new InvalidInputException(error.trim());
		}


		for (int i = 0; i < current_game.getBlocks().size(); i++)
		{
			Block block = current_game.getBlocks().get(i);
			if (!(blockToUpdate == block)) {
				if (red == block.getRed() && green == block.getGreen() && blue == block.getBlue())
				{
					error = "A block with the same color already exists for the game.";
				}
			}
		}
		if(blockToUpdate == null) {
			error = "The block does not exist.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		try {
			blockToUpdate.setRed(red);
			blockToUpdate.setGreen(green);
			blockToUpdate.setBlue(blue);
			blockToUpdate.setPoints(points);
		}
		catch(RuntimeException e) {
			error = e.getMessage();
			throw new InvalidInputException(error.trim());
		}
	}


	// Allan Reuben //
	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		if(current_game == null) {
			error = "A game must be selected to position a block.";
			throw new InvalidInputException(error.trim());
		}

		if(!(user instanceof Admin)) {
			error = "Admin privileges are required to position a block.";
			throw new InvalidInputException(error.trim());
		}

		Admin gameAdmin = current_game.getAdmin();

		if(gameAdmin != user) {
			error = "Only the admin who created the game can position a block.";
			throw new InvalidInputException(error.trim());
		}
		Level current_Level = null;
		try {
			current_Level = current_game.getLevel(level - 1);
		} catch(IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		}


		Block block = current_game.findBlock(id);


		List<BlockAssignment> blockassignments = current_Level.getBlockAssignments();
		if(blockassignments.size() == game.getNrBlocksPerLevel()) {
			error += "The number of blocks has reached the maximum number (" + game.getNrBlocksPerLevel() + ") allowed for this game.";
		}
		for(BlockAssignment blockassignment : blockassignments) {
			if(gridHorizontalPosition == blockassignment.getGridHorizontalPosition() && gridVerticalPosition == blockassignment.getGridVerticalPosition()) {
				error += "A block already exists at location " + gridHorizontalPosition + "/" + gridVerticalPosition + ".";
			}
		}
		if(block == null) {
			error += "The block does not exist.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		new BlockAssignment(gridHorizontalPosition, gridVerticalPosition, current_Level , block, game);
	}


	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game currentGame = Block223Application.getCurrentGame();
		Level currentLevel;

		if(currentGame == null) {
			error += "A game must be selected to move a block.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Admin admin = Block223Application.getCurrentGame().getAdmin();
		if(!(user instanceof Admin)) {
			error = "Admin privileges are required to move a block.";
		}
		if(!(user.getPassword().equals(admin.getPassword())) || (Admin) user != admin) {
			error += "Only the admin who created the game can move a block.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		try {
			currentLevel = currentGame.getLevel(level-1);
		} catch(IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level "+level+" does not exist for the game.");
		} 
		try {
			BlockAssignment assignment = currentLevel.findBlockAssignment(oldGridHorizontalPosition, 
					oldGridVerticalPosition);
			if(assignment == null) {
				throw new InvalidInputException("A block does not exist at location "+ oldGridHorizontalPosition
						+ "/" + oldGridVerticalPosition+ ".");
			}
			if(currentLevel.findBlockAssignment(newGridHorizontalPosition, newGridVerticalPosition) != null) {
				throw new InvalidInputException("A block already exists at location "+ newGridHorizontalPosition
						+ "/" + newGridVerticalPosition+ ".");
			}
			assignment.setGridHorizontalPosition(newGridHorizontalPosition);
			assignment.setGridVerticalPosition(newGridVerticalPosition);		
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			Block223Persistence.save(Block223Application.getBlock223());
		}catch(RuntimeException e) 
		{
			throw new InvalidInputException(e.getMessage());
		}


	}

	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game currentGame = Block223Application.getCurrentGame();
		if(currentGame == null) {
			error += "A game must be selected to remove a block.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Admin admin = Block223Application.getCurrentGame().getAdmin();
		if(!(user instanceof Admin)) {
			error = "Admin privileges are required to remove a block.";
		}
		if(!(user.getPassword().equals(admin.getPassword())) || (Admin) user != admin) {
			error += "Only the admin who created the game can remove a block.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Level currentLevel = currentGame.getLevel(level-1);
		BlockAssignment assignment = currentLevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition);
		if(assignment != null) {
			assignment.delete();
			try {
				Block223Persistence.save(Block223Application.getBlock223());
			}catch(RuntimeException e){
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	public static void saveGame() throws InvalidInputException {

		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException ("Admin privileges are required to save a game.");
		}
		if(Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException ("A game must be selected to save it.");
		}
		if(!(Block223Application.getCurrentUserRole() == Block223Application.getCurrentGame().getAdmin())) {
			throw new InvalidInputException ("Only the admin who created the game can save it.");
		}
		Block223 block223 = Block223Application.getBlock223();
		try {
			Block223Persistence.save(block223);
		}catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		if ((playerPassword == null) || (playerPassword == "")) {throw new InvalidInputException("The player password needs to be specified.");}
		if (!(Block223Application.currentUserRole == null)) {throw new InvalidInputException("Cannot register a new user while a user is logged in.");}
		if (playerPassword.equals(adminPassword)) {throw new InvalidInputException("The passwords have to be different.");}

		Block223 block223 = Block223Application.getBlock223();
		try {
			Player player = new Player(playerPassword, block223);
			User user = new User(username, block223, player);
			if ((!(adminPassword == null)) && (!adminPassword.equals(""))) {
				Admin admin = new Admin(adminPassword, block223);
				user.addRole(admin);
			}
			Block223Persistence.save(block223);
		}catch (RuntimeException e) {
			if (e.getMessage() == "Cannot create due to duplicate username") {
				throw new InvalidInputException("The username has already been taken.");
			}
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void login(String username, String password) throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() == null)){
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		}
		Block223Application.resetBlock223();
		if (!(Block223Application.block223 == null)) {
			Block223Application.block223.delete();
		}
		Block223Application.setCurrentGame(null);
		Block223Application.block223 = Block223Persistence.load();
		if (User.getWithUsername(username) == null) {
			throw new InvalidInputException("The username and password do not match.");
		}
		User user = User.getWithUsername(username);
		List<UserRole> roles = user.getRoles();
		for (UserRole role : roles) {
			String rolePassword = role.getPassword();
			if (rolePassword.equals(password)) {
				Block223Application.setCurrentUserRole(role);
			}
		}
		if (Block223Application.getCurrentUserRole() == null) {
			throw new InvalidInputException("The username and password do not match.");
		}
	}

	public static void logout() {
		Block223Application.setCurrentUserRole(null);	
		Block223Persistence.save(Block223Application.getBlock223());
	}


	// ****************************
	// Query methods
	// ****************************

	public static List<TOGame> getDesignableGames() throws InvalidInputException {
		String error = "";
		UserRole admin = Block223Application.getCurrentUserRole();
		if(!(admin instanceof Admin)) {
			error = "Admin privileges are required to access game information.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Block223 block223 = Block223Application.getBlock223();

		List<TOGame> result = new ArrayList<TOGame>();

		List<Game> games = block223.getGames();

		for(Game game : games) {
			Admin gameAdmin = game.getAdmin();
			if(gameAdmin.equals(admin) && !game.isPublished()){
				TOGame to = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(), game.getBall().getMinBallSpeedX(), 
						game.getBall().getMinBallSpeedY(), game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(), game.getPaddle().getMinPaddleLength());
				result.add(to);
			}
		}
		return result;
	}

	public static TOGame getCurrentDesignableGame() throws InvalidInputException {
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		// EDIT
		if(current_game == null) {
			error = "A game must be selected to access its information.";
			throw new InvalidInputException(error.trim());
		}
		Admin admin = Block223Application.getCurrentGame().getAdmin();
		if(!(user instanceof Admin)) {
			error = error + "Admin privileges are required to access game information.";
		}
		if(user != admin) { // !(user.getPassword().equals(admin.getPassword())) // EDIT
			error = error + "Only the admin who created the game can access its information."; // EDIT
		}
		Game game_exists = Block223.findGame(current_game.getName());
		if(game_exists == null) { // EDIT 
			error = error + "A game with name " + current_game.getName().trim() + " does not exist.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Game game = Block223Application.getCurrentGame();
		TOGame to = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(), game.getBall().getMinBallSpeedX(), 
				game.getBall().getMinBallSpeedY(), game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(), game.getPaddle().getMinPaddleLength());

		return to;

	}

	//------------------------Karl------------------------//
	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException 
	{
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();

		if (current_game != null) {

			if(!(user instanceof Admin)) {
				error = "Admin privileges are required to access game information.";
				throw new InvalidInputException(error.trim());
			}

			Admin gameAdmin = current_game.getAdmin();

			if(!(user.equals(gameAdmin))) {
				error = "Only the admin who created the game can access its information.";
			}
			if(error.length() > 0) {
				throw new InvalidInputException(error.trim());
			}

			List<TOBlock> result = new ArrayList<TOBlock>();
			List<Block> blocks = current_game.getBlocks();

			for(Block block : blocks) {
				TOBlock to = new TOBlock (block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());
				result.add(to);
			}

			return result;

		}
		else {
			error = "A game must be selected to access its information.";
			throw new InvalidInputException(error.trim());
		}

	}
	//------------------------Karl------------------------//

	// Allan Reuben //
	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException{
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		if(current_game == null) {
			error = "A game must be selected to access its information.";
			throw new InvalidInputException(error.trim());
		}

		if(!(user instanceof Admin)) {
			error = "Admin privileges are required to access game information.";
			throw new InvalidInputException(error.trim());
		}

		Block block = current_game.findBlock(id);
		Admin gameAdmin = current_game.getAdmin();
		UserRole admin = Block223Application.getCurrentUserRole();

		if(gameAdmin != admin) {
			error = "Only the admin who created the game can access its information.";
			throw new InvalidInputException(error.trim());
		}

		if(block == null) {
			error = "The block does not exist.";
			throw new InvalidInputException(error.trim());
		}

		TOBlock to = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());
		return to;
	}

	// Allan Reuben //
	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		Game current_game = Block223Application.getCurrentGame();
		if(current_game == null) {
			error = "A game must be selected to access its information.";
			throw new InvalidInputException(error.trim());
		}
		if(!(user instanceof Admin)) {
			error = "Admin privileges are required to access game information.";
			throw new InvalidInputException(error.trim());
		}
		Admin gameAdmin = current_game.getAdmin();

		if(gameAdmin != user) {
			error = "Only the admin who created the game can access its information.";
			throw new InvalidInputException(error.trim());
		}
		Level current_level = null;
		try{
			current_level = current_game.getLevel(level-1);
		}
		catch(IndexOutOfBoundsException e) {
			error = "Level " + level + " does not exist for the game.";
		}

		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		List<TOGridCell> result = (List<TOGridCell>) new ArrayList<TOGridCell>();

		List<BlockAssignment> blockassignments = current_level.getBlockAssignments();

		for(BlockAssignment blockassignment  : blockassignments) {
			TOGridCell to = new TOGridCell(blockassignment.getGridHorizontalPosition(), blockassignment.getGridVerticalPosition(), blockassignment.getBlock().getId(), blockassignment.getBlock().getRed(), blockassignment.getBlock().getGreen(), blockassignment.getBlock().getBlue(), blockassignment.getBlock().getPoints());
			result.add(to);
		}

		return result;
	}


	public static TOUserMode getUserMode() {
		UserRole userRole = Block223Application.getCurrentUserRole();
		if (userRole == null) {
			TOUserMode to = new TOUserMode(TOUserMode.Mode.None);
			return to;

		} else if (userRole instanceof Admin ) {
			TOUserMode to = new TOUserMode(TOUserMode.Mode.Design);
			return to;
		} else {
			TOUserMode to = new TOUserMode(TOUserMode.Mode.Play);
			return to;
		}

	}


	// ****************************
	// Play Mode Interface 
	// ****************************

	public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
		UserRole player = Block223Application.getCurrentUserRole();
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		if(pgame == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		if(player == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if(player instanceof Admin && pgame.getPlayer() != null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if(player instanceof Player && pgame.getPlayer() == null && pgame.getGame() != null) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		if(player instanceof Admin && player != pgame.getGame().getAdmin()) {
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}
		boolean paused = pgame.getPlayStatus() == PlayStatus.Ready || pgame.getPlayStatus() == PlayStatus.Paused;
		TOCurrentlyPlayedGame result = new TOCurrentlyPlayedGame(pgame.getGame().getName(), paused, pgame.getScore(), pgame.getLives(), pgame.getCurrentLevel(), pgame.getPlayername(), (int)pgame.getCurrentBallX(), (int)pgame.getCurrentBallY(), (int)pgame.getCurrentPaddleLength(), (int)pgame.getCurrentBallX());
		List<PlayedBlockAssignment> blocks = pgame.getBlocks();
		for(PlayedBlockAssignment pblock : blocks) {
			TOCurrentBlock to = new TOCurrentBlock(pblock.getBlock().getRed(), pblock.getBlock().getGreen(), pblock.getBlock().getBlue(), pblock.getBlock().getPoints(), pblock.getX(), pblock.getY(), result);
		}
		return result; 
	}

	public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {

		Block223 block223 = Block223Application.getBlock223();
		UserRole player = Block223Application.getCurrentUserRole();
		if(!(player instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		List<TOPlayableGame> result = new ArrayList<TOPlayableGame>();
		List<Game> games = block223.getGames();
		for(Game game : games) {
			boolean published = game.isPublished();
			if(published) {
				TOPlayableGame to = new TOPlayableGame(game.getName(), -1, 0);
				result.add(to);
			}
		}
		List<PlayedGame> games2 = ((Player)player).getPlayedGames();

		for(PlayedGame game2 : games2) {
			TOPlayableGame to = new TOPlayableGame(game2.getGame().getName(), game2.getId(), game2.getCurrentLevel());
			result.add(to);
		}

		return result;
	}

	// Karl
	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {

		String error = "";
		UserRole user = Block223Application.getCurrentUserRole();
		PlayedGame game = Block223Application.getCurrentPlayableGame();

		if(user == null) {
			error = "Player privileges are required to play a game.";
			System.out.println("Error here: " + error);
			throw new InvalidInputException(error.trim());
		}
		if(game == null) {
			error = "A game must be selected to play it.";
			System.out.println("Error: " + error);
			throw new InvalidInputException(error.trim());
		}
		Block223Application.setCurrentGame(game.getGame());

		if(user instanceof Admin && user != game.getGame().getAdmin()) { // A
			error = "Only the admin of a game can test the game.";
			System.out.println("Error: " + error);
			throw new InvalidInputException(error.trim());
		}	

		if(user instanceof Admin && user == game.getGame().getAdmin() && game.getPlayer() != null) { // Random admin trying to 'test' the game
			error = "Player privileges are required to play a game.";
			System.out.println("Error: " + error);
			throw new InvalidInputException(error.trim());
		}

		if(user instanceof Player && game.getPlayer() == null) { // Player trying to test game
			error = "Admin privileges are required to test a game.";
			System.out.println("Error: " + error);
			throw new InvalidInputException(error.trim());
		}

		//System.out.println(" *********** STARTING THE GAME *********** ");

		game.play();
		ui.takeInputs();

		while (game.getPlayStatus() == PlayStatus.Moving) { 
			String userInputs = ui.takeInputs();

			updatePaddlePosition(userInputs);
			game.move();

			if (userInputs.contains(" ")) { // userInputs.contains("") && game.getPlayStatus() == PlayStatus.Paused
				game.pause();
			}

			try {
				TimeUnit.MILLISECONDS.sleep((long) game.getWaitTime()); // waitTime
				//TimeUnit.MILLISECONDS.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ui.refresh(); 
		}


		if (game.getPlayStatus() == PlayStatus.GameOver) {
			Block223Application.setCurrentPlayableGame(null);
		} else if (game.getPlayer() != null)
		{
			Block223 block223 = Block223Application.getBlock223();
			Block223Persistence.save(block223);

		}
	}

	private static void updatePaddlePosition(String userInputs) {


		PlayedGame game = Block223Application.getCurrentPlayableGame();
		String strArray[] = userInputs.split("");

		for (String s : strArray)
		{
			//current paddle x position
			double curPaddleX = Block223Application.getCurrentPlayableGame().getCurrentPaddleX();

			//paddle length for constraint
			double curPaddleLength = Block223Application.getCurrentPlayableGame().getCurrentPaddleLength();

			if (s.equals("l"))				
			{
				if(curPaddleX > 0){
					if(curPaddleX + PlayedGame.PADDLE_MOVE_LEFT > 0) {
						game.setCurrentPaddleX(game.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_LEFT);
					}else {
						game.setCurrentPaddleX(0);
					}
				}
			}
			else if (s.equals("r"))
			{

				if(curPaddleX < Game.PLAY_AREA_SIDE-curPaddleLength) {
					if(curPaddleX + PlayedGame.PADDLE_MOVE_RIGHT < Game.PLAY_AREA_SIDE-curPaddleLength) {
						game.setCurrentPaddleX(game.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_RIGHT);
					}else {
						game.setCurrentPaddleX(Game.PLAY_AREA_SIDE-curPaddleLength);
					}
				}
			}
			else if (s.equals(" "))
			{
				break;
			}
		}
	}

	public static void publishGame() throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();
		UserRole user = Block223Application.getCurrentUserRole();
		if(user instanceof Player) {
			throw new InvalidInputException("Admin privileges are required to publish a game.");
		}
		if(game == null) {
			throw new InvalidInputException("A game must be selected to publish it.");
		}
		if(game.getAdmin() != user) {
			throw new InvalidInputException("Only the admin who created the game can publish it.");
		}
		List<Block> game_blocks = game.getBlocks();
		if(game_blocks.size() < 1) {
			throw new InvalidInputException("At least one block must be defined for a game to be published.");
		}
		game.setPublished(true);
	}

	public static void testGame(Block223PlayModeInterface ui) throws InvalidInputException {
		Game game = Block223Application.getCurrentGame();
		UserRole user = Block223Application.getCurrentUserRole();
		if(game == null) {
			throw new InvalidInputException("A game must be selected to test it.");
		}

		UserRole admin = Block223Application.getCurrentUserRole();
		if(!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		if(user != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can test it.");
		}
		String username = User.findUsername(admin);
		Block223 block223 = Block223Application.getBlock223();

		PlayedGame pgame = new PlayedGame(username, game, block223);
		pgame.setPlayer(null);

		Block223Application.setCurrentPlayableGame(pgame);

		ui.refresh();
		//startGame(ui);
	}

	public static void selectPlayableGame(String name, int id) throws InvalidInputException {


		Game game = Block223.findGame(name);
		Block223 block223 = Block223Application.getBlock223();
		PlayedGame pgame = null;
		UserRole player = Block223Application.getCurrentUserRole();
		if(!(player instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		String username = User.findUsername(player);
		if(game != null) { 
			try {
				pgame = new PlayedGame(username, game, block223);
				pgame.setPlayer((Player)player);
			} catch (RuntimeException e) {
				System.out.println("Error creating PlayedGame: " + e);
			}

		} else {
			pgame = block223.findPlayableGame(id);
			if(pgame == null) {
				throw new InvalidInputException("The game does not exist.");
			}
			if(pgame.getPlayer() != player) {
				throw new InvalidInputException("Only the player that started a game can continue the game.");
			}
		}

		Block223Application.setCurrentPlayableGame(pgame);

	}

	// Allan Reuben //
	public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
		String error = "";
		UserRole userRole = Block223Application.getCurrentUserRole();
		if(!(userRole instanceof Player)) {
			error = "Player privileges are required to access a game's hall of fame.";
			throw new InvalidInputException(error.trim());
		}
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		if(pgame == null) {
			error = "A game must be selected to view its hall of fame.";
			throw new InvalidInputException(error.trim());
		}
		Game game = pgame.getGame();
		TOHallOfFame result = new TOHallOfFame(game.getName());

		if(start < 1) {
			start = 1;
		}
		if(end > game.numberOfHallOfFameEntries()) {
			end = game.numberOfHallOfFameEntries();
		}
		start = game.numberOfHallOfFameEntries() - start;
		end = game.numberOfHallOfFameEntries() - end;

		for(int i = start; i >= end; i--) {
			new TOHallOfFameEntry(i + 1, game.getHallOfFameEntry(i).getPlayername(), game. getHallOfFameEntry(i).getScore(), result);
		}
		return result;
	}


	public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
		String error = "";
		UserRole userRole = Block223Application.getCurrentUserRole();
		if(!(userRole instanceof Player)) {
			error = "Player privileges are required to access a game's hall of fame.";
			throw new InvalidInputException(error.trim());
		}
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		if(pgame == null) {
			error = "A game must be selected to view its hall of fame.";
			throw new InvalidInputException(error.trim());
		}
		Game game = pgame.getGame();
		TOHallOfFame result = new TOHallOfFame(game.getName());
		HallOfFameEntry mostRecent = game.getMostRecentEntry();
		int indexR = game.indexOfHallOfFameEntry(mostRecent);
		int start = indexR + numberOfEntries/2;
		if(start > game.numberOfHallOfFameEntries() - 1) {
			start = game.numberOfHallOfFameEntries() - 1;
		}
		int end = start - numberOfEntries + 1;
		if(end < 0) {
			end = 0;
		}
		for(int i = start; i >= end; i--) {
			new TOHallOfFameEntry(i + 1, game.getHallOfFameEntry(i).getPlayername(), game. getHallOfFameEntry(i).getScore(), result);
		}
		return result;
	}

}



