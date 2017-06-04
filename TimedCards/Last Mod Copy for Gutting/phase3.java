
// Nicholas Nelson, David Henderson, Christopher Calderon
// Nathan Mauga, Haley Dimapilis
// CST338-30_SU17: Software Design
// Module 5
// (M5) Write a Java program (GUI Cards) (4 hrs)

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.*;

   
public class phase3 implements ActionListener
{

   public static void main(String[] args)
   {
   }

   // method that displays hands to user
   static void displayHands()
   {
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         if (computerLabels[i] != null)
         {
            myCardTable.pnlComputerHand.add(computerLabels[i]);
         }
         if (humanButtons[i] != null)
         {
            myCardTable.pnlHumanHand.add(humanButtons[i]);
            humanButtons[i].addActionListener(new phase3());
         }
      }
   }
   
   // method plays the cards and displays appropriate responses
   static void playCards(boolean over)
   {
      // CREATE LABELS ----------------------------------------------------
      JLabel lblComputerHand = new JLabel("Computer Hand", JLabel.CENTER);
      JLabel lblHumanHand = new JLabel("Human Hand", JLabel.CENTER);
      JLabel compWon = new JLabel("Computer Won", JLabel.CENTER);
      JLabel youWon = new JLabel("You Won!", JLabel.CENTER);
      JLabel draw   = new JLabel("Cat's Game!", JLabel.CENTER);
      JLabel labA = new JLabel(GUICard.getIcon(lastComputerCard));
      JLabel labB = new JLabel(GUICard.getIcon(lastHumanCard));

      // remove all the old stuff
      myCardTable.pnlPlayArea.removeAll();
      myCardTable.repaint();

      if (over)
      {
         if (humanScore > compScore)
         {
            myCardTable.pnlPlayArea.add(youWon);
         }
         else if (compScore > humanScore)
         {
            myCardTable.pnlPlayArea.add(compWon);
         }
         else
         {
            myCardTable.pnlPlayArea.add(draw);
         }

      } 
      else
      {
         myCardTable.pnlPlayArea.add(labA);
         myCardTable.pnlPlayArea.add(labB);

         // ADD LABELS TO PANELS -----------------------------------------
         myCardTable.pnlPlayArea.add(lblComputerHand);
         myCardTable.pnlPlayArea.add(lblHumanHand);
      }
   }

   // method clears the old labels to display the new hands
   static void prepHandForDisplay()
   {
      Card nextCard;

      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         if (computerLabels[i] != null)
         {
            myCardTable.pnlComputerHand.remove(computerLabels[i]);
            computerLabels[i] = null;
         }
         if (humanButtons[i] != null)
         {
            myCardTable.pnlHumanHand.remove(humanButtons[i]);
            humanButtons[i] = null;
         }
      }

      // add new labels for comp hand
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         nextCard = compHand.inspectCard(i);
         if (!nextCard.getErrorFlag())
         {
            computerLabels[i] = new JLabel(GUICard.getBackCardIcon());
         }
      }
      
      // add new labels for human hand
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         JButton button;
         nextCard = humanHand.inspectCard(i);
         if (! nextCard.getErrorFlag() )
         {         
            button = new JButton("", GUICard.getIcon(nextCard));
            humanButtons[i] = button;
         }
      }
   }
   
   // method takes the rank of each card played and determines winner
   void scoreRound()
   {
      int comp  = Card.rank(lastComputerCard.getValue());
      int human = Card.rank(lastHumanCard.getValue());
      
      // comp won
      if (comp > human)
      {
         compScore++;
      }
      // human won
      if (human > comp)
      {
         humanScore++;
      }
      // no pts for draw
   }
   
   // method plays the computer hand and compares it to human hand
   void computerPlay()
   {
      int playCard = -1;
      int lowCard = -1;
      Card nextCard = null;
      
      int humRank = Card.rank(lastHumanCard.getValue());
      
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         nextCard = compHand.inspectCard(i);
         if (! nextCard.getErrorFlag())
         {
            if (lowCard < 0)
            {
               lowCard = i;
            }
            if (Card.rank(nextCard.getValue()) > humRank)
            {
               playCard = i;
               break;
            }
         }
      }
      if (playCard < 0)
      {
         playCard = lowCard;
      }
      lastComputerCard = compHand.playCard(playCard);
      scoreRound();
   }

   // method resets the screen and clears the hands
   void refreshScreen()
   {
      myCardTable.pnlHumanHand.setVisible(false);
      myCardTable.pnlHumanHand.setVisible(true);
   }
   
   // action method when user presses play
   public void actionPerformed(ActionEvent e)
   {
      myCardTable.pnlHumanHand.remove((JButton) e.getSource());
      for (int x = 0; x < NUM_CARDS_PER_HAND; x++)
      {
         if ((JButton) e.getSource() == humanButtons[x])
         {
            lastHumanCard = humanHand.playCard(x);
            break;
         }
      }
      computerPlay();
      prepHandForDisplay();
      displayHands();
      playCards(humanHand.getNumCards() <= 0); // bool returned to end game
      refreshScreen();
   }
}