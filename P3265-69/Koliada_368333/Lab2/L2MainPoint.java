package Lab2;
import java.awt.*;

/**
 * Запускает окно программы
 */
public class L2MainPoint {
    public static void main(String[] vars){
        EventQueue.invokeLater(() -> {
            var ex = new L2Frame();
            ex.setVisible(true);
        });
    }
}
