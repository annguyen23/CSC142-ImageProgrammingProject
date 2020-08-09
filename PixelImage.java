package image;

import java.awt.image.*;

/**
 * Provides an interface to a picture as an array of Pixels
 */
public class PixelImage
{
  private BufferedImage myImage;
  private int width;
  private int height;

  /**
   * Map this PixelImage to a real image
   * @param bi The image
   */
  public PixelImage(BufferedImage bi)
  {
    // initialise instance variables
    this.myImage = bi;
    this.width = bi.getWidth();
    this.height = bi.getHeight();
  }

  /**
   * Return the width of the image
   */
  public int getWidth()
  {
    return this.width;
  }

  /**
   * Return the height of the image
   */
  public int getHeight()
  {
    return this.height;
  }

  /**
   * Return the BufferedImage of this PixelImage
   */
  public BufferedImage getImage()
  {
    return this.myImage;
  }

  /**
   * Return the image's pixel data as an array of Pixels.  The
   * first coordinate is the x-coordinate, so the size of the
   * array is [width][height], where width and height are the
   * dimensions of the array
   * @return The array of pixels
   */
  public Pixel[][] getData()
  {
    Raster r = this.myImage.getRaster();
    Pixel[][] data = new Pixel[r.getHeight()][r.getWidth()];
    int[] samples = new int[3];

    for (int row = 0; row < r.getHeight(); row++)
    {
      for (int col = 0; col < r.getWidth(); col++)
      {
        samples = r.getPixel(col, row, samples);
        Pixel newPixel = new Pixel(samples[0], samples[1], samples[2]);
        data[row][col] = newPixel;
      }
    }

    return data;
  }

  /**
   * Set the image's pixel data from an array.  This array matches
   * that returned by getData().  It is an error to pass in an
   * array that does not match the image's dimensions or that
   * has pixels with invalid values (not 0-255)
   * @param data The array to pull from
   */
  public void setData(Pixel[][] data)
  {
    int[] pixelValues = new int[3];     // a temporary array to hold r,g,b values
    WritableRaster wr = this.myImage.getRaster();

    if (data.length != wr.getHeight())
    {
      throw new IllegalArgumentException("Array size does not match");
    }
    else if (data[0].length != wr.getWidth())
    {
      throw new IllegalArgumentException("Array size does not match");
    }

    for (int row = 0; row < wr.getHeight(); row++)
    {
      for (int col = 0; col < wr.getWidth(); col++)
      {
        pixelValues[0] = data[row][col].red;
        pixelValues[1] = data[row][col].green;
        pixelValues[2] = data[row][col].blue;
        wr.setPixel(col, row, pixelValues);
      }
    }
  }

  // add a method to compute a new image given weighted averages
  public Pixel[][] weightedAverage (Pixel[][] data, int[][] matrix, int scale, boolean check) {
      
      Pixel[][] newData = new Pixel [data.length][];
      
      for (int i = 0; i < data.length; i++) {
          newData[i] = new Pixel[data[i].length];
          
          for (int j = 0; j < data[i].length; j++) {
              newData[i][j] = data[i][j];
          }
      }
      
      for (int i = 1; i < data.length - 1; i++) {
          for (int j = 1; j < data[0].length - 1; j++) {
              
              newData[i][j] = new Pixel (0, 0, 0);
              
              for (int m = 0; m < 3; m++) {
                  for (int n = 0; n < 3; n++) {
                      newData[i][j].red += data[i + m - 1][j + n - 1].red * matrix[m][n];
                      newData[i][j].green += data[i + m - 1][j + n - 1].green*matrix[m][n];
                      newData[i][j].blue += data[i + m - 1][j + n - 1].blue*matrix[m][n];
                  }
              }
              if (scale != 1) {
                  newData[i][j].red /= scale;
                  newData[i][j].green /= scale;
                  newData[i][j].blue /= scale;
              }
              
              if (check) {
                  newData[i][j].red = checkValue(newData[i][j].red);
                  newData[i][j].green = checkValue(newData[i][j].green);
                  newData[i][j].blue = checkValue(newData[i][j].blue);
              }
          }
      }
      
      return newData;
      
  }
  
  // check if data.red, data.green and data.blue is in range 0-255
  //if value < 0, return 0
  //if value > 255; return 255
  //else return value
  private int checkValue (int value) {
      if (value < 0) {
          return 0;
      }else if (value > 255) {
          return 255;
      }
      return value;
  }
}
