package neuralNetwork;

import java.util.ArrayList;
import utilities.Utils;

public class SingleNeuron {
	public int numInputs;
	public ArrayList <Double> vecWeights;
	
	public SingleNeuron(int numInputs) {
		this.numInputs=numInputs+1;
		this.vecWeights=new ArrayList <> ();
		for(int i=0;i<numInputs+1;i++)
			vecWeights.add(Utils.randomClamped());
	}
}
