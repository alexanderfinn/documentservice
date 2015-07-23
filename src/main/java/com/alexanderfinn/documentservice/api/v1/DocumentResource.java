package com.alexanderfinn.documentservice.api.v1;

import com.alexanderfinn.documentservice.metadata.DocumentMetadata;

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
  public DocumentMetadata createDocument() {
    return new DocumentMetadata();
  }

  @GET
  @Path("/{documentId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDocument(@PathParam("documentId") String documentId, @HeaderParam("documentKey") String documentKey) {
    return Response.ok().build();
  }

}
