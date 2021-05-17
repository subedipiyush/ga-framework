package framework;

/**
 * Representation of an individual/genotype/chromosome with a min and max value restriction on the alleles
 */

public abstract class BoundedGenotype<A, C extends Comparable<C>> extends Genotype<A, C> {

    /**
     * The minimum allele value allowed
     */
    A min;

    /**
     * The maximum allele value allowed
     */
    A max;

    public BoundedGenotype(A min, A max, int length) {
        super(length);

        this.min = min;
        this.max = max;
    }

    /**
     * Returns a new individual with random values set as alleles
     * @param length length of the chromosome
     */
    @Override
    public Genotype<A, C> newInstance() {
        return newInstance(min, max, length);
    }

    /**
     * Returns a new individual with random values within the range specified by the min and max values
     * @param min minimum random value of type A (could be integer or double)
     * @param max maximun random value of type B (could be integer or double)
     * @param length length of the the new chromosome
     * @return this instance
    */
    public abstract Genotype<A, C> newInstance(A min, A max, int length);

}