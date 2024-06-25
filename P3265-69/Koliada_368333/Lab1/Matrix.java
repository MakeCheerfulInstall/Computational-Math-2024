package Lab1;

import java.util.Random;

/**
 * Класс матрицы, производит все манипуляции с матрицой
 * 
 * @author Анастасия Коляда
 *
 */
public class Matrix {
	/**
	 * место, для хранения матрицы и манипуляций с ней
	 */
	private double[][] matrix;
	/**
	 * копия матрицы, здесь всегда оригинальная матрица, как до расчетов
	 */
	private double[][] matrixBackup;
	/**
	 * вектор значений B
	 */
	private double[] b_vector;
	/**
	 * копия вектора значений B
	 */
	private double[] b_vectorBackup;
	/**
	 * Размерность матрицы
	 */
	private int n_length;

	/**
	 * Конструктор матрицы. Создает матрицу и вектор B
	 * 
	 * @param n - размер матрицы
	 */
	public Matrix(int n) {
		matrix = new double[n][n];
		b_vector = new double[n];
		n_length = n;
	}

	public Matrix(double[][] matrix, double[] b) {
		this.matrix = matrix;
		this.b_vector = b;
		n_length = matrix.length;
	}

	/**
	 * Метод производит необходимые расчеты и выводит результат
	 */
	public void calculateAll() {
		try {
			// Сохранеят копию матрицы, так как все манипуляции проихводятся с основными
			// элементами
			createBackup();
			printMatrix();
			System.out.printf("Определитель матрицы равен %.5f\n", calculateDeterminant());
			// Расчет СЛАУ методом Гаусса
			calcGauss();
		} catch (MatrixException e) {
			System.out.printf("Ошибка при расчете матрицы: %s\n", e.getMessage());
		}
	}

	/**
	 * Производит расчет методом Гаусса
	 * 
	 * @throws MatrixException - если матрицу невозможно посчитать.
	 */
	private void calcGauss() throws MatrixException {
		System.out.println("Расчет СЛАУ методом Гаусса.");
		runForward();
		System.out.println("Треугольная матрица:");
		printMatrix();
		System.out.println("Вектор результатов x:");
		double[] res = runBackward();
		printVector(res);
		System.out.println("Вектор невязок r:");
		res = calcInconsistencesOnBackup(res);
		printVector(res);
	}

	/**
	 * Рассчитывает матрицу без печати результатов
	 * @return результат расчета
	 * @throws MatrixException - ошибка при расчете матрицы
	 */
	public double[] calcMatrix() throws MatrixException {
		double dt = calculateDeterminant();
		//if(dt == 0) throw new MatrixException("Определитель матрицы равен 0");
		runForward();
		return runBackward();
	}

	/**
	 * Расчет невязок
	 * 
	 * @param xs - вектор результатов x
	 * @return - вектор невязок
	 */
	private double[] calcInconsistencesOnBackup(double[] xs) {
		double[] inc = new double[n_length];
		for (int row = 0; row < n_length; row++) {
			double sum = 0;
			for (int col = 0; col < n_length; col++) {
				sum += matrixBackup[row][col] * xs[col];
			}
			inc[row] = sum - b_vectorBackup[row];
		}
		return inc;
	}

	/**
	 * Сохраняет копию матрицы
	 */
	private void createBackup() {
		matrixBackup = new double[n_length][n_length];
		b_vectorBackup = new double[n_length];
		for (int row = 0; row < n_length; row++) {
			System.arraycopy(matrix[row],0,matrixBackup[row],0,n_length);
			b_vectorBackup[row] = b_vector[row];
		}
	}

	/**
	 * Расчет определителя методом Саррюса
	 * 
	 * @return - рассчитанный определитель
	 */
	private double calculateDeterminant() {

		double plus = 0;
		double minus = 0;
		for (int shift = 0; shift < n_length; shift++) {
			double dp = 1;
			// Расчет по основной диагонали
			for (int row = 0; row < n_length; row++) {
				int col = row + shift;
				if (col >= n_length)
					col -= n_length;
				dp *= matrix[row][col];
			}
			plus += dp;
			// Расчет по побочной диагонали
			dp = 1;
			int pos = 0;
			for (int row = n_length - 1; row >= 0; row--) {
				int col = pos + shift;
				if (col >= n_length)
					col -= n_length;
				dp *= matrix[row][col];
				pos++;
			}
			minus += dp;
		}
		return plus - minus;

	}

