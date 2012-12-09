import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class CustomCardLayout extends CardLayout {

	private static final long serialVersionUID = -97997683042901752L;

	@Override
	public Dimension preferredLayoutSize(Container parent) {

		Component current = findCurrentComponent(parent);
		if (current != null) {
			Insets insets = parent.getInsets();
			Dimension pref = current.getPreferredSize();
			pref.width += insets.left + insets.right;
			pref.height += insets.top + insets.bottom;
			return pref;
		}
		return super.preferredLayoutSize(parent);
	}

	public Component findCurrentComponent(Container parent) {
		for (Component comp : parent.getComponents()) {
			if (comp.isVisible()) {
				return comp;
			}
		}
		return null;
	}

	private static void createAndShowUI() {
		final CardLayout cardLayout = new CustomCardLayout();
		final JPanel cardHolder = new JPanel(cardLayout);
		final JFrame frame = new JFrame("MultiSizedPanels");
		JLabel[] labels = {
				new JLabel("Small Label", SwingConstants.CENTER),
				new JLabel("Medium Label", SwingConstants.CENTER),
				new JLabel("Large Label", SwingConstants.CENTER)};

		for (int i = 0; i < labels.length; i++) {
			int padding = 50 * (i + 1);
			Border lineBorder = BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.blue),
					BorderFactory.createEmptyBorder(padding, padding, padding, padding));
			labels[i].setBorder(lineBorder);
			JPanel containerPanel = new JPanel();
			containerPanel.add(labels[i]);
			cardHolder.add(containerPanel, String.valueOf(i));
		}
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.next(cardHolder);
				frame.pack();
			}
		});
		JPanel btnHolder = new JPanel();
		btnHolder.add(nextButton);

		frame.add(cardHolder, BorderLayout.CENTER);
		frame.add(btnHolder, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		createAndShowUI();
	}
}