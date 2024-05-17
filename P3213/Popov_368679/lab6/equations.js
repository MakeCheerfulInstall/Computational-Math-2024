
export const ALL_METHODS = ["euler", "runge-kutta", "milne"];

export const builtinEquations = [
    {
        yDerivative: (x, y) => 3 * x + 7 * y,
        printableYDerivative: "y' = 3x + 7y",
        exactSolutionFn: (x) => (Math.exp(7 * x) - 21 * x - 3) / 49,
        exactSolutionForPlot: "y = (exp(7*x) - 21*x - 3)/49",
    },
    {
        yDerivative: (x, y) => y + Math.cos(2 * x),
        printableYDerivative: "y' = y + cos(2x)",
        exactSolutionFn: (x) => (2 * Math.sin(2 * x) - Math.cos(2 * x) + 5 * Math.exp(x)) / 5,
        exactSolutionForPlot: "y = (2 * sin(2 * x) - cos(2 * x) + 5 * exp(x))/5",
    },
    {
        yDerivative: (x, y) => Math.exp(x) - 100 * Math.log(x),
        printableYDerivative: "y' = e<sup>x</sup> - 100 * ln(x)",
        exactSolutionFn: (x) => -100 * x * Math.log(x) + Math.exp(x) + 100 * x - Math.E - 99,
        exactSolutionForPlot: "y = -100 * x * log(x) + exp(x) + 100 * x - 2.71 - 99",
    },

];
