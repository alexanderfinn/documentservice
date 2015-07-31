package documentservice.converters;

import documentservice.metadata.DocumentMetadata;
import junit.framework.TestCase;

import java.util.List;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public class ConverterFactoryTest extends TestCase {

  public class TestConverter implements Converter {

    @Override
    public void convert(DocumentMetadata metadata, String accessKey) {

    }

    @Override
    public void configure(Properties settings) {

    }
  }

  public void testGetConverterNamesList() throws Exception {

    Properties settings = new Properties();
    settings.setProperty("converter.1", "documentservice.converters.ConverterFactoryTest.TestConverter");
    settings.setProperty("converter.test", "documentservice.converters.ConverterFactoryTest.TestConverter");
    settings.setProperty("converter.test.notworking", "documentservice.converters.ConverterFactoryTest.TestConverter");
    settings.setProperty("converter.", "documentservice.converters.ConverterFactoryTest.TestConverter");
    ConverterFactory factory = new ConverterFactory(settings);
    List<String> names = factory.getConverterNamesList(settings);
    assertEquals(2, names.size());
    assertTrue(names.contains("1"));
    assertTrue(names.contains("test"));

  }
}