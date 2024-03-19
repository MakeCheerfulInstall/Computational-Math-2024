import { EquationInputData, EquationSolvingMethod, FunctionData, functions } from "./solvers/definitions";

/** Абстрактный класс чтения входных данных. */
abstract class Reader {

  protected abstract get rawStart(): string;

  protected abstract get rawEnd(): string;

  protected abstract get rawEpsilon(): string;

  protected abstract get rawFunctionData(): string;

  protected abstract get rawIntegralSolvingMethod(): EquationSolvingMethod;

  /** Считать входные данные. */
  public async read(): Promise<EquationInputData> {
    return {
      start: this.readStart(),
      end: this.readEnd(),
      epsilon: this.readEpsilon(),
      method: this.readIntegralSolvingMethod(),
      functionData: this.readFunctionData(),
    }
  };

  public readStart(): number {
    const result = this._parseFloatWithComma(this.rawStart);
    if (isNaN(result)) {
      throw new ReadError('Левая граница интервала должна быть числом');
    }

    return result;
  }

  public readEnd(): number {
    const result = this._parseFloatWithComma(this.rawEnd);
    if (isNaN(result)) {
      throw new ReadError('Правая граница интервала должна быть числом');
    }

    return result;
  }

  public readEpsilon(): number {
    const epsilon = this._parseFloatWithComma(this.rawEpsilon);
    if (isNaN(epsilon)) {
      throw new ReadError('Эпсилон должно быть числом');
    } else if (epsilon <= 0) {
      throw new ReadError('Эпсилон должен быть положительным');
    } else if (epsilon >= 1000000) {
      throw new ReadError('Эпсилон должен быть меньше 1000000');
    }
    console.log("epsilon: " + epsilon)
    return epsilon;
  }

  public readFunctionData(): FunctionData {
    const functionIndex = parseInt(this.rawFunctionData, 10);
    if (isNaN(functionIndex)) {
      throw new ReadError('Индекс для функции не число');
    } else if (functionIndex < 0 || functionIndex >= functions.length) {
      throw new ReadError('Неверный индекс для функции');
    }

    return functions[functionIndex];
  }

  public readIntegralSolvingMethod(): EquationSolvingMethod {
    return this.rawIntegralSolvingMethod;
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

  protected override get rawEpsilon(): string {
    const input = document.querySelector('input[name="epsilon"]') as HTMLInputElement;
    return input.value;
  }

  protected override get rawEnd(): string {
    const input = document.querySelector('input[name="end"]') as HTMLInputElement;
    return input.value;
  }

  protected override get rawFunctionData(): string {
    const funcs = document.querySelectorAll('input[name="function"]');
    for (let i = 0; i < funcs.length; i++) {
      const func = funcs[i] as HTMLInputElement;
      if (func.checked) {
        return func.value;
      }
    }

    throw new ReadError('Не выбрана функция');
  }

  protected override get rawStart(): string {
    const input = document.querySelector('input[name="start"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawIntegralSolvingMethod(): EquationSolvingMethod {
    const integralSolvingMethodOptions = (
      document.getElementsByName('integral-solving-method')
    );

    for (let i = 0; i < integralSolvingMethodOptions.length; i++) {
      const optionElement = integralSolvingMethodOptions[i] as HTMLInputElement;
      if (optionElement.checked) {
        return optionElement.value as EquationSolvingMethod;
      }
    }

    throw new ReadError('Не выбран метод интегрирования');
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
