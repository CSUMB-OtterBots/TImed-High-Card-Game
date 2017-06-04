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
      TimedCardsViewer myView = new TimedCardsViewer();
      TimedCardsModel myModel = new TimedCardsModel();
      TimedCardsController myController = new TimedCardsController(myView, myModel);
      myController.run();
   }

}
