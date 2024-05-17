

const EPS = 1e-2;

/** Абстрактный класс чтения входных данных. */
class Reader {
   functionsForPoints = [
    (x) => Math.sin(x),
    (x) => x ** 3,
  ];

  get rawX(){};

  get rawInputType(){};

  get rawPointsX(){};

  get rawPointsY(){};

  get rawFunctionIndex(){};

  get rawFunctionIntervalStart(){};

  get rawFunctionIntervalEnd(){};

  get rawFunctionPointsCount(){};

  get functionForPoints() {
    return this.functionsForPoints[this.rawFunctionIndex];
  }

  get pointsFromFunction() {
    const points = [];
    const start = this._parseFloatWithComma(this.rawFunctionIntervalStart);
    const end = this._parseFloatWithComma(this.rawFunctionIntervalEnd);
    const count = parseInt(this.rawFunctionPointsCount) - 1;
    const step = (end - start) / count;
    for (let i = 0; i <= count; i++) {
      const x = start + i * step;
      points.push({ x, y: this.functionForPoints(x) });
    }
    return points;
  }

  /** Считать входные данные. */
  async read() {
    return {
      points: this.readPoints(),
      size: this.readPoints().length,
      xVal: this.readX(),
    };
  }

  readX() {
    return this._parseFloatWithComma(this.rawX);
  }

  readPoints() {
    switch (this.rawInputType) {
      case "function":
        return this.pointsFromFunction;
      case "points":
        const pointsX = this.rawPointsX.trim().split(/\s/);
        const pointsY = this.rawPointsY.trim().split(/\s/);
        if (pointsX.length !== pointsY.length) {
          throw new ReadError("Количество X и Y точек не совпадает");
        }
        return this._sortAndFixDuplicateX(
          pointsX.map((x, i) => ({
            x: this._parseFloatWithComma(x),
            y: this._parseFloatWithComma(pointsY[i]),
          }))
        );
    }
  }

  _parseFloatWithComma(value) {
    value = value.replace(",", ".");
    return parseFloat(value);
  }

  _sortAndFixDuplicateX(points) {
    points.sort();

    for (let i = 1; i < points.length; i++) {
      if (points[i].x === points[i - 1].x) {
        points[i].x += EPS;
      }
    }

    return points;
  }
}

/**
 * Класс для чтения входных данных из формы.
 */
class ReaderFromForm extends Reader {
  get rawX() {
    const input = document.querySelector('[name="x"]') ;
    return input.value;
  }

  get rawInputType() {
    const inputTypeOptions = document.getElementsByName("input-type-select");

    for (let i = 0; i < inputTypeOptions.length; i++) {
      const optionElement = inputTypeOptions[i] ;
      if (optionElement.checked) {
        return optionElement.value ;
      }
    }

    throw new ReadError("Не выбран тип ввода данных");
  }

  get rawPointsX() {
    const input = document.querySelector(
      '[name="points-x"]'
    ) ;
    return input.value;
  }

  get rawPointsY() {
    const input = document.querySelector(
      '[name="points-y"]'
    ) ;
    return input.value;
  }

  get rawFunctionIndex() {
    const inputTypeOptions = document.getElementsByName("function-select");

    for (let i = 0; i < inputTypeOptions.length; i++) {
      const optionElement = inputTypeOptions[i] ;
      if (optionElement.checked) {
        return i;
      }
    }

    throw new ReadError("Не выбрана функция");
  }

  get rawFunctionIntervalStart() {
    const input = document.querySelector('[name="start"]') ;
    return input.value;
  }

  get rawFunctionIntervalEnd() {
    const input = document.querySelector('[name="end"]') ;
    return input.value;
  }

  get rawFunctionPointsCount() {
    const input = document.querySelector(
      '[name="points-count"]'
    ) ;
    return input.value;
  }
}

class ReadError extends Error {
  constructor(message) {
    super(message);
    this.name = "ReadError";
  }
}

/**
 * Получить экземпляр класса для чтения входных данных в зависимости от того,
 * что выбрал пользователь.
 */
export function getReader() {
  return new ReaderFromForm();
}
