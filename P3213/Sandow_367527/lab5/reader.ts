import { InputData, NumberFunction, Point } from "./logic/definitions";

type InputType = "points" | "function";

const EPS = 1e-2;

/** Абстрактный класс чтения входных данных. */
abstract class Reader {
  private readonly functionsForPoints: NumberFunction[] = [
    (x) => Math.sin(x),
    (x) => x ** 3,
  ];

  protected abstract get rawX(): string;

  protected abstract get rawInputType(): InputType;

  protected abstract get rawPointsX(): string;

  protected abstract get rawPointsY(): string;

  protected abstract get rawFunctionIndex(): number;

  protected abstract get rawFunctionIntervalStart(): string;

  protected abstract get rawFunctionIntervalEnd(): string;

  protected abstract get rawFunctionPointsCount(): string;

  private get functionForPoints(): NumberFunction {
    return this.functionsForPoints[this.rawFunctionIndex];
  }

  private get pointsFromFunction(): Point[] {
    const points: Point[] = [];
    const start = this._parseFloatWithComma(this.rawFunctionIntervalStart);
    const end = this._parseFloatWithComma(this.rawFunctionIntervalEnd);
    const count = parseInt(this.rawFunctionPointsCount) - 1;
    const step = (end - start) / count;
    for (let i = 0; i <= count; i++) {
      const x = start + i * step;
      points.push({ x, y: this.functionForPoints(x) });
    }
    return points;
  }

  /** Считать входные данные. */
  public async read(): Promise<InputData> {
    return {
      points: this.readPoints(),
      size: this.readPoints().length,
      xVal: this.readX(),
    };
  }

  private readX(): number {
    return this._parseFloatWithComma(this.rawX);
  }

  private readPoints(): Point[] {
    switch (this.rawInputType) {
      case "function":
        return this.pointsFromFunction;
      case "points":
        const pointsX = this.rawPointsX.trim().split(/\s/);
        const pointsY = this.rawPointsY.trim().split(/\s/);
        if (pointsX.length !== pointsY.length) {
          throw new ReadError("Количество X и Y точек не совпадает");
        }
        return this._sortAndFixDuplicateX(
          pointsX.map((x, i) => ({
            x: this._parseFloatWithComma(x),
            y: this._parseFloatWithComma(pointsY[i]),
          }))
        );
    }
  }

  private _parseFloatWithComma(value: string): number {
    value = value.replace(",", ".");
    return parseFloat(value);
  }

  private _sortAndFixDuplicateX(points: Point[]): Point[] {
    points.sort();

    for (let i = 1; i < points.length; i++) {
      if (points[i].x === points[i - 1].x) {
        points[i].x += EPS;
      }
    }

    return points;
  }
}

/**
 * Класс для чтения входных данных из формы.
 */
class ReaderFromForm extends Reader {
  protected get rawX(): string {
    const input = document.querySelector('[name="x"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawInputType(): InputType {
    const inputTypeOptions = document.getElementsByName("input-type-select");

    for (let i = 0; i < inputTypeOptions.length; i++) {
      const optionElement = inputTypeOptions[i] as HTMLInputElement;
      if (optionElement.checked) {
        return optionElement.value as InputType;
      }
    }

    throw new ReadError("Не выбран тип ввода данных");
  }

  protected get rawPointsX(): string {
    const input = document.querySelector(
      '[name="points-x"]'
    ) as HTMLTextAreaElement;
    return input.value;
  }

  protected get rawPointsY(): string {
    const input = document.querySelector(
      '[name="points-y"]'
    ) as HTMLTextAreaElement;
    return input.value;
  }

  protected get rawFunctionIndex(): number {
    const inputTypeOptions = document.getElementsByName("function-select");

    for (let i = 0; i < inputTypeOptions.length; i++) {
      const optionElement = inputTypeOptions[i] as HTMLInputElement;
      if (optionElement.checked) {
        return i;
      }
    }

    throw new ReadError("Не выбрана функция");
  }

  protected get rawFunctionIntervalStart(): string {
    const input = document.querySelector('[name="start"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawFunctionIntervalEnd(): string {
    const input = document.querySelector('[name="end"]') as HTMLInputElement;
    return input.value;
  }

  protected get rawFunctionPointsCount(): string {
    const input = document.querySelector(
      '[name="points-count"]'
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
