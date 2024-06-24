package Lab4;
import java.awt.*;

/**
 * Просто для инициализации и открытия главного окна программы.
 */
public class L4MainPoint {
    public static void main(String[] vars){
        EventQueue.invokeLater(() -> {
            var ex = new L4Frame();
            ex.setVisible(true);
        });
    }
}
