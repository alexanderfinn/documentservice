package documentservice.api.v1;

import documentservice.configuration.Configuration;
import documentservice.converters.ConverterConfig;
import documentservice.converters.ConvertionException;
import documentservice.metadata.DocumentMetadata;
import documentservice.metadata.DocumentRepository;
import documentservice.metadata.exceptions.DocumentNotAuthorizedException;
import documentservice.metadata.exceptions.DocumentNotFoundException;
import documentservice.utils.FileUpload;
import documentservice.utils.TokenGenerator;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alexander Finn
 * @version 1.0
 */
@Path("v1/documents")
public class DocumentResource {

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response createDocument() {
    String accessKey = TokenGenerator.getUniqueToken();
    DocumentMetadata metadata = new DocumentMetadata(TokenGenerator.getUniqueToken(), accessKey);
    try {
      getRepository().put(metadata, accessKey);
    } catch (DocumentNotAuthorizedException e) {
      throw new NotAuthorizedException(Response.status(401).build());
    }
    return Response.ok(new CreateDocumentResponse(metadata.getDocumentId(), accessKey)).build();
  }

  private DocumentRepository getRepository() {
    return Configuration.getInstance().getDocumentRepository();
  }

  @GET
  @Path("/{documentId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDocument(@PathParam("documentId") String documentId,
                                         @HeaderParam("Access-Key") String accessKey) {
      return Response.ok(new GetDocumentResponse(getDocumentMetadata(documentId, accessKey))).build();
  }

  @POST
  @Path("/{documentId}")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response upload(@FormDataParam("file") InputStream is,
                               @FormDataParam("file") FormDataContentDisposition header,
                               @PathParam("documentId") String documentId,
                               @HeaderParam("Access-Key") String accessKey) {

    DocumentMetadata metadata = getDocumentMetadata(documentId, accessKey);
    UploadResponse uploadResponse = new UploadResponse();
    try {
      long saved = new FileUpload(metadata, header.getFileName()).upload(is, accessKey);
      uploadResponse.setUploadedSize(saved);
    } catch (IOException e) {
      Response.serverError().build();
    } catch (DocumentNotAuthorizedException e) {
      throw new NotAuthorizedException(Response.status(401).build());
    }
    return Response.ok(uploadResponse).build();
  }

  @POST
  @Path("/{documentId}/convert")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response convert(ConverterConfig converterConfig,
                          @PathParam("documentId") String documentId,
                          @HeaderParam("Access-Key") String accessKey) {
    DocumentMetadata metadata = getDocumentMetadata(documentId, accessKey);
    ConvertResponse response = new ConvertResponse();
    try {
      response.setProcessingStatus(Configuration.getInstance().getConverterFactory().getConverter(converterConfig).convert(metadata, converterConfig, accessKey));
      return Response.ok(response).build();
    } catch (ConvertionException e) {
      response.setProcessingStatus(DocumentMetadata.CONVERTER_STATUS_FAILED);
      return Response.serverError().entity(response).build();
    }
  }

  private DocumentMetadata getDocumentMetadata(String documentId, String accessKey) {
    try {
      return getRepository().get(documentId, accessKey);
    } catch (DocumentNotFoundException e) {
      throw new NotFoundException();
    } catch (DocumentNotAuthorizedException e) {
      throw new NotAuthorizedException(Response.status(401).build());
    }
  }

}
