
import framework.GAEngine;
import framework.Genotype;
import framework.IntegerPermutationGenotype;

/**
 * Runner class for N Queens problem solver
 * @author Piyush Subedi
 * @date 8/24/2019
 */

public class N_Queens_Runner {

    final static int DEFAULT_NUMBER_OF_QUEENS = 8;

    public static void main(String[] args) {

        int n = DEFAULT_NUMBER_OF_QUEENS;

        GAEngine<Integer, Integer> engine = new GAEngine<>(
            new IntegerPermutationGenotype<Integer, Integer>(1, n, n),
            N_Queens_Runner::evaluate
        );

        engine.populationSize(20)
            .run();

    }

    private static int evaluate(final Genotype<Integer, Integer> gt) {

        int fitness = 0;

        int len = gt.getLength();
        for(int i=0; i < len - 1; i++) {
            for(int j=i+1; j < len; j++) {
                if(Math.abs(i - j) == Math.abs(gt.getAlleleAt(i) - gt.getAlleleAt(j))) {
                    fitness++;
                }
            }
        }

        return fitness;

    }

}

