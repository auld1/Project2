import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrameDemo {
	
	private static JFrame frame = new JFrame("FrameDemo");
	private static JPanel panel = new JPanel();
	private static JPanel panel2 = new JPanel();
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        //Create and set up the window.
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton button = new JButton("Hello");
        button.addActionListener(new ButtonListener());
        panel.add(button);
        
        JPanel panel2 = new JPanel();
        JButton button2 = new JButton("World");
        button2.addActionListener(new ButtonListener());
        panel2.add(button2);
        
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    private static class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// get the JButton line that the current player clicked on
			JButton button = (JButton) e.getSource();
			
			if (button.getText().equalsIgnoreCase("Hello"))
			{
				frame.add(panel2);
				frame.revalidate();
				frame.repaint();
			} 
			
			else if (button.getText().equalsIgnoreCase("World"))
			{
				frame.add(panel2);
				frame.revalidate();
				frame.repaint();
			}
		}
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
