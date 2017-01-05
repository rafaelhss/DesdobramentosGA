package DesdobramentoInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import jenes.chromosome.IntegerChromosome;
import jenes.population.Fitness;
import jenes.population.Individual;

public class DesdobramentoFitness extends Fitness<IntegerChromosome> {

	
	
	HashMap<Integer, Integer[]> base = null;
    private int precision = 0;
    
    DesdobramentoFitness() {
        super(false);
    }

    HashSet<Integer> todasBolasSelecionadas = new HashSet<>();
    
    @Override
    public void evaluate(Individual<IntegerChromosome> individual) {
    	todasBolasSelecionadas.clear();
    	
        IntegerChromosome chrom = individual.getChromosome();
        int jogos = 0;
        int length = chrom.length();
        for (int i = 0; i < length; i++) {
        	if(base.get((Integer)chrom.getValue(i)) != null) {
        		jogos++;
            	todasBolasSelecionadas.addAll(Arrays.asList(base.get((Integer)chrom.getValue(i)))); //TODO remove duplicates
            }
        }	
        	
        	
    	//verificar em cada jogo
	    Iterator it = base.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Integer[] bolas = (Integer[]) pair.getValue();
	        
	        //se alguma de suas bolas
	        boolean achoubola = false;
	        achabolas:
	        for (Integer bola : bolas) {
				//coincide com alguma bola selecionada
	        	for (Integer bolasel : todasBolasSelecionadas) {
					if(bola == bolasel){
						achoubola = true;
						break achabolas; //entao satisfaz a condicao. 
					}
				}
			}
	        if(!achoubola){ //solucao invalida
	        	individual.setScore(Integer.MAX_VALUE);
	        	return;
	        }
	        System.out.println("");
	    }
        
        individual.setScore(jogos);
    }
    

    
    public void setBase(HashMap<Integer, Integer[]> base) {
		this.base = base;
	}

	public void setPrecision(int precision) {
        this.precision = precision;
    }
	

	
}