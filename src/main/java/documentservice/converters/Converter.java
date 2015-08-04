package documentservice.converters;

import documentservice.metadata.DocumentMetadata;

import java.util.Properties;

/**
 * @author Alexander Finn
 */
public interface Converter {

  String convert(DocumentMetadata metadata, ConverterConfig config, String accessKey) throws ConvertionException;

  void configure(String name, Properties settings);

  String getName();

}
