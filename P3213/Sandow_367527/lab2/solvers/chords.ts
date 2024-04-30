import {EquationInputData, EquationSolver} from "./equation-solver";
import {EquationResult, Table} from "../printer";

/** Метод хорд */
export class Chords extends EquationSolver {
  static readonly columnNames: string[] = ["a", "b", "x", "f(a)", "f(b)", "f(x)", "|x_i+1 - x_i|"];

  solve(data: EquationInputData): EquationResult {
    let table: Table = {columnNames: Chords.columnNames, rows: []};
    const F = data.functionData.value;
    let start = data.start;
    let end = data.end;
    let xPrev = start;
    let iterations = 0;
    let xCurr, fCurr;
    while (++iterations < data.maxIterations) {
      const fStart = F(start);
      const fEnd = F(end);
      // xCurr = (start * fEnd - end * fStart) / (fEnd - fStart);
      xCurr = start - ((end - start) / (fEnd - fStart)) * fStart;
      fCurr = F(xCurr);// @ts-ignore
      table.rows.push([start, end, xCurr, fStart, fEnd, fCurr, Math.abs(xCurr - xPrev)]);

      if (!this.checkIsInsideInterval(xCurr, data) || !this.checkDeltaMoreEpsilon(start, end, xCurr, data)) {
        break;
      }

      if (fStart * fCurr < 0) {
        end = xCurr;
      } else {
        start = xCurr;
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
