
@Service
public class Entitlement Service implements IEntitlement Service {
	
	private static final Logger LOGGER = LoggerFactory.getLogger (Entitlement Service.class);
	
	@Autowired
	GetEntitlement LDAP Helper getEntitlement LDAPHelper;
	
	@Autowired
	GetEntitlement E3Helper getEntitlementE3Helper;
	
	@Value ("${entitlements.check.source}")
	private String entitlement CheckSource;
	
	public String getEntitlement CheckSource() {
		return entitlement CheckSource;
	}
	
	public void setEntitlement CheckSource (String entitlement CheckSource) { 
		this.entitlement CheckSource = entitlement CheckSource;
	}
	
	@Override
	public EntitlementsResponse getAssetClassList (Entitlements getEntitlements) {
		EntitlementsResponse response = new EntitlementsResponse();
		try {
			if (!StringUtils.isEmpty(entitlement CheckSource) && "e3".equalsIgnoreCase (entitlement CheckSource)) { 
				LOGGER.info("Calling E3 service for entitlements");
				response = getEntitlement E3 Helper.getGroupsOfThe User (getEntitlements);
			} else if (!StringUtils.isEmpty (entitlement CheckSource)
						&& "ldap".equalsIgnoreCase (entitlement CheckSource)) { 
				LOGGER.info("Calling LDAP service for entitlements");
				response = getEntitlement LDAPHelper.getGroupsOfThe User (getEntitlements);
			} else {
				LOGGER.warn("Neither E3 nor LDAP service configured in the property entitlements.check.source"); 
				response.setStatusCode ("500");
				response.setStatus ("Service Initialization Error");
			}
		} catch (Exception e) {
			LOGGER.error (e.getMessage(), e);
			response.setStatusCode ("500");
			response.setStatus (DatasetConstants. FAIL);
		}
		return response;
	}
}
	