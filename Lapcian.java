package image;

public class Lapcian implements Filter
{
    public void filter(PixelImage pi)
    {
      Pixel[][] data = pi.getData();

      int [][] lapcian = {{-1, -1, -1},
                          {-1,  8, -1},
                          {-1, -1, -1}};
      
      data = pi.weightedAverage(data, lapcian, 1, true);

      pi.setData(data);
    }
}
