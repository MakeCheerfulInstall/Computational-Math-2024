package Lab3;

import java.awt.*;

/**
 * Просто для инициализации и открытия главного окна программы.
 */
public class L3MainPoint {
    public static void main(String[] vars){
        EventQueue.invokeLater(() -> {
            var ex = new L3Frame();
            ex.setVisible(true);
        });
    }
}
