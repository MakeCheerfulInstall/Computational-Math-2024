import {
  EquationInputData,
  EquationSolvingMethod,
  FunctionData,
  functions,
} from "./solvers/equation-solver";
import {
  EquationSystem,
  EquationSystemSolvingMethod,
  systems,
} from "./solvers/equation-system-solver";
import { File } from "node:buffer";

export type TaskType = "equation" | "system";

/**
 * Входные данные для решения системы линейных уравнений.
 *
 * @property epsilon Точность вычислений.
 * @property maxIterations - Максимальное количество итераций
 * @property x0 Начальное приближение X
 * @property y0 Начальное приближение Y
 * @property system Система 2 уравнений
 */
export interface EquationSystemInputData {
  epsilon: number;
  maxIterations: number;
  x0: number;
  y0: number;
  system: EquationSystem;
  method: EquationSystemSolvingMethod;
}

/** Абстрактный класс чтения входных данных. */
abstract class Reader {
  protected abstract get rawTaskType(): TaskType;

  protected abstract get rawMaxIterations(): string;

  protected abstract get rawStart(): string;

  protected abstract get rawEnd(): string;

  protected abstract get rawEpsilon(): string;

  protected abstract get rawFunctionData(): string;

  protected abstract get rawX0(): string;

  protected abstract get rawY0(): string;

  protected abstract get rawSystemData(): string;

  protected abstract get rawEquationSolvingMethod(): EquationSolvingMethod;

  protected abstract get rawSystemSolvingMethod(): EquationSystemSolvingMethod;

  /** Считать входные данные. */
  public async read(): Promise<{
    data: EquationInputData | EquationSystemInputData;
    type: TaskType;
  }> {
    switch (this.readTaskType()) {
      case "equation":
        return { type: "equation", data: this._readEquationInputData() };
      case "system":
        return { type: "system", data: this._readEquationSystemInputData() };
    }
  }

  public readTaskType(): TaskType {
    return this.rawTaskType;
  }

  public readMaxIterations(): number {
    if (
      this.rawMaxIterations.includes(".") ||
      this.rawMaxIterations.includes(",")
    ) {
      throw new ReadError("Количество итераций должно быть целым");
    }

    const result = parseInt(this.rawMaxIterations, 10);
    if (isNaN(result)) {
      throw new ReadError("Неверное значение количества итераций");
    }

    return result;
  }

  public readStart(): number {
    const result = this._parseFloatWithComma(this.rawStart);
    if (isNaN(result)) {
      throw new ReadError("Левая граница интервала должна быть числом");
    }

    return result;
  }

  public readEnd(): number {
    const result = this._parseFloatWithComma(this.rawEnd);
    if (isNaN(result)) {
      throw new ReadError("Правая граница интервала должна быть числом");
    }

    return result;
  }

  public readEpsilon(): number {
    const epsilon = this._parseFloatWithComma(this.rawEpsilon);
    if (isNaN(epsilon)) {
      throw new ReadError("Эпсилон должно быть числом");
    } else if (epsilon <= 0) {
      throw new ReadError("Эпсилон должен быть положительным");
    } else if (epsilon >= 1000000) {
      throw new ReadError("Эпсилон должен быть меньше 1000000");
    }
    console.log("epsilon: " + epsilon);
    return epsilon;
  }

  public readFunctionData(): FunctionData {
    const functionIndex = parseInt(this.rawFunctionData, 10);
    if (isNaN(functionIndex)) {
      throw new ReadError("Индекс для функции не число");
    } else if (functionIndex < 0 || functionIndex >= functions.length) {
      throw new ReadError("Неверный индекс для функции");
    }

    return functions[functionIndex];
  }

  public readX0(): number {
    const result = this._parseFloatWithComma(this.rawX0);
    if (isNaN(result)) {
      throw new ReadError("X0 должен быть числом");
    }

    return result;
  }

  public readY0(): number {
    const result = this._parseFloatWithComma(this.rawY0);
    if (isNaN(result)) {
      throw new ReadError("Y0 должен быть числом");
    }

    return result;
  }

  public readSystemData(): EquationSystem {
    const systemIndex = parseInt(this.rawSystemData, 10);
    if (isNaN(systemIndex)) {
      throw new ReadError("Индекс для системы не число");
    } else if (systemIndex < 0 || systemIndex >= systems.length) {
      throw new ReadError("Неверный индекс для системы");
    }

    return systems[systemIndex];
  }

  public readEquationSolvingMethod(): EquationSolvingMethod {
    return this.rawEquationSolvingMethod;
  }

  public readSystemSolvingMethod(): EquationSystemSolvingMethod {
    return this.rawSystemSolvingMethod;
  }

  private _parseFloatWithComma(value: string): number {
    value = value.replace(",", ".");
    return parseFloat(value);
  }

  private _readEquationInputData(): EquationInputData {
    const result = {
      maxIterations: this.readMaxIterations(),
      start: this.readStart(),
      end: this.readEnd(),
      epsilon: this.readEpsilon(),
      functionData: this.readFunctionData(),
      method: this.readEquationSolvingMethod(),
    };

    if (result.start > result.end) {
      throw new ReadError("Левая граница интервала должна быть меньше правой");
    }

    return result;
  }

