package timedCards;

import java.util.Random;

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

   // puts all of the cards in the deck back into the right order according to
   // their values
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
      } else
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
      } else
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
