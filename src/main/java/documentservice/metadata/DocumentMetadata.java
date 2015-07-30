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

  private String documentId;

  private String accessKey;

  private final List<FileMetadata> files = new ArrayList<>();

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

  public String createFile(String fileType) {
    FileMetadata fm = new FileMetadata(TokenGenerator.getUniqueToken());
    fm.setFileType(fileType);
    files.add(fm);
    return fm.getFileId();
  }
}
