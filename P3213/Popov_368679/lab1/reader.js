/**
 * Входные данные для решения системы линейных уравнений.
 *
 * @property size Количество неизвестных.
 * @property matrix Матрица коэффициентов.
 * @property rightValues Вектор правых частей (чему равно каждое из уравнений).
 * @property epsilon Точность вычислений.
 */
class InputData {
    constructor(size, matrix, rightValues, epsilon) {
        this.size = size;
        this.matrix = matrix;
        this.rightValues = rightValues;
        this.swap = 0;
    }
}

/** Абстрактный класс чтения входных данных. */
class Reader {
    n;

    /** Считать входные данные. */
    async read() {
        const n = this.readN();
        const matrix = this.readMatrix();
        const rightValues = this.readRightValues();

        return new InputData(n, matrix, rightValues);
    };

    readN() {
        if (this.rawN.includes('.') || this.rawN.includes(',')) {
            throw new ReadError('n должно быть целым');
        }

        this.n = parseInt(this.rawN);
        if (isNaN(this.n)) {
            throw new ReadError('Неверное значение n');
        } else if (this.n < 1 || this.n > 20) {
            throw new ReadError(`n должно быть в диапазоне от 1 до 20`);
        }
        return this.n;
    }

    readMatrix() {
        const n = this.n;
        const numbers = this.rawMatrixAndRightValues.split(/[\s\n]/);
        const matrix = [];

        let oneDimIndex = 0;
        for (let i = 0; i < n; i++) {
            const row = [];
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
        console.log(matrix)
        return matrix;
    }

    readRightValues() {
        const n = this.n;
        const numbers = this.rawMatrixAndRightValues.split(/[\s\n]/);
        const rightValues = [];

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

    _parseFloatWithComma(value) {
        value = value.replace(',', '.');
        return parseFloat(value);
    }
}

/**
 * Класс для чтения входных данных из формы.
 */
class ReaderFromForm extends Reader {

    get rawN() {
        const input = document.querySelector('input[name="n"]');
        return input.value;
    }

    get rawMatrixAndRightValues() {
        const textarea = document.querySelector('textarea[name="matrix"]');
        return textarea.value;
    }

}

/**
 * Класс для чтения входных данных из файла.
 */
class ReaderFromFile extends Reader {
    fileLines;

    constructor(file) {
        super();
        this.file = file;
    }

    async read() {
        let content;
        try {
            content = await this.file.text();
        } catch (e) {
            throw new ReadError('Не удалось считать файл, попробуйте его переоткрыть');
        }

        this.fileLines = content.split('\n').map(s => s.trim());
        return super.read();
    }

    get rawN() {
        if (this.fileLines.length < 1) {
            throw new ReadError('Файл пуст');
        }

        return this.fileLines[0].trim();
    }

    get rawMatrixAndRightValues() {
        const n = this.n;
        if (this.fileLines.length < n + 1) {
            throw new ReadError('Недостаточно строк для матрицы с правыми значениями');
        }
        return this.fileLines.slice(1, n + 1).join('\n');
    }
}

class ReadError extends Error {
    constructor(message) {
        super(message);
        this.name = 'ReadError';
    }
}

/**
 * Получить экземпляр класса для чтения входных данных в зависимости от того,
 * что выбрал пользователь.
 */
export function getReader() {
    const fileInput = document.querySelector('input[name="input-file"]');
    const files = fileInput.files;

    if (files && files.length > 0) {
        return new ReaderFromFile(files[0]);
    } else {
        return new ReaderFromForm();
    }
}