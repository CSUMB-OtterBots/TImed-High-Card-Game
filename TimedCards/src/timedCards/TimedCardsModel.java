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
   
   Hand compHand; // computer's hand
   Hand humanHand; // human's hand
   
   public static CardGameFramework myCardGame;
   
   Hand[] piles = new Hand[2];
   
   int humanScore = 0;
   int compScore = 0;
   
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
      
      //set up piles, and put a card in each
      piles[0] = new Hand();
      piles[1] = new Hand();     
      dealToPiles();
   }
   
   boolean dealToPiles()
   {
      piles[0].takeCard(myCardGame.getCardFromDeck());
      piles[1].takeCard(myCardGame.getCardFromDeck());
      return (! getTopCardInPile(1).getErrorFlag() ); // make sure we didn't run out
   }
   
   boolean addCardToPile(int index, Card card)
   {
      if (index == 0 || index == 1)
      {
         piles[index].takeCard(card);
         return true;
      }
      return false;
   }
   
   Card getTopCardInPile(int index)
   {
      if (index ==0 || index == 1 )
      {
         int numCards = piles[index].getNumCards();
         return piles[index].inspectCard(numCards - 1);
      }
      else
      {
         return new Card('Z', Card.Suit.spades); // invalid card
      }
   }
   
   
   // draw a new card for the computer hand
   boolean drawCompCard()
   {
      return myCardGame.takeCard(0);
   }
   
   // draw a new card for the human hand
   boolean drawHumanCard()
   {
      return myCardGame.takeCard(1);
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
}
