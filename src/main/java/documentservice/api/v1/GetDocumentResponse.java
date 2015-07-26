package documentservice.api.v1;

import documentservice.metadata.DocumentMetadata;

/**
 * @author Alexander Finn
 */
public class GetDocumentResponse extends APIResponse {

  private final DocumentMetadata documentMetadata;

  public GetDocumentResponse(DocumentMetadata documentMetadata) {
    this.documentMetadata = documentMetadata;
  }

  public GetDocumentResponse() {
    documentMetadata = null;
  }

  public DocumentMetadata getDocumentMetadata() {
    return documentMetadata;
  }
}
