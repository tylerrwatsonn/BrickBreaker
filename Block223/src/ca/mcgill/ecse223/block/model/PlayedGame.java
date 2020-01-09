/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import ca.mcgill.ecse223.block.model.BouncePoint.BounceDirection;
import java.awt.geom.Rectangle2D;
import javafx.scene.shape.Circle;
import java.io.Serializable;
import ca.mcgill.ecse223.block.application.Block223Application;
import java.util.*;

// line 20 "../../../../../Block223PlayMode.ump"
// line 112 "../../../../../Block223Persistence.ump"
// line 1 "../../../../../Block223States.ump"
public class PlayedGame implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  /**
   * at design time, the initial wait time may be adjusted as seen fit
   */
  public static final int INITIAL_WAIT_TIME = 30;
  private static int nextId = 1;
  public static final int NR_LIVES = 3;

  /**
   * the PlayedBall and PlayedPaddle are not in a separate class to avoid the bug in Umple that occurred for the second constructor of Game
   * no direct link to Ball, because the ball can be found by navigating to Game and then Ball
   */
  public static final int BALL_INITIAL_X = Game.PLAY_AREA_SIDE / 2;
  public static final int BALL_INITIAL_Y = Game.PLAY_AREA_SIDE / 2;

  /**
   * no direct link to Paddle, because the paddle can be found by navigating to Game and then Paddle
   * pixels moved when right arrow key is pressed
   */
  public static final int PADDLE_MOVE_RIGHT = 5;

  /**
   * pixels moved when left arrow key is pressed
   */
  public static final int PADDLE_MOVE_LEFT = -5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayedGame Attributes
  private int score;
  private int multiplier;
  private int lives;
  private int currentLevel;
  private double waitTime;
  private String playername;
  private double ballDirectionX;
  private double ballDirectionY;
  private double currentBallX;
  private double currentBallY;
  private double currentPaddleLength;
  private double currentPaddleX;
  private double currentPaddleY;

  //Autounique Attributes
  private int id;

  //PlayedGame State Machines
  public enum PlayStatus { Ready, Moving, Paused, GameOver }
  private PlayStatus playStatus;

  //PlayedGame Associations
  private Player player;
  private Game game;
  private List<PlayedBlockAssignment> blocks;
  private BouncePoint bounce;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayedGame(String aPlayername, Game aGame, Block223 aBlock223)
  {
    // line 62 "../../../../../Block223PlayMode.ump"
    boolean didAddGameResult = setGame(aGame);
          if (!didAddGameResult)
          {
             throw new RuntimeException("Unable to create playedGame due to game");
          }
    // END OF UMPLE BEFORE INJECTION
    score = 0;
    multiplier = 0;
    lives = NR_LIVES;
    currentLevel = 1;
    waitTime = INITIAL_WAIT_TIME;
    playername = aPlayername;
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentBallX();
    resetCurrentBallY();
    currentPaddleLength = getGame().getPaddle().getMaxPaddleLength();
    resetCurrentPaddleX();
    currentPaddleY = 355;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playedGame due to game");
    }
    blocks = new ArrayList<PlayedBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playedGame due to block223");
    }
    setPlayStatus(PlayStatus.Ready);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setMultiplier(int aMultiplier)
  {
    boolean wasSet = false;
    multiplier = aMultiplier;
    wasSet = true;
    return wasSet;
  }

  public boolean setLives(int aLives)
  {
    boolean wasSet = false;
    lives = aLives;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentLevel(int aCurrentLevel)
  {
    boolean wasSet = false;
    currentLevel = aCurrentLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaitTime(double aWaitTime)
  {
    boolean wasSet = false;
    waitTime = aWaitTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayername(String aPlayername)
  {
    boolean wasSet = false;
    playername = aPlayername;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionX(double aBallDirectionX)
  {
    boolean wasSet = false;
    ballDirectionX = aBallDirectionX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionX()
  {
    boolean wasReset = false;
    ballDirectionX = getDefaultBallDirectionX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionY(double aBallDirectionY)
  {
    boolean wasSet = false;
    ballDirectionY = aBallDirectionY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionY()
  {
    boolean wasReset = false;
    ballDirectionY = getDefaultBallDirectionY();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallX(double aCurrentBallX)
  {
    boolean wasSet = false;
    currentBallX = aCurrentBallX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallX()
  {
    boolean wasReset = false;
    currentBallX = getDefaultCurrentBallX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallY(double aCurrentBallY)
  {
    boolean wasSet = false;
    currentBallY = aCurrentBallY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallY()
  {
    boolean wasReset = false;
    currentBallY = getDefaultCurrentBallY();
    wasReset = true;
    return wasReset;
  }

  public boolean setCurrentPaddleLength(double aCurrentPaddleLength)
  {
    boolean wasSet = false;
    currentPaddleLength = aCurrentPaddleLength;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentPaddleX(double aCurrentPaddleX)
  {
    boolean wasSet = false;
    currentPaddleX = aCurrentPaddleX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentPaddleX()
  {
    boolean wasReset = false;
    currentPaddleX = getDefaultCurrentPaddleX();
    wasReset = true;
    return wasReset;
  }

  public int getScore()
  {
    return score;
  }

  public int getMultiplier()
  {
    return multiplier;
  }

  public int getLives()
  {
    return lives;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public double getWaitTime()
  {
    return waitTime;
  }

  /**
   * added here so that it only needs to be determined once
   */
  public String getPlayername()
  {
    return playername;
  }

  /**
   * 0/0 is the top left corner of the play area, i.e., a directionX/Y of 0/1 moves the ball down in a straight line
   */
  public double getBallDirectionX()
  {
    return ballDirectionX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionX()
  {
    return getGame().getBall().getMinBallSpeedX();
  }

  public double getBallDirectionY()
  {
    return ballDirectionY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionY()
  {
    return getGame().getBall().getMinBallSpeedY();
  }

  /**
   * the position of the ball is at the center of the ball
   */
  public double getCurrentBallX()
  {
    return currentBallX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallX()
  {
    return BALL_INITIAL_X;
  }

  public double getCurrentBallY()
  {
    return currentBallY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallY()
  {
    return BALL_INITIAL_Y;
  }

  public double getCurrentPaddleLength()
  {
    return currentPaddleLength;
  }

  /**
   * the position of the paddle is at its top right corner
   */
  public double getCurrentPaddleX()
  {
    return currentPaddleX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentPaddleX()
  {
    return (Game.PLAY_AREA_SIDE - currentPaddleLength) / 2;
  }

  public double getCurrentPaddleY()
  {
    return currentPaddleY;
  }

  public int getId()
  {
    return id;
  }

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean play()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Ready:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      case Paused:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pause()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        setPlayStatus(PlayStatus.Paused);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        if (hitPaddle())
        {
        // line 15 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBoundsAndLastLife())
        {
        // line 16 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds())
        {
        // line 17 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.Paused);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlockAndLastLevel())
        {
        // line 18 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlock())
        {
        // line 19 "../../../../../Block223States.ump"
          doHitBlockNextLevel();
          setPlayStatus(PlayStatus.Ready);
          wasEventProcessed = true;
          break;
        }
        if (hitBlock())
        {
        // line 20 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (hitWall())
        {
        // line 21 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        // line 22 "../../../../../Block223States.ump"
        doHitNothingAndNotOutOfBounds();
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayStatus(PlayStatus aPlayStatus)
  {
    playStatus = aPlayStatus;

    // entry actions and do activities
    switch(playStatus)
    {
      case Ready:
        // line 10 "../../../../../Block223States.ump"
        doSetup();
        break;
      case GameOver:
        // line 28 "../../../../../Block223States.ump"
        doGameOver();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public PlayedBlockAssignment getBlock(int index)
  {
    PlayedBlockAssignment aBlock = blocks.get(index);
    return aBlock;
  }

  public List<PlayedBlockAssignment> getBlocks()
  {
    List<PlayedBlockAssignment> newBlocks = Collections.unmodifiableList(blocks);
    return newBlocks;
  }

  public int numberOfBlocks()
  {
    int number = blocks.size();
    return number;
  }

  public boolean hasBlocks()
  {
    boolean has = blocks.size() > 0;
    return has;
  }

  public int indexOfBlock(PlayedBlockAssignment aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public BouncePoint getBounce()
  {
    return bounce;
  }

  public boolean hasBounce()
  {
    boolean has = bounce != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlayedGame(this);
    }
    if (aPlayer != null)
    {
      aPlayer.addPlayedGame(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removePlayedGame(this);
    }
    game.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayedBlockAssignment addBlock(int aX, int aY, Block aBlock)
  {
    return new PlayedBlockAssignment(aX, aY, aBlock, this);
  }

  public boolean addBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    PlayedGame existingPlayedGame = aBlock.getPlayedGame();
    boolean isNewPlayedGame = existingPlayedGame != null && !this.equals(existingPlayedGame);
    if (isNewPlayedGame)
    {
      aBlock.setPlayedGame(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a playedGame
    if (!this.equals(aBlock.getPlayedGame()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(PlayedBlockAssignment aBlock, int index)
  {  
    boolean wasAdded = false;
    if(addBlock(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAt(PlayedBlockAssignment aBlock, int index)
  {
    boolean wasAdded = false;
    if(blocks.contains(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAt(aBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setBounce(BouncePoint aNewBounce)
  {
    boolean wasSet = false;
    bounce = aNewBounce;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock223(Block223 aBlock223)
  {
    boolean wasSet = false;
    if (aBlock223 == null)
    {
      return wasSet;
    }

    Block223 existingBlock223 = block223;
    block223 = aBlock223;
    if (existingBlock223 != null && !existingBlock223.equals(aBlock223))
    {
      existingBlock223.removePlayedGame(this);
    }
    block223.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      Player placeholderPlayer = player;
      this.player = null;
      placeholderPlayer.removePlayedGame(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayedGame(this);
    }
    while (blocks.size() > 0)
    {
      PlayedBlockAssignment aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }
    
    bounce = null;
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayedGame(this);
    }
  }


  /**
   * Guards
   */
  // line 35 "../../../../../Block223States.ump"
   private boolean hitPaddle(){
    BouncePoint bp = calculateBouncePointPaddle();
		setBounce(bp);
		if(bp != null) {
			setMultiplier(0);
		}
		return bp != null;
  }

  // line 44 "../../../../../Block223States.ump"
   private boolean hitWall(){
    BouncePoint bp = calculateBouncePointWall();
		setBounce(bp);
		return bp != null;
  }

  // line 50 "../../../../../Block223States.ump"
   private void doHitPaddleOrWall(){
    bounceBall();
  }

  // line 54 "../../../../../Block223States.ump"
   private boolean isOutOfBoundsAndLastLife(){
    boolean outOfBounds = false;
		if(lives == 1) {
			outOfBounds = isBallOutOfBounds();
		}
		return outOfBounds;
  }

  // line 62 "../../../../../Block223States.ump"
   private boolean isOutOfBounds(){
    boolean outOfBounds = isBallOutOfBounds();
		return outOfBounds;
  }

  // line 79 "../../../../../Block223States.ump"
   public void doOutOfBounds(){
    setLives(lives-1);
		setMultiplier(0);
		resetCurrentBallX();
		resetCurrentBallY();
		resetBallDirectionX();
		resetBallDirectionY();
		resetCurrentPaddleX();
  }

  // line 89 "../../../../../Block223States.ump"
   private void doGameOver(){
    Block223 block223 = Block223Application.getBlock223();
		Player p = getPlayer();
		if(p != null) {
			HallOfFameEntry hof = new HallOfFameEntry(score,playername,p,game,block223);			
			game.setMostRecentEntry(hof);
		}
		delete();
  }

  // line 99 "../../../../../Block223States.ump"
   private boolean isBallOutOfBounds(){
    if(getCurrentBallX() < 0 || getCurrentBallX() > 390) {
			return true;
		}
		if(getCurrentBallY() < 0 || getCurrentBallY() > 390) {
			return true;
		}
		int radius = Ball.BALL_DIAMETER / 2;		
		Rectangle2D rect = new Rectangle2D.Double(0, Game.PLAY_AREA_SIDE-radius, Game.PLAY_AREA_SIDE, radius);
		return rect.intersectsLine(currentBallX, currentBallY, currentBallX+ballDirectionX, currentBallY+ballDirectionY);
  }

  // line 111 "../../../../../Block223States.ump"
   private boolean hitLastBlockAndLastLevel(){
    Game game = this.getGame();
    int nrLevels = game.numberOfLevels();
    this.setBounce(null);
    if (nrLevels == currentLevel) {
    	int nrBlocks = this.numberOfBlocks();
    	if (nrBlocks == 1) {
    		PlayedBlockAssignment block = this.getBlock(0);
    		BouncePoint bp = calculateBouncePointBlock(block); 
    		
    		setBounce(bp);
    		return bp != null;
  			
    	}
    }
    return false;
  }

  // line 129 "../../../../../Block223States.ump"
   private boolean hitLastBlock(){
    int nrBlocks = numberOfBlocks();
    setBounce(null);
    if (nrBlocks == 1) {
    	PlayedBlockAssignment block = getBlock(0);
    	BouncePoint bp = calculateBouncePointBlock(block);
    	setBounce(bp);
    	return bp != null;
    }
    return false;
  }

  // line 141 "../../../../../Block223States.ump"
   private boolean hitBlock(){
    int nrBlocks = numberOfBlocks();
    setBounce(null);
    for (int i = 0; i < nrBlocks; i++) {
    	PlayedBlockAssignment block = getBlock(i);
    	BouncePoint bp = calculateBouncePointBlock(block);
    	BouncePoint bounce = getBounce();
    	boolean closer = isCloser(bp,bounce);
    	if (closer) {
    		setBounce(bp);
    	}
    }
    return (getBounce() != null);
  }


  /**
   * Actions
   */
  // line 158 "../../../../../Block223States.ump"
   private void doSetup(){
    resetCurrentBallX();
			resetCurrentBallY();
			resetBallDirectionX();
		   	resetBallDirectionY();
		   	resetCurrentPaddleX();
		   
		   game = getGame();
		   Level level = game.getLevel(currentLevel - 1);
		   List<BlockAssignment> assignments = level.getBlockAssignments();
		   
		for (BlockAssignment blockAss : assignments)
		{
			PlayedBlockAssignment pblock = new PlayedBlockAssignment(Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * 
				(blockAss.getGridHorizontalPosition()-1), Game.WALL_PADDING +
				(Block.SIZE + Game.ROW_PADDING) *
				(blockAss.getGridVerticalPosition()-1), blockAss.getBlock(), this);
		}
	
		while (numberOfBlocks() < game.getNrBlocksPerLevel())
		{
			Random rand = new Random();
			int i = rand.nextInt(15) + 1; //Random integer from 1 to 15;         Y
			int j = rand.nextInt(15) + 1; //Random integer from 1 to 15;         X
			boolean canAddBlock = false; //True will add block, false will not;	
			boolean found = false;
			int currX = 0; //Block coordinate X ---- j
			int currY = 0; //Block coordinate Y ---- i
			   
		while (i < 16)
		{
			while (j < 16)
			{
				//Checks if there is an assignment with a block at the same position
				found = false;
				for (PlayedBlockAssignment blockAss : blocks)
				{
					if ((blockAss.getX() == Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * 
							(j-1)) && blockAss.getY() == Game.WALL_PADDING +
									(Block.SIZE + Game.ROW_PADDING) *
									(i-1)) {
						found = true;
						break;
					}
				}

				//In case it doesnt find a block
				if (found == false){
					canAddBlock = true;
					currX = j;
					currY = i;
					break;
				}

				j++;
			}

			if (canAddBlock == true) break;
			if (i == 15){ //If doesnt find, starts from (1, 1)
				i = 0;
				j = 1;
			}

			i++;
		}
		if (canAddBlock == true){
			//PlayedBlockAssignment pblock = new PlayedBlockAssignment (currX, currY, game.getRandomBlock(), this);
			PlayedBlockAssignment pblock = new PlayedBlockAssignment(Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * 
					(currX-1), Game.WALL_PADDING +
					(Block.SIZE + Game.ROW_PADDING) *
					(currY-1), game.getRandomBlock(), this);
		}
	}
  }

  // line 234 "../../../../../Block223States.ump"
   private void doHitBlock(){
    score = getScore();
   	   multiplier = getMultiplier();
   	   multiplier += 1;
   	   setMultiplier(multiplier);
	   bounce = getBounce();
	   PlayedBlockAssignment pBlock = bounce.getHitBlock();
	   Block block = pBlock.getBlock();
	   int bScore = block.getPoints();
	   setScore(score + bScore*multiplier);
	   pBlock.delete();
	   bounceBall();
  }

  // line 248 "../../../../../Block223States.ump"
   private void doHitBlockNextLevel(){
    doHitBlock();
	   int level = getCurrentLevel();
	   setCurrentLevel(level +1);
	   setCurrentPaddleLength(getGame().getPaddle().getMaxPaddleLength() - 
			   (getGame().getPaddle().getMaxPaddleLength() - getGame().getPaddle().getMinPaddleLength())
			   /(getGame().numberOfLevels()-1)*(getCurrentLevel() -1) );
	   setWaitTime(INITIAL_WAIT_TIME*((int) getGame().getBall().getBallSpeedIncreaseFactor()^(getCurrentLevel()-1)));
	   //not sure about the cast to integer but it gives an error otherwise
  }

  // line 259 "../../../../../Block223States.ump"
   private void doHitNothingAndNotOutOfBounds(){
    double x = getCurrentBallX();
		double y = getCurrentBallY();
		double dx = getBallDirectionX();
		double dy = getBallDirectionY();
		setCurrentBallX((x + dx));
		setCurrentBallY((y + dy));
  }


  /**
   * new ones
   */
  // line 272 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointBlock(PlayedBlockAssignment block){
    BouncePoint answer = null;
		int radius = Ball.BALL_DIAMETER/2;
		//current and next x and y position of the ball
		double x1 = this.getCurrentBallX();
		double y1 = this.getCurrentBallY();
		double x2 = x1 + this.getBallDirectionX();
		double y2 = y1 + this.getBallDirectionY();
		
		Rectangle2D.Double rect = new Rectangle2D.Double(block.getX()-radius, block.getY() - radius, Block.SIZE + 2*radius, Block.SIZE + 2*radius);
		//8 rectangles that surround the block
		Rectangle2D.Double rectA = new Rectangle2D.Double(block.getX(), block.getY() - radius, Block.SIZE, radius);
		Rectangle2D.Double rectB = new Rectangle2D.Double(block.getX() - radius, block.getY(), radius, Block.SIZE);
		Rectangle2D.Double rectC = new Rectangle2D.Double(block.getX() + Block.SIZE, block.getY(), radius, Block.SIZE);
		Rectangle2D.Double rectD = new Rectangle2D.Double(block.getX(), block.getY() + Block.SIZE, Block.SIZE, radius);
		Rectangle2D.Double rectE = new Rectangle2D.Double(block.getX() - radius, block.getY() - radius, radius, radius);
		Rectangle2D.Double rectF = new Rectangle2D.Double(block.getX() + Block.SIZE, block.getY() - radius, radius, radius);
		Rectangle2D.Double rectG = new Rectangle2D.Double(block.getX() - radius, block.getY() + Block.SIZE, radius, radius);
		Rectangle2D.Double rectH = new Rectangle2D.Double(block.getX() + Block.SIZE, block.getY() + Block.SIZE, radius, radius);
		//booleans to see if the line of the ball intersects any rectangle
		boolean inA = rectA.intersectsLine(x1, y1, x2, y2);
		boolean inB = rectB.intersectsLine(x1, y1, x2, y2);
		boolean inC = rectC.intersectsLine(x1, y1, x2, y2);
		boolean inD = rectD.intersectsLine(x1, y1, x2, y2);
		
		boolean inE = rectE.intersectsLine(x1, y1, x2, y2);
		boolean inF = rectF.intersectsLine(x1, y1, x2, y2);
		boolean inG = rectG.intersectsLine(x1, y1, x2, y2);
		boolean inH = rectH.intersectsLine(x1, y1, x2, y2);
		if (!inA && !inB && !inC && !inD && !inE && !inF && !inG && !inH) {
			//if the ball does not intersect a rectangle it does not hit the block.
			return answer;
		}
		if (inA) {
			boolean onA = rect.contains(x2,y2);
			if (onA) {
				//double bouncePointXA = ((double) (block.getY() - radius - y1)*(x2-x1)/(y2-y1) + x1);
				answer =  new BouncePoint(x1, y1, BouncePoint.BounceDirection.FLIP_Y);
				answer.setHitBlock(block);
				return answer;
			}
		}
		if (inB) {
			//double bouncePointYB = y1 + ((double) (y2-y1)*(block.getX()- x1 - radius)/(x2-x1));
			boolean onB = rect.contains(x2,y2);
			if (onB) {
				answer = new BouncePoint(x1,y1, BounceDirection.FLIP_X);
				answer.setHitBlock(block);
				return answer;
			}
			
		}
		if (inC ) {
			boolean onC = rect.contains(x2, y2);
			if (onC ) {
				answer = new BouncePoint(x1,y1, BounceDirection.FLIP_X);
				answer.setHitBlock(block);
				return answer;
			}
		}
		if (inD) {
			boolean onD = rect.contains(x2, y2);
			if (onD && y1>y2) {
				answer = new BouncePoint(x1,y1, BounceDirection.FLIP_Y);
				answer.setHitBlock(block);
				return answer;
			}
		}
		if (inE) {

			Circle circleE = new Circle(block.getX(), block.getY(), radius);
			boolean onE = circleE.contains(x2, y2);
			if (onE) {
				if (x2 > x1) {
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_X);
					answer.setHitBlock(block);
					return answer;
				}else {
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_Y);
					answer.setHitBlock(block);
					return answer;
				}
			}
		}
		if (inF) {
			
			//Circle circleF = new Circle(block.getX() + Block.SIZE, block.getY(), radius);
			//boolean onF = circleF.contains(x2, y2);
			
				if (x1 > x2) {
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_X);
					answer.setHitBlock(block);
					return answer;
				}else {
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_Y);
					answer.setHitBlock(block);
					return answer;
				}
			
		}
		if (inG) {
			Circle circleG = new Circle(block.getX(), block.getY() + Block.SIZE, radius);
			boolean onG = circleG.contains(x2, y2);
			if (onG) {
				if (x2 > x1) {
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_X);
					answer.setHitBlock(block);
					return answer;
				}else {
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_Y);
					answer.setHitBlock(block);
					return answer;

				}
			}
		}
		if (inH) {
			Circle circleH = new Circle(block.getX() + Block.SIZE, block.getY() + Block.SIZE, radius);
			boolean onH = circleH.contains(x2, y2);
			if (onH) {
				if (x1 > x2) {
					//double bouncePointYH1 = y1 + ((double) )
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_X);
					answer.setHitBlock(block);
					return answer;
				}else {
					answer = new BouncePoint(x1,y1, BounceDirection.FLIP_Y);
					answer.setHitBlock(block);
					return answer;
				}
			}
		}
		return answer;
  }

  // line 407 "../../../../../Block223States.ump"
   private boolean isCloser(BouncePoint bp, BouncePoint bounce2){
    if (bp == null) {
			   return false;
		   }
	    if (bounce2 == null) {
			   return true;
		   }

		   double difference1 = Math.abs((bp.getX() - this.getCurrentBallX())) + Math.abs((bp.getY() - this.getCurrentBallY()));
		   double difference2 = Math.abs((bounce2.getX() - this.getCurrentBallX())) + Math.abs((bounce2.getY() - this.getCurrentBallY()));
		   if (difference1 > difference2) {
			   return false;
		   }
		   return true;
  }

  // line 423 "../../../../../Block223States.ump"
   public static  List<Point> getCircleLineIntersectionPoint(Point pointA, Point pointB, Point center, double radius){
    //obtained from online
	   
	   double baX = pointB.x - pointA.x;
       double baY = pointB.y - pointA.y;
       double caX = center.x - pointA.x;
       double caY = center.y - pointA.y;
       

       double a = baX * baX + baY * baY;
       double bBy2 = baX * caX + baY * caY;
       double c = caX * caX + caY * caY - radius * radius;

       double pBy2 = bBy2 / a;
       double q = c / a;

       double disc = pBy2 * pBy2 - q;
       if (disc < 0) {
           return Collections.emptyList();
       }
       // if disc == 0 ... dealt with later
       double tmpSqrt = Math.sqrt(disc);
       double abScalingFactor1 = -pBy2 + tmpSqrt;
       double abScalingFactor2 = -pBy2 - tmpSqrt;

       Point p1 = new Point(pointA.x - baX * abScalingFactor1, pointA.y
               - baY * abScalingFactor1);
       if (disc == 0) { // abScalingFactor1 == abScalingFactor2
           return Collections.singletonList(p1);
       }
       Point p2 = new Point(pointA.x - baX * abScalingFactor2, pointA.y
               - baY * abScalingFactor2);
       

       return Arrays.asList(p1, p2);
  }

  // line 468 "../../../../../Block223States.ump"
   public BouncePoint calculateBouncePointPaddle(){
    currentPaddleX = getCurrentPaddleX();
			Rectangle2D paddleA = new Rectangle2D.Double(currentPaddleX, currentPaddleY - 5, currentPaddleLength, 5);
			Rectangle2D paddleB = new Rectangle2D.Double(currentPaddleX - 5, currentPaddleY, 5, 5);
			Rectangle2D paddleC = new Rectangle2D.Double(currentPaddleX + currentPaddleLength, currentPaddleY, 5, 5);

			boolean intersectsA = paddleA.intersectsLine(currentBallX, currentBallY, currentBallX + ballDirectionX, currentBallY + ballDirectionY);
			boolean intersectsB = paddleB.intersectsLine(currentBallX, currentBallY, currentBallX + ballDirectionX, currentBallY + ballDirectionY);
			boolean intersectsC = paddleC.intersectsLine(currentBallX, currentBallY, currentBallX + ballDirectionX, currentBallY + ballDirectionY);
			//boolean intersectsE = Math.pow((Math.pow(((currentBallX + ballDirectionX) - currentPaddleX), 2) + Math.pow((currentBallY + ballDirectionY) - currentPaddleY, 2)), 0.5) <= 5;
			Point A = new Point(currentBallX, currentBallY);
			Point B = new Point(currentBallX + ballDirectionX, currentBallY+ballDirectionY);
			if(B.y < (currentPaddleY - 5)) return null;
			
			Point centerE = new Point(currentPaddleX, currentPaddleY);
			Point centerF = new Point(currentPaddleX+currentPaddleLength, currentPaddleY);
			boolean intersectsE = getCircleLineIntersectionPoint(A, B, centerE, 5).size() > 0;
			boolean intersectsF = getCircleLineIntersectionPoint(A, B, centerF, 5).size() > 0;
			//boolean intersectsF = Math.pow((Math.pow(((currentBallX + ballDirectionX) - (currentPaddleX + currentPaddleLength)), 2) + Math.pow((currentBallY + ballDirectionY) - currentPaddleY, 2)), 0.5) <= 5;
			if(ballDirectionX > 0) {
				if(intersectsB) {
					double bouncePointY = ((double) (currentPaddleX - 5 - currentBallX)*ballDirectionY)/ballDirectionX + currentBallY;
					return new BouncePoint(currentPaddleX - 5, bouncePointY, BouncePoint.BounceDirection.FLIP_X);
				}
				if(intersectsE) {
					List<Point> result = getCircleLineIntersectionPoint(A, B, centerE, 5);
					for(Point point: result) {
						if(point.x < currentPaddleX && point.y < currentPaddleY) {
							return new BouncePoint(point.x, point.y, BouncePoint.BounceDirection.FLIP_X);
						}
					}
					
				}

				if(intersectsA) {
					double bouncePointX = ((double) (currentPaddleY - 5 - currentBallY)*ballDirectionX)/ballDirectionY + currentBallX;
					return new BouncePoint(bouncePointX, currentPaddleY - 5, BouncePoint.BounceDirection.FLIP_Y);
				}

				if(intersectsF) {
					List<Point> result = getCircleLineIntersectionPoint(A, B, centerF, 5);
					for(Point point: result) {
					if(point.x > (currentPaddleX + currentPaddleLength) && point.y < currentPaddleY) {
						return new BouncePoint(point.x, point.y, BouncePoint.BounceDirection.FLIP_Y);
					}
					}	
				}

				

			}
			else {
				if(intersectsC) {
					double bouncePointY = ((double) (currentPaddleX +currentPaddleLength+ 5 - currentBallX)*ballDirectionY)/ballDirectionX + currentBallY;
					return new BouncePoint(currentPaddleX + currentPaddleLength + 5, bouncePointY, BouncePoint.BounceDirection.FLIP_X);
				}
				if(intersectsF) {
					List<Point> result = getCircleLineIntersectionPoint(A, B, centerF, 5);
					for(Point point: result) {
						if(point.x > (currentPaddleX + currentPaddleLength) && point.y < currentPaddleY) {
							return new BouncePoint(point.x, point.y, BouncePoint.BounceDirection.FLIP_X);
						}
					}
				}

				if(intersectsA) {
					double bouncePointX = ((double) (currentPaddleY - 5 - currentBallY)*ballDirectionX)/ballDirectionY + currentBallX;
						return new BouncePoint(bouncePointX, currentPaddleY - 5, BouncePoint.BounceDirection.FLIP_Y);
				}

				if(intersectsE) {
					List<Point> result = getCircleLineIntersectionPoint(A, B, centerE, 5);
					for(Point point: result) {
						if(point.x < currentPaddleX && point.y < currentPaddleY) {
							return new BouncePoint(point.x, point.y, BouncePoint.BounceDirection.FLIP_Y);
						}
					}
				}

			}
		
		return null;
  }


  /**
   * line 468 "../../../../../Block223States.ump"
   */
  // line 553 "../../../../../Block223States.ump"
   public BouncePoint calculateBouncePointWall(){
    if((currentBallY + ballDirectionY) <= 5) {
			double bouncePointX = ((double) (5 - currentBallY)*ballDirectionX)/ballDirectionY + currentBallX;
			if(bouncePointX > 5 && bouncePointX < 385) {
				
				return new BouncePoint(bouncePointX, 5, BouncePoint.BounceDirection.FLIP_Y);
			} else if(bouncePointX == 5) {
				return new BouncePoint(bouncePointX, 5, BouncePoint.BounceDirection.FLIP_BOTH);
			} else if(bouncePointX == 385) {
				return new BouncePoint(bouncePointX, 5, BouncePoint.BounceDirection.FLIP_BOTH);
			}
		}
    	else if((currentBallX + ballDirectionX) <= 5) {
			double bouncePointY = ((double) (5 - currentBallX)*ballDirectionY)/ballDirectionX + currentBallY;
			if(bouncePointY <= 385) {
				return new BouncePoint(5, bouncePointY, BouncePoint.BounceDirection.FLIP_X);
			}
		}

		else if((currentBallX + ballDirectionX) >= 385) {
			
			double bouncePointY = ((double) (385 - currentBallX)*ballDirectionY)/ballDirectionX + currentBallY;
			if(bouncePointY <= 385) {
				return new BouncePoint(385, bouncePointY, BouncePoint.BounceDirection.FLIP_X);
			}
		}
		return null;
  }


  /**
   * line 496 "../../../../../Block223States.ump"
   */
  // line 583 "../../../../../Block223States.ump"
   public void bounceBall(){
    BouncePoint bp = getBounce();
		if(bp.getDirection() == BouncePoint.BounceDirection.FLIP_X) {
			double incomingDistanceX = bp.getX() - currentBallX;
			double remainingDistanceX = ballDirectionX - incomingDistanceX;

			if(remainingDistanceX == 0) {
				setCurrentBallX(bp.getX());
				setCurrentBallY(bp.getY());
			}

			else {
				double newBallDirectionX = (-1)*ballDirectionX;
				if(ballDirectionY == 0) {
					double newBallDirectionY = ballDirectionY + 0.1*Math.abs(ballDirectionX);
					setBallDirectionY(newBallDirectionY);
					return;
				}
				double newBallDirectionY = ballDirectionY + Math.signum(ballDirectionY)*0.1*Math.abs(ballDirectionX);
				setCurrentBallY(bp.getY() + remainingDistanceX/ballDirectionX * newBallDirectionY);
				setCurrentBallX(bp.getX() + remainingDistanceX/ballDirectionX * newBallDirectionX);
				setBallDirectionY(newBallDirectionY);
				setBallDirectionX(newBallDirectionX);
			}
		}
		else if(bp.getDirection() == BouncePoint.BounceDirection.FLIP_Y) {
			double incomingDistanceY = bp.getY() - currentBallY;
			double remainingDistanceY = ballDirectionY - incomingDistanceY;
			if(remainingDistanceY == 0) {
				setCurrentBallX(bp.getX());
				setCurrentBallY(bp.getY());
			}

			else {
				double newBallDirectionY = (-1)*ballDirectionY;
				if(ballDirectionX == 0) {
					double newBallDirectionX = ballDirectionX + 0.1*Math.abs(ballDirectionY);
					setBallDirectionX(newBallDirectionX);
					return;
				}
				double newBallDirectionX = ballDirectionX + Math.signum(ballDirectionX)*0.1*Math.abs(ballDirectionY);
				setCurrentBallY(bp.getY() + remainingDistanceY/ballDirectionY * newBallDirectionY);
				setCurrentBallX(bp.getX() + remainingDistanceY/ballDirectionY * newBallDirectionX);
				setBallDirectionY(newBallDirectionY);
				setBallDirectionX(newBallDirectionX);
			}
		}
		else {
			double incomingDistanceY = bp.getY() - currentBallY;
			double remainingDistanceY = ballDirectionY - incomingDistanceY;
			double newBallDirectionY = (-1)*ballDirectionY;

			double incomingDistanceX = bp.getX() - currentBallX;
			double remainingDistanceX = ballDirectionX - incomingDistanceX;
			double newBallDirectionX = (-1)*ballDirectionX;

			setCurrentBallY(bp.getY() + remainingDistanceY/ballDirectionY * newBallDirectionY);
			setCurrentBallX(bp.getX() + remainingDistanceX/ballDirectionX * newBallDirectionX);
			setBallDirectionY(newBallDirectionY);
			setBallDirectionX(newBallDirectionX);
		}
		
		// Control ball speed
		
		if (ballDirectionX >= 10 || ballDirectionY >= 10) {
			ballDirectionX /= 10;
			ballDirectionY /= 10;
		}
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "score" + ":" + getScore()+ "," +
            "multiplier" + ":" + getMultiplier()+ "," +
            "lives" + ":" + getLives()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "waitTime" + ":" + getWaitTime()+ "," +
            "playername" + ":" + getPlayername()+ "," +
            "ballDirectionX" + ":" + getBallDirectionX()+ "," +
            "ballDirectionY" + ":" + getBallDirectionY()+ "," +
            "currentBallX" + ":" + getCurrentBallX()+ "," +
            "currentBallY" + ":" + getCurrentBallY()+ "," +
            "currentPaddleLength" + ":" + getCurrentPaddleLength()+ "," +
            "currentPaddleX" + ":" + getCurrentPaddleX()+ "," +
            "currentPaddleY" + ":" + getCurrentPaddleY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bounce = "+(getBounce()!=null?Integer.toHexString(System.identityHashCode(getBounce())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 115 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 8597675110221231714L ;

// line 459 "../../../../../Block223States.ump"
  static class Point 
  {
    double x, y;

       public Point(double x, double y) { this.x = x; this.y = y; }
  }

  
}