package timedCards;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.*;

public class TimedCardsController
{
   static int NUM_CARDS_PER_HAND = 7; // number of cards in hand to be played
   static int NUM_PLAYERS = 2; // max number of players
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   static JButton[] humanButtons = new JButton[NUM_CARDS_PER_HAND];
   static CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
   static Hand compHand; // computer's hand
   static Hand humanHand; // human's hand
   
   static Card lastComputerCard = null; // clears computer hand
   static Card lastHumanCard = null; // clears human hand
   
   static int humanScore = 0;
   static int compScore = 0;
   
   static Hand winnings = new Hand();
   
   public TimedCardsController(TimedCardsViewer myViewer, TimedCardsModel myModel)
   {
      
   }
   
   // The run method is the main entry point into the program.
   public void run()
   {
      
   }

}
