package edu.ramapo.khawaja.casino;
import java.util.*;

public class Card
{
    String suit;
    int number;
    String symbol;
    public Card ()
    {

    }
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
    public String printCombinationCards(ArrayList<ArrayList<Card>> combination)
    {
        String combinationCard = "[ ";
        String card = "";
        //cout << "[ ";
        for (int i = 0; i < combination.size(); i++)
        {
            card +=  printCards(combination.get(i));
            combinationCard += printCards(combination.get(i));
            //cout << " ";
        }
        combinationCard +=  "] ";

        return combinationCard;
    }
    void printCombinationCardsWhole(ArrayList< ArrayList<Card> > combination)
    {
        Card cardToPrint = new Card();
        for (int i = 0; i < combination.size(); i++)
        {
            cardToPrint.printCardsWhole(combination.get(i));
        }
    }

    public String print()
    {
        return (this.suit + this.symbol);
    }

    String printWhole()
    {
        String message = "";
        message += this.symbol + " of ";
        System.out.print(this.symbol + " of ");
        if (this.suit == "S")
        {
            System.out.print("spades");
            message += "spades";
        }
        if (this.suit == "D")
        {
            System.out.print("diamonds");
            message += "diamonds";
        }
        if (this.suit == "H")
        {
            System.out.print("hearts");
            message += "hearts";
        }
        if (this.suit == "C")
        {
            System.out.print("clubs");
            message += "clubs";
        }
        return message;
    }

    void printCardsWhole(ArrayList<Card> Cards)
    {
        if (Cards.size()>1)
            System.out.print( "A set of ");
        for (int i = 0; i < Cards.size(); i++)
        {
            Cards.get(i).printWhole();
            if (i == Cards.size() - 2)
            {
                System.out.print(" and ");
            }
            else if (Cards.size() > 1)
            {
                System.out.print(", ");
            }
        }
    }

    String printCards(ArrayList<Card> Cards)
    {
        String cardToPrint = "[ ";
        for (int i = 0; i < Cards.size(); i++)
        {
            cardToPrint += Cards.get(i).print();
            System.out.print(" ");
        }
        cardToPrint += " ]";

        return cardToPrint;
    }

    String toStr()
    {
        String cardStr = "";

        cardStr += suit + symbol;

        return cardStr;
    }
    /*Functions missing:
    * printCombinationCardsWhole
      * print
      * convertStringToCard*/
}
