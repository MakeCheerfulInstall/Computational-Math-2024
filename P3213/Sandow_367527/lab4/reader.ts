import { TableFunction } from "./logic/definitions";

/** Абстрактный класс чтения входных данных. */
abstract class Reader {

  protected abstract get rawTableFunction(): string;

  /** Считать входные данные. */
  public async read(): Promise<TableFunction> {
    return this.readTableFunction();
  };

  public readTableFunction(): TableFunction {
    const tableFunction = this.rawTableFunction;
    const lines = tableFunction.split('\n');
    const n = lines.length;
    if (n < 8 || n > 11) {
      throw new ReadError('Количество строк в таблице должно быть от 8 до 11');
    }
    const x: number[] = [];
    const y: number[] = [];
    for (let i = 0; i < n; i++) {
      const [xi, yi] = lines[i].split(' ').map(this._parseFloatWithComma);
      x.push(xi);
      y.push(yi);
    }
    return { n, x, y };
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

  protected get rawTableFunction(): string {
    const tableFunction = (document.getElementById('function-table') as HTMLTextAreaElement)?.value;
    if (!tableFunction) {
      throw new ReadError('Не введена таблица значений функции');
    }
    return tableFunction;
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
  return new ReaderFromForm();
}
