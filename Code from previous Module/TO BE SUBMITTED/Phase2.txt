
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

public class Phase2
{
   static int NUM_CARDS_PER_HAND = 7; // max number of cards per hand
   static int NUM_PLAYERS = 2; // max number of players
   static Deck myDeck = new Deck(); // creates new deck
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND]; 
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];  
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   // gets a random suit and value and returns a random card
   static Card generateRandomCard()
   {
      Random random = new Random();
      Card.Suit suit;
      char val;

      int suitSelector, valSelector;

      // selects a random int to be used to select random suit and value
      suitSelector = random.nextInt(4);
      valSelector = random.nextInt(14);

      // uses the random int to select suit and value
      suit = Card.Suit.values()[suitSelector];
      val = Card.values[valSelector];

      // returns new card of suit and value
      return new Card(val, suit);
   }

   public static void main(String[] args)
   {
      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myDeck.shuffle();

      // CREATE LABELS ----------------------------------------------------
      JLabel lblComputerHand = new JLabel("Computer Hand", JLabel.CENTER);
      JLabel lblHumanHand = new JLabel("Human Hand", JLabel.CENTER);

      // and two random cards in the play region (simulating a computer/hum ply)
      Card cardA, cardB;
      cardA = generateRandomCard();
      cardB = generateRandomCard();
      JLabel labA = new JLabel(GUICard.getIcon(cardA));
      JLabel labB = new JLabel(GUICard.getIcon(cardB));

      myCardTable.pnlPlayArea.add(labA);
      myCardTable.pnlPlayArea.add(labB);

      // ADD LABELS TO PANELS -----------------------------------------
      myCardTable.pnlPlayArea.add(lblComputerHand);
      myCardTable.pnlPlayArea.add(lblHumanHand);

      // deala cards to "Hands"
      dealHands();
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         myCardTable.pnlComputerHand.add(computerLabels[i]);
         myCardTable.pnlHumanHand.add(humanLabels[i]);
      }
      // show everything to the user
      myCardTable.setVisible(true);
   }

   // deals computer and human player hands
   static void dealHands()
   {
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         JLabel lab;
         Card nextCard;

         nextCard = myDeck.dealCard();
         lab = new JLabel(GUICard.getIcon(nextCard));
         // for now we will cheat and just put the back of the card
         // computerLabels[i] = lab;
         computerLabels[i] = new JLabel(GUICard.getBackCardIcon());
         nextCard = myDeck.dealCard();
         lab = new JLabel(GUICard.getIcon(nextCard));
         humanLabels[i] = lab;
      }
   }
}

// this class embodies the JPanels and Layout(s) needed, this is where all the cards and controls will be placed
class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games

   private int numCardsPerHand; // variable for number of cards per hand
   private int numPlayers; // variable for number of players

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   // creates the play area for the game
   CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;

      BorderLayout layout = new BorderLayout();
      setLayout(layout);

      pnlComputerHand = new JPanel(new GridLayout(1, numCardsPerHand));
      setPanelVars(pnlComputerHand, "Computer Hand");
      add(pnlComputerHand, BorderLayout.NORTH);

      pnlPlayArea = new JPanel(new GridLayout(2, 2));
      setPanelVars(pnlPlayArea, "Play Area");
      add(pnlPlayArea, BorderLayout.CENTER);

      pnlHumanHand = new JPanel(new GridLayout(1, numCardsPerHand));
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

//  manages the reading and building of the card image Icons
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

/*
 * This class is a simple representation of a card object. It carries only a
 * suit and a value, as well as an internal error flag.
 */
