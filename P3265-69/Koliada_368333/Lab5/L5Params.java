package Lab5;

import Lab2.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Панель параметров
 */
public class L5Params {
    public JPanel paramsPanel;
    /**
     * Комбобокс для выбора уравнения
     */
    private JComboBox funCombo;
    /**
     * Левая граница интервала для определения корня
     */
    private JFormattedTextField xa;
    /**
     * Правая граница интервала для определения корня
     */
    private JFormattedTextField xb;
    /**
     * Количество точек разбиения интелвала
     */
    private JFormattedTextField dotsNumber;
    /**
     * Кнопка очистить таблицу координат
     */
    private JButton cleanUpXY;
    /**
     * Кнопка нарисовать график уравнения
     */
    private JButton fillTableXY;
    /**
     * Комбобокс для выбора метода решения уравнения
     */
    private JComboBox comboMethods;
    /**
     * Кнопка для отображения графиков системы уравнений
     */
    private JButton btnCalcByAllMethods;
    /**
     * Левая граница интервала решений для x1 (x)
     */
    private JFormattedTextField epsilon;
    /**
     * Кнопка для запуска решения системы уравнения
     */
    private JButton btnCalcSelectedMethod;
    /**
     * Калькулятор всего
     */
    private IL5Calculator _calculation = null;
    /**
     * Введенная таблица значений  x и y
     */
    private ITableXY _tableXY = null;

    public void setTableXY(ITableXY tableXY) {
        _tableXY = tableXY;
    }

    private ILogger _logger = null;

    public void setLogger(ILogger logger) {
        _logger = logger;
    }

    /**
     * Рисовальщик графиков
     */
    private DrawGrafic _painter = null;

    /**
     * Установить рисовальщика
     *
     * @param draw рисовальщик
     */
    public void setPainter(DrawGrafic draw) {
        _painter = draw;
    }

    /**
     * Установить решальщика
     *
     * @param prc - компонент, который будет решать предложенную задачу
     */
    public void setCalcProcess(IL5Calculator prc) {
        _calculation = prc;
    }

    /**
     * Конструктор
     */
    public L5Params() {
        initComboboxes();
        setFormatters();
        cleanUpXY.addActionListener(e -> {
            if (_tableXY != null) _tableXY.clean();
        });
        fillTableXY.addActionListener(this::fillTableFromFunction);
        btnCalcSelectedMethod.addActionListener(this::startCalculation);
        btnCalcByAllMethods.addActionListener(e -> {
            calcAllMethods();
        });
    }

    private void logMessage(String msg) {
        if (_logger != null) _logger.log(msg);
    }

    /**
     * Заполняет таблицу данными и запускает расчет указанным методом
     *
     * @param e
     */
    private void fillTableFromFunction(ActionEvent e) {
        IFunction selectedFun = (IFunction) funCombo.getSelectedItem();
        if (selectedFun == null) {
            logMessage("Функция не выбрана.");
            return;
        }
        CalcParams params = readParams();
        if (params.xa == params.xb || params.dotsNumber <= 2) {
            logMessage("Параметры для интервала выбраны некорректно");
            return;
        }

        if (_tableXY == null) {
            logMessage("Не подключена таблица XY");
            return;
        }
        // Заполним таблицу
        _tableXY.clean();
        double step = Math.abs(params.xb - params.xa) / (params.dotsNumber - 1);
        int count = 1;
        for (double x = params.xa; count <= params.dotsNumber; count++, x += step) {
            if (count == params.dotsNumber) x = params.xb;
            _tableXY.addLine(x, selectedFun.functionOf(x));
        }
        // Рассчитаем всеми методами
        calcAllMethods();
    }

    /**
     * Создает массив методов и запускает расчет по таблице данных
     */
    public void calcAllMethods() {
        if (_calculation != null) {
            IL5Method[] methods = new IL5Method[comboMethods.getItemCount()];
            for (int i = 0; i < comboMethods.getItemCount(); i++) {
                methods[i] = (IL5Method) comboMethods.getItemAt(i);
            }
            _calculation.startCalculation(methods);
        }
    }

