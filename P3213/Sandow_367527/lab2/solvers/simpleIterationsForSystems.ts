import {EquationSystemInputData} from "../reader";
import {EquationSystemResult} from "../printer";
import {EquationSystemSolver, MAX_ABSOLUTE_VALUE} from "./equation-system-solver";

/** Метод простых итераций для систем*/
export class SimpleIterationsForSystems extends EquationSystemSolver {
  public solve(data: EquationSystemInputData): EquationSystemResult {
    const sufficientCondition = this.checkSufficientCondition(data);
    if (!sufficientCondition) {
      alert("Не выполняется достаточное условие, но мы попробуем решить систему");
    }

    let valuesVectors: number[][] = [];
    const F = data.system.f;
    const G = data.system.g;
    let xPrev = data.x0;
    let yPrev = data.y0;
    let xCurr = xPrev;
    let yCurr = yPrev;
    let iterations = 0;


    while (++iterations < data.maxIterations) {
      xCurr = F.phi(xPrev, yPrev);
      yCurr = G.phi(xPrev, yPrev);
      valuesVectors.push([xCurr, yCurr]);
      console.log([xCurr, yCurr])
      if (this.checkIncorrectNumberValue(xCurr) || this.checkIncorrectNumberValue(yCurr)){
        alert("Вероятно, на промежутке нет корней, " +
          "либо метод не подходит для решения данного уравнения с такими входными данными");
        break;
      }
      if (Math.abs(xCurr - xPrev) <= data.epsilon && Math.abs(yCurr - yPrev) <= data.epsilon) {
        break;
      }
      xPrev = xCurr;
      yPrev = yCurr;
    }
    return {
      solution: [xPrev, yPrev],
      iterationsCount: iterations,
      valuesVectors: valuesVectors,
      discrepancy: this.calcDiscrepancy(xPrev, yPrev, F, G)
    }
  }

  public checkSufficientCondition(data: EquationSystemInputData): boolean {
    const F = data.system.f;
    const G = data.system.g;
    let x = data.x0;
    let y = data.y0;
    const delta = 0.00000001;
    return (
      Math.abs((F.derivativeX(x + delta, y) - F.derivativeX(x, y)) / delta)
        + Math.abs((F.derivativeX(x, y + delta) - F.derivativeX(x, y)) / delta)
      ) < 1 &&
      (
        Math.abs((G.derivativeX(x + delta, y) - G.derivativeX(x, y)) / delta)
        + Math.abs((G.derivativeX(x, y + delta) - G.derivativeX(x, y)) / delta)
      ) < 1;
  }

  public checkIncorrectNumberValue(n: number): boolean{
    return isNaN(n) || Math.abs(n) > MAX_ABSOLUTE_VALUE;
  }
}
