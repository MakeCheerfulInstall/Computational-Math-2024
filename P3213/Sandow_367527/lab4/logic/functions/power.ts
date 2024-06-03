import {ApproximatedFunction, TableFunction} from "../definitions";
import {getApproximationReliability, getDeviationMeasure, getStandardDeviation, kramerMethod} from "../utils";
import { fToS } from "../../printer";


function calculateCoefficients(tableFunction: TableFunction): number[] {
  let sx = 0;
  let sxx = 0;
  for (let i = 0; i < tableFunction.n; i++) {
    sx += Math.log(tableFunction.x[i]);
    sxx += Math.log(tableFunction.x[i]) ** 2;
  }
  let sy = 0;
  let sxy = 0;
  for (let i = 0; i < tableFunction.n; i++) {
    sy += Math.log(tableFunction.y[i]);
    sxy += Math.log(tableFunction.x[i]) * Math.log(tableFunction.y[i]);
  }
  const matrixCoefficients: number[][] = [[sxx, sx], [sx, tableFunction.n]];
  const answers: number[] = [sxy, sy];
  const coefficients = kramerMethod(matrixCoefficients, answers);
  coefficients[1] = Math.exp(coefficients[1]);
  [coefficients[0], coefficients[1]] = [coefficients[1], coefficients[0]]; // корректно ли работает???
  return coefficients;
}

export function getPowerFunction(tableFunction: TableFunction): ApproximatedFunction {
  const coefficients = calculateCoefficients(tableFunction);
  const f = (x: number) => coefficients[0] * (x ** coefficients[1]);
  return {
    f: f,
    // TODO вывод графика (пока не работает)
    printableF: `${fToS(coefficients[0])} * x^${fToS(coefficients[1])}`,
    name: "Степенная аппроксимация",
    deviationMeasure: getDeviationMeasure(tableFunction, f),
    standardDeviation: getStandardDeviation(tableFunction, f),
    approximationReliability: getApproximationReliability(tableFunction, f)
  };

}
