package edu.ramapo.khawaja.casino;
import java.util.*;

public class Card
{
    String suit;
    int number;
    String symbol;
    public Card(String suit, int number, String symbol)
    {
        //Suit is the suit of the card (HDSC)
        this.suit = suit;
        //Number is the number value
        this.number = number;
        //Symbol is the 'number' value string (so 2-10 + JQKA)
        this.symbol = symbol;
    }

    public String getSuit()
    {
        return this.suit;
    }

    public int getNumber()
    {
        return number;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public boolean compareCards(ArrayList cards)
    {
        //The sum of the cards in the vector
        int cardsSum = 0;
        //By default I set the ace's presence to false.
        boolean acePresentInCards = false;
        //For every card in the vector of cards passed in I take the number of the card and
        //Then I add it to the cardsSum initialized earlier. I also check if the current card in the loop
        //Has an Ace in it. If it does I update the boolean value from earlier.
        for (int i = 0; i < cards.size(); i++)
        {
            Card currentCard = (Card) cards.get(i);
            cardsSum = cardsSum + currentCard.getNumber();
            if (currentCard.getSymbol() == "A")
            {
                acePresentInCards = true;
            }
        }
        //Checking if the current card is an ace and if there is an ace in the vector of cards
        if (this.symbol == "A")
        {
            if (acePresentInCards)
            {
                if (1 == cardsSum || 1 == cardsSum - 13 || 14 == cardsSum || 14 == cardsSum - 13)
                    return true;
            }
            else if (1 == cardsSum || 14 == cardsSum)
            {
                return true;
            }
        }
        else
        {
            //Otherwise if the card is not an A but an ace is present in the cards
            //I check again
            if (acePresentInCards)
            {
                if (this.number == cardsSum || this.number == cardsSum - 13)
                {
                    return true;
                }

            }
            else if (this.number == cardsSum)
            {
                return true;
            }
            return false;
        }
        return false;
    }
    //Vector combinations = vector < vector<Card> > combination
    public static void printCombinationCards(ArrayList combination)
    {
        //cout << "[ ";
        for (int i = 0; i < combination.size(); i++)
        {
            //printCards(combination.at(i));
            //cout << " ";
        }
        //cout << "] ";
    }

    public void print()
    {
        System.out.print(this.suit + this.symbol);
    }
    /*Functions missing:
    * printCombinationCardsWhole
    *printCards
     * printCardsWhole
      * print
      * convertStringToCard*/
}
