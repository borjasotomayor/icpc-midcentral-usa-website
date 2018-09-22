/*
  markers.cpp
  The Mark of a Wizard, MCPC 2012 Problem E
  C++ version by Michael Goldwasser
*/

#include <iostream>
#include <fstream>
#include <vector>
#include <set>
using namespace std;

#define MAX_TRIALS 16
#define MAX_EDGES 35
#define MAX_SITES 17
#define MAX_DEGREE 6
#define MAX_PATH 7
void validate(int);     // used only for judging

ifstream fin("markers.in");

int degree[MAX_SITES];
int adj[MAX_SITES][MAX_DEGREE];    // adj[v][j] is the index of the jth neighbor of v
int wgt[MAX_SITES][MAX_DEGREE];    // wgt[v][j] is the distance of edge connecting v to its jth neighbor
int topo[MAX_SITES];
int sp[MAX_SITES];
int n;

int toInt(char c) {
    int k = (c - 'A');
    int answer = (k >= 0 && k <= 25 ? k : 26 + (c-'a'));
    if (answer < 0 || answer >= n)
	cerr << "PROBLEM with neighbor label " << c << endl;
    return answer;
}

void computeTopo() {
    int indeg[n];
    int k(0);
    vector<int> ready;
    for (int v=0; v<n; v++) {
	indeg[v] = 0;
    }

    for (int v=0; v<n; v++)
	for (int j=0; j<degree[v]; j++)
	    indeg[adj[v][j]]++;

    for (int v=0; v<n; v++) {
	if (indeg[v] == 0)
	    ready.push_back(v);
    }
    while (!ready.empty()) {
	int u = ready[ready.size()-1];
	ready.pop_back();
	topo[k++] = u;
	for (int j=0; j<degree[u]; j++) {
	    if (--indeg[adj[u][j]] == 0)
		ready.push_back(adj[u][j]);
	}
    }
    validate(k);   // for judging use only
}

bool isSolution(const set<int>& marked) {
    set<int> good;
    good.insert(n-1);
    for (int k=n-2; k >= 0; k--) {
	int u = topo[k];
	int countgood(0);
	for (int j=0; j<degree[u]; j++) {
	    if (good.count(adj[u][j]) == 1 && sp[u] == wgt[u][j] + sp[adj[u][j]])
		countgood++;
	}
	if (countgood >= 1 && (marked.count(u) == 1 || countgood == degree[u]))
	    good.insert(u);
    }
    return good.count(0) == 1;
}

int solve(set<int>& marked, int next) {
    if (isSolution(marked))
	return marked.size();
    int best = MAX_PATH;                // this must exist
    if (marked.size() < MAX_PATH - 1)
	for (int j=next; j<n; j++) {
	    marked.insert(j);
	    best = min(best, solve(marked, j+1));
	    marked.erase(j);
	}
    return best;
}

int main() {
    int trial(0);
    while (true) {
	fin >> n;
	if (n==0) break;
	if (++trial > MAX_TRIALS)
	    cerr << "PROBLEM with too many data sets" << endl;

	// read input
	for (int i=0; i<n; i++) {
	    char c;
	    fin >> c;
	    int v = toInt(c);
	    fin >> degree[v];
	    for (int j=0; j < degree[v]; j++) {
		fin >> c;
		adj[v][j] = toInt(c);
		fin >> wgt[v][j];
	    }
	}

	// Step 1) compute topo order of DAG
	computeTopo();

	// Step 2) compute shortest path lengths from all vertices to sink
	sp[n-1] = 0;   // the sink
	for (int k=n-2; k>=0; k--) {
	    int u = topo[k];
	    sp[u] = wgt[u][0] + sp[adj[u][0]];
	    for (int j=1; j<degree[u]; j++)
		sp[u] = min(sp[u], wgt[u][j] + sp[adj[u][j]]);
	}
	cout << sp[0];  // print shortest path distance (but no newline)

	// Step 3) try to find solution with at most MAX_PATH - 1 markers
	// (otherwise, certainly exists solution with MAX_PATH markers)
	set<int> marked;
	cout << " " << solve(marked, 0) << endl;
    }
}



// used only for judges
void validate(int k) {
    if (n > MAX_SITES)
	cerr << "PROBLEM with too many vertices" << endl;
    if (k != n)
	cerr << "PROBLEM computing topo" << endl;
    if (topo[n-1] != n-1)
	cerr << "PROBLEM as sink is not unique" << endl;
    int e(0);
    for (int j=0; j<n; j++) {
	e += degree[j];
	if (degree[j] > MAX_DEGREE)
	    cerr << "PROBLEM with degree of vertex " << j << endl;
    }
    if (e > MAX_EDGES)
	cerr << "PROBLEM with too many total edges" << endl;

}
