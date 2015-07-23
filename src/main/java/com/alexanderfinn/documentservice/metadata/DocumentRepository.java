package com.alexanderfinn.documentservice.metadata;

import com.alexanderfinn.documentservice.metadata.exceptions.DocumentAlreadyExistsException;
import com.alexanderfinn.documentservice.metadata.exceptions.DocumentNotAuthorizedException;
import com.alexanderfinn.documentservice.metadata.exceptions.DocumentNotFoundException;

/**
 * @author Alexander Finn
 */
public interface DocumentRepository {

  DocumentMetadata get(String documentId, String documentKey) throws DocumentNotFoundException, DocumentNotAuthorizedException;

  DocumentMetadata create();

  void delete(String documentId, String documentKey) throws DocumentNotFoundException, DocumentNotAuthorizedException;

  void put(DocumentMetadata metadata) throws DocumentAlreadyExistsException;

}
