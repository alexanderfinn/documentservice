package documentservice.utils;

/**
 * @author Alexander Finn
 */
public class FileUtils {

  public static String getFileExtension(String filename) {
    if (filename.contains(".")) {
      return filename.substring(filename.lastIndexOf(".") + 1);
    }
    return "";
  }

}
