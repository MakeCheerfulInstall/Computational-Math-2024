import {getApproximationReliability, getDeviationMeasure, getStandardDeviation, kramerMethod} from "../utils.js";
import { fToS } from "../../printer";


function calculateCoefficients(tableFunction) {
  const n = tableFunction.n;
  const sx = tableFunction.x.reduce((acc, curr) => acc + curr, 0);
  let sx2 = 0;
  let sx3 = 0;
  let sx4 = 0;
  for (let i = 0; i < n; i++) {
    const xi = tableFunction.x[i];
    sx2 += xi ** 2;
    sx3 += xi ** 3;
    sx4 += xi ** 4;
  }
  const sy = tableFunction.y.reduce((acc, curr) => acc + curr, 0);
  let sxy = 0;
  let sx2y = 0;
  for (let i = 0; i < n; i++) {
    const xi = tableFunction.x[i];
    const yi = tableFunction.y[i];
    sxy += xi * yi;
    sx2y += xi ** 2 * yi;
  }
  const matrixCoefficients = [
    [n, sx, sx2],
    [sx, sx2, sx3],
    [sx2, sx3, sx4]
  ];
  const answers = [sy, sxy, sx2y];
  return kramerMethod(matrixCoefficients, answers);
}

export function getSquareFunction(tableFunction){
  const coefficients = calculateCoefficients(tableFunction);
  const f = (x) => coefficients[0] + coefficients[1] * x + coefficients[2] * (x ** 2);
  return {
    f: f,
    printableF: `${fToS(coefficients[0])} + ${fToS(coefficients[1])}x + ${fToS(coefficients[2])}x^2`,
    name: "Аппроксимация полиномиальной функцией 2-ой степени",
    deviationMeasure: getDeviationMeasure(tableFunction, f),
    standardDeviation: getStandardDeviation(tableFunction, f),
    approximationReliability: getApproximationReliability(tableFunction, f)
  };
}
