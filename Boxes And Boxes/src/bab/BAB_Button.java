package bab;
import java.awt.Component;
import javax.swing.JButton;

public class BAB_Button extends JButton {

	private static final long serialVersionUID = 1L;

	public BAB_Button(String label) {
		super(label);
	
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false); 
		this.setFocusPainted(false); 
	}
}
