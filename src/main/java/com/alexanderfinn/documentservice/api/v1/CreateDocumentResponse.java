package com.alexanderfinn.documentservice.api.v1;

/**
 * @author Alexander Finn
 */
public class CreateDocumentResponse extends APIResponse {

  private String documentId;

  private String accessKey;

  public CreateDocumentResponse() {
  }

  public CreateDocumentResponse(String documentId, String accessKey) {
    this.documentId = documentId;
    this.accessKey = accessKey;
  }

  public String getDocumentId() {
    return documentId;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }
}
