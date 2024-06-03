import { builtinEquations } from "./logic/equations";
import {
  InputData,
  Interval,
  NumberFunction,
  OrdinaryDifferentialEquation,
  Point,
} from "./logic/definitions";

/** Абстрактный класс чтения входных данных. */
abstract class Reader {
  protected abstract get selectedEquationIndex(): number;

  protected abstract get rawX0(): string;

  protected abstract get rawY0(): string;

  protected abstract get rawDifferentiateIntervalStart(): string;

  protected abstract get rawDifferentiateIntervalEnd(): string;

  protected abstract get rawStep(): string;

  protected abstract get rawEpsilon(): string;

  /** Считать входные данные. */
  public async read(): Promise<InputData> {
    return {
      equation: this.readEquation(),
      startCondition: this.readStartCondition(),
      differentiateInterval: this.readDifferentiateInterval(),
      step: this.readStep(),
      epsilon: this.readEpsilon(),
    };
  }

  private readEquation(): OrdinaryDifferentialEquation {
    return builtinEquations[this.selectedEquationIndex];
  }

  private readStartCondition(): Point {
    return {
      x: this._parseFloatWithComma(this.rawX0),
      y: this._parseFloatWithComma(this.rawY0),
    };
  }

  private readDifferentiateInterval(): Interval {
    return {
      start: this._parseFloatWithComma(this.rawDifferentiateIntervalStart),
      end: this._parseFloatWithComma(this.rawDifferentiateIntervalEnd),
    };
  }

  private readStep(): number {
    return this._parseFloatWithComma(this.rawStep);
  }

  private readEpsilon(): number {
    return this._parseFloatWithComma(this.rawEpsilon);
  }

  private _parseFloatWithComma(value: string): number {
    value = value.replace(",", ".");
    return parseFloat(value);
  }
}

/**
 * Класс для чтения входных данных из формы.
 */
class ReaderFromForm extends Reader {
  protected get selectedEquationIndex(): number {
    const inputTypeOptions = document.getElementsByName("equation");

    for (let i = 0; i < inputTypeOptions.length; i++) {
      const optionElement = inputTypeOptions[i] as HTMLInputElement;
      if (optionElement.checked) {
        return i;
      }
    }

    throw new ReadError("Не выбрано уравнение");
  }

  protected get rawX0(): string {
    const input = document.querySelector('[name="x0"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawY0(): string {
    const input = document.querySelector('[name="y0"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawDifferentiateIntervalStart(): string {
    const input = document.querySelector('[name="start"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawDifferentiateIntervalEnd(): string {
    const input = document.querySelector('[name="end"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawStep(): string {
    const input = document.querySelector('[name="h"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawEpsilon(): string {
    const input = document.querySelector(
      '[name="epsilon"]'
    ) as HTMLInputElement;
    return input.value;
  }
}

class ReadError extends Error {
  constructor(message: string) {
    super(message);
    this.name = "ReadError";
  }
}

/**
 * Получить экземпляр класса для чтения входных данных в зависимости от того,
 * что выбрал пользователь.
 */
export function getReader(): Reader {
  return new ReaderFromForm();
}
