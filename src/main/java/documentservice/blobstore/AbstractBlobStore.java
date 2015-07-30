package documentservice.blobstore;

import documentservice.configuration.Configuration;
import documentservice.metadata.DocumentMetadata;

/**
 * @author Alexander Finn
 */
public abstract class AbstractBlobStore implements BlobStore {

  public void updateMetadata(DocumentMetadata metadata, String uploadStatus, long uploadedSize) {
    metadata.setUploadStatus(uploadStatus);
    metadata.setUploadedSize(uploadedSize);
    Configuration.getInstance().getDocumentRepository().put(metadata);
  }

}
