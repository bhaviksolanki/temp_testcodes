@Component
public class DocRepoClient {
	private static final Logger LOGGER = LoggerFactory.getLogger (DocRepoClient.class); 
	
	@Value ("${datawise-ingestion-new.host.url:http://datawise-research-reports-dev.ms.com:1111}") // Update with the New Api given by Pranita (Hostname and Port Num)
	String ingestionServiceAddress;
	
	@Resource
	DWDstnZKUtil DWDstnZKUtil;
	
	@Resource (name = "DocRepoClientFactory")
	protected JAXRSClientFactoryBean docRepoClient Factory;
	
	public Response callGet (String url) throws IngestionClientException { 
		populateHostPort();
		
		WebClient wc = docRepoClientFactory.createWebClient();
		
		try {
			WebClient.getConfig (wc).getHttpConduit ().getClient().setAllowChunking (false); 
			Response response wc.path (url).type("application/json")
			// .accept("application/json") 
			.get();
		
			LOGGER.info("Ingestion request returns status {}", response.getStatus());
			return response;
		} finally {
			if (wc != null) {
				wc.close();
			}
		}
	}
	
	
	
	public Response callGet (String url, Map<String, String> params) throws IngestionClientException { 
		populateHostPort();
		
		WebClient wc = docRepoClientFactory.createWebClient();
		try {
			WebClient.getConfig (wc).getHttpConduit ().getClient().setAllowChunking (false); 
			wc.path (url);
			for (Map.Entry<String, String> entry params.entrySet()) {
				wc.query(entry.getKey(), entry.getValue());
			}
			Response response = wc.type ("application/json")
				// .accept("application/json")
				.get();
			LOGGER.info("Ingestion request returns status {}", response.getStatus()); 
			return response;
		} finally {
			if (wc != null) {
				wc.close();
			}
		}
	}
	
	public Response callpost (String url, Object body) throws IngestionClientException{ 
		populateHostPort();
		WebClient wc = docRepoClientFactory.createWebClient();
		try {
			WebClient.getConfig (wc).getHttpConduit().getClient().setAllowChunking (false); 
			wc.path (url);
			Response response = wc.type("application/json").post(body);
			LOGGER.info("Ingestion request returns status {}", response.getStatus()); return response;
		finally {
			if (wc != null) {
				wc.close();
			}
		}
	}

	
	private void populateHostPort() throws IngestionClientException {
		LOGGER.info("populate Ingestion Service address to : " + ingestionServiceAddress); 
		if (ingestionServiceAddress == null || ingestionServiceAddress.isEmpty()) {
			throw new IngestionClientException ("Cannot populate ingestion service address");
		}
		
		synchronized (this.docRepoClient Factory) {
			this.docRepoClientFactory.setAddress (ingestionServiceAddress); 
			this.docRepoClientFactory.setProvider (new JacksonJaxbJsonProvider());
		}
	}
}
	
	