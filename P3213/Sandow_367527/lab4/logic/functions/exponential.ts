import {ApproximatedFunction, TableFunction} from "../definitions";
import {getApproximationReliability, getDeviationMeasure, getStandardDeviation, kramerMethod} from "../utils";
import { fToS } from "../../printer";


function calculateCoefficients(tableFunction: TableFunction): number[] {
  let sx = 0;
  let sxx = 0;
  for (let i = 0; i < tableFunction.n; i++) {
    sx += tableFunction.x[i];
    sxx += tableFunction.x[i] ** 2;
  }
  let sy = 0;
  let sxy = 0;
  for (let i = 0; i < tableFunction.n; i++) {
    sy += Math.log(tableFunction.y[i]);
    sxy += tableFunction.x[i] * Math.log(tableFunction.y[i]);
  }
  const matrixCoefficients: number[][] = [[sxx, sx], [sx, tableFunction.n]];
  const answers: number[] = [sxy, sy];
  const coefficients: number[] | null = kramerMethod(matrixCoefficients, answers);
  coefficients[1] = Math.E ** coefficients[1];
  [coefficients[0], coefficients[1]] = [coefficients[1], coefficients[0]];
  return coefficients;
}

export function getExponentialFunction(tableFunction: TableFunction): ApproximatedFunction | null {
  const coefficients = calculateCoefficients(tableFunction);
  if (coefficients === null) {
    return null;
  }
  const f = (x: number) => coefficients[0] * (Math.E ** (coefficients[1] * x));
  return {
    f: f,
    printableF: `${fToS(coefficients[0])} * 2.7182818284^(${fToS(coefficients[1])} * x)`,
    name: "Экспоненциальная аппроксимация",
    deviationMeasure: getDeviationMeasure(tableFunction, f),
    standardDeviation: getStandardDeviation(tableFunction, f),
    approximationReliability: getApproximationReliability(tableFunction, f)
  };
}
