import { EquationInputData, FunctionData, INITIAL_PARTITION_NUMBER, MAX_ITERATIONS } from "./definitions";
import {EquationResult} from "../printer";

const INF = 10**9;

export class EquationSolver {
  public solve(data: EquationInputData): EquationResult | null {
    const denominator = data.method == "simpson" ? 15 : 3;
    data.epsilon /= denominator;

    const xWithDiscontinuity = this.getFunctionDiscontinuity(data);

    if (xWithDiscontinuity != undefined) {
      alert('У функции разрыв!');
      if (xWithDiscontinuity == data.start) {
        data.start += data.epsilon;
      } else if (xWithDiscontinuity == data.end) {
        data.end -= data.epsilon;
      } else {
        const firstPartData = {...data};
        firstPartData.end = xWithDiscontinuity - firstPartData.epsilon;
        const resultForFirstPart = this.solve(firstPartData);
        const secondPartData = {...data};
        secondPartData.start = xWithDiscontinuity + secondPartData.epsilon;
        const resultForSecondPart = this.solve(secondPartData);
        return {
          value: (resultForFirstPart?.value ?? 0) + (resultForSecondPart?.value ?? 0),
          partitionsNumber: (resultForFirstPart?.partitionsNumber ?? 0) + (resultForSecondPart?.partitionsNumber ?? 0)
        };
      }
    }

    let n = INITIAL_PARTITION_NUMBER;
    let valueOld = this.calcIntegralForIntervalsCount(data, n);
    let valueNew = valueOld;
    n *= 2;
    for (let i = 0; i < MAX_ITERATIONS; i++) {
      valueNew  = this.calcIntegralForIntervalsCount(data, n);
      if (Math.abs(valueNew - valueOld) < data.epsilon)
        break;
      n *= 2;
      valueOld = valueNew;
    }
    return {
      value: valueNew,
      partitionsNumber: n
    }
  }

  public calcIntegralForIntervalsCount(data: EquationInputData, intervalsCount: number) {
    let ySum = 0;
    const h = (data.end - data.start) / intervalsCount;
    let x_i = data.start;
    let x_i_p1 = x_i + h;
    for (let i = 0; i < intervalsCount; i++) {
      ySum += this.getCurrentY(i, x_i, x_i_p1, data);
      x_i = x_i_p1;
      x_i_p1 += h;
    }
    return ySum * h;
  }

  /** Считаем Y в зависимости от метода */
  public getCurrentY(iterIndex: number, x0: number, x1: number, data: EquationInputData): number {
    switch (data.method) {
      case "left-rectangle":
        return data.functionData.value(x0);
      case "middle-rectangle":
        return data.functionData.value((x0 + x1) / 2);
      case "right-rectangle":
        return data.functionData.value(x1);
      case "trapezoid":
        return (data.functionData.value(x0) + data.functionData.value(x1)) / 2;
      case "simpson":
        if (x0 == data.start)
          return data.functionData.value(x0) / 3 + data.functionData.value(data.end) / 3;
        else if (iterIndex % 2 == 0)
          return 2 * data.functionData.value(x0) / 3;
        else return 4 * data.functionData.value(x0) / 3;
    }
  }

  public getFunctionDiscontinuity(data: EquationInputData): number | null {
    for (let x = data.start; x <= data.end; x += data.epsilon) {
      if (this.checkPointForDiscontinuity(data.functionData, x)) {
        return x;
      }
    }

    return null;
  }

  public checkPointForDiscontinuity(f: FunctionData, x: number): boolean {
    return (
      !isFinite(f.value(x)) ||
      isNaN(f.value(x)) ||
      Math.abs(f.value(x)) >= INF
    );
  }
}
