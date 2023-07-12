package app.mailing.metiers;
import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaTypeEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import app.mailing.entities.groupe.List;
import app.mailing.entities.groupe.Root;
import app.mailing.entities.tache.ReponseTache;
import app.mailing.entities.tache.Tache;
import app.mailing.entities.tache.TacheFilter;
import app.mailing.entities.tache.TacheFiltreReponse;
import app.mailing.entities.user.DetailUser;

public class TaskAlfrescoServiceImpl implements TaskAlfrescoService {

	@Override
	public ArrayList<Tache> getTasks() {
		System.out.println("----Liste DES TACHES-----");
		String uri = "https://apirect.camacte.com/api-activiti-app/enterprise/tasks/query?api-key=93b7e27a-9e8c-42ee-94be-b9d2cd9bbe67";  
		HttpEntity<String> request = new HttpEntity("{}", setHeader("admin@app.activiti.com", "admin"));
	    ResponseEntity<ReponseTache> response = new RestTemplate().exchange(uri, HttpMethod.POST, request, ReponseTache.class);
	    ReponseTache taches = response.getBody();
		return taches.getData();
	}
	
	@Override
	public List getGroupes() {
		Date date1 = new Date();
		String uri = "https://apirect.camacte.com/api-alfresco-default/alfresco/versions/1/groups?api-key=51645d9e-8ac2-4a12-89c1-a11ab13dd57d&maxItems=1";  
		HttpEntity<String> request = new HttpEntity(setHeader2());
		ResponseEntity<Root> response = new RestTemplate().exchange(uri, HttpMethod.GET, request, Root.class);
		if (response.getBody().list.pagination.totalItems > 0) {
			int max = response.getBody().list.pagination.totalItems;
			uri = "https://apirect.camacte.com/api-alfresco-default/alfresco/versions/1/groups?api-key=51645d9e-8ac2-4a12-89c1-a11ab13dd57d&maxItems=" + max;
			response = new RestTemplate().exchange(uri, HttpMethod.GET, request, Root.class);
		}
		Date date2 = new Date();
		Long inter = date2.getTime() - date1.getTime();
		System.out.println("----Liste DES GROUPS----- " + inter + "ms");
		return response.getBody().list;
	}
	
	@Override
	public app.mailing.entities.user.List getUsersGroupe(String groupe_id) {
		Date date1 = new Date();
		String uri = "https://apirect.camacte.com/api-alfresco-default/alfresco/versions/1/groups/GROUP_GG_GED_PRO_DC_Sarreguemines_Gestionnaire/members?api-key=51645d9e-8ac2-4a12-89c1-a11ab13dd57d&maxItems=1";  
		HttpEntity<String> request = new HttpEntity(setHeader2());
		ResponseEntity<app.mailing.entities.user.Root> response = new RestTemplate().exchange(uri, HttpMethod.GET, request, app.mailing.entities.user.Root.class);
		if (response.getBody().list.pagination.totalItems > 0) {
			int max = response.getBody().list.pagination.totalItems;
			uri = "https://apirect.camacte.com/api-alfresco-default/alfresco/versions/1/groups/" + groupe_id + "/members?api-key=51645d9e-8ac2-4a12-89c1-a11ab13dd57d&maxItems=" + max;
			response = new RestTemplate().exchange(uri, HttpMethod.GET, request, app.mailing.entities.user.Root.class);
		}
		Date date2 = new Date();
		Long inter = date2.getTime() - date1.getTime();
		System.out.println("Users By Group " + inter + "ms");
		return response.getBody().list;
	}
	
	@Override
	public DetailUser getDetailUser(String user_id) {
		Date date1 = new Date();
		String uri = "https://apirect.camacte.com/api-alfresco-default/alfresco/versions/1/people/"+ user_id +"?api-key=51645d9e-8ac2-4a12-89c1-a11ab13dd57d";  
		HttpEntity<String> request = new HttpEntity(setHeader2());
		ResponseEntity<DetailUserResp> response = new RestTemplate().exchange(uri, HttpMethod.GET, request, DetailUserResp.class);
		DetailUserResp detailUser = response.getBody();
		Date date2 = new Date();
		Long inter = date2.getTime() - date1.getTime();
		System.out.println("  Detail User " + inter + "ms");
		return detailUser.entry;
	}
	
	@Override
	public TacheFiltreReponse getTachesFilter(String assignee, String state, Date dateStart, Date dateEnd, Date endDateStart) {
		System.out.println("----getTachesFilter-----");
		String uri = "https://apirect.camacte.com/api-activiti-app/enterprise/custom-service/tasks/filter?api-key=93b7e27a-9e8c-42ee-94be-b9d2cd9bbe67"; 
		TacheFilter tacheFilter = new TacheFilter(assignee, state, dateStart, dateEnd, endDateStart);
		HttpEntity<String> request = new HttpEntity(tacheFilter, setHeader("admin@app.activiti.com", "admin"));
	    ResponseEntity<TacheFiltreReponse> response = new RestTemplate().exchange(uri, HttpMethod.POST, request, TacheFiltreReponse.class);
	    
	    TacheFiltreReponse tachesfilterResponse = response.getBody();
	    if(tachesfilterResponse.last) {
	    	return tachesfilterResponse;
	    }else {
	    	uri = uri + "&size=" + tachesfilterResponse.totalElements;
		    response = new RestTemplate().exchange(uri, HttpMethod.POST, request, TacheFiltreReponse.class);
		    tachesfilterResponse = response.getBody();
		    return tachesfilterResponse;
	    }
	}
	
	public HttpHeaders setHeader(String login, String pwd) {
		// create auth credentials
	    String authStr = "admin@app.activiti.com:admin" ;
	    String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

	    // create headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", "application/json");
	    headers.add("Authorization", "Basic " + base64Creds);
	    headers.add("Content-Type", "application/json");
	    return headers;
	}
	
	public HttpHeaders setHeader2() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("X-Alfresco-Remote-User-RhV26NcYTrfz8gG4", "FALL");
		headers.add("Content-Type", "application/json");
		return headers;
	}


}
