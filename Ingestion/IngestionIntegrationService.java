
@Component
public class IngestionIntegrationService {
	private static final Logger LOGGER = LoggerFactory.getLogger (Ingestion IntegrationService.class); 
	private static final String ENTITY_STRING = "entity";
	
	String Ingestion ProductMetaDataPath = "DOCUMENT_REPO_LATEST_METADATA"; //"INGESTION_SERVICE_PATH_METADATA" 
	String IngestionProductFilePath = "DOCUMENT_REPO_DOWNLOAD_API"; // "INGESTION_SERVICE_PATH_FILE"
	
	String downloadPath = "INGESTION_DOWNLOAD_PATH_FILE";
	
	String ENABLE_UNDERSCORE_FILENAME_FILTER = "ENABLE_UNDERSCORE_FILENAME_FILTER";
	
	@Resource
	DocRepoClient docRepoClient;
	
	@Autowired
	DWDstnZKUtil dstnZKUtil;
	
	@Autowired
	IEventLogDAO eventLogDAO;

	public ProductEntityMetaData getIngestionMetaData (String productId, String as ofDate, String zoneId, String transactionId, String uuid) throws IngestionClientException { 
	
		Map<String, String> queryParams = new HashMap<>();
		
		if ( uuid== null || uuid.isEmpty() || uuid.isBlank() ){
			throw new IngestionClientException ("UUID is empty.");
		}
		
		queryParams.put("uuid", uuid);
		
		ProductEntityMetaData metaData = null;
		
		String url = dstnZKUtil.getZKConfigPropertyAsString (IngestionProductMetaDataPath);
		EventLog event LogEntity = new Event Log (transactionId, productId, "3009",
		
		EventLogConst.DistEventMap.get("3009"), "SUCCESS", "Ingestion metadata URL : "+url, null);
		eventLogDAO.saveEventLogAsync (eventLogEntity);
		
		Response response = docRepoClient.callGet (url, queryParams);
		String output = response.readEntity (String.class);
		
		LOGGER.info (output);
		metaData = composeProductEntityMetadata (productId, output, transactionId);
		
		return metaData;
	}
	
	
	
	private String generateAsofDate (String productId, String zoneId) { 
		String pattern = AsofDateUtil.getAsOfDateFormat();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat (pattern); 
		if (zoneId == null || zoneId.isEmpty()){
			simpleDateFormat.setTime Zone (TimeZone.getTimeZone ("UTC"));
		}else{
			simpleDateFormat.setTimeZone (TimeZone.getTimeZone (zoneId));
		}
		
		String date = simpleDateFormat.format (new Date()); 
		return date;
	}
	
