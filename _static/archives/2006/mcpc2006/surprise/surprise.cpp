// 2006 ACM Mid-Central USA Regional Programming Contest
// Solution to "Surprising Strings" [easy]
// Eric Shade, Missouri State University

#include <iostream>
#include <string>
#include <fstream>
using namespace std;


bool surprising(string s)
{
    int n = s.length();

    for (int d = 1; d < n; d++)
        for (int j = n-1; j > d; j--)
            for (int i = j-1; i >= d; i--)
                if (s[i] == s[j] && s[i-d] == s[j-d])
                    return false;

    return true;
}


int main()
{
    ifstream in("surprise.in");
    string s;

    for (;;) {
        in >> s;
        if (s == "*") break;
        cout << s << " is ";
        if (! surprising(s)) cout << "NOT ";
        cout << "surprising." << endl;
    }

    in.close();
}
