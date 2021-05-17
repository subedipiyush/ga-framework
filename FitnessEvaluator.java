package framework;

/**
 * Implement this interface to provide a fitness evaluation method for the framework
 * Java doesn't support (atleast it is not straightforward) passing function as parameter
 * hence will have to use this SAM(Single Abstract Method) technique
 * 
 * @param A allele type (ex. integer, double, bit, etc)
 * @param C fitness value type (ex. integer, double, etc)
 */
@FunctionalInterface
public interface FitnessEvaluator<A, C extends Comparable<C>> {
    C evaluate(final Genotype<A, C> ind);
}