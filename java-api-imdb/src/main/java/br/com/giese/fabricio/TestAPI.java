package br.com.giese.fabricio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class TestAPI {

	public static void main(String[] args) throws FileNotFoundException, IOException, URISyntaxException, InterruptedException {
		
		Properties apiKey = new Properties();
		String filePath = "./src/main/resources/config.properties";
		apiKey.load(new FileInputStream(filePath));
		
		String search = "/SpiderMan";
		String key = "/" + apiKey.getProperty("apiKey");
		String uriSearch = "https://imdb-api.com/en/API/Search";
		String uriCompleta = uriSearch + key + search;
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI(uriCompleta)).build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(JsonParser.parseString(response.body())));
		
	}

}
