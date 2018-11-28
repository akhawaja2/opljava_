package edu.ramapo.khawaja.casino;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageButton bottomCard1, bottomCard2, bottomCard3, bottomCard4, topCard1, topCard2, topCard3, topCard4;
    ImageButton tableCard1, tableCard2, tableCard3, tableCard4;
    Button build, capture, trail;
    final Round rounds = new Round(false);
    ArrayList<Player> players = rounds.getPlayers();
    boolean isPressed = false;
    boolean movePressed = false;
    Card selectedCard = new Card();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rounds.dealCards(true, true, true);

    }
    public void onClick(View view)
    {
        LinearLayout playerHand = findViewById(R.id.playerHand);

        TextView deckPrintOut =  findViewById(R.id.gameData);
        deckPrintOut.setMovementMethod(new ScrollingMovementMethod());
        switch (view.getId())
        {
            case R.id.playerCard1:
            {
                if (isPressed)
                {
                    view.findViewById(view.getId()).setBackgroundResource(0);
                    isPressed = false;
                }
                else
                {
                    view.findViewById(view.getId()).setBackground(getResources().getDrawable(R.drawable.button_border));
                    isPressed = true;

                    View v = playerHand.getChildAt(0);
                    selectedCard = (Card) v.getTag();

                    deckPrintOut.append("Selected: " + selectedCard.print() + " \n");
                }
                break;
            }
            case R.id.playerCard2:
             {
                if (isPressed)
                {
                    view.findViewById(view.getId()).setBackgroundResource(0);
                    isPressed = false;
                }
                else
                {
                    view.findViewById(view.getId()).setBackground(getResources().getDrawable(R.drawable.button_border));
                    isPressed = true;
                    View v = playerHand.getChildAt(1);
                    selectedCard = (Card) v.getTag();

                    deckPrintOut.append("Selected: " + selectedCard.print() + " \n");
                }
                break;
            }
            case R.id.playerCard3: {
                if (isPressed) {
                    view.findViewById(view.getId()).setBackgroundResource(0);
                    isPressed = false;
                } else {
                    view.findViewById(view.getId()).setBackground(getResources().getDrawable(R.drawable.button_border));
                    isPressed = true;
                    View v = playerHand.getChildAt(2);
                    selectedCard = (Card) v.getTag();

                    deckPrintOut.append("Selected: " + selectedCard.print() + " \n");
                }
                break;
            }
            case R.id.playerCard4: {
                if (isPressed) {
                    view.findViewById(view.getId()).setBackgroundResource(0);
                    isPressed = false;
                } else {
                    view.findViewById(view.getId()).setBackground(getResources().getDrawable(R.drawable.button_border));
                    isPressed = true;
                    View v = playerHand.getChildAt(3);
                    selectedCard = (Card) v.getTag();

                    deckPrintOut.append("Selected: " + selectedCard.print() + " \n");
                }
                break;
            }
            case R.id.trail: {
                if (movePressed) {
                    movePressed = false;
                } else {
                    movePressed = true;
                    deckPrintOut.append("Selected trail\n");
                    if (isPressed)
                    {
                        deckPrintOut.append("Selected card " + "\n" + "Trailing card. \n");

                    }
                    clearView(view);
                    rounds.getTable().getLooseCards().add(selectedCard);
                    players.get(0).removeCardFromHand(selectedCard);
                    loadGameView(view);
                }
                break;
            }
            case R.id.build:
            {
                deckPrintOut.append("build \n");
                break;
            }
            case R.id.capture:
            {
                LinearLayout layout = findViewById(R.id.tableLayout);
                for ( int i = 0; i < layout.getChildCount(); i++)
                {
                    View v = layout.getChildAt(i);
                    Card tableCard = (Card) v.getTag();
                    deckPrintOut.append(tableCard.print() + "\n");
                }

                if (isPressed)
                {
                    deckPrintOut.append("Selected card " + selectedCard.print() + "\n");
                    ArrayList<Card> matchingCards = rounds.getTable().getMatchingLooseCards(selectedCard);

                    for (int i = 0; i < matchingCards.size(); i++)
                    {
                        deckPrintOut.append("Found matching card: " +  matchingCards.get(i).print() + "\n");
                    }
                    if (matchingCards.size() > 0)
                    {
                        players.get(0).addCardInPile(matchingCards, selectedCard);
                        clearView(view);
                        players.get(0).removeCardFromHand(selectedCard);
                        rounds.getTable().removeMatchingLooseCardsFromtable(matchingCards);
                        loadGameView(view);
                    }
                    else
                    {
                        deckPrintOut.append("No valid capture card. \n");
                    }

                }
                //rounds.getTable().getCaptureCardsForPlayedCard(Card playedCard, players, int current`Player,
               // int opponent, boolean addCards, boolean applyAction)
                break;
            }
        }
    }
    public void goToFlip(View view)
    {
        setContentView(R.layout.coin_flip);
    }
    public void coinFlip(View view)
    {
        Random rand = new Random();
        int choice = 0;
        int result = rand.nextInt(2) + 1;

        switch (view.getId())
        {
            case R.id.coin:
                choice = 1;
                break;
            case R.id.coin2:
                choice = 2;
                break;
        }
        setContentView(R.layout.table_view);
        loadGameView(view);
        TextView gameData = findViewById(R.id.gameData);
        if (result == choice)
        {
            gameData.append("\nYou won the toss!\n");
        }
        else
        {
            gameData.append("\nYou lost the toss!\n");
        }

    }
    public void clearView(View view)
    {
        ArrayList<ImageButton> playerHand = new ArrayList<ImageButton>() {{
            add(bottomCard1);
            add(bottomCard2);
            add(bottomCard3);
            add(bottomCard4);
            // etc.
        }};
        ArrayList<ImageButton> tableHand = new ArrayList<ImageButton>() {{
            add(tableCard1);
            add(tableCard2);
            add(tableCard3);
            add(tableCard4);
            // etc.
        }};
        for (int i = 0; i < rounds.getPlayers().get(0).getHand().size(); i++)
        {
            playerHand.get(i).setImageResource(android.R.color.transparent);
        }
        for (int i = 0; i < rounds.getTable().getLooseCards().size(); i++)
        {
            tableHand.get(i).setImageResource(android.R.color.transparent);
        }

    }
    public void loadGameView (View view)
    {

        bottomCard1 =  findViewById(R.id.playerCard1);
        bottomCard1.setOnClickListener(this);
        bottomCard2 =  findViewById(R.id.playerCard2);
        bottomCard2.setOnClickListener(this);
        bottomCard3 =  findViewById(R.id.playerCard3);
        bottomCard3.setOnClickListener(this);
        bottomCard4 = findViewById(R.id.playerCard4);
        bottomCard4.setOnClickListener(this);

        build = findViewById(R.id.build);
        build.setOnClickListener(this);
        capture = findViewById(R.id.capture);
        capture.setOnClickListener(this);
        trail = findViewById(R.id.trail);
        trail.setOnClickListener(this);
        //Setting clickability to buttons

        topCard1 =  findViewById(R.id.compCard1);
        topCard2 =  findViewById(R.id.compCard2);
        topCard3 =  findViewById(R.id.compCard3);
        topCard4 =  findViewById(R.id.compCard4);


        tableCard1 = findViewById(R.id.tableCard1);
        tableCard2 =findViewById(R.id.tableCard2);
        tableCard3 =findViewById(R.id.tableCard3);
        tableCard4 = findViewById(R.id.tableCard4);
        ArrayList<ImageButton> playerHand = new ArrayList<ImageButton>() {{
            add(bottomCard1);
            add(bottomCard2);
            add(bottomCard3);
            add(bottomCard4);
            // etc.
        }};
        ArrayList<ImageButton> tableHand = new ArrayList<ImageButton>() {{
            add(tableCard1);
            add(tableCard2);
            add(tableCard3);
            add(tableCard4);
            // etc.
        }};
        for (int i = 0; i < players.get(0).getHand().size(); i++)
        {
            assignImages(players.get(0).getHand().get(i), playerHand.get(i));
        }
        /*for (int i = 0; i < players.get(1).getHand().size(); i++)
        {

        }*/
        /*assignImages(players.get(0).getHand().get(0), bottomCard1);
        assignImages(players.get(0).getHand().get(1), bottomCard2);
        assignImages(players.get(0).getHand().get(2), bottomCard3);
        assignImages(players.get(0).getHand().get(3), bottomCard4);*/

        assignImages(players.get(1).getHand().get(0), topCard1);
        assignImages(players.get(1).getHand().get(1), topCard2);
        assignImages(players.get(1).getHand().get(2), topCard3);
        assignImages(players.get(1).getHand().get(3), topCard4);

        /*assignImages(rounds.getTable().getLooseCards().get(0), tableCard1);
        assignImages(rounds.getTable().getLooseCards().get(1), tableCard2);
        assignImages(rounds.getTable().getLooseCards().get(2), tableCard3);
        assignImages(rounds.getTable().getLooseCards().get(3), tableCard4);*/

        for (int i = 0; i < rounds.getTable().getLooseCards().size(); i++)
        {
            if (rounds.getTable().getLooseCards().get(i) == null)
            {
                tableHand.get(i).setImageResource(android.R.color.transparent);
            }
            assignImages(rounds.getTable().getLooseCards().get(i), tableHand.get(i));
        }
    }
    public void showDeck(View view)
    {
        TextView deckPrintOut =  findViewById(R.id.gameData);
        String deckString = " ";
        deckString = rounds.getDeck().printDeck();

        deckPrintOut.append("Deck: " + deckString + "\n");
        deckPrintOut.append("Deck size: " + rounds.getDeck().getSize() + "\n");

        ArrayList<Player> plays = rounds.getPlayers();


        deckPrintOut.append("\nPlayer " + plays.get(1).getName());
        deckPrintOut.append(plays.get(1).printHand() + "\n");
        deckPrintOut.append("\nPlayer " + plays.get(0).getName()) ;
        deckPrintOut.append(plays.get(0).printHand() + "\n");
        deckPrintOut.setMovementMethod(new ScrollingMovementMethod());

        deckPrintOut.append("Table: " + rounds.getTable().printTable());
        //deckPrintOut.append(rounds.getTable().printTable() + "\n");
    }


    public void assignImages(Card card, ImageButton image)
    {
        Card assignedCard;

        if (card.toStr().equals("SA"))
        {
           //image.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card1s,0);
            image.setImageResource(R.drawable.card1s);
            assignedCard = new Card("S", 1, "A");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("DA"))
        {
            image.setImageResource(R.drawable.card1d);
            assignedCard = new Card("D", 1, "A");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("CA"))
        {
            image.setImageResource(R.drawable.card1c_90);
            assignedCard = new Card("C", 1, "A");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("HA"))
        {
            image.setImageResource(R.drawable.card1h);
            assignedCard = new Card("H", 1, "A");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S2"))
        {
            image.setImageResource(R.drawable.card2s);
            assignedCard = new Card("S", 2, "2");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D2"))
        {
            image.setImageResource(R.drawable.card2d);
            assignedCard = new Card("D", 2, "2");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C2"))
        {
            image.setImageResource(R.drawable.card2c);
            assignedCard = new Card("C", 2, "2");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H2"))
        {
            image.setImageResource(R.drawable.card2h);
            assignedCard = new Card("H", 2, "2");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H3"))
        {
            image.setImageResource(R.drawable.card3h);
            assignedCard = new Card("H", 3, "3");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D3"))
        {
            image.setImageResource(R.drawable.card3d);
            assignedCard = new Card("D", 3, "3");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C3"))
        {
            image.setImageResource(R.drawable.card3c);
            assignedCard = new Card("C", 3, "3");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S3"))
        {
            image.setImageResource(R.drawable.card3s);
            assignedCard = new Card("S", 3, "3");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S4"))
        {
            image.setImageResource(R.drawable.card4s);
            assignedCard = new Card("S", 4, "4");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D4"))
        {
            image.setImageResource(R.drawable.card4d);
            assignedCard = new Card("D", 4, "4");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C4"))
        {
            image.setImageResource(R.drawable.card4c);
            assignedCard = new Card("C", 4, "4");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H4"))
        {
            image.setImageResource(R.drawable.card4h);
            assignedCard = new Card("H", 4, "4");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S5"))
        {
            image.setImageResource(R.drawable.card5s);
            assignedCard = new Card("S", 5, "5");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D5"))
        {
            image.setImageResource(R.drawable.card5d);
            assignedCard = new Card("D", 5, "5");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C5"))
        {
            image.setImageResource(R.drawable.card5c);
            assignedCard = new Card("C", 5, "5");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H5"))
        {
            image.setImageResource(R.drawable.card5h);
            assignedCard = new Card("H", 5, "5");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S6"))
        {
            image.setImageResource(R.drawable.card6s);
            assignedCard = new Card("S", 6, "6");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D6"))
        {
            image.setImageResource(R.drawable.card6d);
            assignedCard = new Card("D", 6, "6");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C6"))
        {
            image.setImageResource(R.drawable.card6c);
            assignedCard = new Card("C", 6, "6");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H6"))
        {
            image.setImageResource(R.drawable.card6h);
            assignedCard = new Card("H", 6, "6");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S7"))
        {
            image.setImageResource(R.drawable.card7s);
            assignedCard = new Card("S", 7, "7");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D7"))
        {
            image.setImageResource(R.drawable.card7d);
            assignedCard = new Card("D", 7, "7");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C7"))
        {
            image.setImageResource(R.drawable.card7c);
            assignedCard = new Card("C", 7, "7");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H7"))
        {
            image.setImageResource(R.drawable.card7h);
            assignedCard = new Card("H", 7, "7");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H8"))
        {
            image.setImageResource(R.drawable.card8h);
            assignedCard = new Card("H", 8, "8");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S8"))
        {
            image.setImageResource(R.drawable.card8s);
            assignedCard = new Card("S", 8, "8");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D8"))
        {
            image.setImageResource(R.drawable.card8d);
            assignedCard = new Card("D", 8, "8");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C8"))
        {
            image.setImageResource(R.drawable.card8c);
            assignedCard = new Card("C", 8, "8");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("H9"))
        {
            image.setImageResource(R.drawable.card9h);
            assignedCard = new Card("H", 9, "9");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("S9"))
        {
            image.setImageResource(R.drawable.card9s);
            assignedCard = new Card("S", 9, "9");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("D9"))
        {
            image.setImageResource(R.drawable.card9d);
            assignedCard = new Card("D", 9, "9");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("C9"))
        {
            image.setImageResource(R.drawable.card9c);
            assignedCard = new Card("C", 9, "9");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("HX"))
        {
            image.setImageResource(R.drawable.cardth);
            assignedCard = new Card("H", 10, "X");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("SX"))
        {
            image.setImageResource(R.drawable.cardts);
            assignedCard = new Card("S", 10, "X");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("DX"))
        {
            image.setImageResource(R.drawable.cardtd);
            assignedCard = new Card("D", 10, "X");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("CX"))
        {
            image.setImageResource(R.drawable.cardtc);
            assignedCard = new Card("C", 10, "X");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("HJ"))
        {
            image.setImageResource(R.drawable.cardjh);
            assignedCard = new Card("H", 11, "J");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("SJ"))
        {
            image.setImageResource(R.drawable.cardjs);
            assignedCard = new Card("S", 11, "J");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("DJ"))
        {
            image.setImageResource(R.drawable.cardjd);
            assignedCard = new Card("D", 11, "J");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("CJ"))
        {
            image.setImageResource(R.drawable.cardjc);
            assignedCard = new Card("C", 11, "J");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("HQ"))
        {
            image.setImageResource(R.drawable.cardqh);
            assignedCard = new Card("H", 12, "Q");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("SQ"))
        {
            image.setImageResource(R.drawable.cardqs);
            assignedCard = new Card("S", 12, "Q");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("DQ"))
        {
            image.setImageResource(R.drawable.cardqd);
            assignedCard = new Card("D", 12, "Q");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("CQ"))
        {
            image.setImageResource(R.drawable.cardqc);
            assignedCard = new Card("C", 12, "Q");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("HK"))
        {
            image.setImageResource(R.drawable.cardkh);
            assignedCard = new Card("H", 13, "K");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("SK"))
        {
            image.setImageResource(R.drawable.cardks);
            assignedCard = new Card("S", 13, "K");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("DK"))
        {
            image.setImageResource(R.drawable.cardkd);
            assignedCard = new Card("D", 13, "K");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("CK"))
        {
            image.setImageResource(R.drawable.cardkc);
            assignedCard = new Card("C", 13, "K");
            image.setTag(assignedCard);
        }
    }

}
