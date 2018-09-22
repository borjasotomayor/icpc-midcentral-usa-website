/* Quick Search, MCPC 2010 Problem G, C++ solution  by Michael Goldwasser */
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <set>
#include <algorithm>
using namespace std;

//#define DEBUG

const int MAX_SITES(20);

// globals are our friends
fstream fin("search.in");
int numSites, numPolice, numEdges;
vector<int> graph[MAX_SITES];

// utility function
bool next_setting(vector<int>& choices, vector<int>& limits) {
  int k=choices.size();
  do {
    --k;
    if (choices[k] < limits[k]-1) {
      choices[k]++;
      return true;
    } else
      choices[k] = 0;
  } while (k > 0);
  return false;
}


class State {
private:
  vector<bool> visited;  // locations that have already been visited
  vector<int> police;    // stored in sorted order
  int depth;

public:
  State(int s, int n) : visited(s,false), police(n,0), depth(0) {
    visited[0] = true;
  }

  int getDepth() const { return depth;}

  vector<State> neighbors() const {
    vector<State> neigh;

    vector<int> edgeChoice(numPolice,0);
    vector<int> choiceCounts(numPolice);
    for (int k=0; k<numPolice; k++)
      choiceCounts[k] = graph[police[k]].size();
    do {
      State temp(*this);
      temp.depth++;
      for (int k=0; k < police.size(); k++) {
        temp.police[k] = graph[police[k]][edgeChoice[k]];
        temp.visited[temp.police[k]] = true;
      }
      sort(temp.police.begin(), temp.police.end());
      neigh.push_back(temp);
    } while (next_setting(edgeChoice, choiceCounts));

// #ifdef DEBUG
//     cout << "current state" << endl;
//     dump();
//     cout << "has following neighbors:" << endl;
//     for (int k=0; k<neigh.size(); k++)
//       neigh[k].dump();
//#endif

    return neigh;
  }

  bool solved() const {
    for (int k=0; k < visited.size(); k++)
      if (!visited[k])
        return false;
    return true;
  }

  pair<int,int> code() const {
    // computes a unique code to identify a state.  We use two ints,
    // where first is the binary value based on 'visited' (but
    // ignoring site A which is always visted) , and the second is the
    // "base b" value based on police vector, where 'b' is the number
    // of sites.
    int first = 0;
    for (int k=1; k < visited.size(); k++)  // intentionally ignore 'A' as its always covered
      if (visited[k])
        first += 1<<(visited.size() - 1 - k);

    int second(0);
    int digit = 1;
    for (int k=0; k < police.size(); k++) {
      second += police[k] * digit;
      digit *= numSites;
    }

    return make_pair(first,second);
  }

#ifdef DEBUG
  void dump() const {
    cout << "visited: ";
    for (int k=0; k < visited.size(); k++)  // intentionally ignore 'A' as its always covered
      if (visited[k])
        cout << char('A' + k);
    cout << endl;

    cout << "police: ";
    for (int k=0; k < police.size(); k++)
      cout << char('A' + police[k]);
    cout << endl;
  }
#endif

};

int solve() {
  set<pair<int,int> > familiar;
  queue<State> fringe;
  State initial(numSites, numPolice);
  fringe.push(initial);
  familiar.insert(initial.code());

  while (fringe.size() > 0) {
    State next(fringe.front());
    fringe.pop();

#ifdef DEBUG
    cout << "Considering fringe state:" << endl;
    next.dump();
#endif    

    vector<State> others = next.neighbors();
    for (int k=0; k < others.size(); k++) {
      pair<int,int> id = others[k].code();
      if (familiar.find(id) == familiar.end()) {
        // new state
        familiar.insert(id);
        if (others[k].solved())
          return others[k].getDepth();
        fringe.push(others[k]);
      }
    }
  }
  cout << "Unsolvable" << endl;    // hopefully never happens
}

int main() {
  while (true) {
    fin >> numSites;
    if (numSites==0) break;    // all done

    fin >> numPolice >> numEdges;
    for (int k=0; k < numSites; k++)
      graph[k].clear();

    string edge;
    for (int k=0; k<numEdges; k++) {
      fin >> edge;
      graph[edge[0]-'A'].push_back(edge[1]-'A');
      graph[edge[1]-'A'].push_back(edge[0]-'A');
    }

    cout << solve() << endl;
  }
}
