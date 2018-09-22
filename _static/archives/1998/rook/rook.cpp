// rook.cpp   Andy Harrington Loyola University Chicago 9/98
#include <fstream.h>
const MAXSIZE = 4;
int size;
const char OPEN = '.';
const char PIECE = 'P';  // everything else is a wall
char board[MAXSIZE+2][MAXSIZE+2];  // location (x,y) is board[y][x]
 // have space for outer walls at index 0 and size+1 as search sentinels
ifstream inf("rook.in");
ofstream outf("rook.out");

int safeDir(int x, int y, int dx, int dy) {
    // true if no piece visible in direction (dx, dy)
    // must terminate because of sentinel 'X' or \0 placed around edges
    do {
        x += dx; y += dy;
    } while (board[y][x] == OPEN);
    return (board[y][x] != PIECE);
}

int safePlace(int x, int y) {
// only check lower x and y coordinates: no pieces in other directions yet
    return safeDir(x, y, -1, 0) && safeDir(x, y, 0, -1);
}

int moreSafe(int x, int y)  {
    // how many more pieces can be added safely 
    //     with higher y or with same y and higher x
    int nPlaced, mostPlaced = 0;
    // first (x,y) may be right sentinel -- skips 1st IF, fixed in 2nd IF
    while (y <= size) {
        if (board[y][x] == OPEN && safePlace(x, y)) {
            board[y][x] = PIECE;  // place piece in this next safe place
            nPlaced = 1 + moreSafe(x+1, y);  // count piece just placed
            if (nPlaced > mostPlaced) mostPlaced = nPlaced; //
            board[y][x] = OPEN; // remove piece placed; go on to next
        }
        if (x >= size) {              // done with row
            x = 1;                   // start 
            y++;                     //   next row 
        } else x++;
    }
    return mostPlaced;  
}

void main() {
    inf >> size;
    while (size > 0) {
        for(int i=1; i <= size; i++) {
            // add outer walls at index 0 and size+1 as search sentinels
            board[0][i] = board[size+1][i] = 'X'; // top, bottom walls
            board[i][0] = 'X';   // left wall
            inf >> board[i]+ 1;  // nulls from strings are right wall
        }
        outf << moreSafe(1, 1) << endl;
        inf >> size;
    }
}
            
