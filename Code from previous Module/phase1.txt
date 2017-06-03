// Nicholas Nelson, David Henderson, Christopher Calderon
// Nathan Mauga, Haley Dimapilis
// CST338-30_SU17: Software Design
// Module 5
// (M5) Write a Java program (GUI Cards) (4 hrs)

import javax.swing.*;
import java.awt.*;

public class main
{
   static final int NUM_CARD_IMAGES = 57; //Every typical card and back of card
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   
   // helper function to load all the icons into an array
   static void loadCardIcons()
   {
      String fileExt = ".gif";
      int index = 0;
      int numSuits = 4;
      int numCardsPerSuit = 14;
      
      for (int i = 0; i < numSuits; i++)
      {
         for (int j = 0; j < numCardsPerSuit; j++)
         {
            icon[index] = new ImageIcon("images/" + turnIntIntoCardValue(j) + turnIntIntoCardSuit(i) + fileExt);
            index++;
         }
      }
      icon[index] = new ImageIcon("images/BK.gif"); //Back of card (Last)
   }
   
   // helper function to resolve iterators into the value part of the filename
   static String turnIntIntoCardValue(int k)
   {
      String[] cardValues =
      { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X"};
      return cardValues[k];
   }
   
   // helper function to resolve iterators into the suit part of the filename
   static String turnIntIntoCardSuit(int j)
   {
      String[] cardSuits = {"C", "D", "H", "S"};
      return cardSuits[j];
   }

   // main, simple method that loads all the cards and displays them 
   // using swing.
   public static void main(String[] args)
   {
      loadCardIcons();
      
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150,650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      frmMyWindow.setLayout(layout);
      
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (int i = 0; i < NUM_CARD_IMAGES; i++)
      {
         labels[i] = new JLabel(icon[i]);
      }
      
      for (int i = 0; i < NUM_CARD_IMAGES; i++)
      {
         frmMyWindow.add(labels[i]);
      }
      
      frmMyWindow.setVisible(true);
   }
}
