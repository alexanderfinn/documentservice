package documentservice.blobstore;

import com.google.common.io.ByteStreams;
import documentservice.metadata.DocumentMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author Alexander Finn
 */
public class InMemoryBlobStore implements BlobStore {

  Map<String, byte[]> store = new HashMap<>();

  @Override
  public long store(DocumentMetadata metadata, String fileId, InputStream stream) throws IOException {
    byte[] bytes = ByteStreams.toByteArray(stream);
    store.put(metadata.getDocumentId() + "-" + fileId, bytes);
    return bytes.length;
  }

  @Override
  public OutputStream getStream(String documentId, String fileId) {
    return null;
  }
}
