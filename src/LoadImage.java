import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class LoadImage extends Component {

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