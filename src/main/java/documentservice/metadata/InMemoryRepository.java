package documentservice.metadata;

import documentservice.metadata.exceptions.DocumentNotAuthorizedException;
import documentservice.metadata.exceptions.DocumentNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Finn
 */
public class InMemoryRepository implements DocumentRepository {

  private final Map<String, DocumentMetadata> map = new HashMap<>();

  @Override
  public DocumentMetadata get(String documentId, String documentKey) throws DocumentNotFoundException, DocumentNotAuthorizedException {
    DocumentMetadata metadata = map.get(documentId);
    if (metadata == null) throw new DocumentNotFoundException();
    if (!metadata.validateKey(documentKey)) throw new DocumentNotAuthorizedException();
    return metadata;
  }

  @Override
  public void delete(String documentId, String documentKey) throws DocumentNotFoundException, DocumentNotAuthorizedException {
    get(documentId, documentKey);
    map.remove(documentId);
  }

  @Override
  public void put(DocumentMetadata metadata) {
    map.put(metadata.getDocumentId(), metadata);
  }

  @Override
  public boolean exists(String documentId) {
    return map.containsKey(documentId);
  }
}
