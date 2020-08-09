package image;

public class Edgy implements Filter
{
    public void filter(PixelImage pi)
    {
      Pixel[][] data = pi.getData();

      int [][] edgy = {{-1, -1, -1},
                       {-1,  9, -1},
                       {-1, -1, -1}};
      
      data = pi.weightedAverage(data, edgy, 1, true);

      pi.setData(data);
    }
}
