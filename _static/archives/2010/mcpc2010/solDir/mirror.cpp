/* Mirror, Mirror on the Wall, MCPC 2010 Problem E by Michael Goldwasser */

#include <iostream>
#include <fstream>
#include <map>
using namespace std;

fstream fin("mirror.in");

int main() {
  map<char, char> sym;
  sym['b'] = 'd';
  sym['d'] = 'b';
  sym['p'] = 'q';
  sym['q'] = 'p';
  sym['i'] = 'i';
  sym['o'] = 'o';
  sym['v'] = 'v';
  sym['w'] = 'w';
  sym['x'] = 'x';

  string original;
  fin >> original;
  while (original != "#") {

    string mirror;
    bool good = true;
    for (int k=original.size()-1; good && k >= 0; k--) {
      map<char,char>::iterator locate = sym.find(original[k]);
      if (locate != sym.end())
        mirror += sym[original[k]];
      else
        good = false;
    }

    if (good)
      cout << mirror << endl;
    else
      cout << "INVALID" << endl;

    fin >> original;
  }
}

