
@Component
public class IngestionClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IngestionClient.class);
	
	@Value ("${datawise-ingestion.host.url:http://dwip-dev-sf.ms.com:1111}")
	String ingestionServiceAddress;
	
	@Resource
	DWDstnZKUtil DWDstnZKUtil;
	
	@Resource (name = "IngestionWebClientFactory")
	protected JAXRSClientFactoryBean ingestionFileClientFactory;
	
	public Response callGet (String url) throws IngestionClientException { 
		populateHostPort();
		WebClient wc = ingestionFileClientFactory.createWebClient();
		try {
			WebClient.getConfig (wc).getHttpConduit().getClient().setAllowChunking (false); 
			Response response = wc.path(url).type("application/json")
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
		WebClient wc = ingestionFileClientFactory.createWebClient();
		try {
			WebClient.getConfig (wc).getHttpConduit ().getClient().setAllowChunking (false); 
			wc.path(url);
			for (Map.Entry<String, String> entry params.entrySet()) {
				wc.query(entry.getKey(), entry.getValue());
				Response response = wc.type ("application/json")
				// .accept("application/json")
				.get();
			}
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
		WebClient wc = ingestionFileClientFactory.createWebClient();
		try {
			WebClient.getConfig (wc).getHttpConduit ().getClient().setAllowChunking (false); 
			wc.path (url);
			Response response = wc.type("application/json").post(body);
			LOGGER.info("Ingestion request returns status {}", response.getStatus()); 
			return response;
		} finally {
			if(wc != null) {
				wc.close();
			}
		}
	}
	

	private void populateHostPort() throws IngestionClientException {
		LOGGER.info("populate Ingestion Service address to : " + ingestionServiceAddress); 
		if (ingestionServiceAddress == null || ingestionServiceAddress.isEmpty()) {
			throw new IngestionClientException ("Cannot populate ingestion service address");
		}
	
		synchronized (this.ingestionFileClientFactory) {
			this.ingestionFileClientFactory.setAddress (ingestionServiceAddress); 
			this.ingestionFileClientFactory.setProvider (new JacksonJaxbJsonProvider());
		}
	}
}