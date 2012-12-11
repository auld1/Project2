import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
 
public class StylesExample1 {
  public static void main(String[] args) {
    
    JFrame f = new JFrame("Styles Example 1");
    
    JTextPane textPane = new JTextPane();
    textPane.setBackground(new Color(255, 2, 3));
    textPane.setForeground(new Color(88,10,200));
    
    StyledDocument doc = textPane.getStyledDocument();
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    StyleConstants.setFontSize(center, 20);
    doc.setParagraphAttributes(0, doc.getLength(), center, false);
 
    try {
		doc.insertString(0, text, null);
	} catch (BadLocationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    f.getContentPane().add(new JScrollPane(textPane));
    f.setSize(400, 300);
    f.setVisible(true);
  }
 
  public static final String text = "Hello\nWorld";
 
}