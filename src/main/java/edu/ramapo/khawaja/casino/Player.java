package edu.ramapo.khawaja.casino;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Player
{
    public Player()
    {

    }
    public Player(String name)
    {
        this.score = 0;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public int getScore()
    {
        return score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }

    ArrayList <Card> getHand()
    {
        return hand;
    }

    ArrayList<Card> getPile()
    {
        return pile;
    }
    Card validatePlayedCard()
    {
        while (true)
        {
            Scanner reader = new Scanner(System.in);
            String Card = reader.nextLine();
            //For every Card in the users hand if the Card at the hand's
            //Suit and symbol is equivalent to the Card entered return the Card
            for (int i = 0; i < hand.size(); i++)
            {
                if (hand.get(i).getSuit() + hand.get(i).getSymbol() == Card)
                {
                    return hand.get(i);
                }
            }
            System.out.println("Invalid Card - try again!:  ");
        }

    }

    public void addCardInHand(ArrayList<Card> Cards)
    {
        //System.out.print("ENTERED ACIH");
        for (int i = 0; i < Cards.size(); i++)
        {
            hand.add(Cards.get(i));
        }
    }
        void addOneCardInPile(Card Card)
    {
        pile.add(Card);
    }
        void addOneCardInHand(Card Card)
    {
        hand.add(Card);

    }

    void removeCardFromHand(Card Card)
    {
        //Here I loop through the users hand and if the Card at i is equal to the passed in Card I
        //erase the Card from the user hand.
        for (int i = 0; i < hand.size(); i++)
        {
            if (Card.getNumber() == hand.get(i).getNumber() && Card.getSuit() == hand.get(i).getSuit())
            {
                hand.remove(i);
            }
        }
    }

    void incrementScoreBy(int increment)
    {
        this.score = this.score + increment;
    }

    int getCardCount(String suit)
    {
        int count = 0;
        //For every Card in the users pile - if the Card is an ace I increment count
        //Otherwise if the Card follows one of the specified Cards in the score I increment count
        //and then return it
        for (int i = 0; i < pile.size(); i++)
        {
            if (suit == "A")
            {
                if (pile.get(i).getSymbol() == suit)
                {
                    count++;
                }
            }
            else
            {
                if (pile.get(i).getSuit() == suit)
                {
                    count++;
                }
            }
        }
        return count;
    }

    public void addCardInPile(ArrayList<Card> Cards, Card playedCard)
    {
        for (int i = 0; i < Cards.size(); i++)
        {
            if (!(playedCard.getNumber() == Cards.get(i).getNumber() && playedCard.getSuit() == Cards.get(i).getSuit()))
            {
                pile.add(Cards.get(i));
            }
        }
        removeDuplicates(pile);
    }
    void removeDuplicates(ArrayList<Card> Cards)
    {
        //vector< int> indexes;
        //For each Card in the Card
        for (int i = 0; i < Cards.size(); i++)
        {
            for (int j = 0; j < Cards.size(); j++)
            {
                //If i and j aren't equal and the Card at i is equal to the Card at j I erase the Card and break out of this loop
                if (i != j && Cards.get(i).getNumber() == Cards.get(j).getNumber() && Cards.get(i).getSuit() == Cards.get(j).getSuit())
                {
                    Cards.remove(Cards.get(i));
                    i = 0;
                    j = 0;
                    break;
                }
            }
        }
    }

    public boolean findCard(Card x)
    {
        //For every Card in the users pile - if the Card is an ace I increment count
        //Otherwise if the Card follows one of the specified Cards in the score I increment count
        //and then return it
        for (int i = 0; i < pile.size(); i++)
        {
            if (pile.get(i).getSymbol() == x.getSymbol() && pile.get(i).getSuit() == x.getSuit())
            {
                return true;
            }

        }
        return false;
    }
    public int calculateScore(Player you, Player opponent)
    {
        ArrayList <Card> yourPile = you.getPile();
        //Creating 10 of diamonds and 2 of spades for checking if they are in user pile
        Card d = new Card("D", 10, "X");

        Card s = new Card("S", 2, "2");

        if (you.getPile().size() > opponent.getPile().size())
        {
            you.incrementScoreBy(3);
        }
        if (you.getCardCount("S") > opponent.getCardCount("S"))
        {
            you.incrementScoreBy(1);
        }
        if (you.findCard(d))
        {
            you.incrementScoreBy(2);
        }
        if (you.findCard(s))
        {
            you.incrementScoreBy(1);
        }
        if (opponent.findCard(d))
        {
            opponent.incrementScoreBy(2);
        }
        if (opponent.findCard(s))
        {
            opponent.incrementScoreBy(1);
        }
        if (you.getCardCount("S") >= 2)
        {
            you.incrementScoreBy(1);
        }
        you.incrementScoreBy(you.getCardCount("A"));
        return you.getScore();
    }
    String printPile()
    {
        String pileStr = " ";
        pileStr += "\n" + this.name + "'s pile: [ ";
        //Looping through the pile and printing it
        for (int i = 0; i < pile.size(); i++)
        {
            pileStr +=  pile.get(i).print();
            pileStr += " ";
        }
        pileStr += " ]\n";
        return pileStr;
    }
    String printHand()
    {
        String handOutput = "";
        handOutput += "\n" + this.name + "'s hand:  ";
        //Looping through the pile and printing it
        for (int i = 0; i < hand.size(); i++)
        {
            handOutput += hand.get(i).print();
            handOutput += " ";
        }
        return handOutput;
    }


    public int score;
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Card> pile = new ArrayList<>();
    public String name;

    //missing:
    //printdetails
    //printpile
    //removecardfromhand ***** Might not be correct
    //getcardcount
    //addcardinpile
    //removeduplicates
    //findcard
    //calculatescore
}
