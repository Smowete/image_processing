import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class Filters {
    
    private static int byteToInt(byte b) {
        return ((int) b&0xff);
    }

    // take the mean of RGB to calculate black and white value
    public static BufferedImage blackAndWhite(BufferedImage input) {
        ImageInfo info = new ImageInfo(input);

        byte[] data = new byte[info.pixels.length];
        int pos = 0;
        for (int h = 0; h < info.height; h++) {
            for (int w = 0; w < info.width; w++) {
                byte val = (byte) ((byteToInt(info.pixels[pos]) + byteToInt(info.pixels[pos+1]) + byteToInt(info.pixels[pos+2])) / 3);
                data[pos] = val;
                data[pos+1] = val;
                data[pos+2] = val;
                pos += 3;
            }
        }
        BufferedImage ret = new BufferedImage(info.width, info.height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] a = ((DataBufferByte)ret.getRaster().getDataBuffer()).getData();
        // System.out.println(info.pixelLength);
        // System.out.println(data.length);
        // System.out.println(a.length);
        System.arraycopy(data, 0, a, 0, data.length);

        return ret;
    }

    public static void gaussian() {



    }



    public static void bilateralMean() {

    }

    public static void bilateralGaussian() {

    }


}