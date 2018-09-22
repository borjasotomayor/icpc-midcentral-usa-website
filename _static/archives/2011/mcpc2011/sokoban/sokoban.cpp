/* Sokoban, MCPC 2011 Problem G, C++ solution by Michael Goldwasser */
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
using namespace std;

// let's avoid symbol confusion
#define EMPTY              '.'
#define WALL               '#'
#define TARGET             '+'
#define BOX                'b'
#define BOX_ON_TARGET      'B'
#define WORKER             'w'
#define WORKER_ON_TARGET   'W'

#define MAX_R 15

ifstream fin("sokoban.in");

int R,C;
string board[MAX_R];   // represent "2D" board as array of strings

void printBoard(ostream& out = cout) {
    for (int j=0; j < R; j++) out << board[j] << endl;
}

bool isBox(char c) {
    return (c == BOX || c == BOX_ON_TARGET);
}

bool isVacant(char c) {
    return (c == EMPTY || c == TARGET);
}

int main() {
    int game(1);
    while (true) {
        fin >> R >> C;
        if (R == 0) break;

        int misplacedBoxes = 0;
        int wr(-1),wc(-1);  // worker's current position
        for (int i=0; i < R; i++) {
            fin >> board[i];

            for (int j=0; j < C; j++) {
                switch (board[i][j]) {
                case BOX:
                    misplacedBoxes++;
                    break;

                case WORKER:
                case WORKER_ON_TARGET:
                    wr = i; wc = j;
                    break;
                }
            }
        }

        string moves;
        fin >> moves;

        for (int i=0; i < moves.size() && misplacedBoxes > 0; i++) {    // process moves[i]
            int dr, dc;
            switch (moves[i]) {
            case 'U':
                dc = 0;
                dr = -1;
                break;
            case 'D':
                dc = 0;
                dr = 1;
                break;
            case 'L':
                dc = -1;
                dr = 0;
                break;
            case 'R':
                dc = 1;
                dr = 0;
                break;
            }

            char &current = board[wr][wc];
            char &neighbor = board[wr+dr][wc+dc];
            bool willMove = false;

            if (isVacant(neighbor))
                willMove = true;
            else if (isBox(neighbor)) {
                // possible move.
                char &lookahead = board[wr+2*dr][wc+2*dc];
                if (isVacant(lookahead)) {
                    willMove = true;
                    if (lookahead == TARGET) {
                        lookahead = BOX_ON_TARGET;
                        misplacedBoxes--;
                    } else {
                        lookahead = BOX;
                    }

                    if (neighbor == BOX_ON_TARGET) {
                        neighbor = TARGET;
                        misplacedBoxes++;
                    } else {
                        neighbor = EMPTY;
                    }
                }
            }

            if (willMove) {
                current = (current == WORKER_ON_TARGET) ? TARGET : EMPTY;
                neighbor = (neighbor == TARGET) ? WORKER_ON_TARGET : WORKER;
                wr += dr;
                wc += dc;
            }
        }

        cout << "Game " << game++ << ": " << ((misplacedBoxes == 0) ? "complete" : "incomplete") << endl;
        printBoard();
    }
}
