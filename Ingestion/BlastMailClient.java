@Component
public class BlastMailClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BlastMailClient.class);
	
	static final String BLASTMAIL_URL = "BLASTMAIL_URL";
	
	@Resource
	DWDstnZKUtil DWDstnZKUtil;
	
	@Resource (name = "BlastmailWebClientFactory")
	protected JAXRSClientFactoryBean blastmailWebClientFactory;
	
	public Response callPost (String body) throws IngestionClientException {
	
		synchronized (this.blastmailWebClientFactory) {
		String blastMailURLValue = DWDstnZKUtil.getZKConfigPropertyAsString (BLASTMAIL_URL); 
		this.blastmailWebClientFactory.setAddress (blastMailURLValue); }
		
		WebClient wc = blastmailWebClientFactory.createWebClient();
		try {
			WebClient.getConfig (wc).getHttpConduit ().getClient().setAllowChunking (false); 
			Response response = wc.path("submission/xml")
				.type("application/xml").header("Content-type", "application/xml; charset=ISO-8859-1").encoding ("UTF8")
				.post (body);
			LOGGER.info("Request returns status {}" , response.getStatus());
			LOGGER.debug("Reponse : " + response);
			return response;
		} finally {
			if (wc != null) {
				wc.close();
			}
		}
	}
}