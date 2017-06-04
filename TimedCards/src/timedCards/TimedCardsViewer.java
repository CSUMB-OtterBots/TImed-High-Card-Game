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

// manages the reading and building of the card image Icons
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K +
                                                             // joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   // helper method takes in an int and returns the String value of that card
   static String turnIntIntoCardValue(int k) 
   {
      String returnValue = null;
      String[] cardValues =
      { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
      if (k >= 0 && k <= 13)
      {
         returnValue = cardValues[k];
      }
      else
      {
         System.out.println("returning default value A");
         return cardValues[0];// returns default value "A".
      }
      return returnValue;
   }

   // helper method takes in an int and returns the String suit of that card
   static String turnIntIntoCardSuit(int j)
   {
      String returnSuit = null;
      String[] cardSuits =
      { "C", "D", "H", "S" };
      if (j >= 0 && j <= 3)
      {
         returnSuit = cardSuits[j];
      }
      else
      {
         System.out.println("returning default suit C");
         return cardSuits[0]; // returns default value "C"
      }
      return returnSuit;
   }

   // stores the Icons in a 2-D array
   static void loadCardIcons()
   {
      // check if array is loaded... if not load it
      if (!iconsLoaded)
      {
         String fileExt = ".gif";

         for (int i = 0; i < 4; i++)
         {
            for (int j = 0; j < 14; j++)
            {
               iconCards[j][i] = new ImageIcon("images/" + turnIntIntoCardValue(j) + turnIntIntoCardSuit(i) + fileExt);
            }
         }
         iconBack = new ImageIcon("images/BK.gif"); // Back of card (Last)
         iconsLoaded = true;
      }
   }

   // returns the value of card as an integer
   static private int valueAsInt(Card card)
   {
      char val = card.getValue();
      for (int i = 0; i < Card.values.length; i++)
      {
         if (val == Card.values[i])
         {
            return i;
         }
      }
      return -1;
   }

   // returns the suit of the card as an integer
   static private int suitAsInt(Card card)
   {
      return card.getSuit().ordinal(); // returns suit index

   }

   // takes a Card object from the client, and returns the Icon for that card
   static public Icon getIcon(Card card)
   {
      GUICard.loadCardIcons();
      int val, suit;
      val = valueAsInt(card);
      suit = suitAsInt(card);
      return iconCards[val][suit];
   }

   // another method that returns the card-back image, simpler than getIcon()
   static public Icon getBackCardIcon()
   {
      GUICard.loadCardIcons();
      return iconBack;
   }

}