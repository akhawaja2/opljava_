package edu.ramapo.khawaja.casino;

import java.util.ArrayList;
import java.util.Collections;

public class Deck
{

    public Deck()
    {

    }

    private ArrayList< Card > deckCards = new ArrayList<>();
    ArrayList <Card> getDeckCards()
    {
        return deckCards;
    }

    void addCard(Card card)
    {
        deckCards.add(card);
    }
    void autoPopulateDeck()
    {
        String suits[] = { "S" , "H" ,"C", "D" };
        //Each time I iterate through this outer loop I move through the suits array

        for (int i = 0; i < 4; i++)
        {
            Card king = new Card(suits[i], 13, "K");
            deckCards.add(king);
            Card queen = new Card(suits[i], 12, "Q");
            deckCards.add(queen);
            Card jack = new Card(suits[i], 11, "J");
            deckCards.add(jack);
            Card ace = new Card(suits[i], 14, "A");
            deckCards.add(ace);

            for (int j = 2; j < 11; j++)
            {

                if (j == 10)
                {
                    Card ten = new Card(suits[i], 10, "X");
                    deckCards.add(ten);
                }
                else
                {
                    Card Card = new Card(suits[i], j, Integer.toString((j)));
                    deckCards.add(Card);
                }
            }
        }
        Collections.shuffle(deckCards);
    }
    public Card drawCard()
    {
        Card CardToReturn = new Card(deckCards.get(0).getSuit(), deckCards.get(0).getNumber(), deckCards.get(0).getSymbol());
        deckCards.remove(deckCards.get(0));
        return CardToReturn;
    }
    public ArrayList<Card> drawFourCards()
    {
        ArrayList<Card> cards = new ArrayList<>();
        if (deckCards.size() == 0)
        {
            return cards;
        }
        cards.add(drawCard());
        cards.add(drawCard());
        cards.add(drawCard());
        cards.add(drawCard());

        return cards;
    }

    public void printDeck()
    {
        System.out.println("-----------------------------Entered printDeck-----------------------------");
        for (int i = 0; i < deckCards.size(); i++)
        {
            deckCards.get(i).print();
            System.out.print(" ");

        }
        System.out.println("-----------------------------exit printDeck-----------------------------");
    }
}
