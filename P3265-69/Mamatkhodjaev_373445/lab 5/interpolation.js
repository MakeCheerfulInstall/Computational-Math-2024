document.getElementById('input-method').addEventListener('change', function() {
  const method = this.value;
  const dataEntry = document.getElementById('data-entry');
  dataEntry.innerHTML = ''; // Очистка предыдущих полей
  if (method === 'manual') {
    dataEntry.innerHTML = '<input type="text" id="x-values" placeholder="Введите x значения через запятую"><br>' +
                          '<input type="text" id="y-values" placeholder="Введите y значения через запятую">';
  } else if (method === 'file') {
    dataEntry.innerHTML = '<input type="file" id="file-input">';
  } else if (method === 'function') {
    dataEntry.innerHTML = '<select id="function-type">' +
                          '<option value="sin">sin(x)</option>' +
                          '<option value="cos">cos(x)</option>' +
                          '</select>' +
                          '<input type="number" id="start-x" placeholder="Начальное x">' +
                          '<input type="number" id="end-x" placeholder="Конечное x">' +
                          '<input type="number" id="num-points" placeholder="Количество точек">';
  }
});

function processData() {
  const method = document.getElementById('input-method').value;
  let xValues = [];
  let yValues = [];

  if (method === 'manual') {
    console.log()
      xValues = document.getElementById('x-values').value.split(',').map(Number);
      yValues = document.getElementById('y-values').value.split(',').map(Number);
      performInterpolation(xValues, yValues);
  } else if (method === 'file') {
      const fileInput = document.getElementById('file-input');
      const reader = new FileReader();

      reader.onload = function(e) {
          const lines = e.target.result.split('\n');
          lines.forEach(line => {
              if (line.trim()) {
                  const parts = line.split(',');
                  xValues.push(parseFloat(parts[0].trim()));
                  yValues.push(parseFloat(parts[1].trim()));
              }
          });
          performInterpolation(xValues, yValues);
      };

      reader.onerror = function() {
          alert('Не удалось прочитать файл');
      };

      reader.readAsText(fileInput.files[0]);
  } else if (method === 'function') {
      const funcType = document.getElementById('function-type').value;
      const startX = parseFloat(document.getElementById('start-x').value);
      const endX = parseFloat(document.getElementById('end-x').value);
      const numPoints = parseInt(document.getElementById('num-points').value);
      const delta = (endX - startX) / (numPoints - 1);

      for (let i = 0; i < numPoints; i++) {
          const x = startX + i * delta;
          xValues.push(x);
          yValues.push(funcType === 'sin' ? Math.sin(x) : Math.cos(x));
      }
      performInterpolation(xValues, yValues);
  }
}

function performInterpolation(xValues, yValues) {

  xValues = adjustDuplicateXValues(xValues);
  
  const x = parseFloat(prompt("Введите значение X для интерполяции:"));
  const lagrangeResult = lagrangePolynomial(x, xValues, yValues);
  const newtonDivided = newtonDividedDifference(xValues, yValues);
  const newtonDividedResult = newtonDivided(x);
  const newtonFiniteResult = newtonFiniteDifference(xValues, yValues, x);
  const interpolationX = [];
  const interpolationY = [];

  // Вычисление значений интерполяционного полинома для графика
  const minX = Math.min(...xValues);
  const maxX = Math.max(...xValues);
  for (let i = minX; i <= maxX; i += (maxX - minX) / 100) {
      interpolationX.push(i);
      interpolationY.push(newtonDivided(i));
  }

  const output = document.getElementById('output');
    output.innerHTML = `Результат Лагранжа: ${lagrangeResult}<br>` +
                       `Результат Ньютона (разделенные разности): ${newtonDividedResult}<br>` +
                       `Результат Ньютона (конечные разности): ${newtonFiniteResult}<br>`;

  plotGraph(xValues, yValues, interpolationX, interpolationY);
  displayFiniteDifferencesTable(xValues, yValues);
}

