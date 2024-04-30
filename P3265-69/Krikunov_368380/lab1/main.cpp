#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
using namespace std;

const double E = 0.0001;

double CalculateDeterminant(vector<vector<double>> a, int n) {
    double det = 1.0;

    if (n == 2) {
        det = a[0][0] * a[1][1] - a[0][1] * a[1][0];
        return det;
    }
    if (n == 3) {
        det = a[0][0] * a[1][1] * a[2][2] + a[0][1] * a[1][2] * a[2][0] + a[0][2] * a[1][0] * a[2][1] -
              a[0][2] * a[1][1] * a[2][0] - a[0][0] * a[1][2] * a[2][1] - a[0][1] * a[1][0] * a[2][2];
        return det;
    }

    for (int i = 0; i < n; ++i) {
        int pivot = i;
        for (int j = i + 1; j < n; ++j) {
            if (abs(a[j][i]) > abs(a[pivot][i])) {
                pivot = j;
            }
        }
        if (pivot != i) {
            det *= -1.0;
            swap(a[i], a[pivot]);
        }
        det *= a[i][i];
        if (abs(det) < E) return 0.0;
        for (int j = i + 1; j < n; ++j) {
            double coefficient = a[j][i] / a[i][i];
            for (int k = i; k < n; ++k) {
                a[j][k] -= coefficient * a[i][k];
            }
        }
    }
    return det;
}

vector<double> GaussSolve(vector<vector<double>> a, vector<double> y, int n) {
    vector<double> answers(n);
    int k, index;
    for (k = 0; k < n; k++) {
        double maxVal = abs(a[k][k]);
        index = k;
        for (int i = k + 1; i < n; i++) {
            if (abs(a[i][k]) > maxVal) {
                maxVal = abs(a[i][k]);
                index = i;
            }
        }

        swap(a[k], a[index]);
        swap(y[k], y[index]);

        for (int i = k; i < n; ++i) {
            double temp = a[i][k];
            if (abs(temp) < E) continue;
            for (int j = k; j < n; j++)
                a[i][j] = a[i][j] / temp;
            y[i] = y[i] / temp;
            if (i == k) continue;
            for (int j = 0; j < n; j++)
                a[i][j] = a[i][j] - a[k][j];
            y[i] = y[i] - y[k];
        }
    }

    for (k = n - 1; k >= 0; k--) {
        answers[k] = y[k];
        for (int i = 0; i < k; i++)
            y[i] = y[i] - a[i][k] * answers[k];
    }
    return answers;
}

void PrintMatrix(vector<vector<double>> a, const vector<double>&b, int n) {
    cout << "Треугольная матрица:" << endl;
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            cout << a[i][j] << "\t";
        }
        cout << "| " << b[i] << endl;
    }
}

void PrintResults(const vector<double>&x, const vector<double>&residuals) {
    cout << "Вектор неизвестных:" << endl;
    for (int i = 0; i < x.size(); ++i) {
        cout << "x[" << i << "] = " << x[i] << endl;
    }
    cout << "Вектор невязок:" << endl;
    for (int i = 0; i < residuals.size(); ++i) {
        cout << "r[" << i << "] = " << residuals[i] << endl;
    }
}

int main() {
    int n;
    cout << "Введите количество уравнений: ";
    cin >> n;
    if (n > 20) {
        cout << "Количество уравнений превышает допустмиое";
        return 0;
    }

    vector<vector<double>> a(n, vector<double>(n));
    vector<double> y(n);

    cout << "Каким способом заполнить матрицу?" << "\n" << "1 - Через файл" << "\n" << "2 - Вручную" << "\n" << "3 - Случайным образом" << "\n";
    int choice;
    cin >> choice;
    if (choice == 1) {
        string filename;
        cout << "Введите имя файла: ";
        cin >> filename;
        ifstream file(filename);
        if (!file.is_open()) {
            cout << "Ошибка открытия файла" << endl;
            return 1;
        }
        // Считываем элементы матрицы
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                if (!(file >> a[i][j])) {
                    cout << "Ошибка чтения из файла" << endl;
                    return 1;
                }
        // Считываем элементы вектора
        for (int i = 0; i < n; ++i)
            if (!(file >> y[i])) {
                cout << "Ошибка чтения из файла" << endl;
                return 1;
            }
        file.close();
    }
    else if (choice == 2) {
        for (int i = 0; i < n; ++i) {
            cout << "Введите коэффициенты " << i + 1 << "-го уравнения через пробел: ";
            for (int j = 0; j < n; j++)
                cin >> a[i][j];
        }
        cout << "Введите правые части уравнений через пробел: ";
        for (int i = 0; i < n; ++i)
            cin >> y[i];
    }
    else if (choice == 3) {
        srand(time(0));
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = rand() % 200 - 100;
            }
        }
        for (int i = 0; i < n; ++i) {
            y[i] = rand() % 200 - 100;
        }
    }

    PrintMatrix(a, y, n);
    double det = CalculateDeterminant(a, n);
    cout << "Определитель матрицы: " << det << endl;
    if (det != 0.0) {
        vector<double> x = GaussSolve(a, y, n);
        vector<double> residuals(n);
        for (int i = 0; i < n; ++i) {
            double sum = 0.0;
            for (int j = 0; j < n; ++j) {
                sum += a[i][j] * x[j];
            }
            residuals[i] = y[i] - sum;
        }
        PrintResults(x, residuals);
    }
    else {
        cout << "Определитель матрицы равен 0 => система либо имеет бесконечное множество решений, либо не имеет решений, т. е. несовместна.";
    }

    return 0;
}
