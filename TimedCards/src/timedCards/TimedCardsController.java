package timedCards;

import java.util.Random; // not sure if we need yet
import java.awt.event.*;

public class TimedCardsController implements ActionListener
{
   static int NUM_CARDS_PER_HAND = 7; // number of cards in hand to be played
   static int NUM_PLAYERS = 2; // max number of players
   
   TimedCardsViewer myViewer;
   TimedCardsModel  myModel;
   UpTimer myTimer;
   
   boolean deckEmpty = false;
   boolean computerStuck = false;
   boolean humanStuck = false;
         
   public TimedCardsController(TimedCardsViewer view,
             TimedCardsModel model)
   {
      this.myViewer = view;  // set argument to global variable
      this.myModel  = model; // set argument to global variable
   }
   
   public void setTimer(UpTimer timer)
   {
      this.myTimer  = timer; // set argument to global variable
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
      CardGameFramework timedCardGame = 
            new CardGameFramework(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
                                  unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND);
      // shuffle and deal into the hands.
      timedCardGame.deal();
      
      // add default buttons
      myViewer.addControlButtons(this);
      
      // add initial cards to the middle
      updatePlayArea();
      
      // perform initial update
      updateHands();  
   }
   
   void updateTimer()
   {
      myViewer.updateTimer();
   }
   
   private void updateHands()
   {
      myViewer.updateCompHand(myModel.getCompHand()); // update comp hand
      myViewer.updateHumanHand(myModel.getHumanHand()); // update human hand
      myViewer.refreshScreen();  // refresh the screen
   }
   
   void updatePlayArea()
   {
      Card left = myModel.getTopCardInPile(0);
      Card right = myModel.getTopCardInPile(1);
      
      myViewer.upDatePlayArea(left, right, this);
      
   }
   
   boolean unStickPlayers()
   {
      humanStuck = false;
      computerStuck = false;
      return !myModel.dealToPiles();
   }
   
   void checkStuck()
   {
      if (computerStuck && humanStuck)
      {
         deckEmpty = unStickPlayers();
      }
   }
   
   void computerMove()
   {
      int pile = -1;
      int index = -1;
      int leftCard  = myModel.getTopCardInPile(0).getRank();
      int rightCard = myModel.getTopCardInPile(1).getRank();
      
      for (int i = 0; i < myModel.getCompHand().getNumCards(); i++)
      {
         int curCardValue = myModel.getCompHand().inspectCard(i).getRank();
         if ( Math.abs(curCardValue - leftCard) == 1)
         {
            pile = 0;
            index = i;
            break;
         }
         else if ( Math.abs(curCardValue - rightCard) == 1)
         {
            pile = 1;
            index = i;
            break;
         }
      }
      if (pile == -1) // no play
      {
         computerStuck = true;
         myModel.compScore ++;
         checkStuck();
         return;
      }
      // if we get to here, we found a play
      humanStuck = false;
      Card playCard = myModel.getCompHand().playCard(index);
      myModel.addCardToPile(pile, playCard);
      deckEmpty = !myModel.drawCompCard();
   }
   
   void processGameEnd()
   {
      myViewer.displayVictory(myModel.compScore, myModel.humanScore);
   }
   
   void processNoPlay()
   {
      humanStuck = true;
      myModel.humanScore ++;
      checkStuck();
   }
   
   boolean processUserPlay(int pile)
   {
      int index = 0;
      Card playCard;
      
      // get the top card from pile
      Card pileCard = myModel.getTopCardInPile(pile);
      
      // identify the index of the card we intend to play
      for (int i = 0; i < myViewer.humanButtons.length; i++)
      {
         if (myViewer.humanButtons[i].isSelected())
         {
            index = i;
            break;
         }
      }
      // now find the card that was played
      playCard = myModel.getHumanHand().inspectCard(index);
      // check if it was ok
      if ( Math.abs(playCard.getRank() - pileCard.getRank()) == 1)
      {
         myModel.addCardToPile(pile, playCard);
         myModel.getHumanHand().playCard(index);
         deckEmpty = !myModel.drawHumanCard();
         return true;
      }
      return false;
   }
   
   /* required for ActionListner, this method is called
    * when registered actions (buttons etc) are interacted with.
    * This method is obviously officially public because of how
    * it has to be declared, but should only be used by objects
    * created from within the class.
    */
   public void actionPerformed(ActionEvent e)
   {
      boolean goodMove = false;
      
      if (e.getActionCommand() == "Start")
      {
         myTimer.startTimer();
      }
      else if (e.getActionCommand() == "Stop")
      {
         myTimer.stopTimer();
      }
      else if (e.getActionCommand() == "No Play")
      {
         processNoPlay();
      }  
      else if (e.getSource() == myViewer.leftButton)
      {
         goodMove = processUserPlay(0);
      }
      else if(e.getSource() == myViewer.rightButton)
      {
         goodMove = processUserPlay(1);
         if (goodMove)
         {
            computerStuck = false;
         }
         else
         {
            return; // no penalty for bad move
         }
      }
      // finish or default action.
      if (!deckEmpty)
      {
         computerMove();
      }
      // computer moved, so game could have ended
      if(!deckEmpty)
      {
         updatePlayArea();
         updateHands();
         myViewer.refreshScreen();
         return;
      }
      else
      {
         System.out.print("Game Ended");
         processGameEnd();
      }
      
   }
}
