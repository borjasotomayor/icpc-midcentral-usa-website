/*  Judges' Time Calculation, ACM MCPC 2010 Problem A by Michael Goldwasser */

#include <iostream>
#include <fstream>
using namespace std;

ifstream fin("calc.in");

int main() {
  int N;
  fin >> N;
  for (int trial=0; trial < N; trial++) {
    int SH,SM,DH,DM;
    fin >> SH >> SM >> DH >> DM;

    int duration = 60*DH + DM;
    int lasthour = SH + (SM + duration) / 60;

    cout << "------+---------" << endl;
    cout << " time | elapsed" << endl;     // no space after elapsed
    cout << "------+---------" << endl;

    int offset = -SM;
    for (int hour = SH; hour <= lasthour; hour++, offset+=60) {
      int displayhour = (hour > 12) ? hour - 12 : hour;
      if (displayhour < 10)
        cout << " ";
      cout << displayhour << ":XX | XX";
      if (offset < 0)
        cout << " - " << -offset;
      else if (offset > 0)
        cout << " + " << offset;
      cout << endl;
    }
  }
}
