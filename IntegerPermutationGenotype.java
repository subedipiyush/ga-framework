package framework;

import java.util.ArrayList;


/**
 * Implemention of a genotype that represents a permutation of integers
 * over a range
 * 
 */
public class IntegerPermutationGenotype<A, C extends Comparable<C>> extends BoundedGenotype<Integer, C> {

    public IntegerPermutationGenotype(Integer min, Integer max, int length) { 
        super(min, max, length);
    }

    
    /**
     * Returns a new individual with random values within the range specified by the min and max values
     * @param min minimum random value of type A (could be integer or double)
     * @param max maximun random value of type B (could be integer or double)
     * @param length length of the the new chromosome
     * @return this instance
     */
    @Override
    public Genotype<Integer, C> newInstance(Integer min, Integer max, int length) {
        this.alleles = new ArrayList<Integer>();

        for(int i=0; i < length; i++) {
            this.alleles.add(Integer.valueOf(RandomNumberGenerator.randInt(min, max)));
        }

        return this;
    }

}