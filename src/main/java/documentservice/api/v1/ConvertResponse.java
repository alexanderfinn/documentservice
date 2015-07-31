package documentservice.api.v1;

/**
 * @author Alexander Finn
 */
public class ConvertResponse extends APIResponse {

  private String processingStatus;

  public String getProcessingStatus() {
    return processingStatus;
  }

  public void setProcessingStatus(String processingStatus) {
    this.processingStatus = processingStatus;
  }
}
