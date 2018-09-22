// 2008 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem A: "Parity" [very easy]
// Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
using namespace std;

int main() {
    ifstream in("parity.in");
    int p = 0;
    char c;

    while ((c = in.get()) != '#') {
        switch (c) {
        case '0': cout << c; break;
        case '1': cout << c; p = 1 - p; break;
        case 'e': cout << p << endl; p = 0; break;
        case 'o': cout << (1 - p) << endl; p = 0;
        // note that newlines in the input are read and ignored
        }
    }

    in.close();
}
