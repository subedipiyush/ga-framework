package framework;

import java.util.ArrayList;

public class EvolutionState<A, C extends Comparable<C>> {

    ArrayList<Genotype<A, C>> population;

    Genotype<A, C> mostFitIndividual;

    int numberOfGenerations;

    public EvolutionState() {
        this.numberOfGenerations = 0;
        this.population = null;
        this.mostFitIndividual = null;
    }

    public ArrayList<Genotype<A, C>> getPopulation() {
        return this.population;
    }

    public int getNumberOfGenerations() {
        return this.numberOfGenerations;
    }

    public void setPopulation(ArrayList<Genotype<A, C>> population) {
        this.population = population;

        this.mostFitIndividual = discoverMostFitIndividual();
        this.numberOfGenerations++;
    }

    public Genotype<A, C> discoverMostFitIndividual() {
        ArrayList<Genotype<A, C>> temp = new ArrayList<>(this.population);  // store in temp such that the original list is not disrupted
        temp.sort(null); 

        return temp.get(0);
    }

    public void print() {
        System.out.printf("################# GENERATION #%d ################\n", numberOfGenerations);        

        for(Genotype<A, C> gt : population) {
            System.out.printf("%s ---> %2d\n", gt.toString(), gt.getFitness());
        }

        System.out.printf("Most fit individual : %s with fitness %2d\n\n", mostFitIndividual.toString(), mostFitIndividual.getFitness());
    }

}