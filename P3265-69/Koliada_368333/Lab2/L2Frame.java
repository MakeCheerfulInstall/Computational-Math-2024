package Lab2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Класс главного окна программы. Реализуем интерфейс Ilogger, выводит сообщения
 * текстовое окно
 */
public class L2Frame extends JFrame implements ILogger {
    public L2Frame() {
        // Добавляем к фрейму меню, устанавлаваем размер фрэйма с учетом размера меню, которое добавляется методом addMenu
        setSize(1020, 600 + addMenu());
        // Устанавливаем, что размер окна может быть изменен
        setResizable(true);
        // Добавляем панель для выбора параметров расчета
        addParamsPanel();

        // Устанавливаем позицию окна по центру экрана
        setLocationRelativeTo(null);
        // Устанавливаем, что при закрытии окна программа должна завершиться
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Доавлеят текстовое окно для вывода сообщений
        addTextArea();
        //Добавляет панель для вывода графиков
        addDrawingPanel();

    }

    /**
     * Панель с параметрами
     */
    private L2Params paramsPanel;
    /**
     * Метод добавляет панель с параметрами
     */
    private void addParamsPanel() {
        // Добавляем JPanel  для указания параметров
        paramsPanel = new L2Params();
        // Передает панели методы, которые должны быть вызваны при выборе расчетных действий
        paramsPanel.setCalcProcess(new CalculationProcess() {
            //Для расчета нелинейных уравнений
            @Override
            public void startCalculation(IFunction func, IMethod method, CalcParams params) {
                Calculate(func, method, params);
            }

            // Для расчета системы нелинейных уравнений
            @Override
            public void startSysCalculation(ISysFunctions sysFunctions, IMethodSys method, CalcParams params) {
                CalculateSys(sysFunctions, method, params);
            }
        });
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
        //Добавляет панель на верхний участок фрейма
        add(paramsPanel.paramsPanel, BorderLayout.NORTH);

    }

    /**
     * Метод, вызываемый для расчета системы нелинейных уравнений
     *
     * @param funcs  система нелинейных уравнений
     * @param method используемый метод для расчета системы
     * @param params входные параметры для расчета
     */
    private void CalculateSys(ISysFunctions funcs, IMethodSys method, CalcParams params) {
        logArea.setText(""); // Очищает окно вывода сообщений
        method.setLogger(this); //Подключает к методу фрейм, как ILogger
        try {
            method.Calculate(funcs, params); //Запускает расчет
        } catch (CalcErrorException e) {
            log(e.getMessage()); //Вывод сообщений при возникновении ошибок
        }
    }

    /**
     * Метод вызываемый для расчета нельнейной функции
     * @param func функиця для расчета
     * @param method - метод, применяемый для расчета
     * @param params - входные параметры
     */
    private void Calculate(IFunction func, IMethod method, CalcParams params) {
        logArea.setText(""); //Очищает окно для выволда сообщений
        method.setLogger(this); //Устанавливает фрейм, как ILogger
        try {
            method.calculate(func, params);//Запускает расчет
            //Выводит результат
            log(String.format("Корень уравнения равен %f\n", method.getCalculatedRoot()));
        } catch (CalcErrorException e) {
            log(e.getMessage());//Выводит сообщение об ошибке
        }
    }

    /**
     * Панель для отображения графиков
     */
    private DrawPanel _dp = null;
    //Метод добавляет к фрейму панель отображения графиков

    /**
     * Метод добавляет на фрейм панель для отображения гарфиков функций
     */
    private void addDrawingPanel() {
        _dp = new DrawPanel();
        add(_dp);
    }

    /**
     * Текстовое окно для вывода сообщений
     */
    private JTextArea logArea = null;

    /**
     * Добавляет текстовое окно для вывода сообщений в нижнюю часть фрейма
     */
    private void addTextArea() {
        //Создает текстовый контрол и выводит в него начальное сообщение
        logArea = new JTextArea("Результат расчета:", 4, 10);
        logArea.setEditable(false);//Текст в контроле нельзя редактировать
        // Шрифт и табуляция
        logArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        logArea.setTabSize(10);
        //Добавлем текстовый контрол на тфрейм, оборачивая его в JScrollPane, чтобы можно
        //прокручивать текст в контроле
        add(new JScrollPane(logArea), BorderLayout.SOUTH);
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
        JMenuItem menuItem = new JMenuItem("Oткрыть файл с параметрами", KeyEvent.VK_O);
        // Устанавливаем для позиции меню акселератор как ALT+1
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK));
        // Добавляем к меню получателя события, используем безымянный класс для интерфейса ActionListener
        // В качестве метода для вызова указываем метода openFile
        menuItem.addActionListener(ae -> openFile());
        // Добавляем позицию New  к подменю File
        menuFile.add(menuItem);

        // Определяем и создаем позицию меню New, определяем для позиции мнемонику VK_N
        menuItem = new JMenuItem("Cохранить параметры в фале", KeyEvent.VK_C);
        // Устанавливаем для позиции меню акселератор как ALT+2
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_DOWN_MASK));
        // Добавляем к меню получателя события, используем безымянный класс для интерфейса ActionListener
        // В качестве метода для вызова указываем метода openFile
        menuItem.addActionListener(ae -> saveParams());
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
            //прочитать из файла строку параметров, создать из строки объект параметров
            CalcParams params = new CalcParams(bufferReader.readLine());
            //Записать параметры на панель
            paramsPanel.setParams(params);
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
        logArea.setText(logArea.getText() + message);
    }
}
