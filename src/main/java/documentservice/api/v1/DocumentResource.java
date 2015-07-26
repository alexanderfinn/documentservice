package documentservice.api.v1;

import documentservice.blobstore.BlobStore;
import documentservice.configuration.Configuration;
import documentservice.metadata.DocumentMetadata;
import documentservice.metadata.DocumentRepository;
import documentservice.metadata.FileMetadata;
import documentservice.metadata.exceptions.DocumentNotAuthorizedException;
import documentservice.metadata.exceptions.DocumentNotFoundException;
import documentservice.utils.TokenGenerator;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

  private BlobStore getBlobStore() {
    return Configuration.getInstance().getBlobStore();
  }

  @GET
  @Path("/{documentId}")
  @Produces(MediaType.APPLICATION_JSON)
  public GetDocumentResponse getDocument(@PathParam("documentId") String documentId,
                                         @HeaderParam("Access-Key") String accessKey) {
      return new GetDocumentResponse(getDocumentMetadta(documentId, accessKey));
  }

  @POST
  @Path("/{documentId}")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public UploadResponse upload(@FormDataParam("file") InputStream is,
                               @FormDataParam("file") FormDataContentDisposition header,
                               @PathParam("documentId") String documentId,
                               @HeaderParam("Access-Key") String accessKey) {

    DocumentMetadata metadata = getDocumentMetadta(documentId, accessKey);
    String fileId = metadata.createFile(FileMetadata.FILE_TYPE_ORIGINAL);
    UploadResponse uploadResponse = new UploadResponse();
    try {
      long saved = getBlobStore().store(metadata, fileId, is);
      uploadResponse.setUploadedSize(saved);
    } catch (IOException e) {
      // Throw exception!
    }
    return uploadResponse;
  }

  private DocumentMetadata getDocumentMetadta(@PathParam("documentId") String documentId, @HeaderParam("Access-Key") String accessKey) {
    try {
      return getRepository().get(documentId, accessKey);
    } catch (DocumentNotFoundException e) {
      throw new NotFoundException();
    } catch (DocumentNotAuthorizedException e) {
      throw new NotAuthorizedException(Response.status(401).build());
    }
  }

}
