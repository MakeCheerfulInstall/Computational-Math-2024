export type InputData = {
  equation: OrdinaryDifferentialEquation;
  startCondition: Point;
  differentiateInterval: Interval;
  step: number;
  epsilon: number;
};

export const ALL_METHODS = ["euler", "runge-kutta", "milne"] as const;

export type SolvingMethod = (typeof ALL_METHODS)[number];

export type Point = {
  x: number;
  y: number;
};

export type Interval = {
  start: number;
  end: number;
};

export type ResultForSingleMethod = {
  table: Table;
  pointsForGraph: number[][];
};

export type Result = Record<SolvingMethod, ResultForSingleMethod | null>;

export type Table = {
  columnNames: string[];
  rows: number[][];
};

/** Тип: функция получающая число и возвращающая число */
export type NumberFunction = (x: number) => number;

export type TwoVariablesNumberFunction = (x: number, y: number) => number;

export type OrdinaryDifferentialEquation = {
  yDerivative: TwoVariablesNumberFunction;
  printableYDerivative: string;
  exactSolutionFn: NumberFunction;
  exactSolutionForPlot: string;
};
