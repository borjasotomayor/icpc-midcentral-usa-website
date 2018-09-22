// 2005 ACM Mid-Central Regional Programming Contest
// Problem F: Painter
// Posed by John Cigas, Rockhurst University
// Written by Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
using namespace std;

const bool VERBOSE = false;

const int BOTTLE_SIZE = 50;

int N, G, C[12];


void sort_colors()
{
    // simple insertion sort

    for (int i = 1; i < N; i++) {
        int j, temp = C[i];
        for (j = i; j > 0 && temp < C[j-1]; j--)
            C[j] = C[j-1];
        C[j] = temp;
    }
}

void show_colors()
{
    if (VERBOSE) {
        cerr << C[0];
        
        for (int i = 1; i < N; i++) 
            cerr << " " << C[i];

        cerr << endl;
    }
}

int main()
{
    ifstream in("painter.in");

    for (;;) {
        in >> N;
        
        if (N == 0) break;
        
        for (int i = 0; i < N; i++) in >> C[i];

        in >> G;

        for (int i = 0; i < G; i++) {
            sort_colors();
            ++C[0];
            ++C[1];
            ++C[2];
            show_colors();
        }

        sort_colors();
        show_colors();

        int bottles = C[N-1] / BOTTLE_SIZE;
        
        if (C[N-1] % BOTTLE_SIZE != 0) ++bottles;

        cout << bottles << endl;
    }

    in.close();
}
