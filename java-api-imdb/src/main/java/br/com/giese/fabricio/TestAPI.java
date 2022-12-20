package br.com.giese.fabricio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class TestAPI {

	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		
		Properties apiKey = new Properties();
		String filePath = "./src/main/resources/config.properties";
		apiKey.load(new FileInputStream(filePath));
		
		String key = "/" + apiKey.getProperty("apiKey");
		String uriSearch = "https://imdb-api.com/en/API/Top250Movies";
		String uriCompleta = uriSearch + key;
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI(uriCompleta)).build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		Map<Integer, Map<String, String>> jsonParseado = parseJson(response);
		
		for(int i = 0; i < jsonParseado.size(); i++) {
			Map<String,String> mapTemp = jsonParseado.get(i);
			for (Entry<String, String> es : mapTemp.entrySet()) {
				System.out.println(es.getKey() + " : " + es.getValue());
			}
			System.out.println("-----------------------");
			System.lineSeparator();
		}
	}

	private static Map<Integer, Map<String, String>> parseJson(HttpResponse<String> response) {
		String[] json = response.body()
				.replace("[", "")
				.replace("]", "")
				.replace("{", "")
				.replace("},", "/@/")
				.replace("\"items\":", "")
				.replace("\"errorMessage\":\"\"}", "")
				.replace("\",\"", "/@@/")
				.split("/@/");
		
		Map<Integer, Map<String, String>> jsonParseado = new HashMap<>();
		
		for (int i = 0; i < json.length; i++) {
			final int index = i;
			jsonParseado.put(i, new HashMap<>());
			String[] keyValueTogether = json[index].substring(1, json[index].length() - 1) .split("/@@/");
			List<String[]> keyValue = new ArrayList<>();
			Arrays.asList(keyValueTogether).forEach(s -> keyValue.add(s.split("\":\"")));
			keyValue.forEach(kv -> jsonParseado.get(index).put(kv[0], kv[1]));
		}
		return jsonParseado;
	}

}
