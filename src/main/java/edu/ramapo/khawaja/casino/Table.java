package edu.ramapo.khawaja.casino;

import java.util.ArrayList;

public class Table
{
    public Table ()
    {

    }
    void clearBuilds()
    {
        Builds.clear();
    }


    void clearLooseCards()
    {
        looseCards.clear();
    }

    ArrayList<Card> getLooseCards()
    {
        return looseCards;
    }

    ArrayList<Build> getBuilds()
    {
        return Builds;
    }

    void removeBuild(int index)
    {
        Builds.remove(Builds.get(index));
    }

    void addBuild(Build Build)
    {
        Builds.add(Build);
    }

    void addCardInBuilds(Card Card, int index)
    {
        Builds.get(index).addCardInBuildCards(Card);
    }

    void filterCapture(ArrayList<Card> matchingCards, ArrayList < ArrayList<Card> > matchingCombination,
                              ArrayList<Integer> BuildIds)
    {
        int max = 0;
        int sum = 0;
        int oldMaxIndex = -1;
        //looping through matchingCards vector and incrementing sum
        //And if the max is less then the sum I update the max.
        for (int i = 0; i < matchingCards.size(); i++)
        {
            sum = sum + matchingCards.get(i).getNumber();
        }
        if (max < sum)
        {
            max = sum;
        }
        //For each vector of Cards in MatchingCombinations I loop through each one
        //And take the sum and then check if it is greater then the new max
        //Then I check oldMaxIndex and if it is not -1 that means it was updated and I erase the matching combination
        //At the old max Index and then cast our int i into an integer value.
        for (int i = 0; i < matchingCombination.size(); i++)
        {
            sum = 0;
            for (int j = 0; j< matchingCombination.get(i).size(); j++)
            {
                sum = sum + matchingCombination.get(i).get(j).getNumber();
            }
            if (max < sum)
            {
                max = sum;
                if (oldMaxIndex != -1)
                {
                    matchingCombination.remove(matchingCombination.get(oldMaxIndex));
                    i = 0;
                }
                oldMaxIndex = i;
            }
            else
            {
                matchingCombination.remove(matchingCombination.get(i));
                i = 0;
            }
        }
        //I loop through our Build Ids now
        for (int index = 0; index < BuildIds.size(); index++)
        {
            //I store the current Build in the current index
            Build selectedBuild = Builds.get(index);
            //If the Build is a multi Build I get the multiBuild Cards and add up the sum
            if (selectedBuild.getIsMultiBuild())
            {
                for (int i = 0; i < Builds.get(index).getMultiBuildCards().size(); i++)
                {
                    sum = 0;
                    for (int j = 0; j< Builds.get(index).getMultiBuildCards().get(i).size(); j++)
                    {
                        sum = sum + Builds.get(index).getMultiBuildCards().get(i).get(j).getNumber();
                    }
                }
            }
            //Otherwise the Build is not a multiBuild but I still loopthrough the Cards
            //And update our sum
            else
            {
                for (int i = 0; i < Builds.get(index).getBuildCards().size(); i++)
                {
                    sum = sum + Builds.get(index).getBuildCards().get(i).getNumber();
                }
            }
            //I update the max now and erase the BuildId at the index in which I are taking the Build from
            if (max < sum)
            {
                max = sum;
                if (oldMaxIndex != -1)
                {
                    BuildIds.remove(BuildIds.get(oldMaxIndex));
                    index = 0;
                }
                oldMaxIndex = index;
            }
            else
            {
                BuildIds.remove(BuildIds.get(index));
                index = 0;
            }
        }

    }


