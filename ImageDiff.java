import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class ImageDiff {
    public static void main(String[] args) {
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        try {
            image1 = ImageIO.read(new File("a.jpg"));
            image2 = ImageIO.read(new File("b.jpg"));
        } catch (IOException e) {
            System.out.println("IOException");
            return;
        }

        ImageInfo info1 = new ImageInfo(image1);
        ImageInfo info2 = new ImageInfo(image2);
        if (info1.width != info2.width || info1.height != info2.height || info1.pixelLength != info2.pixelLength) {
            throw new IllegalStateException(info1.width + " " + info1.height + " " + info1.pixelLength + " // " + 
                                            info2.width + " " + info2.height + " " + info2.pixelLength);
        }

        System.out.println(info1.pixels.length);

        byte[] data = new byte[info1.pixels.length];
        int pos = 0;
        for (int h = 0; h < info1.height; h++) {
            for (int w = 0; w < info1.width; w++) {
                byte val = (byte) ((byteToInt(info1.pixels[pos]) + byteToInt(info1.pixels[pos+1]) + byteToInt(info1.pixels[pos+2])) / 3);
                data[pos] = val;
                data[pos+1] = val;
                data[pos+2] = val;
                pos += 3;
            }
        }

        BufferedImage bi = new BufferedImage(info1.width, info1.height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] a = ((DataBufferByte)bi.getRaster().getDataBuffer()).getData();
        System.out.println(info1.pixelLength);
        System.out.println(data.length);
        System.out.println(a.length);
        System.arraycopy(data, 0, a, 0, data.length);


        JFrame f = new JFrame("Load Image Sample");   
        f.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

        f.add(new LoadImage(bi));
        f.pack();
        f.setVisible(true);

    
    }

    private static int byteToInt(byte b) {
        return ((int) b&0xff);
    }



    


}


class ImageInfo {
    public byte[] pixels;
    public int width;
    public int height;
    public int pixelLength;

    public ImageInfo(BufferedImage image) {
        pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        width = image.getWidth();
        height = image.getHeight();
        boolean hasAlphaChannel = image.getAlphaRaster() != null;
        pixelLength = 3;
        if (hasAlphaChannel) {
            pixelLength = 4;
        }
    }
}



class LoadImage extends Component {

    BufferedImage img;

    public LoadImage() {
       try {
           img = ImageIO.read(new File("lyuh.jpg"));
       } catch (IOException e) {
       }
    }

    public LoadImage(String fileName) {
       try {
           img = ImageIO.read(new File(fileName));
       } catch (IOException e) {
       }
    }

    public LoadImage(BufferedImage img) {
       this.img = img;
    }


    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }


}