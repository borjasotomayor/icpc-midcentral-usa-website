#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <vector>
#include <string>

using namespace std;

int main ()
{
    int board_size = 0;
    int num_players = 0;
    int num_cards = 0;

    ifstream infile("colors.in");
    if (!infile) {
	cerr << "Can't open colors.in" << endl; exit(2);
    }

    ofstream outfile("colors.out");
    if (!outfile) {
	cerr << "Can't open colors.out" << endl; exit(2);
    }


    infile >> num_players >> board_size >> num_cards;
    while (num_players > 0) {
	vector <int> position(num_players, -1);
        string board;
	infile >> board;
	
	vector <string> deck(num_cards);
	int i;
	for (i=0; i < num_cards; i++) {
	    infile >> deck[i];
	}
	
	int current = 0;
	bool winner = false;
	
	for (i=0; i < num_cards && !winner; i++) {
	    for (int j=0; j<deck[i].size() && !winner; j++) {
	        int new_pos =
		    board.find_first_of(deck[i][0], position[current]+1);
                if (new_pos < 0 || new_pos == board_size-1)
		    winner = true;
	        else
	            position[current] = new_pos;
            }
	    if (!winner)
	        current = (current+1 == num_players) ? 0 : current+1;
	}
	
	if (winner)
	    outfile << "Player " << current+1 << " won after " << i << " cards." << endl;
	else
	    outfile << "No player won after " << num_cards << " cards." << endl;

        infile >> num_players >> board_size >> num_cards;
    }

    return 0;
}