    Player getCaptureCardsForPlayedCard(Card playedCard, ArrayList<Player> players, int currentPlayer,
                                               int opponent, boolean addCards, boolean applyAction)
    {
        //Creating a vector of Cards on table matching the current played Card
        ArrayList <Card> matchingCards = getMatchingLooseCards(playedCard);
        //Creating a vector of vector of Cards which is getting all potential combinations for a capture
        //played Card
        ArrayList < ArrayList<Card> > matchingCombination = getMatchingCombination(playedCard);
        //Getting all of the Build IDs for the played Card
        ArrayList<Integer> BuildIds = getMatchingBuilds(playedCard);

        //Filtering the captures based on the biggest score
        filterCapture(matchingCards, matchingCombination, BuildIds);

        //Creating our computer player
        Player cpuPlayer = new Player("CPU");

        //Adding all Cards of the same # value to the computer players pile.
        cpuPlayer.addCardInPile(matchingCards, playedCard);

        //If apply action is true and the current player is the computer
        if (applyAction && currentPlayer == 1)
        {
            //I're gonna remove the matching loose Cards from the table
            removeMatchingLooseCardsFromtable(matchingCards);
        }
        //For each matching combination in the vector of vectors
        for (int index = 0; index < matchingCombination.size(); index++)
        {
            //add the Card to the pile
            cpuPlayer.addCardInPile(matchingCombination.get(index), playedCard);
            //If the computer is making the move I remove it from the table
            if (applyAction && currentPlayer == 1)
            {
                removeMatchingLooseCardsFromtable(matchingCombination.get(index));
            }

        }
        //for each Build in Build ids IF the Build is a multiple Build I loop through the multiple Build and
        //Add the Cards into the pile
        for (int index = 0; index < BuildIds.size(); index++)
        {
            Build selectedBuild = Builds.get(index);
            if (selectedBuild.getIsMultiBuild())
            {
                for (int i = 0; i < selectedBuild.getMultiBuildCards().size(); i++)
                {
                    cpuPlayer.addCardInPile(selectedBuild.getMultiBuildCards().get(i), playedCard);
                }
            }
            //Otherwise I just add the Build Cards to the pile
            else
            {
                cpuPlayer.addCardInPile(selectedBuild.getBuildCards(), playedCard);
            }
            //If apply action is true and the current player is the computer I update the Build/BuildID info
            //and reset the index
            if (applyAction && currentPlayer == 1)
            {
                Builds.remove(BuildIds.get(0+index));
                BuildIds.remove(BuildIds.get(0+index));
                index = 0;
            }

        }
        //Checking If apply action is true
        if (applyAction)
        {
            //If the current player is a computer
            if (currentPlayer == 1)
                players.get(currentPlayer).addCardInPile(cpuPlayer.getPile(), playedCard);
            //If the current player is a computer I show what they played
            if (currentPlayer == 1)
                System.out.println("\n\nThe computer choose to play");
                //If they are not I suggest to the user what they should play and print the Card to play
            else
                System.out.print("\n\n\n\nI suggest you play the following card (because its score is the highest I evaluated):\n ");
            //playedCard.printWhole();
            System.out.print(" and use this card to capture ");
            //If there are any matching Cards on the table I print those
            if (matchingCards.size() > 0)
            {
                //printCardsWhole(matchingCards);
            }
            //If there are any Builds on the table to capture I print them
            if (matchingCombination.size() > 0)
            {
                //printCombinationCardsWhole(matchingCombination);
            }
            //If there are any Builds available I loop through the BuildsIds and print what
            //Builds they can capture
            if (BuildIds.size() > 0)
            {
                System.out.print(" the Build(s): ");
                for (int index = 0; index < BuildIds.size(); index++)
                {
                    Build selectedBuild = Builds.get(index);
                    if (selectedBuild.getIsMultiBuild())
                    {
                        for (int i = 0; i < selectedBuild.getMultiBuildCards().size(); i++)
                        {
                            //printCardsWhole(selectedBuild.getMultiBuildCards().get(i));
                            //cpu.addCardInPile(selectedBuild.getMultiBuildCards().get(i), playedCard);
                        }
                    }
                    else {
                       // ArrayList<Card> buildsToPrint = selectedBuild.getBuildCards();
                        //buildsToPrint.printCardsWhole(buildsToPrint);
                        //cpu.addCardInPile(selectedBuild.getBuildCards(), playedCard);
                    }
                }
                //printCombinationCardsWhole(matchingCombination);
            }
        }
        //And I return the computer players information
        return cpuPlayer;
    }

