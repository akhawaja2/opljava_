package edu.ramapo.khawaja.casino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity
{
    ImageView deck, bottomCard1, bottomCard2, bottomCard3, bottomCard4, topCard1, topCard2, topCard3, topCard4;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Deck test = new Deck();

        test.autoPopulateDeck();

        test.printDeck();

        ArrayList<Card> hand;
        hand = test.drawFourCards();

        for ( int i = 0; i < hand.size(); i++)
        {
            hand.get(i).print();
        }
    }
    protected void goToGame()
    {
        setContentView(R.layout.game_view);
        deck = findViewById(R.id.deck);
        bottomCard1 =  findViewById(R.id.bottomCard1);
        bottomCard2 =  findViewById(R.id.bottomCard2);
        bottomCard3 =  findViewById(R.id.bottomCard3);
        bottomCard4 = findViewById(R.id.bottomCard4);

        topCard1 =  findViewById(R.id.topCard1);
        topCard2 =  findViewById(R.id.topCard2);
        topCard3 =  findViewById(R.id.topCard3);
        topCard4 =  findViewById(R.id.topCard4);

        bottomCard1.setVisibility(View.INVISIBLE);
        bottomCard2.setVisibility(View.INVISIBLE);
        bottomCard3.setVisibility(View.INVISIBLE);
        bottomCard4.setVisibility(View.INVISIBLE);
        topCard1.setVisibility(View.INVISIBLE);
        topCard2.setVisibility(View.INVISIBLE);
        topCard3.setVisibility(View.INVISIBLE);
        topCard4.setVisibility(View.INVISIBLE);

        final ArrayList <Integer> cards = new ArrayList();

        for (int i = 1; i < 53; i++)
        {
            cards.add(i);
        }

        deck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //shuffle
                Collections.shuffle(cards);

                ///deal
                assignImages(cards.get(0), bottomCard1);
                assignImages(cards.get(1), bottomCard2);
                assignImages(cards.get(2), bottomCard3);
                assignImages(cards.get(3), bottomCard4);
                assignImages(cards.get(4), topCard1);
                assignImages(cards.get(5), topCard2);
                assignImages(cards.get(6), topCard3);
                assignImages(cards.get(7), topCard4);

                bottomCard1.setVisibility(View.VISIBLE);
                bottomCard2.setVisibility(View.VISIBLE);
                bottomCard3.setVisibility(View.VISIBLE);
                bottomCard4.setVisibility(View.VISIBLE);
                topCard1.setVisibility(View.VISIBLE);
                topCard2.setVisibility(View.VISIBLE);
                topCard3.setVisibility(View.VISIBLE);
                topCard4.setVisibility(View.VISIBLE);
            }
        });

    }
    public void assignImages(int card, ImageView image)
    {
        switch (card)
        {
            case 1:
                image.setImageResource(R.drawable.card1c_90);
                break;
            case 2:
                image.setImageResource(R.drawable.card1d);
                break;
            case 3:
                image.setImageResource(R.drawable.card1h);
                break;
            case 4:
                image.setImageResource(R.drawable.card1s);
                break;
            case 5:
                image.setImageResource(R.drawable.card2c);
                break;
            case 6:
                image.setImageResource(R.drawable.card2d);
                break;
            case 7:
                image.setImageResource(R.drawable.card2h);
                break;
            case 8:
                image.setImageResource(R.drawable.card2s);
                break;
            case 9:
                image.setImageResource(R.drawable.card3c);
                break;
            case 10:
                image.setImageResource(R.drawable.card3d);
                break;
            case 11:
                image.setImageResource(R.drawable.card3h);
                break;
            case 12:
                image.setImageResource(R.drawable.card3s);
                break;
            case 13:
                image.setImageResource(R.drawable.card4c);
                break;
            case 15:
                image.setImageResource(R.drawable.card4h);
                break;
            case 14:
                image.setImageResource(R.drawable.card4d);
                break;
            case 16:
                image.setImageResource(R.drawable.card4s);
                break;
            case 17:
                image.setImageResource(R.drawable.card5d);
                break;
            case 18:
                image.setImageResource(R.drawable.card5c);
                break;
            case 19:
                image.setImageResource(R.drawable.card5h);
                break;
            case 20:
                image.setImageResource(R.drawable.card5s);
                break;
            case 21:
                image.setImageResource(R.drawable.card6c);
                break;
            case 22:
                image.setImageResource(R.drawable.card6d);
                break;
            case 23:
                image.setImageResource(R.drawable.card6s);
                break;
            case 24:
                image.setImageResource(R.drawable.card6h);
                break;
            case 25:
                image.setImageResource(R.drawable.card7c);
                break;
            case 26:
                image.setImageResource(R.drawable.card7d);
                break;
            case 27:
                image.setImageResource(R.drawable.card7h);
                break;
            case 28:
                image.setImageResource(R.drawable.card7s);
                break;
            case 29:
                image.setImageResource(R.drawable.card8c);
                break;
            case 30:
                image.setImageResource(R.drawable.card8h);
                break;
            case 31:
                image.setImageResource(R.drawable.card8d);
                break;
            case 32:
                image.setImageResource(R.drawable.card8s);
                break;
            case 33:
                image.setImageResource(R.drawable.card9s);
                break;
            case 34:
                image.setImageResource(R.drawable.card9c);
                break;
            case 35:
                image.setImageResource(R.drawable.card9h);
                break;
            case 36:
                image.setImageResource(R.drawable.card9d);
                break;
            case 37:
                image.setImageResource(R.drawable.card9s);
                break;
            case 38:
                image.setImageResource(R.drawable.cardtc);
                break;
            case 39:
                image.setImageResource(R.drawable.cardtd);
                break;
            case 40:
                image.setImageResource(R.drawable.cardth);
                break;
            case 41:
                image.setImageResource(R.drawable.cardts);
                break;
            case 42:
                image.setImageResource(R.drawable.cardjc);
                break;
            case 43:
                image.setImageResource(R.drawable.cardjd);
                break;
            case 44:
                image.setImageResource(R.drawable.cardjh);
                break;
            case 45:
                image.setImageResource(R.drawable.cardjs);
                break;
            case 46:
                image.setImageResource(R.drawable.cardqc);
                break;
            case 47:
                image.setImageResource(R.drawable.cardqd);
                break;
            case 48:
                image.setImageResource(R.drawable.cardqh);
                break;
            case 49:
                image.setImageResource(R.drawable.cardqs);
                break;
            case 50:
                image.setImageResource(R.drawable.cardkc);
                break;
            case 51:
                image.setImageResource(R.drawable.cardkh);
                break;
            case 52:
                image.setImageResource(R.drawable.cardkd);
                break;
            case 53:
                image.setImageResource(R.drawable.cardks);

        }
    }
    protected void goToFlip(View view)
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
        TextView textView = findViewById(R.id.tossResult);
        if (result == choice)
        {
            textView.setText("You won the toss!");
            goToGame();
        }
        else
        {
            textView.setText("You lost the toss =[");
            goToGame();
        }

    }
}
