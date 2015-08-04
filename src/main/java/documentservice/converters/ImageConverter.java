package documentservice.converters;

import documentservice.metadata.DocumentMetadata;
import documentservice.metadata.FileMetadata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Alexander Finn
 */
public class ImageConverter extends BaseConverter implements Converter {

  private static final String DEFAULT_TARGET_WIDTH = "320";
  private static final String DEFAULT_TARGET_HEIGHT = "240";
  private static final String DEFAULT_TARGET_QUALITY = "100";
  private static final String DEFAULT_TARGET_FORMAT = "jpg";
  private String name;
  private int targetWidth;
  private int targetHeight;
  private int targetQuality;
  private String targetFormat;

  @Override
  public String convert(DocumentMetadata metadata, ConverterConfig config, String accessKey) throws ConvertionException {
    try {
      FileMetadata fm = metadata.createFile(FileMetadata.FILE_TYPE_DERIVATIVE);
      fm.setFileExtension(targetFormat);
      BufferedImage original = readImage(getOriginalFileStream(metadata));
      writeImage(getResizedCopy(original, targetWidth, targetHeight, true), getTargetOutputStream(metadata, fm.getFileId()));
      metadata.setProcessingStatus(name, DocumentMetadata.CONVERTER_STATUS_COMPLETED);
      saveMetadata(metadata, accessKey);
    } catch (Exception e) {
      throw new ConvertionException("Failed to convert document " + metadata.getDocumentId() + ": " + e);
    }
    return DocumentMetadata.CONVERTER_STATUS_COMPLETED;
  }

  public BufferedImage getResizedCopy(BufferedImage originalImage, int widthMax, int heightMax, boolean preserveAlpha) {

    int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;


    double origWidth = originalImage.getWidth();
    double origHeight = originalImage.getHeight();

    int scaledWidth = widthMax;
    int scaledHeight = heightMax;

    if (widthMax * origHeight / origWidth <= heightMax) {
      scaledHeight = (int) Math.round(widthMax * (origHeight / origWidth));
    } else {
      scaledWidth = (int) Math.round(heightMax * origWidth / origHeight);
    }

    BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, imageType);
    Graphics2D g = scaledImage.createGraphics();
    if (preserveAlpha) {
      g.setComposite(AlphaComposite.Src);
    }

    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);


    g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
    g.dispose();

    return scaledImage;
  }

  public BufferedImage readImage(InputStream imageStream) throws IOException, IllegalArgumentException {
    return ImageIO.read(imageStream);
  }

  public void writeImage(BufferedImage image, OutputStream outputStream) throws IOException {
    ImageIO.write(image, targetFormat, outputStream);
  }

  @Override
  public void configure(String name, Properties settings) {
    this.name = name;
    targetWidth = Integer.valueOf(settings.getProperty("targetWidth", DEFAULT_TARGET_WIDTH));
    targetHeight = Integer.valueOf(settings.getProperty("targetHeight", DEFAULT_TARGET_HEIGHT));
    targetQuality = Integer.valueOf(settings.getProperty("targetQuality", DEFAULT_TARGET_QUALITY));
    targetFormat = settings.getProperty("targetFormat", DEFAULT_TARGET_FORMAT);
  }

  @Override
  public String getName() {
    return name;
  }
}
