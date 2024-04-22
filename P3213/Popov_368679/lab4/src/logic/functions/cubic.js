import {getApproximationReliability, getDeviationMeasure, getStandardDeviation, kramerMethod} from "../utils.js";
import { fToS } from "../../printer";

function calculateCoefficients(tableFunction){
  let sx = 0;
  let sx2 = 0;
  let sx3 = 0;
  let sx4 = 0;
  let sx5 = 0;
  let sx6 = 0;

  for (let i = 0; i < tableFunction.n; i++) {
    const x = tableFunction.x[i];
    sx += x;
    sx2 += x ** 2;
    sx3 += x ** 3;
    sx4 += x ** 4;
    sx5 += x ** 5;
    sx6 += x ** 6;
  }

  let sy = 0;
  let sxy = 0;
  let sx2y = 0;
  let sx3y = 0;

  for (let i = 0; i < tableFunction.n; i++) {
    const x = tableFunction.x[i];
    const y = tableFunction.y[i];
    sy += y;
    sxy += x * y;
    sx2y += x ** 2 * y;
    sx3y += x ** 3 * y;
  }

  const matrixCoefficients = [
    [tableFunction.n, sx, sx2, sx3],
    [sx, sx2, sx3, sx4],
    [sx2, sx3, sx4, sx5],
    [sx3, sx4, sx5, sx6]
  ];

  const answers= [sy, sxy, sx2y, sx3y];

  return kramerMethod(matrixCoefficients, answers);
}

export function getCubicFunction(tableFunction) {
  const coefficients = calculateCoefficients(tableFunction);

  const f = (x) => coefficients[0] + coefficients[1] * x + coefficients[2] * (x ** 2) + coefficients[3] * (x ** 3);

  return {
    f: f,
    printableF: `${fToS(coefficients[0])} + ${fToS(coefficients[1])}x + ${fToS(coefficients[2])}x^2 + ${fToS(coefficients[3])}x^3`,
    name: "Аппроксимация полиномиальной функцией 3-ей степени",
    deviationMeasure: getDeviationMeasure(tableFunction, f),
    standardDeviation: getStandardDeviation(tableFunction, f),
    approximationReliability: getApproximationReliability(tableFunction, f)
  };
}
