package documentservice.converters;

import documentservice.metadata.DocumentMetadata;

import java.util.Properties;

/**
 * @author Alexander Finn
 */
public interface Converter {

  void convert(DocumentMetadata metadata, String accessKey);

  void configure(Properties settings);

}
