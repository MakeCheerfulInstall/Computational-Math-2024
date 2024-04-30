import { InputData, Result } from "./definitions";
import { Lagrange } from "./methods/Lagrange";
import { NewtonWithDividedDiffs } from "./methods/NewtonWithDividedDiffs";
import { NewtonWithFiniteDiffs } from "./methods/NewtonWithFiniteDiffs";
import { createTable } from "./utils";

export class Solver {
  public getAllCalculations(data: InputData): Result {
    console.log(data);
    return {
      lagrange: this.lagrange(data.xVal, data),
      newtonWithDividedDiffs: this.newtonWithDividedDiffs(data.xVal, data),
      tableForNewtonWDD: createTable(data),
      newtonWithFiniteDiffs: this.newtonWithFiniteDiffs(data.xVal, data),
      tableForNewtonWFD: createTable(data),
    };
  }

  public lagrange(xCur: number, data: InputData): number {
    return Lagrange.solve(xCur, data);
  }

  public newtonWithDividedDiffs(xCur: number, data: InputData): number {
    return NewtonWithDividedDiffs.solve(xCur, data);
  }

  public newtonWithFiniteDiffs(xCur: number, data: InputData): number {
    return NewtonWithFiniteDiffs.solve(xCur, data);
  }
}
