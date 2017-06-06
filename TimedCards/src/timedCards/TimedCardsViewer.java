package timedCards;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.GridLayout;
import java.awt.event.*;

public class TimedCardsViewer implements ItemListener
{
   static int NUM_CARDS_PER_HAND = TimedCardsController.NUM_CARDS_PER_HAND;
   static int NUM_PLAYERS = TimedCardsController.NUM_PLAYERS;
   
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   static JToggleButton[] humanButtons = new JToggleButton[NUM_CARDS_PER_HAND];
   static CardTable myCardTable = 
         new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
   static GUICard myGUICard = new GUICard();
   
   JLabel timer;
   private int counter = 0;
   
   JButton start = new JButton("Start");
   JButton stop = new JButton("Stop");
   
   JButton noPlay = new JButton("No Play");
   
   // Default constructor
   public TimedCardsViewer()
   {
      
      // set up "table"
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      timer = new JLabel(String.format("%02d:%02d", counter / 60, counter % 60));
      timer.setFont(new Font("Serif",Font.PLAIN, 60));
      myCardTable.pnlTimer.add(timer);
      
      // show everything to the user
      myCardTable.setVisible(true);
   }
   
   /****************** Public Methods *********************************/
   
   void addControlButtons(ActionListener listener)
   {
      start.addActionListener(listener);
      stop.addActionListener(listener);
      noPlay.addActionListener(listener);
      
      myCardTable.pnlTimer.add(start);
      myCardTable.pnlTimer.add(stop);
      myCardTable.pnlControl.add(noPlay);
   }
   
   void updateTimer()
   {
      counter++;
      timer.setText(String.format("%02d:%02d", counter / 60, counter % 60));
      myCardTable.pnlTimer.setVisible(false);
      myCardTable.pnlTimer.setVisible(true);
   }
   
   /*
    * This method updates the play area, and displays the argument cards
    *   within that area
    * This method will interact directly with the GUICard class to get
    *   the images. It may be asked to put JLabels or JButtons on the
    *   screen depending on the needs of the game.
    */
   void upDatePlayArea(Card left, Card right, ActionListener listener)
   {
      // clear old table
      myCardTable.pnlPlayArea.removeAll();
      // set up new stuff
      JButton leftButton = new JButton(GUICard.getIcon(left));
      JButton rightButton = new JButton(GUICard.getIcon(right));
      leftButton.addActionListener(listener);
      rightButton.addActionListener(listener);
      // put on table
      myCardTable.pnlPlayArea.add(leftButton);
      myCardTable.pnlPlayArea.add(rightButton);
      
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
      myCardTable.pnlComputerHand.removeAll(); // clear old labels
      computerLabels = null;
      computerLabels = backLabelsFromHand(hand);
      
      for (int i = 0; i < computerLabels.length; i++)
      {
         System.out.println(i);
         System.out.println(computerLabels.length);
         myCardTable.pnlComputerHand.add(computerLabels[i]);
      }
   }
   
   /*
    * This method updates the human hand area. It displays the cards, as
    *    JButtons in the human play area.
    * This method will use one of the private methods below to get the
    *   appropriate type of information to display.
    *   Probably buttonsFromHand(Hand hand)
    */
   void updateHumanHand(Hand hand, ActionListener listner)
   {
      myCardTable.pnlHumanHand.removeAll(); //  clear old buttons
      humanButtons = null;
      humanButtons = buttonsFromHand(hand);      
      for (int i = 0; i < humanButtons.length; i++)
      {
         myCardTable.pnlHumanHand.add(humanButtons[i]);
         humanButtons[i].addItemListener(this);
      }
      
   }
   
   // method resets the screen
   void refreshScreen()
   {
      myCardTable.setVisible(false);
      myCardTable.setVisible(true);
   }
   

   public void itemStateChanged(ItemEvent e)
   {
      JToggleButton pressedButton = (JToggleButton) e.getSource();
      //first we must ignore events from buttons unchecking
      if (e.getStateChange() == 2)
      {
         return;
      }
      
      // then clear out any 'old' checked buttons
      for (int i = 0; i < humanButtons.length; i++)
      {
         if (humanButtons[i] != pressedButton)
         {
            humanButtons[i].setSelected(false);
         }
      }
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
   private JToggleButton[] buttonsFromHand(Hand hand)
   {
      JToggleButton[] buttons = new JToggleButton[hand.getNumCards()];

      for (int i = 0; i < hand.getNumCards(); i++)
      {
         Card nextCard = hand.inspectCard(i);
         JToggleButton button = new JToggleButton(GUICard.getIcon(nextCard));
         buttons[i] = button;
      }
      return buttons;
   }
   
   /*
    * This method returns an array of JLabels. The returned array must be
    *    full of valid labels as the calling function will have to use 
    *    array.length to get the size.
    * To this end, we allocate an array sized to the number of cards in the
    *    hand and should fill the created array with only valid entries.
    * This method is theoretical future expansion and won't be used here
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

      for (int i = 0; i < hand.getNumCards(); i++)
      {
         labels[i] = new JLabel(GUICard.getBackCardIcon());
      }
      return labels;
   }
}

