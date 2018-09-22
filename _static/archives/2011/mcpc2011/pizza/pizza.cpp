/* Pizza Pricing, MCPC 2011 Problem C, C++ solution by Michael Goldwasser */
#include <iostream>
#include <fstream>
#include <map>        // only for debugging
using namespace std;

void judgeCheck(map<int,int> menu, int N);

int main() {
    ifstream fin("pizza.in");
    int menu(1);
    while (true) {
        map<int,int> pizzas; // only for debugging

        int N;
        fin >> N;
        if (N == 0) break;
        if (N < 0 || N > 10) cerr << "ERROR: invalid N" << endl;

        int bestD, bestP;             // initialize to first choice
        fin >> bestD >> bestP;
        pizzas[bestD] = bestP;        // for judge's check only
        for (int k=1; k < N; k++) {   // consider all other choices
            int D,P;
            fin >> D >> P;

            if (P * bestD * bestD <  bestP * D * D) {  // Note: avoids divisions
                bestD = D;
                bestP = P;
            }
            pizzas[D] = P;            // for judge's check only
        }

        cout << "Menu " << menu++ << ": " << bestD << endl;

        judgeCheck(pizzas,N);
    }
}



void judgeCheck(map<int,int> menu, int N) {
    double epsilon = -1;
    if (menu.size() != N)
        cerr << "ERROR: duplicate pizza diameter" << endl;
    for (map<int,int>::iterator it = menu.begin(); it != menu.end(); ++it) {
        int D = it->first;
        int P = it->second;
        if (D < 1 || D > 36) cerr << "ERROR: invalid D" << endl;
        if (P < 1 || P > 100) cerr << "ERROR: invalid P" << endl;
        map<int,int>::iterator rest = it;
        ++rest;
        while (rest != menu.end()) {
            int rD = rest->first;
            int rP = rest->second;
            if (P * rD * rD == rP * D * D)
                cerr << "ERROR: duplicate price/sqin" << endl;
            double e = 1.0*P/(D*D) - 1.0*rP/(rD*rD);
            if (e < 0) e == -e;  // take absolute value
            if (epsilon == -1 || epsilon > e)
                epsilon = e;
            ++rest;
        }
    }
    cerr << "Smallest epsilon was " << epsilon << endl;
}
