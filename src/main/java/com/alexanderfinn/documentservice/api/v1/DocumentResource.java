package com.alexanderfinn.documentservice.api.v1;

import com.alexanderfinn.documentservice.configuration.Configuration;
import com.alexanderfinn.documentservice.metadata.DocumentMetadata;
import com.alexanderfinn.documentservice.metadata.DocumentRepository;
import com.alexanderfinn.documentservice.metadata.exceptions.DocumentAlreadyExistsException;
import com.alexanderfinn.documentservice.metadata.exceptions.DocumentNotAuthorizedException;
import com.alexanderfinn.documentservice.metadata.exceptions.DocumentNotFoundException;
import com.alexanderfinn.documentservice.utils.TokenGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Alexander Finn
 * @version 1.0
 */
@Path("v1/documents")
public class DocumentResource {

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public CreateDocumentResponse createDocument() {
    String accessKey = TokenGenerator.getUniqueToken();
    DocumentMetadata metadata = new DocumentMetadata(TokenGenerator.getUniqueToken(), accessKey);
    getRepository().put(metadata);
    return new CreateDocumentResponse(metadata.getDocumentId(), accessKey);
  }

  private DocumentRepository getRepository() {
    return Configuration.getInstance().getDocumentRepository();
  }

  @GET
  @Path("/{documentId}")
  @Produces(MediaType.APPLICATION_JSON)
  public GetDocumentResponse getDocument(@PathParam("documentId") String documentId, @HeaderParam("Access-Key") String accessKey) {
    try {
      return new GetDocumentResponse(getRepository().get(documentId, accessKey));
    } catch (DocumentNotFoundException e) {
      throw new NotFoundException();
    } catch (DocumentNotAuthorizedException e) {
      throw new NotAuthorizedException(Response.status(401).build());
    }
  }

}
