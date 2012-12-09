package bab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * NOTES:
 * If all lines around a box are selected (depressed/colored/disabled), set the color
 * of that box to that of the current player and give a point to that player
 */

public class BAB_Game {
	
	
	// dimensions of lines
	private final int LINE_LENGTH = 28;
	private final int LINE_WIDTH = 12;
	private final int BOX_WIDTH = 28;
	
	// game colors
	private final Color p0_box = new Color(240, 80, 0);
	private final Color p1_box = new Color(0, 160, 232);
	private final Color default_box = new Color(20, 20, 20);
	
	private final Color p0_line = new Color(150, 50, 0);
	private final Color p1_line = new Color(0, 100, 145);
	
	// 2-player scores
	private int p0_score = 0;
	private int p1_score = 0;
	
	/**
	 * holds all components of the game (dots, lines, and boxes)
	 */
	private ArrayList<JComponent> gameBoard = new ArrayList<JComponent>();
	
	/**
	 * the total number of moves taken (always increases)
	 */
	private int move_count = 0;
	
	/**
	 * the current player move (increases contingent upon extra_turn)
	 */
	private int turn_count = 0;
	private boolean extra_turn = false;
	
	private Color background;
	
	private int numRows;
	private int numCols;
	
	// the number of available lines on the board
	private int numMoves;
	
	public BAB_Game(Color background, int numBoxes_X, int numBoxes_Y) {
		
		this.background = background;
		
		this.numRows = (numBoxes_X * 2) + 1;
		this.numCols = (numBoxes_Y * 2) + 1;
		this.numMoves = ( (numRows * numCols) / 2);
	}
	

	public void addComponentsToPane(Container pane)
	{
		// obtain dot image
		BufferedImage dot_icon = null;
		BufferedImage line_unclaimed_h = null;
		BufferedImage line_unclaimed_v = null;
		
		try {
			dot_icon = ImageIO.read(new File("images/box_dot_8.gif"));
			line_unclaimed_h = ImageIO.read(new File("images/line_unclaimed_h.gif"));
			line_unclaimed_v = ImageIO.read(new File("images/line_unclaimed_v.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 *  boxes the player claims
		 */
		JPanel box;
		
		/**
		 *  dots at each corner of the box (an image)
		 */
		JLabel dot;

		/**
		 *  lines connecting dots (a button)
		 */
		JButton line;
		
		// initialize GridBag layout and constraints
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// something related to width/height of lines?
		c.ipadx = 2;
		c.ipady = 2;
		
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++) {
				
				// GridBag row/column constraint setting
				c.gridy = i;
				c.gridx = j;
				
				// dot row
				if (i % 2 == 0)
				{
					//dot
					if (j % 2 == 0) {
						dot = new JLabel(new ImageIcon(dot_icon));
						pane.add(dot, c);
						gameBoard.add(dot);
					}
					
					// horizontal line
					else {
						line = new JButton(new ImageIcon(line_unclaimed_h));
						line.setPreferredSize(new Dimension(LINE_LENGTH, LINE_WIDTH));
						line.setBorder(BorderFactory.createLineBorder(background, 3));
						line.addActionListener(new ButtonListener());
						pane.add(line, c);
						gameBoard.add(line);
					}
				}
				
				// box row
				else
				{
					// vertical line
					if (j % 2 == 0) {
						line = new JButton(new ImageIcon(line_unclaimed_v));
						line.setPreferredSize(new Dimension(LINE_WIDTH, LINE_LENGTH));
						line.setBorder(BorderFactory.createLineBorder(background, 3));
						line.addActionListener(new ButtonListener());
						pane.add(line, c);
						gameBoard.add(line);

						
					}
					
					// box
					else {
						box = new JPanel();
						box.setPreferredSize(new Dimension(BOX_WIDTH, BOX_WIDTH));
						box.setBorder(BorderFactory.createLineBorder(background, 3));
						box.setBackground(default_box);
						pane.add(box, c);
						gameBoard.add(box);
					}
				}
			}
		}
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// get the JButton line that the current player clicked on
			JButton button = (JButton) e.getSource();
			
			// claim the line in the color of the current player and disable it
			button.setBackground(getPlayerLineColor());
			button.setEnabled(false);
			button.setOpaque(true);
			button.setIcon(null);
			button.revalidate();
			
			// check the board state after each move
			checkGameBoard();
			
			if (getPlayer() == 0)	
				playSound("sounds/p0_beep.wav");
			else
				playSound("sounds/p1_beep.wav");

			// move to the next turn (unless player has extra turn)
			if (!extra_turn)
				turn_count++;
			else
				extra_turn = false;
			
			// increase the move total regardless
			move_count++;
			
			// check to see if the game has ended
			if ( !(move_count < numMoves) ) {
				JOptionPane.showMessageDialog(null, determineWinner());
			}
		}
	}
	
	// determine who current player is
	private int getPlayer() { return (turn_count % 2); }
	
	// determine box color of current player
	private Color getPlayerBoxColor() { return (getPlayer() == 0) ? p0_box : p1_box; }
	
	// determine line color of current player
	private Color getPlayerLineColor() { return (getPlayer() == 0) ? p0_line : p1_line; }
	
	// increase the score of the current player
	private void scorePlayer() { if (getPlayer() == 0) { p0_score++; } else { p1_score++; } 
	
		// TODO Error checking print statements 
//		System.out.println("P0: " + p0_score);
//		System.out.println("P1: " + p1_score);
//		System.out.println("--------");
	}
	
	// determine which player has the higher score (or a tie)
	private String determineWinner()
	{
		if (p0_score > p1_score) {
			return String.format("Player 1: %d\nPlayer 2: %d\n\nRed Player 1 Wins!", p0_score, p1_score);
		} else if (p0_score < p1_score) {
			return String.format("Player 1: %d\nPlayer 2: %d\n\nBlue Player 2 Wins!", p0_score, p1_score);
		} else {
			return String.format("Player 1: %d\nPlayer 2: %d\n\nIt's a tie!", p0_score, p1_score);
		}
	}
	
	// maintain the game's board state
	private void checkGameBoard() {
		
		// run through entire board (dots, lines, and boxes)
		for (int i = 0; i < (numRows * numCols) ; i++)
		{
			// if the component is a box, check all lines surrounding it
			if (gameBoard.get(i) instanceof JPanel)
			{
				// if the box hasn't already been taken
				if (gameBoard.get(i).isEnabled())
				{
					// check all the lines surrounding the box
					if ( !gameBoard.get(i-numCols).isEnabled() && !gameBoard.get(i-1).isEnabled() && 
							!gameBoard.get(i+1).isEnabled() && !gameBoard.get(i+numCols).isEnabled() )
					{
						/*
						 *  player claims the box if his move completes a box
						 *  player receives one score point as a result
						 *  player also receives an extra turn
						 */
						gameBoard.get(i).setBackground(getPlayerBoxColor());
						gameBoard.get(i).setEnabled(false);
						scorePlayer();
						extra_turn = true;
						
						playSound("sounds/box_get.wav");
					}
				}
			}
		}
	}
	
	private void playSound(String url) {
		
		File soundFile = new File(url);
		
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
