package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;

public class RectangleBlock extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PlayedBlockAssignment> blocks;
	
	public RectangleBlock(List<PlayedBlockAssignment> block_list) {
		this.blocks = block_list;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int width = 25;
		int height = 15;
		for(int i=0; i < blocks.size(); i++) {
			PlayedBlockAssignment block = blocks.get(i);
			int x = block.getX();
			int y = block.getY();
			Block og_block = block.getBlock();
			Color block_color = new Color(og_block.getRed(), og_block.getGreen(), og_block.getBlue());
			
			g2d.setColor(block_color);
			g2d.setBackground(block_color);
			g2d.drawRect(x, y, width, height);
			g2d.fillRect(x, y, width, height);
	
		}
		
		Color paddle_color = new Color(0, 0, 0);
		g2d.setColor(paddle_color);
		g2d.setBackground(paddle_color);
		g2d.drawRect(130, 180, 50, 10);
		g2d.fillRect(130, 180, 50, 10);
		
		Color ball_color = new Color(100, 100, 100);
		int radius = 5;
		g2d.setColor(ball_color);
		g2d.setBackground(ball_color);
		Shape theCircle = new Ellipse2D.Double(100 - radius, 100 - radius, 2.0 * radius, 2.0 * radius);
	    g2d.draw(theCircle);
	    g2d.fill(theCircle);
	    //g2d.fillOval(190 - radius, 190 - radius, (int) 2.0 * radius, (int) 2.0 * radius);

	}
	
	/*
	public static void main(String[] args) {
		Rectangle rects = new Rectangle();
		JFrame frame = new JFrame("Rectangles");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rects);
		frame.setSize(360, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	*/
}
