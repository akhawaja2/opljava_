package edu.ramapo.khawaja.casino;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Build {

    public Build ()
    {

    }
    public Build(String owner, Card BuildHolder, boolean isMultiBuild)
    {

        this.owner = owner;
        this.isMultiBuild = isMultiBuild;
        this.BuildHolder = BuildHolder;
    }

    public void addCardInBuildCards(Card Card)
    {
        buildCards.add(Card);
    }

    public void setBuildCards(ArrayList<Card> BuildCards)
    {
        this.buildCards = BuildCards;
    }

    public ArrayList<Card> getBuildCards()
    {
        return this.buildCards;
    }

    public void setMultiBuildCards(ArrayList<ArrayList<Card>> multiBuildCards)
    {
        this.multiBuildCards = multiBuildCards;
    }

    public ArrayList<ArrayList<Card>> getMultiBuildCards()
    {
        return this.multiBuildCards;
    }
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return this.owner;
    }

    public void setBuildHolder(Card BuildHolder)
    {
        this.BuildHolder = BuildHolder;
    }
    public Card getBuildHolder()
    {
        return this.BuildHolder;
    }

    public void setIsMultiBuild(boolean isParent)
    {
        this.isMultiBuild = isParent;
    }
    public boolean getIsMultiBuild()
    {
        return this.isMultiBuild;
    }

    void print()
    {
        Card buildCardsToPrint = new Card();
        if (this.isMultiBuild == false)
        {
            buildCardsToPrint.printCards(this.getBuildCards());
        }
	    else
        {
            buildCardsToPrint.printCombinationCards(this.getMultiBuildCards());
        }
    }
    void printBuilds(ArrayList<Build> Builds)
    {
        Card buildCardsToPrint = new Card();
        for (int i = 0; i < Builds.size(); i++)
        {
            System.out.print(i + 1 + " :");
            if (Builds.get(i).getIsMultiBuild())
            {
                buildCardsToPrint.printCombinationCards(Builds.get(i).getMultiBuildCards());
            }
            else
            {
                buildCardsToPrint.printCards(Builds.get(i).getBuildCards());
            }
            System.out.print("Because you have ");
            Builds.get(i).getBuildHolder().print();
            System.out.println(" in hand");
        }

    }
    void printBuildsWhole(ArrayList<Build> Builds)
    {
        Card cardToPrint = new Card();
        for (int i = 0; i < Builds.size(); i++)
        {
            //cout << i + 1 << " :";
            if (Builds.get(i).getIsMultiBuild())
            {
                System.out.print("\nmulti build containing: (");
                cardToPrint.printCombinationCardsWhole(Builds.get(i).getMultiBuildCards());
            }
            else
            {
                System.out.print("\nsingle build containing: (");
                cardToPrint.printCardsWhole(Builds.get(i).getBuildCards());
            }
            System.out.print("because of the ");
            Builds.get(i).getBuildHolder().printWhole();
            System.out.println(" in hand)");
        }

    }
    private ArrayList<Card> buildCards;
    private ArrayList<ArrayList<Card>> multiBuildCards;
    private ArrayList<Build> builtWithInBuild;
    private boolean isMultiBuild;
    private Card BuildHolder;
    private String owner;
}
