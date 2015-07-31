package documentservice.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import documentservice.utils.TokenGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Finn
 */
@JsonIgnoreProperties({"originalFile"})
public class DocumentMetadata {

  public static final String UPLOAD_STATUS_NOT_STARTED = "NOT STARTED";
  public static final String UPLOAD_STATUS_IN_PROGRESS = "IN PROGRESS";
  public static final String UPLOAD_STATUS_COMPLETED = "COMPLETED";
  private String documentId;

  private String accessKey;

  private final List<FileMetadata> files = new ArrayList<>();
  private String uploadStatus = UPLOAD_STATUS_NOT_STARTED;
  private long uploadedSize;
  private String originalFilename;
  private long originalSize;

  public DocumentMetadata() {
  }

  public DocumentMetadata(String documentId, String accessKey) {
    this.documentId = documentId;
    this.accessKey = accessKey;
  }

  public String getDocumentId() {
    return documentId;
  }

  public boolean validateKey(String accessKey) {
    return this.accessKey.equals(accessKey);
  }

  public FileMetadata getOriginalFile() {
    for(FileMetadata file: files) {
      if(file.isOriginal()) return file;
    }
    return null;
  }

  public List<FileMetadata> getFiles() {
    return files;
  }

  public FileMetadata createFile(String fileType) {
    FileMetadata fm = new FileMetadata(TokenGenerator.getUniqueToken());
    fm.setFileType(fileType);
    files.add(fm);
    return fm;
  }

  public String getUploadStatus() {
    return uploadStatus;
  }

  public void setUploadStatus(String uploadStatus) {

    this.uploadStatus = uploadStatus;
  }

  public long getUploadedSize() {
    return uploadedSize;
  }

  public void setUploadedSize(long uploadedSize) {
    this.uploadedSize = uploadedSize;
  }

  public String getOriginalFilename() {
    return originalFilename;
  }

  public void setOriginalFilename(String originalFilename) {
    this.originalFilename = originalFilename;
  }

  public void setOriginalSize(long originalSize) {
    this.originalSize = originalSize;
  }

  public long getOriginalSize() {
    return originalSize;
  }
}
