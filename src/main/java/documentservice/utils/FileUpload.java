package documentservice.utils;

import documentservice.configuration.Configuration;
import documentservice.metadata.DocumentMetadata;
import documentservice.metadata.FileMetadata;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alexander Finn
 */
public class FileUpload {

  private final DocumentMetadata metadata;
  private final String filename;

  public FileUpload(DocumentMetadata metadata, String filename) {
    this.metadata = metadata;
    this.filename = filename;
  }

  public long upload(InputStream is) throws IOException {
    FileMetadata fm = metadata.createFile(FileMetadata.FILE_TYPE_ORIGINAL);
    updateMetadata(DocumentMetadata.UPLOAD_STATUS_IN_PROGRESS, 0);
    long result = Configuration.getInstance().getBlobStore().put(metadata, fm.getFileId(), is);
    metadata.setOriginalFilename(this.filename);
    fm.setFileExtension(FileUtils.getFileExtension(filename));
    updateMetadata(DocumentMetadata.UPLOAD_STATUS_COMPLETED, result);
    return result;
  }

  public void updateMetadata(String uploadStatus, long uploadedSize) {
    metadata.setUploadStatus(uploadStatus);
    metadata.setUploadedSize(uploadedSize);
    metadata.setOriginalSize(uploadedSize);
    Configuration.getInstance().getDocumentRepository().put(metadata);
  }

}