class Card
{
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }

   public static char[] values =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };
   public static char[] valueRanks =
   { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X' };
   private char value;
   private Suit suit;
   private boolean errorFlag;

   /*
    * The basic constructor for the card class. It initializes the card to the
    * Ace of Spades
    */
   Card()
   {
      value = 'A';
      suit = Suit.spades;
      errorFlag = false;
   }

   /*
    * Card constructor for creating a deep copy. All of the internal types for
    * this class are scalers and do not require any deep copy mechanisms.
    */
   Card(Card copyCard)
   {
      value = copyCard.getValue();
      suit = copyCard.getSuit();
      errorFlag = copyCard.getErrorFlag();
   }

   /*
    * Another Constructor for the Card class which can be called with a suit and
    * a value. This constructor checks for errors and returns a card initiated
    * with "error=True" if either value is bad. The settings for the value and
    * suit are undefined when a bad values are given, so the errorFlag should be
    * checked when you make a new card to ensure that a valid object was
    * returned.
    */
   Card(char value, Suit suit)
   {
      boolean goodCard;
      goodCard = set(value, suit);
      if (goodCard)
      {
         errorFlag = false;
      }
      else
      {
         errorFlag = true;
      }
   }

   // sorts card into array utilizing bubble sort
   static void arraySort(Card[] cards, int arraySize) 
   {
      int numSwaps;

      do
      {
         numSwaps = 0;
         for (int i = 1; i < arraySize; i++)
         {
            if (rank(cards[i - 1].getValue()) > rank(cards[i].getValue()))
            {
               Card temp = cards[i - 1]; // Store first card
               cards[i - 1] = cards[i]; // Set first card equal to second
               cards[i] = temp; // Set second card equal to original value of
                                // the first card
               numSwaps++;
            }
         }
      } while (numSwaps != 0); // Once there are no swaps in for loop the array
                               // is sorted
   }

   // helper method for arraySort
   private static int rank(char value)
   {
      for (int i = 0; i < valueRanks.length; i++)
      {
         if (value == valueRanks[i])
         {
            return i;
         }
      }
      return -1;
   }

   // simple Accessor for value
   public char getValue()
   {
      return value;
   }

   // simple Accessor for suit
   public Suit getSuit()
   {
      return suit;
   }

   // simple Accessor for errorFlag
   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   /*
    * This Method sets the value and suit for the card. It checks the arguments
    * and returns true when they are correct, false otherwise. It also sets the
    * errorFlag based on the correctness of the arguments. Returns: boolean
    */
   public boolean set(char newValue, Suit newSuit)
   {
      if (isValid(newValue, newSuit))
      {
         value = newValue;
         suit = newSuit;
         errorFlag = false;
      }
      else
      {
         errorFlag = true;
         suit = newSuit;
      }
      return !errorFlag;
   }

   /*
    * This method generates, then returns a string representation of the card.
    */
   public String toString()
   {
      if (errorFlag == true)
      {
         return "[Invalid]";
      }
      return getValue() + " of " + getSuit() + " ";
   }

   /*
    * This method takes an argument for value and suit and returns true if they
    * are acceptable. Returns: boolean
    */
   private boolean isValid(char value, Suit suit)
   {
      for (int i = 0; i < values.length; i++)
      {
         if (value == values[i])
         {
            return true;
         }
      }
      return false;
   }
}

/*
 * The Hand class is a simple representation of a user's hand of cards. It
 * Enforces some limit of cards, and has simple methods to add and remove cards
 * from the hand.
 */
class Hand
{
   public static int MAX_CARDS = 50;
   private Card[] myCards;
   private int numCards;

   /*
    * Simple Constructor for Hand class. It creates an empty array to hold
    * cards, as well as setting the number of held cards to 0
    */
   Hand()
   {
      numCards = 0;
      myCards = new Card[MAX_CARDS];
   }

   /*
    * This method clears the hand (removes all cards)
    */

   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }

   public void resetHand()
   {
      for (int i = 0; i < numCards; i++)
      {
         myCards[i] = null;
      }
      numCards = 0;
   }

   /*
    * This accepts a card as an argument. It then duplicates the card and adds
    * it to the hand.
    */
   public boolean takeCard(Card card)
   {
      if (numCards != MAX_CARDS)
      {
         Card newCard = new Card(card);
         myCards[numCards] = newCard;
         numCards++;
         return true;
      }
      return false;
   }

   /*
    * This method plays the top card from the hand. It removes references to the
    * last Card in the array, and returns that object.
    */
   public Card playCard(int index) // Updated to take index parameter
   {
      Card retCard;
      if (numCards > 0)
      {
         retCard = myCards[index];
         myCards[index] = null;
         numCards--;
      }

      else
      {
         retCard = new Card('Z', Card.Suit.spades); // 'z' ensures bad card
      }
      return retCard;
   }

   /*
    * This method creates and returns a string representation of the Hand class
    * object.
    */
   public String toString()
   {
      String hand = "Hand: {";

      for (int i = 0; i < numCards; i++)
      {
         hand += myCards[i].toString() + ", ";
         if ((i + 1) % 6 == 0)
         {
            hand += "\n";
         }
      }

      hand += " } ";

      return hand;
   }

   // Simple Accessor for the numCard variable.
   public int getNumCards()
   {
      return numCards; //returns number of cards remaining in deck
   }

   /*
    * This method returns a card from a specified index in the array. It first
    * checks if given index points to a valid location. If it does not, it
    * creates a card with the errorFlag set and returns that.
    */
   public Card inspectCard(int k)
   {
      if (numCards == 0 || k < 0 || k > numCards)
      {
         // Creates illegal card
         return new Card('X', Card.Suit.spades);
      }
      else
      {
         return myCards[k];
      }
   }
}

/*
 * This class represents a deck of cards, or a shoe of cards. It has simple
 * methods for initialization, shuffling, dealing etc.
 */
class Deck
{
   public final int NUM_DECKS = 6; // number of max decks
   public final int DECK_SIZE = 56; // the size of a deck in this game
   public final int MAX_CARDS = NUM_DECKS * DECK_SIZE; // allow a maximum of six
                                                       // packs
   // (6 * 56 cards)
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard;
   private int numPacks;

