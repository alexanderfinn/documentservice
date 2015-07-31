package documentservice.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Alexander Finn
 */
public class ConverterFactory {

  private final Map<String, Converter> converters = new HashMap<>();

  private final Logger logger = LoggerFactory.getLogger(ConverterFactory.class);

  public void configure(Properties settings) {
    for (String name: getConverterNamesList(settings)) {
      try {
        Converter converter = (Converter) Class.forName(settings.getProperty("converter." + name)).newInstance();
        converter.configure(settings);
        converters.put(name, converter);
      } catch (Exception e) {
        logger.error("Failed to load converter " + name + ": " + e);
      }
    }
  }

  public List<String> getConverterNamesList(Properties settings) {
    List<String> names = new ArrayList<>();
    for (String name: settings.stringPropertyNames()) {
      if (name.matches("converter\\.(\\w+)")) {
        names.add(name.substring(name.indexOf(".")+1));
      }
    }
    return names;
  }

  public Converter getConverter(ConverterConfig config) {
    return converters.get(config.getConverterName());
  }
}
