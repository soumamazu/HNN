package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {
	public static String getProp(String key) throws IOException {
		Properties prop=new Properties();
		InputStream input=new FileInputStream("config.properties");
		prop.load(input);
		String value=prop.getProperty(key);
		input.close();
		return value;
	}
}
