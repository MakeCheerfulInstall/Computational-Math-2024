package Lab1;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс создания матрицы из текстового файла
 * 
 * @author Анастасия Коляда
 *
 */
public class ReadFromFile extends ReadFromConsole {

	/**
	 * перееопределение метода чтения матрицы
	 */
	@Override
	protected Matrix ReadMatrix(int n) throws IOException {
		Matrix matrix = new Matrix(n);
		for (int i = 0; i < n; i++) {
			// Цикл чтения строки. Этот цикл идет до тех пор, пока в строке не окажется n+1
			// значений double
			String[] line;
			line = bufferReader.readLine().split(" ");
			matrix.saveString(i, line);
		}
		return matrix;
	}
	/**
	 * Переопределение метода чтения размерности матрицы
	 */
	@Override
	protected int GetN() throws IOException {
		int n = Integer.parseUnsignedInt(bufferReader.readLine());
		return n;
	}

	/**
	 * Переопределение метода создания reader'а
	 */
	@Override
	protected boolean OpenReader() throws FileNotFoundException {
		// Создаем файл чузер - диалоговое окно для выбора файла
		JFileChooser fc = new JFileChooser();
		// Определяем текущую папку на место проекта
		String localPath = this.getClass().getResource("").getPath(); // Получаем папку проекта
		// Утснавливаем эту папку в качестве текеущей папки для файлчузера
		File path = new File(localPath);
		fc.setCurrentDirectory(path);
		// Устанавливаем полезные свойства файлчузера
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
		fc.setFileFilter(filter);
		// Определяем родительский фрейм (чтобы приложение было видно на тэскбаре в
		// windows
		JFrame frame = new JFrame();
		try {
			// Устанавливаем полезные свойства фрейма
			frame.setLocationRelativeTo(null);
			frame.toFront();
			frame.requestFocus();
			frame.setVisible(true);
			// Открываем файл чузер
			int returnVal = fc.showOpenDialog(frame);

			// Если файл был выбран, создаем ридер
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				bufferReader = new BufferedReader(new FileReader(file.getPath()));
				return true;
			} else {
				// Иначе не создаем, возвращаем false, что нет ридера
				return false;
			}
		} finally {
			// По завершении закрываем фрейм
			frame.dispose();
		}
	}

}
