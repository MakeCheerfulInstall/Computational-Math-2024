

export function createTable(data) {
  const size = data.size;

  let columnNames = [];
  columnNames.push("xi");
  columnNames.push("yi");
  columnNames.push("∆y");
  for (let i = 2; i < size; i++) {
    columnNames.push(`∆<sup>${i}</sup>y`);
  }

  let rows = [];
  for (let i = 0; i < size; i++) {
    rows.push([]);
    rows[i].push(data.points[i].x);
    rows[i].push(data.points[i].y);
  }
  for (let i = 1; i < size; i++) {
    for (let j = 0; j < size - i; j++) {
      rows[j].push(rows[j + 1][i] - rows[j][i]);
    }
  }
  return {
    columnNames: columnNames,
    rows: rows,
  };
}