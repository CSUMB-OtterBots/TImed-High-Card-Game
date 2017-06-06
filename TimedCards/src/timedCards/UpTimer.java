package timedCards;

import javax.swing.Timer;

public class UpTimer extends Thread
{
   private Timer timer;
   private int counter = 0;
   
   public void run()
   {
      JFrame myWindow = new JFrame("Dope Timer");
      myWindow.setSize(400, 150);
      myWindow.setLocationRelativeTo(null);
      myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
      myWindow.setLayout(layout);
      
      JButton start = new JButton("Start");
      myWindow.add(start);
      start.addActionListener(new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            timer.setInitialDelay(0);
            timer.start();
         }
         
      });
      
      JButton stop = new JButton("Stop");
      myWindow.add(stop);
      stop.addActionListener(new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            timer.stop();
         }
         
      });
      
      myWindow.setVisible(true);
      
      JLabel label = new JLabel(String.format("%02d:%02d", counter / 60, counter % 60));
      label.setFont(new Font("Serif",Font.PLAIN, 60));
      myWindow.add(label);
      
      
      
      
      timer = new Timer(1000, new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            if (counter == 125)
            {
               System.exit(0);
            }
            
            label.setText(String.format("%02d:%02d", counter / 60, counter % 60));
            myWindow.setVisible(true);
            counter++;
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
         System.out.println("Unexpected Interrupt");
         System.exit(0);
      }
   }

   public static void main(String[] args)
   {
      UpTimer myTimer = new UpTimer();
      myTimer.start();
      
      UpTimer myOtherTimer = new UpTimer();
      myOtherTimer.start();
   }
}



