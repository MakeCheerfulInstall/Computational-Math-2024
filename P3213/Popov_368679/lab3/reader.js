import {functions } from "./solvers/funcs.js";

class Reader {

    get rawStart(){}

    get rawEnd(){}

    get rawEpsilon(){}

    get rawFunctionData(){}

    get rawIntegralSolvingMethod(){}

    async read() {
        return {
            start: this.readStart(),
            end: this.readEnd(),
            epsilon: this.readEpsilon(),
            method: this.readIntegralSolvingMethod(),
            functionData: this.readFunctionData(),
        }
    };

    readStart() {
        const result = this._parseFloatWithComma(this.rawStart);
        if (isNaN(result)) {
            throw new ReadError('Левая граница интервала должна быть числом');
        }

        return result;
    }

    readEnd() {
        const result = this._parseFloatWithComma(this.rawEnd);
        if (isNaN(result)) {
            throw new ReadError('Правая граница интервала должна быть числом');
        }

        return result;
    }

    readEpsilon() {
        const epsilon = this._parseFloatWithComma(this.rawEpsilon);
        if (isNaN(epsilon)) {
            throw new ReadError('Эпсилон должно быть числом');
        } else if (epsilon <= 0) {
            throw new ReadError('Эпсилон должен быть положительным');
        } else if (epsilon >= 1000000) {
            throw new ReadError('Эпсилон должен быть меньше 1000000');
        }
        console.log("epsilon: " + epsilon)
        return epsilon;
    }

    readFunctionData() {
        const functionIndex = parseInt(this.rawFunctionData, 10);
        if (isNaN(functionIndex)) {
            throw new ReadError('Индекс для функции не число');
        } else if (functionIndex < 0 || functionIndex >= functions.length) {
            throw new ReadError('Неверный индекс для функции');
        }

        return functions[functionIndex];
    }

    readIntegralSolvingMethod() {
        return this.rawIntegralSolvingMethod;
    }

    _parseFloatWithComma(value) {
        value = value.replace(',', '.');
        return parseFloat(value);
    }
}

class ReaderFromForm extends Reader {

    get rawEpsilon() {
        const input = document.querySelector('input[name="epsilon"]') ;
        return input.value;
    }

    get rawEnd() {
        const input = document.querySelector('input[name="end"]') ;
        return input.value;
    }

    get rawFunctionData() {
        const funcs = document.querySelectorAll('input[name="function"]');
        for (let i = 0; i < funcs.length; i++) {
            const func = funcs[i] ;
            if (func.checked) {
                return func.value;
            }
        }

        throw new ReadError('Не выбрана функция');
    }

    get rawStart() {
        const input = document.querySelector('input[name="start"]') ;
        return input.value;
    }

    get rawIntegralSolvingMethod() {
        switch (document.getElementById('meth').selectedIndex) {
            case 0:
                return "left-rectangle"
            case 1:
                return "right-rectangle"
            case 2:
                return "middle-rectangle"
            case 3:
                return "trapezoid"
            case 4:
                return "simpson"
        }

        throw new ReadError('Не выбран метод интегрирования');
    }

}

class ReadError extends Error {
    constructor(message) {
        super(message);
        this.name = 'ReadError';
    }
}

/**
 * Получить экземпляр класса для чтения входных данных в зависимости от того,
 * что выбрал пользователь.
 */
export function getReader() {
    return new ReaderFromForm();
}
