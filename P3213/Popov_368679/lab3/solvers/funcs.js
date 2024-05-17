export const  INITIAL_PARTITION_NUMBER = 4;
export const  MAX_ITERATIONS = 10;

export const functions = [
    {
        value: (x) => -3 * x ** 3 - 5 * x ** 2 + 4 * x - 2,
    printableValue: '-3x^3 - 5x^2 + 4x - 2',
},
{
    value: (x) => x ** 3 - 3 * x ** 2 + 7 * x - 10,
    printableValue: 'x^3 - 3x^2 + 7x - 10',
},
{
    value: (x) => 5 * x ** 3 - 2 * x ** 2 + 3 * x - 15,
    printableValue: '5x^3 - 2x^2 + 3x - 15',
},
{
    value: (x) => 1/x,
    printableValue: '1/x',
},
{
    value: (x) => 1/Math.sin(x),
    printableValue: '1/sin(x)',
},
{
    value: (x) => Math.log(x),
    printableValue: 'ln(x)',
},
];