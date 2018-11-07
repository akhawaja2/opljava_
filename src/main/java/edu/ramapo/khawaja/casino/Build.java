package edu.ramapo.khawaja.casino;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Build {

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


    private ArrayList<Card> buildCards;
    private ArrayList<ArrayList<Card>> multiBuildCards;
    private ArrayList<Build> builtWithInBuild;
    private boolean isMultiBuild;
    private Card BuildHolder;
    private String owner;
}