		/**
		 * Прямой проход по методу Гаусса
		 *
		 * @throws MatrixException
		 */
		private void runForward() throws MatrixException {
			int lMargin = 0;
			// Для каждой строки матрицы
			for (int l = 0; l < n_length; l++) {
				// Если в текущей позиции строки стоит 0, то поищем замену
				if (matrix[l][lMargin] == 0) {
					swapLine(l, lMargin);
				}
				// Производим исключение позиции lMargin из следующих строк
				// Для каждой следующей строки
				for (int l2 = l + 1; l2 < n_length; l2++) {
					// Определяем коэффициент для исключения позиции lMargin
					double factor = 0 - matrix[l2][lMargin] / matrix[l][lMargin];
					// Получаем результат умножения текущей верхней строки на коэффициент
					double[] toAdd = multVc(l, factor);
					// Добавляем его к текущей строке
					addToLine(l2, toAdd);
					b_vector[l2] += b_vector[l] * factor;
				}
				lMargin++;
			}
		}

	/**
	 * Обратный проход по методу Гаусса
	 * 
	 * @return - вектор решений x
	 */
	private double[] runBackward() {
		double[] result = new double[n_length];
		for (int row = n_length - 1; row >= 0; row--) {
			result[row] = (b_vector[row] - sumOfRightPart(row, result)) / matrix[row][row];
		}
		return result;
	}

	/**
	 * Вспомогательный метод - рассчитывает при обратном ходе сумму элементов с
	 * известными значениями
	 * 
	 * @param row    - строка, для которой производится расчет
	 * @param result - вектор решений СЛАУ
	 * @return - искомая сумма
	 */
	private double sumOfRightPart(int row, double[] result) {
		double sum = 0;
		for (int c = row + 1; c < n_length; c++) {
			sum += result[c] * matrix[row][c];
		}
		return sum;
	}

	/**
	 * Вспомогательный метод добавляет к строке матрицы значения из расчетного
	 * массива
	 *
	 * @param line
	 * @param toAdd
	 */
	private void addToLine(int line, double[] toAdd) {
		for(int i = 0;i<n_length;i++){
			matrix[line][i] += toAdd[i];
		}
	}

	/**
	 * Метод умножает строку матрицы на указанный коэффициент
	 * 
	 * @param line   - индекс строки матрицы
	 * @param factor - коэффициент
	 * @return - вектор результат умножения каждого элемента строки на коэффициент
	 */
	private double[] multVc(int line, double factor) {
		double[] result = new double[n_length];
		for (int i = 0; i < n_length; i++) {
			result[i] = matrix[line][i] == 0 ? 0 : matrix[line][i] * factor;
		}
		return result;
	}

	/**
	 * Ищет строку с ненулевой величиной в позиции col и меняет эту строку местами
	 * со строкой line. Если такой строки нет, то выбрасывается ошибка
	 * 
	 * @param line - номе строки
	 * @param col  - номер колонки
	 * @throws MatrixException
	 */
	private void swapLine(int line, int col) throws MatrixException {
		for (int l = line + 1; l < n_length; l++) {
			if (matrix[l][col] != 0) {
				double[] temp = matrix[line];
				matrix[line] = matrix[l];
				matrix[l] = temp;

				double tb = b_vector[line];
				b_vector[line] = b_vector[l];
				b_vector[l] = tb;
				return;
			}
		}
		throw new MatrixException("Нет строки с ненулевым элементом для перемены строк");
	}

	public void buildRandom() {
		Random random = new Random();
		for (int row = 0; row < n_length;row++){
			for(int col = 0; col < n_length;col++){
				matrix[row][col] = random.nextDouble(100);
			}
			b_vector[row] = random.nextDouble(100);
		}
	}

	/**
	 * сохраняет текстовую строку в матрице
	 * 
	 * @param lineNumber - номер строки
	 * @param s          - строка из массива чисел в текстовом представлении
	 * @return - true, если строка сохранена
	 * @throws NumberFormatException - в результате ошиюбки при преобразования чисел
	 *                               из текста в double
	 */
	public boolean saveString(int lineNumber, String[] s) throws NumberFormatException {
		if (lineNumber < 0 || lineNumber >= n_length)
			return false;
		int i;
		for (i = 0; i < n_length; i++) {
			matrix[lineNumber][i] = Double.parseDouble(s[i]);
		}
		b_vector[lineNumber] = Double.parseDouble(s[i]);
		return true;
	}

	/**
	 * Выводит текущую матрицу на консоль
	 */
	private void printMatrix() {
		printMatrix(matrix, b_vector);
	}

	/**
	 * Выводит на консоль указанный вектор
	 * 
	 * @param line - вектор чисел для вывода
	 */
	public static void printVector(double[] line) {
		System.out.print("(");
		for (double cell : line) {
			System.out.printf("%13.13s", String.format("%.6f", cell));
		}
		System.out.print(")\n");
		System.out.println("Конец результата");
	}

	/**
	 * Выводит на консоль указанную СЛАУ
	 * 
	 * @param m - матрица
	 * @param b - вектор B
	 */
	private static void printMatrix(double[][] m, double[] b) {
		int bc = 0;
		for (double[] line : m) {
			for (double cell : line) {
				System.out.printf("%13.13s", String.format("%.6f", cell));
			}
			System.out.printf("  |  %f\n", b[bc++]);
		}
		System.out.println("Конец матрицы.");
	}
}
