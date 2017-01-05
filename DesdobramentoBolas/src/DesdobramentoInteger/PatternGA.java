package DesdobramentoInteger;


import jenes.GeneticAlgorithm;
import jenes.chromosome.IntegerChromosome;
import jenes.population.Fitness;
import jenes.population.Individual;
import jenes.population.Population;

/**
 * Tutorial showing how to extend <code>GeneticAlgorithm</code> and how to use
 * the flexible and configurable breeding structure in Jenes.
 * The problem consists in searching a pattern of integers with a given precision.
 * Solutions flow through two different crossovers in parallel. Some are processed by
 * a single point crossover, the other by a double point crossover.
 * After solutions are mutated.
 *
 * This class implements the algorithm by extending <code>GeneticAlgorithm</code>.
 *
 * @author Luigi Troiano
 *
 * @version 2.0
 * 
 * @since 1.0
 */
public class PatternGA extends GeneticAlgorithm<IntegerChromosome> {

    private DesdobramentoFitness fitness = new DesdobramentoFitness();

    public PatternGA(Population<IntegerChromosome> pop, int numGen) {
        super(pop, numGen);
        this.setFitness(fitness);
    }
    
    @Override
    protected boolean end() {
        jenes.population.Population.Statistics stat = this.getCurrentPopulation().getStatistics();
        return this.getStatistics().getGenerations() >= 10;//stat.getGroup(Population.LEGALS).getMin()[0] <= this.fitness.precision;
    }
    
    /*public class PatternFitness extends Fitness<IntegerChromosome> {

        private int[] target = null;
        private int precision = 0;
        
        private PatternFitness() {
            super(false);
        }
    
        @Override
        public void evaluate(Individual<IntegerChromosome> individual) {
            IntegerChromosome chrom = individual.getChromosome();
            int diff = 0;
            int length = chrom.length();
            for (int i = 0; i < length; i++) {
                diff += Math.abs(chrom.getValue(i) - target[i]);
            }
            individual.setScore(diff);
        }
        
        public void setTarget(int[] target) {
            this.target = target;
        }
        
        public void setPrecision(int precision) {
            this.precision = precision;
        }
    }*/
}