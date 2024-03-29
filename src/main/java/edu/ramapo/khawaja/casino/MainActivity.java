package edu.ramapo.khawaja.casino;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
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
                        rounds.getPlayers().get(0).removeCardFromHand(selectedCard);
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
                System.out.println("ENTERED CAPTURE");
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
                    ArrayList<Integer> ids = rounds.getTable().getMatchingBuilds(selectedCard);
                    while (ids.size() > 0)
                    {
                        ArrayList<Build>  buildCopy = rounds.getTable().getBuilds();
                        ArrayList<Build> buildToPrint = new ArrayList<>();
                        for (int i = 0; i < ids.size(); i++)
                        {
                            buildToPrint.add(buildCopy.get(ids.get(i)));

                        }
                        Build selectedBuild = rounds.getTable().getBuilds().get(ids.size()-1);
                        if (selectedBuild.getIsMultiBuild())
                        {
                            for (int i = 0; i < selectedBuild.getMultiBuildCards().size(); i++)
                            {
                                players.get(Round.PLAYER.HUMAN.getPlayerVal()).addCardInPile(selectedBuild.getMultiBuildCards().get(i), selectedCard);
                            }
                        }
                        else
                        {
                            players.get(Round.PLAYER.HUMAN.getPlayerVal()).addCardInPile(selectedBuild.getBuildCards(), selectedCard);

                        }
                        players.get(0).addOneCardInPile(selectedCard);
                        players.get(0).removeCardFromHand(selectedCard);
                        rounds.getTable().removeBuild(ids.get(ids.size()-1));
                        clearView(view);
                        ids.remove(ids.get(ids.size()-1));

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
                clearView(view);
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
        rounds.dealCards(true, true, true);
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

    public void clearView(View view)
    {

        for (int i = 0; i < rounds.getPlayers().get(1).getHand().size(); i++)
        {
            computerHand.get(i).setImageResource(android.R.color.transparent);
        }
        for (int i = 0; i < 4; i++)
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
        clearView(view);
        TextView hScore =  findViewById(R.id.humanScore);
        TextView compScore =  findViewById(R.id.computerScore);

        hScore.setText("Human Score:  "+ rounds.getPlayers().get(0).getScore());
        compScore.setText("Computer Score:  "+ rounds.getPlayers().get(1).getScore());
        build = findViewById(R.id.build);
        build.setOnClickListener(this);
        capture = findViewById(R.id.capture);
        capture.setOnClickListener(this);
        trail = findViewById(R.id.trail);
        trail.setOnClickListener(this);
        //Setting clickability to buttons

        for (int i = 0; i < rounds.getPlayers().get(0).getHand().size(); i++)
        {
            playerHand.get(i).setOnClickListener(this);
            assignImages(rounds.getPlayers().get(0).getHand().get(i), playerHand.get(i));
        }

        for (int i = 0; i < rounds.getPlayers().get(1).getHand().size(); i++)
        {
            assignImages(rounds.getPlayers().get(1).getHand().get(i), computerHand.get(i));
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

    public void saveGame(View view)
    {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "save.txt");

        try
        {
            FileOutputStream saveFile = new FileOutputStream(file);

            String output = "";

            //First line of output - Round: X
            output += "Round: " + rounds.getRound() + "\n";


            //Computer player info
            output += rounds.getPlayers().get(1).getName() + ":" + "\n";
            output += "Score:" + rounds.getPlayers().get(1).getScore() + "\n";
            output += "Hand:";
            for (int i = 0; i < rounds.getPlayers().get(1).getHand().size(); i++)
            {
                output += rounds.getPlayers().get(1).getHand().get(i).toStr() + " ";
            }
            output += "\nPile: ";
            for (int i = 0; i < rounds.getPlayers().get(1).getPile().size(); i++)
            {
                output += rounds.getPlayers().get(1).getPile().get(i).toStr() + " ";
            }
            output += "\n\n";

            //Human player info
            output += rounds.getPlayers().get(0).getName() + ":" + "\n";
            output += "Score:" + rounds.getPlayers().get(0).getScore() + "\n";
            output += "Hand:";
            for (int i = 0; i < rounds.getPlayers().get(0).getHand().size(); i++)
            {
                output += rounds.getPlayers().get(0).getHand().get(i).toStr() + " ";
            }
            output += "\nPile: ";
            for (int i = 0; i < rounds.getPlayers().get(0).getPile().size(); i++)
            {
                output += rounds.getPlayers().get(0).getPile().get(i).toStr() + " ";
            }
            output += "\n\n";

            //Printing table & deck
            output += "Table:" + rounds.getTable().printTable() + "\n";
            output += "Deck:" + rounds.getDeck().printDeck() + "\n";
            //Printing next player
            int turn = rounds.getTurn();
            if (turn == 0)
            {
                output += "Next Player: Human";
            }
            else if (turn == 1)
            {
                output += "Next Player:Computer";
            }

            byte[] saveFileBytes = output.getBytes();
            saveFile.write(saveFileBytes);
            System.out.println(file.getAbsolutePath());
            saveFile.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
    public void loadFile(View view)
    {
        ArrayList<String> array = new ArrayList<String>();
        String line = "";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "case1.txt");
        try
        {
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
                array.add(receiveString);
            }

            inputStream.close();
            //line = stringBuilder.toString();
            storeFile(array, view);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
    public void storeFile(ArrayList<String> stringArray, View view)
    {
        Card cardToAdd = new Card();

        for (int i = 0; i < stringArray.size(); i++)
        {
            System.out.println(stringArray.get(i));
        }

        //Getting Round #
        String[] line = stringArray.get(0).split(":");
        int roundNum = Integer.valueOf(line[1].trim());
        System.out.println(roundNum);

        //Getting Computer score
        line = stringArray.get(2).split(":");
        int compScore = Integer.valueOf(line[1].trim());
        System.out.println(compScore);


        //getting computer hand
        line = stringArray.get(3).split(":");
        line = line[1].split(" ");
        ArrayList<Card> cHand = new ArrayList<>();

        for (int i = 0; i < line.length; i++)
        {
            cHand.add(cardToAdd.convertStringToCard(line[i]));
            System.out.print(cHand.get(i).toStr() + " ");
        }
        //getting computer pile
        line = stringArray.get(4).split(":");
        line = line[1].split(" ");
        ArrayList<Card> cPile = new ArrayList<>();

        System.out.println();
        for (int i = 0; i < line.length; i++)
        {
            cPile.add(cardToAdd.convertStringToCard(line[i]));
            System.out.print(cPile.get(i).toStr() + " ");
        }
        System.out.println("\n------------Human stuff under-------------");
        //Getting Human stuff
        //Getting human score
        line = stringArray.get(7).split(":");
        //System.out.println("line at start of human: " + line[1]);
        int hScore = Integer.valueOf(line[1].trim());
        System.out.println(hScore);

        //getting human hand
        line = stringArray.get(8).split(":");
        line = line[1].split(" ");
        ArrayList<Card> hHand = new ArrayList<>();

        for (int i = 0; i < line.length; i++)
        {
            //System.out.println(line[i]);
            hHand.add(cardToAdd.convertStringToCard(line[i]));
            System.out.print(hHand.get(i).toStr() + " ");
        }
        System.out.println("\n");
        //getting human pile
        line = stringArray.get(9).split(":");
        line = line[1].split(" ");
        ArrayList<Card> hPile = new ArrayList<>();
        for (int i = 0; i < line.length; i++)
        {
            hPile.add(cardToAdd.convertStringToCard(line[i]));
        }

        System.out.println("Human pile size: " + hPile.size());

        //------------------------Loading in table
        Table obj = new Table();

        line = stringArray.get(11).split(":");
        line = line[1].split(" ");

        ArrayList<Card> buildCards = new ArrayList<>();
        ArrayList<Card> looseCards = new ArrayList<>();
        ArrayList<Build> builds = new ArrayList<>();

        for (int i = 0; i < line.length; i++)
        {
            boolean insideMulti = false;
            ArrayList<ArrayList<Card>> multiBuildCards = new ArrayList<>();

            char character = line[i].charAt(0);
            if (character == '[')
            {
                i++;
                //looping thru till i find close bracked
                for (int j = i; character != ']'; j++)
                {
                    insideMulti = true;

                    if (line[i].length() == 3)
                    {
                        ArrayList<Card> Cards = new ArrayList<>();
                        String [] st = line[i].split("\\[");
                        Cards.add(cardToAdd.convertStringToCard(st[0]));
                        j++; i++;
                        for (i = i; line[i].length() != 3; i++)
                        {
                            Cards.add(cardToAdd.convertStringToCard(line[i]));
                        }
                        st = line[i].split("\\]");
                        Cards.add(cardToAdd.convertStringToCard(st[0]));
                        multiBuildCards.add(Cards);
                    }
                    if (line[i].length() == 4)
                    {
                        ArrayList<Card> Cards = new ArrayList<>();
                        String CardString = "";
                        CardString += (line[i].charAt(1));
                        CardString += (line[i].charAt(2));
                        Cards.add(cardToAdd.convertStringToCard(CardString));
                        multiBuildCards.add(Cards);
                    }
                }
            }
            if (insideMulti)
            {
                Build build = new Build();
                build.setMultiBuildCards(multiBuildCards);
                build.setIsMultiBuild(true);
                obj.addBuild(build);
            }
            if ( line[i].length() == 3)
            {
                String[] st = line[i].split("\\[");
                //Pushing back the first Card
                buildCards.add(cardToAdd.convertStringToCard(st[0]));
                i++;
                for (i = i; line[i].length()!= 3; i++)
                {
                    buildCards.add(cardToAdd.convertStringToCard(line[i]));
                }
                //Pushing back the last Card
                st = line[i].split("\\]");
                buildCards.add(cardToAdd.convertStringToCard(st[0]));

                //Initializing a Build, setting Build Cards to the current Build Cards vector
                //And then adding the Build to the object
                Build build = new Build();
                build.setBuildCards(buildCards);
                build.setIsMultiBuild(false);
                obj.addBuild(build);
            }
            if (line[i].length() == 4)
            {
                String cardString = "";
                cardString += (line[i].charAt(1));
                cardString += (line[i].charAt(2));
                buildCards.add(cardToAdd.convertStringToCard(cardString));

                Build build = new Build();
                build.setBuildCards(buildCards);
                build.setIsMultiBuild(false);
                obj.addBuild(build);
            }
            if (line[i].length() == 2)
            {
                looseCards.add(cardToAdd.convertStringToCard(line[i]));
            }

        }
        obj.addCardInLooseCards(looseCards);
        for (int i = 0; i < obj.getBuilds().size(); i++)
        {
            line = line[i].split(" ");
            obj.getBuilds().get(i).setOwner(line[line.length - 2]);
        }
        System.out.println("--Table--" + obj.printTable());
        //------------------end table
        //------------------------Loading in deck
        line = stringArray.get(12).split(":");
        line = line[1].split(" ");
        ArrayList<Card> fileDeck = new ArrayList<>();

        if (line.length > 1)
        {
            for (int i = 0; i < line.length; i++)
            {
                cardToAdd = cardToAdd.convertStringToCard(line[i]);
                fileDeck.add(cardToAdd);
                System.out.print(fileDeck.get(i).print() + " ");
            }

        }
        System.out.println("........");
        System.out.println("\nFile Deck size: " + fileDeck.size());


        rounds = null;
        rounds = new Round();



        rounds.setRound(roundNum);
        rounds.getPlayers().get(1).setScore(compScore);
        rounds.getPlayers().get(1).setHand(cHand);
        rounds.getPlayers().get(1).setPile(cPile);
        rounds.getPlayers().get(0).setScore(hScore);
        rounds.getPlayers().get(0).setHand(hHand);
        rounds.getPlayers().get(0).setPile(hPile);
        //Setting tables below
        rounds.getTable().setBuilds(obj.getBuilds());
        rounds.getTable().setLooseCards(obj.getLooseCards());
        rounds.getDeck().setDeckCards(fileDeck);

        setContentView(R.layout.table_view);
        playerHand.clear();
        computerHand.clear();
        tableHand.clear();
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
        //Intent intent = new Intent(this)

        clearView(view);
        loadGameView(view);
    }
    /*Round: 1 stringArray.get(0)
    Computer: stringArray.get(1)
    Score:0stringArray.get(2)
    Hand:DJ C2 H6 SK  stringArray.get(3)
    Pile: stringArray.get(4)
    Human: stringArray.get(5)
    Score:0 stringArray.get(6)
    Hand:CK H8 C9 D7 stringArray.get(7)
    Pile: stringArray.get(8)
    Table:D6 D2 HJ CA stringArray.get(9)
    Deck:HQ S4 D8 DQ S3 C3 HA H3 S2 CQ S8 SJ S7 CJ D9 D4 C7 S5 SQ S6 DK CX H4 HX SA H9 H2 SX C6 D5 DA C4 DX HK D3 C8 H7 S9 H5 C5 stringArray.get(10)
    Next Player: Human stringArray.get(11)
    */
}


//To do
//Make computer builds show up on screen
//Add player builds
//Fix pink border on cards
//Fix Suggesting a move
