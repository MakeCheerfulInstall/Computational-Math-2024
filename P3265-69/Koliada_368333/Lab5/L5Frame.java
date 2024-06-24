package Lab5;

import Lab2.*;
import Lab4.Dots;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс главного окна программы. Реализуем интерфейс Ilogger, выводит сообщения
 * текстовое окно
 */
public class L5Frame extends JFrame implements ILogger {
    public L5Frame() {
        // Добавляем к фрейму меню, устанавлаваем размер фрэйма с учетом размера меню, которое добавляется методом addMenu
        setSize(1020, 600 + addMenu());
        // Устанавливаем, что размер окна может быть изменен
        setResizable(true);
        // Добавляем панель для выбора параметров расчета
        addParamsPanel();

        //Добавляем таблицу для ввода данных
        addTablePanel();

        // Устанавливаем позицию окна по центру экрана
        setLocationRelativeTo(null);
        // Устанавливаем, что при закрытии окна программа должна завершиться
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


        //Добавляет панель для вывода графиков
        splitPane.setTopComponent(createDrawingPanel());

        //Доавлеят текстовое окно для вывода сообщений
        splitPane.setBottomComponent(createTextArea());

        add(splitPane, BorderLayout.CENTER);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.75);

        clearData();
        //splitPane.setDividerLocation(0.8);
    }

    /**
     * Панель с параметрами
     */
    private L5Params paramsPanel;
    /**
     * Метод добавляет панель с параметрами
     */


    /**
     * Панель для отображения графика функции
     */
    private L5DrawPanel _dp = null;


    /**
     * Метод добавляет на фрейм панель для отображения гарфиков функций
     */
    private JComponent createDrawingPanel() {
        _dp = new L5DrawPanel();
        return _dp;
    }

    /**
     * Текстовое окно для вывода сообщений
     */
    private JTextArea logArea = null;

    /**
     * Добавляет текстовое окно для вывода сообщений в нижнюю часть фрейма
     */
    private JComponent createTextArea() {
        //Создает текстовый контрол и выводит в него начальное сообщение
        logArea = new JTextArea("Результат расчета:\n", 4, 10);
        logArea.setEditable(false);//Текст в контроле нельзя редактировать
        // Шрифт и табуляция
        logArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        logArea.setTabSize(10);

        return new JScrollPane(logArea);
    }

    /**
     * Форматирует double в строку для вывода в таблицу
     * @param d
     * @return
     */
    private String formatDbl(double d) {
        String sd = String.format("%.5f", d);
        return String.format("%10s",sd);
    }

    /**
     * Выводит таблицу конечных разностей в журнал
     * @param finalTable
     */
    private void printFinalTable(double[][] finalTable){
        log("Таблица конечных разностей:");
        String line="";
        for(int i = 0; i < finalTable.length; i++){
            line += formatDbl(finalTable[i][0]);
            for(int j = 1; j <= finalTable.length-i; j++){
                line += formatDbl(finalTable[i][j]);
            }
            log(line);
            line ="";
        }
    }

    /**
     * Метод добавляет основное меню и возвращает его высоту
     * @return высота основного меню
     */
    private int addMenu() {
        // Создаем JMenuBar
        JMenuBar menuBar = new JMenuBar();

        // Вызываем метод addFileNemu для добавления мееню File
        addFileMenu(menuBar);

        // Подсоединяем меню к фрейму
        setJMenuBar(menuBar);
        // Возвращаем высоту меню
        return menuBar.getHeight();
    }

    /**
     * Метод добавляет подменю File
     *
     * @param menuBar - меню к которому добавляем подменю
     */
    private void addFileMenu(JMenuBar menuBar) {
        // Создаем JMenu File
        JMenu menuFile = new JMenu("File");
        // Определяем для него мнемонику VK_F
        menuFile.setMnemonic(KeyEvent.VK_F);
        // Добавляем его к menuBar
        menuBar.add(menuFile);

        // Определяем и создаем позицию меню New, определяем для позиции мнемонику VK_N
        JMenuItem menuItem = new JMenuItem("Загрузить функцию из файла...");
        // Устанавливаем для позиции меню акселератор как ALT+1
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK));
        // Добавляем к меню получателя события, используем безымянный класс для интерфейса ActionListener
        // В качестве метода для вызова указываем метода openFile
        menuItem.addActionListener(ae -> openFile());
        // Добавляем позицию New  к подменю File
        menuFile.add(menuItem);



        // Определяем и создаем позицию меню New, определяем для позиции мнемонику VK_N
        menuItem = new JMenuItem("Cохранить Pезультаты в файле", KeyEvent.VK_P);
        // Устанавливаем для позиции меню акселератор как ALT+3
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_DOWN_MASK));
        // Добавляем к меню получателя события, используем безымянный класс для интерфейса ActionListener
        // В качестве метода для вызова указываем метода openFile
        menuItem.addActionListener(ae -> saveResults());
        // Добавляем позицию New  к подменю File
        menuFile.add(menuItem);

        // Добавляем к подменю сепаратор
        menuFile.addSeparator();

        // Определяем и создаем позицию меню Exit, определяем для позици мнемонику E
        menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        // Устанавливаем для позиции меню акселератор как ALT+ESC
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.ALT_DOWN_MASK));
        // Добавляем к позиции меню получателя события, используем безымянный класс для интерфейса ActionListener
        // В качестве метода для вызова указываем метод Systex.exit(0), который прекратит выполнение программы и закроет окно
        menuItem.addActionListener(ae -> System.exit(0));
        // Добавляем к подменю File позицию меню Exit
        menuFile.add(menuItem);
    }

    /**
     * Метод сохраняет результаты в файле
     */
    private void saveResults() {
        File f = getFile(false);
        if(f == null) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f.getPath()))) {
            writer.write(logArea.getText());
        } catch (IOException e) {
            logArea.setText("");
            log(e.getMessage());
        }
    }

    /**
     * Метод запрашивает у пользовтеля File.
     * @param toOpen - true, если файл будет использоваться для чтения, false - если для записи
     * @return - Объект File, или null - если не удалось выбрать файл.
     */
    private File getFile(boolean toOpen) {
        // Создаем файл чузер - диалоговое окно для выбора файла
        var fc = new JFileChooser();
        // Определяем текущую папку на место проекта
        var localPath = this.getClass().getResource("").getPath(); // Получаем папку проекта
        if(localPath == null) return null;
        // Утснавливаем эту папку в качестве текеущей папки для файлчузера
        File path = new File(localPath);
        fc.setCurrentDirectory(path);
        // Устанавливаем полезные свойства файлчузера
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
        fc.setFileFilter(filter);

        // Открываем файл чузер
        int returnVal;
        if(toOpen) returnVal= fc.showOpenDialog(this);
        else returnVal = fc.showSaveDialog(this);

        // Если файл был выбран, возвращаем файл
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            // Иначе не создаем, возвращаем false, что нет ридера
            return null;
        }
    }

    /**
     * Метод открывает файл с данными для функции и запускает расчет
     */
    public void openFile() {
        // Запросить файл
        File f = getFile(true);
        if(f == null) return;
        //Открыть BufferedReader для файла

        //Очищает данные в таблице функции
        clearData();

        try (var bufferReader = new BufferedReader(new FileReader(f.getPath()))){
            String sLine;
            ArrayList<String[]> strings = new ArrayList<>();
            while((sLine = bufferReader.readLine()) != null){
                String[] line = sLine.split(" ");
                if(line.length != 2) throw new FileNotFoundException("Неверный формат файла. Каждая строка файла " +
                        "должна содержать два числа");
                strings.add(line);
            }
            double[] xs = new double[strings.size()];
            double[] ys = new double[strings.size()];
            int i = 0;
            for(var line:strings){
                xs[i] = Double.parseDouble(line[0]);
                ys[i] = Double.parseDouble(line[1]);
                data[i][0] = xs[i];
                data[i][1] = ys[i];
                i++;
            }
            //Перерисовываем таблицу, чтобы отобразить изменения
            table.repaint();
            //Запускаем все расчеты и рисование всех графиков
            paramsPanel.calcAllMethods();

        } catch (IOException | FileFormatException e) {
            logArea.setText(""); // перед выводом сообщения об ошибке очистим текстовое окно
            log(e.getMessage()); // запишем в окно сообщение об ошибке
        }
    }

    /**
     * Очищает данные связанные с таблицей функции
     */
    private void clearData() {
        for (Object[] datum : data) {
            Arrays.fill(datum, "");
        }
    }

    /**
     * добавляет строку к данным в таблице функции
     * @param x
     * @param y
     * @return
     */
    private boolean addLineXY(double x, double y) {
        int row;
        //Ищем снизу последнюю пусту строку
        for(row = data.length - 2; row >= 0; row--) {
            if(!cellIsEmpty(row,0) || !cellIsEmpty(row,1)){
                break;
            }
        }
        //Если нашли, то заполняем
        if(row < data.length) {
            data[row+1][0] = x;
            data[row+1][1] = y;
            return true;
        }
        return false;
    }
    // Проверка на пустоту строки в таблице функции
    private boolean cellIsEmpty(int row, int col) {
        return data[row][col] == null || (data[row][col] instanceof String) && ((String)data[row][col]).isEmpty();
    }

    /**
     * Метод интерфейса ILogger, выводит сообщение в текстовое окно
     * @param message - сообщение для отображения
     */
    @Override
    public void log(String message) {
        logArea.setText(logArea.getText() + message+"\n");
    }

    /**
     * Очищает содержание журнала
     */
    @Override
    public void clear() {

        logArea.setText("");
    }


    /**
     * Данные для таблицы функции - максимум 200 строк
     */
    final private Object[][] data = new Object[200][2];
    /**
     * Таблица для отображения данных функции
     */
    private  JTable table;

    /**
     * Добавляет к пользовательскому интерфейсу таблицу для данных функции
     */
    private void addTablePanel() {

        String[] columnNames = {"x","y"};
        table = new JTable(data, columnNames);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 600));

        add(scrollPane, BorderLayout.WEST);
    }

    /**
     * Метод добавляет панель с параметрами
     */
    private void addParamsPanel() {
        // Добавляем JPanel  для указания параметров
        paramsPanel = new L5Params();

        //Передаем панели с параметрами интерфейс на таблицу с даннми функции,
        // чтобы она могла ее заполнять из указанных обязательных функций
        // и вызывать расчет по этим данным
        paramsPanel.setTableXY(new ITableXY() {
            @Override
            public void clean() {
                clearData();
                table.repaint();
            }

            @Override
            public boolean addLine(double x, double y) {
                boolean res = addLineXY(x,y);
                table.repaint();
                return res;
            }

            @Override
            public double[][] getXY() {
                return getData();
            }
        });

        paramsPanel.setLogger(this);
        //Передает панели методы, которые должны быть вызваны для отображения графиков
        paramsPanel.setPainter(new DrawGrafic() {
            // Для отображения графика нелинейной функции
            @Override
            public void drawGrafic(IFunction func, CalcParams params) {
                _dp.setFunction(func);
                _dp.repaint();
            }

            // ДЛя отображения графиков системы нелинейных уравнений
            @Override
            public void drawGrafics(IFunction[] funcs, CalcParams params) {
                _dp.setFunctions(funcs);
                _dp.repaint();
            }
        });

        //устанавливаем метод, который будет считать все и все рисовать
        paramsPanel.setCalcProcess(this::startCalculation);

        //Добавляет панель на верхний участок фрейма
        add(paramsPanel.paramsPanel, BorderLayout.NORTH);

    }

    /**
     * Подгатавдивает точки графика и узлы интерплоляции на графике
     * @param xs узлы интерполяции
     * @param ys значения функции в узлах интерполяции
     */
    private void setDotsOnDrawPanel(double[] xs,double[] ys){
        //Отображает точки на графике
        // Удваиваем массивы, та как отобразим и точки x на координатной оси x
        double[] dxs = new double[xs.length*2];
        double[] dys = new double[xs.length*2];
        for(int i = 0; i< xs.length;i++){
            dxs[i+xs.length] = dxs[i] = xs[i];
            dys[i] = ys[i];
            dys[i+xs.length] = 0;
        }
        Dots dots = new Dots();
        dots.setN(dxs.length);
        dots.setXPoints(dxs);
        dots.setYPoints(dys);
        _dp.setDots(dots);
        //Теперь создадим табличную функцию
        L5FunctionByTable tf = new L5FunctionByTable();
        tf.setXY(xs,ys);

        //Теперб создадим многочлен Ньютона
        L5Method4 nm = new L5Method4();
        nm.setXY(xs,ys);

        L5Method2 nm2 = new L5Method2();
        nm2.setXY(xs,ys);

        //Занесем их как функции, которые нужно отображать
        IFunction[] funs = new IFunction[]{tf,nm2,nm};
        _dp.setFunctions(funs);
        _dp.repaint();

    }

    /**
     * Запускаем процесс расчета и отображения всего
     * @param methods
     */
    private void startCalculation(IL5Method[] methods){
        try {
            // Готовим два отдельных массива xs and ys для дальнейших расчетов
            double[][] data = null;
            data = getData();
            double[] xs = new double[data.length];
            double[] ys = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                xs[i] = data[i][0];
                ys[i] = data[i][1];
            }
            //Читаем параметры
            CalcParams params = paramsPanel.readParams();

            //Запускаем расчет указанными методами и пероверяем на то, что
            // все орасчеты дают результат
            int counter;
            double lastMove = 0;
            for(counter = 0; counter < 20;counter++) {
                boolean bad = false;
                for (IL5Method m : methods) {
                    try {
                        double res = m.method(xs, ys, params.argument);
                        if(Double.isNaN(res)){
                            bad = true;
                            break;
                        }
                        //log(String.format("%s: результат равен %f", m.toString(), res));
                    } catch (CalcErrorException e) {
                        //log(String.format("%s:  %s", m.toString(), e.getMessage()));
                    }
                }
                if(!bad) break;
                //Если хоть один не дал результат, то пересортируем
                // Сдвигаем равные элементы и так 20 раз
                preSortX(xs,ys);
                lastMove = preMoveX(xs,lastMove);

            }
            // Очищаем журнал
            clear();

            //Если вышли с большим 0, значит с первого раза не смогли посчитать
            if(counter > 0){
                log("Значения узлов интрепояции были некорректны. Произведена коррекция");
            }

            //Если вышли с меньшим 20, значит смоги посчитать и в xs и ys находятся значения, по которым расчет возможен
            //Поэтому счиатем
            if(counter < 20){
                for (IL5Method m : methods) {
                    try {
                        double res = m.method(xs, ys, params.argument);
                        log(String.format("%s: результат равен %f", m.toString(), res));
                    } catch (CalcErrorException e) {
                        log(String.format("%s:  %s", m.toString(), e.getMessage()));
                    }
                }
            }
            //Иначе расчет невозможен и не считаем
            else {
                log("Значения узлов интрепояции некорректны. Корректура не помогла!");
            }

            //передает drawpanel точки для отрисовки узлов
            // и графика табличной функции, а также графика многочлена
            setDotsOnDrawPanel(xs,ys);


            //Заполняет таблицу конечных разностей
            setFinalDifferences(xs,ys);


        }
        catch (Exception e){
            log(e.getMessage());
        }
    }

    private void preSortX(double xs[],double[] ys   ){
        for(int i = 0; i<xs.length; i++){
            for(int j = i+1; j<xs.length; j++){
                if(xs[j]<xs[i]){
                    double temp = xs[i];
                    double temp1 = ys[i];
                    xs[i] = xs[j];
                    ys[i] = ys[j];
                    xs[j] = temp;
                    ys[j] = temp1;
                }
            }
        }
    }

    private double preMoveX(double xs[],double lastMove){
        double h = (xs[xs.length -1]- xs[0])/xs.length;
        for(int i =0; i < xs.length-1;i++){
            if((xs[i+1] - xs[i]) == lastMove){
                xs[i+1] += h/10;
            }
        }
        lastMove+= h;
        return lastMove;
    }

    /**
     * Получает таблицу когнечных разностей и печатает ее
     * @param xs
     * @param ys
     */
    private void setFinalDifferences(double[] xs,double[] ys){
        L5Method3 m3 = new L5Method3();
        m3.setXY(xs,ys);
        double[][] table = m3.getFinalDiffTable();
        printFinalTable(table); //Печать
    }

    /**
     * возвращает данные из таблицы. Поскольку в таблице могут быть и double
     * и строки, то производит необходимые преобразования
     * @return
     */
    private double[][] getData() {
        int count = 0;
        // Сначала считаем, сколько там есть строк
        outer:for (int row = 0; row < data.length; row++) {
            if(data[row] == null) break;
            for (int col = 0; col < data[row].length; col++) {
                if(cellIsEmpty(row,col)) continue outer; //пропускаем строку, если в ней есть пустая ячейка
            }
            count++;
        }
        double[][] mass = new double[count][2];
        outer1:for (int row = 0; row < data.length; row++) {
            if(data[row] == null) break;
            for (int col = 0; col < data[row].length; col++) {
                if(cellIsEmpty(row,col)) continue outer1; //пропускаем строку, если в ней есть пустая ячейка
            }

            if(data[row][0] instanceof String){
                mass[row][0] = Double.parseDouble(((String)data[row][0]).replace(',','.'));
            }
            else
                mass[row][0] = ((Number)  data[row][0]).doubleValue();
            if(data[row][1] instanceof String){
                mass[row][1] = Double.parseDouble(((String)data[row][1]).replace(',','.'));
            }
            else
                mass[row][1] = ((Number)  data[row][1]).doubleValue();
        }
        return mass;
    }
}
