package documentservice.configuration;

import documentservice.blobstore.BlobStore;
import documentservice.metadata.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public class Configuration {

  private static Configuration instance;
  private DocumentRepository documentRepository;
  private BlobStore blobStore;

  private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

  private Configuration(Properties settings) {
    try {
      documentRepository = (DocumentRepository) Class.forName(settings.getProperty("documentrepository.class",
          "documentservice.metadata.InMemoryRepository")).newInstance();
      blobStore = (BlobStore) Class.forName(settings.getProperty("blobstore.class",
          "documentservice.blobstore.InMemoryBlobStore")).newInstance();

      blobStore.configure(settings);
    } catch (Exception e) {
      logger.error("Failed to instantiate classes during configuration loading: " + e);
    }
  }

  public DocumentRepository getDocumentRepository() {
    return this.documentRepository;
  }

  public BlobStore getBlobStore() {
    return blobStore;
  }

  public static Configuration getInstance() {
    if (instance == null) {
      Properties settings = new Properties();
      try {
        settings.load(Configuration.class.getResourceAsStream("bootstrap.properties"));
      } catch (IOException e) {
        logger.error("Failed to load bootstrap configuration with exception: " + e);
      }
      instance = new Configuration(settings);
    }
    return instance;
  }

  public static void initInstance(Properties settings) {
    instance = new Configuration(settings);
  }
}
