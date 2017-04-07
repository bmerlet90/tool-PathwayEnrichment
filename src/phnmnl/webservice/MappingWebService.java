package phnmnl.webservice;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class MappingWebService {
	
	private ClientConfig config;
	private Client client;
	private WebResource Webservice;
	private String url;
	
	/**
	 * 
	 */
	public MappingWebService(){
		this.url="http://vm-metexplore.toulouse.inra.fr/metExploreWebService/";
		
		config = new DefaultClientConfig();
		client = Client.create(this.getConfig());
		
		URI uri=this.getBaseURI();
		Webservice = this.getClient().resource(uri);
	}	
	
	public MappingWebService(String host){
		this.url="http://"+host+":8080/metExploreWebService/";
		
		config = new DefaultClientConfig();
		client = Client.create(this.getConfig());
		
		URI uri=this.getBaseURI();
		Webservice = this.getClient().resource(uri);
	}	
	
	public MappingWebService(String host, String version){
		this.url="http://"+host+":8080/"+version+"/";
		
		config = new DefaultClientConfig();
		client = Client.create(this.getConfig());
		
		URI uri=this.getBaseURI();
		Webservice = this.getClient().resource(uri);
	}
	
	/**
	 * 
	 * @param attribute
	 * @return
	 */
	public JsonObject mapReactions(String attribute, JsonObject input){
		JsonObject output=new JsonObject();
		
		if (this.testConnection()){
			
			this.Webservice=this.Webservice.path("mapping").path("launch").path("reactions").path(attribute).path("4324");
			
			return this.mapInputData(input);
			
		}else{
			output.addProperty("success", false);
			output.addProperty("message", "Unable to connect to the MetExplore web service. Try again later or contact us at 'contact-metexplore@inra.fr'");
		}
		
		return output;
	}
	
	/**
	 * 
	 * @param attribute
	 * @return
	 */
	public JsonObject mapMetabolites(String attribute, JsonObject input){
		JsonObject output=new JsonObject();
		
		if (this.testConnection()){
						
			this.Webservice=this.Webservice.path("mapping").path("launch").path("metabolites").path(attribute).path("4324");
			
			return this.mapInputData(input);
			
		}else{
			output.addProperty("success", false);
			output.addProperty("message", "Unable to connect to the MetExplore web service. Try again later or contact us at 'contact-metexplore@inra.fr'");
		}
		
		return output;
	}
	
	
	private JsonObject mapInputData(JsonObject in){
		ClientResponse response = this.getWebservice().type("application/json").post(ClientResponse.class, in.toString());
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		return (JsonObject)new JsonParser().parse(response.getEntity(String.class));
	}
	
	
	
	/**
	 * Used internally to test the connection to the webservice
	 * @return
	 */
	private boolean testConnection(){
		WebResource data = this.getWebservice().path("biosources").path("1363");
		
		ClientResponse response = data.type("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			return false;
		}
		return true;
	}
	
	private URI getBaseURI() {
		return UriBuilder.fromUri(this.url).build();
	}
	
	public WebResource getWebservice() {
		return Webservice;
	}

	public String getUrl() {
		return url;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setWebservice(WebResource webservice) {
		Webservice = webservice;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Client getClient() {
		return client;
	}

	public ClientConfig getConfig() {
		return config;
	}
}
