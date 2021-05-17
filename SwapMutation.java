package framework;

import java.util.ArrayList;

/**
 * Contains implementation for one or more swap operations on a genotype as a
 * mutation strategy
 * 
 * @param <A>
 * @param <C>
 */
public class SwapMutation<A, C extends Comparable<C>> implements MutationStrategy<A, C> {

    int numberOfSwaps;

    public SwapMutation() {
        this(1);    // 1 swap by default
    }

    public SwapMutation(int numberOfSwaps) {
        this.numberOfSwaps = numberOfSwaps;
    }


    /**
     * Randomly swaps two or more positions
     */
    @Override
    public Genotype<A, C> mutate(Genotype<A, C> offspring) {

        ArrayList<A> alleles = offspring.getAlleles();

        int genotypeLength = offspring.getLength();
        for(int i=0; i < this.numberOfSwaps; i++) {

            int position1 = RandomNumberGenerator.randInt(0, genotypeLength - 1);
            int position2 = RandomNumberGenerator.randInt(0, genotypeLength - 1);

            A temp = alleles.get(position2);
            alleles.add(position2, alleles.get(position1));
            alleles.add(position1, temp);
        }

        return offspring.newInstance().setAlleles(alleles);
    }

}