/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;

// line 56 "../../../../../Block223Persistence.ump"
// line 205 "../../../../../Block223.ump"
public class BlockAssignment implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BlockAssignment Attributes
  private int gridHorizontalPosition;
  private int gridVerticalPosition;
  private int maxNumberOfHorizontalBlocks;
  private int maxNumberOfVerticalBlocks;

  //BlockAssignment Associations
  private Level level;
  private Block block;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BlockAssignment(int aGridHorizontalPosition, int aGridVerticalPosition, Level aLevel, Block aBlock, Game aGame)
  {
    // line 213 "../../../../../Block223.ump"
    if( aGridHorizontalPosition < 1 || aGridHorizontalPosition > 15 ){
       		throw new RuntimeException("The horizontal position must be between 1 and " + this.getMaxNumberOfHorizontalBlocks() + ".");
       	}
    // END OF UMPLE BEFORE INJECTION
    // line 218 "../../../../../Block223.ump"
    if( aGridVerticalPosition < 1 || aGridVerticalPosition > 15 ){
       		throw new RuntimeException("The vertical position must be between 1 and " + this.getMaxNumberOfVerticalBlocks() + ".");
       	}
    // END OF UMPLE BEFORE INJECTION
    gridHorizontalPosition = aGridHorizontalPosition;
    gridVerticalPosition = aGridVerticalPosition;
    maxNumberOfHorizontalBlocks = 15;
    maxNumberOfVerticalBlocks = 15;
    boolean didAddLevel = setLevel(aLevel);
    if (!didAddLevel)
    {
      throw new RuntimeException("Unable to create blockAssignment due to level");
    }
    boolean didAddBlock = setBlock(aBlock);
    if (!didAddBlock)
    {
      throw new RuntimeException("Unable to create blockAssignment due to block");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create blockAssignment due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGridHorizontalPosition(int aGridHorizontalPosition)
  {
    boolean wasSet = false;
    // line 213 "../../../../../Block223.ump"
    if( aGridHorizontalPosition < 1 || aGridHorizontalPosition > 15 ){
       		throw new RuntimeException("The horizontal position must be between 1 and " + this.getMaxNumberOfHorizontalBlocks() + ".");
       	}
    // END OF UMPLE BEFORE INJECTION
    gridHorizontalPosition = aGridHorizontalPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setGridVerticalPosition(int aGridVerticalPosition)
  {
    boolean wasSet = false;
    // line 218 "../../../../../Block223.ump"
    if( aGridVerticalPosition < 1 || aGridVerticalPosition > 15 ){
       		throw new RuntimeException("The vertical position must be between 1 and " + this.getMaxNumberOfVerticalBlocks() + ".");
       	}
    // END OF UMPLE BEFORE INJECTION
    gridVerticalPosition = aGridVerticalPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxNumberOfHorizontalBlocks(int aMaxNumberOfHorizontalBlocks)
  {
    boolean wasSet = false;
    maxNumberOfHorizontalBlocks = aMaxNumberOfHorizontalBlocks;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxNumberOfVerticalBlocks(int aMaxNumberOfVerticalBlocks)
  {
    boolean wasSet = false;
    maxNumberOfVerticalBlocks = aMaxNumberOfVerticalBlocks;
    wasSet = true;
    return wasSet;
  }

  public int getGridHorizontalPosition()
  {
    return gridHorizontalPosition;
  }

  public int getGridVerticalPosition()
  {
    return gridVerticalPosition;
  }

  public int getMaxNumberOfHorizontalBlocks()
  {
    return maxNumberOfHorizontalBlocks;
  }

  public int getMaxNumberOfVerticalBlocks()
  {
    return maxNumberOfVerticalBlocks;
  }
  /* Code from template association_GetOne */
  public Level getLevel()
  {
    return level;
  }
  /* Code from template association_GetOne */
  public Block getBlock()
  {
    return block;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLevel(Level aLevel)
  {
    boolean wasSet = false;
    if (aLevel == null)
    {
      return wasSet;
    }

    Level existingLevel = level;
    level = aLevel;
    if (existingLevel != null && !existingLevel.equals(aLevel))
    {
      existingLevel.removeBlockAssignment(this);
    }
    level.addBlockAssignment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock(Block aBlock)
  {
    boolean wasSet = false;
    if (aBlock == null)
    {
      return wasSet;
    }

    Block existingBlock = block;
    block = aBlock;
    if (existingBlock != null && !existingBlock.equals(aBlock))
    {
      existingBlock.removeBlockAssignment(this);
    }
    block.addBlockAssignment(this);
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
      existingGame.removeBlockAssignment(this);
    }
    game.addBlockAssignment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Level placeholderLevel = level;
    this.level = null;
    if(placeholderLevel != null)
    {
      placeholderLevel.removeBlockAssignment(this);
    }
    Block placeholderBlock = block;
    this.block = null;
    if(placeholderBlock != null)
    {
      placeholderBlock.removeBlockAssignment(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeBlockAssignment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "gridHorizontalPosition" + ":" + getGridHorizontalPosition()+ "," +
            "gridVerticalPosition" + ":" + getGridVerticalPosition()+ "," +
            "maxNumberOfHorizontalBlocks" + ":" + getMaxNumberOfHorizontalBlocks()+ "," +
            "maxNumberOfVerticalBlocks" + ":" + getMaxNumberOfVerticalBlocks()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "level = "+(getLevel()!=null?Integer.toHexString(System.identityHashCode(getLevel())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block = "+(getBlock()!=null?Integer.toHexString(System.identityHashCode(getBlock())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 59 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = -6011556325373584641L ;

  
}