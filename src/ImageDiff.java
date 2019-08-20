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
            image1 = ImageIO.read(new File("../img/ferry.bmp"));
            image2 = ImageIO.read(new File("../img/b.jpg"));
        } catch (IOException e) {
            System.out.println("IOException");
            return;
        }




        // BufferedImage bi = Filters.blackAndWhite(image1);
        // BufferedImage bi = Filters.diff(image1, image2, 20);
        BufferedImage bi = Filters.edge(image1);

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




}




