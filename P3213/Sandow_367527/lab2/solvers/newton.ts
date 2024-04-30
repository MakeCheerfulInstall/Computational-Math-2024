import {EquationInputData, EquationSolver} from "./equation-solver";
import {EquationResult, Table} from "../printer";

/** Метод Ньютона */
export class Newton extends EquationSolver {
  static readonly columnNames: string[] = ["x_i", "f(x_i)", "f'(x_i)", "x_i+1", "|x_i+1 - x_i|"];

  solve(data: EquationInputData): EquationResult {
    let table: Table = {columnNames: Newton.columnNames, rows: []};
    const F = data.functionData.value;
    const FDer = data.functionData.derivative;
    let xPrev = data.end;
    let iterations = 0;
    let xCurr, fCurr;
    while (++iterations < data.maxIterations) {
      const fPrev = F(xPrev);
      const fDerPrev = FDer(xPrev);
      xCurr = xPrev - fPrev / fDerPrev;
      fCurr= F(xCurr);// @ts-ignore
      table.rows.push([xPrev, fPrev, fDerPrev, xCurr, Math.abs(xCurr - xPrev)]);

      if (!this.checkIsInsideInterval(xCurr, data) || !this.checkDeltaMoreEpsilon(xPrev, xCurr, xCurr, data)) {
        break;
      }
      xPrev = xCurr;
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
