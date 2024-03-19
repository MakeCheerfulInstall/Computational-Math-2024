/** Начальное количество интервалов разбиения*/
export const  INITIAL_PARTITION_NUMBER = 4;
export const  MAX_ITERATIONS = 10;

export type EquationSolvingMethod = 'left-rectangle' | 'right-rectangle' | 'middle-rectangle' | 'trapezoid' | 'simpson';

/** Входные данные от пользователя, которые передаются в метод solve */
export interface EquationInputData {
  start: number;
  end: number;
  epsilon: number; // абсолютная величина разности 2 соседних вычислений
  functionData: FunctionData,
  method: EquationSolvingMethod,
}

/** Функция и ее производная */
export interface FunctionData {
  value: NumberFunction, // сама функция
  printableValue: string // Выводимая форма функции
}

/** Массив функций и их производных */
export const functions: FunctionData[] = [
  {
    value: (x: number): number => -3 * x ** 3 - 5 * x ** 2 + 4 * x - 2,
    printableValue: '-3x^3 - 5x^2 + 4x - 2',
  },
  {
    value: (x: number): number => x ** 3 - 3 * x ** 2 + 7 * x - 10,
    printableValue: 'x^3 - 3x^2 + 7x - 10',
  },
  {
    value: (x: number): number => 5 * x ** 3 - 2 * x ** 2 + 3 * x - 15,
    printableValue: '5x^3 - 2x^2 + 3x - 15',
  },
  {
    value: (x: number): number => 1/x,
    printableValue: '1/x',
  },
  {
    value: (x: number): number => 1/Math.sin(x),
    printableValue: '1/sin(x)',
  },
  {
    value: (x: number): number => Math.log(x),
    printableValue: 'ln(x)',
  },
];

/** Тип: функция получающая число и возвращающая число */
export type NumberFunction = (x: number) => number;


