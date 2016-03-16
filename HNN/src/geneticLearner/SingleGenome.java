package geneticLearner;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class SingleGenome implements Comparable {
	public ArrayList <Double> vecWeights;
	public double fitness;
	
	public SingleGenome() {
		this.fitness=0;
		this.vecWeights=new ArrayList <> ();
	}
	
	public SingleGenome(ArrayList <Double> weights,double fitness) {
		this.vecWeights=weights;
		this.fitness=fitness;
	}
	
	@Override
	public int compareTo(Object other) {
		return (this.fitness>((SingleGenome)other).fitness)?1:(this.fitness<((SingleGenome)other).fitness)?-1:0;
	}
}
