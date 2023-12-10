
package com.ms.datawise.distn.app;
import com.ms.datawise.distn.config.QueueConfiguration; 
import com.ms.datawise.distn.dao.ClientService;
import com.ms.datawise.distn.dao.DistributionDetailService; 
import com.ms.datawise.distn.dao. Product DistributionService; 
import com.ms.datawise.distn.entity. Client;
import com.ms.datawise.distn.entity.DistributionDetail; 
import com.ms.datawise.distn.entity.ProDistUniqueID; 
import com.ms.datawise.distn.entity.ProductDistribution; 
import com.ms.datawise.distn.services.impl.ZipService; 
import com.ms.datawise.distn.subscriber. DistributionRequest; 
import lombok.extern.slf4j.slf4j;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.core.env.Environment; 
import org.springframework.http.MediaType; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE; 
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
@slf4j
@RestController
public class DocumentReportDistnController {
	private final ClientService clientService;
	private final DistributionDetailService distributionDetailService; 
	private final Product DistributionService productDistributionService; 
	
	private final QueueConfiguration queueConfiguration;
	
	@Value ("${distn.input-queue}")
	private String SenderQueueName;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ZipService zipService;
	
	public DocumentReport DistnController (ClientService clientService,
											DistributionDetailService distributionDetailService, 
											ProductDistributionService product DistributionService, 
											QueueConfiguration queueConfiguration) {
	
		this.clientService clientService; 
		this.distributionDetailService = distributionDetailService; 
		this.productDistributionService = product DistributionService; 
		this.queueConfiguration = queueConfiguration;
	}
	
	
	@GetMapping("/hello")
	public String sayHello() {
		return ("Hello, world!\n");
	}
	
	@GetMapping (value = "/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Client getClient (@PathVariable ("id") String clientId) {
		return clientService.getClientDetails (clientId);
	}
	
	@GetMapping (value = "/distribution/{id}", produces MediaType.APPLICATION_JSON_VALUE) 
	public DistributionDetail getDistribution (@PathVariable ("id") Integer id) {
		return distributionDetailService.getDistributionDetails (id);
	}
	
	@GetMapping (value = "/prod-dist/{prodId}/{distId}", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Product Distribution getProdDistribution (@PathVariable ("prodId") String prodid, @PathVariable ("distId") Integer distId) {
		return product DistributionService.getProdDistDetails (new ProDistUnique ID (distId, prodId));
	}
	
	
	// Post request for generating report
	@PostMapping (value="/triggerReportDistribution", consumes = APPLICATION_JSON_VALUE, produces =TEXT_PLAIN_VALUE)
	public String processRequest (@RequestBody DistributionRequest restRequest) {
		try{
			ObjectWriter ow = new ObjectMapper().writer().withDefault PrettyPrinter (); 
			String jsonString = ow.writeValueAsString (restRequest);
			queueConfiguration.publisher().writeToQueue (SenderQueueName, jsonString);
	} catch (Exception e) {
		log.error("Exception sending the data to MQ" + e);
		return ResponseEntity.badRequest() .body ("Failed to Send message.").toString();
	}
		return restRequest.toString();
	}
}
	