import {EquationResult} from "../printer";

/** Количество выводимых знаков после запятой. */
const CALC_PRECISION = 8;

export type EquationSolvingMethod = 'bisection' | 'chords' | 'newton' | 'secants' | 'simple-iterations';

/** Входные данные от пользователя, которые передаются в метод solve */
export interface EquationInputData {
  start: number;
  end: number;
  epsilon: number;
  maxIterations: number;
  functionData: FunctionData,
  method: EquationSolvingMethod,
}

/** Функция и ее производная */
export interface FunctionData {
  value: NumberFunction, // сама функция
  derivative: NumberFunction // ее производная
  phi: NumberFunction,
  phiDerivative: NumberFunction,
  printableValue: string // Выводимая форма функции
}

/** Массив функций и их производных */
export const functions: FunctionData[] = [
  {
    value: (x: number): number => x**3 - 3.125*(x**2) - 3.5*x + 2.458,
    derivative: (x: number): number => -3.5 - 6.25*x + 3*(x**2),
    phi: (x: number): number => -0.126051 + 1.17949*x + 0.160256*(x**2) - 0.0512821*(x**3),
    phiDerivative: (x: number): number => 1.17949 + 0.320512*x - 0.153846*(x**2),
    printableValue: 'x^3 - 3.125*x^2 - 3.5*x + 2.458',
  },
  {
    value: (x: number): number => 5 * Math.sin(x) - 10 * x,
    derivative: (x: number): number => 5 * Math.cos(x) - 10,
    phi: (x: number): number => 0.5 * Math.sin(x),
    phiDerivative: (x: number): number => 0.5 * Math.cos(x),
    printableValue: '5sin(x) - 10x',
  },
  {
    value: (x: number): number => 9 * (x ** 2) - 6 * x - 1,
    derivative: (x: number): number => 18 * x - 6,
    phi: (x: number): number => 1.5 * (x ** 2) - 1/6,
    phiDerivative: (x: number): number => 3 * x,
    printableValue: '9x^2 - 6x - 1',
  },
];

/** Тип: функция получающая число и возвращающая число */
export type NumberFunction = (x: number) => number;

/** Абстрактный класс чтения входных данных. */
export abstract class EquationSolver {
  public abstract solve(data: EquationInputData): EquationResult;

  /** Находится ли x внутри изначально заданного интервала? */
  public checkIsInsideInterval(x: number, data: EquationInputData): boolean {
    if (!(data.start <= x && x <= data.end)){
      alert("Последовательность выходит за пределы установленного отрезка. " +
        "Выбранный метод не подходит для решения данного уравнения на этом отрезке. " +
        "Выводим значение последней итерации");
      return false;
    }
    return true;
  }

  /** True, если дельта >= epsilon */
  public checkDeltaMoreEpsilon(a: number, b: number, x: number, data: EquationInputData): boolean {
    if (Math.abs(data.functionData.value(x)) < data.epsilon){
      return false;
    } else if (Math.abs(a - b) < Math.pow(0.1, CALC_PRECISION)){
      alert("Нет корней на данном отрезке. " +
        "Выводим значение последней итерации");
      return false;
    }
    return true;
  }

  public handleOutEpsilonByIterationsCount(data: EquationInputData, iterations: number, fValue?: number): void {
    if (fValue && iterations == data.maxIterations && Math.abs(fValue) >= data.epsilon){
      alert("Значение недостаточно точное из-за недостаточного допустимого количества итераций. " +
        "Выводим значение последней итерации");
    }
  }
}
