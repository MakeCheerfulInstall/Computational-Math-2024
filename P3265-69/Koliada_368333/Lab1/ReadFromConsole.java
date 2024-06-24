package Lab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс чтения матрицы с консоли
 * 
 * @author Анастасия Клоляда
 *
 */
public class ReadFromConsole implements IGetMatrix {

	/**
	 * Метод интерфейса IGetMatrix, возвращает матрицу
	 */
	@Override
	public Matrix GetMatrix() throws IOException {

		if (!OpenReader())
			return null;
		try {
			int n = GetN();
			if (n == 0)
				return null;
			return ReadMatrix(n);
		} finally {
			CloseReader();
		}
	}

	public int ReadN() throws IOException{
		if (!OpenReader())
			return 0;
		try {
			return  GetN();
		} finally {
			CloseReader();
		}
	}

	/**
	 * Метод создает матрицу в диалоге с пользователем через консоль
	 * 
	 * @param n - количество строк матрицы
	 * @return - матрицу
	 * @throws IOException
	 */
	protected Matrix ReadMatrix(int n) throws IOException {

		Matrix matrix = new Matrix(n);
		for (int i = 0; i < n; i++) {
			// Цикл чтения строки. Этот цикл идет до тех пор, пока в строке не окажется n+1
			// значений double
			String[] line;
			boolean done = false;
			do {
				// Цикл чтения строки. Этот цикл идет до тех пор, пока в строке не будет введено
				// как минимум n+1 значение
				do {
					System.out.printf("Введите через пробел вектор значений для строки номер %d\n", i + 1);
					System.out.println("матрицы и в конце строки через пробел укажите значение B:");
					line = bufferReader.readLine().split(" ");
				} while (line == null || line.length < n + 1);

				try {
					done = matrix.saveString(i, line);
				} catch (NumberFormatException e) {
					System.out.println("Неверный формат числа");
				}

			} while (!done);
		}
		return matrix;
	}

	/**
	 * Метод возвращает размерность матрицы, полученную в диалоге с пользователем
	 * 
	 * @return - размерность матрицы n*n
	 * @throws IOException
	 */
	protected int GetN() throws IOException {
		int n = -1;
		while (n < 0 || n > 20) {
			System.out.println("Введите размерность матрицы (> 0 и <= 20) или 0 для завершения работы:");
			try {
				n = Integer.parseUnsignedInt(bufferReader.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Неверный формат целого числа");
			}
		}
		return n;
	}

	/**
	 * Используемый reader для чтения данных
	 */
	protected BufferedReader bufferReader = null;

	/**
	 * Метод создает требуемый reader
	 * 
	 * @return - true, если reader был создан.
	 * @throws FileNotFoundException - если при создании reader из файла, указанный
	 *                               файл не былд найден.
	 */
	protected boolean OpenReader() throws FileNotFoundException {
		bufferReader = new BufferedReader(new InputStreamReader(System.in));
		return true;
	}

	/**
	 * Метод закрывает reader
	 */
	private void CloseReader() {
		try {
			bufferReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.printf("Ошибка при закрытии источника данных %s\n", e.getMessage());
		}
	}
}
