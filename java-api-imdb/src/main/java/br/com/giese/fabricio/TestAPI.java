package br.com.giese.fabricio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestAPI {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Properties apiKey = new Properties();
		String filePath = "./src/main/resources/config.properties";
		apiKey.load(new FileInputStream(filePath));
		
		String key = apiKey.getProperty("apiKey");
		
		System.out.println(key);
		

	}

}
