
export class NewtonWithDividedDiffs {
  static solve(xCur, data) {
    let size = data.size;
    let xArray = [...data.points.map((point) => point.x)]; // Работаем с копиями для безопасности
    let cArray = [...data.points.map((point) => point.y)];
    for (let k = 1; k < size; k++) {
      for (let j = size - 1; j >= k; j--) {
        cArray[j] = (cArray[j] - cArray[j - 1]) / (xArray[j] - xArray[j - k]);
      }
    }

    size--; // Степень полинома
    let answer = cArray[size];
    for (let k = 1; k < size + 1; k++) {
      answer = cArray[size - k] + (xCur - xArray[size - k]) * answer;
    }
    return answer;
  }
}
