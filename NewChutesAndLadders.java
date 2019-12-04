import java.util.*;
import java.io.File;
import java.io.IOException;

/**
 * NewChutesAndLadders simulates the game chutes and ladders from reading in from a file
 * prints out statistics about spins, chutes and ladders
 * 
 * @author Rachael Mahar
 * @version due 2/3/7
 */
public class NewChutesAndLadders
{
    // instance variables - averages and min/maxes
    private double averageSpins;
    private double averageChutes;
    private double averageladders;
    private int[] mostCommonSpaces;
    private double maxSpins;
    private double minSpins;
    private double maxChutes;
    private double minChutes;
    private double maxLadders;
    private double minLadders;
    private String boardFile;
    private String deckFile;
    ArrayList deckOfCards;
    // contructor which will be called by the driver
    public NewChutesAndLadders(String board, String deck) {
        this.averageSpins = 0;
        this.averageChutes = 0;
        this.averageladders = 0;
        this.mostCommonSpaces = new int[3];
        this.maxSpins = 0;
        this.minSpins = 1000;
        this.maxChutes = 0;
        this.minChutes = 1000;
        this.maxLadders = 0;
        this.minLadders = 1000;
        this.boardFile = board;
        this.deckFile = deck;
    }
    // which will be called with the number of games (>0) that will be run.
    public void runSimulation(int simulationCount) throws IOException {
        // scanners for board and deck file
        Scanner boardScanner = new Scanner(new File(boardFile));
        Scanner deckScanner = new Scanner(new File(deckFile));
        // scans in first number in file, the number of spaces
        int numOfSpaces = boardScanner.nextInt();
        // creates array of spaces and sets them all to index value
        int[] board = new int[numOfSpaces+1];
        for (int i = 1; i < numOfSpaces+1; i++) {
            board[i] = i;
        }
        // scans in number of chutes
        int numOfChutes = boardScanner.nextInt();
        // scans in chutes and stores them into board array
        for (int i = 0; i < numOfChutes; i++) {
            int start = boardScanner.nextInt();
            int end = boardScanner.nextInt();
            board[start] = end;
        }
        // scans in ladders and stores them into board array
        int numOfLadders = boardScanner.nextInt();
        for (int i = 0; i < numOfLadders; i++) {
            int start = boardScanner.nextInt();
            int end = boardScanner.nextInt();
            board[start] = end;
        }
        // scans in numbers of cards and how many different numbers
        int numOfCards = deckScanner.nextInt();
        int typesOfCards = deckScanner.nextInt();
        // creates ArrayList of cards
        deckOfCards = new ArrayList(numOfCards);
        while(deckScanner.hasNextInt()) {
            int amount = deckScanner.nextInt();
            int number = deckScanner.nextInt();
            for (int i = 0; i < amount; i++) {
                deckOfCards.add(number);
            }
        }
        // start position at 0
        int position = 0;
        int num = 0;
        int cardFromDeck = 0;
        // array to check most common board piece landed on - set to zero
        int[] common = new int[numOfSpaces];
        // initalizes statisitcs to zeros
        double totalSpins = 0;
        double totalChutes = 0;
        double totalladders = 0;
        double spins = 0;
        double ladders = 0;
        double chutes = 0;
        // for loop will execute amount of games you simulate
        for (int i = 0; i < simulationCount; i++) {
            // resets statistics to zero after each game
            spins = 0;
            ladders = 0;
            chutes = 0;
            position = 0;
            // puts cards back in deck at end of game
            ArrayList usingDeckOfCards = new ArrayList(deckOfCards);
            Collections.shuffle(usingDeckOfCards);
            while (position < numOfSpaces) {
                // gets random card from deck
                // reloads deck if there are no cards left
                if (usingDeckOfCards.size() == 0) {
                    usingDeckOfCards = new ArrayList(deckOfCards);
                    Collections.shuffle(usingDeckOfCards);
                }
                // get first card from the ArrayList and assigns it to the num
                num = (int)usingDeckOfCards.get(0);
                // removes first card from ArrayList
                usingDeckOfCards.remove(usingDeckOfCards.get(0));
                // increment spin
                spins++;
                position = position + num;
                // if position is at zero and spins -1
                if (position < 1) {
                    position = 1;
                }
                // if position ends up over numOfSpaces, then stay there & do not count as a space landing
                if (position > numOfSpaces) {
                    position = position - num;
                    common[position]--;
                }
                // if it is a ladder
                if (board[position] > position) {
                    ladders++;
                }
                // if it is a chute
                if (board[position] < position) {
                    chutes++;
                }
                // if position reaches numOfSpaces, game is over
                if (position == numOfSpaces) {
                    continue; // gameover
                }
                position = board[position];
                // there is also a chance you could go to a latter to numOfSpaces
                if (position == numOfSpaces) {
                    continue; // gameover
                }
                // if game still goes on, you increment the count of position you are on
                common[position]++;
            }
            // if there is a new max spins
            if (maxSpins < spins) {
                maxSpins = spins;
            }
            if (minSpins > spins) {
                minSpins = spins;
            }
            if (maxChutes < chutes) {
                maxChutes = chutes;
            }
            if (minChutes > chutes) {
                minChutes = chutes;
            }
            if (maxLadders < ladders) {
                maxLadders = ladders;
            }
            if (minLadders > ladders) {
                minLadders = ladders;
            }
            totalSpins = totalSpins + spins;
            totalladders = totalladders + ladders;
            totalChutes = totalChutes + chutes;
        }
        averageSpins = totalSpins / simulationCount;
        averageChutes = totalChutes / simulationCount;
        averageladders = totalladders / simulationCount;
        // calculates what the common space is
        // assignes most common space to the first index in common board
        int mostCommonSpaceAmt = common[1];
        // loops through all common spaces
        for (int i = 1; i < numOfSpaces; i++) {
            // if the value in the common space in i in loop is 
            // greater than most common space amount
            if (mostCommonSpaceAmt < common[i]) {
                // assigns most common space slot 1 to found common slot
                mostCommonSpaces[0] = i;
                // updates highest amount found
                mostCommonSpaceAmt = common[i];
                
            }
        }
        common[mostCommonSpaces[0]] = 0;
        mostCommonSpaceAmt = common[1];
        for (int i = 1; i < numOfSpaces; i++) {
            if (mostCommonSpaceAmt < common[i]) {
                mostCommonSpaces[1] = i;
                mostCommonSpaceAmt = common[i];
            }
        }
        common[mostCommonSpaces[1]] = 0;
        mostCommonSpaceAmt = common[1];
        for (int i = 1; i < numOfSpaces; i++) {
            if (mostCommonSpaceAmt < common[i]) {
                mostCommonSpaces[2] = i;
                mostCommonSpaceAmt = common[i];
            }
        }
    }
    // which will return the average number of spins in a game after a simulation is run.
    public double getAverageSpins() {
        return averageSpins;
    }
    // which will return the average number of chutes landed in a game after a simulation is run.
    public double getAverageChutes() {
        return averageChutes;
    }
    // which will return the average number of ladders landed in a game after a simulation is run.
    public double getAverageLadders() {
        return averageladders;
    }
    // which will return the number of the square most landed upon in a game.
    public int[] getMostCommonSpaces() {
        return mostCommonSpaces;
    }
    // will retrieve the maximum number of spins in a game during the simulation
    public double getMaxSpins() {
        return maxSpins;
    }
    // will retrieve the minimum number of spins in a game during the simulation
    public double getMinSpins(){
        return minSpins;
    }
    // will retrieve the maximum number of chutes landed upon in a game during the simulation
    public double getMaxChutes(){
        return maxChutes;
    }
    // will retrieve the minimum number of chutes landed upon in a game during the simulation
    public double getMinChutes(){
        return minChutes;
    }
    // will retrieve the maximum number of ladders landed upon in a game during the simulation
    public double getMaxLadders(){
        return maxLadders;
    }
    // will retrieve the minimum number of ladders landed upon in a game during the simulation
    public double getMinLadders(){
        return minLadders;
    }
}
