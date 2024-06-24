package Lab2;

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
import java.util.Objects;

/**
 * Панель параметров
 */
public class L2Params {
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
     * Начальное приближение
     */
    private JFormattedTextField startedDelta;
    /**
     * Точность решения
     */
    private JFormattedTextField precision;
    /**
     * Кнопка рассчитать уравнение
     */
    private JButton calculate;
    /**
     * Кнопка нарисовать график уравнения
     */
    private JButton drawChart;
    /**
     * Комбобокс для выбора метода решения уравнения
     */
    private JComboBox comboMethods;
    /**
     * Комбобокс для выбора системы уравнений
     */
    private JComboBox sysFunCombo;
    /**
     * Кнопка для отображения графиков системы уравнений
     */
    private JButton btnDrawSysFuns;
    /**
     * Левая граница интервала решений для x1 (x)
     */
    private JFormattedTextField asMinX;
    /**
     * /Правая граница интервала решений для x1 (x)
     */
    private JFormattedTextField asMaxX;
    /**
     * Левая граница интервала решений для x2(y)
     */
    private JFormattedTextField asMinY;
    /**
     * Правая граница интервала решения для x2(y)
     */
    private JFormattedTextField asMaxY;
    /**
     * Начальное приближение по x1 (x)
     */
    private JFormattedTextField iaX;
    /**
     * Начальное приближение по x2 (y)
     */
    private JFormattedTextField iaY;
    /**
     * Кнопка для запуска решения системы уравнения
     */
    private JButton btnCalcSysFunctions;
    private JComboBox comboSysMethods;
    /**
     * Калькулятор всего
     */
    private CalculationProcess _calculation = null;
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
    public void setCalcProcess(CalculationProcess prc) {
        _calculation = prc;
    }

    /**
     * Конструктор
     */
    public L2Params() {
        initComboboxes();
        setFormatters();
        calculate.addActionListener(this::startCalculation);
        drawChart.addActionListener(this::startDrawing);
        btnDrawSysFuns.addActionListener(this::startDrawGrafics);
        btnCalcSysFunctions.addActionListener(this::startSysCaclulation);
    }

    /**
     * Запускает рисование графика функции
     *
     * @param e
     */
    private void startDrawing(ActionEvent e) {
        if (_painter != null)
            _painter.drawGrafic((IFunction) funCombo.getSelectedItem(), readParams());
    }

