package com.alexanderfinn.documentservice.api.v1;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import com.alexanderfinn.documentservice.configuration.Configuration;
import com.alexanderfinn.documentservice.metadata.DocumentMetadata;
import com.alexanderfinn.documentservice.utils.TokenGenerator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alexander Finn
 */
public class DocumentResourceTest extends JerseyTest {

	private String documentId1;
  private String accessKey1;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
    documentId1 = TokenGenerator.getUniqueToken(); accessKey1 = TokenGenerator.getUniqueToken();
		DocumentMetadata doc1 = new DocumentMetadata(documentId1, accessKey1);
		Configuration.getInstance().getDocumentRepository().put(doc1);
	}

	@Override
	protected Application configure() {
		return new ResourceConfig(DocumentResource.class);
	}

	@Test
	public void testGetDocument() {
		final Response response = target("v1/documents/" + documentId1).request().header("Access-Key", accessKey1).get();
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testCreateDocument() {
		final Response response = target("v1/documents").request().put(Entity.json(""));
		assertEquals(200, response.getStatus());
    CreateDocumentResponse createDocumentResponse = response.readEntity(CreateDocumentResponse.class);
    assertNotNull(createDocumentResponse.getDocumentId());
	}

}
