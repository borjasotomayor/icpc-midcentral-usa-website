/* Queen Collison, MCPC 2010 Problem D, C++ solution by Michael Goldwasser */
#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

fstream fin("collision.in");

int main() {
  int n,g;
  fin >> n;
  while (n > 0) {

    /*
     * tallies[0][x]        tracks each row from 1 to n
     * tallies[1][y]        tracks each column from 1 to n
     * tallies[2][x+y]      tracks each diagonal from 2 to 2*n
     * tallies[3][n+1+x-y]  tracks each reverse diagonal from 2 to 2*n
     */
    int tallies[4][2*n+1];
    for (int j=0; j<4; j++)
      for (int k=0; k <= 2*n; k++)
        tallies[j][k] = 0;

    fin >> g;
    for (int i=0; i<g; i++) {
      int k,x,y,s,t;
      fin >> k >> x >> y >> s >> t;
      for (int j=0; j<k; j++) {
        tallies[0][x]++;
        tallies[1][y]++;
        tallies[2][x+y]++;
        tallies[3][1+n+x-y]++;

        x += s;
        y += t;
      }
    }

    // compute number of collisions
    long count = 0;
    for (int j=0; j<4; j++)
      for (int k=0; k <= 2*n; k++)
        if (tallies[j][k] > 1)
          count += (tallies[j][k] - 1);

    cout << count << endl;
    fin >> n;
  }
}
