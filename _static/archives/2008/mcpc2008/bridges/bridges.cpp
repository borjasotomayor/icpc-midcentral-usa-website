// 2008 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem D: "The Bridges of San Mochti" [easy/moderate]
// Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

const int MAX_BRIDGES = 20 + 1;
const int MAX_CROSSING_TIME = 100;

int crossTime[MAX_BRIDGES]; // crossing time in seconds for bridge b
int capacity[MAX_BRIDGES];  // max number of people who can cross bridge b
int waiting[MAX_BRIDGES];   // number of people waiting to cross bridge b
int unit[MAX_BRIDGES];      // people in unit currently crossing bridge b
int timeLeft[MAX_BRIDGES];  // remaining time for unit crossing bridge b

int bridges, people;


void dumpState(int delta = 0, int total = 0) {
    for (int b = 0; b < bridges; b++) {
        cerr << waiting[b];
        if (unit[b] > 0)
            cerr << " /" << unit[b] << ":" << timeLeft[b] << "/ ";
        else
            cerr << " ";
    }
    cerr << waiting[bridges];
    if (delta > 0) cerr << " [+" << delta << " = " << total << "]";
    cerr << endl;
}


int main() {
    ifstream in("bridges.in");

    for (int config = 1; ; config++) {
        in >> bridges >> people;

        if (bridges == 0) break;

        bridges = -bridges;

        cerr << "CONFIGURATION " << config << endl;

        for (int b = 0; b < bridges; b++) {
            in >> capacity[b] >> crossTime[b];
            waiting[b] = unit[b] = timeLeft[b] = 0;
        }

        int seconds = 0;
        int crossing = 0;

        // everyone is initially waiting to cross the first bridge
        waiting[0] = people;

        // the bridges are numbered 0..(bridges-1), so waiting[bridges]
        // is really the number of people who have *crossed* the last bridge
        waiting[bridges] = 0;

        dumpState();

        while (waiting[bridges] < people) {
            int minTimeLeft = MAX_CROSSING_TIME;

            // put units on bridges, calculate minimum crossing time left
            for (int b = 0; b < bridges; b++) {
                if (waiting[b] > 0 && unit[b] == 0) {
                    unit[b] = min(waiting[b], capacity[b]);
                    waiting[b] -= unit[b];
                    timeLeft[b] = crossTime[b];
                }
                if (timeLeft[b] > 0)
                    minTimeLeft = min(timeLeft[b], minTimeLeft);
            }

            seconds += minTimeLeft;

            // decrease crossing times by minTimeLeft, completing
            // crossings when time drops to zero
            for (int b = 0; b < bridges; b++) {
                if (timeLeft[b] > 0) {
                    timeLeft[b] -= minTimeLeft;
                    if (timeLeft[b] == 0) {
                        waiting[b+1] += unit[b];
                        unit[b] = 0;
                    }
                }
            }

            dumpState(minTimeLeft, seconds);
        }

        cout << seconds << endl;
    }

    in.close();
}
