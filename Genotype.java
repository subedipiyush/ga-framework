package framework;

import java.util.ArrayList;

/**
 * Representation of an individual/genotype/chromosome
 * @param A allele type
 * @param C fitness value type
 */

public abstract class Genotype<A, C extends Comparable<C>> implements Comparable<Genotype<A, C>> {

    protected C fitness;

    protected ArrayList<A> alleles;

    protected int length;

    public Genotype(int length) {
        this.length = length;
        this.alleles = new ArrayList<>();
    }

    /**
     * Returns a new individual with random values set as alleles
     */
    public abstract Genotype<A, C> newInstance();


    @Override
    public int compareTo(Genotype<A, C> other) {
        return this.isFitterThan(other) ? 1 : -1;
    }


    /**
     * Method to compare this individual with another individual
     * @param other individual to be compared with
     * @return <code>true</code> if the individual is fitter than the given individual else return <code>false</code>
     */
    protected boolean isFitterThan(Genotype<A, C> other) {
        return this.getFitness().compareTo(other.getFitness()) > 0;
    }

    public int getLength() { return length; }

    public C getFitness() { return fitness; }
    public void setFitness(C fitness) { this.fitness = fitness; }

    public ArrayList<A> getAlleles() { return alleles; }
    public Genotype<A, C> setAlleles(ArrayList<A> alleles) { this.alleles = alleles; return this; }

    public A getAlleleAt(int i) { return alleles.get(i); }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for(int i=0; i < this.length; i++) {
            sb.append(getAlleleAt(i).toString() + " ");
        }
        
        return sb.toString();
    }


    public Genotype() {}
}