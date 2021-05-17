
import framework.JIndividual;
import framework.Jenetics;
import framework.util.JRandomizer;

/**
 * This class tries to solve the N Queens problem using GA.
 * @author Piyush Subedi
 * @date 8/24/2019
 */

public class N_Queens_Solver extends Jenetics {

    private int numQueens;  // number of queens  

    public N_Queens_Solver(int numQueens, String[] args) {
        super(Integer.parseInt(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Integer.parseInt(args[3]));
        this.numQueens = numQueens;
    }

    @Override
    protected boolean desiredSolutionReached(double fitnessValue) {
        return fitnessValue == numQueens;
    }

    @Override
    protected JIndividual getRandomIndividual() {

        NQueensIndividual ind = new NQueensIndividual();

        int[] values = new int[numQueens];
        for(int i=0; i < numQueens; i++) {
            values[i] = JRandomizer.getRandomIntegerValueBetween(1, n);
        }

        ind.allele = values;

        return ind;
    }

    @Override
    protected double evaluateFitness(JIndividual ind) {
        
        NQueensIndividual nInd = (NQueensIndividual) ind;

        int[] values = nInd.allele;

        for(int i=0; i < (numQueens - 1); i++) {
            for(int j=i+1; j < numQueens; j++) {
                if (values[i] == values[j]) {
                    // 1 -> 2 -> 3 -> 4 -> 5678
                    
                }
            }
        }

    }

    @Override
    protected JIndividual[] selectParents(JIndividual[] population) {
        
        // Tournament selection - extract two parents
        final int NUMBER_OF_PARENTS_TO_SELECT = 2;

        JIndividual[] selectedParents = new JIndividual[NUMBER_OF_PARENTS_TO_SELECT];

        int i = 0;

        while ( i < NUMBER_OF_PARENTS_TO_SELECT ) {
            int individual1Pos,individual2Pos;
            individual1Pos = individual2Pos = 0;

            while(individual1Pos == individual2Pos) {
                individual1Pos = JRandomizer.getRandomIntegerValueBetween(1, numQueens) - 1;
                individual2Pos = JRandomizer.getRandomIntegerValueBetween(1, numQueens) - 1;
            }

            JIndividual parent1 = population[individual1Pos];
            JIndividual parent2 = population[individual2Pos];

            selectedParents[i] = parent1.isFitterThan(parent2) ? parent1 : parent2;

            // duplicate check
            boolean alreadyExists = false;
            for(int k=0; k < i; k++) {
                if(selectedParents[k] == selectedParents[i]) {
                    alreadyExists = true;
                }
            }

            if(alreadyExists) {
                continue;
            }
                
            i++;
        }

        return selectedParents;
 
    }

    @Override
    protected JIndividual[] performCrossOver(JIndividual[] parents) {

        // cut and cross-fill

        return null;
    }

    @Override
    protected JIndividual[] mutate(JIndividual[] offsprings) {

        for (int i=0; i < offsprings.length; i++) {

            JIndividual offSpring = offsprings[i];

            // swap two positions randomly
            int from, to;
            from = to = 1;

            while(from == to) {
                from = JRandomizer.getRandomIntegerValueBetween(1, numQueens) - 1;  // -1 because of zero based indexing
                to = JRandomizer.getRandomIntegerValueBetween(1, numQueens) - 1;
            }
            
            int[] values = offSpring.allele;

            int temp = values[to];
            values[to] = values[from];
            values[from] = temp;

            offSpring.allele = values;
        }

        return offsprings;

    }

    @Override
    protected JIndividual[] selectSurvivors(JIndividual[] population, JIndividual[] offsprings) {
        return null;
    }

}


private class NQueensIndividual extends JIndividual {

    private int[] values;   // represents columnar positions; [1,2,3,4,5,6,7,8] implies that the queens are arranged diagonally

    public NQueensIndividual() {}

    public boolean isFitterThan(JIndividual other) {
        return this.fitness > other.fitness;
    }

    @Override
    public String toString() {
        return "";  // draw a board
    }
}

