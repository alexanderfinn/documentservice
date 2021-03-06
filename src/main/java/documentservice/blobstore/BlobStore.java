package documentservice.blobstore;

import documentservice.metadata.DocumentMetadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public interface BlobStore {

  void configure(Properties settings);

  long put(DocumentMetadata metadata, String fileId, InputStream stream) throws IOException;

  OutputStream getOutputStream(DocumentMetadata metadata, String fileId) throws IOException;

  InputStream get(String documentId, String fileId) throws FileNotFoundException;

}
