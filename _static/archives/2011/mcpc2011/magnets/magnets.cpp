/* Refrigerator Magnets, MCPC 2011 Problem E, C++ solution by Gabriel Foust */
#include <fstream>
#include <iostream>
#include <string>
using namespace std;

ifstream fin( "magnets.in" );

int main()
{
    string line;
    while (true)
    {
        getline(fin,line);
        if (line[line.size()-1] == '\r') line.resize(line.size()-1);  // portability
        if (line == "END") break;
        bool duplicate= false;
        int count[26]= {0};

        for (int i= 0; i < line.length(); ++i)
        {
            char c= line[i];
            if (c != ' '  && ++count[c - 'A'] > 1)
                duplicate= true;
        }

        if (!duplicate)
            cout << line << endl;
    }
    return 0;
}
