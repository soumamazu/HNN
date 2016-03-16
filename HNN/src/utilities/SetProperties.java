package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SetProperties {
	public static void main(String[] args) throws IOException {
		Properties prop=new Properties();
		OutputStream output=new FileOutputStream("config.properties");
		prop.setProperty("NumInputs","4");
		prop.setProperty("NumHidden","1");
		prop.setProperty("NeuronsPerHiddenLayer","6");
		prop.setProperty("NumOutputs","2");
		prop.setProperty("ActivationResponse","1");
		prop.setProperty("Bias","-1");
		prop.setProperty("CrossoverRate","0.7");
		prop.setProperty("MutationRate","0.1");
		prop.setProperty("MaxPerturbation","0.3");
		prop.setProperty("NumElite","4");
		prop.setProperty("NumCopiesElite","1");
		prop.store(output,null);
		output.close();
	}	
}
