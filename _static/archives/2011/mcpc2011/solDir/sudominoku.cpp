/* Su-domino-ku, MCPC 2011 Problem D, C++ solution by Michael Goldwasser */
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
using namespace std;

class Board {
public:
  vector<vector<int> > data;
  pair<int,int> opening;

  Board() {
    data.resize(9, vector<int>(9,0));
    opening = make_pair(0,0);
  }

  bool operator==(const Board& other) {
    return data == other.data;
  }

  bool operator<(const Board& other) {
    return data < other.data;
  }

  int get(int r, int c) const { return data[r][c]; }

  void set(int r, int c, int v) {
    data[r][c] = v;
    pair<int,int> cur(r,c);
    if (v == 0) { // clearing
      if (opening == make_pair(-1,-1) || cur < opening)
        opening = cur;
    } else if (opening == cur) {
      while (r < 9 && data[r][c] != 0) {
        c++;
        if (c == 9) {
          c = 0;
          r++;
        }
      }
      if (r == 9) {
        r = -1; c = -1;
      }
      opening = make_pair(r,c);
    }
  }

  // is current (partial) board legal thus far
  bool legal(int newR, int newC) const {
    std::set<int> check;

    // check row newR
    for (int c=0; c<9; c++) {
      int val = data[newR][c];
      if (val > 0 && !check.insert(val).second)
        return false;  
    }

    // check column newC
    check.clear();
    for (int r=0; r<9; r++) {
      int val = data[r][newC];
      if (val > 0 && !check.insert(val).second)
        return false;  
    }

    // check appropriate square
    int s = newR / 3;
    int t = newC / 3;
    check.clear();
    for (int i=0; i<3; i++)
      for (int j=0; j<3; j++) {
        int val = data[3*s+i][3*t+j];
        if (val > 0 && !check.insert(val).second)
          return false;  
      }
    
    return true;   // passed all tests
  }

  pair<int,int> firstOpening() const {
    return opening;
  }
};

ostream& operator<<(ostream& out, const Board& board) {
  for (int r=0; r<9; r++) {
    for (int c=0; c<9; c++) {
      if (board.get(r,c) > 0)
        out << board.get(r,c);
      else
        out << '.';
    }
    out << endl;
  }
  return out;
}


Board minBoard,maxBoard;
bool foundSoln;

int recursions(0);

// all of this is premised on a,b being distinct from 1 to 9,
// so we really only use subset of inventory[12] to inventory[89].
bool inventory[90];

// assume inventory is up to date
void solve(Board& board) {
    recursions++;                               // benchmarking
  pair<int,int> open = board.firstOpening();
  if (open.first == -1) {
    if (foundSoln) {
      if (board < minBoard) {
        minBoard = board;
      }
      if (maxBoard < board) {
        maxBoard = board;
      }
    } else {
      foundSoln = true;
      minBoard = maxBoard = board;
    }
  } else {
    // fill first square with a domino
    for (int t=12; t<90; t++) {
      if (inventory[t]) {
        pair<int,int> tile = make_pair(t/10,t%10);
        // consider all placements of that tile covering first openingn
        for (int reverse=0; reverse<2; reverse++)
          for (int vertical=0; vertical<2; vertical++) {
            int rOther = open.first + (vertical ? 0 : 1);
            int cOther = open.second + (vertical ? 1 : 0);
            if (rOther < 9 && cOther < 9 && board.get(rOther,cOther) == 0) {
              board.set(open.first,open.second, reverse ? tile.second : tile.first);
              board.set(rOther,cOther, reverse ? tile.first : tile.second);

              if (board.legal(open.first,open.second) && board.legal(rOther,cOther)) {
                inventory[t] = false;
                solve(board);
                inventory[t] = true;
              }
              board.set(open.first,open.second,0);
              board.set(rOther,cOther,0);
            }
          }
      }
    }
  }
}

int main(int argv, char** argc) {
  bool echoGreatest = (argv > 2);   

  ifstream fin;
  if (argv > 1) {
    fin.open(argc[1]);
  }

  if (!fin.is_open()) {
    fin.clear();
    fin.open("sudominoku.in");
  }


  int puzCount(0);
  while (true) {
    int n;
    fin >> n;
    if (n==0) break;

    Board board;

    for (int k=12; k<90; k++)
      inventory[k] = false;
    for (int a=1; a < 9; a++)
      for (int b=a+1; b <= 9; b++)
        inventory[a*10 + b] = true;

    // read dominos
    int a,b;
    string locA,locB;
    for (int k=0; k<n; k++) {
      fin >> a >> locA >> b >> locB;
      board.set(locA[0]-'A',locA[1]-'1', a);
      board.set(locB[0]-'A',locB[1]-'1', b);
      inventory[10*min(a,b) + max(a,b)] = false;
    }

    // read individual 1 to 9
    for (int k=1; k<=9; k++) {
      fin >> locA;
      board.set(locA[0]-'A',locA[1]-'1', k);
    }

    // quick check to make sure starting configuration is not flawed
    bool consistent = true;
    for (int r=0; consistent && r<9; r++)
      for (int c=0; consistent && c<9; c++)
        if (!board.legal(r,c)) {
          consistent = false;
          cerr << "ERROR: flawed initial conditions" << endl;
        }

    // time to fill the board
    foundSoln = false;
    if (consistent)
      solve(board);

    // output results
    cout << "Puzzle " << ++puzCount << endl;
    if (foundSoln) {
      cout << minBoard;
      if (echoGreatest) {
        cout << "max lexicographic:" << endl;
        if (maxBoard == minBoard)
          cout << "SAME" << endl;
        else
          cout << maxBoard;
      }
    } else {
      cout << "impossible" << endl;
    }
  }
  cerr << "Total number of recursions was: " << recursions << endl;
}