   /*
    * Default constructor for Deck class. Returns a new deck with only one set
    * of cards.
    */
   Deck()
   {
      numPacks = 1;
      allocateMasterPack();

      // creates a new deck of cards 
      cards = new Card[numPacks * DECK_SIZE];
      for (int i = 0; i < DECK_SIZE; i++)
      {
         cards[i] = new Card(masterPack[i]); // duplicate the card into the
         // array.
      }
      topCard = cards.length - 1;
   }

   /*
    * Deck constructor that lets you make a shoe with an arbitrary (bit limited)
    * number of decks.
    */
   Deck(int newNumPacks)
   {
      int numCards = newNumPacks * DECK_SIZE;
      allocateMasterPack();

      if (numCards <= MAX_CARDS && numCards > 0)
      {
         numPacks = newNumPacks;
         cards = new Card[numCards];
         for (int k = 0; k < numCards; k++)
         {
            Card newCard = new Card(masterPack[k % DECK_SIZE]);
            cards[k] = newCard;
         }
         topCard = cards.length - 1;
      }

      else
      {
         System.out.println("Error: Invalid number of decks");
      }
   }

   /*
    * This Method re-initializes the deck. If the deck hasn't been used it
    * returns without touching the deck.
    */
   public void init(int numPacks)
   {
      if (topCard + 1 == numPacks * DECK_SIZE)
      {
         return;
      }

      // if we pass the return then the deck was at least partially used
      for (int x = 0; x < cards.length; ++x)
      {
         cards[x] = null;
         cards[x] = new Card(masterPack[x % DECK_SIZE]);
      }
      topCard = cards.length - 1;
   }

   // this method determines whether it is possible to add anothr card to deck
   public boolean addCard(Card card)
   {
      int copies = 0;
      int length = 0;

      // check deck for another copy of card  
      for (int i = 0; i < cards.length; i++)                                   
      {
         if (cards[i].getSuit() == card.getSuit() && cards[i].getValue() == card.getValue())
         {
            copies++;
         }
      }

      // check if too many cards in deck
      if (copies < NUM_DECKS) 
      {
         length = cards.length;
         cards[length] = card;
         topCard = length;
         return true;
      }
      return false;
   }

   // simple method to get the number of cards remaining in the deck
   public int getNumCards()
   {
      return cards.length;
   }

   // puts all of the cards in the deck back into the right order according to their values
   public void sort()
   {
      Card.arraySort(cards, topCard + 1);
   }

   
   // shuffles the decks
   public void shuffle()
   {
      int index;
      Card temp;
      Random random = new Random();
      for (int i = cards.length - 1; i > 0; i--)
      {
         index = random.nextInt(i + 1);
         temp = cards[index];
         cards[index] = cards[i];
         cards[i] = temp;
      }
   }

   // simple accessor for topCard
   public int topCardAccessor()
   {
      return topCard;
   }
   
   // removes a specific card from deck
   public boolean removeCard(Card card)
   {
      int index = -1;
      for (int i = 0; i < cards.length; i++)
      {
         // check if desired card is still in deck
         if (cards[i].getSuit() == card.getSuit() && cards[i].getValue() == card.getValue())
         {
            index = i;
            break;
         }
      }
      if (index >= 0)
      {
         cards[index] = cards[topCard];
         cards[topCard] = null;
         topCard--;
         return true;
      }
      return false;
   }

   /*
    * This method takes the top card from the deck and "deals it" thus removing
    * it from the deck.
    */
   public Card dealCard()
   {
      Card retCard;
      if (topCard >= 0)
      {
         retCard = cards[topCard];
         topCard--;
      }
      else
      {
         retCard = new Card('z', Card.Suit.clubs); // 'z' forces a card with
         // errorFlag set.
      }
      return retCard;
   }

   /*
    * This function returns the card at a specific index. It returns a bad card
    * if an out-of-index argument is made.
    */
   public Card inspectCard(int index)
   {
      if (!(index >= 0 && index < topCard))
      {
         Card badCard = new Card();
         badCard.set('z', Card.Suit.clubs); // this will receive an error flag
         // because of "z"
         return badCard;
      }
      else
      {
         return cards[index];
      }
   }

   /*
    * This Method creates a masterPack of cards for later use if and only if it
    * wasn't created already.
    */
   private static void allocateMasterPack()
   {
      int index = 0;

      if (masterPack == null)
      {
         masterPack = new Card[56];
         for (int i = 0; i < Card.Suit.values().length; i++)
         {
            for (int j = 0; j < Card.values.length; j++)
            {
               masterPack[index] = new Card(Card.values[j], Card.Suit.values()[i]);
               index++;
            }
         }
      }
   }
}
