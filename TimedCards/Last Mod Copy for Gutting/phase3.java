
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
   public static int rank(char value)
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

   // calls the arraySort method
   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }
  
   // This method clears the hand (removes all cards)
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

   // simple Accessor for the numCard variable
   public int getNumCards()
   {
      return numCards;
   }

   /*
    * This method returns a card from a specified index in the array. It first
    * checks if given index points to a valid location. If it does not, it
    * creates a card with the errorFlag set and returns that.
    */
   public Card inspectCard(int k)
   {
      if (numCards == 0 || k < 0 || myCards[k] == null)
      {
         // Creates illegal card
         return new Card('Z', Card.Suit.spades);
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
   public final int NUM_DECKS = 6;
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

   // this method determines whether it is possible to add another card to deck
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

      if (copies < NUM_DECKS) // check if too many cards in deck
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

   
   // method shuffles the decks.
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
