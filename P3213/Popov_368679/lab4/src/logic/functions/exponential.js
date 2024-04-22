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
    sy += Math.log(tableFunction.y[i]);
    sxy += tableFunction.x[i] * Math.log(tableFunction.y[i]);
  }
  const matrixCoefficients = [[sxx, sx], [sx, tableFunction.n]];
  const answers = [sxy, sy];
  const coefficients = kramerMethod(matrixCoefficients, answers);
  coefficients[1] = Math.E ** coefficients[1];
  [coefficients[0], coefficients[1]] = [coefficients[1], coefficients[0]];
  return coefficients;
}

export function getExponentialFunction(tableFunction){
  const coefficients = calculateCoefficients(tableFunction);
  if (coefficients === null) {
    return null;
  }
  const f = (x) => coefficients[0] * (Math.E ** (coefficients[1] * x));
  return {
    f: f,
    printableF: `${fToS(coefficients[0])} * 2.7182818284^(${fToS(coefficients[1])} * x)`,
    name: "Экспоненциальная аппроксимация",
    deviationMeasure: getDeviationMeasure(tableFunction, f),
    standardDeviation: getStandardDeviation(tableFunction, f),
    approximationReliability: getApproximationReliability(tableFunction, f)
  };
}
