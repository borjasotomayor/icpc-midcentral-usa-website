#include <iostream>
#include <string>
#include <fstream>
using namespace std;


int p(string m, int i, int delta)
{
    while (m[i] == '_') i += delta;
    return m[i] == '.' ? 1 : 0;
}


int main()
{
    ifstream in("linear.in");
    string m;

    for (;;) {
        in >> m;
        if (m == "#") break;

        int n = m.length();
        int sum = 0;
        m = "." + m + ".";

        for (int i = 1; i <= n; i++) {
            switch (m[i]) {
            case '.' : sum += 100; break;
            case '/' : sum += 100*p(m, i-1, -1); break;
            case '\\': sum += 100*p(m, i+1, 1); break;
            case '|' : sum += 50*p(m, i-1, -1) + 50*p(m, i+1, 1);
            }
        }

        cout << sum/n << endl;
    }

    in.close();
}
