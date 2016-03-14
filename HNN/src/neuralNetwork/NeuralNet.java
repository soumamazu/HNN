package neuralNetwork;

import java.io.IOException;
import java.util.ArrayList;

import utilities.GetProperties;

public class NeuralNet {
	private int numInputs;
	private int numOutputs;
	private int numHiddenLayers;
	private int numNeuronsPerHiddenLayer;
	private ArrayList <NeuronLayer> vecLayers;
	
	public NeuralNet() throws NumberFormatException, IOException {
		this.numInputs=Integer.parseInt(GetProperties.getProp("NumInputs"));
		this.numOutputs=Integer.parseInt(GetProperties.getProp("NumOutputs"));
		this.numHiddenLayers=Integer.parseInt(GetProperties.getProp("NumHidden"));
		this.numNeuronsPerHiddenLayer=Integer.parseInt(GetProperties.getProp("NeuronsPerHiddenLayer"));
		this.vecLayers=new ArrayList <> ();
	}
	
	public void createNet() {
		if(this.numHiddenLayers>0) {
			this.vecLayers.add(new NeuronLayer(this.numNeuronsPerHiddenLayer,this.numInputs));
			for(int i=0;i<this.numHiddenLayers-1;i++)
				this.vecLayers.add(new NeuronLayer(this.numNeuronsPerHiddenLayer,this.numNeuronsPerHiddenLayer));
			this.vecLayers.add(new NeuronLayer(this.numOutputs,this.numNeuronsPerHiddenLayer));
		} else
			this.vecLayers.add(new NeuronLayer(this.numOutputs,this.numInputs));
	}
	
	public ArrayList <Double> getWeights() {
		ArrayList <Double> weights=new ArrayList <> ();
		for(int i=0;i<this.numHiddenLayers+1;i++)
			for(int j=0;j<this.vecLayers.get(i).numNeurons;j++)
				for(int k=0;k<this.vecLayers.get(i).vecNeurons.get(j).numInputs;k++)
					weights.add(this.vecLayers.get(i).vecNeurons.get(j).vecWeights.get(k));
		return weights;
	}
	
	public int getNumberWeights() {
		int weight=0;
		for(int i=0;i<this.numHiddenLayers+1;i++)
			for(int j=0;j<this.vecLayers.get(i).numNeurons;j++)
				for(int k=0;k<this.vecLayers.get(i).vecNeurons.get(j).numInputs;k++)
					weight++;
		return weight;
	}
	
	public void putWeights(ArrayList <Double> weights) {
		int weight=0;
		for(int i=0;i<this.numHiddenLayers+1;i++)
			for(int j=0;j<this.vecLayers.get(i).numNeurons;j++)
				for(int k=0;k<this.vecLayers.get(i).vecNeurons.get(j).numInputs;k++)
					this.vecLayers.get(i).vecNeurons.get(j).vecWeights.set(k,weights.get(weight++));
	}
	
	public ArrayList <Double> update(ArrayList <Double> inputs) throws NumberFormatException, IOException {
		ArrayList <Double> outputs=new ArrayList <> ();
		int weight=0;
		if(inputs.size()!=this.numInputs)
			return outputs;
		for(int i=0;i<this.numHiddenLayers+1;i++) {
			if(i>0)
				inputs=outputs;
			outputs.clear();
			weight=0;
			for(int j=0;j<this.vecLayers.get(i).numNeurons;j++) {
				double netInput=0;
				int numInputs=this.vecLayers.get(i).vecNeurons.get(j).numInputs;
				for(int k=0;k<numInputs-1;k++)
					netInput+=this.vecLayers.get(i).vecNeurons.get(j).vecWeights.get(k)*inputs.get(weight++);
				netInput+=Double.parseDouble(GetProperties.getProp("Bias"));
				outputs.add(sigmoid(netInput,Double.parseDouble(GetProperties.getProp("ActivationResponse"))));
				weight=0;
			}
		}
		return outputs;
	}
	
	public double sigmoid(double activation,double response) {
		return (1/(1+Math.exp(-activation/response)));
	}
}
