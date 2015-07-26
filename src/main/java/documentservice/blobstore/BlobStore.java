package documentservice.blobstore;

import documentservice.metadata.DocumentMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Alexander Finn
 */
public interface BlobStore {

  long store(DocumentMetadata metadata, String fileId, InputStream stream) throws IOException;

  OutputStream getStream(String documentId, String fileId);

}
