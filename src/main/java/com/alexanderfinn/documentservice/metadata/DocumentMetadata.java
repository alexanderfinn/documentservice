package com.alexanderfinn.documentservice.metadata;

/**
 * @author Alexander Finn
 */
public class DocumentMetadata {

  private final String documentId;

  private final String documentKey;

  public DocumentMetadata(String documentId, String documentKey) {
    this.documentId = documentId;
    this.documentKey = documentKey;
  }

  public DocumentMetadata() {
    this(generateId(), "");
  }

  private static String generateId() {
    return "123";
  }

  public String getDocumentId() {
    return documentId;
  }

  public boolean validateKey(String documentKey) {
    return this.documentKey.equals(documentKey);
  }
}
