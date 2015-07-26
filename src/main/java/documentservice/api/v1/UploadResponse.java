package documentservice.api.v1;

/**
 * @author Alexander Finn
 */
public class UploadResponse extends APIResponse {

  private long uploadedSize;

  public long getUploadedSize() {
    return uploadedSize;
  }

  public void setUploadedSize(long uploadedSize) {
    this.uploadedSize = uploadedSize;
  }
}
