package documentservice.converters;

import documentservice.configuration.Configuration;
import documentservice.metadata.DocumentMetadata;
import documentservice.metadata.exceptions.DocumentNotAuthorizedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Alexander Finn
 */
public abstract class BaseConverter implements Converter {

  protected InputStream getOriginalFileStream(DocumentMetadata metadata) throws FileNotFoundException {
    return Configuration.getInstance().getBlobStore().get(metadata.getDocumentId(), metadata.getOriginalFile().getFileId());
  }

  protected OutputStream getTargetOutputStream(DocumentMetadata metadata, String fileId) throws IOException {
    return Configuration.getInstance().getBlobStore().getOutputStream(metadata, fileId);
  }

  protected void saveMetadata(DocumentMetadata metadata, String accessKey) throws DocumentNotAuthorizedException {
    Configuration.getInstance().getDocumentRepository().put(metadata, accessKey);
  }
}
