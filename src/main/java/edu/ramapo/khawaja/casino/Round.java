package edu.ramapo.khawaja.casino;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Round
{
    //Enums for player used for things like dealing/removing cards, setting turns, setting the last capture, setting the score.
    //also used to check who ended the round.
    enum PLAYER
    {
        HUMAN(0), COMPUTER(1);
        public int playerVal;

        PLAYER(int numVal)
        {
            this.playerVal = numVal;
        }

        public int getPlayerVal() {
            return playerVal;
        }
    };
    //enum value for turn: Only really used for computer player moves/ User suggestions
    enum TURN {
        CAPTURE(1), BUILD(2), INCBUILD(3), TRAIL(4);
        private int turnVal;

        TURN(int turnVal)
        {
            this.turnVal = turnVal;
        }

        public int getTurnVall() {
            return turnVal;
        }
    };
    TURN turnType;
    //Latest capture - 0 is human, 1 is player. It kept breaking when I made it private or protected. Sorry =[
    int latestCapture = 0;
    public Round ()
    {
        Player Human = new Player("Human");
        players.add(Human);

        Player Computer = new Player("Computer");
        players.add(Computer);
    }

    Round(boolean useFileForDeck)
    {
        //Creating a vector of players, vector.get(0) is human, at.(1) is computer
        Player Human = new Player("Human");
        players.add(Human);

        Player Computer = new Player("Computer");
        players.add(Computer);
        roundNumber = 1;
        if (useFileForDeck)
        {
           // Deck.populatefromFile();
        }
        else
        {
            Deck.autoPopulateDeck();
        }
    }

    void humanMove()
    {

        System.out.print( "What card would you like to play?: ");
        //Card playedCard = players.get(HUMAN).validatePlayedCard();

       // gameEngine(playedCard);

    }
    void computerGameEngine(PLAYER currentPlayer, PLAYER opponent)
    {
        int maxScore = 0;
        int currentScore = 0;
        Card chosenCard = new Card();
        //By default the turn type is a trail because that means
        //the CPU has no moves to make.
        turnType = TURN.TRAIL;
        for (int i = 0; i < players.get(currentPlayer.getPlayerVal()).getHand().size(); i++)
        {
            //Creating a potential Card to be played (this Card is whatever i is)
            Card playedCard = players.get(currentPlayer.getPlayerVal()).getHand().get(i);
            //Creating a dummyPlayer and since the last value is false it is just being used
            //by the computer to calculate the potential score of their move.
            Player cpuPlayer = Table.getCaptureCardsForPlayedCard(playedCard, players, currentPlayer.getPlayerVal(),
                    opponent.getPlayerVal(), true, false);

            Player otherPlayer = Table.getCaptureCardsForPlayedCard(playedCard, players, opponent.getPlayerVal(),
                    currentPlayer.getPlayerVal(), true, false);
            //calculating the current score of the player
            currentScore = cpuPlayer.calculateScore(cpuPlayer, otherPlayer );
            //If this newly calculated score is greater then maxScore
            //I set chosenCard to the current Card, max Score is updated and the turnType is capture
            if (currentScore > maxScore)
            {
                chosenCard = playedCard;
                maxScore = currentScore;
                turnType = TURN.CAPTURE;
            }
            //Now I do the same as above but instead of doing it for a capture
            //I calculate the score for a build for the played Card at i.
            cpuPlayer = Table.getBuildForPlayedCard(playedCard, players, currentPlayer.getPlayerVal(), opponent.getPlayerVal(), true, false);
            currentScore = cpuPlayer.calculateScore(cpuPlayer, otherPlayer);
            //If this newly calculated score is greater then maxScore
            //I set the chosen Card to the current Card I are checking and change
            //the turn type to build
            if (currentScore > maxScore)
            {
                chosenCard = playedCard;
                maxScore = currentScore;
                turnType = TURN.BUILD;
            }
            //Now I check for build increases and do the same.
            currentScore = Table.getBuildIncForPlayedCard(playedCard, players, currentPlayer.getPlayerVal(), opponent.getPlayerVal(), true, false);
            //If the current score with a build increase is greater then the max
            //The turn type is an Increased build and the maxScore and current Card to play
            //is updated
            if (currentScore > maxScore)
            {
                chosenCard = playedCard;
                maxScore = currentScore;
                turnType = TURN.INCBUILD;
            }

        }
        //Here I check turnType - if it is a capture
        //I get the captured Cards for the player. If The player is a computer the move is made for them
        //and their hand and pile is updated.
        if (turnType == TURN.CAPTURE)
        {
            Player cpuPlayer = Table.getCaptureCardsForPlayedCard(chosenCard, players, currentPlayer.getPlayerVal(), opponent.getPlayerVal(), false, true);
            if (currentPlayer == PLAYER.COMPUTER)
            {
                players.get(currentPlayer.getPlayerVal()).addOneCardInPile(chosenCard);
                players.get(currentPlayer.getPlayerVal()).removeCardFromHand(chosenCard);
                latestCapture = PLAYER.COMPUTER.getPlayerVal();
                checkForRoundEnd(PLAYER.COMPUTER, PLAYER.HUMAN);
            }
        }
        //if turnType is a build
        //I get the Cards to build for the player. If The player is a computer the move is made for them
        //and their hand and pile is updated.
        else if (turnType == TURN.BUILD)
        {
           // System.out.println(chosenCard.print());
            Player cpuPlayer = Table.getBuildForPlayedCard(chosenCard, players, currentPlayer.getPlayerVal(), opponent.getPlayerVal(), false, true);
            if (currentPlayer == PLAYER.COMPUTER)
            {
                players.get(currentPlayer.getPlayerVal()).removeCardFromHand(chosenCard);
            }
        }
        //if turnType is an increased build
        //I get the Cards to build for the player. If The player is a computer the move is made for them
        //and their hand and pile is updated.
        else if (turnType == TURN.INCBUILD)
        {
            int buildIncIds = Table.getBuildIncForPlayedCard(chosenCard, players, currentPlayer.getPlayerVal(), opponent.getPlayerVal(), false, true);
            if (currentPlayer == PLAYER.COMPUTER)
            {
                players.get(currentPlayer.getPlayerVal()).removeCardFromHand(chosenCard);
            }
        }
        //If turnType is trail and the player is a computer print that the CPU
        //has trailed a Card. Then I add the Card to the table and
        //remove the Cards from the computers hand
        else if (turnType == TURN.TRAIL && currentPlayer == PLAYER.COMPUTER)
        {
            System.out.print("\n\nThe computer has trailed the ");
            message += "\n\nThe computer has trailed the " ;

            //players.get(currentPlayer.getPlayerVal()).getHand().get(0).printWhole();
            if (players.get(currentPlayer.getPlayerVal()).getHand().size() > 0)
            {
                message = message + (players.get(currentPlayer.getPlayerVal()).getHand().get(0).printWhole());
                System.out.print("\n because they have no options for capturing, building, or increasing a build.");
                message += "\n because they have no options for capturing, building, or increasing a build.";

                Table.getLooseCards().add(players.get(currentPlayer.getPlayerVal()).getHand().get(0));
                players.get(PLAYER.COMPUTER.getPlayerVal()).removeCardFromHand(players.get(currentPlayer.getPlayerVal()).getHand().get(0));
                checkForRoundEnd(PLAYER.COMPUTER, PLAYER.HUMAN);
            }

        }
        else  if (turnType == TURN.TRAIL && currentPlayer == PLAYER.HUMAN)
        {
            System.out.print("\nYou should trail a card because I could not find any viable captures or builds for you.");
            message += "\nYou should trail a card because I could not find any viable captures or builds for you.";
        }
        //Printing our new updated table!
        System.out.print("\n\n");
        System.out.print("\n\nTable: ");
        Table.printTable();

    }

    void gameEngine(Card playedCard)
    {
        boolean  isCardPlayed = false;
        //Capturing all matching Cards of the size entered (So if S8 entered and an H8 is on the table
        //I are capturing it
        ArrayList <Card> matchingCards = new ArrayList<Card>();
        matchingCards = Table.getMatchingLooseCards(playedCard);
        if (matchingCards.size() > 0)
        {
            System.out.print("\n-----The following matching cards has been added into your pile-----\n");
            message += "\n-----The following matching cards has been added into your pile-----\n";
            Card printMatching = new Card();
            printMatching.printCards(matchingCards);
            message +=  printMatching.printCards(matchingCards);
            message += "\n\n";
            System.out.print("\n\n");
            //adding the Card to the human pile and removing it from the hand so no duplicates
            //and then removing the matching Card from the table. Card played is now true
            //because I played a Card
            players.get(PLAYER.HUMAN.getPlayerVal()).addCardInPile(matchingCards, playedCard);
            players.get(PLAYER.HUMAN.getPlayerVal()).removeCardFromHand(playedCard);
            Table.removeMatchingLooseCardsFromtable(matchingCards);
            isCardPlayed = true;
        }
        //now I check for Cards I can capture by creating a vector of vector of Cards (so
        //a bunch of Card combinations I could capture)
        ArrayList < ArrayList<Card> > matchingCombination = Table.getMatchingCombination(playedCard);
        //while there is anything in this vector (that means I can capture)
        while (matchingCombination.size() > 0)
        {
            //I print the potential Cards that can be captured and offer the user the option
            //to pick
            message += "\n-----Following are the combination of cards you can capture-----\n";
            System.out.print("\n-----Following are the combination of cards you can capture-----\n");
            Card printCombinations = new Card();
            message += printCombinations.printCombinationCards(matchingCombination);
            printCombinations.printCombinationCards(matchingCombination);
            System.out.print("\nPlease choose the position (1, 2, etc.) of combination you want to capture (Press N/n to leave): ");
            //Setting up input validation - every matching combination + n and N incase the user
            //does not want to capture anything
            int size = (int)matchingCombination.size() + 2;
            ArrayList<String> options = new ArrayList<>();
            options.add("N");
            options.add("n");
            for (int i = 1; i <= matchingCombination.size(); i++)
            {
                //pushing back all the options user can select (n,N,1,2,3, etc)
                options.add(Integer.toString(i));
            }
            String option = validateOptionVector(options);
            if (option != "N" && option != "n")
            {
                //Index equals the option - 1, and then I add the selected Cards to the pile
                //I then remove the Cards from the table and then erase the vector of vectors.
                //isCardPlayed is now true because a card has been played.
                int index = Integer.parseInt(option) - 1;
                System.out.print("\n-----The following matching cards have been added into your pile-----\n");
                Card matchedCards = new Card();
                matchedCards.printCards(matchingCombination.get(index));
                players.get(PLAYER.HUMAN.getPlayerVal()).addCardInPile(matchingCombination.get(index), playedCard);
                Table.removeMatchingLooseCardsFromtable(matchingCombination.get(index));
                matchingCombination.remove(matchingCombination.get(0+index));
                isCardPlayed = true;
            }
            else
            {
                break;
            }
        }
        //now I make a vector of integers to and get Matching builds for the played Card
        ArrayList<Integer> ids = Table.getMatchingBuilds(playedCard);
        //while the id vector has anything in it I offer the user the builds they can capture
        while (ids.size() > 0)
        {
            System.out.print("\n-----Following are the builds of Cards you can capture-----\n");
            //I store the builds in a vector of builds
            ArrayList<Build>  buildCopy = Table.getBuilds();
            ArrayList<Build> buildToPrint = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++)
            {
                buildToPrint.add(buildCopy.get(ids.get(i)));

            }
            Build printingAvailableBuilds = new Build();
            printingAvailableBuilds.printBuilds(buildToPrint);
            //Same deal earlier as the capture: I offer the user the option of builds they'd like to capture
            //Store all the possible options (including N/n for if they do not want to capture a build)
            //and allow the user to select which one.
            System.out.print("\nPlease choose the option (ex. 1, 2) of build you want to capture (press n/N to exit without building): ");
            //Making size vector size + 2 to factor in N/n user input
            int size = ids.size() + 2;
            ArrayList<String> options = new ArrayList<>();
            options.add("N");
            options.add("n");
            for (int i = 1; i <= buildToPrint.size(); i++)
            {
                options.add(Integer.toString(i));
            }
            String option = validateOptionVector(options);
            if (option != "N" && option != "n")
            {
                //get the user selection and then get the build at the index
                //I print it to let the user know it's been added to their pile.
                int index = Integer.parseInt(option) - 1;
                Build selectedBuild = Table.getBuilds().get(ids.get(index));
                System.out.print("----------The following build has been added into your pile----------\n" );

                selectedBuild.print();
                //if the build is a multiple build I loop through and add all the builds to the user pile
                //Otherwise the single build is added into the user pile.
                if (selectedBuild.getIsMultiBuild())
                {
                    for (int i = 0; i < selectedBuild.getMultiBuildCards().size(); i++)
                    {
                        players.get(PLAYER.HUMAN.getPlayerVal()).addCardInPile(selectedBuild.getMultiBuildCards().get(i), playedCard);
                    }
                }
                else
                {
                    players.get(PLAYER.HUMAN.getPlayerVal()).addCardInPile(selectedBuild.getBuildCards(), playedCard);
                }
                //I remove the build from the table, clear the ids vector, and isCardPlayed is now true
                Table.removeBuild(ids.get(index));
                ids.remove(ids.get(0+index));
                isCardPlayed = true;
                break;
            }
            else
            {
                break;
            }
        }
        //now isCardPlayed comes into play -
        //If a Card is played I addthePlayedCard to the pile
        //Remove the Card from the hand and updated latestCapture
        //and also check if the round is over for the human
        if (isCardPlayed)
        {
            players.get(PLAYER.HUMAN.getPlayerVal()).addOneCardInPile(playedCard);
            players.get(PLAYER.HUMAN.getPlayerVal()).removeCardFromHand(playedCard);
            latestCapture = PLAYER.HUMAN.getPlayerVal();
            checkForRoundEnd(PLAYER.HUMAN, PLAYER.COMPUTER);
        }
        //if isCardPlayed is still false the player will now be given the option to build. Same deal as
        //before. Creating a vector of builds and checking the user build options.
        ArrayList<Build> builds = Table.checkBuildOptions(playedCard, players.get(PLAYER.HUMAN.getPlayerVal()).getHand());
        //If no Card is played and the build of builds vector has data in it
        while (!isCardPlayed && (builds.size() > 0))
        {
            //I show the user what build options they have, store all the indexes of builds with
            //"N" and "n" incase the user does not want to build
            System.out.print("----------The following builds are available for you----------\n");
            Build availableBuildsPrint = new Build();
            availableBuildsPrint.printBuilds(builds);
            System.out.print("Please choose the list number(1,2, etc) of the build you want to make (enter N/n to leave): ");
            ArrayList<String> options = new ArrayList<>();
            options.add("N");
            options.add("n");
            for (int i = 1; i <= builds.size(); i++)
            {
                options.add(Integer.toString(i));
            }
            String option = validateOptionVector(options);
            if (option != "N" && option != "n")
            {
                //When the user selects an option I print the build created,
                //add the build to builds vector (a vector of builds containing all the builds),
                //And then remove the loose Cards from the table (Since they are in a build now).
                int index = Integer.parseInt(option) - 1;
                System.out.print("-----The following build Has been formed------\n");
                builds.get(index).print();
                Table.addBuild(builds.get(index));
                Table.removeMatchingLooseCardsFromtable(builds.get(index).getBuildCards());
                //I remove the played Card from the hand, and erase the build
                players.get(PLAYER.HUMAN.getPlayerVal()).removeCardFromHand(playedCard);
                builds.remove(builds.get(0 + index));
                isCardPlayed = true;
                break;
            }
            else
            {
                break;
            }
        }
        //I are checking if there is a build to increase
        ids = Table.checkBuildToIncrease(playedCard, "COMPUTER", players.get(PLAYER.HUMAN.getPlayerVal()).getHand());
        if (ids.size() > 0 && !isCardPlayed)
        {
            //Show the user what builds they can improve upon
            System.out.print("The following builds are available for you to increase\n");
            ArrayList<Build>  buildCopy = Table.getBuilds();
            ArrayList<Build> buildToPrint = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++)
            {
                buildToPrint.add(buildCopy.get(ids.get(i)));
                buildToPrint.get(ids.get(i)).getBuildCards().add(playedCard);
            }
            Build buildsAvailable = new Build();
            buildsAvailable.printBuilds(buildToPrint);
            // Allow the user to select their build and if they do not wish to increase they can
            //terminate with N/n.
            System.out.print("Please choose the list number (1, 2, etc.) of the build you would like to capture (or n/N to exit without building): ");
            ArrayList<String> options = new ArrayList<>();
            options.add("N");
            options.add("n");
            for (int i = 1; i <= buildToPrint.size(); i++)
            {
                options.add(Integer.toString(i));
            }
            String option = validateOptionVector(options);
            if (option != "N" && option != "n")
            {
                //If they selected something I own the new build, the Card is added to the build
                //The new build is printed, the playedCard is removed from human hand
                //and isCardPlayed is now true.
                int index = Integer.parseInt(option) - 1;
                System.out.print("The following build ownership is now yours: \n");
                Table.addCardInBuilds(playedCard, index);
                //Table.getBuilds().get(ids.get(index)).getBuildCards().add(playedCard);
                Table.getBuilds().get(ids.get(index)).print();
                players.get(PLAYER.HUMAN.getPlayerVal()).removeCardFromHand(playedCard);
                isCardPlayed = true;
            }
        }
        //If a Card is still not played then I trail the Card
        if (!isCardPlayed)
        {
            //I get all the loose Cards and push back our played Card.
            //Then I remove it from the human players hand.
            //Finally, I print the new table.
            System.out.print("Your card has been trailed. \n");
            Table.getLooseCards().add(playedCard);
            players.get(PLAYER.HUMAN.getPlayerVal()).removeCardFromHand(playedCard);
            System.out.print("\n\n");
            System.out.print("Table: \n");
            Table.printTable();
            checkForRoundEnd(PLAYER.HUMAN, PLAYER.COMPUTER);
        }
    }
    /* validateOption(String options, int size)
    {
        Scanner cin = new Scanner(System.in);
        boolean isFalse = true;
        String option = cin.nextLine();
        while (isFalse == true)
        {
            for (int i = 0; i < size; i++)
            {
                if (options[i] == option)
                {
                    return option;
                }
            }

            System.out.print("Invalid selection; try again: ");

            option = cin.nextLine();
        }
        return option;
    }*/
    String validateOptionVector(ArrayList<String> options)
    {
        Scanner cin = new Scanner(System.in);
        boolean isFalse = true;
        String option = cin.nextLine();

        while (isFalse)
        {
            for (int i = 0; i < options.size(); i++)
            {
                if (options.get(i) == option)
                {
                    return option;
                }
            }

            System.out.print("Invalid selection  try again: ");

            option = cin.nextLine();
        }
        return option;
    }
    void checkForRoundEnd(PLAYER player1, PLAYER player2)
{
    String lastCap;
    if (latestCapture == 0)
    {
        lastCap = "Human";
    }
    else
    {
        lastCap = "Computer";
    }
    Card Card = new Card();
    //If the player's got no Cards (since they get dealt first) and the
    //deck is empty I get all the builds and add all the leftover builds to the proper player
    if ((players.get(player1.getPlayerVal()).getHand().size() == 0) && (players.get(player2.getPlayerVal()).getHand().size() == 0) && (Deck.getDeckCards().size() == 0))
    {
        for (int i = 0; i < Table.getBuilds().size(); i++)
        {
            Build selectedBuild = Table.getBuilds().get(i);
            System.out.print( "\n\nThe Following table builds have been added into pile because " + lastCap + " had the last capture:\n");
            selectedBuild.print();
            if (selectedBuild.getIsMultiBuild())
            {
                for (int j = 0; j < selectedBuild.getMultiBuildCards().size(); j++)
                {
                    players.get(player1.getPlayerVal()).addCardInPile(selectedBuild.getMultiBuildCards().get(j), Card);
                }
            }
            else
            {
                players.get(player1.getPlayerVal()).addCardInPile(selectedBuild.getBuildCards(), Card);
            }
        }
        //I clear the table of the builds since they have been added to the players pile and now
        //the loose Cards are added to whomever had the last capture.
        Table.clearBuilds();
        System.out.print("\n\nThe following loose cards have been added into the pile because " + lastCap + " had the last capture:\n ");
        Card looseCardsToPile = new Card();
        looseCardsToPile.printCards(Table.getLooseCards());
        //I print the Cards that are added, add them to the players pile,
        //and clear the table (which should be clear now).
        players.get(player1.getPlayerVal()).addCardInPile(Table.getLooseCards(), Card);

        //Printing Cards
        System.out.print("\n" + players.get(player1.getPlayerVal()).getName() + " had " + players.get(player1.getPlayerVal()).getPile().size() + " cards.\n");
        System.out.print(players.get(player2.getPlayerVal()).getName() + " had " + players.get(player2.getPlayerVal()).getPile().size() + " cards.\n");
        //Printing spades
        System.out.print("\n" + players.get(player1.getPlayerVal()).getName() + "'s # of spades: " + players.get(player1.getPlayerVal()).getCardCount("S"));
        System.out.print(players.get(player2.getPlayerVal()).getName() + "'s # of spades: " + players.get(player2.getPlayerVal()).getCardCount("S") + "\n");

        //Printing piles
        players.get(player1.getPlayerVal()).printPile();
        players.get(player2.getPlayerVal()).printPile();
        //Clearing table
        Table.clearLooseCards();
    }
}
    void dealCards(boolean isHuman, boolean isComputer, boolean isTable)
    {
        if (isHuman)
        {
            players.get(PLAYER.HUMAN.getPlayerVal()).addCardInHand(Deck.drawFourCards());
            //System.out.println(players.get(PLAYER.HUMAN.getPlayerVal()).getHand());
        }
        if (isComputer)
        {
            players.get(PLAYER.COMPUTER.getPlayerVal()).addCardInHand(Deck.drawFourCards());
            //System.out.println(players.get(PLAYER.COMPUTER.getPlayerVal()).getHand());
            //System.out.println(Deck.printDeck());
        }
        if (isTable)
        {
            Table.addCardInLooseCards(Deck.drawFourCards());
        }

    }
    void setScoreForPlayer(PLAYER player, int score)
    {
        this.players.get(player.getPlayerVal()).setScore(score);
    }

    void setRound(int round)
    {
        roundNumber = round;
    }
    int getRound()
    {
        return roundNumber;
    }
    void setTurn(int turn)
    {
        this.turn = turn;
    }
    String getMessage()
    {
        return message;
    }
    TURN getTurnType()
    {
        return  turnType;
    }
    int getTurn()
    {
        return turn;
    }
    void setFirstTurn(int turn)
    {
        this.turn = turn;
    }
    public Deck getDeck()
    {
        return Deck;
    }
    public Table getTable() { return Table;}
    public ArrayList<Player> getPlayers()
    {
        return players;
    }




    private ArrayList<Player> players = new ArrayList<>();
    private Deck Deck = new Deck();
    private Table Table = new Table();
    private int roundNumber = 1;
    private int turn = 0; // 1 is Human 2 is Computer
    private int nextTurn = 0;
    String message = "";

    //problems
    //HumanMove .get(HUMAN)
}
