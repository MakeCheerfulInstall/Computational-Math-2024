import {getApproximationReliability, getDeviationMeasure, getStandardDeviation, kramerMethod} from "../utils.js";
import { fToS } from "../../printer";


function calculateCoefficients(tableFunction) {
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
  const matrixCoefficients = [[sxx, sx], [sx, tableFunction.n]];
  const answers = [sxy, sy];
  return kramerMethod(matrixCoefficients, answers);
}

export function getLinearFunction(tableFunction) {
  const coefficients = calculateCoefficients(tableFunction);
  const f = (x) => coefficients[0] * x + coefficients[1];
  return {
    f: f,
    printableF: `${fToS(coefficients[0])} * x + ${fToS(coefficients[1])}`,
    name: "Линейная аппроксимация",
    deviationMeasure: getDeviationMeasure(tableFunction, f),
    standardDeviation: getStandardDeviation(tableFunction, f),
    approximationReliability: getApproximationReliability(tableFunction, f)
  };
}

