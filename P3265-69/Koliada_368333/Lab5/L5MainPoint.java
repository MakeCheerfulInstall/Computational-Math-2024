package Lab5;

import java.awt.*;

/**
 * Просто для инициализации и открытия главного окна программы.
 */
public class L5MainPoint {
    public static void main(String[] vars){
        EventQueue.invokeLater(() -> {
            var ex = new L5Frame();
            ex.setVisible(true);
        });
    }
}
