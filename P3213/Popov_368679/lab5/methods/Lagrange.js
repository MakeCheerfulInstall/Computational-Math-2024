
export class Lagrange {
  static solve(xCur, data) {
    const size = data.size;
    let answer = 0;
    for (let i = 0; i < size; i++) {
      let polynomial = 1;
      for (let j = 0; j < size; j++) {
        if (i !== j) {
          polynomial *=
            (xCur - data.points[j].x) / (data.points[i].x - data.points[j].x);
        }
      }
      answer += data.points[i].y * polynomial;
    }
    return answer;
  }
}
