import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class Filters {


    ////////////////////////////////  Basic Filter Kernel  ////////////////////////////////

    public static BufferedImage filterKernel(BufferedImage input, FilterKernel kernel) {
        ImageInfo info = new ImageInfo(input);
        byte[] data = new byte[info.pixels.length];

        for (int h = 0; h < info.height; h++) {
            for (int w = 0; w < info.width; w++) {
    
                for (int d = 0; d < info.pixelLength; d++) {
                    double sum = 0;
                    for (int b = 0; b < kernel.length(); b++) {  // b: kernel index h direction
                        for (int a = 0; a < kernel.length(); a++) {  // a: kernel index w direction
                            int x = w - kernel.length()/2 + a;  // pixel w direction
                            int y = h - kernel.length()/2 + b;  // pixel h direction
                            // Deal with edges
                            if (x < 0) {
                                x = - x - 1;
                            }
                            if (x >= info.width) {
                                x = info.width - (x - info.width) - 1;
                            }
                            if (y < 0) {
                                y = - y - 1;
                            }
                            if (y >= info.height) {
                                y = info.height - (y - info.height) - 1;
                            }
                            sum += byteToInt(info.pixels[pos(y, x, info) + d]) * kernel.get(a, b);
                        }
                    }
                    // sum = sum / divisor;
                    // sum += offset;
                    if (sum < 0) {
                        sum = 0;
                    }
                    if (sum > 255) {
                        sum = 255;
                    }
                    byte val = (byte)((int)sum);
                    data[pos(h, w, info) + d] = val;
                }
            }
        }

        BufferedImage ret = new BufferedImage(info.width, info.height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] retData = ((DataBufferByte)ret.getRaster().getDataBuffer()).getData();
        System.arraycopy(data, 0, retData, 0, data.length);

        return ret;
    }
    







    ////////////////////////////////  Filters  ////////////////////////////////


    // take the mean of RGB to calculate black and white value
    public static BufferedImage blackAndWhite(BufferedImage input) {
        ImageInfo info = new ImageInfo(input);

        byte[] data = new byte[info.pixels.length];
        for (int h = 0; h < info.height; h++) {
            for (int w = 0; w < info.width; w++) {
                int pos = pos(h, w, info);
                byte val = (byte) ((byteToInt(info.pixels[pos]) + byteToInt(info.pixels[pos+1]) + byteToInt(info.pixels[pos+2])) / 3);
                data[pos] = val;
                data[pos+1] = val;
                data[pos+2] = val;
            }
        }
        BufferedImage ret = new BufferedImage(info.width, info.height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] retData = ((DataBufferByte)ret.getRaster().getDataBuffer()).getData();
        // System.out.println(info.pixelLength);
        // System.out.println(data.length);
        // System.out.println(a.length);
        System.arraycopy(data, 0, retData, 0, data.length);

        return ret;
    }


    public static BufferedImage edge(BufferedImage input) {
        return filterKernel(input, FilterKernel.edgeDetectionKernel());

    }

    public static BufferedImage diff(BufferedImage input1, BufferedImage input2, int threshold) {
        ImageInfo info1 = new ImageInfo(input1);
        ImageInfo info2 = new ImageInfo(input2);
        if (info1.width != info2.width || info1.height != info2.height || info1.pixelLength != info2.pixelLength) {
            throw new IllegalStateException(info1.width + " " + info1.height + " " + info1.pixelLength + " // " + 
                                            info2.width + " " + info2.height + " " + info2.pixelLength);
        }



        byte[] data = new byte[info1.pixels.length];
        for (int h = 0; h < info1.height; h++) {
            for (int w = 0; w < info1.width; w++) {
                // center pixel
                int pos = pos(h, w, info1);
                int center1 = (byteToInt(info1.pixels[pos]) + byteToInt(info1.pixels[pos+1]) + byteToInt(info1.pixels[pos+2])) / 3;
                int center2 = (byteToInt(info2.pixels[pos]) + byteToInt(info2.pixels[pos+1]) + byteToInt(info2.pixels[pos+2])) / 3;

                if (h > 0) {
                    int pos2 = pos(h - 1, w, info1);
                    int up1 = (byteToInt(info1.pixels[pos2]) + byteToInt(info1.pixels[pos2+1]) + byteToInt(info1.pixels[pos2+2])) / 3;
                    int up2 = (byteToInt(info2.pixels[pos2]) + byteToInt(info2.pixels[pos2+1]) + byteToInt(info2.pixels[pos2+2])) / 3;
                    int diff = (center1 - up1) - (center2 - up2);
                    if (diff > threshold) {
                        data[pos] = (byte) 255;
                        data[pos+1] = (byte) 255;
                        data[pos+2] = (byte) 255;
                    }
                }

                if (w > 0) {
                    int pos2 = pos(h, w - 1, info1);
                    int up1 = (byteToInt(info1.pixels[pos2]) + byteToInt(info1.pixels[pos2+1]) + byteToInt(info1.pixels[pos2+2])) / 3;
                    int up2 = (byteToInt(info2.pixels[pos2]) + byteToInt(info2.pixels[pos2+1]) + byteToInt(info2.pixels[pos2+2])) / 3;
                    int diff = (center1 - up1) - (center2 - up2);
                    if (diff > threshold) {
                        data[pos] = (byte) 255;
                        data[pos+1] = (byte) 255;
                        data[pos+2] = (byte) 255;
                    }
                }

                if (h < info1.height-1) {
                    int pos2 = pos(h + 1, w, info1);
                    int up1 = (byteToInt(info1.pixels[pos2]) + byteToInt(info1.pixels[pos2+1]) + byteToInt(info1.pixels[pos2+2])) / 3;
                    int up2 = (byteToInt(info2.pixels[pos2]) + byteToInt(info2.pixels[pos2+1]) + byteToInt(info2.pixels[pos2+2])) / 3;
                    int diff = (center1 - up1) - (center2 - up2);
                    if (diff > threshold) {
                        data[pos] = (byte) 255;
                        data[pos+1] = (byte) 255;
                        data[pos+2] = (byte) 255;
                    }
                }

                if (w < info1.width-1) {
                    int pos2 = pos(h, w + 1, info1);
                    int up1 = (byteToInt(info1.pixels[pos2]) + byteToInt(info1.pixels[pos2+1]) + byteToInt(info1.pixels[pos2+2])) / 3;
                    int up2 = (byteToInt(info2.pixels[pos2]) + byteToInt(info2.pixels[pos2+1]) + byteToInt(info2.pixels[pos2+2])) / 3;
                    int diff = (center1 - up1) - (center2 - up2);
                    if (diff > threshold) {
                        data[pos] = (byte) 255;
                        data[pos+1] = (byte) 255;
                        data[pos+2] = (byte) 255;
                    }
                }




            }
        }
        BufferedImage ret = new BufferedImage(info1.width, info1.height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] retData = ((DataBufferByte)ret.getRaster().getDataBuffer()).getData();
        System.arraycopy(data, 0, retData, 0, data.length);

        return ret;
    }






    public static void gaussian() {



    }



    public static void bilateralMean() {

    }

    public static void bilateralGaussian() {

    }






    ////////////////////////////////  Utilities  ////////////////////////////////
    
    /*
        Return a int value of a byte (0-255), which solves the problem of sign/unsigned byte 
        resulting in negative number.
    */
    private static int byteToInt(byte b) {
        return ((int) b&0xff);
    }

    /* 
        Get the starting position (of BGR) of a pixel specified with (h, w)
    */
    private static int pos(int h, int w, ImageInfo info) {
        return h * info.width * info.pixelLength + w * info.pixelLength;
    }




}