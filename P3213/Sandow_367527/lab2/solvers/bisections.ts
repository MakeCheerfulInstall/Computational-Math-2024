import {EquationInputData, EquationSolver} from "./equation-solver";
import {EquationResult, Table} from "../printer";

/** Метод половинного деления */
export class Bisections extends EquationSolver {
  static readonly columnNames: string[] = ["a", "b", "x", "f(a)", "f(b)", "f(x)", "|a - b|"];

  solve(data: EquationInputData): EquationResult {
    let table: Table = {columnNames: Bisections.columnNames, rows: []};

    const F = data.functionData.value;
    let end = data.end;
    let start = data.start;
    let iterations = 0;
    let xCurr, fCurr;
    while (++iterations < data.maxIterations) {
      xCurr = (start + end) / 2;
      fCurr = F(xCurr);
      const fStart = F(start);
      const fEnd = F(end);// @ts-ignore
      table.rows.push([start, end, xCurr, fStart, fEnd, fCurr, Math.abs(start - end)]);

      if (
        !this.checkIsInsideInterval(xCurr, data) ||
        !this.checkDeltaMoreEpsilon(start, end, xCurr, data)
      ) {
        break;
      }

      if (fStart * fCurr < 0) {
        end = xCurr;
      } else if (fEnd * fCurr < 0){
        start = xCurr;
      } else if (Math.abs(start) < Math.abs(end)){
        end = xCurr;
      } else {
        start = xCurr;
      }
    }
    this.handleOutEpsilonByIterationsCount(data, iterations, fCurr);
    return {
      x: xCurr,
      f: fCurr,
      iterationsCount: iterations,
      table: table
    };
  }
}
