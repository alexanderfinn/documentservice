package documentservice.api.v1;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import documentservice.configuration.Configuration;
import documentservice.metadata.DocumentMetadata;
import documentservice.utils.TokenGenerator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

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
    Properties settings = new Properties();
    settings.load(this.getClass().getResourceAsStream("test_bootstrap.properties"));
    Configuration.initInstance(settings);
		Configuration.getInstance().getDocumentRepository().put(doc1);
	}

	@Override
	protected Application configure() {
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    ResourceConfig resourceConfig = new ResourceConfig(DocumentResource.class);
    resourceConfig.register(MultiPartFeature.class);
    return resourceConfig;
	}

  @Override
  protected void configureClient(ClientConfig clientConfig) {
    clientConfig.register(MultiPartFeature.class);
  }

  @Test
	public void testGetDocument() {
		final Response response = target("v1/documents/" + documentId1).request().header("Access-Key", accessKey1).get();
    assertEquals(200, response.getStatus());
    GetDocumentResponse entity = response.readEntity(GetDocumentResponse.class);
    assertNotNull(entity.getDocumentMetadata());
	}

	@Test
	public void testCreateDocument() {
		final Response response = target("v1/documents").request().put(Entity.json(""));
		assertEquals(200, response.getStatus());
    CreateDocumentResponse createDocumentResponse = response.readEntity(CreateDocumentResponse.class);
    assertNotNull(createDocumentResponse.getDocumentId());
	}

  @Test
  public void testUpload() {
    GetDocumentResponse getDocumentResponse = target("v1/documents/" + documentId1).request().header("Access-Key", accessKey1).get(GetDocumentResponse.class);
    assertEquals(DocumentMetadata.UPLOAD_STATUS_NOT_STARTED, getDocumentResponse.getDocumentMetadata().getUploadStatus());

    File testFile = new File(this.getClass().getResource("testfile.txt").getFile());
    FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", testFile, MediaType.TEXT_PLAIN_TYPE);
    MultiPart entity = new FormDataMultiPart().bodyPart(fileDataBodyPart);
    UploadResponse response = target("v1/documents/" + documentId1).request().header("Access-Key", accessKey1).post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE), UploadResponse.class);
    assertEquals(testFile.length(), response.getUploadedSize());

    getDocumentResponse = target("v1/documents/" + documentId1).request().header("Access-Key", accessKey1).get(GetDocumentResponse.class);
    assertEquals(DocumentMetadata.UPLOAD_STATUS_COMPLETED, getDocumentResponse.getDocumentMetadata().getUploadStatus());
  }

}
