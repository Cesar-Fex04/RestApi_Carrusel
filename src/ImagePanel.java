import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;


public class ImagePanel extends JPanel {
    Logger logger = Logger.getLogger(ImagePanel.class.getName());

    private ImageIcon icon;
    private MediaTracker tracker;
    private BufferedImage image;

    public ImagePanel(URL imgUrl) {
        super();
        tracker = new MediaTracker(this);
        loadImage(imgUrl);
        initPanel();


    }

    private void loadImage(URL imgUrl){
        try{
            logger.info("Loading image...");
            image = ImageIO.read(imgUrl);
            tracker.addImage(image,1);
            icon = new ImageIcon(image);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void initPanel(){
        try{
            tracker.waitForID(1);
            logger.info("Image OK");

        } catch (InterruptedException e) {
            logger.severe(e.getMessage());
        }
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        setPreferredSize(new Dimension(w,h));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if((tracker.statusAll(false) & MediaTracker.ERRORED) != 0){
            logger.info("Waiting for image");
            g.setColor(Color.red);
            g.fillRect(0,0,size().width, size().height);
            return;

        }
        g.drawImage(image, 0,0,this);

    }









}
