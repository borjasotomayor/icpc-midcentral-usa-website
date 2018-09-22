// 2006 ACM Mid-Central USA Regional Programming Contest
// Solution to "Linear Pachinko" [easy/moderate]
// Eric Shade, Missouri State University

#include <iostream>
#include <string>
#include <fstream>
using namespace std;


double p(string m, int i, int delta)
{
    while (m[i] == '_') i += delta;
    return m[i] == '.' ? 1.0 : 0.0;
}


int main()
{
    ifstream in("linear.in");
    string m;

    for (;;) {
        in >> m;
        if (m == "#") break;

        int n = m.length();
        double sum = 0.0;
        m = "." + m + ".";

        for (int i = 1; i <= n; i++) {
            switch (m[i]) {
            case '.' : sum += 1.0; break;
            case '/' : sum += p(m, i-1, -1); break;
            case '\\': sum += p(m, i+1,  1); break;
            case '|' : sum += 0.5*p(m, i-1, -1) + 0.5*p(m, i+1, 1);
            }
        }

        cout << static_cast<int>(100.0 * sum / n) << endl;
    }

    in.close();
}
