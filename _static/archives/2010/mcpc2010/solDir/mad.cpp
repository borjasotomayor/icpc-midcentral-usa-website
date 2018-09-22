/* Mad Scientist, MCPC 2010 Problem B by Michael Goldwasser */

#include <iostream>
#include <fstream>
using namespace std;

fstream fin("mad.in");
const int MAX(26);       // max for n.  k is at least one less
int k;
int P[MAX];    // P-sequence  (leaving P[0] empty for convenience)
int M[MAX];    // Original sequence of measurements

int main() {

  while (true) {
    fin >> k;
    if (k==0) break;     // all done

    for (int j=1; j <= k; j++)
      fin >> P[j];

    int n(0);
    int j(1);

    while (j <= k) {
      while (n < P[j])
        M[n++] = j;   // add another 'j' to measurement sequence
      j++;
    }   

    cout << M[0];
    for (int i=1; i < n; i++)
      cout << " " << M[i];
    cout << endl;
  }
}