    /**
     * Записывает введенные параметры в класс CalcParams
     *
     * @return
     */
    public CalcParams readParams() {
        CalcParams params = new CalcParams();
        params.xa = ((Number) xa.getValue()).doubleValue();
        params.xb = ((Number) xb.getValue()).doubleValue();
        params.dotsNumber = ((Number) dotsNumber.getValue()).intValue();
        params.argument = ((Number) epsilon.getValue()).doubleValue();
        return params;
    }


    /**
     * Запускает расчет выбранного уравнения выбранным методом
     *
     * @param e
     */
    private void startCalculation(ActionEvent e) {
        if (_calculation != null) _calculation.startCalculation(
                new IL5Method[]{(IL5Method) comboMethods.getSelectedItem()});
    }


    /**
     * Записывает в комбобоксы уравнения, системы уравнений и вычислительные методы
     */
    private void initComboboxes() {
        var fun1 = new IFunction() {
            @Override
            public double functionOf(double x) {
                return Math.sin(x);
            }

            @Override
            public String toString() {
                return "sin(x)";
            }
        };

        var fun2 = new IFunction() {
            @Override
            public double functionOf(double x) {
                return Math.cos(x);
            }

            @Override
            public String toString() {
                return "cos(x)";
            }
        };
        var fun3 = new IFunction() {

            @Override
            public double functionOf(double x) {
                return Math.sqrt(x);
            }

            @Override
            public String toString() {
                return "sqrt(x)";
            }
        };
        funCombo.addItem(fun1);
        funCombo.addItem(fun2);
        funCombo.addItem(fun3);

        comboMethods.addItem(new L5Method1());
        comboMethods.addItem(new L5Method2());
        //comboMethods.addItem(new L5Method3());
        comboMethods.addItem(new L5Method4());
        comboMethods.addItem(new L5Method5());
        comboMethods.addItem(new L5Method6());

    }

    /**
     * Форматирует числовые поля для ввода
     */
    private void setFormatters() {
        NumberFormat number = new DecimalFormat("##0.#####");
        DefaultFormatterFactory dff = new DefaultFormatterFactory(new NumberFormatter(number));
        xa.setFormatterFactory(dff);
        xb.setFormatterFactory(dff);
        dotsNumber.setFormatterFactory(dff);
        epsilon.setFormatterFactory(dff);

        xa.setValue(0.);
        xb.setValue(0.);
        dotsNumber.setValue(0);
        epsilon.setValue(0.);

    }

    /**
     * Метод записывает параметры в контролы панели
     *
     * @param params - параметры для расчета
     */
    public void setParams(CalcParams params) {
        xa.setValue(params.xa);
        xb.setValue(params.xb);
        dotsNumber.setValue(params.startingDelta);

        epsilon.setValue(params.asMinX);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        paramsPanel = new JPanel();
        paramsPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 5, new Insets(2, 2, 2, 2), 10, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Фукнция для анализа");
        paramsPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        funCombo = new JComboBox();
        paramsPanel.add(funCombo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Исследуемый интервал");
        paramsPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        paramsPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        xa = new JFormattedTextField();
        panel1.add(xa, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        xb = new JFormattedTextField();
        panel1.add(xb, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Количество точек");
        paramsPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        cleanUpXY = new JButton();
        cleanUpXY.setText("Очистить таблицу x-y");
        paramsPanel.add(cleanUpXY, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fillTableXY = new JButton();
        fillTableXY.setText("Заполнить таблицу x y");
        paramsPanel.add(fillTableXY, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        paramsPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(219, 34), null, 0, false));
        dotsNumber = new JFormattedTextField();
        dotsNumber.setHorizontalAlignment(2);
        panel2.add(dotsNumber, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        btnCalcByAllMethods = new JButton();
        btnCalcByAllMethods.setText("Решить всеми методами");
        paramsPanel.add(btnCalcByAllMethods, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCalcSelectedMethod = new JButton();
        btnCalcSelectedMethod.setText("Решить указанным методом");
        paramsPanel.add(btnCalcSelectedMethod, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Аргумент");
        paramsPanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        paramsPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        epsilon = new JFormattedTextField();
        panel3.add(epsilon, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Вычислительный метод");
        paramsPanel.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboMethods = new JComboBox();
        paramsPanel.add(comboMethods, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return paramsPanel;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
