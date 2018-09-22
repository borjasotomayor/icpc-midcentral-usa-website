// 2007 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem H: "Rock Skipping" [easy]
// Eric Shade, Missouri State University, with tweaks by Andy Harrington

#include <iostream>
#include <string>
#include <fstream>
using namespace std;

int max_count, max_len, max_i, min_d;
int maxima;     // used by judges for verification


// returns positive if better, 0 if equal, negative if worse

int compare_to_best(int c, int l, int i, int d) {
    if (c != max_count) return c - max_count;
    if (l != max_len) return l - max_len;
    if (i != max_i) return i - max_i;
    return min_d - d;
}


int main() {
    ifstream in("rock.in");
    string map;

    for (;;) {
        in >> map;
        if (map == "END") break;

        int n = map.length();
        max_count = max_len = max_i = -1;
        min_d = n;
        maxima = 0;
        
        for (int i = 0; i < n; i++) {
            for (int d = n - i; d > 0; d--) {
                int c = 0;
                int l;                
                
                for (l = i; l < n && map[l] == '.'; c++, l += d) ;
                
                if (l >= n) l -= d;
                
                int cmp = compare_to_best(c, l, i, d);
                
                if (cmp > 0) {
                    max_count = c;
                    max_len = l;
                    max_i = i;
                    min_d = d;
                    maxima = 1;
                } else if (cmp == 0) {
                    maxima++;
                }
            }
        }

        if (maxima != 1) cerr << max_i << " " << min_d << " is not unique!\n";
            
        cout << max_i << " " << min_d << endl;
    }

    in.close();
}
