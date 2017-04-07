package phnmnl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import phnmnl.webservice.MappingWebService;

public class PathwayEnrichment {

	public static void main(String[] args) {
		
		JsonObject json=null;

		try {

			InputStream inputStream       = new FileInputStream("mapping.json");
			Reader      inputStreamReader = new InputStreamReader(inputStream);

			json=(JsonObject)new JsonParser().parse(inputStreamReader);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		MappingWebService mapper=new MappingWebService("vm-metexplore.toulouse.inra.fr","metExploreWebService-dev");
		
		JsonObject out = mapper.mapReactions("dbIdentifier", json);
		System.out.println(out.toString());
	}

}
