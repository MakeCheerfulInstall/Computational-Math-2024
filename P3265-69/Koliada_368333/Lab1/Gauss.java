package Lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 
 * @author Анастасия Коляда
 *
 */
public class Gauss {
	public static void main(String[] args) {

		//Создаем ридер для чтения с консоли, такой метод чтения работает и из консоли IDE
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Решение СЛАУ методом ГАУССА.");
		System.out.println("Студент Анастасия Коляда");

		int inputType = -1;

		// Цикл считывания действия пользователя по запросу
		do {
			System.out.println(
					"""
							Для продолжения введите:
							    0 - для прекращения работы;
							    1 - если Вы хотите продолжить и ввести размерность матрицы и матрицу с консоли;
							    2 - если Вы хотите указать текстовый файл, который модержиит размерность матрицы, матрицу и вектор B.
							    3 - если сформировать случайную матрицу
							""");

			try {
				inputType = Integer.parseInt(bufferReader.readLine());
			} catch (NumberFormatException | IOException e) {
				System.out.printf("Ошибка чтения %s\n", e.getMessage());
			}
			// До тех пор, пока пользоветль не введет ожидаемые значения
		} while (inputType < 0 || inputType > 3);

		//прекращение работы по запросу
		if (inputType == 0)
			return;

		if(inputType == 3){
			try {
				int n = new ReadFromConsole().ReadN();
				Matrix matrix = new Matrix(n);
				matrix.buildRandom();
				//Запускаем требуемые расчеты
				matrix.calculateAll();
			}
			catch(IOException e) {
				System.out.printf("Ошибка чтения размерности матрицы %s\n", e.getMessage());
			}
		}
		else {
			//Создаем подходящий для запроса ридер
			IGetMatrix reader = inputType == 1 ? new ReadFromConsole() : new ReadFromFile();
			try {
				// Получаем у ридера матрицу
				Matrix matrix = reader.GetMatrix();
				if (matrix == null)
					return;
				//Запускаем требуемые расчеты
				matrix.calculateAll();
			} catch (IOException e) {
				System.out.printf("Ошибка чтения матрицы %s\n", e.getMessage());
			}
		}
	}
}
