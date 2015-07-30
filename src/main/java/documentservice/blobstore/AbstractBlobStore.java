package documentservice.blobstore;

import documentservice.configuration.Configuration;
import documentservice.metadata.DocumentMetadata;

/**
 * @author Alexander Finn
 */
public abstract class AbstractBlobStore implements BlobStore {

  public void setUploadStatus(DocumentMetadata metadata, String uploadStatus) {
    metadata.setUploadStatus(uploadStatus);
    Configuration.getInstance().getDocumentRepository().put(metadata);
  }

}
