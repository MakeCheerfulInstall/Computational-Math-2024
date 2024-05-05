import { MAX_N } from './solver.js';

/**
 * Входные данные для решения системы линейных уравнений.
 *
 * @property size Количество неизвестных.
 * @property matrix Матрица коэффициентов.
 * @property rightValues Вектор правых частей (чему равно каждое из уравнений).
 * @property epsilon Точность вычислений.
 */
export interface InputData {
  size: number;
  matrix: number[][];
  rightValues: number[];
  epsilon: number;
}

/** Абстрактный класс чтения входных данных. */
abstract class Reader {
  
  protected n!: number;
  
  /** Строковое значение n, введённое пользователем. */
  protected abstract get rawN(): string;
  
  /** Строковое значение всей матрицы, введённое пользователем. */
  protected abstract get rawMatrixAndRightValues(): string;
  
  /** Строковое значение эпсилон, введённое пользователем. */
  protected abstract get rawEpsilon(): string;
  
  /** Считать входные данные. */
  public async read(): Promise<InputData> {
    const n = this.readN();
    const matrix = this.readMatrix();
    const rightValues = this.readRightValues();
    const epsilon = this.readEpsilon();

    return { size: n, matrix, rightValues, epsilon };
  };

  public readN(): number {
    if (this.rawN.includes('.') || this.rawN.includes(',')) {
      throw new ReadError('n должно быть целым');
    }

    this.n = parseInt(this.rawN);
    if (isNaN(this.n)) {
      throw new ReadError('Неверное значение n');
    } else if (this.n < 1 || this.n > MAX_N) {
      throw new ReadError(`n должно быть в диапазоне от 1 до ${MAX_N}`);
    }
    return this.n;
  }

  public readMatrix(): number[][] {
    const n = this.n;
    const numbers = this.rawMatrixAndRightValues.split(/[\s\n]/);
    const matrix: number[][] = [];
  
    let oneDimIndex = 0;
    for (let i = 0; i < n; i++) {
      const row: number[] = [];
      for (let j = 0; j < n; j++) {
        if (oneDimIndex >= numbers.length) {
          throw new ReadError(`Неверный элемент матрицы на позиции ${i + 1}, ${j + 1}`);
        }
  
        const number = this._parseFloatWithComma(numbers[oneDimIndex]);
        if (isNaN(number)) {
          throw new ReadError(`Неверный элемент матрицы на позиции ${i + 1}, ${j + 1}`);
        }
        row.push(number);
        oneDimIndex++;
      }
      matrix.push(row);
      oneDimIndex++;
    }
  
    return matrix;
  }

  public readRightValues(): number[] {
    const n = this.n;
    const numbers = this.rawMatrixAndRightValues.split(/[\s\n]/);
    const rightValues: number[] = [];

    for (let i = 0; i < n; i++) {
      const rightValueIndex = i * (n + 1) + n;
      if (rightValueIndex >= numbers.length) {
        throw new ReadError(`Неверный элемент правых значений на позиции ${i + 1}, ${n + 1}`);
      }

      const rightValue = this._parseFloatWithComma(numbers[rightValueIndex]);
      if (isNaN(rightValue)) {
        throw new ReadError(`Неверный элемент правых значений на позиции ${i + 1}, ${n + 1}`);
      }
      rightValues.push(rightValue);
    }
    return rightValues;
  }

  public readEpsilon(): number {
    const result = this._parseFloatWithComma(this.rawEpsilon);
    if (isNaN(result)) {
      throw new ReadError('Неверное значение эпсилон');
    } else if (result <= 0) {
      throw new ReadError('Эпсилон должен быть положительным');
    }
    return result;
  }

  private _parseFloatWithComma(value: string): number {
    value = value.replace(',', '.');
    return parseFloat(value);
  }
}

/**
 * Класс для чтения входных данных из формы.
 */
class ReaderFromForm extends Reader {  

  protected override get rawN(): string {
    const input = document.querySelector('input[name="n"]') as HTMLInputElement;
    return input.value;
  }

  protected override get rawMatrixAndRightValues(): string {
    const textarea = document.querySelector('textarea[name="matrix"]') as HTMLTextAreaElement;
    return textarea.value;
  }
  
  protected override get rawEpsilon(): string {
    const input = document.querySelector('input[name="epsilon"]') as HTMLInputElement;
    return input.value;
  }

}

/**
 * Класс для чтения входных данных из файла.
 */
class ReaderFromFile extends Reader {
  private fileLines!: string[];

  constructor(private file: File) {
    super();
  }

  public override async read(): Promise<InputData> {
    let content: string;
    try {
      content = await this.file.text();
    } catch (e) {
      throw new ReadError('Не удалось считать файл, попробуйте его переоткрыть');
    }

    this.fileLines = content.split('\n').map(s => s.trim());
    return super.read();
  }

  protected override get rawN(): string {
    if (this.fileLines.length < 1) {
      throw new ReadError('Файл пуст');
    }

    return this.fileLines[0].trim();
  }

  protected override get rawMatrixAndRightValues(): string {
    const n = this.n;
    if (this.fileLines.length < n + 1) {
      throw new ReadError('Недостаточно строк для матрицы с правыми значениями');
    }
    return this.fileLines.slice(1, n + 1).join('\n');
  }

  protected get rawEpsilon(): string {
    if (this.fileLines.length < this.n + 2) {
      throw new ReadError('Недостаточно строк для эпсилон');
    }

    return this.fileLines[this.n + 1].trim();
  }
}

class ReadError extends Error {
  constructor(message: string) {
    super(message);
    this.name = 'ReadError';
  }
}

/**
 * Получить экземпляр класса для чтения входных данных в зависимости от того,
 * что выбрал пользователь.
 */
export function getReader(): Reader {
  const fileInput = document.querySelector('input[name="input-file"]') as HTMLInputElement;
  const files = fileInput.files;

  if (files && files.length > 0) {
    return new ReaderFromFile(files[0]);
  } else {
    return new ReaderFromForm();
  }
}