    Player getBuildForPlayedCard(Card playedCard, ArrayList<Player> players, int currentPlayer,
                                        int opponent, boolean addCards, boolean applyAction)
    {
        //Creating a vector of temporary Builds and checking all potential Builds
        ArrayList<Build> BuildsTemp = checkBuildOptions(playedCard, players.get(currentPlayer).getHand());
        int max = 0;
        int sum = 0;
        int oldMaxIndex = -1;
        //For each Build in the vector I set the selected Build  to the Build at the index
        for (int index = 0; index < BuildsTemp.size(); index++)
        {
            Build selectedBuild = BuildsTemp.get(index);
            if (selectedBuild.getIsMultiBuild())
            {
                for (int i = 0; i < BuildsTemp.get(index).getMultiBuildCards().size(); i++)
                {
                    sum = 0;
                    for (int j = 0; j< BuildsTemp.get(index).getMultiBuildCards().get(i).size(); j++)
                    {
                        sum = sum + BuildsTemp.get(index).getMultiBuildCards().get(i).get(j).getNumber();
                    }
                }
            }
            else
            {
                for (int i = 0; i < BuildsTemp.get(index).getBuildCards().size(); i++)
                {
                    sum = sum + BuildsTemp.get(index).getBuildCards().get(i).getNumber();
                }
            }
            if (max < sum)
            {
                max = sum;
                if (oldMaxIndex != -1)
                {
                    BuildsTemp.remove(BuildsTemp.get(0 + oldMaxIndex));
                    index = 0;
                }
                oldMaxIndex = index;
            }
            else
            {
                BuildsTemp.remove(BuildsTemp.get(0 + index ));
                oldMaxIndex = index = 0;
            }
        }
        //Initializng our CPU
        Player cpuPlayer = new Player("CPU");

        //For each Build I check if it is a multiple Build. Then
        //The Cards with the max potential score are added to the dummy players pile

        for (int index = 0; index < BuildsTemp.size(); index++)
        {
            Build selectedBuild = BuildsTemp.get(index);

            if (selectedBuild.getIsMultiBuild())
            {
                for (int i = 0; i < selectedBuild.getMultiBuildCards().size(); i++)
                {
                    cpuPlayer.addCardInPile(selectedBuild.getMultiBuildCards().get(i), playedCard);
                }
            }
            else
            {
                cpuPlayer.addCardInPile(selectedBuild.getBuildCards(), playedCard);
            }
            if (applyAction && currentPlayer == 1)
            {
                BuildsTemp.get(index).setOwner(players.get(currentPlayer).getName());
                removeMatchingLooseCardsFromtable(BuildsTemp.get(index).getBuildCards());
                Builds.add(BuildsTemp.get(index));
                players.get(currentPlayer).removeCardFromHand(playedCard);
                /*Builds.erase(Builds.begin() + index);
                index = 0;*/
            }

        }
        if (applyAction)
        {
            if (currentPlayer == 1)
                System.out.print("\n\nThe Computer choose to play ");

            else
                System.out.print("\n\nI suggest you play the following card (because its score is the highest):\n");
            playedCard.printWhole();
            System.out.print(" and use this card to make: ");
            //Print the whole Build
            if (BuildsTemp.size() > 0)
            {
                //Build::printBuildsWhole(BuildsTemp);
            }
        }
        //Return the player
        return cpuPlayer;
    }


    int getBuildIncForPlayedCard(Card playedCard, ArrayList<Player> players, int currentPlayer, int opponent, boolean addCards, boolean applyAction)
    {
        //IDS for Builds I can increase
        ArrayList<Integer> ids = checkBuildToIncrease(playedCard, "HUMAN", players.get(currentPlayer).getHand());
        int maxScore = 0;
        int maxScoreBuildIndex = 0;
        boolean BuildFound = false;

        //	if (addCards)
        //		cpuPlayer.addCardInPile(players.get(currentPlayer).getPile(), playedCard);
        for (int index = 0; index < ids.size(); index++)
        {
            Player cpuPlayer = new Player("CPU");
            Build selectedBuild = Builds.get(index);
            cpuPlayer.addCardInPile(selectedBuild.getBuildCards(), playedCard);
            int score = cpuPlayer.calculateScore(cpuPlayer, players.get(opponent));
            if (score > maxScore)
            {
                maxScore = score;
                maxScoreBuildIndex = index;
                BuildFound = true;
            }
        }
        if (applyAction)
        {
            if (BuildFound  && currentPlayer == 1)
            {
                Builds.get(maxScoreBuildIndex).getBuildCards().add(playedCard);
                Builds.get(maxScoreBuildIndex).setOwner("Computer");
            }
            if (currentPlayer == 1)
                System.out.print("\n\nThe computer choose to play ") ;
            else
                System.out.print("\n\n\n\nI suggest you play the following Card (because its score is the highest I evaluated):\n");
            playedCard.printWhole();
            System.out.print(" and increase the build");

            if (Builds.size() > 0)
            {
                //Builds.get(maxScoreBuildIndex).printWhole();
            }
        }
        return maxScore;
    }

