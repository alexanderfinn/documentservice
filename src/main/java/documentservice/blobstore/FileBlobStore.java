package documentservice.blobstore;

import documentservice.metadata.DocumentMetadata;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public class FileBlobStore implements BlobStore {

  private final String baseFolder;

  public FileBlobStore(Properties settings) {
    this.baseFolder = settings.getProperty("blobstore.file.baseFolder");
  }

  @Override
  public long store(DocumentMetadata metadata, String fileId, InputStream stream) throws IOException {
    String filePath = baseFolder + File.separator + metadata.getDocumentId() + File.separator + fileId;
    File targetFile = new File(filePath);
    targetFile.mkdirs();
    return 0;
  }

  @Override
  public OutputStream getStream(String documentId, String fileId) {
    return null;
  }
}
