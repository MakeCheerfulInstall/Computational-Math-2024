package lab2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SystemFrame extends JFrame {
  public SystemFrame(String fileName) {
    setTitle("SystemGraph");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel imageLabel = new JLabel();;
    try {
      BufferedImage image = ImageIO.read(new File(fileName));
      imageLabel.setIcon(new ImageIcon(image));
    } catch (IOException e) {
      System.exit(-1);
    }

    JPanel panel = new JPanel(new GridLayout(1, 2));
    panel.add(imageLabel);

    getContentPane().add(panel);

    pack();
    setLocationRelativeTo(null);
  }
}
