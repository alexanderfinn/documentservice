package com.alexanderfinn.documentservice.metadata;

import com.alexanderfinn.documentservice.metadata.exceptions.DocumentAlreadyExistsException;
import com.alexanderfinn.documentservice.metadata.exceptions.DocumentNotAuthorizedException;
import com.alexanderfinn.documentservice.metadata.exceptions.DocumentNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Finn
 */
public class InMemoryRepository implements DocumentRepository {

  private final Map<String, DocumentMetadata> map = new HashMap<>();

  @Override
  public DocumentMetadata get(String documentId, String documentKey) throws DocumentNotFoundException, DocumentNotAuthorizedException {
    DocumentMetadata metadata = map.get(documentKey);
    if (metadata == null) throw new DocumentNotFoundException();
    if (!metadata.validateKey(documentKey)) throw new DocumentNotAuthorizedException();
    return metadata;
  }

  @Override
  public DocumentMetadata create() {
    return new DocumentMetadata();
  }

  @Override
  public void delete(String documentId, String documentKey) throws DocumentNotFoundException, DocumentNotAuthorizedException {
    get(documentId, documentKey);
    map.remove(documentId);
  }

  @Override
  public void put(DocumentMetadata metadata) throws DocumentAlreadyExistsException {
    if (map.containsKey(metadata.getDocumentId())) {
      throw new DocumentAlreadyExistsException();
    }
    map.put(metadata.getDocumentId(), metadata);
  }
}
