package Lab4;

import Lab2.*;
import Lab3.L3Params;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * Класс главного окна программы. Реализуем интерфейс Ilogger, выводит сообщения
 * текстовое окно
 */
public class L4Frame extends JFrame implements ILogger {
    public L4Frame() {
        // Добавляем к фрейму меню, устанавлаваем размер фрэйма с учетом размера меню, которое добавляется методом addMenu
        setSize(1020, 600 + addMenu());
        // Устанавливаем, что размер окна может быть изменен
        setResizable(true);
        // Добавляем панель для выбора параметров расчета
        //addParamsPanel();

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

        //splitPane.setDividerLocation(0.8);
    }

    /**
     * Панель с параметрами
     */
    private L3Params paramsPanel;
    /**
     * Метод добавляет панель с параметрами
     */
    private void calculate(Dots dots) {
        logArea.setText(""); //Очищает окно для вывода сообщений
        try {
            L4Calculator calc = new L4Calculator(dots,this);
            calc.setDrawPanel(_dp);
            calc.calculate();

        } catch (CalcErrorException e) {
            log(e.getMessage());//Выводит сообщение об ошибке
        }
    }

    /**
     * Панель для отображения графика функции
     */
    private L4DrawPanel _dp = null;


    /**
     * Метод добавляет на фрейм панель для отображения гарфиков функций
     */
    private JComponent createDrawingPanel() {
        _dp = new L4DrawPanel();
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
        //Добавлем текстовый контрол на фрейм, оборачивая его в JScrollPane, чтобы можно
        //прокручивать текст в контроле
        return new JScrollPane(logArea);
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
        JMenuItem menuItem = new JMenuItem("Загрузить график из файла...");
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
     * Метод сохраняет параметры в файле
     */
    private void saveParams() {
        File f = getFile(false);
        if(f == null) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f.getPath()))) {
            writer.write(paramsPanel.readParams().toString());
        } catch (IOException e) {
            logArea.setText("");
            log(e.getMessage());
        }
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
     * Метод записывает параметры из файла, указанного пользователем, в панель параметров
     */
    public void openFile() {
        // Запросить файл
        File f = getFile(true);
        if(f == null) return;
        //Открыть BufferedReader для файла

        try (var bufferReader = new BufferedReader(new FileReader(f.getPath()))){
            String sLine;
            ArrayList<String[]> strings = new ArrayList<>();
            while((sLine = bufferReader.readLine()) != null){
                String[] line = sLine.split(" ");
                if(line.length != 2) throw new FileNotFoundException("Неверный формат файла. Каждая строка файла " +
                        "должна содержать два числа");
                strings.add(line);
            }
            Dots dots = new Dots();
            double[] xs = new double[strings.size()];
            double[] ys = new double[strings.size()];
            int i = 0;
            for(var line:strings){
                xs[i] = Double.parseDouble(line[0]);
                ys[i] = Double.parseDouble(line[1]);
                i++;
            }
            dots.setN(strings.size());
            dots.setXPoints(xs);
            dots.setYPoints(ys);
            _dp.setDots(dots);

            L4Calculator calc = new L4Calculator(dots,this);
            calc.setDrawPanel(_dp);
            calc.calculate();

        } catch (IOException | FileFormatException e) {
            logArea.setText(""); // перед выводом сообщения об ошибке очистим текстовое окно
            log(e.getMessage()); // запишем в окно сообщение об ошибке
        }

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
}
