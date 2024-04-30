import { Solver } from "./logic/Solver";
import { InputData, Result, SolvingMethod, Table } from "./logic/definitions";
import functionPlot from "function-plot";

/** Количество выводимых знаков после запятой. */
const PRINT_PRECISION = 4;
const SPACE_BEFORE_AND_AFTER_INTERVAL = 3;

/** Float to string. */
export function fToS(value: number): string {
  return value.toFixed(PRINT_PRECISION);
}

export function showResult(input: InputData, result: Result): void {
  renderPlot(input, result);
  showLagrangeResult(result.lagrange);
  showNewtonResult(result.newtonWithDividedDiffs);
  showNewtonFiniteResult(result.newtonWithFiniteDiffs);
  showTableForNewton(result.tableForNewtonWDD);
  unhideResultSection();
}

function showLagrangeResult(y: number): void {
  const resultElement = document.querySelector(
    ".result__lagrange"
  ) as HTMLParagraphElement;
  resultElement.textContent = y.toString();
}

function showNewtonResult(y: number): void {
  const resultElement = document.querySelector(
    ".result__newton"
  ) as HTMLParagraphElement;
  resultElement.textContent = y.toString();
}

function showNewtonFiniteResult(y: number): void {
  const resultElement = document.querySelector(
    ".result__newton-finite"
  ) as HTMLParagraphElement;
  resultElement.textContent = y.toString();
}

function showTableForNewton(table: Table): void {
  const tableElement = document.getElementsByClassName("result__table")[0];
  const tableHead = tableElement.getElementsByTagName("thead")[0];
  tableHead.innerHTML = "";
  for (const columnName of table.columnNames) {
    const th = document.createElement("th");
    th.innerHTML = columnName;
    tableHead.appendChild(th);
  }

  const tableBody = tableElement.getElementsByTagName("tbody")[0];
  tableBody.innerHTML = "";

  for (const row of table.rows ?? []) {
    const tr = document.createElement("tr");
    for (const cell of row) {
      const td = document.createElement("td");
      td.innerHTML = cell.toFixed(PRINT_PRECISION);
      tr.appendChild(td);
    }
    tableBody.appendChild(tr);
  }
}

function unhideResultSection(): void {
  document.getElementsByClassName("result")[0]?.classList.remove("hidden");
}

function renderPlot(input: InputData, result: Result): void {
  const xValues = [...input.points.map((p) => p.x), input.xVal];

  const minX = Math.min(...xValues);
  const maxX = Math.max(...xValues);

  let xDomain: number[] = [
    minX - SPACE_BEFORE_AND_AFTER_INTERVAL,
    maxX + SPACE_BEFORE_AND_AFTER_INTERVAL,
  ];

  const points = input.points.map((p) => [p.x, p.y]);

  // TODO дополнить выводом результатов новых методов
  functionPlot({
    target: "#plot",
    width: 500,
    height: 500,
    xAxis: {
      domain: xDomain,
    },
    grid: true,
    data: [
      {
        points,
        fnType: "points",
        graphType: "scatter",
        color: "blue",
        attr: {
          "stroke-width": `10px`,
        },
      },
      {
        points: [[input.xVal, result.newtonWithDividedDiffs]],
        fnType: "points",
        graphType: "scatter",
        color: "orange",
        attr: {
          "stroke-width": `22px`,
        },
      },
      {
        points: [[input.xVal, result.lagrange]],
        fnType: "points",
        graphType: "scatter",
        color: "purple",
        attr: {
          "stroke-width": `15px`,
        },
      },
      {
        points: [[input.xVal, result.newtonWithFiniteDiffs]],
        fnType: "points",
        graphType: "scatter",
        color: "red",
        attr: {
          "stroke-width": `10px`,
        },
      },
      {
        points: getInterpolationPoints(input, { a: minX, b: maxX }, "lagrange"),
        fnType: "points",
        graphType: "polyline",
        color: "pink",
        attr: {
          "stroke-width": `1px`,
        },
      },
      {
        points: getInterpolationPoints(
          input,
          { a: minX, b: maxX },
          "newtonWithDividedDiffs"
        ),
        fnType: "points",
        graphType: "polyline",
        color: "green",
        attr: {
          "stroke-width": `1px`,
        },
      },
      {
        points: getInterpolationPoints(
          input,
          { a: minX, b: maxX },
          "newtonWithFiniteDiffs"
        ),
        fnType: "points",
        graphType: "polyline",
        color: "#9c4600",
        attr: {
          "stroke-width": `1px`,
        },
      },
    ],
  });
}

function getInterpolationPoints(
  input: InputData,
  interval: { a: number; b: number },
  method: SolvingMethod,
  step = 0.01
): number[][] {
  const points: number[][] = [];
  const solver = new Solver();
  for (let x = interval.a; x <= interval.b; x += step) {
    let fn: (x: number, data: InputData) => number;
    switch (method) {
      case "lagrange":
        fn = solver.lagrange;
        break;
      case "newtonWithDividedDiffs":
        fn = solver.newtonWithDividedDiffs;
        break;
      case "newtonWithFiniteDiffs":
        fn = solver.newtonWithFiniteDiffs;
        break;
    }
    points.push([x, fn(x, input)]);
  }

  return points;
}
