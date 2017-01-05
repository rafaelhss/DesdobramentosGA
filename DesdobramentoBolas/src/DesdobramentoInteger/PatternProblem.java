package DesdobramentoInteger;

import jenes.GenerationEventListener;
import jenes.GeneticAlgorithm;
import jenes.utils.Random;
import jenes.chromosome.IntegerChromosome;
import jenes.population.Individual;
import jenes.population.Population;
import jenes.population.Population.Statistics;
import jenes.population.Population.Statistics.Group;
import jenes.stage.AbstractStage;
import jenes.stage.Parallel;
import jenes.stage.operator.common.OnePointCrossover;
import jenes.stage.operator.common.SimpleMutator;
import jenes.stage.operator.common.TournamentSelector;
import jenes.stage.operator.common.TwoPointsCrossover;
import jenes.tutorials.utils.Utils;


/**
 * Tutorial showing how to extend <code>GeneticAlgorithm</code> and how to use
 * the flexible and configurable breeding structure in Jenes.
 * The problem consists in searching a pattern of integers with a given precision.
 * Solutions flow through two different crossovers in parallel. Some are processed by
 * a single point crossover, the other by a double point crossover.
 * After solutions are mutated.
 *
 * This is the main class that specifies the problem.
 *
 * @author Luigi Troiano
 *
 * @version 2.0
 *
 * @since 1.0
 */
public class PatternProblem implements GenerationEventListener<IntegerChromosome> {
    
    private static int POPULATION_SIZE = 100;
    private static int CHROMOSOME_LENGTH = 60000;
    private static int GENERATION_LIMIT = 1000;
    public static final int num_jogos = 3268760; 
    
    private PatternGA algorithm = null;
    
    public PatternProblem() {
        
        IntegerChromosome chrom = new IntegerChromosome(CHROMOSOME_LENGTH,-1,num_jogos);
        Individual<IntegerChromosome> ind = new Individual<IntegerChromosome>(chrom);
        Population<IntegerChromosome> pop = new Population<IntegerChromosome>(ind, POPULATION_SIZE);
        
        algorithm = new PatternGA(pop, GENERATION_LIMIT);
        algorithm.setElitism(5);
        
        AbstractStage<IntegerChromosome> selection = new TournamentSelector<IntegerChromosome>(2);
        
        Parallel<IntegerChromosome> parallel = new Parallel<IntegerChromosome>(new SimpleDispenser<IntegerChromosome>(2));
        
        AbstractStage<IntegerChromosome> crossover1p = new OnePointCrossover<IntegerChromosome>(0.8);
        parallel.add(crossover1p);
        
        AbstractStage<IntegerChromosome> crossover2p = new TwoPointsCrossover<IntegerChromosome>(0.5);
        parallel.add(crossover2p);
        
        AbstractStage<IntegerChromosome> mutation = new SimpleMutator<IntegerChromosome>(0.02);
        
        algorithm.addStage(selection);
        algorithm.addStage(parallel);
        algorithm.addStage(mutation);
        algorithm.addGenerationEventListener(this);
    }
    
    public void run(int[] target, int precision) {
        ((DesdobramentoFitness) algorithm.getFitness()).setBase(DesdobramentoFitnessTest.geraMapaRandom());
        ((DesdobramentoFitness) algorithm.getFitness()).setPrecision(precision);
        algorithm.evolve();
        
        Population.Statistics stats = algorithm.getCurrentPopulation().getStatistics();
        GeneticAlgorithm.Statistics algostats = algorithm.getStatistics();
        
        System.out.println();
        System.out.print("Target:[");
        for( int i = 0; i < target.length; ++i ) {
            System.out.print(target[i]+ ( i < target.length - 1 ? " " : ""));
        }
        System.out.println("]");
        System.out.println();
        
        System.out.println("Solution: ");
        System.out.println(stats.getGroup(Population.LEGALS).get(0));
        System.out.format("found in %d ms and %d generations.\n", algostats.getExecutionTime(), algostats.getGenerations() );
        System.out.println();
    }
    
    
    public void onGeneration(GeneticAlgorithm ga, long time) {
        Statistics stat = ga.getCurrentPopulation().getStatistics();
        Group legals = stat.getGroup(Population.LEGALS);
        System.out.println("Current generation: " + ga.getGeneration());
        System.out.println("\tBest score: " + legals.getMin()[0]);
        System.out.println("\tAvg score : " + legals.getMean()[0]);
    }
    
    private static void randomize(int[] sample) {
        for(int i=0;i<sample.length;i++)
            sample[i] = i%4; //Random.getInstance().nextInt(0, MAX_INT+1);
    }
    
    public static void main(String[] args) {
        
        Utils.printHeader();
        System.out.println();
        
        System.out.println("TUTORIAL 2:");
        System.out.println("This algorithm aims to autonomously find a vector of integers that best matches with a target vector.");
        System.out.println();
        
        Random.getInstance().setStandardSeed();
        
        PatternProblem problem = new PatternProblem();
        int[] target = new int[CHROMOSOME_LENGTH];
        
        randomize(target);
        problem.run(target, 2);
        
      /*  randomize(target);
        problem.run(target, 0);*/
        
    }
    
}