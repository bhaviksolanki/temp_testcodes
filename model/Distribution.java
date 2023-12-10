
@Data
public class Distribution {
	@Validated
	@DuplicateCheck (entityClass = ProductDetail.class)
	@RegexValidated
	@CopyProp (targetPropName = "productId") 
	private String product_id;

	@Validated
	@DuplicateCheck (entityClass = Client.class)
	@RegexValidated
	@CopyProp (targetPropName = "clientId")
	private String client_id;

	@Validated
	@RegexValidated
	@CopyProp (targetPropName = "dist Handle")
	private String distHandle;
	
	@Validated
	@RegexValidated
	@CopyProp (targetPropName = "distType") 
	private String distType;
	
	@Validated
	@CopyProp (target PropName = "endpoint DetailId") 
	private Integer endpointDetailId;
	
	@Validated
	private String active;
	
	@Validated
	private String mode;
	
	@RegexValidated
	@Validated
	@DuplicateCheck (entityClass = DistributionDetail.class) 
	//@CopyProp (targetPropName "distributionId")
	private String distributionId;
}