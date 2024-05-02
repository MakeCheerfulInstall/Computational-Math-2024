export interface InputData {
  points: Point[];
  size: number;
  xVal: number;
}

export type SolvingMethod =
  | "lagrange"
  | "newtonWithDividedDiffs"
  | "newtonWithFiniteDiffs";

export interface Point {
  x: number;
  y: number;
}

export interface Result {
  lagrange: number;
  newtonWithDividedDiffs: number;
  tableForNewtonWDD: Table;
  newtonWithFiniteDiffs: number;
  tableForNewtonWFD: Table;
}

/** Таблица разностей */
export interface Table {
  columnNames: string[];
  rows: number[][];
}

/** Тип: функция получающая число и возвращающая число */
export type NumberFunction = (x: number) => number;

export type Method = (xCur: number, data: InputData) => number;
