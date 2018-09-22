// 2007 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem B: "The Seven Percent Solution" [very easy]
// Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
using namespace std;

int main() {
    ifstream in("percent.in");
    char c;

    while ((c = in.get()) != '#') {
        switch (c) {
        case ' ': cout << "%20"; break;
        case '!': cout << "%21"; break;
        case '$': cout << "%24"; break;
        case '%': cout << "%25"; break;
        case '(': cout << "%28"; break;
        case ')': cout << "%29"; break;
        case '*': cout << "%2a"; break;
        default : cout << c;
        }
    }

    in.close();
}
