package neuralNetwork;

import java.util.ArrayList;

public class NeuronLayer {
	public int numNeurons;
	public ArrayList <SingleNeuron> vecNeurons;
	
	public NeuronLayer(int numNeurons,int numInputsPerNeuron) {
		this.numNeurons=numNeurons;
		this.vecNeurons=new ArrayList <> ();
		for(int i=0;i<numNeurons;i++)
			vecNeurons.add(new SingleNeuron(numInputsPerNeuron));
	}
}
