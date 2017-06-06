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
      // get MVC and Timer set up and configured
      TimedCardsViewer myView = new TimedCardsViewer();
      TimedCardsModel myModel = new TimedCardsModel();
      TimedCardsController myController = new TimedCardsController(myView, myModel);
      UpTimer myTimer = new UpTimer(myController);
      myController.setTimer(myTimer);
      // start the show!
      myTimer.start();
      myController.run();
   }

}
