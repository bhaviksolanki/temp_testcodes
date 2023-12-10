
@WebService (targetNamespace = "http://xml.ms.com/ns/datawise-distn/xml/1.0")
@SOAPBinding (parameterStyle = SOAPBinding.ParameterStyle.BARE)

public interface IEntitlementService {
	@WebMethod (operationName = "Entitlements")
	@WebResult (name = "EntitlementsResponse")
	
	EntitlementsResponse getAssetClassList(
		@WebParam(targetNamespace="http://xml.ms.com/ns/datawise-distn/xml/1.0", name =	"GetEntitlements") Entitlements getEntitlements);
	}
}