package timedCards;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.*;

public class TimedCardsController
{
   static int NUM_CARDS_PER_HAND = 7; // number of cards in hand to be played
   static int NUM_PLAYERS = 2; // max number of players
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   static JButton[] humanButtons = new JButton[NUM_CARDS_PER_HAND];
   static CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
   static Hand compHand; // computer's hand
   static Hand humanHand; // human's hand
   
   static Card lastComputerCard = null; // clears computer hand
   static Card lastHumanCard = null; // clears human hand
   
   static int humanScore = 0;
   static int compScore = 0;
   
   static Hand winnings = new Hand();

   public TimedCardsController(TimedCardsViewer myViewer, TimedCardsModel myModel)
   {
      
   }
   
   // The run method is the main entry point into the program.
   public void run()
   {
      // establish main frame in which program will run
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      // set up "table"
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // instantiate game
      CardGameFramework highCardGame = 
            new CardGameFramework(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
                                  unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND);
      // shuffle and deal into the hands.
      highCardGame.deal();
      
      // register buttons to play 
      //compWon
      
      // set the globals to the human and game hands
      compHand = highCardGame.getHand(0);
      humanHand = highCardGame.getHand(1);
      
      compHand.sort();
      humanHand.sort();

      // Deal Cards to "Hands" and do initial display
      prepHandForDisplay();
      displayHands();
      
      // show everything to the user
      myCardTable.setVisible(true);
      
   }

}
