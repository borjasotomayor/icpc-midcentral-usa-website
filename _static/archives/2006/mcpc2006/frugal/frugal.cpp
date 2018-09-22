// 2006 ACM Mid-Central USA Regional Programming Contest
// Solution to "Frugal Search" [easy]
// Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
#include <cctype>
#include <string>
#include <vector>
#include <algorithm>
using namespace std;

vector<string> words;   // the current word list
string q;               // the current query


inline bool has(string word, char c)
{
    return word.find(c) != string::npos;
}


bool match_term(string word, int i)
{
    int unsigneds = 0;

    for ( ; islower(q[i]); i++)
        if (has(word, q[i]))
            unsigneds++;

    if (unsigneds == 0) return false;

    for ( ; q[i] != '|'; i++) {
        if (q[i] == '+') {
            if (! has(word, q[++i])) return false;
        } else if (q[i] == '-') {
            if (has(word, q[++i])) return false;
        } else {
            // not required, just for sanity checking
            cerr << "error in query " << q << endl;
        }
    }
    
    return true;
}


bool match(string word)
{
    for (string::size_type i = 0; i < q.size(); i = q.find('|', i) + 1) {
        if (match_term(word, i))
            return true;
    }

    return false;
}


int main()
{
    ifstream in("frugal.in");

    for (;;) {
        string w;
        in >> w;
        if (w == "#") break;

        words.clear();
        do {
            words.push_back(w);
            in >> w;
        } while (w != "*");
        sort(words.begin(), words.end());

        for (;;) {
            in >> q;
            if (q == "**") break;
            q += "|"; // make sure the last term ends with a bar
            vector<string>::iterator pos = 
                find_if(words.begin(), words.end(), match);
            cout << (pos != words.end() ? *pos : "NONE") << endl;
        }
        cout << "$" << endl;
    }

    in.close();
}
