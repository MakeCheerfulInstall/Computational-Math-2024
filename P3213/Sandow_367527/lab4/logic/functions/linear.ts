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
    sy += tableFunction.y[i];
    sxy += tableFunction.x[i] * tableFunction.y[i];
  }
  const matrixCoefficients: number[][] = [[sxx, sx], [sx, tableFunction.n]];
  const answers: number[] = [sxy, sy];
  return kramerMethod(matrixCoefficients, answers);
}

export function getLinearFunction(tableFunction: TableFunction): ApproximatedFunction {
  const coefficients = calculateCoefficients(tableFunction);
  const f = (x: number) => coefficients[0] * x + coefficients[1];
  return {
    f: f,
    printableF: `${fToS(coefficients[0])} * x + ${fToS(coefficients[1])}`,
    name: "Линейная аппроксимация",
    deviationMeasure: getDeviationMeasure(tableFunction, f),
    standardDeviation: getStandardDeviation(tableFunction, f),
    approximationReliability: getApproximationReliability(tableFunction, f)
  };
}

