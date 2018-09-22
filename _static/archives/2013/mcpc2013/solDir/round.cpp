/*
  round.cpp
  Round Robin, MCPC 2013, Problem B
  C++ solution by Michael Goldwasser

  This problem is small enough for us to just do the simulation step-by-step.
*/
#include <iostream>
#include <fstream>
#include <list>
using namespace std;

ifstream fin("round.in");

int main() {
    int N,T;
    while (true) {
        fin >> N;
        if (N == 0) break;
        fin >> T;
        int count=0;
        list<int> game(N,0);        // actual value of 0 is irrelevant
        list<int>::iterator cur = game.begin();
        do {
            for (int j=0; j < T-1; j++) {  // note: only T-1 steps thus far
                ++cur;
                if (cur == game.end()) {
                    count++;               // everyone has had another turn
                    cur = game.begin();
                }
            }
            cur = game.erase(cur);      // bye-bye 
            if (cur == game.end()) {
                count++;
                cur = game.begin();
            }
        } while (cur != game.begin());

        cout << game.size() << " " << count << endl;
    }
}
