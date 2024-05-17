
import { createTable } from "../utils.js";

export class NewtonWithFiniteDiffs {
  static solve(xCur, data) {
    const medium = (data.points[0].x + data.points[data.size - 1].x) / 2;
    return xCur < medium
      ? this.calcResInLeftHalf(xCur, data)
      : this.calcResInRightHalf(xCur, data);
  }

  static calcResInLeftHalf(xCur, data) {
    const rows = createTable(data).rows;
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

  static calcResInRightHalf(xCur, data) {
    const rows = createTable(data).rows;
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

  static calcOptimumTAndIndexForLeftHalf(
    xCur,
    data
  ) {
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

  static calcOptimumTAndIndexForRightHalf(
    xCur,
    data
  ) {
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
