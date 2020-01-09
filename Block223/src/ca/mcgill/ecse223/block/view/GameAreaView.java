package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;

public class GameAreaView extends JPanel {
	
	private List<Rectangle2D> blocks = new ArrayList<Rectangle2D>();
	private Ellipse2D ball;
	private Rectangle2D paddle;
	private final int BLOCK_WIDTH = 20; // width of block
	private final int BLOCK_HEIGHT = 20; // height of block
	private final int BALL_DIAMETER = 10; // diameter of ball
	private final double CUR_PADDLE_WIDTH = Block223Application.getCurrentPlayableGame().getCurrentPaddleLength(); // paddle length
	private final int PADDLE_HEIGHT = 5; // paddle height
	
	public GameAreaView() {
		super();
		init();
	}
	
	private void init() {
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(390, 390));
		repaint();
		
	}
	
	private void doDrawing(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		// Show the Blocks
		List<PlayedBlockAssignment> block_list = Block223Application.getCurrentPlayableGame().getBlocks();
		
		for(int i=0; i < block_list.size(); i++) {
			PlayedBlockAssignment block_assignment = block_list.get(i);
			int x = block_assignment.getX();
			int y = block_assignment.getY();
			Block og_block = block_assignment.getBlock();
			Color block_color = new Color(og_block.getRed(), og_block.getGreen(), og_block.getBlue());
			Rectangle2D block = new Rectangle2D.Double(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
				
			g2d.setColor(block_color);
			g2d.fill(block);
			g2d.setColor(Color.BLACK);
			g2d.draw(block);
	
		}
		
		// Paddle
		Double paddleX = Block223Application.getCurrentPlayableGame().getCurrentPaddleX();
		Double paddleY = Block223Application.getCurrentPlayableGame().getCurrentPaddleY();
		Rectangle2D paddle = new Rectangle2D.Double(paddleX, 355, Block223Application.getCurrentPlayableGame().getCurrentPaddleLength(), PADDLE_HEIGHT); // 350
		g2d.fill(paddle);
		g2d.setColor(Color.BLUE);		
		g2d.draw(paddle);

		// Ball
		double ball_x = (this.getWidth()/2) - - (BALL_DIAMETER/2);
		double ball_y = (this.getHeight()/2) - - (BALL_DIAMETER/2);
		
		try {
			ball_x = Block223Application.getCurrentPlayableGame().getCurrentBallX();
			ball_y = Block223Application.getCurrentPlayableGame().getCurrentBallY();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Ellipse2D ball = new Ellipse2D.Double(ball_x - (BALL_DIAMETER/2), ball_y - (BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);
		
		g2d.setColor(Color.BLACK);
		g2d.fill(ball);
		g2d.setColor(Color.BLACK);
		g2d.draw(ball);
		
		
		
	}
	
	//public void setBlocks()
	
	@Override
	public void paintComponent(Graphics g) {
		if (!(Block223Application.getCurrentPlayableGame() == null)) {
			super.paintComponent(g);
			doDrawing(g);
		}
	}

}
