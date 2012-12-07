include Java

java_import java.awt.event.ActionEvent;
java_import java.awt.event.ActionListener;
java_import java.awt.Color
java_import java.awt.Dimension
java_import java.awt.GridBagLayout;
java_import java.awt.GridBagConstraints;
java_import java.util.ArrayList;
java_import javax.swing.ImageIcon;
java_import javax.swing.JButton;
java_import javax.swing.JComponent;
java_import javax.swing.JFrame;
java_import javax.swing.JLabel;
java_import javax.swing.JOptionPane;
java_import javax.swing.JPanel;

class DotsAndBoxes
  
  # boxes = (n-1) / 2
  $NUM_ROWS = 11
  $NUM_COLS = 11
  
  # the number of available lines on the board
  $NUM_MOVES = ( ($NUM_ROWS * $NUM_COLS) / 2 )
  
  # dimensions of lines
  $LINE_LENGTH = 40
  $LINE_WIDTH = 10
  
  # game colors
  $p0_color = Color.red
  $p1_color = Color.blue
  $box_color = Color.gray
  
  # the total number of moves taken (always increases)
  $move_count = 0
  
  # the current player move (increases contingent upon extra_turn)
  $turn_count = 0
  $extra_turn = false
  
  # 2-player scores
  $p0_score = 0
  $p1_score = 0
  
  # array holding all components of the game (dots, lines, and boxes)
  $gameBoard = ArrayList.new
  
  def initialize
    
    # create and set up the window
    frame = JFrame.new("Dots and Boxes")
    frame.setDefaultCloseOperation(JFrame::EXIT_ON_CLOSE)
    
    # set up the content pane
    addComponentsToPane(frame.getContentPane)
    
    # display the window
    frame.pack
    frame.setVisible(true)
    
  end
  
  def addComponentsToPane(pane)
    
    # obtain dot image
    dot_image = ImageIcon.new("black_dot_24.png")
    
    # initialize GridBag layout and constraints
    pane.setLayout(GridBagLayout.new)
    c = GridBagConstraints.new
    
    # something related to width/height of lines?
    c.ipadx = 2
    c.ipady = 2
    
    # add all game elements to the pane
    for i in 0..($NUM_ROWS-1) do
      for j in 0..($NUM_COLS-1) do
        
        # GridBag row/column constraint setting
        c.gridy = i
        c.gridx = j
        
        # dot row
        if (i % 2 == 0)
          
          #dot
          if (j % 2 == 0)
            dot = JLabel.new(dot_image)
            pane.add(dot, c)
            $gameBoard.add(dot)
            
          # horizontal line
          else
            line = JButton.new
            line.setPreferredSize(Dimension.new($LINE_LENGTH, $LINE_WIDTH))
            line.addActionListener($ButtonListener)
            pane.add(line, c)
            $gameBoard.add(line)
            
          end
          
        # box row
        else
          
          # vertical line
          if (j % 2 == 0)
            line = JButton.new
            line.setPreferredSize(Dimension.new($LINE_WIDTH, $LINE_LENGTH))
            line.addActionListener($ButtonListener)
            pane.add(line, c)
            $gameBoard.add(line)
            
          # box
          else
            box = JPanel.new
            box.setPreferredSize(Dimension.new($LINE_LENGTH, $LINE_LENGTH))
            box.setBackground($box_color)
            pane.add(box, c)
            $gameBoard.add(box)
            
          end
        end
      end
    end  
  end
  
  # ButtonListener for line buttons
  $ButtonListener = ActionListener.new
  def $ButtonListener.actionPerformed(event)
    
    # get the JButton line that the current player clicked on
    button = event.getSource

    # take the line out of play (disable it)
    button.setEnabled(false)
    button.setOpaque(true)
    
    # determine whose turn it is
    player = $turn_count % 2
    
    # get the color of whose turn it is
    pColor = nil
    if (player == 0)
      pColor = $p0_color
    else
      pColor = $p1_color
    end
    
    # claim the line in current player's color
    button.setBackground(pColor)
    
    # run through entire board (dots, lines, and boxes)
    for i in 0..( ($NUM_ROWS*$NUM_COLS) - 1 ) do
      
      # if the component is a box
      if ($gameBoard.get(i).getClass.getName == "javax.swing.JPanel")
        
        # if the box hasn't already been taken
        if ($gameBoard.get(i).isEnabled)
          
          # check all the lines surrounding the box
          # if current player has completed a box, claim it as thiers
          if ( !$gameBoard.get(i-$NUM_COLS).isEnabled && 
                !$gameBoard.get(i-1).isEnabled &&
                  !$gameBoard.get(i+1).isEnabled && 
                    !$gameBoard.get(i+$NUM_COLS).isEnabled)
            
            # claim the box in current player's color and disable it
            $gameBoard.get(i).setBackground(pColor)
            $gameBoard.get(i).setEnabled(false)
            
            # add a score point to current player
            if (player == 0)
              $p0_score += 1
            else
              $p1_score += 1
            end
            
            # give current player an extra turn (since they completed a box)
            $extra_turn = true;   
                     
          end        
        end
      end
    end
    
    # move to the next player's turn (unless current player has extra turn)
    if (!$extra_turn)
      $turn_count += 1
    else
      $extra_turn = false
    end
    
    # increase the move total regardless
    $move_count += 1
    
    # check to see if the game has ended
    if ( !($move_count < $NUM_MOVES) )
      
      # compose end-game message
      message = ""
      if ($p0_score > $p1_score)
        message = "        Player One Wins!"
      elsif ($p0_score < $p1_score)
        message = "        Player Two Wins!" 
      else
        message = "              It's a draw!"
      end

      # display end-game message
      JOptionPane.showMessageDialog(nil, "Player One = #{$p0_score} points\nPlayer Two = #{$p1_score} points\n\n#{message}")
      
    end
  end
end

DotsAndBoxes.new
