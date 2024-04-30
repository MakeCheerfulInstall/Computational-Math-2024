import { InputData } from "../definitions";
import { createTable } from "../utils";

export class NewtonWithFiniteDiffs {
  public static solve(xCur: number, data: InputData): number {
    const medium = (data.points[0].x + data.points[data.size - 1].x) / 2;
    return xCur < medium
      ? this.calcResInLeftHalf(xCur, data)
      : this.calcResInRightHalf(xCur, data);
  }

  public static calcResInLeftHalf(xCur: number, data: InputData): number {
    const rows: number[][] = createTable(data).rows;
    const [t, index] = this.calcOptimumTAndIndexForLeftHalf(xCur, data);
    let res = 0;
    let numerator = 1;
    let denominator = 1;
    for (let i = 1; i <= data.size + 1; i++) {
      if (rows[index].length <= i) {
        break;
      }
      res += (numerator / denominator) * rows[index][i];
      numerator *= t + 1 - i;
      denominator *= i;
    }
    return res;
  }

  public static calcResInRightHalf(xCur: number, data: InputData): number {
    const rows: number[][] = createTable(data).rows;
    const [t, index] = this.calcOptimumTAndIndexForRightHalf(xCur, data);
    let res = 0;
    let numerator = 1;
    let denominator = 1;
    for (let i = 1; i <= data.size + 1; i++) {
      if (index - i + 1 < 0 || rows[index - i + 1].length <= i) {
        break;
      }
      res += (numerator / denominator) * rows[index - i + 1][i];
      numerator *= t + i - 1;
      denominator *= i;
    }
    return res;
  }

  public static calcOptimumTAndIndexForLeftHalf(
    xCur: number,
    data: InputData
  ): number[] {
    const { points, size, xVal } = data;
    let optDiff = xCur - points[0].x;
    let index = 0;
    for (let i = 0; i < size; i++) {
      const diff = xCur - points[i].x;
      if (0 < diff && diff < optDiff) {
        optDiff = diff;
        index = i;
      }
    }
    return [optDiff / (points[1].x - points[0].x), index];
  }

  public static calcOptimumTAndIndexForRightHalf(
    xCur: number,
    data: InputData
  ): number[] {
    const { points, size, xVal } = data;
    let optDiff = xCur - points[size - 1].x;
    let index = size - 1;
    for (let i = 0; i < size; i++) {
      const diff = xCur - points[i].x;
      if (optDiff < diff && diff < 0) {
        optDiff = diff;
        index = i;
      }
    }
    return [optDiff / (points[1].x - points[0].x), index];
  }
}
