package app.mailing.metiers;
import java.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TaskAlfrescoServiceImpl implements TaskAlfrescoService {

	@Override
	public void getTasks() {
		System.out.println("LE TESTE DES TACHES");
		String uri = "https://api-explorer.alfresco.com/alfresco/api/-default-/public/workflow/versions/1//tasks";  
		 HttpEntity<String> request = new HttpEntity(setHeader("admin", "admin"));
	    ResponseEntity<String> response = new RestTemplate().exchange(uri, HttpMethod.GET, request, String.class);
		System.out.println("result - " + response.getBody());
		
	}
	
	
	public HttpHeaders setHeader(String login, String pwd) {
		// create auth credentials
	    String authStr = "admin:admin";
	    String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

	    // create headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Basic " + base64Creds);

	    return headers;
	}

}
