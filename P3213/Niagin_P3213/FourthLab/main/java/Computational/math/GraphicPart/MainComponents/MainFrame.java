package Computational.math.GraphicPart.MainComponents;

import Computational.math.GraphicPart.FunctionsComponents.CasualFunction.DrawFunction;
import Computational.math.GraphicPart.FunctionsComponents.SystemFunctions.SystemFunctionPainterComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

public class MainFrame extends JFrame {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private MainFrame(String name){
        super(name);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH,HEIGHT));
    }
    private static MainFrame getFrame(String titleOfFrame){
        MainFrame frame = new MainFrame(titleOfFrame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(1000,1000));
        return frame;
    }
    public static void drawSingleFunction(String methodName, Function<Double,Double> f){
        EventQueue.invokeLater(() -> {

            MainFrame frame = MainFrame.getFrame(methodName);

            // Создаем Axes и FirstFunctionComponent
            Axes axes = new Axes();
            DrawFunction firstFunctionComponent = new DrawFunction(f);

            // Создаем панель для компонентов, используя OverlayLayout
            JPanel overlayPanel = new JPanel();
            overlayPanel.setLayout(new OverlayLayout(overlayPanel));

            // Добавляем компоненты на панель
            overlayPanel.add(axes);
            overlayPanel.add(firstFunctionComponent);

            // Устанавливаем панель с компонентами в центр окна
            frame.add(overlayPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
    public static void drawSystem(String methodName, ArrayList<Function<Double,Double>> listOfFunctions){
        EventQueue.invokeLater(()->{
            MainFrame frame = MainFrame.getFrame(methodName);
            // Создаем Axes и SystemFunctionPainterComponent
            Axes axes = new Axes();
            SystemFunctionPainterComponent systemDrawer = new SystemFunctionPainterComponent(listOfFunctions);

            // Создаем панель для компонентов, используя OverlayLayout
            JPanel overlayPanel = new JPanel();
            overlayPanel.setLayout(new OverlayLayout(overlayPanel));

            // Добавляем компоненты на панель
            overlayPanel.add(axes);
            overlayPanel.add(systemDrawer);

            // Устанавливаем панель с компонентами в центр окна
            frame.add(overlayPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

}
