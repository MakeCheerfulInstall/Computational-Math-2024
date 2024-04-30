// Интерфейс для аппроксимированной функции

// Интерфейс для аппроксимированной функции
export interface ApproximatedFunction {
  f: NumberFunction;
  printableF: string;
  name: string;
  deviationMeasure: number;
  standardDeviation: number;
  approximationReliability: number;
}

export interface TableFunction {
  n: number;
  x: number[];
  y: number[];
}

/** Тип: функция получающая число и возвращающая число */
export type NumberFunction = (x: number) => number;
