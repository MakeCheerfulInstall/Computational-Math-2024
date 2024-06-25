import {ALL_METHODS} from "./equations";


export class Solve {
  solve(data) {
    const result = {
      euler: null,
      "runge-kutta": null,
      milne: null,
    };
    const columnNames = ["x", "y"];
    ALL_METHODS.forEach((methodName) => {
      const method = this.getSolvingMethod(methodName);
      console.log(methodName, method(data));
      const table = { columnNames: columnNames, rows: method(data) };
      const pointsForGraph = method({ ...data, step: 0.01 });
      result[methodName] = { table, pointsForGraph };
    });
    return result;
  }

  getSolvingMethod(methodName) {
    switch (methodName) {
      case "euler":
        return this.EulerMethod;
      case "runge-kutta":
        return this.RungeKuttaMethod;
      case "milne":
        return this.MilneMethod;
    }
  }

  EulerMethod(data) {
    const { equation, startCondition, differentiateInterval, step } = data;
    let x = startCondition.x;
    let y = startCondition.y;
    let rows = [];

    while (differentiateInterval.start < x) {
      rows = [[x, y], ...rows];
      y -= step * equation.yDerivative(x, y);
      x -= step;
    }

    x = startCondition.x;
    y = startCondition.y;

    while (x <= differentiateInterval.end) {
      rows.push([x, y]);
      y += step * equation.yDerivative(x, y);
      x += step;
    }
    return rows;
  }

  RungeKuttaMethod(data) {
    const { equation, startCondition, differentiateInterval, step } = data;
    let x = startCondition.x;
    let y = startCondition.y;
    let rows = [];
    rows.push([x, y]);

    while (differentiateInterval.start < x) {
      const k1 = step * equation.yDerivative(x, y);
      const k2 = step * equation.yDerivative(x - step / 2, y - k1 / 2);
      const k3 = step * equation.yDerivative(x - step / 2, y - k2 / 2);
      const k4 = step * equation.yDerivative(x - step, y - k3);
      y -= (k1 + 2 * k2 + 2 * k3 + k4) / 6;
      x -= step;
      rows = [[x, y], ...rows];
    }

    x = startCondition.x;
    y = startCondition.y;

    while (x <= differentiateInterval.end) {
      const k1 = step * equation.yDerivative(x, y);
      const k2 = step * equation.yDerivative(x + step / 2, y + k1 / 2);
      const k3 = step * equation.yDerivative(x + step / 2, y + k2 / 2);
      const k4 = step * equation.yDerivative(x + step, y + k3);
      y += (k1 + 2 * k2 + 2 * k3 + k4) / 6;
      x += step;
      rows.push([x, y]);
    }

    return rows;
  }

  MilneMethod(data) {
      let solver = new Solve();
    let initialPoints = solver.RungeKuttaMethod(data);

    const { equation, differentiateInterval, step } = data;
    let rows = initialPoints.slice();
    let x = rows[rows.length - 1][0];
    let ys = rows.map((row) => row[1]);

    while (x <= differentiateInterval.end) {
      let y =
        ys[0] -
        4 *
          step *
          (2 * equation.yDerivative(x - 2 * step, ys[2]) -
            equation.yDerivative(x - step, ys[3]) +
            2 * equation.yDerivative(x, ys[4]));
      x += step;
      rows.push([x, y]);
      ys.shift();
      ys.push(y);
    }

    return rows;
  }
}
