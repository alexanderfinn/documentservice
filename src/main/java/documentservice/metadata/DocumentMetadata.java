package documentservice.metadata;

import documentservice.utils.TokenGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Finn
 */
public class DocumentMetadata {

  private final String documentId;

  private final String accessKey;

  private final List<FileMetadata> files = new ArrayList<>();

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

  public String createFile(String fileType) {
    FileMetadata fm = new FileMetadata(TokenGenerator.getUniqueToken());
    fm.setFileType(fileType);
    files.add(fm);
    return fm.getFileId();
  }
}
