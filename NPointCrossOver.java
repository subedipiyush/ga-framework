package framework;

import java.util.ArrayList;

/**
 * Contains implementation for an N-point crossover operation
 * NOTE: Only supports 2 parents crossover and results only in 2 children
 * @param <A>
 * @param <C>
 */
public class NPointCrossOver<A, C extends Comparable<C>> implements CrossOverStrategy<A, C> {

    final int numberOfPoints;
    final int numberOfOffSprings;

    public NPointCrossOver(int numberOfPoints) {
        this(numberOfPoints, 2);    // 2 offsprings by default
    }

    public NPointCrossOver(int numberOfPoints, int numberOfOffSprings) {
        this.numberOfPoints = numberOfPoints;
        this.numberOfOffSprings = numberOfOffSprings;
    }


    /**
     * Selects a parent from the population
     */
    @Override
    public ArrayList<Genotype<A, C>> crossOver(ArrayList<Genotype<A, C>> parents) {

        ArrayList<Genotype<A, C>> offsprings = new ArrayList<>();

        ArrayList<Integer> points = new ArrayList<>();

        int genotypeLength = parents.get(0).getLength();

        for(int i=0; i < this.numberOfPoints; i++) {

            int randomPoint = RandomNumberGenerator.randInt(1, genotypeLength - 2); // we are excluding boundary values as having crossover exactly at boundaries (i.e 0 or genotype length) makes no sense
            while(points.indexOf(randomPoint) != -1) {
                randomPoint = RandomNumberGenerator.randInt(1, genotypeLength - 2);
            }
            points.add(randomPoint);
        }

        points.sort(null);

        ArrayList<A> allelesForFirstChild = new ArrayList<>();
        ArrayList<A> allelesForSecondChild = new ArrayList<>();
        
        int startingPoint = 0;
        int parentNum = 0;  // 1st parent; we will keep switching at points
        for(int i=0; i < this.numberOfPoints; i++) {
            int endingPoint = points.get(i) + 1;
            allelesForFirstChild.addAll(parents.get(parentNum).getAlleles().subList(startingPoint, endingPoint));
            allelesForSecondChild.addAll(parents.get(~parentNum).getAlleles().subList(startingPoint, endingPoint));
            startingPoint = endingPoint + 1;
            parentNum = ~parentNum;
        }

        offsprings.add(parents.get(0).newInstance().setAlleles(allelesForFirstChild));
        offsprings.add(parents.get(0).newInstance().setAlleles(allelesForSecondChild));

        return offsprings;
    }
    
}