    /**
     * Запускает рисование графиков функций системы уравнений
     *
     * @param e
     */
    private void startDrawGrafics(ActionEvent e) {
        if (_painter != null) {
            _painter.drawGrafics(((ISysFunctions) Objects.requireNonNull(sysFunCombo.getSelectedItem()))
                    .getSysFunctions(), readParams());
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
        params.startingDelta = ((Number) startedDelta.getValue()).doubleValue();
        params.precision = ((Number) precision.getValue()).doubleValue();
        params.asMaxX = ((Number) asMaxX.getValue()).doubleValue();
        params.asMinX = ((Number) asMinX.getValue()).doubleValue();
        params.asMaxY = ((Number) asMaxY.getValue()).doubleValue();
        params.asMinY = ((Number) asMinY.getValue()).doubleValue();
        params.iaX = ((Number) iaX.getValue()).doubleValue();
        params.iaY = ((Number) iaY.getValue()).doubleValue();
        return params;
    }

    /**
     * Запускает расчет выбранного уравнения выбранным методом
     *
     * @param e
     */
    private void startCalculation(ActionEvent e) {

        if (_calculation != null) _calculation.startCalculation(
                (IFunction) funCombo.getSelectedItem(),
                (IMethod) comboMethods.getSelectedItem(),
                readParams());
    }

    /**
     * Запускает расчет выбранной системы уравнений методом простых итераций
     *
     * @param e
     */
    private void startSysCaclulation(ActionEvent e) {
        if (_calculation != null)
            _calculation.startSysCalculation((ISysFunctions) sysFunCombo.getSelectedItem(),
                    (IMethodSys) comboSysMethods.getSelectedItem(), readParams());
    }

    /**
     * Записывает в комбобоксы уравнения, системы уравнений и вычислительные методы
     */
    private void initComboboxes() {
        funCombo.addItem(new Function1());
        funCombo.addItem(new Function2());
        funCombo.addItem(new Function3());
        funCombo.addItem(new Function4());
        //funCombo.addItem("x**3 + 4.81x**2 - 17.37x + 5.38");

        comboMethods.addItem(new MHalfDivision());
        comboMethods.addItem(new MChord());
        comboMethods.addItem(new MNewtons());
        comboMethods.addItem(new MSecant());
        comboMethods.addItem(new MSimpleIteration());

        sysFunCombo.addItem(new SystemOfFunctions());
        sysFunCombo.addItem(new SystemOfFunctions2());
        sysFunCombo.addItem(new SystemOfFunctions3());
        sysFunCombo.addItem(new SystemOfFunctions4());

        comboSysMethods.addItem(new MSysNewton());
        comboSysMethods.addItem(new MSysSimpleIteration());

    }

    /**
     * Форматирует числовые поля для ввода
     */
    private void setFormatters() {
        NumberFormat number = new DecimalFormat("##0.#####");
        DefaultFormatterFactory dff = new DefaultFormatterFactory(new NumberFormatter(number));
        xa.setFormatterFactory(dff);
        xb.setFormatterFactory(dff);
        startedDelta.setFormatterFactory(dff);
        asMinY.setFormatterFactory(dff);
        asMaxX.setFormatterFactory(dff);
        asMaxY.setFormatterFactory(dff);
        asMinX.setFormatterFactory(dff);
        iaY.setFormatterFactory(dff);
        iaX.setFormatterFactory(dff);
        precision.setFormatterFactory(dff);

        xa.setValue(0.);
        xb.setValue(0.);
        startedDelta.setValue(0);
        precision.setValue(0.01);

        asMinX.setValue(0.);
        asMaxX.setValue(0.);
        asMinY.setValue(0.);
        asMaxY.setValue(0.);
        iaX.setValue(0.);
        iaY.setValue(0.);
    }

    /**
     * Метод записывает параметры в контролы панели
     *
     * @param params - параметры для расчета
     */
    public void setParams(CalcParams params) {
        xa.setValue(params.xa);
        xb.setValue(params.xb);
        startedDelta.setValue(params.startingDelta);
        precision.setValue(params.precision);

        asMinX.setValue(params.asMinX);
        asMaxX.setValue(params.asMaxX);
        asMinY.setValue(params.asMinY);
        asMaxY.setValue(params.asMaxY);
        iaX.setValue(params.iaX);
        iaY.setValue(params.iaY);
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
        paramsPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 5, new Insets(2, 2, 2, 2), 10, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Укажите уравнение");
        paramsPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        funCombo = new JComboBox();
        paramsPanel.add(funCombo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Границы интервала");
        paramsPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        paramsPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        xa = new JFormattedTextField();
        panel1.add(xa, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        xb = new JFormattedTextField();
        panel1.add(xb, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Начальное приближение к корню");
        paramsPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        calculate = new JButton();
        calculate.setText("Вычислить корень уравнения");
        paramsPanel.add(calculate, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        drawChart = new JButton();
        drawChart.setText(" Нарисовать график функции");
        paramsPanel.add(drawChart, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Вычислительный метод");
        paramsPanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        comboMethods = new JComboBox();
        paramsPanel.add(comboMethods, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        paramsPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(219, 34), null, 0, false));
        startedDelta = new JFormattedTextField();
        startedDelta.setHorizontalAlignment(2);
        panel2.add(startedDelta, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Погрешность");
        panel2.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        precision = new JFormattedTextField();
        panel2.add(precision, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        btnDrawSysFuns = new JButton();
        btnDrawSysFuns.setText("Нарисовать график функций");
        paramsPanel.add(btnDrawSysFuns, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Система уравнений");
        paramsPanel.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCalcSysFunctions = new JButton();
        btnCalcSysFunctions.setText("Найти решение системы");
        paramsPanel.add(btnCalcSysFunctions, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Начальное приближение");
        paramsPanel.add(label7, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        paramsPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("x =");
        panel3.add(label8, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        iaX = new JFormattedTextField();
        panel3.add(iaX, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("y=");
        panel3.add(label9, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        iaY = new JFormattedTextField();
        panel3.add(iaY, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Область решений");
        paramsPanel.add(label10, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        paramsPanel.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        asMinX = new JFormattedTextField();
        panel4.add(asMinX, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("<x<");
        panel4.add(label11, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        asMaxX = new JFormattedTextField();
        panel4.add(asMaxX, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        asMinY = new JFormattedTextField();
        panel4.add(asMinY, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("<y<");
        panel4.add(label12, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        asMaxY = new JFormattedTextField();
        panel4.add(asMaxY, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Вычислительный метод");
        paramsPanel.add(label13, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboSysMethods = new JComboBox();
        paramsPanel.add(comboSysMethods, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sysFunCombo = new JComboBox();
        paramsPanel.add(sysFunCombo, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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


}
