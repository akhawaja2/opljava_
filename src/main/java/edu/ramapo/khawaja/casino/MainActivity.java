package edu.ramapo.khawaja.casino;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static android.bluetooth.BluetoothClass.Device.Major.COMPUTER;


public class MainActivity  extends AppCompatActivity implements View.OnClickListener
{
    ImageButton bottomCard1, bottomCard2, bottomCard3, bottomCard4, topCard1, topCard2, topCard3, topCard4;
    ImageButton tableCard1, tableCard2, tableCard3, tableCard4;
    Button build, capture, trail;
    Round rounds = new Round(false);
    ArrayList<Player> players = rounds.getPlayers();
    boolean isPressed = false;
    boolean movePressed = false;
    Card selectedCard = new Card();
    ArrayList<ImageButton> playerHand = new ArrayList<ImageButton>();

    ArrayList<ImageButton> tableHand = new ArrayList<ImageButton>();
    ArrayList<ImageButton> computerHand = new ArrayList<ImageButton>();
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
                        //deckPrintOut.append("Selected card " + "." + "Trailing card. \n");
                    }
                    if (rounds.getTable().getLooseCards().size() >= 4)
                    {
                        ImageButton cardToTrail = new ImageButton(this);
                        cardToTrail.setBackground(null);

                        tableHand.add(cardToTrail);

                        LinearLayout tableLayout = findViewById(R.id.tableLayout);


                        tableLayout.addView(cardToTrail, rounds.getTable().getLooseCards().size());
                        LinearLayout.LayoutParams cardLayout = new LinearLayout.LayoutParams(tableLayout.getHeight(), tableLayout.getHeight());
                        cardToTrail.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        cardToTrail.setLayoutParams(cardLayout);
                        clearView(view);
                        rounds.getTable().getLooseCards().add(selectedCard);
                        players.get(0).removeCardFromHand(selectedCard);
                    }
                    else
                    {
                        clearView(view);
                        rounds.getTable().getLooseCards().add(selectedCard);
                        players.get(0).removeCardFromHand(selectedCard);
                        //rounds.computerGameEngine(Round.PLAYER.COMPUTER, Round.PLAYER.HUMAN);
                    }
                    rounds.computerGameEngine(Round.PLAYER.COMPUTER, Round.PLAYER.HUMAN);
                    if ( rounds.getTable().getLooseCards().size() > tableHand.size())
                    {
                        ImageButton computerCardToTrail = new ImageButton(this);
                        computerCardToTrail.setBackground(null);
                        //cardToTrail.setTag(selectedCard.getTag());
                        tableHand.add(computerCardToTrail);
                        LinearLayout tableLayout = findViewById(R.id.tableLayout);
                        LinearLayout.LayoutParams cardLayout = new LinearLayout.LayoutParams(tableLayout.getHeight(), tableLayout.getHeight());
                        computerCardToTrail.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        computerCardToTrail.setLayoutParams(cardLayout);
                        tableLayout.addView(computerCardToTrail);
                        clearView(view);
                    }
                    while (rounds.getPlayers().get(0).getHand().size() == 0 && rounds.getPlayers().get(1).getHand().size() > 0)
                    {
                        rounds.computerGameEngine(Round.PLAYER.COMPUTER, Round.PLAYER.HUMAN);
                    }
                    if (rounds.getPlayers().get(0).getHand().size() == 0 && rounds.getPlayers().get(1).getHand().size() == 0)
                    {
                        rounds.dealCards(true, true, false);
                    }

                    rounds.checkForRoundEnd(Round.PLAYER.HUMAN, Round.PLAYER.COMPUTER);
                    if (rounds.getPlayers().get(0).getHand().size() == 0 && rounds.getPlayers().get(1).getHand().size() == 0 && rounds.getTable().getLooseCards().size() == 0)
                    {
                        clearView(view);
                        checkForWin();
                    }
                    loadGameView(view);
                }
                break;
            }
            case R.id.build:
            {
                if (movePressed) {
                    movePressed = false;
                } else {


                    if (isPressed)
                    {
                        ArrayList<Build> builds = rounds.getTable().checkBuildOptions(selectedCard, players.get(Round.PLAYER.HUMAN.getPlayerVal()).getHand());
                        while ((builds.size() > 0))
                        {
                            int index = 0;
                            //I show the user what build options they have, store all the indexes of builds with
                            //"N" and "n" incase the user does not want to build
                            System.out.print("----------The following builds are available for you----------\n");
                            Build availableBuildsPrint = new Build();
                            availableBuildsPrint.printBuilds(builds);
                            System.out.print("Please choose the list number(1,2, etc) of the build you want to make (enter N/n to leave): ");

                            //When the user selects an option I print the build created,
                            //add the build to builds vector (a vector of builds containing all the builds),
                            //And then remove the loose Cards from the table (Since they are in a build now).
                            System.out.print("-----The following build Has been formed------\n");
                            builds.get(index).print();
                            clearView(view);
                            rounds.getTable().addBuild(builds.get(builds.size()-1));
                            rounds.getTable().removeMatchingLooseCardsFromtable(builds.get(builds.size()-1).getBuildCards());
                            //I remove the played Card from the hand, and erase the build
                            players.get(Round.PLAYER.HUMAN.getPlayerVal()).removeCardFromHand(selectedCard);
                            builds.remove(index);
                            movePressed = true;
                        }
                    }
                }

                loadGameView(view);
                break;
            }
            case R.id.capture:
            {
                //LinearLayout layout = findViewById(R.id.tableLayout);

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
                        players.get(0).addOneCardInPile(selectedCard);
                        clearView(view);
                        players.get(0).removeCardFromHand(selectedCard);
                        rounds.getTable().removeMatchingLooseCardsFromtable(matchingCards);
                        rounds.computerGameEngine(Round.PLAYER.COMPUTER, Round.PLAYER.HUMAN);
                        loadGameView(view);
                    }
                    else
                    {
                        deckPrintOut.append("No valid  matching loose cards. \n");
                    }
                    ArrayList<ArrayList<Card>> matchingCombinations = rounds.getTable().getMatchingCombination(selectedCard);
                    while (matchingCombinations.size() > 0)
                    {
                        int index = 0;
                        clearView(view);
                        players.get(0).addOneCardInPile(selectedCard);
                        players.get(0).removeCardFromHand(selectedCard);
                        players.get(0).addCardInPile(matchingCombinations.get(index), selectedCard);
                        rounds.getTable().removeMatchingLooseCardsFromtable(matchingCombinations.get(index));
                        matchingCombinations.remove(index);
                        index++;
                        movePressed = false;

                    }
                }
                if (rounds.getPlayers().get(0).getHand().size() == 0 && rounds.getPlayers().get(1).getHand().size() == 0)
                {
                    rounds.dealCards(true, true, false);
                }
                while (rounds.getPlayers().get(0).getHand().size() == 0 && rounds.getPlayers().get(1).getHand().size() > 0)
                {
                    rounds.computerGameEngine(Round.PLAYER.COMPUTER, Round.PLAYER.HUMAN);
                }
                rounds.checkForRoundEnd(Round.PLAYER.HUMAN, Round.PLAYER.COMPUTER);
                if (rounds.getPlayers().get(0).getHand().size() == 0 && rounds.getPlayers().get(1).getHand().size() == 0 && rounds.getTable().getLooseCards().size() == 0)
                {
                    clearView(view);
                    checkForWin();
                }
                loadGameView(view);
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

        bottomCard1 =  findViewById(R.id.playerCard1);
        bottomCard2 =  findViewById(R.id.playerCard2);
        bottomCard3 =  findViewById(R.id.playerCard3);
        bottomCard4 = findViewById(R.id.playerCard4);
        playerHand.add(bottomCard1);
        playerHand.add(bottomCard2);
        playerHand.add(bottomCard3);
        playerHand.add(bottomCard4);


        tableCard1 = findViewById(R.id.tableCard1);
        tableCard2 =findViewById(R.id.tableCard2);
        tableCard3 =findViewById(R.id.tableCard3);
        tableCard4 = findViewById(R.id.tableCard4);
        tableHand.add(tableCard1);
        tableHand.add(tableCard2);
        tableHand.add(tableCard3);
        tableHand.add(tableCard4);

        topCard1 =  findViewById(R.id.compCard1);
        topCard2 =  findViewById(R.id.compCard2);
        topCard3 =  findViewById(R.id.compCard3);
        topCard4 =  findViewById(R.id.compCard4);
        computerHand.add(topCard1);
        computerHand.add(topCard2);
        computerHand.add(topCard3);
        computerHand.add(topCard4);
        loadGameView(view);
        //startRound();
        TextView gameData = findViewById(R.id.gameData);
        if (result == choice)
        {
            gameData.append("\nYou won the toss!\n");
            rounds.setTurn(0);
        }
        else
        {
            gameData.append("\nYou lost the toss!\n");
            rounds.setTurn(1);
        }
    }
    public void startRound()
    {
        while (rounds.getDeck().getDeckCards().size() > 0 || rounds.getTable().getBuilds().size() > 0 || rounds.getTable().getLooseCards().size() > 0)
        {
            if (rounds.getPlayers().get(1).getHand().size() == 0 && rounds.getPlayers().get(0).getHand().size() == 0)
            {
                rounds.dealCards(true, true, false);
                //If the table is empty and there are no Builds out (that the user can capture) then deal to the table
                if (rounds.getTable().getLooseCards().size() == 0 && rounds.getTable().getBuilds().size() == 0)
                {
                    rounds.dealCards(false, false, true);
                }
            }
        }
        if (rounds.getTurn() == Round.PLAYER.COMPUTER.getPlayerVal())
        {
            rounds.computerGameEngine(Round.PLAYER.COMPUTER, Round.PLAYER.HUMAN);
            rounds.setTurn(0);
        }
    }
    public void clearView(View view)
    {

        for (int i = 0; i < players.get(1).getHand().size(); i++)
        {
            computerHand.get(i).setImageResource(android.R.color.transparent);
        }
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
        build = findViewById(R.id.build);
        build.setOnClickListener(this);
        capture = findViewById(R.id.capture);
        capture.setOnClickListener(this);
        trail = findViewById(R.id.trail);
        trail.setOnClickListener(this);
        //Setting clickability to buttons

        // etc.
        for (int i = 0; i < players.get(0).getHand().size(); i++)
        {
            playerHand.get(i).setOnClickListener(this);
            assignImages(players.get(0).getHand().get(i), playerHand.get(i));
        }
        for (int i = 0; i < players.get(1).getHand().size(); i++)
        {
            assignImages(players.get(1).getHand().get(i), computerHand.get(i));
        }

        for (int i = 0; i < rounds.getTable().getLooseCards().size(); i++)
        {
            assignImages(rounds.getTable().getLooseCards().get(i), tableHand.get(i));
        }
    }
    public void showDeck(View view)
    {
        TextView deckPrintOut =  findViewById(R.id.gameData);
        String deckString = " ";
        deckString = rounds.getDeck().printDeck();
        deckPrintOut.append("\nDeck: " + deckString + "\n");
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

    public void checkPile(View view)
    {
        TextView deckPrintOut =  findViewById(R.id.gameData);
        deckPrintOut.append(rounds.getPlayers().get(1).printPile() + "Size: " + rounds.getPlayers().get(1).getPile().size() +"\n");
        deckPrintOut.append(rounds.getPlayers().get(0).printPile()+ "Size: " + rounds.getPlayers().get(0).getPile().size() +"\n");
    }
    public void suggestMove(View view)
    {
        TextView deckPrintOut =  findViewById(R.id.gameData);

       rounds.computerGameEngine(Round.PLAYER.HUMAN, Round.PLAYER.COMPUTER);


       deckPrintOut.append(rounds.getMessage() + "\n");

       System.out.println("\n\n\n\n" + rounds.getMessage());

    }
    public void assignImages(Card card, ImageButton image)
    {
        Card assignedCard;

        if (card.toStr().equals("SA"))
        {
           //image.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card1s,0);
            image.setImageResource(R.drawable.card1s);
            assignedCard = new Card("S", 14, "A");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("DA"))
        {
            image.setImageResource(R.drawable.card1d);
            assignedCard = new Card("D", 14, "A");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("CA"))
        {
            image.setImageResource(R.drawable.card1c_90);
            assignedCard = new Card("C", 14, "A");
            image.setTag(assignedCard);
        }
        if (card.toStr().equals("HA"))
        {
            image.setImageResource(R.drawable.card1h);
            assignedCard = new Card("H", 14, "A");
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
    void checkForWin()
    {
        Player playerScore = new Player();
        TextView deckPrintOut =  findViewById(R.id.gameData);
        TextView computerScoreText = findViewById(R.id.computerScore);
        TextView humanScoreText = findViewById(R.id.humanScore);
        TextView roundNum = findViewById(R.id.roundNumber);

        int humanScore = playerScore.calculateScore(rounds.getPlayers().get(0), rounds.getPlayers().get(1));
        int computerScore = playerScore.calculateScore(rounds.getPlayers().get(1), rounds.getPlayers().get(0));

        //Printing score after round to "console" & to game screen (for easier viewing)
        computerScoreText.setText("Computer player score: " + computerScore);
        humanScoreText.setText("Human player score: " + humanScore);
        roundNum.setText("Round number: " + rounds.getRound());

        deckPrintOut.append("\nHuman score after round " + rounds.getRound() + "----------------> " + humanScore + "\n");
        deckPrintOut.append("\nComputer score after round " + rounds.getRound() + "----------------> " + computerScore + "\n");
        if ((humanScore >= 21) && (computerScore >= 21))
        {
            deckPrintOut.append("The tournament ended in a tie!!");
        }
        else if (humanScore >= 21)
        {
            deckPrintOut.append("You won the tournament!");
        }
        else if (computerScore >= 21)
        {
            deckPrintOut.append("The computer won the tournament... =[\n");
        }
        else
        {
            //Get the round # and the latest capture
            int round = rounds.getRound();
            int turn = rounds.latestCapture;
            //Create a new round and check if we are loading from a file
            rounds = new Round(false);

            //Set the score for each player
            rounds.setScoreForPlayer(Round.PLAYER.HUMAN, humanScore);
            rounds.setScoreForPlayer(Round.PLAYER.COMPUTER, computerScore);

            //Update the round to display the next round
            rounds.setRound(round + 1);
            //Set the first turn to whoevers turn it is now
            rounds.setTurn(turn);

            rounds.dealCards(true, true, true);
            players = rounds.getPlayers();
            //startRound(true);
        }
    }

   /* public void saveFile()
    {
        FileOutputStream fos = new FileOutputStream("mybean.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(rounds);
        oos.close();
    }*/
}


//To do
//Make computer builds show up on screen
//Add player builds
//Fix pink border on cards
//Fix Suggesting a move
