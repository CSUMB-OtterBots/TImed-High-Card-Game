package timedCards;

import javax.swing.Icon;
import javax.swing.ImageIcon;

//manages the reading and building of the card image Icons
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
      } else
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
      } else
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