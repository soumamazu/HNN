package geneticLearner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import utilities.GetProperties;
import utilities.Utils;

public class GeneticAlgo {
	private ArrayList <SingleGenome> vecPop;
	private int popSize;
	private int chromoLength;
	private double totalFitness;
	private double bestFitness;
	private double averageFitness;
	private double worstFitness;
	private int fittestGenome;
	private double mutationRate;
	private double crossoverRate;
	private int generation;
	
	private void crossover(ArrayList <Double> mom,ArrayList <Double> dad,ArrayList <Double> baby1,ArrayList <Double> baby2) {
		if((Math.random()>this.crossoverRate) || mom.equals(dad)) {
			baby1=mom;
			baby2=dad;
			return;
		}
		int cp=(int)(Math.random()*this.chromoLength);
		for(int i=0;i<cp;i++) {
			baby1.add(mom.get(i));
			baby2.add(dad.get(i));
		}
		for(int i=cp;i<mom.size();i++) {
			baby1.add(mom.get(i));
			baby2.add(dad.get(i));
		}
	}
	
	private void mutate(ArrayList <Double> chromo) throws NumberFormatException, IOException {
		for(int i=0;i<chromo.size();i++)
			if(Math.random()<this.mutationRate)
				chromo.set(i,chromo.get(i)+(Utils.randomClamped()*Double.parseDouble(GetProperties.getProp("MaxPerturbation"))));
	}
	
	private SingleGenome getChromoRoulette() {
		double slice=Math.random()*this.totalFitness;
		SingleGenome theChosenOne=null;
		double fitnessSoFar=0;
		for(int i=0;i<this.popSize;i++) {
			fitnessSoFar+=this.vecPop.get(i).fitness;
			if(fitnessSoFar>=slice) {
				theChosenOne=this.vecPop.get(i);
				break;
			}
		}
		return theChosenOne;
	}
	
	private void grabNBest(int NBest,int numCopies,ArrayList <SingleGenome> vecpop) {
		while(NBest-->0)
			for(int i=0;i<numCopies;i++)
				vecpop.add(this.vecPop.get(this.popSize-1-NBest));
	}
	
	private void calculateBestWorstAvgTotal() {
		this.totalFitness=0;
		double highestSoFar=0;
		double lowestSoFar=Double.MAX_VALUE;
		for(int i=0;i<this.popSize;i++) {
			if(this.vecPop.get(i).fitness>highestSoFar) {
				highestSoFar=this.vecPop.get(i).fitness;
				this.fittestGenome=i;
				this.bestFitness=highestSoFar;
			}
			if(this.vecPop.get(i).fitness<lowestSoFar) {
				lowestSoFar=this.vecPop.get(i).fitness;
				this.worstFitness=this.vecPop.get(i).fitness;
			}
			this.totalFitness+=this.vecPop.get(i).fitness;
		}
		this.averageFitness=this.totalFitness/this.popSize;
	}
	
	private void reset() {
		this.totalFitness=this.averageFitness=this.bestFitness=0;
		this.worstFitness=Integer.MAX_VALUE;
	}
	
	public GeneticAlgo(int popSize,double mutRate,double crossRate,int numWeights) {
		this.popSize=popSize;
		this.mutationRate=mutRate;
		this.crossoverRate=crossRate;
		this.chromoLength=numWeights;
		this.totalFitness=this.generation=this.fittestGenome=0;
		this.bestFitness=this.averageFitness=0;
		this.worstFitness=Integer.MAX_VALUE;
		
		for(int i=0;i<this.popSize;i++) {
			this.vecPop.add(new SingleGenome());
			for(int j=0;j<this.chromoLength;j++)
				this.vecPop.get(i).vecWeights.add(Utils.randomClamped());
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList <SingleGenome> epoch(ArrayList <SingleGenome> oldPop) throws NumberFormatException, IOException {
		this.vecPop=oldPop;
		this.reset();
		Collections.sort(this.vecPop);
		this.calculateBestWorstAvgTotal();
		ArrayList <SingleGenome> newPop=new ArrayList <> ();
		if((Integer.parseInt(GetProperties.getProp("NumCopiesElite"))*Integer.parseInt(GetProperties.getProp("NumElite"))%2)==0)
			this.grabNBest(Integer.parseInt(GetProperties.getProp("NumElite")),
					Integer.parseInt(GetProperties.getProp("NumCopiesElite")),newPop);
		while(newPop.size()<this.popSize) {
			SingleGenome mom=this.getChromoRoulette();
			SingleGenome dad=this.getChromoRoulette();
			ArrayList <Double> baby1,baby2;
			baby1=baby2=new ArrayList <> ();
			this.crossover(mom.vecWeights,dad.vecWeights,baby1,baby2);
			this.mutate(baby1);
			this.mutate(baby2);
			newPop.add(new SingleGenome(baby1,0));
			newPop.add(new SingleGenome(baby2,0));
		}
		this.vecPop=newPop;
		return this.vecPop;
	}
	
	public ArrayList <SingleGenome> getChromos() {
		return this.vecPop;
	}
	
	public double averageFitness() {
		return this.totalFitness/this.popSize;
	}
	
	public double bestFitness() {
		return this.bestFitness;
	}
}
