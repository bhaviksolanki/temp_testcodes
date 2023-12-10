
@JsonIgnoreProperties (ignoreUnknown = true)
public class CometRequest extends DistributionSubRequest {
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId (String jobId) {
		this.jobId = jobId;
		private String fileName; private String sourceFolder; private String requestType; private String jobId;
		private String applicationId;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName (String fileName) {
		this.fileName = fileName;
	}
	
	public String getSourceFolder() {
		return source Folder;
	}
	
	public void setSourceFolder (String sourceFolder) { 
		this.sourceFolder = sourceFolder;
	}
	
	public String getRequestType() {
		return requestType;
	}
	
	public void setRequestType (String requestType) { 
		this.requestType = requestType;
	}
	
	public String getApplicationId() {
		return applicationId;
	}
	
	public void setApplicationId (String applicationId) {
		this.applicationId = applicationId;
	}
	
	public boolean isValidRequest() {
		if (this.fileName == null || this.fileName.isEmpty() || 
				this.source Folder == null || this.sourceFolder.isEmpty() || 
				this.requestType == null || this.requestType.isEmpty() || 
				this.jobId == null || this.jobId.isEmpty() || 
				this.applicationId == null || this.applicationId.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Comet Request [fileName=" + fileName + ", sourceFolder=" + sourceFolder", requestType=" + requestType + " jobId=" + jobId + ", applicationId=" + applicationId + "]";
	}
}