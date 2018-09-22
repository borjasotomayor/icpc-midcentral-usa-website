// 2007 ACM Mid-Central USA Regional Programming Contest
// Alternate solution to Problem D: "Persistent Bits" [easy/moderate]
// Brute force solution without bit-twiddling.
// Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
using namespace std;

int A, B, C, S;

bool seen[65536];       // may overflow stack if a local array!

bool get_bit(int x, int d) {
    return (x % d) >= d/2;      // 0 = false, 1 = true
}    
    
char bit(int d) {
    for (int i = 0; i < 65536; i++) seen[i] = false;
    bool S_bit = get_bit(S, d);
    int x = S;
    do {
        seen[x] = true;
        x = (A*x + B) % C;
        if (get_bit(x, d) != S_bit) return '?';
    } while (! seen[x]);
    return S_bit ? '1' : '0';
}      

int main() {
    ifstream in("bits.in");

    for (;;) {
        in >> A;
        if (A == 0) break;
        in >> B >> C >> S;
        for (int d = 65536; d > 1; d /= 2) cout << bit(d);
        cout << endl;
    }

    in.close();
}
