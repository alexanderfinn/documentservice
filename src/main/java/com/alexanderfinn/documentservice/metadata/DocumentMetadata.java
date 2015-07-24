package com.alexanderfinn.documentservice.metadata;

/**
 * @author Alexander Finn
 */
public class DocumentMetadata {

  private final String documentId;

  private final String accessKey;

  public DocumentMetadata(String documentId, String accessKey) {
    this.documentId = documentId;
    this.accessKey = accessKey;
  }

  public String getDocumentId() {
    return documentId;
  }

  public boolean validateKey(String accessKey) {
    return this.accessKey.equals(accessKey);
  }

}
