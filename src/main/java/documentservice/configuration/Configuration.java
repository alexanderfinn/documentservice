package documentservice.configuration;

import documentservice.blobstore.BlobStore;
import documentservice.blobstore.InMemoryBlobStore;
import documentservice.metadata.DocumentRepository;
import documentservice.metadata.InMemoryRepository;

/**
 * @author Alexander Finn
 */
public class Configuration {

  private final static Configuration instance = new Configuration();
  private final DocumentRepository documentRepository;
  private final BlobStore blobStore;

  private Configuration() {
    this.documentRepository = new InMemoryRepository();
    this.blobStore = new InMemoryBlobStore();
  }

  public DocumentRepository getDocumentRepository() {
    return this.documentRepository;
  }

  public BlobStore getBlobStore() {
    return blobStore;
  }

  public static Configuration getInstance() {
    return instance;
  }
}
