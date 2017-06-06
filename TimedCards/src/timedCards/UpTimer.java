package timedCards;

import javax.swing.Timer;
import java.awt.event.*;

public class UpTimer extends Thread
{
   private Timer timer;
   TimedCardsController controller;
   
   public UpTimer(TimedCardsController controller)
   {
      this.controller = controller;
   }
   
   public void run()
   {
      timer = new Timer(1000, new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            controller.updateTimer();
            doNothing(10);
         }

      });
   }

   public void doNothing(int milliseconds)
   {
      try
      {
         Thread.sleep(milliseconds);
      }
      catch (InterruptedException e)
      {
         // catch and release
      }
   }
}