	private ProductEntityMetaData compose ProductEntityMetadata (String productId, String strResponse, String transactionId) throws IngestionClientException { 
		if (productId != null) {
			Optional<ProductEntityMetaData> metaDataWrap = JsonTransformer.transform (strResponse, ProductEntityMetaData.class,
				new ObjectMapper());
			if (metaDataWrap.isPresent()) {
				return metaDataWrap.get();
			}else {
				String msg = "Error parsing ingestion metadata response : "+ strResponse;
				EventLog eventLogEntity = new EventLog (transactionId, productId, "3010", EventLogConst. DistEventMap.get("3010"), "FAIL", msg, null); 
				eventLogDAO.saveEventLogAsync (eventLogEntity); throw new IngestionClientException (msg);
			}
		}
		return null;
	}
	
	
	public List<String> fetchIngestionFile (String url, String productId, String savePath, String transactionId) throws IOException, IngestionClientException {
	
		List<String> downloadFileList = new ArrayList<String>();
		
		Response response = docRepoClient.callGet (url);
		
		Event Log event LogEntity = new Event Log (transactionId, productId, "3012",
			EventLogConst. DistEventMap.get("3012"), "SUCCESS", "file URL "+url, null); 
		
		eventLogDAO.saveEventLogAsync (eventLogEntity);
		
		InputStream in response.readEntity (InputStream.class);
		
		String underscore Flag = dstnZKUtil.getZKConfigPropertyAsString (ENABLE_UNDERSCORE_FILENAME_FILTER);
		// String filename = "dumpIngetstion.txt";
		String fieldValue = response.getHeaderString("Content-Disposition");
		if (fieldValue == null || !fieldValue.contains("filename=\"")) {
			return downloadFileList;
		}
		// parse the file name from the header field
		String filename = fieldValue.substring (fieldValue.indexOf("filename=\"") + 10, fieldValue.length()- 1);
		
			
		if (filename.endsWith(".zip")) {
			LOGGER.info("source file name {}. The file is in zip format.", filename); 
			try (ZipInputStream stream = new ZipInputStream(in)) {
				byte[] buffer = new byte[2048];
				ZipEntry entry;
				while ((entry = stream.getNextEntry()) != null) {
					String inFilename = entry.getName();
					inFilename = FileNameCleaner.cleanTimeStamp (inFilename, underscoreFlag); 
					if (inFilename == null || inFilename.isEmpty()) {
						continue;
					}
					LOGGER. R.info("extracting file ...", inFilename);
					Path path = Paths.get(savePath, inFilename);
					try (FileOutputStream fos = new FileOutputStream (path. toFile());
						BufferedOutputStream bos = new BufferedOutputStream (fos, buffer.length)) {
						int len;
						while ((len = stream.read(buffer)) > 0) {
							bos.write(buffer, 0, len);
						}
					}
					catch (Exception e) {
						LOGGER.error("Datawise Request | Exception extracting file () from {}", inFilename, filename); 
						LOGGER.error ("Exception: ", e);
					}
					downloadFileList.add(inFilename);
				}
			}
			
		} else {
			filename = FileNameCleaner.cleanTimeStamp (filename, underscoreFlag); 
			Path path =Paths.get(savePath, filename);
			Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING); 
			downloadFileList.add(filename);
		}
		return downloadFileList;
	}
	
	
	/** Created for downloading files for given uuid */
	@Retry (retryExceptions={FileSystemException.class, ClientException.class}) 
	public List<String> get Date Product ByAsofDate (String productId, String asofDate, String zoneId, String savePath,
			String transactionId, String uuid) throws IOException, IngestionClientException {
		ProductEntityMetaData metaData = getIngestionMetaData (productId, asofDate, zoneId, transactionId, uuid); 
		/** Verify productEntity validity and Collect the downloadLinks if valid. */
		
		if (! isValidateEntityMetadata (metaData, transactionId, productId)) {
			//code should never hit here since invalid metadata will cause IngestionClientException 
			return null;
		}
		
		// populate download links
		List<String> downloadLinks = metaData.getEntity().getBody().getReportList()
			.stream()
			.map(x-> x.getFileDownloadLink())
			.collect (Collectors.toList());
		
		if (downloadLinks == null || downloadLinks.isEmpty()) {
			
			String msg = "Invalid Download link details.";
			EventLog event LogEntity = new Event Log (transactionId, productId, "3010",
			EventLogConst. DistEventMap.get("3010"), "FAIL", msg, null);
			eventLogDAO.saveEventLogAsync (event LogEntity);
			throw new IngestionClientException (msg);
		}
		downloadLinks = new ArrayList<>();
		for (ProductEntityMetaData. Report report: metaData.getEntity().getBody().getReportList()) {
			downloadLinks.addAll(fetchIngestionFile (report.getFileDownloadLink(),
			productId, savePath,
			transactionId));
		}
		return downloadLinks;
	}
	
	
	public boolean isValidateEntityMetadata (ProductEntityMetaData productEntityMetaData,
		String transactionId, String productId) throws IngestionClientException { 
		if (productEntityMetaData == null) { 
			String msg = "could not fetch metaData";
			EventLog event LogEntity = new Event Log (transactionId, productId, "3010", EventLogConst. DistEventMap.get("3010"), "FAIL", msg, null); 
			eventLogDAO.saveEventLogAsync (event LogEntity);
			throw new IngestionClientException (msg);
		}
		if (productEntityMetaData.getStatus () != 200) {
			String msg = "Status: "+ productEntityMetaData.getStatus () + ";</br>";
			EventLog event LogEntity = new Event Log (transactionId, productId, "3010", EventLogConst. DistEventMap.get("3010"), "FAIL", msg, null); 
			eventLogDAO.saveEventLogAsync (eventLogEntity);
			throw new IngestionClientException (msg);
		}
		
		if (productEntityMetaData.getEntity() == null ){
			String msg = "Status: "+ productEntityMetaData.getStatus() + ";</br>"; 
			msg+="Entity field is null.";
			EventLog event LogEntity = new Event Log (transactionId, productId, "3010", EventLogConst. DistEventMap.get("3010"), "FAIL", msg, null); 
			
			eventLogDAO.saveEventLogAsync (event LogEntity);
			throw new IngestionClientException (msg);
		}
		
		if (productEntityMetaData.getEntity().getBody().getReportList() == null
			|| productEntityMetaData.getEntity().getBody().getReportList().isEmpty()) {
		
			String msg = "StatusCodeValue: "+ productEntityMetaData.getEntity().getStatusCodeValue() + ";</br>" 
			+" Status: "+ productEntityMetaData.getEntity().getStatusCode () + ";</br>"
			+" EntityBodyMessage: "+ productEntityMetaData.getEntity().getBody() +";</br>" ;
			
			msg+= "Report list is empty.";
			EventLog event LogEntity = new Event Log (transactionId, productId, "3010",
			EventLogConst. DistEventMap.get("3010"), "FAIL", msg, null);
			eventLogDAO.saveEventLogAsync (event LogEntity);
			throw new IngestionClientException (msg);
		}
		return true;
	}
	

	public Event Log addEventLog (EventLog evnetLogEntity) {
		return event LogDAO.saveEventLog (evnetLogEntity);
	}
}