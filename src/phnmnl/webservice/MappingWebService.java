package phnmnl.webservice;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class MappingWebService {
	
	protected ClientConfig config;
	protected Client client;
	protected WebResource Webservice;
	private String url;
	
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
	
	public JsonObject mapReactions(){
		JsonObject output=new JsonObject();
		
		if (this.testConnection()){
			output.addProperty("success", true);
		}else{
			output.addProperty("success", true);
			output.addProperty("message", "Unable to connect to the MetExplore web service. Try again later or contact us at 'contact-metexplore@inra.fr'");
		}
		
		return output;
	}
	
	public JsonObject mapMetabolites(){
		JsonObject output=new JsonObject();
		
		if (this.testConnection()){
			output.addProperty("success", true);
		}else{
			output.addProperty("success", true);
			output.addProperty("message", "Unable to connect to the MetExplore web service. Try again later or contact us at 'contact-metexplore@inra.fr'");
		}
		
		return output;
	}
	
	private boolean testConnection(){
		WebResource data = this.getWebservice().path("biosources").path("1363");
		
		ClientResponse response = data.type("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			return false;
		}
		return true;
	}
	
	protected URI getBaseURI() {
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
