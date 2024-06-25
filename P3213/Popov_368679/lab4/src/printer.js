import { getCorrelationCoefficientPearson } from "./logic/utils.js";
import functionPlot from "function-plot";

/** Количество выводимых знаков после запятой. */
const PRINT_PRECISION = 4;
const SPACE_BEFORE_AND_AFTER_INTERVAL = 10;

/** Float to string. */
export function fToS(value) {
  return value.toFixed(PRINT_PRECISION);
}

export function showResult(input, result,) {
  console.log(result)
  if (result === null
   || result.printableF.includes("NaN")
  ) {
    alert('Не удалось найти аппроксимирующую функцию');
    return;
  }
  renderPlot(input, result);
  printApproximatedFunction(result.printableF);
  printStdDev(result.standardDeviation);
  printXVector(input.x);
  printYVector(input.y);
  const phiVector = input.x.map(result.f);
  printPhiVector(phiVector);
  const epsilonVector = input.y.map((y, i) => y - phiVector[i]);
  printEpsilonVector(epsilonVector);
  if (result.name === 'Линейная аппроксимация') {
    const pearsonCoeff = getCorrelationCoefficientPearson(input);
    printPearsonCoeff(pearsonCoeff);
  }
  unhideResultSection();
}

function printApproximatedFunction(func) {
  document.getElementsByClassName('result__best-approximated-function')[0].textContent = (
    func
  );
}

function printStdDev(stdDev) {
  document.getElementsByClassName('result__std-dev')[0].textContent = (
    fToS(stdDev)
  );
}

function printXVector(xVector) {
  document.getElementsByClassName('result__x')[0].textContent = (
    convertVectorToString(xVector)
  );
}

function printYVector(yVector) {
  document.getElementsByClassName('result__y')[0].textContent = (
    convertVectorToString(yVector)
  );
}

function printPhiVector(phiVector) {
  document.getElementsByClassName('result__phi')[0].textContent = (
    convertVectorToString(phiVector)
  );
}

function printEpsilonVector(epsilonVector) {
  document.getElementsByClassName('result__epsilon')[0].textContent = (
    convertVectorToString(epsilonVector)
  );
}

function printPearsonCoeff(pearsonCoeff) {
  document.getElementsByClassName('result__epsilon')[0].textContent = (
    fToS(pearsonCoeff)
  );
}

function convertVectorToString(vector) {
  return `(${vector.map(x => fToS(x)).join(', ')})`;
}

function unhideResultSection() {
  document.getElementsByClassName('result')[0]?.classList.remove('hidden');
}

function renderPlot(input, result,) {
  let xDomain = [
    Math.min(...input.x) - SPACE_BEFORE_AND_AFTER_INTERVAL,
    Math.max(...input.x) + SPACE_BEFORE_AND_AFTER_INTERVAL,
  ];

  const data = {
      fn: result.printableF,
  };

  if (
    [
      'Степенная аппроксимация',
      'Экспоненциальная аппроксимация'
    ]
      .includes(result.name)
  ) {
    data.graphType = 'polyline';
  }

  functionPlot({
    target: "#plot",
    width: 500,
    height: 500,
    xAxis: {
      domain: xDomain,
    },
    grid: true,
    data: [
      data
    ],
  });
}
