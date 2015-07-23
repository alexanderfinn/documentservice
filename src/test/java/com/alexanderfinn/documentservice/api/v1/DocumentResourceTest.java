package com.alexanderfinn.documentservice.api.v1;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import com.alexanderfinn.documentservice.metadata.DocumentMetadata;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

/**
 * @author Alexander Finn
 */
public class DocumentResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(DocumentResource.class);
	}

	@Test
	public void testGetDocument() {
		final Response response = target("v1/documents/test").request().get();
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testCreateDocument() {
		final Response response = target("v1/documents").request().put(Entity.json(""));
		assertEquals(200, response.getStatus());
		DocumentMetadata md = response.readEntity(DocumentMetadata.class);
		assertNotNull(md.getDocumentId());
	}

}
