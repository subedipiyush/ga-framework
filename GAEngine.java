package framework;

import java.util.ArrayList;
import java.util.function.Predicate;


/**
 * GA Engine - main class to run a GA algorithm
 * @author Piyush Subedi
 * @date 9/17/2019
 * 
 * @param A allele type
 * @param C fitness value type
 */

public final class GAEngine<A, C extends Comparable<C>> {


    final int DEFAULT_POPULATION_SIZE = 100;

    final Double DEFAULT_CROSSOVER_RATE = 0.8;    // 80%

    final Double DEFAULT_MUTATION_RATE = 0.2;     // 20%

    final int DEFAULT_FITNESS_EVALUATION_THRESHOLD = 10000;

    private Genotype<A, C> genotypeFactory;
    private FitnessEvaluator<A, C> fitnessEvaluator;

    private int size_of_population;
    private double crossover_rate;
    private double mutation_rate;
    private int fitness_evaluation_threshold;
    
    private SelectionStrategy<A, C> selectionStrategy;
    private CrossOverStrategy<A, C> crossOverStrategy;
    private MutationStrategy<A, C> mutationStrategy;
    private ArrayList<Predicate<EvolutionState<A, C>>> terminationCriteria;

    private EvolutionState<A, C> state;
    
    public GAEngine(Genotype<A, C> genotypeFactory, FitnessEvaluator<A, C> fitnessEvaluator) {

        this.genotypeFactory = genotypeFactory;     // TODO: require non-null
        this.fitnessEvaluator = fitnessEvaluator;   // TODO : require non-null

        this.size_of_population = DEFAULT_POPULATION_SIZE;
        this.crossover_rate = DEFAULT_CROSSOVER_RATE;
        this.mutation_rate = DEFAULT_MUTATION_RATE;
        this.fitness_evaluation_threshold = DEFAULT_FITNESS_EVALUATION_THRESHOLD;

        this.selectionStrategy = new TournamentSelector<>(); // default selectionStrategy is tournament
        this.crossOverStrategy = null;
        this.mutationStrategy = null;
        this.terminationCriteria = new ArrayList<>();

        // add the default threshold limit criterium for termination
        this.terminationCriteria.add((st) -> { 
            return st.getNumberOfGenerations() >= this.fitness_evaluation_threshold ; });

        this.state = new EvolutionState<>();
    }

    public GAEngine<A,C> populationSize(int popSize) {
        this.size_of_population = popSize;
        return this;
    }

    public GAEngine<A,C> crossOverRate(double crossover_rate) {
        this.crossover_rate = crossover_rate;
        return this;
    }

    public GAEngine<A,C> mutationRate(double mutation_rate) {
        this.mutation_rate = mutation_rate;
        return this;
    }

    public GAEngine<A,C> fitnessEvaluationThreshold(int fitness_evaluation_threshold) {
        this.fitness_evaluation_threshold = fitness_evaluation_threshold;
        return this;
    }

    public GAEngine<A, C> selectionStrategy(SelectionStrategy<A, C> selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
        return this;
    }

    public GAEngine<A, C> crossOverStrategy(CrossOverStrategy<A, C> crossOverStrategy) {
        this.crossOverStrategy = crossOverStrategy;
        return this;
    }

    public GAEngine<A, C> mutationStrategy(MutationStrategy<A, C> mutationStrategy) {
        this.mutationStrategy = mutationStrategy;
        return this;
    }

    public GAEngine<A, C> terminateIf(Predicate<EvolutionState<A, C>> criterium) {
        this.terminationCriteria.add(criterium);
        return this;
    }


    /**
     * Starts the algorithm
     * Terminates if the desired solution is found or if fitness function evaluation exhaustion limit is reached
     * @return Result of the run; If solution was found, solution individual is returned else the best fit solution seen until then is returned
     */
    public EvolutionState<A, C> run() {

        // GENERATION GA

        this.state.setPopulation(initializePopulation());

        while (true) {

            // check if we are ready to terminate
            for(Predicate<EvolutionState<A, C>> criterium : this.terminationCriteria) {
                if(criterium.test(this.state)) {
                    return this.state;
                }
            }

            for (Genotype<A, C> gt : this.state.getPopulation()) {
                evaluateAndSetFitness(gt);
            }

            // create new generation
            this.state.setPopulation(createNewGeneration());
        }
    }

    private ArrayList<Genotype<A, C>> createNewGeneration() {
        
        ArrayList<Genotype<A, C>> newPopulation = new ArrayList<>();

        while(newPopulation.size() < this.size_of_population) {

            // selection [for now let us only support 2 parents model]
            ArrayList<Genotype<A, C>> parents = selectParents(2);

            // VARIANCE OPERATIONS
            
            ArrayList<Genotype<A, C>> offsprings = parents; // if crossover or mutation is bypassed, just slide in the parents into the next generation
            
            // crossover
            if(this.crossOverStrategy != null) {
                if(RandomNumberGenerator.eventOccurred(crossover_rate)) {
                    offsprings = crossOver(parents);
                }
            }

            // mutation
            if(this.mutationStrategy != null) {
                // flip a coin twice for each offspring
                for(Genotype<A, C> gt : offsprings) {
                    if(RandomNumberGenerator.eventOccurred(mutation_rate)) {
                        gt = mutate(gt);
                    }
                }
            }
            
            // add to new population
            newPopulation.addAll(offsprings);
        }

        return newPopulation;
    }

    
    private ArrayList<Genotype<A, C>> initializePopulation() {

        ArrayList<Genotype<A, C>> population = new ArrayList<>();

        for(int i=0; i < size_of_population; i++) {
            population.add(this.genotypeFactory.newInstance());
        }

        return population;
    }

    private Genotype<A, C> evaluateAndSetFitness(Genotype<A, C> gt) {
        gt.setFitness(this.fitnessEvaluator.evaluate(gt));

        return gt;
    }


    private ArrayList<Genotype<A, C>> selectParents(int numberOfParentsToSelect) {

        ArrayList<Genotype<A, C>> parents = new ArrayList<>();

        for(int i=0; i < numberOfParentsToSelect; i++) {
            parents.add(selectionStrategy.select(this.state.getPopulation()));
        }

        return parents;
    }

    private ArrayList<Genotype<A, C>> crossOver( ArrayList<Genotype<A, C>> parents) {
        return crossOverStrategy.crossOver(parents);
    }

    
    private Genotype<A, C> mutate(Genotype<A, C> offspring) {
        return mutationStrategy.mutate(offspring);
    }

}