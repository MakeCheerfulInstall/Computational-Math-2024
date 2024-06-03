import { OrdinaryDifferentialEquation } from "./definitions";

export const builtinEquations: OrdinaryDifferentialEquation[] = [
  {
    yDerivative: (x, y) => Math.sin(x),
    printableYDerivative: "y' = sin(x)",
    exactSolutionFn: (x) => -Math.cos(x),
    exactSolutionForPlot: "y = -cos(x)",
  },
  // {
  //   yDerivative: (x, y) => y + (1 + x) * y ** 2,
  //   printableYDerivative: "y' = y + (1 + x) * y<sup>2</sup>",
  //   exactSolutionFn: (x) => 1 / (Math.exp(-x) - x),
  //   exactSolutionForPlot: "y = 1 / (exp(-x) - x)",
  // },
  {
    yDerivative: (x, y) => Math.exp(x) - 100 * Math.log(x),
    printableYDerivative: "y' = e<sup>x</sup> - 100 * ln(x)",
    exactSolutionFn: (x) => -100 * x * Math.log(x) + Math.exp(x) + 100 * x - Math.E - 99,
    exactSolutionForPlot: "y = -100 * x * log(x) + exp(x) + 100 * x - 2.71 - 99",
  },
  {
    yDerivative: (x, y) => x + y,
    printableYDerivative: "y' = x + y",
    exactSolutionFn: (x) => Math.exp(x) - x - 1,
    exactSolutionForPlot: "y = exp(x) - x - 1",
  },
];
