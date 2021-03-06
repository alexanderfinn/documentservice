package documentservice.metadata;

import documentservice.metadata.exceptions.DocumentNotAuthorizedException;
import documentservice.metadata.exceptions.DocumentNotFoundException;

/**
 * @author Alexander Finn
 */
public interface DocumentRepository {

  DocumentMetadata get(String documentId, String accessKey) throws DocumentNotFoundException, DocumentNotAuthorizedException;

  void delete(String documentId, String documentKey) throws DocumentNotFoundException, DocumentNotAuthorizedException;

  void put(DocumentMetadata metadata, String accessKey) throws DocumentNotAuthorizedException;

  boolean exists(String documentId);
}
