package DesdobramentoInteger;

import jenes.chromosome.Chromosome;
import jenes.population.Individual;
import jenes.population.Population;
import jenes.stage.ExclusiveDispenser;

/**
 * Tutorial showing how to extend <code>GeneticAlgorithm</code> and how to use
 * the flexible and configurable breeding structure in Jenes.
 * The problem consists in searching a pattern of integers with a given precision.
 * Solutions flow through two different crossovers in parallel. Some are processed by
 * a single point crossover, the other by a double point crossover.
 * After solutions are mutated.
 *
 * This class implements the strategy for dispensing solutions in the two branches.
 * Odd solutions goes to the first, even to the second.
 *
 * @author Luigi Troiano
 *
 * @version 2.0
 *
 * @since 1.0
 */
public class SimpleDispenser<T extends Chromosome> extends ExclusiveDispenser<T> {
    
    private int count;
    
    public SimpleDispenser(int span) {
        super(span);
    }
    
    public void preDistribute(Population<T> population){
        this.count = 0;
    }
    
    @Override
    public int distribute(Individual<T> ind) {
        return count++ % span;
    }
    
}