/* Shut the Box, MCPC 2011 Problem F, C++ solution by Michael Goldwasser */
#include <iostream>
#include <fstream>
#include <vector>
#include <set>

using namespace std;

#define MAX_N 22      // assumed to be at most 31 for using 32-bit codes
#define MAX_VAL 22  

ifstream fin("shut.in");
int N;                           // actual N for current trial
vector<int> choices[MAX_VAL+1];  // choices[v] is set of possible turn codes for value v

//-------------------- utilities for piece codes ------------------------
// We use int as bitfield to code which pieces are unmarked, with bit for
// 2^{p-1} being set if piece p is unmarked (i.e., least significant
// bit is piece 1). We will also use these codes to describe subsets
// of pieces that can be marked during a single turn.

// Return true if piece p is unmarked
bool unmarked(int code, int p) {
  return (code & (1 << (p-1))) > 0;   // bit is set
}

// returns true if every piece of turn is unmarked
bool legal(int code, int turn) {
  return ((code & turn) == turn);  // i.e. turn is subset of code
}

// return new code with extra piece marked
int markPiece(int code, int p) {   
  return code | (1 << (p-1));   // clear the bit
}

// return new code based on marking pegs from given turn (assuming legal)
int reduce(int code, int turn) {   
  return (code ^ turn);    // exclusive or 
}

// Returns number of marked pegs
int numMarked(int code) {
  int total(0);
  for (int p=1; p <= N; p++)
    if (!unmarked(code,p))
      total++;
  return total;
}

// Returns sum of the values of those pieces marked in the code
int pegSum(int code) {
  int total(0);
  for (int p=1; p <= N; p++)
    if (unmarked(code,p))
      total += p;
  return total;
}

// this function only needs to be called once, to precompute list of choices
void initChoices() {
  int half = MAX_VAL / 2;
  int halfcode = 1 << half;
  // to save time, we will only cycle through codes for bottom half of pegs.
  // can use at most one other peg.
  for (int code = 0; code < halfcode; code++) {
    int total = pegSum(code);
    if (total <= MAX_VAL) {
      choices[total].push_back(code);
      for (int p = half+1; p <= MAX_VAL; p++) {
        if (total + p <= MAX_VAL)
          choices[total+p].push_back(markPiece(code,p));
        else
          break;
      }
    }
  }
}

int main() {
  int game(1);

  N = MAX_N;      // for initialization
  initChoices();  // call only once

  while (true) {
    int T;
    vector<int> turns;

    fin >> N >> T;
    if (N == 0) break;

    for (int k=0; k < T; k++) {
      int temp;
      fin >> temp;
      turns.push_back(temp);
    }

    set<int> reachable;
    int initial = (1 << N) - 1;   // all N pegs unmarked
    int mostMarked(0);
    reachable.insert(initial);

    for (int t=0; t < T; t++) {
      int turn = turns[t];
      set<int> newReach;

      for (set<int>::const_iterator it = reachable.begin(); it != reachable.end(); ++it) {
        int curCode = *it;
        for (int k = 0; k < choices[turn].size(); k++) {
          int subset = choices[turn][k];
          if (legal(curCode,subset)) {
            int newCode = reduce(curCode,subset);
            newReach.insert(newCode);
            int score = numMarked(newCode);
            if (score > mostMarked)
              mostMarked = score;
          }
        }
      }
      reachable = newReach;
      if (reachable.size() == 0) break; // no reason to continue
    }

    cout << "Game " << game++ << ": " << mostMarked << endl;
  }
}
