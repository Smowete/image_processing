import javax.swing.*;
import java.awt.*;



public class AppFrame {

    private static final String TITLE = "Impressionist";

    private JFrame appFrame;
    

    public AppFrame() {
        this.appFrame = new JFrame(TITLE);
        appFrame.setPreferredSize(new Dimension(1000, 800));





        appFrame.pack();
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appFrame.setVisible(true);

    }
    




}


