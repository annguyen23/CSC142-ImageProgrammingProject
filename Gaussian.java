package image;

public class Gaussian implements Filter
{
    public void filter(PixelImage pi)
    {
      Pixel[][] data = pi.getData();

      int [][] gaussian = {{1, 2, 1},
                           {2, 4, 2},
                           {1, 2, 1}};
      
      data = pi.weightedAverage(data, gaussian, 16, false);

      pi.setData(data);
    }
}
