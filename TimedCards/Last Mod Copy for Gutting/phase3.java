
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

   public static void main(String[] args)
   {
      // establish main frame in which program will run
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      // set up "table"
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // instantiate game
      CardGameFramework highCardGame = 
            new CardGameFramework(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
                                  unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND);
      // shuffle and deal into the hands.
      highCardGame.deal();
      
      // register buttons to play 
      //compWon
      
      // set the globals to the human and game hands
      compHand = highCardGame.getHand(0);
      humanHand = highCardGame.getHand(1);
      
      compHand.sort();
      humanHand.sort();

      // Deal Cards to "Hands" and do initial display
      prepHandForDisplay();
      displayHands();
      
      // show everything to the user
      myCardTable.setVisible(true);
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

// this class embodies the JPanels and Layout(s) needed, this is where all the cards and controls will be placed
class CardTable extends JFrame
{
   public static final long serialVersionUID = 12;
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   // creates the play area for the game
   CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;

      BorderLayout layout = new BorderLayout();
      setLayout(layout);

      pnlComputerHand = new JPanel(new GridLayout(1, this.numCardsPerHand));
      setPanelVars(pnlComputerHand, "Computer Hand");
      add(pnlComputerHand, BorderLayout.NORTH);

      pnlPlayArea = new JPanel(new GridLayout(3, 3));
      setPanelVars(pnlPlayArea, "Play Area");
      add(pnlPlayArea, BorderLayout.CENTER);

      pnlHumanHand = new JPanel(new GridLayout(1, this.numCardsPerHand));
      setPanelVars(pnlHumanHand, "Human Hand");
      add(pnlHumanHand, BorderLayout.SOUTH);
   }
   
   // sets up the panel so that the text is within the border
   private void setPanelVars(JPanel panel, String name)
   {
      TitledBorder border = new TitledBorder(name);
      border.setTitleJustification(TitledBorder.LEFT);
      border.setTitlePosition(TitledBorder.TOP);

      panel.setBorder(border);
      // panel.setMinimumHeight( 200);
      panel.setEnabled(true);
      panel.setVisible(true);
   }
}

// manages the reading and building of the card image Icons
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K +
                                                             // joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   // helper method takes in an int and returns the String value of that card
   static String turnIntIntoCardValue(int k) 
   {
      String returnValue = null;
      String[] cardValues =
      { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
      if (k >= 0 && k <= 13)
      {
         returnValue = cardValues[k];
      }
      else
      {
         System.out.println("returning default value A");
         return cardValues[0];// returns default value "A".
      }
      return returnValue;
   }

   // helper method takes in an int and returns the String suit of that card
   static String turnIntIntoCardSuit(int j)
   {
      String returnSuit = null;
      String[] cardSuits =
      { "C", "D", "H", "S" };
      if (j >= 0 && j <= 3)
      {
         returnSuit = cardSuits[j];
      }
      else
      {
         System.out.println("returning default suit C");
         return cardSuits[0]; // returns default value "C"
      }
      return returnSuit;
   }

   // stores the Icons in a 2-D array
   static void loadCardIcons()
   {
      // check if array is loaded... if not load it
      if (!iconsLoaded)
      {
         String fileExt = ".gif";

         for (int i = 0; i < 4; i++)
         {
            for (int j = 0; j < 14; j++)
            {
               iconCards[j][i] = new ImageIcon("images/" + turnIntIntoCardValue(j) + turnIntIntoCardSuit(i) + fileExt);
            }
         }
         iconBack = new ImageIcon("images/BK.gif"); // Back of card (Last)
         iconsLoaded = true;
      }
   }

   // returns the value of card as an integer
   static private int valueAsInt(Card card)
   {
      char val = card.getValue();
      for (int i = 0; i < Card.values.length; i++)
      {
         if (val == Card.values[i])
         {
            return i;
         }
      }
      return -1;
   }

   // returns the suit of the card as an integer
   static private int suitAsInt(Card card)
   {
      return card.getSuit().ordinal(); // returns suit index

   }

   // takes a Card object from the client, and returns the Icon for that card
   static public Icon getIcon(Card card)
   {
      GUICard.loadCardIcons();
      int val, suit;
      val = valueAsInt(card);
      suit = suitAsInt(card);
      return iconCards[val][suit];
   }

   // another method that returns the card-back image, simpler than getIcon()
   static public Icon getBackCardIcon()
   {
      GUICard.loadCardIcons();
      return iconBack;
   }

}

// class CardGameFramework ----------------------------------------------------
// this class uses an already created class to deal cards for display from an actual deck
class CardGameFramework
{
   private static final int MAX_PLAYERS = 50; 

   private int numPlayers;
   private int numPacks; // # standard 52-card packs per deck
                         // ignoring jokers or unused cards
   private int numJokersPerPack; // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack; // # cards removed from each pack
   private int numCardsPerHand; // # cards to deal each player
   private Deck deck; // holds the initial full deck and gets
                      // smaller (usually) during play
   private Hand[] hand; // one Hand for each player
   private Card[] unusedCardsPerPack; // an array holding the cards not used
                                      // in the game. e.g. pinochle does not
                                      // use cards 2-8 of any suit

   
   public CardGameFramework(int numPacks, int numJokersPerPack, int numUnusedCardsPerPack, Card[] unusedCardsPerPack,
         int numPlayers, int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) // > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if (numCardsPerHand < 1 || numCardsPerHand > numPacks * (52 - numUnusedCardsPerPack) / numPlayers)
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   // returns card after calling dealCard()
   public Card getCardFromDeck()
   {
      return deck.dealCard();
   }

   // returns numbers of card remaining in deck
   public int getNumCardsRemainingInDeck()
   {
      return deck.getNumCards();
   }

   // initializes a new game
   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard(unusedCardsPerPack[k]);

      // add jokers
      for (k = 0; k < numPacks; k++)
         for (j = 0; j < numJokersPerPack; j++)
            deck.addCard(new Card('X', Card.Suit.values()[j]));

      // shuffle the cards
      deck.shuffle();
   }

   // deal method that checks if there are enough cards
   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard(deck.dealCard());
            else
            {
               enoughCards = false;
               break;
            }
      }

      return enoughCards;
   }

   // sorts the hands according to number of players
   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   // plays cards, checks and return valid card
   Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1 || cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         // Creates a card that does not work
         return new Card('M', Card.Suit.spades);
      }

      // return the card played
      return hand[playerIndex].playCard(cardIndex);

   }

   // checks if there are enough cards to play
   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;

      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;

      return hand[playerIndex].takeCard(deck.dealCard());
   }

}
