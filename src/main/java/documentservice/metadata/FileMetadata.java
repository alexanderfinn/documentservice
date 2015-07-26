package documentservice.metadata;

/**
 * @author Alexander Finn
 */
public class FileMetadata {
  public static final String FILE_TYPE_ORIGINAL = "ORIGINAL";
  private String fileType;
  private final String fileId;

  public FileMetadata(String fileId) {
    this.fileId = fileId;
    this.fileType = FILE_TYPE_ORIGINAL;
  }

  public boolean isOriginal() {
    return fileType.equals(FILE_TYPE_ORIGINAL);
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
}
