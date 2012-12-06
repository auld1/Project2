import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * NOTES:
 * If all lines around a box are selected (depressed/colored/disabled), set the color
 * of that box to that of the current player and give a point to that player
 */

public class DotsAndBoxes {
	
	// boxes = (n-1) / 2
	private static final int NUM_ROWS = 11;
	private static final int NUM_COLS = 11;
	
	// the number of available lines on the board
	private static final int NUM_MOVES = ( (NUM_ROWS * NUM_COLS) / 2);
	
	// dimensions of lines
	private static final int LINE_LENGTH = 40;
	private static final int LINE_WIDTH = 10;
	
	// game colors
	private static final Color p0_color = Color.RED;
	private static final Color p1_color = Color.BLUE;
	private static final Color box_color = Color.GRAY;
	
	// 2-player scores
	private static int p0_score = 0;
	private static int p1_score = 0;
	
	/**
	 * holds all components of the game (dots, lines, and boxes)
	 */
	private static ArrayList<JComponent> gameBoard = new ArrayList<JComponent>();
	
	/**
	 * the total number of moves taken (always increases)
	 */
	private static int move_count = 0;
	
	/**
	 * the current player move (increases contingent upon extra_turn)
	 */
	private static int turn_count = 0;
	private static boolean extra_turn = false;

	public static void addComponentsToPane(Container pane)
	{
		// obtain dot image
		BufferedImage dot_icon = null;
		try {
			dot_icon = ImageIO.read(new File("black_dot_24.png"));
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
		
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++) {
				
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
						line = new JButton();
						line.setPreferredSize(new Dimension(LINE_LENGTH, LINE_WIDTH));
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
						line = new JButton();
						line.setPreferredSize(new Dimension(LINE_WIDTH, LINE_LENGTH));
						line.addActionListener(new ButtonListener());
						pane.add(line, c);
						gameBoard.add(line);
						
					}
					
					// box
					else {
						box = new JPanel();
						box.setPreferredSize(new Dimension(LINE_LENGTH, LINE_LENGTH));
						box.setBackground(box_color);
						pane.add(box, c);
						gameBoard.add(box);
					}
				}
			}
		}
	}
	
	private static class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// get the JButton line that the current player clicked on
			JButton button = (JButton) e.getSource();
			
			// claim the line in the color of the current player and disable it
			button.setBackground(getPlayerColor());
			button.setEnabled(false);
			button.setOpaque(true);
			
			// check the board state after each move
			checkGameBoard();

			// move to the next turn (unless player has extra turn)
			if (!extra_turn)
				turn_count++;
			else
				extra_turn = false;
			
			// increase the move total regardless
			move_count++;
			
			// check to see if the game has ended
			if ( !(move_count < NUM_MOVES) ) {
				JOptionPane.showMessageDialog(null, determineWinner());
			}
		}
	}
	
	// determine who current player is
	private static int getPlayer() { return (turn_count % 2); }
	
	// determine color of current player
	private static Color getPlayerColor() { return (getPlayer() == 0) ? p0_color : p1_color; }
	
	// increase the score of the current player
	private static void scorePlayer() { if (getPlayer() == 0) { p0_score++; } else { p1_score++; } 
	
		// TODO Error checking print statements 
//		System.out.println("P0: " + p0_score);
//		System.out.println("P1: " + p1_score);
//		System.out.println("--------");
	}
	
	// determine which player has the higher score (or a tie)
	private static String determineWinner()
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
	private static void checkGameBoard() {
		
		// run through entire board (dots, lines, and boxes)
		for (int i = 0; i < (NUM_ROWS * NUM_COLS) ; i++)
		{
			// if the component is a box, check all lines surrounding it
			if (gameBoard.get(i) instanceof JPanel)
			{
				// if the box hasn't already been taken
				if (gameBoard.get(i).isEnabled())
				{
					// check all the lines surrounding the box
					if ( !gameBoard.get(i-NUM_COLS).isEnabled() && !gameBoard.get(i-1).isEnabled() && 
							!gameBoard.get(i+1).isEnabled() && !gameBoard.get(i+NUM_COLS).isEnabled() )
					{
						/*
						 *  player claims the box if his move completes a box
						 *  player receives one score point as a result
						 *  player also receives an extra turn
						 */
						gameBoard.get(i).setBackground(getPlayerColor());
						gameBoard.get(i).setEnabled(false);
						scorePlayer();
						extra_turn = true;
					}
				}
			}
		}
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("Dots and Boxes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the content pane.
		addComponentsToPane(frame.getContentPane());

		//Display the window.
		frame.pack();
		frame.setVisible(true);	
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
