import {EquationSystemResult} from "../printer";
import {EquationSystemInputData} from "../reader";

export const MAX_ABSOLUTE_VALUE = Math.pow(10, 10);

export type EquationSystemSolvingMethod = 'system-newton' | 'system-simple-iterations';

/** Система 2 уравнений */
export interface EquationSystem {
  f: FunctionInsideSystem;
  g: FunctionInsideSystem;
}

/** Функция и ее производные по каждой переменной */
export interface FunctionInsideSystem {
  value: TwoVariablesFunction; // сама функция
  derivativeX: TwoVariablesFunction; // ее производная по X
  derivativeY: TwoVariablesFunction; // ее производная по Y
  phi: TwoVariablesFunction // функция phi для метода простых итераций
  printableValue: string; // Функция в виде строки
}

/** Массив систем функций */
export const systems: EquationSystem[] = [
  { // из презентации на МПИ
    f: {
      value: (x: number, y: number): number => 0.1 * x * x + x + 0.2 * y * y - 0.3,
      derivativeX: (x: number, y: number): number => 0.2 * x + 1,
      derivativeY: (x: number, y: number): number => 0.4 * y,
      phi: (x: number, y: number): number => - 0.1 * x * x - 0.2 * y * y + 0.3,
      printableValue: '0.1 * x^2 + x + 0.2 * y^2 - 0.3',
    },
    g: {
      value: (x: number, y: number): number => 0.2 * x * x + y + 0.1 * x * y - 0.7,
      derivativeX: (x: number, y: number): number => 0.4 * x + 0.1 * y,
      derivativeY: (x: number, y: number): number => 1 + 0.1 * x,
      phi: (x: number, y: number): number => -0.2 * x * x - 0.1 * x * y + 0.7,
      printableValue: '0.2 * x^2 + y + 0.1 * x * y - 0.7',
    },
  },
  { // из презентации на Ньютона
    f: {
      value: (x: number, y: number): number => x * x + y * y - 4,
      derivativeX: (x: number, y: number): number => 2 * x,
      derivativeY: (x: number, y: number): number => 2 * y,
      phi:(x: number, y: number): number => Math.sqrt(- y * y + 4),
      printableValue: 'x * x + y * y - 4',
    },
    g: {
      value: (x: number, y: number): number => y - 3 * x * x,
      derivativeX: (x: number, y: number): number => -6 * x,
      derivativeY: (x: number, y: number): number => 1,
      phi:(x: number, y: number): number => 3 * x * x,
      printableValue: 'y - 3 * x * x',
    },
  },
  { // 2 вариант
    f: {
      value: (x: number, y: number): number => Math.tan(x * y + 0.1) - x ** 2,
      derivativeX: (x: number, y: number): number => y * (1 / (Math.cos(x * y + 0.1)) ** 2) - 2 * x,
      derivativeY: (x: number, y: number): number => x * (1 / (Math.cos(x * y + 0.1)) ** 2),
      phi:(x: number, y: number): number => Math.sqrt(Math.tan(x * y + 0.1)),
      printableValue: 'tan(x*y + 0.1) - x^2',
    },
    g: {
      value: (x: number, y: number): number => x ** 2 + 2 * (y ** 2) - 1,
      derivativeX: (x: number, y: number): number => 2 * x,
      derivativeY: (x: number, y: number): number => 2 * y,
      phi:(x: number, y: number): number => Math.sqrt((1 - x ** 2) / 2),
      printableValue: 'x^2 + 2y^2 - 1',
    },
  },
  { // 10 вариант
    f: {
      value: (x: number, y: number): number => Math.cos(y - 1) + 2*x + 1.7,
      derivativeX: (x: number, y: number): number => 2,
      derivativeY: (x: number, y: number): number => -Math.sin(y - 1),
      phi:(x: number, y: number): number => -Math.cos(y - 2)/2 - 1.7/2,
      printableValue: 'cos(y - 2) + x',
    },
    g: {
      value: (x: number, y: number): number => y - Math.cos(x) - 0.8,
      derivativeX: (x: number, y: number): number => -Math.sin(x),
      derivativeY: (x: number, y: number): number => 1,
      phi:(x: number, y: number): number => Math.cos(x) + 0.8,
      printableValue: 'sin(x + 0.5) - y - 1', 
    }
  }
];

/** Тип: функция получающая число и возвращающая число */
export type TwoVariablesFunction = (x: number, y: number) => number;

/** Абстрактный класс чтения входных данных. */
export abstract class EquationSystemSolver {
  public abstract solve(data: EquationSystemInputData): EquationSystemResult;

  public calcDiscrepancy(x: number, y: number, F: FunctionInsideSystem, G: FunctionInsideSystem) {
    return [Math.abs(F.value(x, y)), Math.abs(G.value(x, y))]
  }
}
