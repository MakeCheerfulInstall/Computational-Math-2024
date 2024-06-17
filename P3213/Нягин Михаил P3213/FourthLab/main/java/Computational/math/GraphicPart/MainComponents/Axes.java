package Computational.math.GraphicPart.MainComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Axes extends JComponent {
    public static final int NUM_TICKS_X = 50;
    private static final int NUM_TICKS_Y = 50;
    private static final int TICK_LENGTH = 5;
//    public Axes(){
//        setPreferredSize(new Dimension(1000,1000));
//    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;

        // Найти центр экрана
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Отрисовка осей
        gr.drawLine(0, centerY, getWidth(), centerY); // Ось абсцисс
        gr.drawString("y", centerX + (int) (getWidth() * 0.01), (int) (getHeight() * 0.01));
        gr.drawLine(centerX, 0, centerX, getHeight()); // Ось ординат
        gr.drawString("x", centerX + (int) (getWidth()/2 - getWidth()/2 * 0.01), centerY - (int)(getHeight()*0.01));



        int xTickSpacing = getWidth() / NUM_TICKS_X;

        //positive ticks
        for (int i = 0; i < NUM_TICKS_X/2; i++){
            int xTick = centerX+i*xTickSpacing;
            gr.drawLine(xTick, centerY - TICK_LENGTH / 2, xTick, centerY + TICK_LENGTH / 2);
            if(i != 0)
                gr.drawString(""+i,xTick, centerY - TICK_LENGTH / 2);
        }
        //negative ticks
        for (int i = 0; i < NUM_TICKS_X/2; i++) {
            int negXTick = centerX - i * xTickSpacing;
            gr.drawLine(negXTick, centerY - TICK_LENGTH / 2, negXTick, centerY + TICK_LENGTH / 2);
            if(i != 0)
                gr.drawString(""+-i,negXTick, centerY - TICK_LENGTH / 2);

        }
        //y ticks 1. positive  2. negative

        int yTickSpacing = getHeight() / NUM_TICKS_Y;

        for (int i = 0; i < NUM_TICKS_Y/2; i++) {
            int yTick = centerY - i * yTickSpacing;
            gr.drawLine(centerX - TICK_LENGTH / 2, yTick, centerX + TICK_LENGTH / 2, yTick);
            gr.drawString(""+i,centerX - TICK_LENGTH / 2 + TICK_LENGTH, yTick);
        }

        for (int i = 0; i < NUM_TICKS_Y/2; i++) {
            int yTick = centerY + i * yTickSpacing;
            gr.drawLine(centerX - TICK_LENGTH / 2, yTick, centerX + TICK_LENGTH / 2, yTick);
            gr.drawString(""+-i,centerX - TICK_LENGTH / 2 + TICK_LENGTH, yTick);

        }
    }

}
