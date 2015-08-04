package documentservice.blobstore;

import com.google.common.io.ByteStreams;
import documentservice.metadata.DocumentMetadata;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public class InMemoryBlobStore implements BlobStore {

  Map<String, byte[]> store = new HashMap<>();

  @Override
  public void configure(Properties settings) {

  }

  @Override
  public long put(DocumentMetadata metadata, String fileId, InputStream stream) throws IOException {
    byte[] bytes = ByteStreams.toByteArray(stream);
    store.put(getStoreKey(metadata.getDocumentId(), fileId), bytes);
    return bytes.length;
  }

  @Override
  public OutputStream getOutputStream(DocumentMetadata metadata, String fileId) throws IOException {
    return new ByteArrayOutputStream();
  }

  private String getStoreKey(String documentId, String fileId) {
    return documentId + "-" + fileId;
  }

  @Override
  public InputStream get(String documentId, String fileId) {
    return new ByteArrayInputStream(store.get(getStoreKey(documentId, fileId)));
  }
}
