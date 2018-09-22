// 2008 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem H: "Steganography" [easy/moderate]
// Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
using namespace std;

char decode[] = " ABCDEFGHIJKLMNOPQRSTUVWXYZ',-.?";

int main() {
    ifstream in("steg.in");
    int spaces = 0;
    int bits = 0;
    int code = 0;
    char c;

    while ((c = in.get()) != '#') {
        if (c == ' ') {
            ++spaces;
        } else {
            if (spaces > 0) {
                code = (code << 1) + 1 - (spaces & 1);
                spaces = 0;
                if (++bits == 5) {
                    cout << decode[code];
                    code = bits = 0;
                }
            }
            if (c == '*') {
                if (bits > 0) {
                    cout << decode[code << (5 - bits)];
                    code = bits = 0;
                }
                cout << endl;
            }
        }
    }

    in.close();
}
