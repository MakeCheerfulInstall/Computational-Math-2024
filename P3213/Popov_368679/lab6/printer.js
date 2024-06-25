
import functionPlot from "function-plot";
import {ALL_METHODS} from "./equations";

/** Количество выводимых знаков после запятой. */
const PRINT_PRECISION = 4;
const SPACE_BEFORE_AND_AFTER_INTERVAL = 3;

/** Float to string. */
export function fToS(value){
  return value.toFixed(PRINT_PRECISION);
}

export function showResult(input, result) {
  renderPlot(input, result);
  ALL_METHODS.forEach((method) => {
    const resultForMethod = result[method] ;
    showTableForSelectedMethod(resultForMethod.table, method);
  });
  unhideResultSection();
}

function showTableForSelectedMethod(table, method) {
  const tableElement = document.getElementsByClassName(
    `result__table_${method}`
  )[0];
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

function unhideResultSection() {
  document.getElementsByClassName("result")[0]?.classList.remove("hidden");
}

function renderPlot(input, result) {
  let xDomain = [
    input.differentiateInterval.start - SPACE_BEFORE_AND_AFTER_INTERVAL,
    input.differentiateInterval.end + SPACE_BEFORE_AND_AFTER_INTERVAL,
  ];
  const colors = ["red", "green", "blue", "purple", "orange", "pink"];

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
        fn: input.equation.exactSolutionForPlot,
        graphType: "scatter",
        color: colors[0],
      },
    ],
  });
}
