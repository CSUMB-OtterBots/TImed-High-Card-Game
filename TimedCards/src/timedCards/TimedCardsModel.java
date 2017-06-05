package timedCards;

import java.util.Random;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.*;

public class TimedCardsModel
{
   static int NUM_CARDS_PER_HAND = TimedCardsController.NUM_CARDS_PER_HAND;
   static int NUM_PLAYERS = TimedCardsController.NUM_PLAYERS;
   
   static private Hand compHand; // computer's hand
   static private Hand humanHand; // human's hand
   
   static private Card lastComputerCard = null; // clears computer hand
   static private Card lastHumanCard = null; // clears human hand
   
   static private int humanScore = 0;
   static private int compScore = 0;
   
   static private Hand winnings = new Hand();
   
   public static CardGameFramework myCardGame;
   
   // default constructor
   public TimedCardsModel()
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      // instantiate game
      myCardGame = 
            new CardGameFramework(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
                                  unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND);
      // shuffle and deal into the hands.
      myCardGame.deal();
      
      // set the globals to the human and game hands
      compHand = myCardGame.getHand(0);
      humanHand = myCardGame.getHand(1);
      
      compHand.sort();
      humanHand.sort();
      
   }
   
   /*
    * This method is used to add a card to the used card "hand"
    */
   void addUsedCard(Card card)
   {
      
   }
   
   /*
    * This method is used to set the last computerCard
    */
   void setLastCompCard(Card card)
   {
      
   }
   
   /*
    * This method is used to set the last humanCard
    */
   void setLastHumanCard(Card card)
   {
      
   }
   
   /*
    * This method is used to get the last computerCard
    */
   Card getLastCompCard()
   {
      return new Card(); // just to allow compile
   }
   
   /*
    * This method is used to get the last human card
    */
   Card getLastHumanCard()
   {
      return new Card(); // just to allow compile
   }
   
   /*
    * this method allows access to the computer hand
    */
   Hand getCompHand()
   {
      return compHand;
   }
   
   /*
    * This method allows access to the human hand
    */
   Hand getHumanHand()
   {
      return humanHand;
   }
   
   /*
    * This method takes a card as argument
    *   and returns the index to its location.
    *   it should probably use inspectCard() as == should
    *   work here.
    */
   int getIdxFromHumanHand(Card card)
   {
      return -1; // just to allow compile
   }
   
   /*
    * This method takes a card as an argument
    *   and returns the index to its location.
    *   it should probably use inspectCard() as == should
    *   work here.
    */
   int getIdxFromCompHand(Card card)
   {
      return -1; // just to allow compile
   }
}