  private _readEquationSystemInputData(): EquationSystemInputData {
    return {
      maxIterations: this.readMaxIterations(),
      x0: this.readX0(),
      y0: this.readY0(),
      epsilon: this.readEpsilon(),
      system: this.readSystemData(),
      method: this.readSystemSolvingMethod(),
    };
  }
}

/**
 * Класс для чтения входных данных из формы.
 */
class ReaderFromForm extends Reader {
  protected get rawTaskType(): TaskType {
    const taskTypeButton = document.getElementsByName("task-type");
    for (let i = 0; i < taskTypeButton.length; i++) {
      const optionElement = taskTypeButton[i] as HTMLInputElement;
      if (optionElement.checked) {
        return optionElement.value as TaskType;
      }
    }

    throw new ReadError("Не выбран тип задачи");
  }

  protected override get rawEpsilon(): string {
    const input = document.querySelector(
      'input[name="epsilon"]'
    ) as HTMLInputElement;
    return input.value;
  }

  protected override get rawEnd(): string {
    const input = document.querySelector(
      'input[name="end"]'
    ) as HTMLInputElement;
    return input.value;
  }

  protected override get rawFunctionData(): string {
    const equations = document.querySelectorAll('input[name="equation"]');
    for (let i = 0; i < equations.length; i++) {
      const equation = equations[i] as HTMLInputElement;
      if (equation.checked) {
        return equation.value;
      }
    }

    throw new ReadError("Не выбрано уравнение");
  }

  protected override get rawMaxIterations(): string {
    const input = document.querySelector(
      'input[name="max-iterations"]'
    ) as HTMLInputElement;
    return input.value;
  }

  protected override get rawStart(): string {
    const input = document.querySelector(
      'input[name="start"]'
    ) as HTMLInputElement;
    return input.value;
  }

  protected get rawSystemData(): string {
    const systems = document.querySelectorAll('input[name="system"]');
    for (let i = 0; i < systems.length; i++) {
      const system = systems[i] as HTMLInputElement;
      if (system.checked) {
        return system.value;
      }
    }

    throw new ReadError("Не выбрана система");
  }

  protected get rawX0(): string {
    const input = document.querySelector(
      'input[name="x0"]'
    ) as HTMLInputElement;
    return input.value;
  }

  protected get rawY0(): string {
    const input = document.querySelector(
      'input[name="y0"]'
    ) as HTMLInputElement;
    return input.value;
  }

  protected get rawEquationSolvingMethod(): EquationSolvingMethod {
    const equationSolvingMethodOptions = document.getElementsByName(
      "equation-solving-method"
    );

    for (let i = 0; i < equationSolvingMethodOptions.length; i++) {
      const optionElement = equationSolvingMethodOptions[i] as HTMLInputElement;
      if (optionElement.checked) {
        return optionElement.value as EquationSolvingMethod;
      }
    }

    throw new ReadError("Не выбран метод решения уравнения");
  }

  protected get rawSystemSolvingMethod(): EquationSystemSolvingMethod {
    const equationSolvingMethodOptions = document.getElementsByName(
      "system-solving-method"
    );

    for (let i = 0; i < equationSolvingMethodOptions.length; i++) {
      const optionElement = equationSolvingMethodOptions[i] as HTMLInputElement;
      if (optionElement.checked) {
        return optionElement.value as EquationSystemSolvingMethod;
      }
    }

    throw new ReadError("Не выбран метод решения системы");
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

  public override async read() {
    let content: string;
    try {
      content = await this.file.text();
    } catch (e) {
      throw new ReadError(
        "Не удалось считать файл, попробуйте его переоткрыть"
      );
    }

    this.fileLines = content.split("\n").map((s) => s.trim());
    return super.read();
  }

  protected get rawMaxIterations(): string {
    return this.fileLines[0].trim();
  }

  protected get rawStart(): string {
    return this.fileLines[3].split(/\s/)[0].trim();
  }

  protected get rawEnd(): string {
    return this.fileLines[3].split(/\s/)[1].trim();
  }

  protected get rawEpsilon(): string {
    return this.fileLines[1].trim();
  }

  protected get rawFunctionData(): string {
    return this.fileLines[4].trim();
  }

  protected get rawTaskType(): TaskType {
    return this.fileLines[2].trim() as TaskType;
  }

  protected get rawEquationSolvingMethod(): EquationSolvingMethod {
    const result = this.fileLines[5].trim();

    if (
      [
        "bisection",
        "chords",
        "newton",
        "secants",
        "simple-iterations",
      ].includes(result)
    ) {
      return result as EquationSolvingMethod;
    }

    throw new ReadError("Неверный метод решения уравнения");
  }

  protected get rawSystemSolvingMethod(): EquationSystemSolvingMethod {
    const result = this.fileLines[5].trim();

    if (["system-newton", "system-simple-iterations"].includes(result)) {
      return result as EquationSystemSolvingMethod;
    }

    throw new ReadError("Неверный метод решения системы");
  }

  protected get rawX0(): string {
    return this.fileLines[3].split(/\s/)[0].trim();
  }
  protected get rawY0(): string {
    return this.fileLines[3].split(/\s/)[1].trim();
  }
  protected get rawSystemData(): string {
    return this.fileLines[4].trim();
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
  const fileInput = document.querySelector(
    'input[name="input-file"]'
  ) as HTMLInputElement;
  const files = fileInput.files;

  if (files && files.length > 0) {
    // @ts-ignore
    return new ReaderFromFile(files[0]);
  } else {
    return new ReaderFromForm();
  }
}
