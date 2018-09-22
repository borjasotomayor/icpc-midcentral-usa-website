// 2005 ACM Mid-Central Regional Programming Contest
// Problem I: Netiquette
// Written and Coded by Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
#include <string>
#include <cctype>
using namespace std;

bool VERBOSE = false;
bool ok;


bool isloner(char c) 
{ 
    return c != ' ' && c != 'a' && c != 'A' && c != 'I'; 
}


void error(int pos, string description)
{
    ok = false;

    if (VERBOSE) {
        for (int i = 1; i < pos; i++) cerr << ' ';
        cerr << "^-- " << description << endl;
    }
}


int main ()
{
    string msg;

    ifstream in("netiq.in");

    for (;;) {
        getline(in, msg);
        
        if (msg == "#") break;
        
        if (VERBOSE) cerr << msg << endl;

        // pad with spaces to simplify algorithm
        msg = " " + msg + " ";

        ok = true;
        
        for (int i = 1; ok && i+1 < msg.length(); i++) {
            char x = msg[i-1];
            char y = msg[i];
            char z = msg[i+1];

            if (isupper(x) && isupper(y))
                error(i, "two adjacent uppercase letters");
            else if (isdigit(x) && isalpha(y) || isalpha(x) && isdigit(y))
                error(i, "digit next to a letter");
            else if (isloner(y) && x == ' ' && z == ' ')
                error(i, "isolated character other than a, A, or I");
            else if (ispunct(x) && ispunct(y) && x != '"' && y != '"')
                error(i, "adjacent punctuation other than \"");
        }
        
        cout << (ok ? "OK" : "suspicious") << endl;
    }

    in.close();
}
