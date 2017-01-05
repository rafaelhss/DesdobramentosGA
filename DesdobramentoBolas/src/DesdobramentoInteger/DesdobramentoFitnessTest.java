package DesdobramentoInteger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import jenes.chromosome.IntegerChromosome;
import jenes.population.Individual;

public class DesdobramentoFitnessTest {

	private static final int MIN_BOLAS_DIFERENTES = 7;
	private static final int MAX_BOLAS_DIFERENTES = 15;
	private static final int BolasUnicas = 4457400;
	public static final int num_jogos = 3268760; 
	
	
    private static int POPULATION_SIZE = 100;
    private static int CHROMOSOME_LENGTH = 6;
    private static int GENERATION_LIMIT = 1000;
    private static int MAX_INT = 6;
	
	public static void main(String[] args) {
		
		HashMap<Integer, Integer[]> base = geraMapaExemplo();
		printMap(base);
		
		
		DesdobramentoFitness desdobramentoFitness = new DesdobramentoFitness();
		
		IntegerChromosome chrom = new IntegerChromosome(CHROMOSOME_LENGTH,-1,MAX_INT);
		chrom.setValue(0, 0);
		chrom.setValue(1, -1);
		chrom.setValue(2, -1);
		chrom.setValue(3, 3);
		chrom.setValue(4, -1);
		chrom.setValue(5, -1);
        
		
		Individual<IntegerChromosome> ind = new Individual<IntegerChromosome>(chrom);
        
		
		desdobramentoFitness.setBase(base);
		
		
		desdobramentoFitness.evaluate(ind);
		
		
		System.out.println("Fitness: " + ind.getScore());
		
		
		
		
		
		

	}
	private static void randomize(int[] sample) {
        for(int i=0;i<sample.length;i++)
            sample[i] = i%4; //Random.getInstance().nextInt(0, MAX_INT+1);
    }
	

	private static HashMap<Integer, Integer[]> geraMapaExemplo() {
		HashMap<Integer, Integer[]> base =  new HashMap<>();
		
		Integer[] bolas0 = {0,1,2};
		base.put(0, bolas0);
		
		Integer[] bolas1 = {0,2,3};
		base.put(1, bolas1);
		
		Integer[] bolas2 = {0,3,4};
		base.put(2, bolas2);
		
		Integer[] bolas3 = {4,5,6};
		base.put(3, bolas3);
		
		Integer[] bolas4 = {6,7,8};
		base.put(4, bolas4);
		
		Integer[] bolas5 = {2,3,8};
		base.put(5, bolas5);
		
		
		
		return base;
	}



	public static HashMap<Integer, Integer[]> geraMapaRandom() {
		System.out.println("gerando...");
		HashMap<Integer, Integer[]> base =  new HashMap<>();
		
		for (int i = 0; i < num_jogos ; i++) {
			int bolasJogo = ThreadLocalRandom.current().nextInt(MIN_BOLAS_DIFERENTES, MAX_BOLAS_DIFERENTES);
			Integer[] bolas = new Integer[bolasJogo];
			for (int j = 0; j < bolasJogo; j++) {
				bolas[j] = ThreadLocalRandom.current().nextInt(0, BolasUnicas);
			}
			base.put(i, bolas);
		}
		System.out.println("fim gerando...");
		return base;
	}

	private static void printMap(HashMap<Integer, Integer[]> mp) {
		 Iterator it = mp.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.print(pair.getKey() + " = ");
		        Integer[] bolas = (Integer[]) pair.getValue();
		        for (Integer integer : bolas) {
					System.out.print(" " + integer + " ");
				}
		        System.out.println("");
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		
	}

}
