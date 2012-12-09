package bab;
import java.awt.Color;
import java.awt.Font;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class BAB_Utils
{
	public static JTextPane getText(String path)
	{
		JTextPane textPane = new JTextPane();
		StyledDocument doc = textPane.getStyledDocument();
		
		try
		{
			List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			String text = "";
			
			for (String line : lines) { text += line + "\n"; }
			
			doc.insertString(0, text, null);
		} 
		
		catch (Exception e) {
			System.err.println("Couldn't read file at: " + path);
            return null;
		}
		
		return textPane;
	}
	
	public static void formatText(JTextPane textPane, Color background, int size) {
		
		Color foreground = new Color(255-background.getRed(), 
										255-background.getGreen(), 
											255-background.getBlue());
		
		textPane.setBackground(background);
		textPane.setForeground(foreground);
		textPane.setFont(new Font("Verdana", Font.BOLD, size));
		
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
	    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
	    doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}
	
	public static ImageIcon getImageIcon(String path)
	{
        Path imgURL = Paths.get(path);
        
        if (imgURL != null) { return new ImageIcon(imgURL.toString()); }
        
        else
        {
            System.err.println("Couldn't find image at: " + path);
            return null;
        }
    }
	
	public static void getSound() {

	}	
	
	public static void main(String[] args) {
		
		JTextPane test = getText("text/about.txt");
		
		System.out.println(test.getText());
	}
}
