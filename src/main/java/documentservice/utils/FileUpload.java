package documentservice.utils;

import documentservice.configuration.Configuration;
import documentservice.metadata.DocumentMetadata;
import documentservice.metadata.FileMetadata;
import documentservice.metadata.exceptions.DocumentNotAuthorizedException;

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

  public long upload(InputStream is, String accessKey) throws IOException, DocumentNotAuthorizedException {
    FileMetadata fm = metadata.createFile(FileMetadata.FILE_TYPE_ORIGINAL);
    updateMetadata(DocumentMetadata.UPLOAD_STATUS_IN_PROGRESS, 0, accessKey);
    long result = Configuration.getInstance().getBlobStore().put(metadata, fm.getFileId(), is);
    metadata.setOriginalFilename(this.filename);
    fm.setFileExtension(FileUtils.getFileExtension(filename));
    updateMetadata(DocumentMetadata.UPLOAD_STATUS_COMPLETED, result, accessKey);
    return result;
  }

  public void updateMetadata(String uploadStatus, long uploadedSize, String accessKey) throws DocumentNotAuthorizedException {
    metadata.setUploadStatus(uploadStatus);
    metadata.setUploadedSize(uploadedSize);
    metadata.setOriginalSize(uploadedSize);
    Configuration.getInstance().getDocumentRepository().put(metadata, accessKey);
  }

}
