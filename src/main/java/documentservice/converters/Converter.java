package documentservice.converters;

import documentservice.metadata.DocumentMetadata;

import java.util.Properties;

/**
 * @author Alexander Finn
 */
public interface Converter {

  String convert(DocumentMetadata metadata, String accessKey);

  void configure(String name, Properties settings);

  String getName();

}
