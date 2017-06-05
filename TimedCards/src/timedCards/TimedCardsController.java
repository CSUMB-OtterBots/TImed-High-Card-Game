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
     
      
   }

}
