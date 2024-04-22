/** Абстрактный класс чтения входных данных. */
class Reader {
    
    get rawTableFunction(){};

  /** Считать входные данные. */
  async read(){
    return this.readTableFunction();
  };

  readTableFunction() {
    const tableFunction = this.rawTableFunction;
    const lines = tableFunction.split('\n');
    const n = lines.length;
    if (n < 8 || n > 11) {
      throw new ReadError('Количество строк в таблице должно быть от 8 до 11');
    }
    const x = [];
    const y = [];
    for (let i = 0; i < n; i++) {
      const [xi, yi] = lines[i].split(' ').map(this._parseFloatWithComma);
      x.push(xi);
      y.push(yi);
    }
    return { n, x, y };
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

  get rawTableFunction() {
    const tableFunction = (document.getElementById('function-table'))?.value;
    if (!tableFunction) {
      throw new ReadError('Не введена таблица значений функции');
    }
    return tableFunction;
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
  return new ReaderFromForm();
}
