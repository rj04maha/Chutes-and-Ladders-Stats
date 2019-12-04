import java.io.IOException;
/**
 * This is the driver for HW #2, Enhanced Chutes And Ladders Simulation
 * 
 * @author Darren Lim, Jim Teresco
 * @version 1.0
 */
public class Driver
{
    /**
     * Main method for the Driver.  This uses the same NewChutesAndLadders object to
     * run simulations of different lengths.  It also tests how long it takes to
     * generate the output.
     * 
     * 1.4 is a good time 
     * 
     * @param   args Command Line Arguments
     */
    public static void main(String args[]) throws IOException
    {
        final int UPPER_BOUND = 1_000_000;

        NewChutesAndLadders c = new NewChutesAndLadders(args[0], args[1]);
        long time = System.currentTimeMillis();
        for (int runLength = 10; runLength <= UPPER_BOUND; runLength *= 10)
        {
            System.out.println("Using " + runLength + " iterations");
            c.runSimulation(runLength);
            System.out.println("Average/Minimum/Maximum # of Spins = " + c.getAverageSpins() + " / " + c.getMinSpins() + " / " + c.getMaxSpins());
            System.out.println("Average/Minimum/Maximum # of Chutes = " + c.getAverageChutes() + " / " + c.getMinChutes() + " / " + c.getMaxChutes());
            System.out.println("Average/Minimum/Maximum # of Ladders = " + c.getAverageLadders() + " / " + c.getMinLadders() + " / " + c.getMaxLadders());
            int mostCommon[] = c.getMostCommonSpaces();
            System.out.println("Most landed upon non-end spaces are " + mostCommon[0] + ", " + mostCommon[1] + ", " + mostCommon[2]);
            System.out.println();

        }
        System.out.println("Time elapsed " + (System.currentTimeMillis() - time)/1000.0 + " seconds");

    }
}