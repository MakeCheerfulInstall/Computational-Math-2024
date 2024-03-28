
/** Количество выводимых знаков после запятой. */
const PRINT_PRECISION = 4;
const SPACE_BEFORE_AND_AFTER_INTERVAL = 1;

export function showResult(
    inputData,
    type,
    result,
) {
    renderPlot(inputData, type);

    switch (type) {
        case "equation":
            printX(result.x);
            printF(result.f);
            printIterationsCount(result.iterationsCount);
            printTable(result.table);
            break;
        case 'system':
            printSystemSolution(result.solution);
            printSystemIterationsCount(result.iterationsCount);
            printSystemValuesVectors(result.valuesVectors);
            printSystemDiscrepancy(result.discrepancy);
            break;
    }

    unhideResultSection(type);
}

function renderPlot(inputData, type) {
    let xDomain = [];
    let functionsData= [];
    switch (type) {
        case 'equation':
            xDomain = [inputData.start_x - SPACE_BEFORE_AND_AFTER_INTERVAL, inputData.end_y + SPACE_BEFORE_AND_AFTER_INTERVAL];
            functionsData = [
                {
                    fn: inputData.functionData.stroke,
                },
            ];
            break;
        case 'system':
            xDomain = [-10, 10];
            functionsData = [
                {
                    fn: inputData.functionData.f.stroke,
                    fnType: 'implicit',
                },
                {
                    fn: inputData.functionData.g.stroke,
                    fnType: 'implicit',
                },
            ];
            console.log(functionsData)
            break;
    }

    functionPlot({
        target: "#plot",
        width: 500,
        height: 500,
        xAxis: {
            domain: xDomain,
        },
        grid: true,
        data: functionsData,
    });
}

function printX(x) {
    document.getElementsByClassName('result__solution')[0].textContent = (
        x.toFixed(PRINT_PRECISION)
    );
}

function printF(f) {
    document.getElementsByClassName('result__function-value')[0].textContent = (
        f.toFixed(PRINT_PRECISION)
    );
}

function printIterationsCount(iterationsCount) {
    document.getElementsByClassName('result__iterations-count')[0].textContent = (
        iterationsCount.toString()
    );
}

function printTable(table) {
    const tableElement = document.getElementsByClassName('result__table')[0];
    const tableHead = tableElement.getElementsByTagName('thead')[0];
    tableHead.innerHTML = '';
    for (const columnName of table.columnNames) {
        const th = document.createElement('th');
        th.textContent = columnName;
        tableHead.appendChild(th);
    }

    const tableBody = tableElement.getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    for (const row of (table.rows ?? [])) {
        const tr = document.createElement('tr');
        for (const cell of row) {
            const td = document.createElement('td');
            td.textContent = cell.toFixed(PRINT_PRECISION);
            tr.appendChild(td);
        }
        tableBody.appendChild(tr);
    }
}

function unhideResultSection(type) {
    document.getElementsByClassName('result-plot')[0]?.classList.remove('hidden');
    document.getElementsByClassName('result')[0]?.classList.remove('hidden');

    switch (type) {
        case 'equation':
            document.getElementById('equation-result')?.classList.remove('hidden');
            document.getElementById('system-result')?.classList.add('hidden');
            break;
        case 'system':
            document.getElementById('equation-result')?.classList.add('hidden');
            document.getElementById('system-result')?.classList.remove('hidden');
            break;
    }
}

function printSystemSolution(solution) {
    document.getElementsByClassName('result__system-solution')[0].textContent = (
        convertVectorToString(solution)
    );
}

function printSystemIterationsCount(iterationsCount) {
    console.log(iterationsCount);
    document.getElementsByClassName('result__system-iterations-count')[0].textContent = (
        iterationsCount.toString()
    );
}

function printSystemDiscrepancy(discrepancy) {
    document.getElementsByClassName('result__system-discrepancy')[0].textContent = (
        convertVectorToString(discrepancy)
    );
}

function printSystemValuesVectors(valuesVectors) {
    const tableElement = document.getElementsByClassName('result__system-value-vectors')[0];
    const tableHead = tableElement.getElementsByTagName('thead')[0];
    tableHead.innerHTML = '';
    for (const columnName of ['X', 'Y']) {
        const th = document.createElement('th');
        th.textContent = columnName;
        tableHead.appendChild(th);
    }

    const tableBody = tableElement.getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    for (const [x, y] of valuesVectors) {
        const tr = document.createElement('tr');
        for (const cell of [x, y]) {
            const td = document.createElement('td');
            td.textContent = cell.toFixed(PRINT_PRECISION);
            tr.appendChild(td);
        }
        tableBody.appendChild(tr);
    }
}

function convertVectorToString(vector) {
    return `(${vector.map(x => x.toFixed(PRINT_PRECISION)).join(', ')})`;
}