    ArrayList<Card> getMatchingLooseCards(Card playedCard)
    {
        //initializing a vector of Cards
        ArrayList<Card> Cards = new ArrayList<Card>();
        for (int i = 0; i < Builds.size(); i++)
        {
            //if the current Build, for the current number equals the played Card number
            //and if the current Build for the current number equals the played Card suit
            //return the Cards
            if (Builds.get(i).getBuildHolder().getNumber() == playedCard.getNumber() &&
                    Builds.get(i).getBuildHolder().getSuit() == playedCard.getSuit())
            {
                return Cards;
            }
        }
        for (int i = 0; i < looseCards.size(); i++)
        {
            //For every loose Card with the same value as the current played Card if it is equal to our played
            //Card I add it to our vector of Cards.
            if (looseCards.get(i).getNumber() == playedCard.getNumber())
            {
                Cards.add(looseCards.get(i));
            }
        }
        //returning a vector of Cards matching our playedCard value.
        return Cards;
}

    ArrayList < ArrayList<Card> > getMatchingCombination(Card playedCard)
    {
        //Getting all the combinations of loose Cards on the table
        ArrayList< ArrayList<Card> > combinations = getCombinationOfLooseCards();
        boolean deleted = false;
        //Looping through each possible combination
        for (int i = 0; i <= combinations.size(); i++)
        {
            if (deleted == true)
            {
                i = 0;
            }
            if (i < combinations.size() && !playedCard.compareCards(combinations.get(i)))
            {
                //If the combo is not valid for the played Card I erase it
                combinations.remove(combinations.get(0 + i));
                deleted = true;
            }
            else {
                deleted = false;
            }
        }
        //The remaining combos are valid and will be returned to be selected by the user
        return combinations;
    }

    ArrayList<Integer>  getMatchingBuilds(Card playedCard)
    {
        //Creating of vector of vector of Cards (all the Builds)
        ArrayList< ArrayList<Card> > matchingBuild;
        //A vector of int to store the Build IDs
        ArrayList<Integer> ids = new ArrayList<>();
        //For each Build I have on the table
        for (int i = 0; i < Builds.size(); i++)
        {
            if (!Builds.get(i).getIsMultiBuild())
            {
                //If the played Card is equal to the Build at i I store the ID
                if (playedCard.compareCards(Builds.get(i).getBuildCards()) == true)
                    ids.add(i);
            }
            else
            {
                //Else  if the Build is a multi Build and is equal to the played Card I store the id
                if (playedCard.compareCards(Builds.get(i).getMultiBuildCards().get(0)) == true)
                    ids.add(i);
            }

        }
        return ids;
    }

    ArrayList<Integer> checkBuildToIncrease(Card playedCard, String type, ArrayList <Card> Cards)
    {
        ArrayList<Build> BuildsCopy = Builds;
        ArrayList< ArrayList<Card> > allBuilds = new ArrayList<>();
        ArrayList< Integer > ids = new ArrayList<>();
        //For all the Builds on the table if the Build at J has same owner and is not a multi Build
        //I add our Card to the Build and update the new Build in allBuilds
        for (int j = 0; j < BuildsCopy.size(); j++)
        {
            if (BuildsCopy.get(j).getOwner() == type && !BuildsCopy.get(j).getIsMultiBuild())
            {
                BuildsCopy.get(j).getBuildCards().add(playedCard);
                allBuilds.add(BuildsCopy.get(j).getBuildCards());
            }
        }
        //For every Card in player hand and for every Build in our new updated list of
        //Builds, if the Card # and value are equal to a the Card in allBuilds
        //I push back the value of J adding another potential option to Build
        for (int i = 0; i < Cards.size(); i++)
        {

            for (int j = 0; j < allBuilds.size(); j++)
            {
                if (!(Cards.get(i).getNumber() == playedCard.getNumber() && Cards.get(i).getSuit() == playedCard.getSuit())
                        && Cards.get(i).compareCards(allBuilds.get(j)))
                {
                    ids.add(j);
                }
            }
        }
        //Returning id to all possible Build options
        return ids;
    }

