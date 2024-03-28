import {functions, systems} from "./solvers/Equations.js";
import {getReader} from "./reader.js";
import {NewtonForSystems} from "./solvers/newtonForSystems.js";
import {showResult} from "./printer.js";
import {Newton} from "./solvers/newton.js";
import {ProstoIter} from "./solvers/prostoIter.js";
import {PolDel} from "./solvers/polDel.js";
import {Chords} from "./solvers/chords.js";


const onClick = () => {

    const button = document.getElementById('solve-button');
    button.addEventListener('click', () => {
            console.log(document.getElementById('meth').selectedIndex)
            const reader = getReader();
            reader.read().then(inputData => {
                let result;
                switch (inputData.type) {
                    case 'equation':
                         switch (inputData.meth) {
                             case "polDel":
                                 const polDel = new PolDel();
                                 result = polDel.solve(inputData);
                                 break;
                             case "newton":
                                 const newton = new Newton();
                                 result = newton.solve(inputData);
                                 break;
                             case "prostoIter":
                                 const prostoIter = new ProstoIter();
                                 result = prostoIter.solve(inputData);
                                 break;
                         }
                        // const chord = new Chords();
                        // result = chord.solve(inputData);
                        console.log(result.table)
                        showResult(inputData, inputData.type, result)
                        break;
                    case 'system':
                        const newtonForSystems = new NewtonForSystems();
                        result = newtonForSystems.solve(inputData);
                        showResult(inputData, inputData.type, result)
                        break;
                }
            })
        }
    )
}


const changeFunc = () => {
    const taskTypeButton = document.getElementsByName('task-type');
    taskTypeButton.forEach((button) => {
        button.addEventListener('change', (event) => {
            const checkedRadio = event.target;
            switch (checkedRadio.value) {
                case 'equation':
                    document.getElementById('equation-field').classList.remove('hidden');
                    document.getElementById('system-field').classList.add('hidden');
                    console.log(document.getElementById('equation-field').classList)
                    console.log(document.getElementById('system-field').classList)
                    break;
                case 'system':
                    document.getElementById('system-field')?.classList.remove('hidden');
                    document.getElementById('equation-field')?.classList.add('hidden');
                    console.log(document.getElementById('equation-field')?.classList)
                    console.log(document.getElementById('system-field')?.classList)
                    break;
            }
        });
    });
}

const loadSystems = () => {
    const systemsContainer = (
        document.getElementById('system-select-container')
    );
    systemsContainer.innerHTML = '';

    for (const i in systems) {
        const system = systems[i];
        systemsContainer.innerHTML += `
    <label for="system${i}" class="flex flex-row">
      <input type="radio" name="system" id="system${i}" value="${i}">
      <span class="system flex flex-row">
        <span class="text-4xl ">{</span>
        <span class="text-start">
          <div>${system.f.html}</div>
          <div>${system.g.html}</div>
        </span>
      </span>
    </label>
    `;
    }
}

function loadEquations() {
    const equationsContainer = document.getElementById('equation-select-container');
    equationsContainer.innerHTML = '';
    for (const i in functions) {
        const equation = functions[i];
        equationsContainer.innerHTML += `
    <label for="equation${i}">
      <input type="radio" name="equation" id="equation${i}" value="${i}">
      <span>${equation.html}</span>
    </label>
    `;
    }
}


window.onload = () => {
    onClick();
    changeFunc();
    loadEquations();
    loadSystems();
}