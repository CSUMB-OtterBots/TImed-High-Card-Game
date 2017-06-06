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
      System.out.println("thread is running...");  
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
   
   void startTimer()
   {
      timer.start();
   }
   
   void stopTimer()
   {
      timer.stop();
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
