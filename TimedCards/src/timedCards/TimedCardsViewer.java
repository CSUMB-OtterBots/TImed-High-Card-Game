package timedCards;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.GridLayout;
import java.awt.event.*;

public class TimedCardsViewer implements ActionListener
{
   public TimedCardsViewer()
   {
      
   }

   /* required for ActionListner, this method is called
    * when registered actions (buttons etc) are interacted with.
    */
   public void actionPerformed(ActionEvent e)
   {
   }
}

// this class embodies the JPanels and Layout(s) needed, this is where all the cards and controls will be placed
class CardTable extends JFrame
{
   public static final long serialVersionUID = 12;
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   // creates the play area for the game
   CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;

      BorderLayout layout = new BorderLayout();
      setLayout(layout);

      pnlComputerHand = new JPanel(new GridLayout(1, this.numCardsPerHand));
      setPanelVars(pnlComputerHand, "Computer Hand");
      add(pnlComputerHand, BorderLayout.NORTH);

      pnlPlayArea = new JPanel(new GridLayout(3, 3));
      setPanelVars(pnlPlayArea, "Play Area");
      add(pnlPlayArea, BorderLayout.CENTER);

      pnlHumanHand = new JPanel(new GridLayout(1, this.numCardsPerHand));
      setPanelVars(pnlHumanHand, "Human Hand");
      add(pnlHumanHand, BorderLayout.SOUTH);
   }
   
   // sets up the panel so that the text is within the border
   private void setPanelVars(JPanel panel, String name)
   {
      TitledBorder border = new TitledBorder(name);
      border.setTitleJustification(TitledBorder.LEFT);
      border.setTitlePosition(TitledBorder.TOP);

      panel.setBorder(border);
      // panel.setMinimumHeight( 200);
      panel.setEnabled(true);
      panel.setVisible(true);
   }
}