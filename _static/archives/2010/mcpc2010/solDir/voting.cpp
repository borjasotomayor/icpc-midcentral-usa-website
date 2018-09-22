/* Voting, MCPC 2010 Problem D, C++ solution by Michael Goldwasser */
#include <iostream>
#include <fstream>
using namespace std;

fstream fin("voting.in");

int main() {

  while (true) {
    string votes;
    fin >> votes;
    if (votes == "#") break; // end of input

    int y,n,a,p;
    y = n = a = p = 0;
    for (int k=0; k < votes.size(); k++) {
      switch (votes[k]) {
      case 'Y':
        y++;
        break;
      case 'N':
        n++;
        break;
      case 'A':
        a++;
        break;
      case 'P':
        p++;
        break;
      default:
        cout << "OOPS" << endl;
      }
    }

    if (a >= y+n+p)
      cout << "need quorum" << endl;
    else if (y > n)
      cout << "yes" << endl;
    else if (n > y)
      cout << "no" << endl;
    else
      cout << "tie" << endl;

  }

}
