package framework;

import java.util.ArrayList;

public class TournamentSelector<A, C extends Comparable<C>> implements SelectionStrategy<A, C> {

    final int numberOfParticipants;

    public TournamentSelector() {
        this(2);    // binary tournament by default
    }

    public TournamentSelector(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }


    /**
     * Selects a parent from the population
     */
    @Override
    public Genotype<A, C> select(ArrayList<Genotype<A, C>> population) {

        ArrayList<Genotype<A, C>> parents = new ArrayList<>();

        for(int i=0; i < numberOfParticipants; i++) {
            parents.add(population.get(RandomNumberGenerator.randInt(0, population.size())));
        }

        parents.sort(null); // genotypes are by default sorted by fitness

        return parents.get(0);
    }
    
}