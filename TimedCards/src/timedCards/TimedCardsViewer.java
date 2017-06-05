package timedCards;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.GridLayout;
import java.awt.event.*;

public class TimedCardsViewer
{
   static int NUM_CARDS_PER_HAND = TimedCardsController.NUM_CARDS_PER_HAND;
   static int NUM_PLAYERS = TimedCardsController.NUM_PLAYERS;
   
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   static JButton[] humanButtons = new JButton[NUM_CARDS_PER_HAND];
   static CardTable myCardTable = 
         new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
   static GUICard myGUICard = new GUICard();
   
   // Default constructor
   public TimedCardsViewer()
   {
      
      // set up "table"
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // show everything to the user
      myCardTable.setVisible(true);
   }
   
   /****************** Public Methods *********************************/
   
   /*
    * This method updates the play area, and displays the argument cards
    *   within that area
    * This method will interact directly with the GUICard class to get
    *   the images. It may be asked to put JLabels or JButtons on the
    *   screen depending on the needs of the game.
    */
   void upDatePlayArea(Card[] cards, int numCards)
   {
      
   }
   
   /*
    * This method updates the computer hand area.  It will display a number
    *   of card backs, that represent how many cards are in the hand.
    * This method will use one of the private methods below to get the
    *   appropriate type of information to display.
    *   Probably backLabelsFromHand(Hand hand)
    */
   void updateCompHand(Hand hand)
   {
      
   }
   
   /*
    * This method updates the human hand area. It displays the cards, as
    *    JButtons in the human play area.
    * This method will use one of the private methods below to get the
    *   appropriate type of information to display.
    *   Probably buttonsFromHand(Hand hand)
    */
   void updateHumanHand(Hand hand)
   {
      
   }
   
   // method resets the screen
   void refreshScreen()
   {
      myCardTable.setVisible(false);
      myCardTable.setVisible(true);
   }
   
   /************************* Private Methods *****************************/
   /*
    * This method returns an array of JButtons. The returned array must be
    *    full of valid buttons as the calling function will have to use 
    *    array.length to get the size.
    * To this end, we allocate an array sized to the number of cards in the
    *    hand and should fill the created array with only valid entries.
    * The returned buttons should be set to respond to this class upon
    *    activation.
    */
   private JButton[] buttonsFromHand(Hand hand)
   {
      JButton[] buttons = new JButton[hand.getNumCards()];
      
      return buttons;
   }
   
   /*
    * This method returns an array of JLabels. The returned array must be
    *    full of valid labels as the calling function will have to use 
    *    array.length to get the size.
    * To this end, we allocate an array sized to the number of cards in the
    *    hand and should fill the created array with only valid entries.
    */
   private JLabel[] labelsFromHand(Hand hand)
   {
      JLabel[] labels = new JLabel[hand.getNumCards()];
      
      return labels;
      
   }
   
   /*
    * This method returns an array of JLabels. The returned array must be
    *    full of valid labels as the calling function will have to use 
    *    array.length to get the size.
    * This method is different from labelsFromHand() in that it returns
    *    labels of only the back of the card (one per card in hand).
    *    it is used to display the computer hand.
    * To this end, we allocate an array sized to the number of cards in the
    *    hand and should fill the created array with only valid entries.
    */
   private JLabel[] backLabelsFromHand(Hand hand)
   {
      JLabel[] labels = new JLabel[hand.getNumCards()];
      
      return labels;
   }
}

