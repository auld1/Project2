package bab;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class BAB_Main
{
	private static final  Color backgroundColor = new Color(10, 10, 10);

	private final String MENU_PANEL = "BAB Menu";
	private final String GAME_PANEL = "BAB Game";
	private final String INST_PANEL = "BAB Instructions";
	private final String ABOUT_PANEL = "BAB About";

	private final String IMAGE_DIRECTORY = "images/";
	private final String MAIN_IMAGE = "BAB_Main.png";
	private final String GAME_IMAGE = "BAB_Game.png";
	private final String INST_IMAGE = "BAB_Inst.png";
	private final String ABOUT_IMAGE = "BAB_About.png";
	private final String BUTTON_IMAGE = "BAB_Button.gif";
	
	private final String TEXT_DIRECTORY = "text/";
	private final String INST_TEXT = "instructions.txt";
	private final String ABOUT_TEXT = "about.txt";

	private final String GAME = "Start Game";
	private final String INST = "Instructions";
	private final String ABOUT = "About";
	private final String QUIT = "Quit";
	private final String MENU = "Back To Menu";
	
	private final int BOTTOM_PADDING = 20;
	
	private JMenuBar menuBar;
	private JPanel JP_Base; //a panel that uses CardLayout

	private BAB_Button gameButton;
	private BAB_Button instButton;
	private BAB_Button aboutButton;
	private BAB_Button quitButton;
	private BAB_Button menuButton_G;
	private BAB_Button menuButton_I;
	private BAB_Button menuButton_A;

	private void addComponentToPane(Container pane)
	{
		ImageIcon button_img = BAB_Utils.getImageIcon(IMAGE_DIRECTORY+BUTTON_IMAGE);
		Border padding = BorderFactory.createEmptyBorder(0, 0, BOTTOM_PADDING, 0);

		gameButton = new BAB_Button(GAME);
		gameButton.setIcon(button_img);	
		gameButton.addActionListener(new ButtonListener());

		instButton = new BAB_Button(INST);
		instButton.setIcon(button_img);
		instButton.addActionListener(new ButtonListener());

		aboutButton = new BAB_Button(ABOUT);
		aboutButton.setIcon(button_img);
		aboutButton.addActionListener(new ButtonListener());

		quitButton = new BAB_Button(QUIT);
		quitButton.setIcon(button_img);
		quitButton.addActionListener(new ButtonListener());

		menuButton_G = new BAB_Button(MENU);
		menuButton_G.setIcon(button_img);
		menuButton_G.setBorder(padding);
		menuButton_G.addActionListener(new ButtonListener());

		menuButton_I = new BAB_Button(MENU);
		menuButton_I.setIcon(button_img);
		menuButton_I.setBorder(padding);
		menuButton_I.addActionListener(new ButtonListener());

		menuButton_A = new BAB_Button(MENU);
		menuButton_A.setIcon(button_img);
		menuButton_A.setBorder(padding);
		menuButton_A.addActionListener(new ButtonListener());

		//Create the panel that contains the "cards".
		JP_Base = new JPanel(new CardLayout());
		JP_Base.add(createMenuPanel(), MENU_PANEL);
//		JP_Base.add(new JPanel(), GAME_PANEL);
		JP_Base.add(createInstPanel(), INST_PANEL);
		JP_Base.add(createAboutPanel(), ABOUT_PANEL);

		pane.add(JP_Base, BorderLayout.CENTER);
	}

	private JPanel createMenuPanel()
	{
		JPanel JP_Menu = new JPanel(new BorderLayout());

		JP_Menu.setBackground(backgroundColor);

		JP_Menu.add(new JLabel(BAB_Utils.getImageIcon(IMAGE_DIRECTORY+MAIN_IMAGE)), BorderLayout.NORTH);

		JPanel menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setBackground(backgroundColor);

		menu.add(gameButton);
		menu.add(instButton);
		menu.add(aboutButton);
		menu.add(quitButton);
		menu.add(Box.createRigidArea(new Dimension(0, BOTTOM_PADDING)));

		JP_Menu.add(menu, BorderLayout.CENTER);

		return JP_Menu;
	}

	private JPanel createGamePanel()
	{
		JPanel JP_Game = new JPanel(new BorderLayout());

		JP_Game.setBackground(backgroundColor);

		JP_Game.add(new JLabel(BAB_Utils.getImageIcon(IMAGE_DIRECTORY+GAME_IMAGE)), BorderLayout.NORTH);
		
		JPanel gamePanel = new JPanel();
		gamePanel.setBackground(backgroundColor);
		
		BAB_Game game = new BAB_Game(backgroundColor, 11, 11);
		
		game.addComponentsToPane(gamePanel);
		
		JP_Game.add(gamePanel, BorderLayout.CENTER);

		return JP_Game;
	}

	private JPanel createInstPanel()
	{
		JPanel JP_Inst = new JPanel(new BorderLayout());

		JP_Inst.setBackground(backgroundColor);

		JP_Inst.add(new JLabel(BAB_Utils.getImageIcon(IMAGE_DIRECTORY+INST_IMAGE)), BorderLayout.NORTH);
		
		JTextPane textPane = BAB_Utils.getText(TEXT_DIRECTORY+INST_TEXT);
		BAB_Utils.formatText(textPane, backgroundColor, 12);
		
		JP_Inst.add(textPane, BorderLayout.CENTER);

		JP_Inst.add(menuButton_I, BorderLayout.SOUTH);

		return JP_Inst;
	}

	private JPanel createAboutPanel()
	{
		JPanel JP_About = new JPanel(new BorderLayout());

		JP_About.setBackground(backgroundColor);

		JP_About.add(new JLabel(BAB_Utils.getImageIcon(IMAGE_DIRECTORY+ABOUT_IMAGE)), BorderLayout.NORTH);
		
		JTextPane textPane = BAB_Utils.getText(TEXT_DIRECTORY+ABOUT_TEXT);
		BAB_Utils.formatText(textPane, backgroundColor, 12);
		
		JP_About.add(textPane, BorderLayout.CENTER);

		JP_About.add(menuButton_A, BorderLayout.SOUTH);

		return JP_About;
	}

	private JMenuBar createMenuBar() {

		JMenu menu;
		JMenuItem menuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		menuItem = new JMenuItem(MENU, KeyEvent.VK_M);
		menuItem.addActionListener(new MenuListener());
		menu.add(menuItem);

		menuItem = new JMenuItem(GAME, KeyEvent.VK_R);
		menuItem.addActionListener(new MenuListener());
		menu.add(menuItem);

		menuItem = new JMenuItem(QUIT, KeyEvent.VK_Q);
		menuItem.addActionListener(new MenuListener());
		menu.add(menuItem);

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);

		menuItem = new JMenuItem(INST, KeyEvent.VK_I);
		menuItem.addActionListener(new MenuListener());
		menu.add(menuItem);

		menuItem = new JMenuItem(ABOUT, KeyEvent.VK_A);
		menuItem.addActionListener(new MenuListener());
		menu.add(menuItem);      

		return menuBar;
	}
	
	// TODO: Hackish, find better solution to restart game
	private void newGame() {
		
		// discards and restarts panel content on each new game
		if (JP_Base.getComponentCount() > 3)
			JP_Base.add(createGamePanel(), GAME_PANEL);
		else {
			JP_Base.remove(2);
			JP_Base.add(createGamePanel(), GAME_PANEL);
		}
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();

			CardLayout cl = (CardLayout)(JP_Base.getLayout());

			switch (command)
			{
			case GAME:
				newGame();
				cl.show(JP_Base, GAME_PANEL);
				break;
			case INST:
				cl.show(JP_Base, INST_PANEL);
				break;
			case ABOUT:
				cl.show(JP_Base, ABOUT_PANEL);
				break;
			case QUIT:
				System.exit(0);
				break;
			case MENU:
				cl.show(JP_Base, MENU_PANEL);
				break;
			default:
				cl.show(JP_Base, MENU_PANEL);
				break;
			}
		}
	}

	private class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			
			CardLayout cl = (CardLayout)(JP_Base.getLayout());

			switch (command)
			{
			case GAME:
				newGame();
				cl.show(JP_Base, GAME_PANEL);
				break;
			case INST:
				cl.show(JP_Base, INST_PANEL);
				break;
			case ABOUT:
				cl.show(JP_Base, ABOUT_PANEL);
				break;
			case QUIT:
				System.exit(0);
				break;
			case MENU:
				cl.show(JP_Base, MENU_PANEL);
				break;
			default:
				cl.show(JP_Base, MENU_PANEL);
				break;
			}
		}
	}
	
	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	private static void createAndShowGUI() {

		//Create and set up the content pane.
		BAB_Main bab = new BAB_Main();

		//Create and set up the window.
		JFrame frame = new JFrame("Boxes and Boxes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(bab.createMenuBar());
		frame.setResizable(false);
		//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBackground(backgroundColor);

		
		bab.addComponentToPane(frame.getContentPane());

		//Display the window.

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
