
import { Lagrange } from "./methods/Lagrange";
import { NewtonWithDividedDiffs } from "./methods/NewtonWithDividedDiffs";
import { NewtonWithFiniteDiffs } from "./methods/NewtonWithFiniteDiffs";
import { createTable } from "./utils";

export class Solver {
  getAllCalculations(data) {
    console.log(data);
    return {
      lagrange: this.lagrange(data.xVal, data),
      newtonWithDividedDiffs: this.newtonWithDividedDiffs(data.xVal, data),
      tableForNewtonWDD: createTable(data),
      newtonWithFiniteDiffs: this.newtonWithFiniteDiffs(data.xVal, data),
      tableForNewtonWFD: createTable(data),
    };
  }

  lagrange(xCur, data) {
    return Lagrange.solve(xCur, data);
  }

  newtonWithDividedDiffs(xCur, data) {
    return NewtonWithDividedDiffs.solve(xCur, data);
  }

  newtonWithFiniteDiffs(xCur, data) {
    return NewtonWithFiniteDiffs.solve(xCur, data);
  }
}
