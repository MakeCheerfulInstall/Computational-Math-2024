import {EquationSystemInputData} from "../reader";
import {EquationSystemResult} from "../printer";
import {EquationSystemSolver, FunctionInsideSystem} from "./equation-system-solver";

/** Метод Ньютона для систем */
export class NewtonForSystems extends EquationSystemSolver {
  public solve(data: EquationSystemInputData): EquationSystemResult {
    let valuesVectors: number[][] = [];

    const F = data.system.f;
    const G = data.system.g;
    let xPrev = data.x0;
    let yPrev = data.y0;
    let iterations = 0;
    const offset = 0.000001;
    let jacobian = calculateJacobian(F, G, xPrev, yPrev);
    if (jacobian === 0) {
      xPrev -= offset;
      jacobian = calculateJacobian(F, G, xPrev, yPrev);
    }

    let xCurr = xPrev - getDeltaX(F, G, xPrev, yPrev) / jacobian;
    let yCurr = yPrev - getDeltaY(F, G, xPrev, yPrev) / jacobian;
    valuesVectors.push([xCurr, yCurr]);

    while (++iterations < data.maxIterations) {
      xPrev = xCurr;
      yPrev = yCurr;

      jacobian = calculateJacobian(F, G, xPrev, yPrev);
      if (jacobian === 0) {
        xPrev -= offset;
        jacobian = calculateJacobian(F, G, xPrev, yPrev);
      }
      xCurr = xPrev - getDeltaX(F, G, xPrev, yPrev) / jacobian;
      yCurr = yPrev - getDeltaY(F, G, xPrev, yPrev) / jacobian;
      valuesVectors.push([xCurr, yCurr]);
      
      if (Math.abs(xCurr - xPrev) <= data.epsilon && Math.abs(yCurr - yPrev) <= data.epsilon) {
        break;
      }
    }
    return {
      solution: [xPrev, yPrev],
      iterationsCount: iterations,
      valuesVectors: valuesVectors,
      discrepancy: this.calcDiscrepancy(xPrev, yPrev, F, G)
    }
  }
}


function calculateJacobian(F: FunctionInsideSystem, G: FunctionInsideSystem, x: number, y: number): number {
  const fDX = F.derivativeX(x, y);
  const fDY = F.derivativeY(x, y);
  const gDX = G.derivativeX(x, y);
  const gDY = G.derivativeY(x, y);
  return fDX * gDY - fDY * gDX;
}

function getDeltaX(F: FunctionInsideSystem, G: FunctionInsideSystem, x: number, y: number): number {
  const f = F.value(x, y);
  const g = G.value(x, y);
  const fDY = F.derivativeY(x, y);
  const gDY = G.derivativeY(x, y);
  return f * gDY - fDY * g;
}

function getDeltaY(F: FunctionInsideSystem, G: FunctionInsideSystem, x: number, y: number): number {
  const f = F.value(x, y);
  const g = G.value(x, y);
  const fDX = F.derivativeX(x, y);
  const gDX = G.derivativeX(x, y);
  return fDX * g - f * gDX;
}
