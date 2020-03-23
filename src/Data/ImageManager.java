package Data;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {
	public static BufferedImage LoadImage(String filePath) throws IOException
	{
		return ImageIO.read(new File(filePath));
	}
	
	public static BufferedImage LoadImage(String filePath, int scaleWidth, int scaleHeight) throws IOException
	{
		//System.out.println(filePath);
		return createResizedCopy(ImageIO.read(new File(filePath)), scaleWidth, scaleHeight, false);
	}
	
	static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha)
    {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
        g.dispose();
        return scaledBI;
    }
}
