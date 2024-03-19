import {EquationInputData, EquationSolver, FunctionData} from "./equation-solver";
import {EquationResult, Table} from "../printer";

/** Метод простых итераций */
export class SimpleIterations extends EquationSolver {
  static readonly columnNames: string[] = ["x_i", "x_i+1", "f(x_i+1)", "|x_i+1 - x_i|"];

  solve(data: EquationInputData): EquationResult {
    let table: Table = {columnNames: SimpleIterations.columnNames, rows: []};
    let iterations = 0;

    const FVal = data.functionData.value;
    const Phi = data.functionData.phi;
    let xPrev = this.findInitialApproximation(data);
    let xCurr, fCurr;
    while (++iterations < data.maxIterations) {
      xCurr = Phi(xPrev);
      fCurr = FVal(xCurr); // @ts-ignore
      table.rows.push([xPrev, xCurr, fCurr, Math.abs(xCurr - xPrev)]);

      // if (!this.checkIsInsideInterval(xCurr, data) || !this.checkDeltaMoreEpsilon(xPrev, xCurr, xCurr, data)) {
      //   break;
      // }
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

  public findInitialApproximation(data: EquationInputData): number {
    if (this.checkSufficientCondition(data.start, data.functionData)) {
      return data.start;
    } else if (this.checkSufficientCondition(data.end, data.functionData)) {
      return data.end;
    } else if (!this.checkSufficientCondition((data.end + data.start) / 2, data.functionData)) {
      alert("Не выполняется достаточное условие, но мы попробуем решить уравнение");
    }
    return (data.end + data.start) / 2;
  }

  public checkSufficientCondition(x: number, F: FunctionData): boolean {
    // @ts-ignore
    return Math.abs(F.phiDerivative(x)) < 1;
  }
}