    ArrayList<Build> checkBuildOptions(Card playedCard, ArrayList <Card> Cards)
    {
        //Vector that I will return containing all potential Builds
        ArrayList<Build> Builds = new ArrayList<>();
        //Vector of vector of Card combinations of loose Cards on the table
        ArrayList< ArrayList<Card> > combinations = getCombinationOfLooseCards();
        //Size of the combinations
        int size = combinations.size();

        //Looping through all of the combinations
        for (int i = 0; i < size; i++)
        {
            //I push a backup vector of Cards with our played Card to the end of combinations
            ArrayList <Card> backup = combinations.get(i);
            backup.add(playedCard);
            combinations.add(backup);
        }
        //For all of the loose Cards out on the table I make new combinations of the loose Card at I and the played Card
        //And add them to our combinations vector
        for (int i = 0; i < looseCards.size(); i++)
        {
            ArrayList<Card> newCombination1 = new ArrayList<>();
            newCombination1.add(looseCards.get(i));
            newCombination1.add(playedCard);
            combinations.add(newCombination1);
        }
        //Looping through the vector of Cards passed in
        for (int i = 0; i < Cards.size(); i++)
        {
            //Initializing a vector of vector of Cards for multiBuilds
            ArrayList<ArrayList<Card>> multiBuildCards = new ArrayList<>();
            //Creating a Build at the current index of i
            Build newBuild = new Build("Human", Cards.get(i), false);
            //Looping through all of the possible combinations and  if the Card numbers and suits are not equal and the Cards do not compare at i and j
            //I push the multiBuild into our multiBuild vector
            for (int j = 0; j < combinations.size(); j++)
            {
                if (!(Cards.get(i).getNumber() == playedCard.getNumber() && Cards.get(i).getSuit() == playedCard.getSuit()) && Cards.get(i).compareCards(combinations.get(j)))
                {
                    multiBuildCards.add(combinations.get(j));
                }
            }
            //If there is a Build I set our new Build to this multiple Build and push it into our Builds vector
            if (multiBuildCards.size() == 1)
            {
                newBuild.setBuildCards(multiBuildCards.get(0));
                Builds.add(newBuild);
            }
            //If there is a multiple Build I set these Cards to be a multiple Build, set isMultipleBuild to true and push it into the Builds vector
            else if (multiBuildCards.size() > 1)
            {
                newBuild.setMultiBuildCards(multiBuildCards);
                newBuild.setIsMultiBuild(true);
                Builds.add(newBuild);
            }
        }
        return Builds;

    };


    ArrayList< ArrayList<Card> > getCombinationOfLooseCards()
    {
        //Initializing a vector of vector of Cards (these are the possible Card combinations)
        ArrayList< ArrayList<Card> > combination= new ArrayList<>();
        //Outer loose Card loop
        for (int length = 0; length < looseCards.size(); length++)
        {
            //Inner loose Card loop
            for (int i = 0; i < looseCards.size() - 1; i++)
            {

                for (int j = i + 1 + length; j < looseCards.size(); j++)
                {
                    //Creating a vector of Cards
                    ArrayList<Card> Card = new ArrayList<>();
                    for (int k = i; k <= i + length; k++)
                    {
                        //First pushing back the Cards from i to length
                        Card.add(looseCards.get(k));
                    }
                    //I push back the loose Card at j
                    Card.add(looseCards.get(j));
                    //If Card size is greater then 1 then it's actually a combination
                    //So I push it back
                    if (Card.size()>1)
                        combination.add(Card);
                }
            }
        }
        //returning all combinations of loose Cards
        return combination;
    }

    void removeMatchingLooseCardsFromtable(ArrayList<Card> Cards)
    {
        //I loop through the vector of Cards passed in and then the loose Cards on the table
        //If the Cards are the same I erase the Card from the table and reset j
        for (int i = 0; i < Cards.size(); i++)
        {
            for (int j = 0; j < looseCards.size(); j++)
            {
                if (looseCards.get(j).getNumber() == Cards.get(i).getNumber() && looseCards.get(j).getSuit() == Cards.get(i).getSuit())
                {
                    looseCards.remove(looseCards.get(0 + j));
                    j = 0;
                }
            }
        }
    }

    void addCardInLooseCards(ArrayList<Card> Cards)
    {
        for (int i = 0; i < Cards.size(); i++)
        {
            looseCards.add(Cards.get(i));
        }

    }

    String printTable()
    {
        Card cardToPrint = new Card();
        String table = "[ ";
        for (int i = 0; i < Builds.size(); i++)
        {
            if (Builds.get(i).getIsMultiBuild())
            {

                table += cardToPrint.printCombinationCards(Builds.get(i).getMultiBuildCards());
               // Card::printCombinationCards(Builds.get(i).getMultiBuildCards());
            }
            else
            {
               table +=  cardToPrint.printCards(Builds.get(i).getBuildCards());
                //Card::printCards(Builds.get(i).getBuildCards());
            }

        }
        for (int i = 0; i < looseCards.size(); i++)
        {
            table += looseCards.get(i).print();
            table += " ";
        }
        table += " ]";
        return table;
    }
    //add function PrintCardsWhole to card
    private ArrayList<Card> looseCards = new ArrayList<>();
    private ArrayList<Build> Builds = new ArrayList<>();
}
