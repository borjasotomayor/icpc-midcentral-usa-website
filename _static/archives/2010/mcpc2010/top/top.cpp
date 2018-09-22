// Top This, Java version by Andy Harrington (ported to C++ by Michael Goldwasser)
#include <iostream>
#include <fstream>
using namespace std;

typedef long long int  long64;   // not sure which is standard across platforms

const int L = 6;  // output is LxL L < 8
const int GROUP_MAX = 3;
const int DOUBLE_GROUP_MAX = 6;   // need compile-time constant
int groupSize;  //number of pieces in a group

// pAll[index in all 7 pieces][rotation] = L x L bitfield 
long64 pAll[7][4];
int pieceIndex[DOUBLE_GROUP_MAX]; // pieces to use
long64 best;
long64 B;        // will initialize to 1LL << (L*L-1) at runtime;   // bit for [r][c] = [0][0]

long64 shift(long64 a, int r, int c) {
  return a >> (L*r + c);
}

bool get(long64 a, int r, int c) {// get bit [r][c]
  return (a & shift(B, r, c)) > 0;
}

long64 set(long64 a, int r, int c) {// set bit [r][c]
  return a | shift(B, r, c);
}

//assumes a shifted to upper left; return rotated, shifted up, left
long64 rot90Shift(long64 a) {
  long64 b=0;  
  int rMax = 0;
  for (int r = 0; r < L; r++)
    for (int c = 0; c < L; c++)
      if (get(a, r, c))
        rMax = r;
  for (int r = 0; r <= rMax; r++)
    for (int c = 0; c < L; c++) 
      if (get(a, r, c))
        b = set(b, c, rMax-r);
  return b;
}
                             
void print(long64 a) {
  for (int r = 0; r < L; r++) {
    for (int c = 0; c < L; c++) 
      cout << (get(a, r, c) ? '#' : '.');
    cout << endl;
  }
}

void findSol(long64 a, int next, long64 shape){
  if (next == 2*groupSize) {
    if (shape > best)
      best = shape;
    return;
  }
  if (next == groupSize) {
      if (a <= best)
        return;  // save lots of time to test if best here!
    shape = a;
    a = (B << 1) - 1 - a;  //invert all bits
  }
  int pNum = pieceIndex[next];
  int rows = 2, cols = 3;
  if (pNum == 0) {
    rows = 1;
    cols = 4;
  }
  for (int rot = 0; rot < 4; rot++) {
    for (int dr = 0; dr <= L-rows; dr++)
      for (int dc = 0; dc <= L-cols; dc++) { 
        long64 shifted = shift(pAll[pNum][rot], dr, dc);
        if ((a & shifted)== 0) // if disjoint
          findSol(a | shifted, next+1, shape); //solve wih union
      }
    int temp = rows;
    rows = cols;
    cols = temp;
  }
}

int main() {        
  B = 1LL << (L*L - 1);

  fstream in("top.in");
  string ps[7][2] = {{"####",  //quick coding - next convert to array
                    "...."},
                   {"###.",
                    "#..."},
                   {"###.",
                    ".#.."},
                   {"###.",
                    "..#."},
                   {"##..",
                    ".##."},
                   {"##..",
                    "##.."},
                   {".##.",
                    "##.."}};

  for (int i=0; i < 7; i++) { // set up all 4x4 boolean arrays
    pAll[i][0] = 0LL;  // need to initialize in C++
    for (int r = 0; r < 2; r++) {  //translate from String representation
      for (int c = 0; c < ps[i][r].size(); c++)  
        if (ps[i][r][c] == '#') {
          pAll[i][0] = set(pAll[i][0], r, c);
        }
    }
    for (int rot=0; rot<3; rot++)  // record rotations, shift up left
      pAll[i][rot+1] = rot90Shift(pAll[i][rot]);
  }

  int dataSets;
  in >> dataSets;
  for (int ds = 1; ds <= dataSets; ds++) {
    cout << ds << endl;
    groupSize = 3;             // hardwired for this problem
    for (int i=0; i<2; i++) {
      string pieces;
      in >> pieces;
      for (int j=0; j < groupSize; j++)
        pieceIndex[i*groupSize + j] = pieces[j] - 'A';
    }
    best = 0;
    findSol(0, 0, 0);
    if (best == 0) // array never replaced
      cout << "No solution" << endl;
    else
      print(best);
  }
}

