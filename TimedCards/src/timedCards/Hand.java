package timedCards;

/*
 * The Hand class is a simple representation of a user's hand of cards. It
 * Enforces some limit of cards, and has simple methods to add and remove cards
 * from the hand.
 * 
 * This class has been updated slightly, it no longer allows gaps in the hand,
 *   if the hand has 3 cards, they are index 0,1,2. Any place where this isn't
 *   true should be updated for this project.
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
         // shift cards down
         for (int i = index; i < numCards; i++)
         {
            myCards[i] = myCards[i + 1];
         }
         // null the last card
         myCards[numCards] = null;
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
      } else
      {
         return myCards[k];
      }
   }
}
