import {functions, systems} from "./solvers/Equations.js";

class Reader {
    async read() {
        const result = {
            type: this.readTaskType(),
            functionData: this.readTaskType() === 'equation' ? this.readFunctionData() : this.readSystemData(),
            epsilon: this.readEpsilon(),
            start_x: this.readTaskType() === 'equation' ? this.readStart() : this.readX(),
            end_y: this.readTaskType() === 'equation' ? this.readEnd() : this.readY(),
            meth: this.readMeth()
        };
        if (result.start_x > result.end_y && result.type === 'equation') {
            throw new ReadError('Левая граница интервала должна быть меньше правой');
        }
        return result;
    }

    readStart() {
        const result = this._parseFloatWithComma(this.rawStart);
        if (isNaN(result)) {
            throw new ReadError('Левая граница интервала должна быть числом');
        }
        return result;
    }
    readMeth() {
        return this.rawMeth;
    }

    readX() {
        const result = this._parseFloatWithComma(this.rawX);
        if (isNaN(result)) {
            throw new ReadError('Приближение x должно быть числом');
        }
        return result;
    }

    readY() {
        const result = this._parseFloatWithComma(this.rawY);
        if (isNaN(result)) {
            throw new ReadError('Приближение y должно быть числом');
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

    readSystemData() {
        const functionIndex = parseInt(this.rawSystemData, 10);
        if (isNaN(functionIndex)) {
            throw new ReadError('Индекс для функции не число');
        } else if (functionIndex < 0 || functionIndex >= systems.length) {
            throw new ReadError('Неверный индекс для функции');
        }
        return systems[functionIndex];
    }

    readTaskType() {
        return this.rawTaskType;
    }

    _parseFloatWithComma(value) {
        value = value.replace(',', '.');
        return parseFloat(value);
    }
}

class ReaderFromForm extends Reader {
    get rawMeth() {
        switch (document.getElementById('meth').selectedIndex) {
            case 0:
                return "polDel"
            case 1:
                return "newton"
            case 2:
                return "prostoIter"
        }
    }
    get rawEpsilon() {
        const input = document.querySelector('input[name="epsilon"]');
        return input.value;
    }

    get rawEnd() {
        const input = document.querySelector('input[name="end"]');
        return input.value;
    }

    get rawFunctionData() {
        const equations = document.querySelectorAll('input[name="equation"]');
        for (let i = 0; i < equations.length; i++) {
            const equation = equations[i];
            if (equation.checked) {
                return equation.value;
            }
        }
        throw new ReadError('Не выбрано уравнение');
    }

    get rawSystemData() {
        const equations = document.querySelectorAll('input[name="system"]');
        for (let i = 0; i < equations.length; i++) {
            const equation = equations[i];
            if (equation.checked) {
                return equation.value;
            }
        }
        throw new ReadError('Не выбрано уравнение');
    }

    get rawStart() {
        const input = document.querySelector('input[name="start"]');
        return input.value;
    }

    get rawX() {
        const input = document.querySelector('input[name="x"]');
        return input.value;
    }

    get rawY() {
        const input = document.querySelector('input[name="y"]');
        return input.value;
    }

    get rawTaskType() {
        const taskTypeButton = document.getElementsByName('task-type');
        for (let i = 0; i < taskTypeButton.length; i++) {
            const optionElement = taskTypeButton[i];
            if (optionElement.checked) {
                return optionElement.value;
            }
        }

        throw new ReadError('Не выбран тип задачи');
    }
}

class ReaderFromFile extends Reader {

    fileLines;

    constructor(file) {
        super();
        this.file = file;
    }

    async read() {
        let content;
        try {
            content = await this.file.text();
        } catch (e) {
            throw new ReadError('Не удалось считать файл, попробуйте его переоткрыть');
        }

        this.fileLines = content.split('\n').map(s => s.trim());
        return super.read();
    }
    get rawMeth() {
        return this.fileLines[4]?.trim();
    }

    get rawStart() {
        return this.fileLines[3]
            .split(/\s/)[0]
            .trim();
    }

    get rawEnd() {
        return this.fileLines[3]
            .split(/\s/)[1]
            .trim();
    }

    get rawEpsilon() {
        return this.fileLines[1].trim();
    }

    get rawFunctionData() {
        return this.fileLines[2].trim();
    }

    get rawTaskType() {
        return this.fileLines[0].trim();
    }

    get rawX() {
        return this.fileLines[3]
            .split(/\s/)[0]
            .trim();
    }

    get rawY() {
        return this.fileLines[3]
            .split(/\s/)[1]
            .trim();
    }

    get rawSystemData() {
        return this.fileLines[2].trim();
    }

}


class ReadError extends Error {
    constructor(message) {
        super(message);
        this.name = 'ReadError';
        alert(message);
    }
}

export function getReader() {
    const fileInput = document.querySelector('input[name="input-file"]');
    const files = fileInput.files;
    if (files && files.length > 0) {
        return new ReaderFromFile(files[0]);
    } else {
        return new ReaderFromForm();
    }
}