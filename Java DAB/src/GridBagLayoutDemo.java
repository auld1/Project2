import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * NOTES:
 * If all lines around a box are selected (depressed/colored/disabled), set the color
 * of that box to that of the current player and give a point to that player
 */

public class GridBagLayoutDemo {
	
	static final Color p0_color = Color.RED;
	static final Color p1_color = Color.BLUE;
	static final Color box_color = Color.GRAY;
	
	private static int turn_count = 0;

	public static void addComponentsToPane(Container pane)
	{
		BufferedImage dot_icon = null;

		try {
			dot_icon = ImageIO.read(new File("black_dot_24.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JButton line;
		JLabel dot;
		JPanel box;

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.ipadx = 2;
		c.ipady = 2;

		//////////////////////////////////////////   TOP DOT ROW

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 0;
		c.gridy = 0;
		pane.add(dot, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(40,10));
		c.gridx = 1;
		c.gridy = 0;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 2;
		c.gridy = 0;
		pane.add(dot, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(40,10));
		c.gridx = 3;
		c.gridy = 0;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 4;
		c.gridy = 0;
		pane.add(dot, c);

		//////////////////////////////////////////   FIRST BOX ROW

		line = new JButton();
		line.setPreferredSize(new Dimension(10,40));
		c.gridx = 0;
		c.gridy = 1;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		box = new JPanel();
		box.setPreferredSize(new Dimension(40,40));
		box.setBackground(box_color);
		c.gridx = 1;
		c.gridy = 1;
		pane.add(box, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(10,40));
		c.gridx = 2;
		c.gridy = 1;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		box = new JPanel();
		box.setPreferredSize(new Dimension(40,40));
		box.setBackground(box_color);
		c.gridx = 3;
		c.gridy = 1;
		pane.add(box, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(10,40));
		c.gridx = 4;
		c.gridy = 1;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		//////////////////////////////////////////   MIDDLE DOT ROW

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 0;
		c.gridy = 2;
		pane.add(dot, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(40,10));
		c.gridx = 1;
		c.gridy = 2;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 2;
		c.gridy = 2;
		pane.add(dot, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(40,10));
		c.gridx = 3;
		c.gridy = 2;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 4;
		c.gridy = 2;
		pane.add(dot, c);

		//////////////////////////////////////////   SECOND BOX ROW

		line = new JButton();
		line.setPreferredSize(new Dimension(10,40));
		c.gridx = 0;
		c.gridy = 3;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		box = new JPanel();
		box.setPreferredSize(new Dimension(40,40));
		box.setBackground(box_color);
		c.gridx = 1;
		c.gridy = 3;
		pane.add(box, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(10,40));
		c.gridx = 2;
		c.gridy = 3;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		box = new JPanel();
		box.setPreferredSize(new Dimension(40,40));
		box.setBackground(box_color);
		c.gridx = 3;
		c.gridy = 3;
		pane.add(box, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(10,40));
		c.gridx = 4;
		c.gridy = 3;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		//////////////////////////////////////////   BOTTOM DOT ROW

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 0;
		c.gridy = 4;
		pane.add(dot, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(40,10));
		c.gridx = 1;
		c.gridy = 4;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 2;
		c.gridy = 4;
		pane.add(dot, c);

		line = new JButton();
		line.setPreferredSize(new Dimension(40,10));
		c.gridx = 3;
		c.gridy = 4;
		pane.add(line, c);
		line.addActionListener(new ButtonListener());

		dot = new JLabel(new ImageIcon(dot_icon));
		c.gridx = 4;
		c.gridy = 4;
		pane.add(dot, c);
	}
	
	private static class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton) e.getSource();
			
			if (turn() == 0)
				button.setBackground(p0_color);
			else
				button.setBackground(p1_color);
			
			button.setEnabled(false);
			button.setOpaque(true);
		}
	}
	
	// increase turn count and determine current turn's player
	private static int turn() { return (turn_count++ % 2); }

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("GridBagLayoutDemo");
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
