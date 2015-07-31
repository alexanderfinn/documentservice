package documentservice.utils;

import junit.framework.TestCase;

/**
 * @author Alexander Finn
 */
public class FileUtilsTest extends TestCase {

  public void testGetFileExtension() throws Exception {
    assertEquals("txt", FileUtils.getFileExtension("test.txt"));
    assertEquals("txt", FileUtils.getFileExtension("test.test.txt"));
    assertEquals("", FileUtils.getFileExtension("test"));
    assertEquals("", FileUtils.getFileExtension("test."));
    assertEquals("", FileUtils.getFileExtension(""));
  }
}