package documentservice.converters;

import documentservice.metadata.DocumentMetadata;

import java.util.Properties;

/**
 * @author Alexander Finn
 */
public class ImageConverter implements Converter {
  @Override
  public String convert(DocumentMetadata metadata, String accessKey) {
    return DocumentMetadata.CONVERTER_STATUS_COMPLETED;
  }

  @Override
  public void configure(String name, Properties settings) {

  }

  @Override
  public String getName() {
    return null;
  }
}
