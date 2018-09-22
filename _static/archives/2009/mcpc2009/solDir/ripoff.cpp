/*  RIPOFF, MCPC 2009 Problem I, by Gabriel Foust
*/

#include <fstream>
#include <iostream>
#include <iomanip>
using namespace std;

/*
 * Explanation:
 *
 * board[i]        = the number of points for square i
 *                   (board[0] is the starting point - always 0)
 * canFinish[i][j] = can we finish from square i with j turns left
 * score[i][j]     = the best score we can get from square i with j turns left
 * next[i][j]      = the next square we'd go to get score[i][j]
 *                   (not needed, my program just traces for fun)
 *
 * When we're done, score[0][T] is the best score we can get from the start
 * with T turns left.
 */

int N;
int S;
int T;
int board[201];
bool canFinish[201][201];
int score[201][201];

short int next[201][201];

ifstream fin( "ripoff.in" );

bool getDataSet()
{
	fin >> N;
	if (N <= 0)
		return false;

	fin >> S >> T;

	board[0]= 0;
	for (int i= 1; i <= N; i++)
		fin >> board[i];

	return true;
}

void finish( int space, int left, int n )
{
	int extra= 0;
	if (n >= 0)
		extra= score[n][left - 1];

	if (canFinish[space][left])
	{
		if (score[space][left] < board[space] + extra)
		{
			score[space][left]= board[space] + extra;
			next[space][left]= n;
		}
	}
	else
	{
		score[space][left]= board[space] + extra;
		next[space][left]= n;
		canFinish[space][left]= true;
	}
}

void makeScoresFor( int space )
{
	for (int left= 0; left < 200; left++)
	{
		canFinish[space][left]= false;
		score[space][left]= 0;
	}

	for (int left= 1; left <= T; left++)
	{
		for (int move= 1; move <= S; move++)
		{
			if (space + move >= N + 1)
				finish( space, left, -1 );
			else if (canFinish[space + move][left - 1])
				finish( space, left, space + move );
		}
	}
}

int bestScore()
{
	for (int i= N; i >= 0; i--)
		makeScoresFor( i );

	int best= score[0][T];

	int start= next[0][T];
	int turn= T;
	for (int i= 1; i < N; i++)
	{
		if (i % 10 == 0)
			cerr << endl;
		if (i == start)
		{
			cerr << " (" << setw( 3 ) << board[i] << ')';
			start= next[i][--turn];
		}
		else
			cerr << setw( 5 ) << board[i] << ' ';
	}
	cerr << endl << endl;

	return best;
}

int main()
{
	while (getDataSet())
		cout << bestScore() << endl;
	return 0;
}
