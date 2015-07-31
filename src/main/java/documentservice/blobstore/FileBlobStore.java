package documentservice.blobstore;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import documentservice.metadata.DocumentMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public class FileBlobStore implements BlobStore {

  private String baseFolder;

  final Logger logger = LoggerFactory.getLogger(FileBlobStore.class);

  @Override
  public void configure(Properties settings) {
    this.baseFolder = settings.getProperty("blobstore.file.baseFolder", System.getProperty("java.io.tmpdir") + File.separator + "documentservice");
  }

  @Override
  public long put(DocumentMetadata metadata, String fileId, InputStream stream) throws IOException {
    File targetFile = getTargetFile(metadata.getDocumentId(), fileId);
    Files.createParentDirs(targetFile);

    FileOutputStream out = new FileOutputStream(targetFile);
    long size = ByteStreams.copy(stream, out);
    out.close();
    return size;
  }

  private File getTargetFile(String documentId, String fileId) {
    String filePath = baseFolder + documentId + File.separator + fileId;
    return new File(filePath);
  }

  @Override
  public InputStream get(String documentId, String fileId) throws FileNotFoundException {
    return new FileInputStream(getTargetFile(documentId, fileId));
  }
}