function calculateFiniteDifferences(xValues, yValues) {
  const n = yValues.length;
  let differences = new Array(n).fill(null).map(() => new Array(n).fill(null));
  for (let i = 0; i < n; i++) {
      differences[i][0] = yValues[i];
  }

  for (let j = 1; j < n; j++) {
      for (let i = 0; i < n - j; i++) {
          differences[i][j] = differences[i + 1][j - 1] - differences[i][j - 1];
      }
  }

  return differences;
}

function displayFiniteDifferencesTable(xValues, yValues) {
  const differences = calculateFiniteDifferences(xValues, yValues);
  const table = document.createElement('table');
  table.style.width = '100%';
  table.setAttribute('border', '1');
  const header = table.insertRow();
  header.insertCell().textContent = 'x';
  for (let i = 0; i < yValues.length; i++) {
      header.insertCell().textContent = `Δ${i}`;
  }

  for (let i = 0; i < yValues.length; i++) {
      const row = table.insertRow();
      row.insertCell().textContent = xValues[i];
      for (let j = 0; j <= i; j++) {
          row.insertCell().textContent = differences[i - j][j];
      }
  }

  const container = document.getElementById('difference-table');
  container.innerHTML = ''; // Clear previous table
  container.appendChild(table);
}

function adjustDuplicateXValues(xValues) {
  let adjustment = 0.001; // Величина для коррекции повторяющихся значений
  let seen = new Map(); // Для отслеживания встреченных значений и их количества

  return xValues.map(x => {
      if (seen.has(x)) {
          let count = seen.get(x);
          let adjustedValue = x + count * adjustment;
          seen.set(x, count + 1);
          return adjustedValue;
      } else {
          seen.set(x, 1);
          return x;
      }
  });
}


function lagrangePolynomial(x, xPoints, yPoints) {
  debugger;
  let sum = 0;
  const n = xPoints.length;
  for (let i = 0; i < n; i++) {
      let term = yPoints[i];
      for (let j = 0; j < n; j++) {
          if (i !== j) {
              term *= (x - xPoints[j]) / (xPoints[i] - xPoints[j]);
              if(term == -Infinity || term == Infinity) term = 0.001; 
          }
      }
      sum += term;
  }
  return sum;
}

function newtonDividedDifference(xPoints, yPoints) {
  const n = xPoints.length;
  let f = [...yPoints]; // Копирование yPoints в новый массив f
  for (let i = 1; i < n; i++) {
      for (let j = n - 1; j >= i; j--) {
          f[j] = (f[j] - f[j-1]) / (xPoints[j] - xPoints[j-i]);
      }
  }

  // Функция для вычисления значения в точке x
  return function(x) {
      let result = f[0];
      let term = 1;
      for (let i = 1; i < n; i++) {
          term *= (x - xPoints[i-1]);
          if(term == -Infinity) term = 0.001; ;
          result += f[i] * term;
      }
      return result;
  };
}

function newtonFiniteDifference(xPoints, yPoints, x) {
  let n = xPoints.length;
  let f = [...yPoints]; // Инициализация массива конечных разностей
  for (let i = 1; i < n; i++) {
      for (let j = n - 1; j >= i; j--) {
          f[j] = f[j] - f[j-1];
      }
  }

  let result = f[0];
  let h = xPoints[1] - xPoints[0]; // Шаг интервала
  let t = (x - xPoints[0]) / h;
  let term = 1;
  for (let i = 1; i < n; i++) {
      term *= (t - i + 1) / i;
      if(term == -Infinity) term = 0.001; 
      result += term * f[i];
  }
  return result;
}

function plotGraph(xValues, yValues, interpolationX, interpolationY) {
  const trace1 = {
      x: xValues,
      y: yValues,
      mode: 'markers',
      type: 'scatter',
      name: 'Оригинальные данные',
      marker: { size: 12, color: 'blue' }
  };

  const trace2 = {
      x: interpolationX,
      y: interpolationY,
      mode: 'lines',
      type: 'scatter',
      name: 'Интерполяция Ньютона',
      line: { color: 'red' }
  };

  const data = [trace1, trace2];

  const layout = {
      title: 'График интерполяции',
      xaxis: { title: 'X' },
      yaxis: { title: 'Y' }
  };

  Plotly.newPlot('plot', data, layout);
}



