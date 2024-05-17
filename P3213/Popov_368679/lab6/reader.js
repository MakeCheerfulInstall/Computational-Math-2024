import { builtinEquations } from "./equations.js";

/** Абстрактный класс чтения входных данных. */
class Reader {

   get selectedEquationIndex(){};

   get rawX0(){};

   get rawY0(){};

   get rawDifferentiateIntervalStart(){};

   get rawDifferentiateIntervalEnd(){};

   get rawStep(){};

   get rawEpsilon(){};

  /** Считать входные данные. */
  async read() {
    return {
      equation: this.readEquation(),
      startCondition: this.readStartCondition(),
      differentiateInterval: this.readDifferentiateInterval(),
      step: this.readStep(),
      epsilon: this.readEpsilon(),
    };
  }

  readEquation() {
    return builtinEquations[this.selectedEquationIndex];
  }

  readStartCondition() {
    return {
      x: this._parseFloatWithComma(this.rawX0),
      y: this._parseFloatWithComma(this.rawY0),
    };
  }

  readDifferentiateInterval() {
    return {
      start: this._parseFloatWithComma(this.rawDifferentiateIntervalStart),
      end: this._parseFloatWithComma(this.rawDifferentiateIntervalEnd),
    };
  }

  readStep() {
    return this._parseFloatWithComma(this.rawStep);
  }

  readEpsilon() {
    return this._parseFloatWithComma(this.rawEpsilon);
  }

  _parseFloatWithComma(value) {
    value = value.replace(",", ".");
    return parseFloat(value);
  }
}

/**
 * Класс для чтения входных данных из формы.
 */
class ReaderFromForm extends Reader {
  get selectedEquationIndex() {
    const inputTypeOptions = document.getElementsByName("equation");

    for (let i = 0; i < inputTypeOptions.length; i++) {
      const optionElement = inputTypeOptions[i] ;
      if (optionElement.checked) {
        return i;
      }
    }

    throw new ReadError("Не выбрано уравнение");
  }

  get rawX0() {
    const input = document.querySelector('[name="x0"]') ;
    return input.value;
  }

  get rawY0() {
    const input = document.querySelector('[name="y0"]') ;
    return input.value;
  }

  get rawDifferentiateIntervalStart() {
    const input = document.querySelector('[name="start"]') ;
    return input.value;
  }

  get rawDifferentiateIntervalEnd() {
    const input = document.querySelector('[name="end"]') ;
    return input.value;
  }

  get rawStep() {
    const input = document.querySelector('[name="h"]') ;
    return input.value;
  }

  get rawEpsilon() {
    const input = document.querySelector(
      '[name="epsilon"]'
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
