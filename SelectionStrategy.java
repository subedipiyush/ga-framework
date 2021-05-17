package framework;

import java.util.ArrayList;

/**
 * Implement this interface to provide a selection method for the framework
 * Java doesn't support (atleast it is not straightforward) passing function as
 * parameter hence will have to use this SAM(Single Abstract Method) technique
 * 
 * @param A allele type (ex. integer, double, bit, etc)
 * @param C fitness value type (ex. integer, double, etc)
 */
@FunctionalInterface
public interface SelectionStrategy<A, C extends Comparable<C>> {
    Genotype<A, C> select(final ArrayList<Genotype<A, C>> population);
}