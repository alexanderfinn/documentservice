package documentservice.blobstore;

import documentservice.metadata.DocumentMetadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public interface BlobStore {

  void configure(Properties settings);

  long store(DocumentMetadata metadata, String fileId, InputStream stream) throws IOException;

  InputStream getStream(String documentId, String fileId) throws FileNotFoundException;

}
