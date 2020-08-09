package image;

public class UnsharpMasking implements Filter
{
    public void filter(PixelImage pi)
    {
      Pixel[][] data = pi.getData();

      int [][] unsharpMasking = {{-1, -2, -1},
                                 {-2, 28, -2},
                                 {-1, -2, -1}};
      
      data = pi.weightedAverage(data, unsharpMasking, 16, true);

      pi.setData(data);
    }
}