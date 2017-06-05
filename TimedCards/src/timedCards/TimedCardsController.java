package timedCards;

import java.util.Random; // not sure if we need yet
import java.awt.event.*;

public class TimedCardsController implements ActionListener
{
   static int NUM_CARDS_PER_HAND = 7; // number of cards in hand to be played
   static int NUM_PLAYERS = 2; // max number of players
   
   TimedCardsViewer myViewer;
   TimedCardsModel  myModel;

   public TimedCardsController(TimedCardsViewer view, TimedCardsModel model)
   {
      this.myViewer = view; // set argument to global variable
      this.myModel  = model;  // set argument to global variable  
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
      
      // perform initial update
      updateHands();
     
      
   }
   
   private void updateHands()
   {
      myViewer.updateCompHand(myModel.getCompHand()); // update comp hand
      myViewer.updateHumanHand(myModel.getHumanHand()); // update human hand
      myViewer.refreshScreen();  // refresh the screen
   }
   
   /* required for ActionListner, this method is called
    * when registered actions (buttons etc) are interacted with.
    * This method is obviously officially public because of how
    * it has to be declared, but should only be used by objects
    * created from within the class.
    */
   public void actionPerformed(ActionEvent e)
   {
   }
}
