
public class ProductContactModel {
	@Validated
	@RegexValidated
	@CopyProp (targetPropName = "productId")
	private String = productId;
	
	@Validated
	@CopyProp (targetPropName = "contactName")
	private String contactName;

	@Validated
	@CopyProp (targetPropName = "contactEmail") 
	private String contact Email;

	@Validated
	@RegexValidated
	@CopyProp (targetPropName = "contactType") 
	private String contactType;

	@Validated
	@RegexValidated
	@CopyProp (targetPropName = "isSupport")
	private String isSupport;

	@Validated
	@RegexValidated
	@CopyProp (targetPropName = "isActive")
	private String isActive;

	@Validated
	private String mode;
}