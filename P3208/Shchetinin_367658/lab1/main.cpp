#include <iostream>
#include <vector>
#include <fstream>
#include <random>
#include <algorithm>
#include <sstream>
#include "Matrix.h"

using namespace std;

double string_to_double( const std::string& s )
{
    std::istringstream i(s);
    double x;
    if (!(i >> x))
        return 0;
    return x;
}

long double input_ld() {
    string s;
    cin >> s;
    replace(s.begin(), s.end(), ',', '.');
    return string_to_double(s);
}

void solve(){

    int n;
    cin >> n;

    vector <vector <long double>> mat(n, vector <long double>(n));
    vector <long double> res(n);

    for (int i = 0; i < n; ++i){
        for (int j = 0; j < n; ++j){
            mat[i][j] = input_ld();
        }
        res[i] = input_ld();
    }

    Matrix matrix(mat, res);
    matrix.methodGauss();
}

void generateRandomMatrix(){
    cout << "Random matrix" << endl;
    int n = rand() % 10 + 1;

    vector <vector <long double>> mat(n, vector <long double>(n));
    vector <long double> res(n);

    for (int i = 0; i < n; ++i){
        for (int j = 0; j < n; ++j){
            mat[i][j] = rand() % 20 - 20;
        }
        res[i] = rand() % 20 - 20;
    }

    Matrix matrix(mat, res);
    matrix.methodGauss();

}

int main() {

    int is_console;
    cout << "Enter 0 if you count from the file, otherwise from the console" << endl;
    cin >> is_console;

    if (is_console) {

        cout << "Input size of matrix: " << endl;
        solve();
        generateRandomMatrix();

    } else {
        freopen("../input.txt", "r", stdin);
        int test;
        cin >> test;

        for (int i = 1; i <= test; ++i){
            cout << "----------------TEST #" << i << "----------------" << endl << endl;
            solve();
        }
    }



    return 0;
}
