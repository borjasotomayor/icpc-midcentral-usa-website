/* Grade School Multiplication, MCPC 2011 Problem A, C++ solution by Michael Goldwasser */
#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

ifstream fin("multiply.in");

string toString(long long n) {
    stringstream ss;
    ss << n;
    return ss.str();
}

string pad(const string& s, int k) {
    stringstream ss;
    for (int j=0; j < k; ++j)
        ss << " ";
    ss << s;
    return ss.str();
}

int main() {
    long long a,b;
    int problem(1);
    while (true) {
        fin >> a >> b;
        if (a == 0) break;
        string aStr = toString(a);
        string bStr = toString(b);
        string result = toString(a*b);
        int n = result.size();

        cout << "Problem " << problem++ << endl;
        cout << pad(aStr, n-aStr.size()) << endl;
        cout << pad(bStr, n-bStr.size()) << endl;
        cout << string(n,'-') << endl;                 // dashes

        int count(0);                // number of lines of partial products
        int zeros(0);                // number of accumulated zeros at end of current line
        int column(result.size());   // what column has rightmost digit of current line
        int d(bStr.size()-1);        // which digit of second operand?
        while (d >= 0) {
            int digit = bStr[d] - '0';
            if (digit == 0) {
                zeros++;
            } else {
                string partial = toString(a*digit);
                cout << pad(partial, column-partial.size()-zeros);
                for (int z=0; z < zeros; z++)
                    cout << "0";
                cout << endl;
                column -= (1+zeros);
                zeros = 0;
                count++;
            }
            d--;
        }

        if (count > 1) {
            cout << string(n,'-') << endl;       // dashes
            cout << result << endl;
        }
    }
}
