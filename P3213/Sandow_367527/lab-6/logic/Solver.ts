import { ALL_METHODS, InputData, Result, SolvingMethod } from "./definitions";

export class Solver {
  public static solve(data: InputData): Result {
    const result: Result = {
      euler: null,
      "runge-kutta": null,
      milne: null,
    };
    const columnNames = ["x", "y"];
    ALL_METHODS.forEach((methodName: SolvingMethod) => {
      const method = Solver.getSolvingMethod(methodName);
      console.log(methodName, method(data));
      const table = { columnNames: columnNames, rows: method(data) };
      const pointsForGraph = method({ ...data, step: 0.01 });
      result[methodName] = { table, pointsForGraph };
    });
    return result;
  }

  public static getSolvingMethod(
    methodName: SolvingMethod
  ): (data: InputData) => number[][] {
    switch (methodName) {
      case "euler":
        return Solver.getPointsByEulerMethod;
      case "runge-kutta":
        return Solver.getPointsByRungeKuttaMethod;
      case "milne":
        return Solver.getPointsByMilneMethod;
    }
  }

  public static getPointsByEulerMethod(data: InputData): number[][] {
    const { equation, startCondition, differentiateInterval, step } = data;
    let x = startCondition.x;
    let y = startCondition.y;
    let rows: number[][] = [];

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

  public static getPointsByRungeKuttaMethod(data: InputData): number[][] {
    const { equation, startCondition, differentiateInterval, step } = data;
    let x = startCondition.x;
    let y = startCondition.y;
    let rows: number[][] = [];
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

  public static getPointsByMilneMethod(data: InputData): number[][] {
    // Начальные точки складываем с помощью метода Рунге-Кутта
    // let initialPoints = Solver.getPointsByRungeKuttaMethod({
    //   ...data,
    //   differentiateInterval: {
    //     start: data.differentiateInterval.start,
    //     end: data.startCondition.x + 3 * data.step,
    //   },
    // });
    let initialPoints = Solver.getPointsByRungeKuttaMethod(data);

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
