// Nicholas Nelson, David Henderson, Christopher Calderon
// Nathan Mauga, Haley Dimapilis
// CST338-30_SU17: Software Design
// Module 6
// Timed High-Card Game.

import timedCards.*;

public class Main
{

   public static void main(String[] args)
   {
      TimedCardsViewer myView = TimedCardsViewer();
      TimedCardsModel myModel = TimedCardsModel();
      myController = TimedCardController(myView, myModel);
      myController.run();
      
   }

}
